package com.example.livetogo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class CameraActivity extends AppCompatActivity {
    private String ip = "192.168.1.4";
    private int port = 5000;

    private static final int requestCode = 100;
    private CameraManager cameraManager;
    private CameraPreview cameraPreview;
    private boolean isOn = true;
    GlobalVariables globals;

    private Socket socket;

    @BindView(R.id.captureButton)Button capture;
    @BindView(R.id.switchCameraButton)Button switchCamera;
    @BindView(R.id.camera_preview)FrameLayout cameraPreviewLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);

        new Thread(new ClientThread()).start();

        globals = GlobalVariables.getInstance();
        if(globals.getCameraSet() == false) {
            setGlobalParams(ip, port);
        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, requestCode);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, requestCode);

        cameraManager = new CameraManager(this);
        cameraPreview = new CameraPreview(this, cameraManager.getCamera());
        FrameLayout preview = cameraPreviewLayout;
        preview.addView(cameraPreview);

        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOn) {
                    isOn = false;
                    capture.setText("Stop");
                    switchCamera.setVisibility(View.GONE);

                    PrintWriter out = null;
                    try {
                        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                        out.println("hello");
                        out.flush();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    resetVideo();
                }
            }
        });

        switchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(globals.getWhichCamera() == 0) {
                    globals.setWhichCamera(1);
                }
                else {
                    globals.setWhichCamera(0);
                }

                Intent intent = new Intent(CameraActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraPreview.onPause();
        cameraManager.onPause();
        resetVideo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraManager.onResume();
        cameraPreview.setCamera(cameraManager.getCamera());
    }

    private void resetVideo() {
        capture.setText("Start");
        isOn = true;
        switchCamera.setVisibility(View.VISIBLE);
    }

    private void setGlobalParams(String ip, int port) {
        globals.setWhichCamera(0);
        globals.setCameraSet();
        globals.setIp(ip);
        globals.setPort(port);
    }

    class ClientThread implements Runnable {
        @Override
        public void run() {
            try {
                InetAddress srvAddr = InetAddress.getByName(ip);
                socket = new Socket(srvAddr, port);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
