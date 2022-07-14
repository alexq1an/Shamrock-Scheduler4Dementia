package com.example.shamrock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity3 extends AppCompatActivity {
    private Button setting;
    private Button patientTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        setting = (Button) findViewById(R.id.settingbt);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSetting();
            }
        });
        patientTask = (Button) findViewById(R.id.patientTask);
        patientTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity5();
            }
        });
    }
    public void openSetting(){
        Intent intent = new Intent(this,caregiverSettingsActivity.class);
        startActivity(intent);
    }
    public void openActivity5(){
        Intent intent = new Intent(this,MainActivity5.class);
        startActivity(intent);
    }
}