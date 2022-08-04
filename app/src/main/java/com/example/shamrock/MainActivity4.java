package com.example.shamrock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

/**
 * This page is for editing patient information and setting tasks
 * */

public class MainActivity4 extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    //user interface ids
    private Button newevent;
    private Button changePatientInfo_button;
    TextView tvDate;
    TextView etDate;
    TextView patientName;

    //counter for error checking
    private Integer count = 0;

    //database references
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference sRef = db.collection("Schedule");
    private CollectionReference pRef = db.collection("Patient");

    //transferred patient information
    public String username;
    public String patientDocId;
    public Calendar calendar;
    public String scheduleID;
    public String caregiverID;

    //listAdapter task list
    ListView allTaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        //transferring information from user interface
        tvDate = findViewById(R.id.tv_date);
        etDate = findViewById(R.id.et_date);
        patientName = findViewById(R.id.patient_name);
        allTaskList = (ListView) findViewById(R.id.hourListView);

        //grabbing information from previous page
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            //setting Patient Name to the title
            patientName.setText(extras.get("username").toString());
            //grabbing Ids to access database
            patientDocId = extras.get("patientDocId").toString();
            caregiverID = extras.get("caregiverDocId").toString();
        }

        //for selecting a date
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        //for adding a new task
        newevent = (Button) findViewById(R.id.newevent);
        newevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity5();
            }
        });

        //for editing patient information
        changePatientInfo_button = (Button) findViewById(R.id.changePatientInfo_button);
        changePatientInfo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity8();
            }
        });

        //sets the list of tasks displayed at the bottom half of mainActivity4
        ListAdapter2 listAdapter2 = new ListAdapter2(MainActivity4.this, ((global)this.getApplication()).allTasks);
        allTaskList.setAdapter(listAdapter2);

    }


    public void openActivity5(){
        //checking if the information provided below can be passed
        if (count < 1){
            Toast.makeText(this, "Must Select Date first", Toast.LENGTH_SHORT).show();
            return;
        }

        //passing information
        Intent intent = new Intent(this,MainActivity5.class);
        intent.putExtra("calendar", calendar);
        intent.putExtra("scheduleDocId", scheduleID);
        intent.putExtra("patientDocId", patientDocId);
        intent.putExtra("caregiverDocId", caregiverID);
        startActivity(intent);
    }

    public void openActivity8(){
        Intent i = new Intent(this,MainActivity8.class);
        //passing documentId to MainActivity8
        i.putExtra("patientDocId", patientDocId);
        i.putExtra("caregiverDocId", caregiverID);
        startActivity(i);
    }

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
        //sets the calendar to select the current date
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        etDate.setText(currentDateString);
        //set the counter to imply that a date has been chosen
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
                                //if a document for the date already exists then grab and upate
                                counter++;
                                Schedule schedule = documentSnapshot.toObject(Schedule.class);
                                String id = documentSnapshot.getId();
                                scheduleID = id;
                                schedule.setDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

                                pRef.document(patientDocId).collection("Schedule").document(id).set(schedule);
                            }
                            //if a document doesn't exist, create a new one and add it to datebase
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

    }
}
