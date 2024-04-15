package com.example.studysync;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class MarksDetailActivity extends AppCompatActivity {

    public static final String EXTRA_STUDENT_ID="studentId";

    DBHelper dbHelper;
    RecyclerView recyclerView;
    SubjectMarksAdapter adapter;

    Button save;
    Toast toastAnimation;

    TextView studentName,toastText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks_detail);

        int studentId = getIntent().getIntExtra(EXTRA_STUDENT_ID, -1);

        StudentDataId.setStudentId(studentId);
        toastAnimation=new Toast(this);
        //        Convert the layout into View
        View view2=getLayoutInflater().inflate(R.layout.toast_view,(ViewGroup) findViewById(R.id.toastView));
        toastAnimation.setView(view2);
        //        set the text in toast
        toastText=view2.findViewById(R.id.toast_text);
        //        set Toast duration
        toastAnimation.setDuration(Toast.LENGTH_SHORT);

        toastAnimation.setGravity(Gravity.TOP|Gravity.RIGHT,0,0);




        dbHelper=new DBHelper(this);
        studentName=findViewById(R.id.student_name);

//        Retrieve the name of the student using the studentId
        String studentNameText= dbHelper.getStudentName(studentId+1);
//        Set the retrieved name in the TextView
        studentName.setText(studentNameText);

        List<SubjectAndValues> subjects=dbHelper.getAllSubjectStudent(studentId+1);

        recyclerView=findViewById(R.id.recycler_marks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new SubjectMarksAdapter(subjects);
        recyclerView.setAdapter(adapter);

        save=findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastText.setText("Saved");
                toastAnimation.show();
                finish();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the page when it resumes
        int studentId = StudentDataId.getStudentId();
        List<SubjectAndValues> subjects = dbHelper.getAllSubjectStudent(studentId+1);
        adapter.updateData(subjects); // Update the data in the adapter
    }
}