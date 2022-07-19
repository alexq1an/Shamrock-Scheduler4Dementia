package com.example.shamrock;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.example.shamrock.databinding.ActivityMain3Binding;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;



public class MainActivity3 extends AppCompatActivity {
    private Button setting;
    private Button patientTask;
    private Button testing_button;
    private CollectionReference cRef = FirebaseFirestore.getInstance().collection("Caregiver");

    public ActivityMain3Binding binding;

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
                openActivity4();
            }
        });
//        testing_button = (Button) findViewById(R.id.testingbt);
//        testing_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                    testing();
//            }
//        });

        //below are patient info List on caregiver's side.
        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int[] imageId = {R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,
                R.drawable.f,R.drawable.g,R.drawable.h,R.drawable.i};
        String[] name = {"Christopher","Craig","Sergio","Mubariz","Mike","Michael","Toa","Ivana","Alex"};
        String[] lastMessage = {"Heye","Supp","Let's Catchup","Dinner tonight?","Gotta go",
                "i'm in meeting","Gotcha","Let's Go","any Weekend Plans?"};
        String[] lastmsgTime = {"8:45 pm","9:00 am","7:34 pm","6:32 am","5:76 am",
                "5:00 am","7:34 pm","2:32 am","7:76 am"};
        String[] phoneNo = {"7656610000","9999043232","7834354323","9876543211","5434432343",
                "9439043232","7534354323","6545543211","7654432343"};
        String[] country = {"United States","Russia","India","Israel","Germany","Thailand","Canada","France","Switzerland"};

        ArrayList<User> userArrayList = new ArrayList<>();

        for(int i = 0;i< imageId.length;i++){

            User user = new User(name[i],lastMessage[i],lastmsgTime[i],phoneNo[i],country[i],imageId[i]);
            userArrayList.add(user);

        }

        ListAdapter listAdapter = new ListAdapter(this,userArrayList);

        binding.patientsListView.setAdapter(listAdapter);
        binding.patientsListView.setClickable(true);
        binding.patientsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(MainActivity3.this,MainActivity4.class);
                i.putExtra("name",name[position]);
                i.putExtra("phone",phoneNo[position]);
                i.putExtra("country",country[position]);
                i.putExtra("imageid",imageId[position]);
                startActivity(i);

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