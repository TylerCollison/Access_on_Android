package com.access.accessonandroid.FacialRecog;

/**
 * Created by suzey on 10/24/2017.
 */

import com.amazonaws.services.rekognition.model.Image;


public interface FacialRecog {

    /**
     * Given two images, report whether they match with a SIMILARITY_THREASHOLD certainty.
     *
     * @param source
     * @param target
     * @return
     */
    public boolean compareFaces(Image source, Image target);


    /**
     * Create a new thread before asking AWS Rekognition to comparefaces. Do not use unless absolutely necessary.
     * @param imageA
     * @param imageB
     * @return
     */
    public boolean compareFacesThreaded(final Image imageA, final Image imageB);


    /**
     * NOT YET IMPLEMENTED
     * @param s3FileName_1
     * @param s3BucketName_1
     * @param s3FileName_2
     * @param s3BucketName_2
     * @return
     */
//    public boolean comapreFaces(String s3FileName_1, String s3BucketName_1, String s3FileName_2, String s3BucketName_2);



}
