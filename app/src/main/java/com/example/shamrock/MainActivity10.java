package com.example.shamrock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shamrock.databinding.ActivityMain10Binding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class MainActivity10 extends AppCompatActivity {
    ActivityMain10Binding binding;
    StorageReference storageReference;
    String texttitle;
    String description;
    String imageName = null;
    TextView title;
    TextView des;
    private DocumentReference docTask;
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain10Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        title = findViewById(R.id.patient_output_title);
        confirm = findViewById(R.id.confirm_button);
        des = findViewById(R.id.task_description);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            texttitle = extras.get("title").toString();
            description = extras.get("description").toString();
            imageName = extras.get("image").toString();
        }
        title.setText(texttitle);
        des.setText(description);

        binding.getImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if (imageName == null){
                    Toast.makeText(MainActivity10.this, "No images to show", Toast.LENGTH_SHORT).show();
                    return;
                }

                storageReference = FirebaseStorage.getInstance().getReference(imageName);
                try {
                    File localfile = File.createTempFile("tempfile",".jpg");
                    storageReference.getFile(localfile)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                                    binding.imageView.setImageBitmap(bitmap);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity10.this,"Failed to retrieve",Toast.LENGTH_SHORT).show();
                                }
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity10.this, patient_homepage.class);
                startActivity(i);
            }
        });
    }
}