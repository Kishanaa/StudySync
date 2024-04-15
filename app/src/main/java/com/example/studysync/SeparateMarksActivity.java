package com.example.studysync;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class SeparateMarksActivity extends AppCompatActivity {

    public static final String SUBJECT_NAME_ID="studentId";

    EditText subjectMark;
    Button updateMark;
    DBHelper dbHelper;
    Toast toastAnimation;
    TextView toastText;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_separate_marks);

        subjectMark=findViewById(R.id.subject_mark);
        updateMark=findViewById(R.id.update_mark);
        dbHelper=new DBHelper(this);

        toastAnimation=new Toast(this);

        //        Convert the layout into View
        View view2=getLayoutInflater().inflate(R.layout.toast_view,(ViewGroup) findViewById(R.id.toastView));
        toastAnimation.setView(view2);
//        set the text in toast
        toastText=view2.findViewById(R.id.toast_text);

//        set Toast duration
        toastAnimation.setDuration(Toast.LENGTH_SHORT);
        toastAnimation.setGravity(Gravity.TOP|Gravity.RIGHT,0,0);




        int studentId = StudentDataId.getStudentId()+1;

        String subjectName=getIntent().getStringExtra(SUBJECT_NAME_ID);



        updateMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mark = Integer.parseInt(subjectMark.getText().toString());

                // Update the subject marks using DBHelper
                boolean updated = dbHelper.updateData(studentId, subjectName, mark);

                if (updated) {
                    toastText.setText("Entry updated");
                    toastAnimation.show();
                } else {
                    toastText.setText("Entry not updated");
                    toastAnimation.show();
                }
                finish(); // Close the activity
            }
        });
    }
}