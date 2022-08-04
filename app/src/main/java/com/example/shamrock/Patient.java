package com.example.shamrock;
//importing all the required libraries
import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
//this class helps to add patient information in the database
public class Patient {
    //initializing
    private String username;
    private String documentId;
    private String list_patient_id;


    public Patient(){
        //no-arg constructor

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

    public String getList_patient_id(){
        return list_patient_id;
    }


    public void setUsername(String username){
        this.username = username;
    }

}