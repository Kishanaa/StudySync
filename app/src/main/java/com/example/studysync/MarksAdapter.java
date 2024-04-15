package com.example.studysync;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MarksAdapter extends RecyclerView.Adapter<MarksAdapter.MarksViewHolder> {
    private Context mContext;
    private List<Student> mStudent;



    public MarksAdapter(Context context,List<Student> student){
        mContext=context;
        mStudent=student;
    }

    @NonNull
    @Override
    public MarksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.marks_card,parent,false);
        return new MarksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarksViewHolder holder, int position) {

        Student student=mStudent.get(position);
        holder.mNameTextView.setText(student.getId()+" "+student.getName());

        holder.mAddMarksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Retrieve the current position using holder.getAdapterPosition()
                int currentPosition=holder.getAdapterPosition();
//                NO_POSITION means -1
                if (currentPosition!=RecyclerView.NO_POSITION){
//                    Use the current position
                    Intent intent=new Intent(mContext,MarksDetailActivity.class);
                    intent.putExtra(MarksDetailActivity.EXTRA_STUDENT_ID,currentPosition);
                    mContext.startActivity(intent);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return mStudent.size();
    }

    public class MarksViewHolder extends RecyclerView.ViewHolder{
        public TextView mNameTextView;
        public Button mAddMarksButton;

        public MarksViewHolder(@NonNull View itemView){
            super(itemView);
            mNameTextView=itemView.findViewById(R.id.name_student);
            mAddMarksButton=itemView.findViewById(R.id.add_nums);
        }
    }
}