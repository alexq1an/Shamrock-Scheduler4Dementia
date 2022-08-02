package com.example.shamrock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BufferCaregiver extends AppCompatActivity {

    Button button;
    String patientDocId;
    String patientName;
    String caregiverID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buffer_caregiver);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            patientDocId = extras.get("patientDocId").toString();
            patientName = extras.get("username").toString();
            caregiverID = extras.get("caregiverDocId").toString();
            //showTask();//call this method to show the task list
        }


        ((global)this.getApplication()).refreshTaskForTargetPatientForAll(patientDocId);


        button = (Button) findViewById(R.id.bufferconfirm1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(BufferCaregiver.this, MainActivity4.class);
                i.putExtra("patientDocId", patientDocId);
                i.putExtra("username",patientName);
                i.putExtra("caregiverDocId", caregiverID);
                startActivity(i);

            }
        });





    }
}
