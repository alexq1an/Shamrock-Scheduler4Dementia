package com.example.shamrock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * This page allows a caregiver to create a patient's account.
 */

public class MainActivity8 extends AppCompatActivity {
    private static final String TAG = "MainActivity8";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //    private CollectionReference cRef = db.collection("Caregiver");
    private CollectionReference pRef = db.collection("Patient");

    private EditText editTextUsername;
    private EditText editTextAge;
    private TextView editTextGender;

    private Button add_button;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main6);
//
//        editTextUsername = findViewById(R.id.edit_text_username);
//        editTextAge = findViewById(R.id.edit_text_userage);
//        editTextGender = findViewById(R.id.edit_text_usergender);
////        Spinner spinner = (Spinner) findViewById(R.id.user_sex_spin);
////        spinner.setOnItemSelectedListener(this);
//
//        add_button = findViewById(R.id.patientInfo_set_confirm_button);
//
//        add_button.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                addPatient(view);
//            }
//        });
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main6);

        editTextUsername = findViewById(R.id.edit_text_username);
        editTextAge = findViewById(R.id.edit_text_userage);
        editTextGender = findViewById(R.id.edit_text_usergender);

        add_button = findViewById(R.id.patientInfo_set_confirm_button);

        //when add button clicked, call this method
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPatient(view);
            }
        });
    }

//    public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {
//
//        public void onItemSelected(AdapterView<?> parent, View view,
//                                   int pos, long id) {
//            // An item was selected. You can retrieve the selected item using
//             parent.getItemAtPosition(pos);
//        }
//
//        public void onNothingSelected(AdapterView<?> parent) {
//            // Another interface callback
//        }
//    }

    //adds a new patient with generated id
    public void addPatient(View v) {
        //gets user inputs
        String username = editTextUsername.getText().toString();
        String age = editTextAge.getText().toString();
        String sex = editTextGender.getText().toString();

//        Patient patient = new Patient(username, age, sex);

        //error check
        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Username cannot be empty");
            editTextUsername.requestFocus();
        } else {
//            DocumentReference addedDocRef = pRef.document();
//            patient.setDocumentId(addedDocRef.getId());
//            addedDocRef.set(patient);

            //adding info into a document
            Map<String, Object> patient = new HashMap<>();
            patient.put("username", username);
            patient.put("age", age);
            patient.put("sex", sex);

//            Patient patient = new Patient(username, age, sex);

            db.collection("Patient")
                    .add(patient)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "Patient added!\nPatient ID: " + documentReference.getId());
                            //lead to Home page
                            Intent intent = new Intent(MainActivity8.this, MainActivity3.class);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding patient", e);
                        }
                    });
        }
    }
}
