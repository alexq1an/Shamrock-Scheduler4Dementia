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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import android.widget.Button;
import android.content.Intent;
 import android.view.View;

public class MainActivity extends AppCompatActivity {
    public Button button;
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

        editTextName = findViewById(R.id.edit_text_name);
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
        //gets user input
        String name = editTextName.getText().toString();
        String user = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        //checks if user exists already
        for(int i = 0; i < caregiverList.size(); i++){
            if(caregiverList.get(i).getUsername().equals(user)){
                //user already exists
                String data = "Account Already Exists";
                textViewData.setText(data);
                return;
            }
        }

        Caregiver caregiver = new Caregiver(name, user, password);
        //new create account
//        Caregiver caregiver = new Caregiver(user, password);

        DocumentReference addedDocRef = cRef.document();
        addedDocRef.set(caregiver);
        caregiver.setDocumentId(addedDocRef.getId());
        caregiverList.add(caregiver);
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

        if(i > caregiverList.size()){
            //case where the user doesn't exist
            return;
        }
        cRef.document(caregiverList.get(i).getDocumentId()).update(KEY_DESCRIPTION, password);

    }

    public void logIn(View v){
        String user = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        //checks if user exists already


        button = (Button) findViewById(R.id.login_button);
         button.setOnClickListener(new View.OnClickListener() { @Override
        public void onClick(View v) {
            Intent intent= new Intent(MainActivity.this,MainActivity2.class);
            startActivity(intent);
        }
        });

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
                            String name = caregiver.getName();
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