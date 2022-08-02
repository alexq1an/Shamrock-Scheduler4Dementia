package com.example.shamrock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

//activity for notification page
//currently displays just text, will change in the future

public class DestinationActivity extends AppCompatActivity {
    private TextView title;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference taskRef;
    private Integer count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        title = findViewById(R.id.patient_output_title);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String patientID = extras.get("patientDocID").toString();
            String scheduleID = extras.get("scheduleDocID").toString();
            String taskID = extras.get("taskDocID").toString();
            taskRef = db.collection("Patient").document(patientID)
                    .collection("Schedule").document(scheduleID)
                    .collection("Task").document(taskID);
        }

        taskRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                }else{
                    Toast.makeText(DestinationActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

            title.setText(taskRef.get().getResult().toObject(com.example.shamrock.Task.class).getTitle());

    }


}