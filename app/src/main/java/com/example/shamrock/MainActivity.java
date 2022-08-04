package com.example.shamrock;
//importing all the required libraries

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    //initializing all the buttons used
    private Button button;
    private Button pButton;
    FirebaseAuth auth;
    FirebaseUser User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //linking the buttons with the id set in xml file
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.caregiver);
        pButton = (Button) findViewById(R.id.patient);

        //leads to caregiver login when the button is pressed
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
            }
        });

        //leads to patient login when the button is pressed
        pButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openPatientLogin();
            }
        });

    }

    //lead to Caregiver page
    public void openActivity2(){
        //grabbing information about user
        auth = FirebaseAuth.getInstance();
        User = auth.getCurrentUser();

        //changes page to Caregiver homepage
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    //lead to Patient page
    public void openPatientLogin(){
        //grabbing information about user
        auth = FirebaseAuth.getInstance();
        User = auth.getCurrentUser();

        //change page to patient homepage
        Intent intent = new Intent(this, MainActivity7.class);
        startActivity(intent);

    }
}
