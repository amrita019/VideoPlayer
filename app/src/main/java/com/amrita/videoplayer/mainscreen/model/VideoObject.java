package com.amrita.videoplayer.mainscreen.model;

public class VideoObject {

    private String filePath;
    private String thumbnail;

    public VideoObject(String filePath, String thumbnail) {
        this.filePath = filePath;
        this.thumbnail = thumbnail;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
