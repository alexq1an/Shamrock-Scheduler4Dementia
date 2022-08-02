package com.example.shamrock;
//importing all the required libraries
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;

//making AlarmReceiver class
public class AlarmReceiver extends BroadcastReceiver {
    String patientID;
    String scheduleID;
    String taskID;
    @Override
    //this method will specifically be used to receive the notification
    //when the alarm rings
    public void onReceive(Context context, Intent intent) {
        //Alarm receiver helps the user to take them to the notification activity
        //page after the alarm is stopped by the user
        Bundle extras = intent.getExtras();
        if(extras != null) {
            patientID = extras.get("patientDocId").toString();
            scheduleID = extras.get("scheduleDocId").toString();
            taskID = extras.get("taskDocID").toString();
        }

        Intent i = new Intent(context,MainActivity10.class);
        i.putExtra("patientDocId", patientID);
        i.putExtra("scheduleDocId", scheduleID);
        i.putExtra("taskDocId", taskID);
        //setting the required flags
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i,0);
        //building the notification
        //which will be sent to the patient when the alarm rings
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"Shamrock")
                .setSmallIcon(R.drawable.ic_launcher_background)
                //name of the notification
                .setContentTitle("Shamrock Alarm Manager")
                //giving instruction to stop the alarm
                .setContentText("Click on this to stop the alarm")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123,builder.build());

    }

    public void setStrings(String PatientID, String ScheduleID, String TaskID){
        this.patientID = PatientID;
        this.scheduleID = ScheduleID;
        this.taskID = TaskID;
    }
}