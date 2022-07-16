package com.example.shamrock;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;

public class Caregiver {
    private String documentId;
    private String name;
    private String username;
    private String email;
    private String password;
//    private String pList;

    public Caregiver() {
        //public no-arg constructor needed
    }

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Caregiver(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Caregiver(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getName(){
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
