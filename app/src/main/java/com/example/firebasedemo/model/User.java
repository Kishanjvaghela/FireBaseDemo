package com.example.firebasedemo.model;

/**
 * Created by DSK02 on 9/25/2015.
 */
public class User {
    public static final String FIELD_NAME = "name";
    public static final String FIELD_VOTE = "vote";

    public User() {
    }

    private String userId;
    private String name;
    private int age;
    private int vote;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public User(String name, int age, int vote) {
        this.name = name;
        this.age = age;
        this.vote = vote;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }
}
