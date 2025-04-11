package com.example.skillswap.models;

import com.example.skillswap.R;

import java.util.List;

public class User {
    private String id;
    private String name;
    private List<String> teach;
    private List<String> learn;

    private int imageId;

    public User(String id, String name, List<String> teach, List<String> learn) {
        this.id = id;
        this.name = name;
        this.teach = teach;
        this.learn = learn;
        this.imageId = R.drawable.placeholderprofile;

    }

    public User(String id, String name, List<String> teach, List<String> learn,int imageId) {
        this.id = id;
        this.name = name;
        this.teach = teach;
        this.learn = learn;
        this.imageId=imageId;
    }

    //getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public List<String> getTeach() {
        return teach;
    }

    public List<String> getLearn() {
        return learn;
    }

    //methods to add to teach list and learn list

    public void addTeach(String teach){
        this.teach.add(teach);
    }

    public void addLearn(String learn){
        this.learn.add(learn);
    }

    public void removeTeach(String teach){
        this.teach.remove(teach);
    }

    public void removeLearn(String learn){
        this.learn.remove(learn);
    }

    @Override
    public String toString(){
        return id+" - "+name;
    }
}
