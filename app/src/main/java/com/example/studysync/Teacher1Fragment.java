package com.example.studysync;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Teacher1Fragment extends Fragment {

    RecyclerView recyclerView;
    StudentAdapter studentAdapter;
    List<Student> student=new ArrayList<>();
    DBHelper dbHelper;


    public Teacher1Fragment() {
        // Required empty public constructor
    }

    Button addMore;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View root use karna pda kyuki coordinator layout recycler view ki id ni de rha tha
        View view=inflater.inflate(R.layout.fragment_teacher1,container,false);

        dbHelper=new DBHelper(getContext());

        recyclerView=view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        studentAdapter=new StudentAdapter(getContext(),student);
        recyclerView.setAdapter(studentAdapter);


        addMore=view.findViewById(R.id.add_More);
        addMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), StudentAddActivity.class);
                startActivity(intent);
            }
        });

        LoadStudentName();
        return view;
    }

    private void LoadStudentName(){
        student.clear(); // Clear existing data
        List<Student> name=dbHelper.getAllStudent(); // Retrieve all students from the database
        student.addAll(name); // Add students to the list
        studentAdapter.notifyDataSetChanged();
    }
}