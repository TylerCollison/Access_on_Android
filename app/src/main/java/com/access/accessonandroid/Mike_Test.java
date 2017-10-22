package com.access.accessonandroid;

/**
 * Created by suzey on 10/21/2017.
 */

//
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.services.rekognition.AmazonRekognition;
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

public class Mike_Test {
    //Log.d("Testing", "Testing"//);
//    android.util.Log("Testing");
    public static void mike_test() {

        System.out.println("sdf");
    }

//    public static void m_test(String[] args) throws Exception {
//        String photo = "photo.jpg";
//        String bucket = "S3bucket";

//        AWSCredentials credentials;
//        try {
//            credentials = new ProfileCredentialsProvider("AdminUser").getCredentials();
//        } catch(Exception e) {
//            throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
//                    + "Please make sure that your credentials file is at the correct "
//                    + "location (/Users/userid/.aws/credentials), and is in a valid format.", e);
//        }

//        // Initialize the Amazon Cognito credentials provider
//        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
//                getApplicationContext(),
//                "us-east-1:06e29e47-16cb-4d97-a6cc-cc9f5d774691", // Identity pool ID
//                Regions.US_EAST_1 // Region
//        );

//        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder
//                .standard()
//                .withRegion(Regions.US_WEST_2)
//                .withCredentials(new AWSStaticCredentialsProvider(credentials))
//                .build();
//
//        DetectLabelsRequest request = new DetectLabelsRequest()
//                .withImage(new Image()
//                        .withS3Object(new S3Object()
//                                .withName(photo).withBucket(bucket)))
//                .withMaxLabels(10)
//                .withMinConfidence(75F);
//
//        try {
//            DetectLabelsResult result = rekognitionClient.detectLabels(request);
//            List <Label> labels = result.getLabels();
//
//            System.out.println("Detected labels for " + photo);
//            for (Label label: labels) {
//                System.out.println(label.getName() + ": " + label.getConfidence().toString());
//            }
//        } catch(AmazonRekognitionException e) {
//            e.printStackTrace();
//        }
//    }
}
