package com.example.shamrock;

public class Patient {
    private String username;
    private int age;
    private char sex;
    //might need it
//    private email;

//    private String location; //not sure what type

//    private ArrayList<String> scheduleList; //not sure what type -> reference

    public Patient(){
        //no-arg constructor

    }

    public Patient(String username, int age, char sex){
        this.username = username;
        this.age = age;
        this.sex = sex;
    }

    public String getUsername(){
        return username;
    }

    public int getAge(){
        return age;
    }

    public char getSex(){
        return sex;
    }
}
