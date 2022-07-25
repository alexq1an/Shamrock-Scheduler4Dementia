package com.example.shamrock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shamrock.databinding.ActivityMain3Binding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;


/**
 * This is the caregiver homepage with the list of patients
* */
public class MainActivity3 extends AppCompatActivity {

    private CollectionReference cRef = FirebaseFirestore.getInstance().collection("Caregiver");
    private CollectionReference pRef = FirebaseFirestore.getInstance().collection("Patient");

    public ActivityMain3Binding binding;
    public ArrayList<Patient> patients = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //below are patient info List on caregiver's side.
        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            cRef.document(extras.get("documentId").toString())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {

                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Toast.makeText(MainActivity3.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }else{
                            Caregiver caregiver = value.toObject(Caregiver.class);

                            //iterating through caregiver's patients
                            for(int i = 0; i < caregiver.getpList().size(); i++){
                                pRef.document(caregiver.getpList().get(i)).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                    //adding each patient to list for ListAdapter
                                    @Override
                                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                        Patient patient = value.toObject(Patient.class);
                                        patients.add(patient);
                                    }
                                });
                            }
                        }
                    }
                });

        }

        //Adapter for our arraylist
        ListAdapter listAdapter = new ListAdapter(this,patients);

        binding.patientsListView.setAdapter(listAdapter);
        binding.patientsListView.setClickable(true);
        binding.patientsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // insert the data using the position

                //passing patient information
                Intent i = new Intent(MainActivity3.this,MainActivity4.class);
                i.putExtra("username",patients.get(position).getUsername());
                i.putExtra("loginId",patients.get(position).getList_patient_id());
                //                i.putExtra("imageid",imageId[position]);
                i.putExtra("patientDocId", patients.get(position).getDocumentId());
                startActivity(i);

//                Intent i2 = new Intent(MainActivity3.this,MainActivity8.class);
//                i2.putExtra("patientDocId", patients.get(position).getDocumentId());//passing patient's documentId
//                startActivity(i2);

            }
        });

    }

    public void openActivity5(){
        Intent intent = new Intent(this,MainActivity5.class);
        startActivity(intent);
    }


}
