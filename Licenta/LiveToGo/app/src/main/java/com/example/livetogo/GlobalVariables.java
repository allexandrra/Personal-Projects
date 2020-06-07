package com.example.livetogo;

public class GlobalVariables {
    private static GlobalVariables instance;

    private int whichCamera;
    private boolean isCameraSet;
    private String ip;
    private int port;

    private GlobalVariables() {
        this.isCameraSet = false;
    }

    private GlobalVariables(int var) {
        this.whichCamera = var;
    }

    public void setWhichCamera(int whichCamera) {
        this.whichCamera = whichCamera;
    }

    public int getWhichCamera() {
        return this.whichCamera;
    }

    public void setCameraSet() { this.isCameraSet = true; }

    public boolean getCameraSet() { return this.isCameraSet; }

    public void setIp(String ip) { this.ip = ip; }

    public String getIp() { return this.ip; }

    public void setPort(int port) { this.port = port; }

    public int getPort() { return this.port; }

    public static synchronized GlobalVariables getInstance() {
        if(instance == null) {
            instance = new GlobalVariables();
        }

        return instance;
    }
}
