package com.example.shamrock;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import android.widget.Button;
import android.content.Intent;

public class MainActivity2 extends AppCompatActivity {
    //Declarations
    public Button button;
    private static final String TAG = "MainActivity";

    FirebaseAuth mAuth;

    //fields
    private static final String KEY_NAME = "Name";
    private static final String KEY_TITLE = "Username";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_DESCRIPTION = "password";

    //from xml from user
    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewData;

    private Button register_button;
    private Button login_button;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference dRef = db.collection("Caregiver").document("My First Note");
    private CollectionReference cRef = db.collection("Caregiver");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);

        //storing strings and information from activity_main2.xml
        editTextUsername = findViewById(R.id.edit_text_username);
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_password);
        textViewData = findViewById(R.id.text_view_data);

        //buttons from activity_main2.xml
        register_button = findViewById(R.id.register_button);
        login_button = findViewById(R.id.login_button);

        mAuth = FirebaseAuth.getInstance();

        //when login button clicked, call this method
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = editTextUsername.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                //error checking user input
                if (TextUtils.isEmpty(user)){
                    editTextUsername.setError("Username cannot be empty");
                    editTextUsername.requestFocus();
                }
                else if (TextUtils.isEmpty(email)){
                    editTextEmail.setError("Email cannot be empty");
                    editTextEmail.requestFocus();
                }
                else if (TextUtils.isEmpty(password)){
                    editTextPassword.setError("Password cannot be empty");
                    editTextPassword.requestFocus();
                }
                else{
                    //adding user to firebase authentication
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(MainActivity2.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(MainActivity2.this, MainActivity3.class);
                                i.putExtra("documentId",mAuth.getCurrentUser().getUid());
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(MainActivity2.this, "Login Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        //lead to registration page
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount(view);            }
        });
    }

    //Caregiver can login by entering their username, email, and password
    public void logIn(View v){
        //Storing Strings from user interface
        String user = editTextUsername.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        //error checking user input
        if (TextUtils.isEmpty(user)){
            editTextUsername.setError("Username cannot be empty");
            editTextUsername.requestFocus();
        }
        else if (TextUtils.isEmpty(email)){
            editTextEmail.setError("Email cannot be empty");
            editTextEmail.requestFocus();
        }
        else if (TextUtils.isEmpty(password)){
            editTextPassword.setError("Password cannot be empty");
            editTextPassword.requestFocus();
        }
        else{
            //adding user to firebase authentication
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        //show reminder to user that task is successful
                        Toast.makeText(MainActivity2.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                        //change page and add caregiver user document id so that we can grab it on the next page
                        Intent i = new Intent(MainActivity2.this, MainActivity3.class);

                        i.putExtra("documentId",mAuth.getCurrentUser().getUid().toString());
                        startActivity(i);
                    }
                    else{
                        //show user log in error
                        Toast.makeText(MainActivity2.this, "Login Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    //Caregiver can create an account by entering username, email, and password
    //No duplicate account should be created
    public void createAccount(View v) {
        //gets user input
        String user = editTextUsername.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (TextUtils.isEmpty(user)) {
            editTextUsername.setError("Username cannot be empty");
            editTextUsername.requestFocus();
        } else if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Email cannot be empty");
            editTextEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Password cannot be empty");
            editTextPassword.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity2.this, "Account created", Toast.LENGTH_SHORT).show();

                        //new create account
                        Caregiver caregiver = new Caregiver(user, email, password);

                        //add document to firebase
                        cRef.document(mAuth.getCurrentUser().getUid()).set(caregiver);
                        Intent i = new Intent(MainActivity2.this, MainActivity6.class);
                        i.putExtra("documentId",mAuth.getCurrentUser().getUid());
                        startActivity(i);
                    } else {
                        Toast.makeText(MainActivity2.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

}

