package com.example.shamrock;
//importing all the required libraries
import com.google.firebase.firestore.Exclude;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

//this task class will help to store the set alarm in the database
public class pTask {
    //initializing
    private Calendar calendar;
//    String Description;
//    String DocumentId;
    String Time;
//    String Title;

    public pTask(String newDescription, String newTitle, String newTime){
        this.Description = newDescription;
        this.Time = newTime;
        this.Title = newTitle;
    }

    //might need it to show task list on patient homepage
    private ArrayList<pTask> pTaskList;

    private Date time;
    private String image;
    private String Description;
    private String Title;
    private String DocumentId;
    private String BindScheduleId;

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

        this.Description = null;
        this.Title = null;
        this.image = null;
    }

    //getters and setters
    @Exclude
    public String getDocumentId() {return DocumentId;}

    public String getDescription() {
        return Description;
    }

    public Date getTime() {
        return time;
    }

    public String getTitle() {
        return Title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDocumentId(String DocumentId) {
        this.DocumentId = DocumentId;
    }
    public void setDescription(String Description){
        this.Description = Description;
    }

    public String getBS(){return BindScheduleId;}
    public void setBindScheduleId(String BS){this.BindScheduleId = BS;}
}
