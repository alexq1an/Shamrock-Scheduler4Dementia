package com.example.shamrock;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shamrock.databinding.ActivityMain3Binding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
    public ActivityMain3Binding binding;

    ListView pList;

    //    TextView month,day,year;
    TextView tvDay;
    //    TextView tvYear, tvMonth;
    @Override
    // present date-year-month
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_homepage);

        tvDay =findViewById(R.id.day);
        pList = (ListView) findViewById(R.id.patientTaskListView);

        Date currentTime = Calendar.getInstance().getTime();
        String formattedDate = DateFormat.getInstance ().format(currentTime);

        String[] splitDate = formattedDate.split(",");
        Log.d("my Log",currentTime.toString());
        Log.d("my Log",formattedDate);
//        tvMonth.setText(splitDate[1]);
        tvDay.setText(splitDate[0]);
//        tvYear.setText(splitDate[2]);

        Log.d("my Log",splitDate[0].trim());
//        Log.d("my Log",splitDate[1].trim());
//        Log.d("my Log",splitDate[2].trim());

        //get the current year, month, date to query a specific date's schedule
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        date = cal.get(Calendar.DATE);

        //grabbing documentId passed from MainActivity7
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            pDocId = extras.get("documentId").toString();
            //showTask();//call this method to show the task list
        }


//        ArrayList<pTask> allTasks = new ArrayList<>();
//        //access collection "Schedule
//        scheduleRef = pRef.document(pDocId).collection("Schedule");
//
//        //check if the schedule date matches with the current date
//        //get the present date documentId
//        scheduleRef
//                .whereEqualTo("year", year)//need generalization
//                .whereEqualTo("month", month)
//                .whereEqualTo("day", date)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {//successfully got access to a specific date's schedule reference
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                                todayId = document.getId();
//                                //access collection "Task"
//                                taskRef = scheduleRef.document(todayId).collection("Task");
////                                Toast.makeText(patient_homepage.this, "scheduleRef: " + scheduleRef.document(todayId), Toast.LENGTH_SHORT).show();
//                            }
//                            //iterate through Task collection and grab all the documents(tasks) inside
//                            taskRef.orderBy("time")
//                                    .get()
//                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                            if (task.isSuccessful()) {
//                                                //manually add each task into a task list
//                                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                                    Log.d(TAG, document.getId() + " => " + document.getData());
//                                                    //Toast is only for testing
//                                                    //Toast.makeText(patient_homepage.this, "Task title: " +document.get("title") + "\nTime: " + document.get("time"), Toast.LENGTH_SHORT).show();
//                                                    String title = document.get("title").toString();
//                                                    String time = document.get("time").toString();
//                                                    String description = document.get("description").toString();
//                                                    pTask newTask = new pTask(description, title, time);
//                                                    allTasks.add(newTask);
//                                                    ListAdapter2 listAdapter2 = new ListAdapter2(patient_homepage.this, allTasks);
//                                                    pList.setAdapter(listAdapter2);
//
////                                                        Task aTask = document.toObject(Task.class);//creates a Task object
//
////                                                        taskList.add(aTask);
//                                                    //use ListAdaptor2 to show it on the screen(patient_homepage.xml)
////                                ListAdapter2 listAdapter = new ListAdapter2(patient_homepage.this, taskList);
////                                binding.patientsListView.setAdapter(listAdapter);
//                                                }
//                                            } else {
//                                                Log.d(TAG, "Error getting documents: ", task.getException());
//                                            }
//                                        }
//                                    });}
//                        else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
        ListAdapter2 listAdapter2 = new ListAdapter2(patient_homepage.this, ((global)this.getApplication()).allTasks);
        pList.setAdapter(listAdapter2);

    }

    /**
     * Set the list of tasks and show
     * */
    public void showTask() {
        //access collection "Schedule
        scheduleRef = pRef.document(pDocId).collection("Schedule");

        //check if the schedule date matches with the current date
        //get the present date documentId
        scheduleRef
                .whereEqualTo("year", year)//need generalization
                .whereEqualTo("month", month)
                .whereEqualTo("day", date)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {//successfully got access to a specific date's schedule reference
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                todayId = document.getId();
                                //access collection "Task"
                                taskRef = scheduleRef.document(todayId).collection("Task");
//                                Toast.makeText(patient_homepage.this, "scheduleRef: " + scheduleRef.document(todayId), Toast.LENGTH_SHORT).show();
                            }
                            //iterate through Task collection and grab all the documents(tasks) inside
                            taskRef.orderBy("time")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                //manually add each task into a task list
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                                    //Toast is only for testing
                                                    Toast.makeText(patient_homepage.this, "Task title: " +document.get("title") +
                                                            "\nTime: " + document.get("time"), Toast.LENGTH_SHORT).show();


//                                                        Task aTask = document.toObject(Task.class);//creates a Task object

//                                                        taskList.add(aTask);
                                                    //use ListAdaptor2 to show it on the screen(patient_homepage.xml)
//                                ListAdapter2 listAdapter = new ListAdapter2(patient_homepage.this, taskList);
//                                binding.patientsListView.setAdapter(listAdapter);
                                                }
                                            } else {
                                                Log.d(TAG, "Error getting documents: ", task.getException());
                                            }
                                        }
                                    });}
                        else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
