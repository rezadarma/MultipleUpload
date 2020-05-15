package com.reza.multipleupload.getterSetter;

import java.io.File;

public class ListImage {
    String picturePath;
    File imagFile;


    public File getImagFile() {
        return imagFile;
    }

    public void setImagFile(File imagFile) {
        this.imagFile = imagFile;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }
}
