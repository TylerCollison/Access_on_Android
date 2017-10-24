package com.access.accessonandroid.FacialRecog;

/**
 * Created by suzey on 10/21/2017.
 */

//
import android.content.Context;

import android.util.Log;
import java.util.ArrayList;


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

/* Theads staff */
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FacialRegSu implements FacialRecog{
    private Float SIMILARITY_THRESHOLD;
    private Context applicationContext;
    private CognitoCachingCredentialsProvider generalAwsCredential;


    /**
     * Constructor
     * @param androidAppContext
     * @param awsCredential
     */
    public FacialRegSu(Context androidAppContext, CognitoCachingCredentialsProvider awsCredential) {
        this.SIMILARITY_THRESHOLD = 70F;
        this.applicationContext = androidAppContext;
        this.generalAwsCredential = awsCredential;
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



    public boolean compareFaces(final Image imageA, final Image imageB) {
        boolean isMatch = false;

        ExecutorService executor = Executors.newFixedThreadPool(1);
        List<Future<Boolean>> list = new ArrayList<Future<Boolean>>();
        Callable<Boolean> callable = new Callable() {
            @Override
            public Boolean call() throws Exception {
                boolean result = false;
                try {
                    result = compareFacesHelper(imageA, imageB);
                } catch(Exception e) {
                    System.out.println("Something went wrong..");
                }
                return result;
            }
        };

        Future<Boolean> future = executor.submit(callable);

        try {
            //print the return value of Future, notice the output delay in console
            // because Future.get() waits for task to get completed
            isMatch = future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
        return isMatch;


//
//        boolean result = false;
//        new Thread(new Runnable(){
//            @Override
//            public void run() {
//                try {
//                    compareFacesHelper(imageA, imageB);
//                } catch(Exception e) {
//                    System.out.println("Something went wrong..");
//                }
//            }
//        }).start();
//
//
//        ExecutorService pool = Executors.newFixedThreadPool(3);
//        Set<Future<Integer>> set = new HashSet<Future<Integer>>();
//        Callable<Integer> callable = new WordLengthCallable(word);
//        Future<Integer> future = pool.submit(callable);
//        set.add(future);
//        for (Future<Integer> future : set) {
//            result = future.get();
//        }
//
//        return result;
    }


    /**
     * Given two images, report whether they match with a SIMILARITY_THREASHOLD certainty.
     * @param imageA
     * @param imageB
     * @return
     */
    private boolean compareFacesHelper(Image imageA, Image imageB) {
        CognitoCachingCredentialsProvider credentialsProvider = this.generalAwsCredential;
        AmazonRekognition client = new AmazonRekognitionClient(credentialsProvider);

        CompareFacesRequest request = new CompareFacesRequest()
                .withSourceImage(imageA)
                .withTargetImage(imageB)
                .withSimilarityThreshold(this.SIMILARITY_THRESHOLD);

        CompareFacesResult compareFacesResult=client.compareFaces(request);

        // Display results
        List <CompareFacesMatch> faceDetails = compareFacesResult.getFaceMatches();

        // Check if there is a match. This is currently flawed
        boolean isMatched = faceDetails.size() == 1;

        return isMatched;
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
            System.exit(1);                                                         // #ToDo Probably shouldn't exit the program!
        }

        Image result=new Image()
                .withBytes(imageBytes);
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




    /**
     * Only here for my sanity. You can safely ignore this method.
     *
     * @param applicationContext
     * @return
     * @throws Exception
     */
    private boolean mikes_test(android.content.Context applicationContext) throws Exception {
        String IDENTITY_POOL_ID = "intentionally scambled********";
        String photo = "my_face.jpg";
        String bucket = "infosecurity";


        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                applicationContext,
                IDENTITY_POOL_ID, // Identity pool ID
                Regions.US_EAST_1 // Region
        );


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
        Float similarityThreshold = this.SIMILARITY_THRESHOLD;
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

        return true;
    }



}
