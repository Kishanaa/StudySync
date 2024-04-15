package com.example.studysync;

public class SubjectAndValues {

    private int id;
    private String subject;
    private int marks;

    public SubjectAndValues(int id,String subject,int marks){
        this.id=id;
        this.subject=subject;
        this.marks=marks;
    }

    public int getMarks() {
        return marks;
    }

    public String getSubject() {
        return subject;
    }
}
