package com.example.studysync;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StudentAddActivity extends AppCompatActivity {

    EditText studentName;
    Button addStudent;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add);

        studentName=findViewById(R.id.student_name);
        addStudent=findViewById(R.id.add_student);
        dbHelper=new DBHelper(this);

        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Retrieve student name from EditText
                String name=studentName.getText().toString().trim();

                if (!name.isEmpty()){
//                    Open database in write mode
                    SQLiteDatabase db=dbHelper.getWritableDatabase();

//                    Insert student name into Students table
                    ContentValues values=new ContentValues();
                    values.put("name",name);
                    long newRowId=db.insert("Students",null,values);

//                    Close database connection
                    db.close();

//                    Show toast message indicating success or failure
                    if ( newRowId!= -1){
                        Toast.makeText(StudentAddActivity.this,"Student added successfully",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(StudentAddActivity.this,"Failed to add student",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(StudentAddActivity.this,"Please enter student name",Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }
}