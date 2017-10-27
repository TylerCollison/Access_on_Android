package com.access.accessonandroid.FacialRecog;

import com.access.accessonandroid.FacialRecog.FacialRegSu;
import com.amazonaws.services.rekognition.model.Image;

/**
 * Created by suzey on 10/26/2017.
 */

public class FacialRecogRunnable implements Runnable {
    public boolean result;
    private Image image_1;
    private Image image_2;
    private android.content.Context appContext;

    FacialRecogRunnable(android.content.Context androidAppContext, Image img_1, Image img_2) {
        this.result = false;
        this.image_1 = img_1;
        this.image_2 = img_2;
        this.appContext = androidAppContext;
    }

    public void run() {
        FacialRecog tmp = new FacialRegSu(this.appContext);
        this.result = tmp.compareFaces(this.image_1, this.image_2);
    }
}
