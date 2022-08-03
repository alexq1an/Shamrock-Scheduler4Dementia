package com.example.shamrock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shamrock.databinding.ActivityMain10Binding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class MainActivity10 extends AppCompatActivity {
    ActivityMain10Binding binding;
    StorageReference storageReference;
    ProgressDialog progressDialog;
    String patientID;
    String scheduleID;
    String taskID;
    String texttitle;
    String description;
    String imageName = null;
    TextView title;
    TextView des;
    Task currentTask;
    private DocumentReference docTask;
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain10Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        title = findViewById(R.id.patient_output_title);
        confirm = findViewById(R.id.confirm_button);
        des = findViewById(R.id.task_description);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            patientID = extras.get("patientDocId").toString();
            scheduleID = extras.get("scheduleDocId").toString();
            taskID = extras.get("taskDocId").toString();
//            docTask = FirebaseFirestore.getInstance()
//                    .collection("Patient").document(patientID)
//                    .collection("Schedule").document(scheduleID)
//                    .collection("Task").document(taskID);
//            texttitle = FirebaseFirestore.getInstance()
//                    .collection("Patient").document(patientID)
//                    .collection("Schedule").document(scheduleID)
//                    .collection("Task").document(taskID).get().getResult().toObject(Task.class).getTitle();
//            description = task.getDescription();

        }

        FirebaseFirestore.getInstance()
                .collection("Patient").document(patientID)
                .collection("Schedule").document(scheduleID)
                .collection("Task").document(taskID)
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            currentTask = task.getResult().toObject(Task.class);
                            texttitle = currentTask.getTitle();
                            description = currentTask.getDescription();
                            imageName = currentTask.getImage();
                        }
                    }
                });
//            Task task = docTask.get().getResult().toObject(Task.class);
            title.setText(description); //task.getTitle()
//            des.setText();
//        }


        binding.getImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                progressDialog = new ProgressDialog(MainActivity10.this);
//                progressDialog.setMessage("Fetching the desired image.....");
//                progressDialog.setCancelable(false);
//                progressDialog.show();

                Task task = FirebaseFirestore.getInstance()
                                .collection("Patient").document(patientID)
                                .collection("Schedule").document(scheduleID)
                                .collection("Task").document(taskID).get().getResult().toObject(Task.class);
                String imageID = task.getImage();
                Toast.makeText(MainActivity10.this, "image: " + imageID, Toast.LENGTH_SHORT).show();

                storageReference = FirebaseStorage.getInstance().getReference(imageName);
                try {
                    File localfile = File.createTempFile("tempfile",".jpg");
                    storageReference.getFile(localfile)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    if(progressDialog.isShowing())
                                        progressDialog.dismiss();

                                    Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                                    binding.imageView.setImageBitmap(bitmap);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if(progressDialog.isShowing())
                                        progressDialog.dismiss();
                                    Toast.makeText(MainActivity10.this,"Failed to retrieve",Toast.LENGTH_SHORT).show();
                                }
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity10.this, MainActivity7.class);
                startActivity(i);
            }
        });
    }
}