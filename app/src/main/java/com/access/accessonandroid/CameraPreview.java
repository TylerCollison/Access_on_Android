package com.access.accessonandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
//import android.hardware.camera2;
import android.util.Log;
import android.view.TextureView;

import org.w3c.dom.Text;

public class CameraPreview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private boolean safeCameraOpen(int id) {
        boolean qOpened = false;

        try {
            releaseCameraAndPreview();
            //mCamera = camera2.open(id);
            //qOpened = (mCamera != null);
        } catch (Exception e) {
            Log.e(getString(R.string.app_name), "failed to open FrontFacingCameraScanner");
            e.printStackTrace();
        }

        return qOpened;
    }

    private void releaseCameraAndPreview() {
        //mPreview.setCamera(null);
        //if (mCamera != null) {
           // mCamera.release();
           // mCamera = null;
        //}
    }
}