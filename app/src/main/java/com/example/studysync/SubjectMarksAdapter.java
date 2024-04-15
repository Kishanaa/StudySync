package com.example.studysync;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SubjectMarksAdapter extends RecyclerView.Adapter<SubjectMarksAdapter.SubjectMarksViewHolder> {
    private List<SubjectAndValues> subjectAndValues;

    public SubjectMarksAdapter(List<SubjectAndValues> subjectAndValues) {
        this.subjectAndValues=subjectAndValues;
    }

    @NonNull
    @Override
    public SubjectMarksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marks_detail_card, parent, false);
        return new SubjectMarksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectMarksViewHolder holder, int position) {
        SubjectAndValues subjectAndValue=subjectAndValues.get(position);
        holder.subjectTextView.setText(subjectAndValue.getSubject());
        holder.marksTextView.setText(String.valueOf(subjectAndValue.getMarks()));

        // You can set listeners for EditTexts here
        Context context=holder.marksTextView.getContext();
        holder.marksTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, SeparateMarksActivity.class);
                intent.putExtra(SeparateMarksActivity.SUBJECT_NAME_ID,subjectAndValue.getSubject());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectAndValues.size();
    }

    public class SubjectMarksViewHolder extends RecyclerView.ViewHolder {
        TextView subjectTextView;
        TextView marksTextView;

        public SubjectMarksViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectTextView = itemView.findViewById(R.id.subjects_name);
            marksTextView = itemView.findViewById(R.id.enter_marks);
        }
    }

    public void updateData(List<SubjectAndValues> newData) {
        subjectAndValues.clear(); // Clear existing data
        subjectAndValues.addAll(newData); // Add new data
        notifyDataSetChanged(); // Notify adapter about dataset change
    }
}
