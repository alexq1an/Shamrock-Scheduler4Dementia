package com.example.shamrock;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shamrock.databinding.ActivityMain3Binding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * This is the home page for patient.
 * It shows the list of tasks being scheduled on the current day.
 */
public class patient_homepage extends AppCompatActivity {
    // Create firebase references
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference pRef = db.collection("Patient");

    //define variables for showing the task list
    private static final String TAG = "patient_homepage";
    public DocumentReference pDocId;
    public CollectionReference scheduleRef;
    public String todayId;
    public CollectionReference taskRef;

    public ArrayList<Task> taskList = new ArrayList<>();
    public ActivityMain3Binding binding;

//    private DocumentReference newRef = db.collection("Patient").document("4VqwOvfFqCD0HiRZWknB")
//            .collection("Schedule").document("lMuGUFBBPGE1BcyiWfRS");


//    TextView month,day,year;
    TextView day;
    @Override
    // present date-year-month
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_homepage);

        //grabbing documentId passed from MainActivity7
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            pDocId = pRef.document(extras.get("documentId").toString());
        }

//        month = findViewById(R.id.month);
        day =findViewById(R.id.day);
//        year=findViewById(R.id.year);



        Date currentTime = Calendar.getInstance().getTime();
        String formattedDate = DateFormat.getInstance ().format(currentTime);

        String[] splitDate = formattedDate.split(",");
        Log.d("my Log",currentTime.toString());
        Log.d("my Log",formattedDate);
//        month.setText(splitDate[1]);
        day.setText(splitDate[0]);
//        year.setText(splitDate[2]);

        Log.d("my Log",splitDate[0].trim());
//        Log.d("my Log",splitDate[1].trim());
//        Log.d("my Log",splitDate[2].trim());

        showTask(pDocId);//call this method to show the task list
    }

/**
 * Set the list of tasks and show
 * */
    public void showTask(DocumentReference pDocId) {
        //access collection "Schedule"
        /*
        * Getting null pointer
        * */
        scheduleRef = pDocId.collection("Schedule");

        //check if the schedule date matches with the current date
        //get the present date documentId
        scheduleRef
                .whereEqualTo("year", 2022)//need generalization
                .whereEqualTo("month", 6)
                .whereEqualTo("day", 31)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {//successfully got access to a specific date's schedule reference
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                todayId = document.getId();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        //access collection "Task"
        taskRef = scheduleRef.document(todayId).collection("Task");

        //iterate through Task collection and grab all the documents(tasks) inside
        taskRef
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //manually add each task into a task list
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                Task aTask = document.toObject(Task.class);//creates a Task object
                                taskList.add(aTask);
                                //use ListAdaptor2 to show it on the screen(patient_homepage.xml)
                                ListAdapter2 listAdapter = new ListAdapter2(patient_homepage.this, taskList);
                                binding.patientsListView.setAdapter(listAdapter);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
