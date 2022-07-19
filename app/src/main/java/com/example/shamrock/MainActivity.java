package com.example.shamrock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button pButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.caregiver);
        pButton = (Button) findViewById(R.id.patient);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
            }
        });
        pButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openPatientLogin();
            }
        });
    }
    //lead to Caregiver page
    public void openActivity2(){
        Intent intent = new Intent(this,MainActivity2.class);
        startActivity(intent);
    }
    //lead to Patient page
    public void openPatientLogin(){
        Intent intent = new Intent(this,MainActivity7.class);
        startActivity(intent);
    }
}