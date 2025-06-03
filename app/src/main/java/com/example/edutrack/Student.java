package com.example.edutrack;

public class Student {

    private String email;
    private String userType;


    public Student() {
    }

    public Student(String email, String userType) {
        this.email = email;
        this.userType = userType;

    }

    public String getEmail() {
        return email;
    }

    public String getUserType() {
        return userType;
    }



}
