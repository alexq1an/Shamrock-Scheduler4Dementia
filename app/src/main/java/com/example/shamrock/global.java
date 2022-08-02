package com.example.shamrock;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class global extends Application {

    private static final String TAG = "LOG: patient_homepage";

    CollectionReference taskRef;

    ////////
    ArrayList<pTask> allTasks;


    //    (String patientID, int year, int month, int date)
    public void refreshTaskForTargetPatientForAll(String patientID){
        this.allTasks = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference pRef = db.collection("Patient");
        //access collection "Schedule
        CollectionReference scheduleRef = pRef.document(patientID).collection("Schedule");

        //check if the schedule date matches with the current date
        //get the present date documentId
        scheduleRef
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {//successfully got access to a specific date's schedule reference
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                String todayId = document.getId();
                                //access collection "Task"
                                taskRef = scheduleRef.document(todayId).collection("Task");
//                                Toast.makeText(patient_homepage.this, "scheduleRef: " + scheduleRef.document(todayId), Toast.LENGTH_SHORT).show();
                                //iterate through Task collection and grab all the documents(tasks) inside
                                taskRef.orderBy("time")
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    //manually add each task into a task list
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        com.example.shamrock.Task task1 = document.toObject(com.example.shamrock.Task.class);
                                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                                        //Toast is only for testing
                                                        Toast.makeText(getApplicationContext(),
                                                                "Task title: " + task1.getTitle()+
                                                                        "\nTime: " + task1.getTime(), Toast.LENGTH_SHORT).show();
                                                        pTask newTask = new pTask();
                                                        newTask.setDescription(task1.getDescription());
                                                        newTask.setTime(task1.getTime());
                                                        newTask.setTitle(task1.getTitle());
                                                        allTasks.add(newTask);
                                                    }
                                                } else {
                                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                                }
                                            }
                                        });
                            }
                        }
                        else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }




}



