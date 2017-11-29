package com.access.accessonandroid.FacialRecog;

/**
 * Created by Zeyang Su on 10/21/2017.
 */

//
import android.content.Context;


import com.access.accessonandroid.Data.AWSCognitoCredentialProvider;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.S3Object;
import java.util.List;

/* Detect Face Staff */
import com.amazonaws.services.rekognition.model.Attribute;
import com.amazonaws.services.rekognition.model.DetectFacesRequest;
import com.amazonaws.services.rekognition.model.DetectFacesResult;
import com.amazonaws.services.rekognition.model.FaceDetail;
/* End of Detect Face Staff */

/* Compare Face Staff */
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import com.amazonaws.util.IOUtils;
import com.amazonaws.services.rekognition.model.CompareFacesMatch;
import com.amazonaws.services.rekognition.model.CompareFacesRequest;
import com.amazonaws.services.rekognition.model.CompareFacesResult;
import com.amazonaws.services.rekognition.model.ComparedFace;
import com.amazonaws.services.rekognition.model.BoundingBox;

/* Theads staff */

public class FacialRegSu implements FacialRecog{
    private Thread newThread;

    private Float SIMILARITY_THRESHOLD;
    private Context applicationContext;
    private CognitoCachingCredentialsProvider generalAwsCredential;


    /**
     * Constructor
     * @param androidAppContext
//     * @param awsCredential
     */
    public FacialRegSu(Context androidAppContext) {                                         // , CognitoCachingCredentialsProvider awsCredential
        this.newThread = null;

        this.SIMILARITY_THRESHOLD = 70F;
        this.applicationContext = androidAppContext;                                        //        this.generalAwsCredential = awsCredential;

        this.generalAwsCredential = new CognitoCachingCredentialsProvider(      // @TODO Should move this out eventually
                this.applicationContext,
                "us-east-1:06e29e47-16cb-4d97-a6cc-cc9f5d774691", // Identity pool ID
                Regions.US_EAST_1 // Region
        );

//        if (AWSCognitoCredentialProvider.getInstance().GetProvider() == null) {     // If the Amazon AWS credential has not been initialized
//            AWSCognitoCredentialProvider.getInstance().CreateProvider(androidAppContext);           // initialize Amazon AWS credential
//        }
//        this.generalAwsCredential = (CognitoCachingCredentialsProvider) AWSCognitoCredentialProvider.getInstance().GetProvider();   // store the Amazon AWS credential

        }


    /**
     * Mainly used as a helper function
     * @param pic
     * @return Number of faces detected
     */
    public int detectFaces(Image pic) {
        int numOfFacesDetected = 0;         // Default is 0

        CognitoCachingCredentialsProvider credentialsProvider = this.generalAwsCredential;
        AmazonRekognition client = new AmazonRekognitionClient(credentialsProvider);

        DetectFacesRequest detectFaceRequest = new DetectFacesRequest()
                .withImage(pic);
        try {
            DetectFacesResult result = client.detectFaces(detectFaceRequest);
            List < FaceDetail > faceDetails = result.getFaceDetails();
            numOfFacesDetected = faceDetails.size();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return numOfFacesDetected;
    }

    /**
     * Given two images, report whether they match with a SIMILARITY_THRESHOLD certainty.
     * @param imageOnFile
     * @param target
     * @return
     */
    public boolean compareFaces(Image imageOnFile, Image target) {
        // Initialize the Amazon Cognito credentials provider
        boolean isMatched = false;


        CognitoCachingCredentialsProvider credentialsProvider = this.generalAwsCredential;
        AmazonRekognition client = new AmazonRekognitionClient(credentialsProvider);

        /* Detect how many faces are in the given picture */
        int numOfFacesDetected = detectFaces(target);
//        System.out.println("Number of faces detected: " + numOfFacesDetected);
        if (numOfFacesDetected != 1) {      // if the target image has 0 or more than 1 faces in it
            return isMatched;               // return no match
        }


        CompareFacesRequest request = new CompareFacesRequest()
                .withSourceImage(imageOnFile)
                .withTargetImage(target)
                .withSimilarityThreshold(this.SIMILARITY_THRESHOLD);

        CompareFacesResult compareFacesResult=client.compareFaces(request);

        // Display results
        List <CompareFacesMatch> faceDetails = compareFacesResult.getFaceMatches();

        // Check if there is a match.
        isMatched = faceDetails.size() == 1;

        return isMatched;
    }



    public boolean compareFacesThreadedBlocking(final Image imageOnFile, final Image target) {
        boolean isMatch = false;            // default facial comparison result

        final boolean[] facialCompResult = new boolean[1];
        facialCompResult[0] = false;         //default facial comparison result

        Runnable codeToRunOnNewThread = new FacialRecogRunnable(this, imageOnFile, target, facialCompResult);

        if (this.newThread != null && this.newThread.isAlive()) {          // A thread is already running. Ignore facial comparison request and return false
            return isMatch;
        }
        this.newThread = new Thread(codeToRunOnNewThread);
        this.newThread.start();
        try {
            this.newThread.join();
            isMatch = facialCompResult[0];      // Once the thread is finished the result will be stored at facialCompResult[0]

        }catch(InterruptedException e) {
            e.printStackTrace();
            System.out.println("Something wrong with the facial recognition threaded face comparison!");
        }

        return isMatch;

    }



    public boolean compareFacesWithCallbackFunc(final Image imageOnFile, final Image target, final FacialRecogCallbackFuncObj callbackFunc) {
        boolean comparisonInitiated = false;            // default facial comparison result

        // THIS IS NOT PERFECT. IT DOES NOT ACCOUNT FOR RACE CONDITION. BUT FOR THE PURPOSE OF A DEMONSTRATION IT SHOULD SUFFICE
        if (this.newThread != null && this.newThread.isAlive()) {          // A thread is already running. Ignore facial comparison request and return false
            return comparisonInitiated;
        }

        Runnable codeToRunOnNewThread = new Runnable() {
            @Override
            public void run() {
                boolean result = false;
                result = compareFaces(imageOnFile, target);
                callbackFunc.execute(result);           // invoke callback function
            }
        };

        this.newThread = new Thread(codeToRunOnNewThread);
        this.newThread.start();
        comparisonInitiated = true;

        return comparisonInitiated;
    }



    /**
     * TODO Needs to be improved upon
     * @param filePath
     * @return
     */
    public static Image makeImageFromLocalFile(String filePath) {
        ByteBuffer imageBytes=null;
        try (InputStream inputStream = new FileInputStream(new File(filePath))) {
            imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        }
        catch(Exception e)
        {
            System.out.println("Failed to load source image " + filePath);
//            System.exit(1);                                                         // #ToDo Probably shouldn't exit the program!
        }

        Image result=new Image()
                .withBytes(imageBytes);
        return result;
    }


    public static Image makeImageFromByteBuffer(ByteBuffer inputBuffer) {
        Image result = new Image()
                .withBytes(inputBuffer);
        return result;
    }



    public static Image makeImageFromS3File (String fileName, String bucketName) {
        Image result = new Image().withS3Object(new S3Object()         // WHY does this work!?
                .withName(fileName)
                .withBucket(bucketName));
        return result;
    }

}
