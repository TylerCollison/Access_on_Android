package com.access.accessonandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



/* Mike's Imports */
import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
//import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
//import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;
import com.amazonaws.services.rekognition.model.S3Object;
import java.util.List;

/* Detect Face Staff */
//import com.amazonaws.services.rekognition.model.Attribute;
//import com.amazonaws.services.rekognition.model.DetectFacesRequest;
//import com.amazonaws.services.rekognition.model.DetectFacesResult;
//import com.amazonaws.services.rekognition.model.FaceDetail;
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

/* End of Compare Face Staff */
/* End of Mike's Imports*/
/* Nick test push */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // ----------- Mike's Code ----------------
//        new Thread(new Runnable(){
//            @Override
//            public void run() {
//                try {
//                    mikes_test();
//                } catch(Exception e) {
//                    System.out.println("Something went wrong..");
//                }
//            }
//        }).start();
        // -------------- End of Mike's Code ----------
    }

    public void mikes_test() throws Exception {
        String IDENTITY_POOL_ID = "intentionally scambled********";
        String photo = "my_face.jpg";
        String bucket = "infosecurity";

        AWSCredentials credentials;
//        try {
//            credentials = new ProfileCredentialsProvider("AdminUser").getCredentials();
//        } catch(Exception e) {
//            throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
//                    + "Please make sure that your credentials file is at the correct "
//                    + "location (/Users/userid/.aws/credentials), and is in a valid format.", e);
//        }

        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                IDENTITY_POOL_ID, // Identity pool ID
                Regions.US_EAST_1 // Region
        );

//        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder
//                .standard()
//                .withRegion(Regions.US_WEST_2)
//                .withCredentials(new AWSStaticCredentialsProvider(credentials))
//                .build();

        AmazonRekognition client = new AmazonRekognitionClient(credentialsProvider);


        /* DETECTLABELSREQUEST */
//        DetectLabelsRequest request = new DetectLabelsRequest()
//                .withImage(new Image()
//                        .withS3Object(new S3Object()
//                                .withName(photo).withBucket(bucket)))
//                .withMaxLabels(10)
//                .withMinConfidence(75F);
//
//        try {
//            DetectLabelsResult result = client.detectLabels(request);
//            List <Label> labels = result.getLabels();
//
//            System.out.println("Detected labels for " + photo);
//            for (Label label: labels) {
//                System.out.println(label.getName() + ": " + label.getConfidence().toString());
//            }
//        } // catch(AmazonRekognitionException e) {
////            e.printStackTrace();
////        }
//        catch (Exception e) {           // Don't know what to do with this! To-DO
//            e.printStackTrace();
//        }

        /* Comapre Faces */
        Float similarityThreshold = 70F;
        String sourceImageName = "target.jpg";
        String targetImageName = "target_2.jpg";
        String sourceBucket = "infosecurity";
        String targetBucket = "infosecurity";
        ByteBuffer sourceImageBytes=null;
        ByteBuffer targetImageBytes=null;


//        //Load source and target images and create input parameters
//        try (InputStream inputStream = new FileInputStream(new File(sourceImage))) {
//            sourceImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
//        }
//        catch(Exception e)
//        {
//            System.out.println("Failed to load source image " + sourceImage);
//            System.exit(1);
//        }
//        try (InputStream inputStream = new FileInputStream(new File(targetImage))) {
//            targetImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
//        }
//        catch(Exception e)
//        {
//            System.out.println("Failed to load target images: " + targetImage);
//            System.exit(1);
//        }

//        Image source=new Image()
//                .withBytes(sourceImageBytes);
//        Image target=new Image()
//                .withBytes(targetImageBytes);

        Image source = new Image().withS3Object(new S3Object()
                                                     .withName(sourceImageName)
                                                     .withBucket(sourceBucket));
        Image target = new Image().withS3Object(new S3Object()
                                                      .withName(targetImageName)
                                                      .withBucket(targetBucket));

        CompareFacesRequest request = new CompareFacesRequest()
                .withSourceImage(source)
                .withTargetImage(target)
                .withSimilarityThreshold(similarityThreshold);


        // Call operation
        CompareFacesResult compareFacesResult=client.compareFaces(request);

        // Display results
        List <CompareFacesMatch> faceDetails = compareFacesResult.getFaceMatches();
        for (CompareFacesMatch match: faceDetails){
            ComparedFace face= match.getFace();
            BoundingBox position = face.getBoundingBox();
            System.out.println("Face at " + position.getLeft().toString()
                    + " " + position.getTop()
                    + " matches with " + face.getConfidence().toString()
                    + "% confidence.");

        }

        System.out.println("There were " + faceDetails.size()
                + " that did match");

        /* End of Compare Faces */

    }
}

// This is a test push from Megan G

// test push from mae :)

//test puch Daniel