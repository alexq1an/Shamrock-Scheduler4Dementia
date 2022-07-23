package com.example.shamrock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


/**
 * This page allows a patient to login with their unique id
 * */
public class MainActivity7 extends AppCompatActivity {

    private Button loginButton;

    private EditText editTextLoginId;

    // Create firebase references
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference patientRef = db.collection("Patient");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        editTextLoginId = findViewById(R.id.edit_text_patient);

        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pLogin(v);
            }
        });

    }
    public void pLogin(View v){
        String loginId = editTextLoginId.getText().toString();

        //error checking user input
        if (TextUtils.isEmpty(loginId)){
            editTextLoginId.setError("LoginId cannot be empty");
            editTextLoginId.requestFocus();
        }
        else{
            //find and get the document with the entered loginId
            // Create a query against the collection.
            Task<QuerySnapshot> query = patientRef.whereEqualTo("loginId", loginId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {//if loginId matches
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //get its(patient) documentId
//                                    document.getId();
                                    Toast.makeText(MainActivity7.this,
                                            "Login successful\n ID: " + document.getId(), Toast.LENGTH_SHORT).show();
//                                    Log.d(TAG, document.getId() + " => " + document.getData());

                                    //compare with caregiver pList reference
                                    //if matches, link the patient with the caregiver
                                    //login successful, lead to patient homepage
                                    Intent i = new Intent(MainActivity7.this, patient_homepage.class);
                                    i.putExtra("documentId", document.getId());
                                    startActivity(i);//passing patient documentId
                                }
                            } else {
                                //login fails
                                Toast.makeText(MainActivity7.this,
                                        "Login fail", Toast.LENGTH_SHORT).show();

//                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }

    }
}