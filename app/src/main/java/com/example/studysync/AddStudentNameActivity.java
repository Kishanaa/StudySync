package com.example.studysync;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

public class AddStudentNameActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    StudentAdapter studentAdapter;
    List<Student> student=new ArrayList<>();
    DBHelper dbHelper;
    Button addStudent;
    EditText studentName;

    Toast toastAnimation;

    public final static int TIMER=1000;
    RelativeLayout addMore;
    TextView textView,toastText;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_name);

        studentName=findViewById(R.id.student_name);
        addStudent=findViewById(R.id.add_More);
        dbHelper=new DBHelper(this);
        addMore=findViewById(R.id.more_add);
        lottieAnimationView=findViewById(R.id.lottie);

        textView=findViewById(R.id.textView);


        toastAnimation=new Toast(this);

        //        Convert the layout into View
        View view=getLayoutInflater().inflate(R.layout.toast_view,findViewById(R.id.toastView));
        toastAnimation.setView(view);
//        set the text in toast
        toastText=view.findViewById(R.id.toast_text);

//        set Toast duration
        toastAnimation.setDuration(Toast.LENGTH_SHORT);
        toastAnimation.setGravity(Gravity.CENTER,0,0);

        recyclerView=findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentAdapter=new StudentAdapter(this,student);
        recyclerView.setAdapter(studentAdapter);

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
                        toastText.setText("Student added successfully");
                        toastAnimation.show();
                        LoadStudentName();

                    }else {
                        toastText.setText("Failed to add student");
                        toastAnimation.show();
                    }
                }else {
                    toastText.setText("Please enter student name");
                    toastAnimation.show();
                }
                studentName.getText().clear();
            }
        });
//        LoadStudentName();

        addMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                make lottie animation visible
                lottieAnimationView.setVisibility(View.VISIBLE);
                lottieAnimationView.playAnimation();

//                make textView gone
                textView.setVisibility(View.GONE);
//                Handler
                new Handler().postDelayed(this::resetButton,TIMER);
                //                Show the toast
//                toastAnimation.show();
            }

            private void resetButton() {
//                make lottie animation gone
                lottieAnimationView.pauseAnimation();
                lottieAnimationView.setVisibility(View.GONE);
//                make Text visible
                textView.setVisibility(View.VISIBLE);

//                start new activity
                startActivity(new Intent(AddStudentNameActivity.this,TeacherMainActivity.class));
            }
        });

    }
    private void LoadStudentName(){
        student.clear(); // Clear existing data
        List<Student> name=dbHelper.getAllStudent(); // Retrieve all students from the database
        student.addAll(name); // Add students to the list
        studentAdapter.notifyDataSetChanged();
    }
}