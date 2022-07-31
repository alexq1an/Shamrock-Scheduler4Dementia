package com.example.shamrock;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class patient_homepage extends AppCompatActivity {
    // Create firebase references
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference pRef = db.collection("Patient");

    //define variables for showing task list
    public DocumentReference pDocId;
    public ActivityMain3Binding binding;
    public ArrayList<Task> taskList = new ArrayList<>();
    ArrayList<String> DocID;
    Task task;
    Schedule schedule;

//    private DocumentReference newRef = db.collection("Patient").document("4VqwOvfFqCD0HiRZWknB")
//            .collection("Schedule").document("lMuGUFBBPGE1BcyiWfRS");


//    TextView month,day,year;
    TextView day;
    @Override
    // present date-year-month
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_homepage);

//        //grab documentId
//        Bundle extras = getIntent().getExtras();
//        if(extras != null) {
//            pDocId = pRef.document(extras.get("documentId").toString());
//        }

//        month = findViewById(R.id.month);
        day =findViewById(R.id.day);
//        year=findViewById(R.id.year);



        Date currentTime = Calendar.getInstance().getTime();
        String formattedDate = DateFormat.getInstance ().format(currentTime);

        String[] splitDate = formattedDate.split(",");
        Log.d("my Log",currentTime.toString());
        Log.d("my Log",formattedDate);
//        month.setText(splitDate[1]);
        day.setText(splitDate[0]);
//        year.setText(splitDate[2]);

        Log.d("my Log",splitDate[0].trim());
//        Log.d("my Log",splitDate[1].trim());
//        Log.d("my Log",splitDate[2].trim());

/**
 * Shows task list
 * */
        //below are patient info List on caregiver's side.
        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            pDocId = pRef.document(extras.get("documentId").toString());//grabbing patient docId

            //using dummy
            pDocId.collection("Schedule")
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(error != null){//fails to get document?
                        Toast.makeText(patient_homepage.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }else{
                        schedule = value.toObject(Schedule.class);

                        //iterating through sList
                        for(int i = 0; i < schedule.getTaskArrayList().size(); i++){
                            int index = i;
                            pDocId.collection("Schedule").document(String.valueOf(schedule.getTaskArrayList().get(i))).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                //adding each task to list for ListAdapter
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                    Task task = value.toObject(Task.class);
//                                    patient.setDocumentId(caregiver.getpList().get(index));
                                    taskList.add(task);//manually adding task into another taskList
                                    ListAdapter2 la2 = new ListAdapter2(patient_homepage.this, taskList);
                                    binding.patientsListView.setAdapter(la2);
                                }
                            });
                        }

//                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                        FirebaseFirestore docRef = FirebaseFirestore.getInstance();
//                        docRef.collection("Caregiver")
//                                .document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                        if (task.isSuccessful()) {
//                                            DocumentSnapshot doc = task.getResult();
//                                            DocID = (ArrayList<String>) doc.get("pList");
//                                        }
//                                        else{
//                                            Toast.makeText(patient_homepage.this, "Sb", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
                    }
                }
                    });

        }
//        showTask();
    }

    public void showTask(){
        //grab patient docId

        //access





        //below are patient's task List
        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        newRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
        {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Toast.makeText(MainActivity3.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }else{
                            caregiver = value.toObject(Caregiver.class);

                            //iterating through caregiver's patients
                            for(int i = 0; i < caregiver.getpList().size(); i++){
                                int index = i;
                                pRef.document(caregiver.getpList().get(i)).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                    //adding each patient to list for ListAdapter
                                    @Override
                                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                        Patient patient = value.toObject(Patient.class);
                                        patient.setDocumentId(caregiver.getpList().get(index));
                                        patients.add(patient);
                                        ListAdapter listAdapter = new ListAdapter(MainActivity3.this,patients);
                                        binding.patientsListView.setAdapter(listAdapter);
                                    }
                                });
                            }
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            FirebaseFirestore docRef = FirebaseFirestore.getInstance();
                            docRef.collection("Caregiver")
                                    .document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot doc = task.getResult();
                                                DocID = (ArrayList<String>) doc.get("pList");
                                            }
                                            else{
                                                Toast.makeText(MainActivity3.this, "Sb", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
            });

}
