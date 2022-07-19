package com.example.shamrock;
import com.google.firebase.firestore.Exclude;
import java.util.Calendar;

public class Task {
    private Calendar calendar;
    private String Description;
    private String DocumentId;

    //Constructors
    public Task(){
        this.DocumentId = null;
    }
    public Task(Calendar calendar){
        this.calendar = calendar;
        this.DocumentId = null;

    }
    public Task(Calendar calendar, String Description, String DocumentId) {
        this.calendar = calendar;
        this.Description = Description;
        this.DocumentId = DocumentId;
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
}
