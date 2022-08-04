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
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

/**
 * Page that displays a sepecific task to a patient
 */
public class MainActivity10 extends AppCompatActivity {
    //to control the UI
    ActivityMain10Binding binding;
    //reference to where the images are stored
    StorageReference storageReference;

    //for displaying information
    String texttitle;
    String description;
    String imageName = null;
    TextView title;
    TextView des;

    //for exiting page
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain10Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //for displaying information to the UI
        title = findViewById(R.id.patient_output_title);
        confirm = findViewById(R.id.confirm_button);
        des = findViewById(R.id.task_description);

        //grabbing information to display
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            texttitle = extras.get("title").toString();
            //check if the there is something to display
            if(extras.get("description") != null){
                description = extras.get("description").toString();

            }
            if(extras.get("image") != null){
                imageName = extras.get("image").toString();
            }
        }

        //displaying information
        title.setText(texttitle);
        des.setText(description);

        //for displaying image
        binding.getImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //check if image exists
                if (imageName == null){
                    Toast.makeText(MainActivity10.this, "No images to show", Toast.LENGTH_SHORT).show();
                    return;
                }

                //grabbing from Firebase storage
                storageReference = FirebaseStorage.getInstance().getReference(imageName);
                try {
                    File localfile = File.createTempFile("tempfile",".jpg");
                    storageReference.getFile(localfile)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    //displays the image
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

        //changes pages and jumps back to the patient homepage
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity10.this, patient_homepage.class);
                startActivity(i);
            }
        });
    }
}
