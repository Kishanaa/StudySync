package com.example.studysync;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Teacher3Fragment extends Fragment {

    SubjectAdapter subjectAdapter;
    RecyclerView recyclerView;
    List<String> subject=new ArrayList<>();
    DBHelper dbHelper;
    Button addSubject;
    Toast toastAnimation;
    TextView toastText;
    EditText subjectName;

    public Teacher3Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_teacher3,container,false);
        dbHelper=new DBHelper(getContext());

        recyclerView=view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        subjectAdapter=new StudentAdapter(getContext(),subject);
        subjectAdapter=new SubjectAdapter(getContext(),subject);
        recyclerView.setAdapter(subjectAdapter);

        toastAnimation=new Toast(getContext());

        //        Convert the layout into View
        View view2=getLayoutInflater().inflate(R.layout.toast_view,view.findViewById(R.id.toastView));
        toastAnimation.setView(view2);
//        set the text in toast
        toastText=view2.findViewById(R.id.toast_text);

//        set Toast duration
        toastAnimation.setDuration(Toast.LENGTH_SHORT);
//        set gravity
        toastAnimation.setGravity(Gravity.TOP|Gravity.RIGHT,0,0);

        subjectName = view.findViewById(R.id.subject_name);
        addSubject = view.findViewById(R.id.add_subject);

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

                    toastText.setText("Subject added successfully");
                    toastAnimation.show();

                } else {
                    toastText.setText("Please enter subject name");
                    toastAnimation.show();
                }
                subjectName.getText().clear();
                LoadSubjectName();
            }
        });


//        addMore=view.findViewById(R.id.add_More);
//        addMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getContext(),SubjectAddActivity.class);
//                startActivity(intent);
//            }
//        });



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadSubjectName(); // Refresh the data when the fragment resumes
    }

    private void LoadSubjectName(){
        subject.clear();
        int studentID = 1;
        List<String> name=dbHelper.getAllSubject(studentID);
        subject.addAll(name);
        subjectAdapter.notifyDataSetChanged();
    }
}