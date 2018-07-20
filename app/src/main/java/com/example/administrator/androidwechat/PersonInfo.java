package com.example.administrator.androidwechat;

public class PersonInfo {
    private String personName;
    private int img;
    public PersonInfo(String personName, int img){
        this.personName = personName;
        this.img = img;
    }
    public String getPersonName() {

        return personName;
    }

    public int getImg() {
        return img;
    }


}
