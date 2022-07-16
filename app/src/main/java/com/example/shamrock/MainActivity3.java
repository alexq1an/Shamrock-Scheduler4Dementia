package com.example.shamrock;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity3 extends AppCompatActivity {
    private Button setting;
    private Button patientTask;
    private Button testing_button;
    private CollectionReference cRef = FirebaseFirestore.getInstance().collection("Caregiver");

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
        testing_button = (Button) findViewById(R.id.testingbt);
        testing_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    testing();
            }
        });

    }

    public void openActivity4(){
        Intent intent = new Intent(this,MainActivity4.class);
        startActivity(intent);
    }

    public void openSetting(){
//        Intent intent = new Intent(this,caregiverSettingsActivity.class);
//        startActivity(intent);
    }
    public void openActivity5(){
        Intent intent = new Intent(this,MainActivity5.class);
        startActivity(intent);
    }

    //temporary function for testing if data transfers over
    public void testing(){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            cRef.document(extras.get("documentId").toString())
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Toast.makeText(MainActivity3.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }else{
                            Caregiver caregiver = value.toObject(Caregiver.class);
                            String username = caregiver.getUsername();
                            Toast.makeText(MainActivity3.this, username, Toast.LENGTH_SHORT).show();
                        }
                }
            });
        }

    }
}