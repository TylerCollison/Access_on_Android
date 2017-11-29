package com.access.accessonandroid.FacialRecog;

import com.amazonaws.services.rekognition.model.Image;

/**
 * You shouldn't have to understand what this class does
 * Created by Zeyang Su on 10/26/2017.
 */

public class FacialRecogRunnable implements Runnable {
    private boolean[] result;
    private FacialRecog facialRecogObject;

    private Image sourceImage;
    private Image targetImage;
//    private android.content.Context appContext;

    FacialRecogRunnable(FacialRecog currObject, Image imageOnFile, Image target, boolean[] resultQuickFix)         //android.content.Context androidAppContext,
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
