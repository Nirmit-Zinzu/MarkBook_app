package com.nirmit.markbook;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class ChoseGPAType extends AppCompatActivity {

    ImageView cGPA_view, sGPA_view;  //pictures for cGPA and sGPA
    EditText termField, yearField;   // term and year field for sGPA
    finalGPAScale finalGPA;

    int sGPABtnCount = 0;    // stores the amount of time sGPA btn clicked
    private static int year;  // stores the year
    private static String term; // stores the term

    private static double sGPA, cGPA, percentage;
    private static boolean sGPABtn, cGPABtn;
    private static String performanceStatus;   //informs the user about their mark

    // getting updated information from previous activity
    MarkDatabase markDatabase = AddMarks.getMarkDatabase();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_gpatype);

        cGPA_view = (ImageView) findViewById(R.id.cGPA_view);
        sGPA_view = (ImageView) findViewById(R.id.sGPA_view);

        termField = (EditText) findViewById(R.id.termField);
        yearField = (EditText) findViewById(R.id.yearField);

        finalGPA = new finalGPAScale();   // instantiating class

        // term and year fields are invisible until sGPA btn is clicked once
        termField.setVisibility(View.INVISIBLE);
        yearField.setVisibility(View.INVISIBLE);

        cGPACalculate();  // calculates cGPA
        sGPACalculate();  // calculates sGPA

    }


    // calculates cGPA
    public void cGPACalculate() {
        cGPA_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cGPABtn = true;
                calculateGPAMark();  // helper method which calculated cGPA
                // goes to the next activity which displays the marks
                Intent intent = new Intent(ChoseGPAType.this, DisplayGPA.class);
                startActivity(intent);
            }
        });
    }

    // calculates sGPA
    public void sGPACalculate() {
        sGPA_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sGPABtnCount++;
                if (sGPABtnCount == 1) { // term and year filed become visible when sGPA btn is clicked
                    termField.setVisibility(View.VISIBLE);
                    yearField.setVisibility(View.VISIBLE);
                    Toast.makeText(ChoseGPAType.this, " ^^ Enter Year and Term ^^ ", Toast.LENGTH_LONG).show();
                } else {
                    // checking to see if the information entered is valid or not
                    if (getInformation() == true) {

                        sGPABtn = true;

                        calculateSGPAMark(year, term); // helper method which calculates sGPA

                        // takes user to the next activity (DisplayGPA)
                        Intent intent = new Intent(ChoseGPAType.this, DisplayGPA.class);
                        startActivity(intent);

                        termField.setVisibility(View.INVISIBLE);
                        termField.setText("");

                        yearField.setVisibility(View.INVISIBLE);
                        yearField.setText("");

                        sGPABtnCount = 0;
                    } else {
                        Toast.makeText(ChoseGPAType.this, "Invalid Information!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // getting the typed information
    public boolean getInformation() {
        boolean status = false;
        if (!(yearField.getText().toString().matches("")) && !(termField.getText().toString().matches("")) ) {
            year = Integer.parseInt(yearField.getText().toString());
            term = termField.getText().toString().toUpperCase();

            // checking to see the if the information exists
            if (informationExists(year, term) == true) {
                status = true;
            }
        }
        return status;
    }


    // helper method which checks to see if the information exists or not
    public boolean informationExists(int year, String term) {
        boolean status = false;

        // getting all the database
        Cursor cursor = markDatabase.getAllData();
        cursor.moveToFirst();  // moving the cursor to the fist row
        do {
            // checking to see if both year and term exists in the database
            if (cursor.getInt(3) == year && cursor.getString(5).compareToIgnoreCase(term) == 0) {
                status = true;
                break;
            }
            cursor.moveToNext(); // moving the cursor to the next row
        } while (!cursor.isAfterLast());
        return status;
    }

    // calculates cGPa
    public void calculateGPAMark() {
        double totalMark = 0, totalCredit = 0, courseGPA, proportionalPercentage = 0;

        Cursor cursor = markDatabase.getAllData(); // getting all the information from the database
        cursor.moveToFirst();  // moving the cursor to the first row

        do {
            courseGPA = finalGPA.GPAperCourse(cursor.getDouble(4));  // uOfT_GPA(mark)
            totalMark += courseGPA * cursor.getDouble(2);           // e.g 4.0 * 0.5 (uOfT_GPA(mark) * credit)
            totalCredit += cursor.getDouble(2);                      // total credit
            proportionalPercentage += (cursor.getDouble(4) * cursor.getDouble(2));  // (mark * credit)
            cursor.moveToNext();                // moving the cursor to the next row
        } while (!cursor.isAfterLast());

        cGPA = totalMark / totalCredit;                         // final cGPA mark
        percentage = proportionalPercentage / totalCredit;      // final percentage
        performanceLevel();
    }

    public void calculateSGPAMark(int year, String term) {
        double totalMark = 0, totalCredit = 0, courseGPA, proportionalPercentage = 0;

        Cursor cursor = markDatabase.getAllData(); // getting all the information from the database
        cursor.moveToFirst();                // moving the cursor to the first row

        Log.i("aa", "Year: " + year + " mark: " + term);

        do {
            if (cursor.getDouble(3) == year && (cursor.getString(5).compareToIgnoreCase(term) == 0)) {
                courseGPA = finalGPA.GPAperCourse(cursor.getDouble(4));  // uOfT_GPA(mark)
                totalMark += courseGPA * cursor.getDouble(2);           // e.g 4.0 * 0.5 (uOfT_GPA(mark) * credit)
                totalCredit += cursor.getDouble(2);   // total credit
                proportionalPercentage += (cursor.getDouble(4) * cursor.getDouble(2));  // (mark * credit)
            }
            cursor.moveToNext();                // moves the cursor to the next row
        } while (!cursor.isAfterLast());

        sGPA = totalMark / totalCredit;                    // final sGPA
        percentage = proportionalPercentage / totalCredit; // final percentage
        performanceLevel();
    }


    // Following method assigns status based on user's performance
    public void performanceLevel() {
        if (percentage < 50) {
            performanceStatus = finalGPA.FAIL;  // Inadeqate
        } else if (percentage < 60) {
            performanceStatus = finalGPA.GRADE_D;  // Marginal
        } else if (percentage < 70) {
            performanceStatus = finalGPA.GRADE_C;  // Adequate
        } else if (percentage < 80) {
            performanceStatus = finalGPA.GRADE_B; // Good
        } else if (percentage <= 100) {
            performanceStatus = finalGPA.GRADE_A;  // Excellent
        }
    }

    // return sGPA
    public static double getsGPA() {
        return sGPA;
    }

    // return cGPA
    public static double getcGPA() {
        return cGPA;
    }

    //return percentage
    public static double getPercentage() {
        return percentage;
    }

    // return whether of not if sGPA btn was clicked
    public static boolean issGPABtn() {
        return sGPABtn;
    }

    // return whether of not if cGPA btn was clicked
    public static boolean iscGPABtn() {
        return cGPABtn;
    }

    // returns performance status
    public static String getPerformanceStatus() {
        return performanceStatus;
    }

    // setter method - sets the status of sGPA btn
    public static void setsGPABtn(boolean sGPABtn) {
        ChoseGPAType.sGPABtn = sGPABtn;
    }

    // setter method - sets the status of cGPA btn
    public static void setcGPABtn(boolean cGPABtn) {
        ChoseGPAType.cGPABtn = cGPABtn;
    }

}


// actiong bar - tut
/* // Creating a menu option on the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gpa_menu, menu);
        return true;
    }

    // Takes an appropriate actions based on the selected item from the action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.gpaScale) {
            GPA_Scale_Set();
        }

        return true;
    }

    public void GPA_Scale_Set(){
       Intent intent = new Intent(ChoseGPAType.this, GPAScaleSet.class);
        startActivity(intent);
    }*/