package com.access.accessonandroid.FacialRecog;

import com.amazonaws.services.rekognition.model.Image;

/**
 * @author Zeyang Su
 * All code in this file is original.
 *
 * Runnable used for network operations on the facial recognition component.
 */

public class FacialRecogRunnable implements Runnable {
    private boolean[] result;
    private FacialRecog facialRecogObject;

    private Image sourceImage;
    private Image targetImage;

    FacialRecogRunnable(FacialRecog currObject, Image imageOnFile, Image target, boolean[] resultQuickFix)
    {
        this.result = resultQuickFix;
        this.sourceImage = imageOnFile;
        this.targetImage = target;
        this.facialRecogObject = currObject;
    }

    public void run() {
        boolean [] resultTmpArray = this.result;
        resultTmpArray[0] = this.facialRecogObject.compareFaces(this.sourceImage, this.targetImage);
    }
}
