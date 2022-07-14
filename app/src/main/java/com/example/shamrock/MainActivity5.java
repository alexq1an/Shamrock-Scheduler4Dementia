package com.example.shamrock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity5 extends AppCompatActivity {
    private Button task_set_confirm_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        task_set_confirm_button = (Button) findViewById(R.id.task_set_confirm_button);
        task_set_confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backActivity4();
            }
        });
    }
    public void backActivity4(){
        Intent intent = new Intent(this,MainActivity4.class);
        startActivity(intent);
    }
}