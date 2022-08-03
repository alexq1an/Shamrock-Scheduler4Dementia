package com.example.shamrock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BufferPatientHome extends AppCompatActivity {

    Button button;

    String pDocId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buffer_patient_home);


        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            pDocId = extras.get("documentId").toString();
        }


        ((global)this.getApplication()).refreshTaskForTargetPatientForAll(pDocId);


        button = (Button) findViewById(R.id.bufferconfirm2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(BufferPatientHome.this, patient_homepage.class);
                i.putExtra("documentId", pDocId);
                startActivity(i);

            }
        });



    }
}
