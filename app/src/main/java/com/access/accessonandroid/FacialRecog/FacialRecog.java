package com.access.accessonandroid.FacialRecog;

/**
 * Created by suzey on 10/24/2017.
 */

import com.amazonaws.services.rekognition.model.Image;


public interface FacialRecog {
    /**
     * All image files passed to any functions in this interface should be of the format PNG or JPEG
     */



    /**
     * Most recommended method for comparing faces.
     *
     * Will create a new thread in order to compare faces. Will only allow one facial comparison request to take place at any given time.
     * If a facial comparison request is already being carried out when this function is invoked, it will return false.
     *
     * If the client wants to run more than one facial comparison request in parallel, the client can use the method
     * compareFaces(Image imageOnFile, Image target).
     *
     * @param imageOnFile
     * @param target
     * @param callbackFunc  The function to invoke when the comparison is finished
     * @return Whether a facial comparison procedure is initiated by a call to this function
     */
    public boolean compareFacesWithCallbackFunc(final Image imageOnFile, final Image target, final FacialRecogCallbackFuncObj callbackFunc);


    /**
     * Given two images, report whether they match with a SIMILARITY_THREASHOLD certainty.
     * Contains network operation so cannot be called by the main thread.
     *
     * @param imageOnFile
     * @param target
     * @return Whether the face in imageOnFile matches the face in target
     */
//    public boolean compareFaces(Image imageOnFile, Image target);


    /**
     * Create a new thread before asking AWS Rekognition to comparefaces. Do not use unless absolutely necessary.
     * @param imageOnFile
     * @param target
     * @return Whether the face in imageOnFile matches the face in target
     */
    public boolean compareFacesThreadedBlocking(final Image imageOnFile, final Image target);




    /**
     * Report how many faces are in a given picture
     * @param pic
     * @return  number of faces in a given picture
     */
    public int detectFaces(Image pic);





}
