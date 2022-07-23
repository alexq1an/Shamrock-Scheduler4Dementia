package com.example.shamrock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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
    private static final String TAG = "MainActivity6";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference pRef = db.collection("Patient");
    private CollectionReference cRef = db.collection("Caregiver");

    private EditText editTextUsername;
    private EditText editTextAge;
    private TextView editTextGender;

    private Button add_button;

    private final int GALLERY_REQ_CODE =1000;
    ImageView imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main6);

        editTextUsername = findViewById(R.id.edit_text_username);
        editTextAge = findViewById(R.id.edit_text_userage);
        editTextGender = findViewById(R.id.edit_text_usergender);

        add_button = findViewById(R.id.patientInfo_set_confirm_button);

        ImageView add_picture_gallery=findViewById(R.id.add_picture_gallery);
        Button imageButton =findViewById(R.id.imageButton);

        //when add button clicked, call this method
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPatient(view);
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent igButton= new Intent(Intent.ACTION_PICK);
                igButton.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(igButton, GALLERY_REQ_CODE);
            }

        });
    }

    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK){
            if(requestCode==GALLERY_REQ_CODE){
                imageButton.setImageURI(data.getData());
            }
        }
        
    }



        public static void random_6_digit(String[] args){
            int minimum = 0;
            int maximum = 9999;
            Random rand = new Random();
            int randomNum = minimum + rand.nextInt((maximum-minimum)+1);
            //System.out.println(randomNum);

            int digit6 = rand.nextInt(9999999);
            System.out.println(String.format("%06d",digit6));
        }


    //adds a new patient with generated id
    public void addPatient(View v) {
        //gets user inputs
        String username = editTextUsername.getText().toString();
        String age = editTextAge.getText().toString();
        String sex = editTextGender.getText().toString();

        //error check
        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Username cannot be empty");
            editTextUsername.requestFocus();
        } else {
            //adding info into a document
            Map<String, Object> patient = new HashMap<>();
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
                    String docId = extras.get("documentId").toString();
                    cRef.document(docId)
                            .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                    if(error != null){
                                        Toast.makeText(MainActivity6.this, error.toString(), Toast.LENGTH_SHORT).show();
                                        return;
                                    }else{
                                        Caregiver caregiver = value.toObject(Caregiver.class);
                                        ArrayList<String> newList = new ArrayList<>();
                                        newList.add(patientDocId);
                                        caregiver.setpList(newList);
                                        cRef.document(docId).set(caregiver);
                                    }
                                }
                            });
                    //lead to Home page
                    Intent intent = new Intent(MainActivity6.this, MainActivity3.class);
                    startActivity(intent);
        }



        }
    }
}
