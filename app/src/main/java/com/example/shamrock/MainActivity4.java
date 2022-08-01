package com.example.shamrock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

/**
 * This page is for editing patient information and setting tasks
 * */

public class MainActivity4 extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

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
    public Calendar calendar;
    public String scheduleID;

    public Patient temp_patient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        tvDate = findViewById(R.id.tv_date);
        etDate = findViewById(R.id.et_date);
        patientName = findViewById(R.id.patient_name);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            patientName.setText(extras.get("username").toString());
            patientDocId = extras.get("patientDocId").toString();
        }

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

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
        intent.putExtra("calendar", calendar);
        intent.putExtra("scheduleDocId", scheduleID);
        intent.putExtra("patientDocId", patientDocId);
        startActivity(intent);
    }

    public void openActivity8(String patientDocId){
        Intent intent = new Intent(this,MainActivity8.class);
        //passing documentId to MainActivity8
        intent.putExtra("patientDocId", patientDocId);
        startActivity(intent);
    }

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        etDate.setText(currentDateString);
        count++;
        calendar = c;

        //query database for existing date
        pRef.document(patientDocId).collection("Schedule")
                .whereEqualTo("day", c.get(Calendar.DAY_OF_MONTH))
                .whereEqualTo("month", c.get(Calendar.MONTH))
                .whereEqualTo("year", c.get(Calendar.YEAR))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            int counter = 0;
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                counter++;
                                Schedule schedule = documentSnapshot.toObject(Schedule.class);
                                String id = documentSnapshot.getId();
                                scheduleID = id;
                                schedule.setDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

                                pRef.document(patientDocId).collection("Schedule").document(id).set(schedule);
                            }
                            if(counter == 0){
                                DocumentReference addedDocRef = pRef.document(patientDocId).collection("Schedule").document();
                                Schedule schedule = new Schedule(addedDocRef.getId());
                                scheduleID = addedDocRef.getId();
                                schedule.setDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                                addedDocRef.set(schedule);
                            }
                        }

                    }
                });
        //add calendar to intent

    }
}
