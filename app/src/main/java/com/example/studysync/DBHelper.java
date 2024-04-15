package com.example.studysync;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="students.db";
    private static final int DATABASE_VERSION=1;

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

//         Create the "Students" table with fixed columns
        String createStudentTableQuery="CREATE TABLE Students (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "Name TEXT)";
        db.execSQL(createStudentTableQuery);

//        Create the "SubjectValues" table to store dynamic columns and values
        String createSubjectValuesTableQuery="CREATE TABLE SubjectValues("+
                "StudentID INTEGER,"+
                "SubjectName TEXT,"+
                "Value INTEGER,"+
                "FOREIGN KEY(StudentID) REFERENCES Students(ID))";
        db.execSQL(createSubjectValuesTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


//    get how many student entered in database
    public int getStudentCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM Students", null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }
//    Method to retrieve all students from the database
    public List<Student> getAllStudent(){
        List<Student> students=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM Students",null);
        if (cursor!=null && cursor.moveToFirst()){
            int idIndex=cursor.getColumnIndex("ID");
            int nameIndex=cursor.getColumnIndex("Name");
            do {
                if (idIndex!=-1 && nameIndex!=-1){
                    int id=cursor.getInt(idIndex);
                    String name=cursor.getString(nameIndex);
                    students.add(new Student(id,name));
                }else {
                    Log.e("DBHelper", "Column not found: " + "Name");

                }
            }while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return students;
    }

//    public List<Subjects> getAllSubject(){
//        List<Subjects> subjects=new ArrayList<>();
//        SQLiteDatabase db=this.getWritableDatabase();
//        Cursor cursor=db.rawQuery("SELECT * FROM SubjectValues",null);
//        if (cursor!=null && cursor.moveToFirst()){
//            int subjectIndex=cursor.getColumnIndex("SubjectName");
//            do {
//                if (subjectIndex!=-1){
//                    String subject=cursor.getString(subjectIndex);
//                    subjects.add(new Subjects(subject));
//                }else {
//                    Log.e("DBHelper", "Column not found: " + "SubjectName");
//                }
//            }
//            while (cursor.moveToNext());
//            cursor.close();
//        }
//        db.close();
//        return subjects;
//    }

    public List<String> getAllSubject(int studentID) {
        List<String> subjects = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to select subject names for a specific student ID
        String query = "SELECT SubjectName FROM SubjectValues WHERE StudentID = ?";

        // Arguments for the query
        String[] selectionArgs = {String.valueOf(studentID)};

        // Execute the query
        Cursor cursor = db.rawQuery(query, selectionArgs);

        // Check if the cursor is not null and move to the first row
        if (cursor != null && cursor.moveToFirst()) {
            // Get the column index of the "SubjectName" column
            int subjectNameIndex = cursor.getColumnIndex("SubjectName");

            // Iterate through the cursor to extract subject names
            do {
                String subjectName = cursor.getString(subjectNameIndex);
                subjects.add(subjectName);
            } while (cursor.moveToNext());
        }

        // Close the cursor
        cursor.close();
        return subjects;
    }

//    get specific Student name
    public String getStudentName(int studentId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String studentName = null;

        Cursor cursor = db.rawQuery("SELECT Name FROM Students WHERE ID = ?", new String[]{String.valueOf(studentId)});
        if (cursor != null && cursor.moveToFirst()) {
            int nameId=cursor.getColumnIndex("Name");
            if (nameId!=-1){
                studentName = cursor.getString(nameId);
            }else {
                Log.e("DBHelper", "Column not found: " + "Name");
            }
            cursor.close();
        }
        db.close();

        return studentName;
    }

//    public List<SubjectAndValues> getAllSubjectStudent(){
//        List<SubjectAndValues> subjectAndValues=new ArrayList<>();
//        SQLiteDatabase db=this.getReadableDatabase();
//
//        Cursor cursor=db.rawQuery("SELECT * FROM SubjectValues",null);
//
//        if (cursor!=null && cursor.moveToFirst()){
//            int idIndex=cursor.getColumnIndex("StudentID");
//            int nameIndex=cursor.getColumnIndex("SubjectName");
//            int marksIndex=cursor.getColumnIndex("Value");
//            do {
//                if (idIndex!=-1 && nameIndex!=-1){
//                    int id=cursor.getInt(idIndex);
//                    String name=cursor.getString(nameIndex);
//                    int marks=cursor.getInt(marksIndex);
//                    subjectAndValues.add(new SubjectAndValues(id,name,marks));
//                }else {
//                    Log.e("DBHelper", "Column not found: " + "Name");
//
//                }
//            }while (cursor.moveToNext());
//            cursor.close();
//        }
//        db.close();
//        return subjectAndValues;
//    }

    public List<SubjectAndValues> getAllSubjectStudent(int studentID) {
        List<SubjectAndValues> subjectsWithValues = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to select SubjectName and Value for a specific student ID
        String query = "SELECT SubjectName, Value FROM SubjectValues WHERE StudentID = ?";

        // Arguments for the query
        String[] selectionArgs = {String.valueOf(studentID)};

        // Execute the query
        Cursor cursor = db.rawQuery(query, selectionArgs);

        // Check if the cursor is not null and move to the first row
        if (cursor != null && cursor.moveToFirst()) {
            // Get the column indexes of "SubjectName" and "Value" columns
            int subjectNameIndex = cursor.getColumnIndex("SubjectName");
            int valueIndex = cursor.getColumnIndex("Value");

            // Iterate through the cursor to extract subject names and values
            do {
                String subjectName = cursor.getString(subjectNameIndex);
                int value = cursor.getInt(valueIndex);
                SubjectAndValues subjectAndValues = new SubjectAndValues(studentID, subjectName, value);
                subjectsWithValues.add(subjectAndValues);
            } while (cursor.moveToNext());
        }

        // Close the cursor
        cursor.close();
        return subjectsWithValues;
    }

    // Method to update subject marks for a specific student
    public Boolean updateSubjectMarks(int studentID, String subjectName, int marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Value", marks);

        long result=db.update("SubjectValues", values, "StudentID = ? AND SubjectName = ?", new String[]{String.valueOf(studentID), subjectName});
        if (result==-1){
            return false;
        }else {
            return true;
        }
    }

    public boolean updateData(int studentId, String subjectName, int value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("StudentID", studentId);
        contentValues.put("SubjectName", subjectName);
        contentValues.put("Value", value);

        String whereClause = "StudentID=? AND SubjectName=?";
        String[] whereArgs = new String[] {String.valueOf(studentId), subjectName};

        int rowsAffected = db.update("SubjectValues", contentValues, whereClause, whereArgs);

        return rowsAffected > 0;
    }

    public Boolean addSubjectMarks(int studentID, String subjectName, int marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("StudentID", studentID);
        values.put("SubjectName", subjectName);
        values.put("Value", marks);

        long result=db.insert("SubjectValues", null, values);
        if (result==-1){
            return false;
        }else {
            return true;
        }
    }

    public void insertData(int studentId, String subjectName, int value) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("StudentID", studentId);
            contentValues.put("SubjectName", subjectName);
            contentValues.put("Value", value);

            long result = db.insert("SubjectValues", null, contentValues);

            if (result == -1) {
                Log.d("DBHelper", "Insert failed");
            } else {
                Log.d("DBHelper", "Insert successful");
            }
        } catch (Exception e) {
            Log.e("DBHelper", "Error while inserting data", e);
        }
    }

    public Cursor getData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM SubjectValues",null);
        return cursor;
    }

    public int calculateTotalValueForStudent(SQLiteDatabase db, int studentID) {
        int totalValue = 0;
        String[] columns = {"Value"};
        String selection = "StudentID=?";
        String[] selectionArgs = {String.valueOf(studentID)};
        Cursor cursor = db.query("SubjectValues", columns, selection, selectionArgs, null, null, null);
        if (cursor != null) {
            int valueIndex = cursor.getColumnIndex("Value");
            if (valueIndex != -1 && cursor.moveToFirst()) {
                do {
                    int value = cursor.getInt(valueIndex);
                    totalValue += value;
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return totalValue;
    }

}
