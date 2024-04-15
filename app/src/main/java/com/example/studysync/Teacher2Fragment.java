package com.example.studysync;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Teacher2Fragment extends Fragment {

    RecyclerView recyclerView;
    DBHelper dbHelper;
    MarksAdapter marksAdapter;
    List<Student> students=new ArrayList<>();

    public Teacher2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_teacher2,container,false);

        dbHelper=new DBHelper(getContext());

        recyclerView=view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        marksAdapter=new MarksAdapter(getContext(),students);
        recyclerView.setAdapter(marksAdapter);

        LoadStudentName();



        return view;
    }

    private void LoadStudentName(){
        students.clear(); // Clear existing data
        List<Student> name=dbHelper.getAllStudent(); // Retrieve all students from the database
        students.addAll(name); // Add students to the list
        marksAdapter.notifyDataSetChanged();
    }
}