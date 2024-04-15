package com.example.studysync;

public class StudentDataId {
    private static int studentId;

    public static int getStudentId() {
        return studentId;
    }

    public static void setStudentId(int id) {
        studentId = id;
    }
}
