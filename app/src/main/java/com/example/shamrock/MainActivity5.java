package com.example.shamrock;

//importing all the required libraries
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shamrock.databinding.ActivityMain5Binding;

//import com.google.android.gms.cast.framework.media.ImagePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.UUID;

//making a public class
public class MainActivity5 extends AppCompatActivity {
    //initializing
    private ActivityMain5Binding binding;
    private MaterialTimePicker picker;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference taskRef = db.collection("Task");
    private pTask pTask;
    private Integer count = 0;
    //button for adding image from gallery
    //private Button AddImage;
    //button for youtube link
    private Button Url;
    //int SELECT_PHOTO = 1;
    //public Uri imageUri;
    ///private FirebaseStorage storage;
    //private StorageReference storageReference;
    private Button AddImage;

    //this will display the image
    //private ImageView profilePic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pTask = new pTask();
        super.onCreate(savedInstanceState);
        binding = ActivityMain5Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        createNotificationChannel();
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
        //finding the image and button using their id
        AddImage = findViewById(R.id.AddImage1);
        AddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity5.this , MainActivity9.class);
                startActivity(i);
            }
        });
        //profilePic = findViewById(R.id.profilePic);
        //storage = FirebaseStorage.getInstance();
        //storageReference = storage.getReference();
        //profilePic.setOnClickListener(new View.OnClickListener() {
        //@Override
        //using intent
        //public void onClick(View view) {
        //  choosePicture();

        //}



        // });
        Url = findViewById(R.id.AddUrl);
        Url.setOnClickListener(new View.OnClickListener() {
            @Override
            //using method gotoUrl
            //add the link to youtube main page
            public void onClick(View view) {
                gotoUrl("https://www.youtube.com");
            }
            private void gotoUrl(String s) {
                Uri uri = Uri.parse(s);
                startActivity((new Intent(Intent.ACTION_VIEW,uri)));
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String date = extras.getString("date");
        }

    }
//    private void choosePicture() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent,1);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==1 && requestCode==RESULT_OK && data!=null && data.getData()!=null){
//            imageUri = data.getData();
//            profilePic.setImageURI(imageUri);
//            uploadPicture();
//        }
//    }

//    private void uploadPicture() {
//        final ProgressDialog pd = new ProgressDialog(this);
//        pd.setTitle("Uploading Image.....");
//        pd.show();
//
//        final String randomKey = UUID.randomUUID().toString();
//        StorageReference riverRef = storageReference.child("images/" + randomKey);
//
//        riverRef.putFile(imageUri)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){
//                        pd.dismiss();
//                        Snackbar.make(findViewById(android.R.id.content), "Image Uploaded", Snackbar.LENGTH_LONG).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        pd.dismiss();
//                        Toast.makeText(getApplicationContext(), "Failed to Upload",Toast.LENGTH_LONG).show();
//
//                    }
//                })
//                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onProgress(@NonNull UploadTask.TaskSnapshot tasksnapshot) {
//                        double progressPercent  = (100.00 * tasksnapshot.getBytesTransferred() / tasksnapshot.getTotalByteCount());
//                        pd.setMessage("Progress: "+ (int) progressPercent + "%");
//                    }
//                });



    //}

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
        Toast.makeText(this, "Alarm Cancelled", Toast.LENGTH_SHORT).show();
    }

    //making a method which will be used to set the alarm
    private void setAlarm() {
        //using AlarmManager
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this,AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

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

                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,picker.getHour());
                calendar.set(Calendar.MINUTE,picker.getMinute());
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND,0);

                //adding task to firebase
                if(pTask.getDocumentId() != null ){
//                    cancelAlarm();

                    taskRef.document(pTask.getDocumentId()).set(calendar);
                  //  taskRef.document(task.getDocumentId()).set()
                }else{
                    DocumentReference addedDocRef = taskRef.document();
                    pTask.setCalendar(calendar); //change
                    pTask.setDocumentId(addedDocRef.getId());
                    addedDocRef.set(pTask);
                }

                //automatically setting alarm
                setAlarm();
                count++;
            }
        });

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