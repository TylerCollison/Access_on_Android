package com.access.accessonandroid.Camera;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.media.midi.MidiManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tyler Collison on 11/12/2017.
 */

public class FrontFacingCameraScanner implements ICamera {

    private Context cameraContext;
    private TextureView previewTexture;
    private CameraManager cameraManager;
    private String cameraId;
    Size[] jpegSizes;
    private List<ImageCaptureCallback> listeners = new ArrayList<>();

    public FrontFacingCameraScanner(Context context) throws CameraAccessException {
        cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        cameraId = cameraManager.getCameraIdList()[0];
        CameraCharacteristics cc = cameraManager.getCameraCharacteristics(cameraId);
        StreamConfigurationMap streamConfigs = cc.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        //Size[] rawSizes = streamConfigs.getOutputSizes(ImageFormat.RAW_SENSOR);
        jpegSizes = streamConfigs.getOutputSizes(ImageFormat.JPEG);
        cameraContext = context;
    }

    @Override
    public boolean Initialize(Context context) throws CameraAccessException {

        return true;
    }

    @Override
    public void SetPreviewView(TextureView textureView) {
        previewTexture = textureView;
    }

    @Override
    public void AddOnCaptureListener(ImageCaptureCallback listener) {
        listeners.add(listener);
    }

    @Override
    public void StartPreview() throws CameraAccessException {
        startContinuousCaptureWithPreview();
    }

    @Override
    public void GetCurrentImage(ImageCaptureCallback callback) throws CameraAccessException {
        startStillCapture(callback);
    }

    private void startStillCapture(final ImageCaptureCallback callback) throws CameraAccessException {
        ImageReader imageReader = ImageReader.newInstance(jpegSizes[0].getWidth(),
                jpegSizes[0].getHeight(), ImageFormat.JPEG, 1);
        imageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
            @Override
            public void onImageAvailable(ImageReader reader) {
                Image image = reader.acquireLatestImage();
                callback.execute(image);
                image.close();
                reader.close();
            }
        }, null);
        List<Surface> surfaces = new ArrayList<>(Arrays.asList(imageReader.getSurface()));
        openCamera(cameraId, surfaces, CameraDevice.TEMPLATE_STILL_CAPTURE);
    }

    private void startContinuousCaptureWithPreview() {
        previewTexture.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                try {
                    List<Surface> surfaces = new ArrayList<>(Arrays.asList(new Surface(surface)));
                    openCamera(cameraId, surfaces, CameraDevice.TEMPLATE_PREVIEW);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {}

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {return false;}

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {}
        });
    }

    private boolean requestCameraPermission(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)context,
                    new String[] {Manifest.permission.CAMERA}, 0);
        }

        return ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    @SuppressLint("MissingPermission")
    private void openCamera(
            String cameraId, final List<Surface> surfaces, final int template) throws CameraAccessException {
        if (requestCameraPermission(cameraContext)) {
            cameraManager.openCamera(cameraId, new CameraDevice.StateCallback() {
                @Override
                public void onOpened(@NonNull CameraDevice camera) {
                    try {
                        createCaptureSession(camera, surfaces, template);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onDisconnected(@NonNull CameraDevice camera) {}

                @Override
                public void onError(@NonNull CameraDevice camera, int error) {}
            }, null);
        }
    }

    private void createCaptureSession(final CameraDevice cameraDevice,
                                                          final List<Surface> surfaces,
                                                          final int template) throws CameraAccessException {
        cameraDevice.createCaptureSession(surfaces, new CameraCaptureSession.StateCallback() {
            @Override
            public void onConfigured(@NonNull CameraCaptureSession session) {
                try {
                    CaptureRequest.Builder request = cameraDevice.createCaptureRequest(template);
                    for (int i = 0; i < surfaces.size(); i++) {
                        request.addTarget(surfaces.get(i));
                    }
                    switch (template) {
                        case CameraDevice.TEMPLATE_PREVIEW:
                            session.setRepeatingRequest(request.build(), null, null);
                            break;
                        case CameraDevice.TEMPLATE_STILL_CAPTURE:
                            session.capture(request.build(), null, null);
                            break;
                    }
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onConfigureFailed(@NonNull CameraCaptureSession session) {}
        }, null);
    }
}
