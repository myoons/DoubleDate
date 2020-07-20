package com.example.madcamp_week_2.UI.Gallery;

import android.graphics.Bitmap;

public class ImageInfo {
    private Bitmap image;
    private String name;
    private String title;

    public ImageInfo(Bitmap image, String name, String title) {
        this.image = image;
        this.name = name;
        this.title = title;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
