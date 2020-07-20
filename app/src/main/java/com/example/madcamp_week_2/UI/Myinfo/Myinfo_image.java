package com.example.madcamp_week_2.UI.Myinfo;

import android.graphics.Bitmap;

public class Myinfo_image {
    private Bitmap image;
    private String date;
    private String title;
    private String tag;
    private String score;

    public Myinfo_image(Bitmap image, String date, String title, String tag, String score) {
        this.image = image;
        this.date = date;
        this.title = title;
        this.tag = tag;
        this.score = score;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
