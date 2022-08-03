package com.example.shamrock;

//importing all the required libraries
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.shamrock.databinding.ActivityMain5Binding;

//import com.google.android.gms.cast.framework.media.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

//making a public class
public class MainActivity5 extends AppCompatActivity {
    //initializing
    private ActivityMain5Binding binding;
    private MaterialTimePicker picker;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    public String patientID;
    public String scheduleID;
    public String currentID;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference pRef = db.collection("Patient");
    private CollectionReference taskRef;
    public Integer count = 0;
    public Integer imageCount = 0;

    //button for youtube link
    private Button Url;
    private Button confirm;
    private Button AddImage;
    private EditText description;
    private EditText title;

    private Button uploadBtn;
    private ImageView imageView;
    private ProgressBar progressBar;

    //vars
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Image");

    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMain5Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        createNotificationChannel();

        //image stuff
        uploadBtn = findViewById(R.id.upload_btn);
//        progressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent , 2);
            }
        });
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri != null){
                    uploadToFirebase(imageUri);
                }else{
                    Toast.makeText(MainActivity5.this, "Please Select Image", Toast.LENGTH_SHORT).show();
                }
            }
        });


        title = findViewById(R.id.title);
        description = findViewById(R.id.task_description);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            patientID = extras.get("patientDocId").toString();
            scheduleID = extras.get("scheduleDocId").toString();
        }

        //using setOnClickerListener
        binding.selectTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        binding.cancelAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });

        Url = findViewById(R.id.AddUrl);
        Url.setOnClickListener(new View.OnClickListener() {
            @Override
            //using method gotoUrl
            //add the link to youtube main page
            public void onClick(View view) {
                gotoUrl("https://www.google.com/imghp?hl=EN");
            }
            private void gotoUrl(String s) {
                Uri uri = Uri.parse(s);
                startActivity((new Intent(Intent.ACTION_VIEW,uri)));
            }
        });

        confirm = findViewById(R.id.confirm_button);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm(v);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==2 && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private void uploadToFirebase(Uri uri){

        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Model model = new Model(uri.toString());
                        String modelId = root.push().getKey();
                        root.child(modelId).setValue(model);
//                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(MainActivity5.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        imageView.setImageResource(R.drawable.ic_baseline_add_a_photo_24);
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity5.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
            }
        });

        db.collection("Patient").document(patientID).collection("Schedule")
                .document(scheduleID).collection("Task").document(currentID).update("image", fileRef.getName());
    }
    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }

    //making a method which will be used to cancel the alarm
    private void cancelAlarm() {
        //checking the condition
        if (count < 1){
            Toast.makeText(this, "Select a time first", Toast.LENGTH_SHORT).show();
            return;
        }
        //linking the pages
        Intent intent = new Intent(this,AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        //cancelling the alarm
        if (alarmManager == null){
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }
        //giving the message that alarm has been cancelled
        alarmManager.cancel(pendingIntent);
        db.collection("Patient").document(patientID)
                .collection("Schedule")
                .document(scheduleID)
                .collection("Task")
                .document(currentID)
                .delete();
        count = 0;
        Toast.makeText(this, "Alarm Cancelled", Toast.LENGTH_SHORT).show();
    }

    //making a method which will be used to set the alarm
    private void setAlarm() {
        //using AlarmManager
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(),AlarmReceiver.class);
        intent.putExtra("patientDocID", patientID);
        intent.putExtra("taskDocId", currentID);
        intent.putExtra("scheduleDocID", scheduleID);

        pendingIntent = PendingIntent.getBroadcast(this,0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        sendBroadcast(intent);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,pendingIntent);
        //giving the message that alarm is set
        Toast.makeText(this, "Alarm set Successfully", Toast.LENGTH_SHORT).show();
    }

    //method for picking up time correct format
    private void showTimePicker() {
        //using TimeFormat
        picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Alarm Time")
                .build();

        picker.show(getSupportFragmentManager(),"Shamrock");

        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this will decide am or pm
                if (picker.getHour() > 12){

                    binding.selectedTime.setText(
                            String.format("%02d",(picker.getHour()-12))+" : "+String.format("%02d",picker.getMinute())+" PM"
                    );

                }else {

                    binding.selectedTime.setText(picker.getHour()+" : " + picker.getMinute() + " AM");

                }

                Bundle extras = getIntent().getExtras();
                if(extras != null) {
                    calendar = (Calendar) extras.get("calendar");
                }
                calendar.set(Calendar.HOUR_OF_DAY,picker.getHour());
                calendar.set(Calendar.MINUTE,picker.getMinute());
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND,0);

                //adding task to firebase
                taskRef = db.collection("Patient").document(patientID)
                        .collection("Schedule")
                        .document(scheduleID)
                        .collection("Task");

                taskRef.whereEqualTo("time", calendar.getTime())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                    int counter = 0;
                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                        counter++;
                                        Task tempTask = documentSnapshot.toObject(Task.class);
                                        String id = documentSnapshot.getId();
                                        currentID = documentSnapshot.getId();
                                        tempTask.setTime(calendar.getTime());
                                        taskRef.document(id).set(tempTask);
                                    }
                                    if(counter == 0){
                                        DocumentReference addedDocRef = taskRef.document();
                                        Task task1 = new Task();
                                        currentID = addedDocRef.getId();
                                        task1.setTime(calendar.getTime());
                                        addedDocRef.set(task1);
                                    }
                                }

                            }
                        });
                //automatically setting alarm
                setAlarm();
                count++;
            }
        });

    }

    private void confirm(View v){
        //check all inputs
        String finalTitle = title.getText().toString();
        String finalDescription = description.getText().toString();

        if (TextUtils.isEmpty(finalTitle)) {
            title.setError("Title cannot be empty");
            title.requestFocus();
            return;
        } else if (count < 1) {
            Toast.makeText(this, "Must Select A Time  ", Toast.LENGTH_SHORT).show();
            return;

        }

        taskRef = db.collection("Patient").document(patientID)
                .collection("Schedule")
                .document(scheduleID)
                .collection("Task");

        if(TextUtils.isEmpty(finalDescription)){
            taskRef.document(currentID).update("title", finalTitle);
            taskRef.document(currentID).update("description", null);
        }else{
            taskRef.document(currentID).update("title", finalTitle);
            taskRef.document(currentID).update("description", finalDescription);
        }
        Toast.makeText(this, "Task added, return or add a new task", Toast.LENGTH_SHORT).show();
        //resetting page
        title.setText("");
        description.setText("");
        count = 0;

        //change pages
//        Intent i = new Intent(this,MainActivity4.class);
//        i.putExtra("patientDocId", patientID);
//        i.putExtra("scheduleDocId", scheduleID);
//        startActivity(i);

    }

    //this method is created for notification
    private void createNotificationChannel() {
        //will be used to send notification to the user (Patients)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Shamrock Alarm";
            String description = "Channel For Alarm Manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("Shamrock",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}