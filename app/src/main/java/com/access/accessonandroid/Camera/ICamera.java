package com.access.accessonandroid.Camera;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.view.TextureView;

/**
 * Created by Tyler Collison on 11/12/2017.
 */

public interface ICamera {

    boolean Initialize(Context context) throws CameraAccessException;

    void SetPreviewView(TextureView textureView);

    void AddOnCaptureListener(ImageCaptureCallback listener);

    void StartPreview() throws CameraAccessException;

    void GetCurrentImage(ImageCaptureCallback callback) throws CameraAccessException;

}
