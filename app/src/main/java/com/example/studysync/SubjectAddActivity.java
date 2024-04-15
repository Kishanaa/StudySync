package com.example.studysync;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class SubjectAddActivity extends AppCompatActivity {

    EditText subjectName;
    Button addSubject;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_add);

        subjectName = findViewById(R.id.subject_name);
        addSubject = findViewById(R.id.add_subject);
        dbHelper = new DBHelper(this);

        addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = subjectName.getText().toString().trim();

                if (!name.isEmpty()) {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();

                    int num = dbHelper.getStudentCount();
                    for (int i = 1; i <= num; i++) {
                        values.put("subjectName", name);
                        values.put("StudentID", i);
                        values.put("Value", 0);
                        db.insert("SubjectValues", null, values);

                    }
                    db.close();

                } else {
                    Toast.makeText(SubjectAddActivity.this, "Please enter student name", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }
}