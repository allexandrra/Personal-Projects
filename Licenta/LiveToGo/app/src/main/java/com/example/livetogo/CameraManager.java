package com.example.livetogo;

import android.content.Context;
import android.hardware.Camera;

public class CameraManager {
    private Camera mCamera;
    private Context mContext;

    public CameraManager(Context context) {
        mContext = context;
        mCamera = getCameraInstance();
    }

    public Camera getCamera() {
        return mCamera;
    }

    public void releaseCamera() {
        if(mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    public void onPause() {
        releaseCamera();
    }

    public void onResume() {
        if(mCamera == null)
            mCamera = getCameraInstance();
    }

    private static Camera getCameraInstance() {
        Camera cam = null;

        try {
            GlobalVariables globals = GlobalVariables.getInstance();
            cam = Camera.open(globals.getWhichCamera());
        } catch (Exception e) {

        }

        return cam;
    }
}
