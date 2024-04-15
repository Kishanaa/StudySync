package com.example.studysync;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {
    private Context context;
    private List<Integer> num;
    private List<Student> mStudent;

    public ResultAdapter(Context context,List<Student> student,List<Integer> num){
        this.context=context;
        this.num=num;
        mStudent=student;
    }


    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.result_card,parent,false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        Student student=mStudent.get(position);
        holder.NameTextView.setText(student.getId()+" "+student.getName());
        Integer integer=num.get(position);
        holder.MarkTextView.setText(integer.toString());

    }

    @Override
    public int getItemCount() {
        return mStudent.size();
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder {

        public TextView NameTextView;
        public TextView MarkTextView;
        public ResultViewHolder(@NonNull View itemView) {

            super(itemView);
            NameTextView=itemView.findViewById(R.id.student_name_result);
            MarkTextView=itemView.findViewById(R.id.total_marks_result);
        }
    }
}
