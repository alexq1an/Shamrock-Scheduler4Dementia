package com.example.shamrock;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * This page allows a caregiver to update patient's information
 */

public class MainActivity8 extends AppCompatActivity {
    private static final String TAG = "MainActivity8";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference pRef = db.collection("Patient");

    private EditText editTextUsername;
    private EditText editTextAge;
    private TextView editTextGender;
    private TextView textViewData;
    private Button update_button;

    public String patientDocId;
    public String caregiverID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main8);

        editTextUsername = findViewById(R.id.edit_text_username);
        editTextAge = findViewById(R.id.edit_text_userage);
        editTextGender = findViewById(R.id.edit_text_usergender);
        textViewData = findViewById(R.id.text_view_data);
        update_button = findViewById(R.id.update_button);


        //grabbing the transferred patient information from MainActivity4
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            patientDocId = extras.getString("patientDocId");
            caregiverID = extras.getString("caregiverDocId");
            //Toast for testing
//            Toast.makeText(MainActivity8.this, "DocumentId: " + patientDocId, Toast.LENGTH_SHORT).show();

        }

        //when update button clicked, call this method

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateInfo(patientDocId);
                gobacktoHome();
            }
        });


    }

    //updates a patient's information
    public void updateInfo(String patientDocId) {
        //gets user inputs
        String username = editTextUsername.getText().toString();
        String age = editTextAge.getText().toString();
        String sex = editTextGender.getText().toString();

        DocumentReference currentPRef = pRef.document(patientDocId);

        //check which field to update
        if (!TextUtils.isEmpty(username)) {
            //update username
            currentPRef
                    .update("username", username)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "Patient username updated!");
                            Toast.makeText(MainActivity8.this, "Username updated!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error updating", e);
                            Toast.makeText(MainActivity8.this, "Update fail", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        if (!TextUtils.isEmpty(age)) {
            //update age
            currentPRef
                    .update("age", age)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "Patient age updated!");
                            Toast.makeText(MainActivity8.this, "Age updated!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error updating", e);
                            Toast.makeText(MainActivity8.this, "Update fail", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        if (!TextUtils.isEmpty(sex)) {
            //update sex
            currentPRef
                    .update("sex", sex)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "Patient sex updated!");
                            Toast.makeText(MainActivity8.this, "Sex updated!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error updating", e);
                            Toast.makeText(MainActivity8.this, "Update fail", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public void gobacktoHome(){
        Intent intent = new Intent(this,MainActivity3.class);
        intent.putExtra("documentId", caregiverID);
        startActivity(intent);
    }
}

//    public void loadNote(View v){
//        pRef.get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        String data = "";
//
//                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
//                            Patient patient = documentSnapshot.toObject(Patient.class);
//                            patient.setDocumentId(documentSnapshot.getId());
//
//                            String documentId = patient.getLoginId();
////
//                            //add name
//                            data += "Patient Login ID: " + documentId + "\n\n";
//
////
//                        }
//                        textViewData.setText(data);
//                    }
//                });
//    }
