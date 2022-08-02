package com.example.shamrock;
//importing all the required libraries
import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.Calendar;

//this task class will help to store the set alarm in the database
public class pTask {
    //initializing
    private Calendar calendar;
    String Description;
    String DocumentId;
    String Time;
    String Title;

    public pTask(String newDescription, String newTitle, String newTime){
        this.Description = newDescription;
        this.Time = newTime;
        this.Title = newTitle;
    }

    //might need it to show task list on patient homepage
    private ArrayList<pTask> pTaskList;

    //might need it to show task list on patient homepage
    private ArrayList<Task> taskList;

    //Constructors
    //without document id
    public pTask(){
        this.DocumentId = null;
    }

    public pTask(Calendar calendar, ArrayList<pTask> list){
        this.calendar = calendar;
        this.DocumentId = null;
        this.pTaskList = list;

    }
    public pTask(Calendar calendar, String Description, String DocumentId, ArrayList<pTask> list) {
        this.calendar = calendar;
        this.Description = Description;
        this.DocumentId = DocumentId;
        this.pTaskList = list;
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
    public ArrayList<pTask> getTaskList(){
        return pTaskList;
    }
    public void setTaskList(ArrayList<pTask> pTaskList){
        this.pTaskList = pTaskList;
    }
}