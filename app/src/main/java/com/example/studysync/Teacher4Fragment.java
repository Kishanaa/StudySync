package com.example.studysync;

import static android.content.Context.STORAGE_SERVICE;


import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;

public class Teacher4Fragment extends Fragment {

    public final static int TIMER=1500;
    DBHelper dbHelper;
    public List<Student> students=new ArrayList<>();
    public List<Integer> totalNum=new ArrayList<>();
//    List<String> subject=new ArrayList<>();

    int size;
    RecyclerView recyclerView;
    ResultAdapter resultAdapter;
    RelativeLayout update,resetIt,createExl;
    TextView textView,toastText;
    LottieAnimationView lottieAnimationView,lottieAnimationLoader;
    Toast toastAnimation;



    public Teacher4Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_teacher4,container,false);
        dbHelper=new DBHelper(getContext());
        textView=view.findViewById(R.id.textView);
        lottieAnimationView=view.findViewById(R.id.lottie);
        lottieAnimationLoader=view.findViewById(R.id.lottie2);

        ActivityManager activityManager=(ActivityManager)getContext().getSystemService(Context.ACTIVITY_SERVICE);


        update=view.findViewById(R.id.update);
        resetIt=view.findViewById(R.id.reset_it);

        createExl=view.findViewById(R.id.create_exl);

        recyclerView=view.findViewById(R.id.result_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        toastAnimation=new Toast(getContext());

        //        Convert the layout into View
        View view2=getLayoutInflater().inflate(R.layout.toast_view,view.findViewById(R.id.toastView));
        toastAnimation.setView(view2);
//        set the text in toast
        toastText=view2.findViewById(R.id.toast_text);

//        set Toast duration
        toastAnimation.setDuration(Toast.LENGTH_SHORT);



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                make lottie animation visible
                lottieAnimationView.setVisibility(View.VISIBLE);
                lottieAnimationView.playAnimation();

//                make textView gone
                textView.setVisibility(View.GONE);
//                Handler

                lottieAnimationLoader.setVisibility(View.VISIBLE);
                lottieAnimationLoader.playAnimation();


                new Handler().postDelayed(this::resetButton,TIMER);
                totalNum.clear();
                LoadStudentName();
//                LoadSubjectName();
                SQLiteDatabase db=dbHelper.getReadableDatabase();
                for (int i=1;i<=size;i++){
                    int totalValue=dbHelper.calculateTotalValueForStudent(db,i);
                    totalNum.add(totalValue);
                }
                resultAdapter=new ResultAdapter(getContext(),students,totalNum);

            }

            private void resetButton() {
//                make lottie animation gone
                lottieAnimationView.pauseAnimation();
                lottieAnimationView.setVisibility(View.GONE);
//                make Text visible
                textView.setVisibility(View.VISIBLE);

                lottieAnimationLoader.playAnimation();
                lottieAnimationLoader.setVisibility(View.GONE);
//                set adapter
                recyclerView.setAdapter(resultAdapter);
            }
        });


        resetIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure to clear all data").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean success=activityManager.clearApplicationUserData();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

//        List<String> stud = new ArrayList<>(Arrays.asList("A1", "B2", "C1", "DD", "A"));
//        String[] array4 = stud.toArray(new String[0]);


//        String[] headings = {"NAME", "TOTAL MARKS"};

//        String[] array2 = {"1", "2", "3", "4"};
//        String[] array3 = {"5", "6", "7", "8"};

        createExl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Show a dialog to input the name
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("Enter Name");

//                Set up the input
                final EditText input=new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setHint("Enter file name");
                builder.setView(input);

//                Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

//                        show a dialog to ask for roll number
                        AlertDialog.Builder builder1=new AlertDialog.Builder(getContext());
                        builder1.setTitle("Do you want to enter roll numbers also?");
//                        set up the input
                        final EditText rollNum=new EditText(getContext());
                        rollNum.setInputType(InputType.TYPE_CLASS_TEXT);
                        rollNum.setHint("Enter first roll number");
                        builder1.setView(rollNum);
//                        set up the button
                        builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String num=rollNum.getText().toString();
                                String subNum=num.substring(0,num.length()-2);
                                int index=totalNum.size();
                                String[] rollNumS=new String[index];
                                for (int i=0;i<index-1;i++){
                                    rollNumS[i]=subNum+String.format("%02d", i+1);;
                                }
                                String[] headings = {"Roll Number","NAME", "TOTAL MARKS"};

                                String name = input.getText().toString();
                                createExcel(name, headings,rollNumS, students.stream().map(Student::getName).toArray(String[]::new), totalNum.stream().map(Object::toString).toArray(String[]::new));
                            }
                        });
                        builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String[] headings = {"NAME", "TOTAL MARKS"};
                                String name = input.getText().toString();
                                createExcel(name, headings, students.stream().map(Student::getName).toArray(String[]::new), totalNum.stream().map(Object::toString).toArray(String[]::new));

                            }
                        });
                        builder1.show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
        return view;
    }

    private void createExcel(String name, String[] headings, String[]... data) {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = hssfWorkbook.createSheet("Mysheet");

        // Create a row for headings (row 0)
        HSSFRow headingRow = hssfSheet.createRow(0);
        for (int j = 0; j < headings.length; j++) {
            HSSFCell headingCell = headingRow.createCell(j);
            headingCell.setCellValue(headings[j]);
        }

        int size = totalNum.size();
        // Insert data into columns (starting from row 1)
        for (int i = 0; i < size; i++) {
            HSSFRow dataRow = hssfSheet.createRow(i + 1);
            for (int j = 0; j < data.length; j++) {
                // Check if the current index 'i' is within the bounds of the array 'data[j]'
                if (i < data[j].length) {
                    HSSFCell cell = dataRow.createCell(j);
                    cell.setCellValue(data[j][i]);
                }
            }
        }
        saveWorkBook(name,hssfWorkbook);
    }
    private void saveWorkBook(String name,HSSFWorkbook hssfWorkbook){
        StorageManager storageManager= (StorageManager) getContext().getSystemService(STORAGE_SERVICE);
//        for saving in download folder getStorageVolumes().get(0);
//        here get(0) is for internal storage and internal storage in download folder
        StorageVolume storageVolume=storageManager.getStorageVolumes().get(0);

        File file = new File(storageVolume.getDirectory().getPath()+"/Download/"+name+".xls");

        try {
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            hssfWorkbook.write(fileOutputStream);
            fileOutputStream.close();
            hssfWorkbook.close();

            toastText.setText("File created");
            toastAnimation.show();


        } catch (Exception e) {
            toastText.setText("File not created ");
            toastAnimation.show();
            throw new RuntimeException(e);
        }
    }
    private void LoadStudentName(){
        students.clear(); // Clear existing data
        List<Student> name=dbHelper.getAllStudent(); // Retrieve all students from the database
        students.addAll(name); // Add students to the list
        size=name.size()+1; //We know the size of student in number form
    }
//    private void LoadSubjectName(){
//        subject.clear();
//        int studentID = 1;
//        List<String> name=dbHelper.getAllSubject(studentID);
//        // Add "Name" at the first index
//        subject.add("Name");
//
//        // Add "Total marks" at the second index
//        subject.add("Total Marks");
//
//        // Add all other subject names after the first two indices
//        subject.addAll(name);
//    }

}