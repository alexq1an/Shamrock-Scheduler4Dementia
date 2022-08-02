package com.example.shamrock;
//importing all the required libraries
import com.google.firebase.firestore.Exclude;


import java.util.Calendar;
import java.util.Date;

//this task class will help to store the set alarm in the database
public class Task {
    //initializing
    private Date time;
    private String image;
    private String Description;
    private String Title;
    private String DocumentId;

    //Constructors
    //without document id
    public Task(){
        this.DocumentId = null;
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
}
