package com.example.shamrock;

// This class is for store the information for each of our patient, so that we can list them
// in our patient list at caregiver's home page.
public class User {

    String name, list_patient_id, phoneNo, country;
    int imageId;

    public User(String name, String list_patient_id, String phoneNo, String country, int imageId) {
        this.name = name;
        this.list_patient_id = list_patient_id;
        this.phoneNo = phoneNo;
        this.country = country;
        this.imageId = imageId;
    }
}