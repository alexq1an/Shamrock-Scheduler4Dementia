package com.example.shamrock;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class patient_homepage extends AppCompatActivity {
    TextView month,day,year;
    @Override
    // present date-year-month
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_homepage);

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

    }
}
