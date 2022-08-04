package com.example.shamrock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * This is a buffer page to let the task lists load before
 * entering the patient homepage
 */
public class BufferPatientHome extends AppCompatActivity {
    //initializing variables
    Button button;
    String pDocId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buffer_patient_home);

        //updating the patient Document Id to pass information
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            pDocId = extras.get("documentId").toString();
        }

        //setting up the information for the ListAdapter
        ((global)this.getApplication()).refreshTaskForTargetPatientForAll(pDocId);

        //button to exit page
        button = (Button) findViewById(R.id.bufferconfirm2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //passing information
                Intent i = new Intent(BufferPatientHome.this, patient_homepage.class);
                i.putExtra("documentId", pDocId);
                startActivity(i);
            }
        });
    }
}
