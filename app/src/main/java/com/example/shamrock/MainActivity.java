package com.example.shamrock;

//import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.google.firestore.v1.WriteResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final String KEY_NAME = "Name";
    private static final String KEY_TITLE = "Username";
    private static final String KEY_DESCRIPTION = "password";

    private EditText editTextName;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private TextView textViewData;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference dRef = db.collection("Caregiver").document("My First Note");
    private CollectionReference cRef = db.collection("Caregiver");

    private ArrayList<Caregiver> caregiverList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

//        editTextName = findViewById(R.id.edit_text_name);
        editTextUsername = findViewById(R.id.edit_text_username);
        editTextPassword = findViewById(R.id.edit_text_password);
        textViewData = findViewById(R.id.text_view_data);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        cRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
//                if (e != null){
//                    return;
//                }
//
//                String data = "";
//                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
//                    Caregiver caregiver = documentSnapshot.toObject(Caregiver.class);
//                    caregiver.setDocumentId(documentSnapshot.getId());
//
//                    String documentId = caregiver.getDocumentId();
//                    String name = caregiver.getName();
//                    String username = caregiver.getUsername();;
//                    String password = caregiver.getPassword();
//
//                    data += "ID: " + documentId + "\nName: " + name +
//                            "\nUsername: " + username + "\nPassword: " + password + "\n\n";
//                }
//                textViewData.setText(data);
//            }
//        });
//    }

    public void createAccount(View v){
//        String name = editTextName.getText().toString();
        String user = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

//        Caregiver caregiver = new Caregiver(name, user, password);
        Caregiver caregiver = new Caregiver(user, password);

        DocumentReference addedDocRef = cRef.document();
        addedDocRef.set(caregiver);
        caregiver.setDocumentId(addedDocRef.getId());
        caregiverList.add(caregiver);

                //not sure if these works
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Toast.makeText(MainActivity.this, "Account created", Toast.LENGTH_SHORT).show();
//                    }
//
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
//                        Log.d(TAG, e.toString());
//                    }
//                });



    }

    public void updatePassword(View v){
        String password = editTextPassword.getText().toString();
        String user = editTextUsername.getText().toString();
        int i = 0;
        for( i = 0; i < caregiverList.size(); i++){
            if(caregiverList.get(i).getUsername().equals(user)){
                break;
            }
        }

        if(i < caregiverList.size()){
            cRef.document(caregiverList.get(i).getDocumentId()).update(KEY_DESCRIPTION, password);
        }

    }

    public void loadNote(View v){
        cRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            Caregiver caregiver = documentSnapshot.toObject(Caregiver.class);
                            caregiver.setDocumentId(documentSnapshot.getId());

                            String documentId = caregiver.getDocumentId();
//                            String name = caregiver.getName();
                            String username = caregiver.getUsername();;
                            String password = caregiver.getPassword();
                            //add name
                            data += "ID: " + documentId + "\nName: " +
                                    "\nUsername: " + username + "\nPassword: " + password + "\n\n";

//
                        }
                        textViewData.setText(data);
                    }
                });
    }
}