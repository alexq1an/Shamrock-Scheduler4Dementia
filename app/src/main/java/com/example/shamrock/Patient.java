package com.example.shamrock;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;

public class Patient {
    private String username;
    private String age;
    private String sex;
    private String documentId;
//    private String location; //not sure what type
    private ArrayList<Schedule> sList;

    public Patient(){
        //no-arg constructor

    }

    public Patient(String username, String age, String sex){
        this.username = username;
        this.age = age;
        this.sex = sex;
    }

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getUsername(){
        return username;
    }

    public String getAge(){
        return age;
    }

    public String getSex(){
        return sex;
    }
}
