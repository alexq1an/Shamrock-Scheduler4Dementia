package com.example.shamrock;
//importing all the required libraries
import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
//this class helps to add patient information in the database
public class Patient {
    //initializing
    private String username;
    private String age;
    private String sex;
    private String documentId;
    //array list for storing patients
    private ArrayList<Schedule> sList;

    public Patient(){
        //no-arg constructor

    }
    //constructor
    public Patient(String username, String age, String sex){
        this.username = username;
        this.age = age;
        this.sex = sex;
    }

    @Exclude
    //getters and setters
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