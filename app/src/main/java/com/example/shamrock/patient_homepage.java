package com.example.shamrock;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shamrock.databinding.PatientHomepageBinding;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * This is the home page for patient.
 * It shows the list of tasks being scheduled on the current day.
 */
public class patient_homepage extends AppCompatActivity {
    // Create firebase references
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference pRef = db.collection("Patient");

    //define variables for showing the task list
    private static final String TAG = "LOG: patient_homepage";
    public String pDocId;
    public CollectionReference scheduleRef;
    public String todayId;
    public CollectionReference taskRef;

    public String docId;
    public int year;
    public int month;
    public int date;

    public ArrayList<Task> taskList = new ArrayList<>();
    public PatientHomepageBinding binding;

    ListView pList;

    //    TextView month,day,year;
    TextView tvDay;
    //    TextView tvYear, tvMonth;
    @Override
    // present date-year-month
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_homepage);

        //for displaying and getting information from the UI
        binding = PatientHomepageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tvDay =findViewById(R.id.day);
        pList = (ListView) findViewById(R.id.patientTaskListView);

        //displays the current date to the patient
        Date currentTime = Calendar.getInstance().getTime();
        String formattedDate = DateFormat.getInstance ().format(currentTime);

        //formatting the date to display
        String[] splitDate = formattedDate.split(",");
        Log.d("my Log",currentTime.toString());
        Log.d("my Log",formattedDate);
        tvDay.setText(splitDate[0]);
        Log.d("my Log",splitDate[0].trim());

        //get the current year, month, date to query a specific date's schedule
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        date = cal.get(Calendar.DATE);

        //grabbing documentId passed from MainActivity7
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            pDocId = extras.get("documentId").toString();
        }

        //the task list being assigned
        ListAdapter2 listAdapter2 = new ListAdapter2(patient_homepage.this, ((global)this.getApplication()).allTasks);
        pList.setAdapter(listAdapter2);

        //allows patient to choose a particular task
        binding.patientTaskListView.setAdapter(listAdapter2);
        binding.patientTaskListView.setClickable(true);
        binding.patientTaskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //passes information for the task
                Intent i = new Intent(getApplicationContext(), MainActivity10.class);
                i.putExtra("title",((global)patient_homepage.this.getApplication()).allTasks.get(position).getTitle());
                i.putExtra("description",((global)patient_homepage.this.getApplication()).allTasks.get(position).getDescription());
                i.putExtra("image",((global)patient_homepage.this.getApplication()).allTasks.get(position).getImage());
                startActivity(i);
            }
        });
    }


}