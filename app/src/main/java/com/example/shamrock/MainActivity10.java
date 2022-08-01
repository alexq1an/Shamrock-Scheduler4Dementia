package com.example.shamrock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.shamrock.databinding.ActivityMain10Binding;
import com.example.shamrock.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class MainActivity10 extends AppCompatActivity {
    ActivityMain10Binding binding;
    StorageReference storageReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain10Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.getImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                progressDialog = new ProgressDialog(MainActivity10.this);
                progressDialog.setMessage("Fetching the desired image.....");
                progressDialog.setCancelable(false);
                progressDialog.show();

                String imageID = binding.etimageId.getText().toString();
                storageReference = FirebaseStorage.getInstance().getReference(imageID+".jpg");
                try {
                    File localfile = File.createTempFile("tempfile",".jpg");
                    storageReference.getFile(localfile)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    if(progressDialog.isShowing())
                                        progressDialog.dismiss();

                                    Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                                    binding.imageView.setImageBitmap(bitmap);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if(progressDialog.isShowing())
                                        progressDialog.dismiss();
                                    Toast.makeText(MainActivity10.this,"Failed to retrieve",Toast.LENGTH_SHORT).show();
                                }
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        });
    }
}