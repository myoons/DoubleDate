package com.example.madcamp_week_2.UI.Myinfo;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Myinfo {

    String ID;
    String Nickname;
    String Number;
    String bone;
    String score;
    String animals;
    String gender;
    ArrayList<Myinfo_image> images;

    public Myinfo(String ID, String nickname, String number, String bone,
                  String score, String animals, String gender, ArrayList<Myinfo_image> images) {
        this.ID = ID;
        Nickname = nickname;
        Number = number;
        this.bone = bone;
        this.score = score;
        this.animals = animals;
        this.gender = gender;
        this.images = images;
    }

    public ArrayList<Myinfo_image> getImages() {
        return images;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getBone() {
        return bone;
    }

    public void setBone(String bone) {
        this.bone = bone;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setImages(ArrayList<Myinfo_image> images) { this.images = images; }

    public String getAnimals() {
        return animals;
    }

    public void setAnimals(String animals) {
        this.animals = animals;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
