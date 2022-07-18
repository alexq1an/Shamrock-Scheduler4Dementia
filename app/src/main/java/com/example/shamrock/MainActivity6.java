package com.example.shamrock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity6 extends AppCompatActivity {
    private Button patientInfo_set_confirm_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        patientInfo_set_confirm_button = (Button) findViewById(R.id.patientInfo_set_confirm_button);
        patientInfo_set_confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backtoMainActivity3();
            }
        });
    }

    public void backtoMainActivity3(){
        Intent intent = new Intent(this,MainActivity5.class);
        startActivity(intent);
    }
}
