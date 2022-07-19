package com.example.shamrock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * This page allows a caregiver to create a patient's account.
 */

public class MainActivity6 extends AppCompatActivity {
    private static final String TAG = "MainActivity6";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference cRef = db.collection("Caregiver");
    private CollectionReference pRef = db.collection("Patient");

    private EditText editTextUsername;
    private EditText editTextAge;
    private EditText editTextGender;

    private Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        editTextUsername = findViewById(R.id.edit_text_username);
//        editTextAge = findViewById(R.id.edit_text_age);
//        editTextGender = findViewById(R.id.edit_text_sex);
//
//        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                addPatient(view);
            }
        });
    }

    public void addPatient(View v){
        String username = editTextUsername.getText().toString();
        int age = Integer.parseInt(editTextAge.getText().toString());
        char sex = editTextGender.getText().charAt(0);

        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Username cannot be empty");
            editTextUsername.requestFocus();
        }
        else {
            Map<String, Object> patient = new HashMap<>();
            patient.put("username", username);
            patient.put("age", age);
            patient.put("sex", sex);

//            db.collection("Patient")
//                    .set(patient)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Log.d(TAG, "Patient successfully added!");
//
//                            //new create account
//                            Patient patient = new Patient(username, age, sex);
//
//                            //add document to firebase
//                            cRef.document(getCurrentUser().getUid()).set(patient);
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.w(TAG, "Error adding patient", e);
//                        }
//                    });
        }
    }
}
