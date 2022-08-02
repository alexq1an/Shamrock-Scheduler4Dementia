package com.example.shamrock;
import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.Calendar;

public class Schedule {
    private Calendar calendar;
    private ArrayList<pTask> pTaskArrayList;
    private String documentID;
    private Integer Day;
    private Integer Month;
    private Integer Year;

    //constructors
    public Schedule(Calendar calendar, ArrayList<pTask> pTaskArrayList) {
        this.calendar = calendar;
        this.pTaskArrayList = pTaskArrayList;
    }
    public Schedule(Calendar calendar, String documentID) {
        this.calendar = calendar;
        this.documentID = documentID;
    }
    public Schedule(String documentID) {
        this.documentID = documentID;
    }

    public Schedule() {

    }

    //getters and setters
    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    @Exclude
    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }


    public void setTaskArrayList(ArrayList<pTask> pTaskArrayList) {
        this.pTaskArrayList = pTaskArrayList;
    }

    public ArrayList<pTask> getTaskArrayList() {
        return pTaskArrayList;
    }

    public Integer getDay() {
        return Day;
    }

    public void setDay(Integer day) {
        Day = day;
    }

    public Integer getMonth() {
        return Month;
    }

    public void setMonth(Integer month) {
        Month = month;
    }

    public Integer getYear() {
        return Year;
    }

    public void setYear(Integer year) {
        Year = year;
    }

    public void setDate(Integer year, Integer month, Integer day){
        Year = year;
        Month = month;
        Day = day;
    }

}
