package com.example.studysync;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.start);

//        check if the activity has been show before
        SharedPreferences sharedPreferences=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        boolean isFirstTime=sharedPreferences.getBoolean("isFirstTime",true);

        if (isFirstTime){
//            If its first time, start the AddStudentNameActivity and mark it as shown
            SharedPreferences.Editor editor=sharedPreferences.edit();


            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.putBoolean("isFirstTime",false);
                    editor.apply();
                    startActivity(new Intent(MainActivity.this, AddStudentNameActivity.class));
                }
            });
        }else {
//            If not the first time, proceed with your normal flow
            startActivity(new Intent(MainActivity.this,TeacherMainActivity.class));
            finish();
        }

    }
}