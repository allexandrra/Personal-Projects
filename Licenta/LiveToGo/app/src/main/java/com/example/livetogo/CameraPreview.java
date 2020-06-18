package com.example.livetogo;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.io.IOException;
import java.util.LinkedList;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private static SurfaceHolder mHolder;
    private static Camera mCamera;
    private Context mContext;
    private Size mPreviewSize;

    private int mFrameLength;
    private byte[] mImageData;
    private LinkedList<byte[]> mQueue = new LinkedList<byte[]>();
    private static final int MAX_BUFF = 15;
    private byte[] mLastFrame = null;

    private Camera.PreviewCallback mPreviewCallback = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            synchronized (mQueue) {
                if(mQueue.size() == MAX_BUFF)
                    mQueue.poll();
                mQueue.add(data);
            }
        }
    };

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        mContext = context;
        mHolder = getHolder();
        mHolder.addCallback(this);

        Camera.Parameters param = mCamera.getParameters();
        param.setPreviewSize(640, 480);
        mCamera.setParameters(param);

        mPreviewSize = mCamera.getParameters().getPreviewSize();

        int format = mCamera.getParameters().getPreviewFormat();
        mFrameLength = mPreviewSize.width * mPreviewSize.height * ImageFormat.getBitsPerPixel(format) / 8;
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            setCameraDisplayOrientation();
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        if(mHolder.getSurface() == null)
            return;

        try {
            mCamera.stopPreview();
            resetBuff();
        } catch (Exception e){

        }
        try {
            mCamera.setPreviewCallback(mPreviewCallback);
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (Exception e) {

        }
    }

    public void setCamera(Camera camera) {
        mCamera = camera;
    }

    public byte[] getImageBuff() {
        synchronized (mQueue) {
            if(mQueue.size() > 0)
                mLastFrame = mQueue.poll();
        }

        return mLastFrame;
    }

    private void resetBuff() {
        synchronized (mQueue) {
            mQueue.clear();
            mLastFrame = null;
        }
    }

    public int getPreviewLength() {
        return mFrameLength;
    }

    public int getPreviewWidth() {
        return mPreviewSize.width;
    }

    public int getPreviewHeight() {
        return mPreviewSize.height;
    }

    public void onPause() {
        if(mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
        }
        resetBuff();
    }

    public void setCameraDisplayOrientation() {
        if(mCamera == null)
            return;

        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(0, info);

        WindowManager winManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int rotation = winManager.getDefaultDisplay().getRotation();
        int degrees = 0;
        int result;

        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        if(info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) %360;
            result = (360 - result) % 360;
        } else {
            result = (info.orientation - degrees + 360) %360;
        }

        mCamera.setDisplayOrientation(result);
    }
}
