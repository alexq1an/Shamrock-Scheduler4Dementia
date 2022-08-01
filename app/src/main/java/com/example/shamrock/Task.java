package com.example.shamrock;
//importing all the required libraries
import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.Calendar;

//this task class will help to store the set alarm in the database
public class Task {
    //initializing
    private Calendar calendar;
    private String Description;
    private String DocumentId;

    //might need it to show task list on patient homepage
    private ArrayList<Task> taskList;

    //Constructors
    //without document id
    public Task(){
        this.DocumentId = null;
    }
    public Task(Calendar calendar, ArrayList<Task> list){
        this.calendar = calendar;
        this.DocumentId = null;
        this.taskList = list;

    }
    public Task(Calendar calendar, String Description, String DocumentId, ArrayList<Task> list) {
        this.calendar = calendar;
        this.Description = Description;
        this.DocumentId = DocumentId;
        this.taskList = list;
    }

    //getters and setters
    @Exclude
    public String getDocumentId() {return DocumentId;}

    public void setDocumentId(String DocumentId) {
        this.DocumentId = DocumentId;
    }
    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public Calendar getTime(){
        return calendar;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description){
        this.Description = Description;
    }

//might need these to show task list on patient homepage
    public ArrayList<Task> getTaskList(){
        return taskList;
    }
    public void setTaskList(ArrayList<Task> taskList){
        this.taskList = taskList;
    }
}