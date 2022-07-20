package com.example.shamrock;

import android.widget.Toast;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;

public class Caregiver {
    private String documentId;
    private String name;
    private String username;
    private String email;
    private String password;
    private ArrayList<String> pList;

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

    public Caregiver(ArrayList<String> list, String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.pList = list;
    }

    public Caregiver(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
        this.pList = null;
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

    public ArrayList<String> getpList() {
        return pList;
    }

    public void setpList(ArrayList<String> pList) {
        this.pList = pList;
    }


}
