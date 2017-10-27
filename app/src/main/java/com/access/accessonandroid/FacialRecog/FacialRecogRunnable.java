package com.access.accessonandroid.FacialRecog;

import com.access.accessonandroid.FacialRecog.FacialRegSu;
import com.amazonaws.services.rekognition.model.Image;

/**
 * Created by suzey on 10/26/2017.
 */

public class FacialRecogRunnable implements Runnable {
    private boolean[] result;
    FacialRecog facialRecogObject;

    private Image image_1;
    private Image image_2;
//    private android.content.Context appContext;

    FacialRecogRunnable(FacialRegSu currObject, Image img_1, Image img_2, boolean[] resultQuickFix)         //android.content.Context androidAppContext,
    {
        this.result = resultQuickFix;
        this.image_1 = img_1;
        this.image_2 = img_2;
        this.facialRecogObject = currObject;
    }

    public void run() {
        boolean [] resultTmpArray = this.result;
        resultTmpArray[0] = this.facialRecogObject.compareFaces(this.image_1, this.image_2);
    }
}
