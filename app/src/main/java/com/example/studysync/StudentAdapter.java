package com.example.studysync;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private Context mContext;
    private List<Student> mStudent;


    public StudentAdapter(Context context,List<Student> student){
        mContext=context;
        mStudent=student;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.student_card,parent,false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student=mStudent.get(position);
        holder.mNameTextView.setText(student.getName());
    }

    @Override
    public int getItemCount() {
        return mStudent.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder{
        public TextView mNameTextView;

        public StudentViewHolder(@NonNull View itemView){
            super(itemView);
            mNameTextView=itemView.findViewById(R.id.student_name);
        }
    }
}
