package com.example.shamrock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity4 extends AppCompatActivity {
    private Button newevent;
    private Button changePatientInfo_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        newevent = (Button) findViewById(R.id.newevent);
        newevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity5();
            }
        });
        changePatientInfo_button = (Button) findViewById(R.id.changePatientInfo_button);
        changePatientInfo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity6();
            }
        });
    }
    public void openActivity5(){
        Intent intent = new Intent(this,MainActivity5.class);
        startActivity(intent);
    }

    public void openActivity6(){
        Intent intent = new Intent(this,MainActivity6.class);
        startActivity(intent);
    }

}
