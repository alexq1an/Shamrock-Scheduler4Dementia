package com.example.shamrock;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import com.google.firebase.firestore.EventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * This page allows a caregiver to create a patient's account.
 */

public class MainActivity6 extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference pRef = db.collection("Patient");
    private CollectionReference cRef = db.collection("Caregiver");

    private EditText editTextUsername;
    private EditText editTextAge;
    private TextView editTextGender;
    private TextView textViewData;
    private Button update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main6);

        editTextUsername = findViewById(R.id.edit_text_username);
        editTextAge = findViewById(R.id.edit_text_userage);
        editTextGender = findViewById(R.id.edit_text_usergender);
        textViewData = findViewById(R.id.text_view_data);
        update = findViewById(R.id.update_button);

        //displays the patient login id
        String id = shortId();
        String data = "";
        data += "Patient Login ID: \n" + id + "\n";
        textViewData.setText(data);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPatient(v, id);
            }
        });

    }

    //adds a new patient with generated id
    public void addPatient(View v, String id) {
        //gets user inputs
        String username = editTextUsername.getText().toString();
        String age = editTextAge.getText().toString();
        String sex = editTextGender.getText().toString();

        //error check
        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Username cannot be empty");
            editTextUsername.requestFocus();
        } else {
            //adding info into a patient
            Map<String, Object> patient = new HashMap<>();
            patient.put("loginId", id);
            patient.put("username", username);
            patient.put("age", age);
            patient.put("sex", sex);

            //adding patient
            DocumentReference addedDocRef = db.collection("Patient").document();
            addedDocRef.set(patient);
            String patientDocId = addedDocRef.getId();

            //adding first patient to Caregiver
            Bundle extras = getIntent().getExtras();
                if(extras != null){
                    String docId = extras.get("documentId").toString();//retrieves documentId
                    cRef.document(docId)
                            .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                    if(error != null){//fail to create patient
                                        Toast.makeText(MainActivity6.this, error.toString(), Toast.LENGTH_SHORT).show();
                                        return;
                                    }else{
                                        //create a new list and add to database under specified caregiver
                                        Caregiver caregiver = value.toObject(Caregiver.class);
                                        ArrayList<String> newList = new ArrayList<>();
                                        newList.add(patientDocId);//add patient to the arraylist
                                        caregiver.setpList(newList);
                                        cRef.document(docId).set(caregiver);

                                    }
                                }
                            });
                    //lead to Home page
                    Toast.makeText(this, "account created, please Login", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity6.this, MainActivity2.class);
                    startActivity(intent);
        }
        }
    }

    //generates a 6 digit number id for patient
    public String shortId(){
        int min = 0;
        int max = 9999;
        Random rand = new Random();

        int digit6 = rand.nextInt(9999999);
        return Integer.toString(digit6);
    }
}
