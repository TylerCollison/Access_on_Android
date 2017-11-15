package com.access.accessonandroid.FacialRecog;

/**
 * Created by suzey on 10/21/2017.
 */

//
import android.content.Context;


import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
//import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
//import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.S3Object;
import java.util.List;

/* Detect Face Staff */
import com.amazonaws.services.rekognition.model.Attribute;
import com.amazonaws.services.rekognition.model.DetectFacesRequest;
import com.amazonaws.services.rekognition.model.DetectFacesResult;
import com.amazonaws.services.rekognition.model.FaceDetail;
//import com.amazonaws.services.rekognition.model.AgeRange;
//import com.fasterxml.jackson.databind.ObjectMapper;
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
                "***scambled****", // Identity pool ID
                Regions.US_EAST_1 // Region
        );
    }
//
//    private class MyCallable implements Callable<Boolean> {
//        @Override
//        public Boolean call() throws Exception {
//            boolean result = false;
//            try {
//                result = compareFacesHelper(imageA, imageB);
//            } catch(Exception e) {
//                System.out.println("Something went wrong..");
//            }
//            return result;
//        }
//    }
//

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

//            for (FaceDetail face: faceDetails) {
//                if (detectFaceRequest.getAttributes().contains("ALL")) {
//                    AgeRange ageRange = face.getAgeRange();
//                    System.out.println("The detected face is estimated to be between "
//                            + ageRange.getLow().toString() + " and " + ageRange.getHigh().toString()
//                            + " years old.");
//                    System.out.println("Here's the complete set of attributes:");
//                } else { // non-default attributes have null values.
//                    System.out.println("Here's the default set of attributes:");
//                }
//
//                ObjectMapper objectMapper = new ObjectMapper();
//                System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(face));
//            }

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
    private boolean compareFaces(Image imageOnFile, Image target) {
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

//        System.out.println("Comparing Faces...");
//        System.out.println("The result is: " + isMatched + " Hohoho!");

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


















    // -------------------------------------- YOU SHOULDN'T HAVE TO READ ANYTHING BELOW THIS LINE ------------------------
    //    public static void mike_test() {
//
//        System.out.println("sdf");
//    }




//    /**
//     * Only here for my sanity. You can safely ignore this method.
//     *
//     * @param applicationContext
//     * @return
//     * @throws Exception
//     */
//    private boolean mikes_test(android.content.Context applicationContext) throws Exception {
//        String IDENTITY_POOL_ID = "intentionally scambled********";
//        String photo = "my_face.jpg";
//        String bucket = "infosecurity";
//
//
//        // Initialize the Amazon Cognito credentials provider
//        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
//                applicationContext,
//                IDENTITY_POOL_ID, // Identity pool ID
//                Regions.US_EAST_1 // Region
//        );
//
//
//        AmazonRekognition client = new AmazonRekognitionClient(credentialsProvider);
//
//
//        /* DETECTLABELSREQUEST */
////        DetectLabelsRequest request = new DetectLabelsRequest()
////                .withImage(new Image()
////                        .withS3Object(new S3Object()
////                                .withName(photo).withBucket(bucket)))
////                .withMaxLabels(10)
////                .withMinConfidence(75F);
////
////        try {
////            DetectLabelsResult result = client.detectLabels(request);
////            List <Label> labels = result.getLabels();
////
////            System.out.println("Detected labels for " + photo);
////            for (Label label: labels) {
////                System.out.println(label.getName() + ": " + label.getConfidence().toString());
////            }
////        } // catch(AmazonRekognitionException e) {
//////            e.printStackTrace();
//////        }
////        catch (Exception e) {           // Don't know what to do with this! To-DO
////            e.printStackTrace();
////        }
//
//        /* Comapre Faces */
//        Float similarityThreshold = this.SIMILARITY_THRESHOLD;
//        String sourceImageName = "target.jpg";
//        String targetImageName = "target_2.jpg";
//        String sourceBucket = "infosecurity";
//        String targetBucket = "infosecurity";
//        ByteBuffer sourceImageBytes=null;
//        ByteBuffer targetImageBytes=null;
//
//
////        //Load source and target images and create input parameters
////        try (InputStream inputStream = new FileInputStream(new File(sourceImage))) {
////            sourceImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
////        }
////        catch(Exception e)
////        {
////            System.out.println("Failed to load source image " + sourceImage);
////            System.exit(1);
////        }
////        try (InputStream inputStream = new FileInputStream(new File(targetImage))) {
////            targetImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
////        }
////        catch(Exception e)
////        {
////            System.out.println("Failed to load target images: " + targetImage);
////            System.exit(1);
////        }
//
////        Image source=new Image()
////                .withBytes(sourceImageBytes);
////        Image target=new Image()
////                .withBytes(targetImageBytes);
//
//        Image source = new Image().withS3Object(new S3Object()
//                .withName(sourceImageName)
//                .withBucket(sourceBucket));
//        Image target = new Image().withS3Object(new S3Object()
//                .withName(targetImageName)
//                .withBucket(targetBucket));
//
//        CompareFacesRequest request = new CompareFacesRequest()
//                .withSourceImage(source)
//                .withTargetImage(target)
//                .withSimilarityThreshold(similarityThreshold);
//
//
//        // Call operation
//        CompareFacesResult compareFacesResult=client.compareFaces(request);
//
//        // Display results
//        List <CompareFacesMatch> faceDetails = compareFacesResult.getFaceMatches();
//        for (CompareFacesMatch match: faceDetails){
//            ComparedFace face= match.getFace();
//            BoundingBox position = face.getBoundingBox();
//            System.out.println("Face at " + position.getLeft().toString()
//                    + " " + position.getTop()
//                    + " matches with " + face.getConfidence().toString()
//                    + "% confidence.");
//
//        }
//
//        System.out.println("There were " + faceDetails.size()
//                + " that did match");
//
//        /* End of Compare Faces */
//
//        return true;
//    }



}
