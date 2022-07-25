package com.example.shamrock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

/**
 * This page is for editing patient information and setting tasks
 * */
/*
   *Page is still in progress
 */
public class MainActivity4 extends AppCompatActivity {
    private Button newevent;
    private Button changePatientInfo_button;
    private Button newDate;
    DatePickerDialog.OnDateSetListener setListener;
    TextView tvDate;
    TextView etDate;
    TextView patientName;
    private Integer count = 0;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference sRef = db.collection("Schedule");
    private CollectionReference pRef = db.collection("Patient");

    //transferred patient information
//    private DocumentReference pDocId;
    public String username;
    public String loginId;
    public String patientDocId;
    public String date;


    public Patient temp_patient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        tvDate = findViewById(R.id.tv_date);
        etDate = findViewById(R.id.et_date);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        //grabbing the transferred patient information from MainActivity3
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
             username = extras.getString("username");
             loginId = extras.getString("loginId");
             patientDocId = extras.getString("patientDocId");

            Toast.makeText(MainActivity4.this, "DocumentId: " + patientDocId, Toast.LENGTH_SHORT).show();


//            DocumentReference patientDocId = pRef.document(extras.get("documentId").toString());
        }


        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate();
            }
        });
//

        newevent = (Button) findViewById(R.id.newevent);
        newevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity5();
            }
        });

        changePatientInfo_button = (Button) findViewById(R.id.changePatientInfo_button);
        changePatientInfo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity8(patientDocId);
            }
        });

    }
    public void openActivity5(){
        if (count < 1){
            Toast.makeText(this, "Must Select Date first", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this,MainActivity5.class);
        startActivity(intent);
    }

    public void openActivity8(String patientDocId){
            Intent intent = new Intent(this,MainActivity8.class);
            //passing documentId to MainActivity8
            intent.putExtra("patientDocId", patientDocId);
            startActivity(intent);
    }

    public void selectDate(){

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        //etDate
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity4.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month +1;
                date = day + "/" + month + "/" + year;
                etDate.setText(date);
                count++;
                //pre set time for date
                calendar.set(year, month, day, 0, 0, 0);
                calendar.setTimeInMillis(0);
            }
        },year,month,day);
        datePickerDialog.show();

        //passing date to MA5
        Intent i = new Intent(MainActivity4.this, MainActivity5.class);
        i.putExtra("date",date);



        //query database for existing date
        sRef.whereEqualTo("calendar", calendar);
        //if exists grab schedule task for that day and add
        //if doesn't exist create new schedule
        //add calendar to intent

        DocumentReference addedDocRef = sRef.document();
        Schedule schedule = new Schedule(addedDocRef.getId());
        schedule.setCalendar(calendar);
        addedDocRef.set(schedule);
    }

}
