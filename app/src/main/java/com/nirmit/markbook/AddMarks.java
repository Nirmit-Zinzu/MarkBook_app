package com.nirmit.markbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AddMarks extends AppCompatActivity {

    private static MarkDatabase markDatabase; //SQlite database


    RelativeLayout databaseScreen;  //Following layout contains widgets that get user information

    //Display Widgets
    EditText courseText, creditText, yearText, markText, idNumber;
    Button addDataBut, viewAllDataBut, updateDataBut, deleteBtn, deleteAllBtn, gpaBtn;
    TextView idNumberText;
    Spinner termList;   //Provides term options (Fall, Winter, Summer)

    String terms[] = {"FALL", "WINTER", "SUMMER"};      //terms

    //conter variable for update and delete database method
    int updateCount = 0, deleteCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_marks);  //xml layout

        markDatabase  = new MarkDatabase(this);   //instantiating database

        databaseScreen = (RelativeLayout) findViewById(R.id.databaseScreen);

        idNumberText = (TextView) findViewById(R.id.uniqueID); // id number title

        courseText = (EditText) findViewById(R.id.courseEditText);  // course
        creditText = (EditText) findViewById(R.id.creditEditText);  // credit
        yearText = (EditText) findViewById(R.id.yearEditText);      // year
        markText = (EditText) findViewById(R.id.markEditText);      // mark
        idNumber = (EditText) findViewById(R.id.idText);            // id

        addDataBut = (Button) findViewById(R.id.markAddButton);      // add data btn
        viewAllDataBut = (Button) findViewById(R.id.showDataButton); // view data btn
        updateDataBut = (Button) findViewById(R.id.updateDataButton); // update specific data btn
        deleteBtn = (Button) findViewById(R.id.deleteButton);         // delete specific data btn
        deleteAllBtn = (Button) findViewById(R.id.deleteAllBtn);      // delete all data btn
        gpaBtn = (Button) findViewById(R.id.gpaButton);               // calculate gpa btn

        // currently id text view and id field is invisible (displayed when update or delete btn clicked)
        idNumber.setVisibility(View.INVISIBLE);
        idNumberText.setVisibility(View.INVISIBLE);

        termList = (Spinner) findViewById(R.id.termList);   // term list options

        // array adapter for term list
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, terms);
        termList.setAdapter(adapter);

        addData();     // adds data to the database
        viewAllData(); // allows user to view all the entries
        updateData();  // allows user to update any information from the database
        deleteData();  // allows user to delete information from the database
        deleteAllData(); // deletes all the database
        calculateGPA();  // takes user to the next screen (chose cGPA or sGPA)
    }


    // following method is run when Add button is pressed
    public void addData() {
        addDataBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // checks to see if all the provided entries filled or not
                if (allDataFill() == true) {

                    // insets data into the database class
                    boolean isInserted = markDatabase.insertData(courseText.getText().toString(),
                            creditText.getText().toString(),
                            yearText.getText().toString(),
                            markText.getText().toString(),
                            terms[termList.getSelectedItemPosition()]);

                    if (isInserted == true) {
                        Toast.makeText(AddMarks.this, "Data Inserted!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(AddMarks.this, "Data NOT Inserted!", Toast.LENGTH_LONG).show();
                    }
                }
            }

        });
    }

    // following method is run when update btn is clicked
    public void updateData() {
        updateDataBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking to see if the mark is within 0% to 100%

                // if no data -> user cannot update anything
                if (markDatabase.getAllData().getCount() == 0) {
                    Toast.makeText(AddMarks.this, "No data to update!", Toast.LENGTH_SHORT).show();

                } else {  // there is some content stored in the database
                    updateCount++;
                    if (updateCount == 1) {  // When update btn is clicked first time, ID text and label become visible
                        idNumberText.setVisibility(View.VISIBLE);
                        idNumber.setVisibility(View.VISIBLE);

                        Toast.makeText(AddMarks.this, "Please Enter ID number corresponding to the course.",
                                Toast.LENGTH_LONG).show();
                    } else {
                        if (allDataFill() == true) { // all entries filled by the user

                            // updates the database
                            boolean isUpdate = markDatabase.updateData(idNumber.getText().toString(),
                                    courseText.getText().toString(),
                                    creditText.getText().toString(),
                                    yearText.getText().toString(),
                                    markText.getText().toString(),
                                    terms[termList.getSelectedItemPosition()]);

                            if (isUpdate == true) {
                                Toast.makeText(AddMarks.this, "Data Updated!", Toast.LENGTH_LONG).show();
                                idNumber.setVisibility(View.INVISIBLE);
                                idNumberText.setVisibility(View.INVISIBLE);
                                updateCount = 0;
                                idNumber.setText("");
                            } else {
                                Toast.makeText(AddMarks.this, "Unknown ID", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            }
        });
    }

    // following methods is called when delete btn is clicked
    public void deleteData() {
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if no data -> user cannot update anything
                if(markDatabase.getAllData().getCount() == 0) {
                    Toast.makeText(AddMarks.this, "No data to delete!", Toast.LENGTH_LONG).show();

                }  else {  // database contains information
                    deleteCount++;

                    // when delete btn is clicked, first time, id text and field come visible
                    if (deleteCount == 1) {
                        idNumberText.setVisibility(View.VISIBLE);
                        idNumber.setVisibility(View.VISIBLE);

                        databaseScreen.setVisibility(View.INVISIBLE);  // top half of the screen becomes invisible

                        Toast.makeText(AddMarks.this, "Please Enter ID number corresponding to the course.",
                                Toast.LENGTH_LONG).show();
                    } else {
                        // deletes a row
                        Integer deletedRows = markDatabase.deleteData(idNumber.getText().toString());
                        if (deletedRows > 0) {
                            Toast.makeText(AddMarks.this, "Data Deleted!", Toast.LENGTH_LONG).show();
                            idNumber.setVisibility(View.INVISIBLE);
                            idNumberText.setVisibility(View.INVISIBLE);
                            databaseScreen.setVisibility(View.VISIBLE);
                            deleteCount = 0;
                            idNumber.setText("");
                        } else {
                            Toast.makeText(AddMarks.this, "Unknown ID", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
    }

    // following method is called when delete all btn is clicked
    public void deleteAllData() {
        deleteAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // building a alert dialog
                AlertDialog.Builder builder1 = new AlertDialog.Builder(AddMarks.this);
                builder1.setMessage("Delete ALL data?");
                builder1.setCancelable(true);  // allows user to go back

                // following code is run when 'Yes' option selected
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                markDatabase.deleteAll();   // deletes all the information
                            }
                        });

                // following code is run when 'No' option selected
                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();      // goes back
                            }
                        });

                AlertDialog alert11 = builder1.create(); // creating the dialog
                alert11.show();     // displaying

            }
        });
    }

    // following method is ten when 'GPA' btn is clicked
    public void calculateGPA() {
        gpaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting all the information
                Cursor cursor = markDatabase.getAllData();

                if (!cursor.moveToFirst()) { // checks to see if the database empty or now
                    Toast.makeText(AddMarks.this, "Empty Database!", Toast.LENGTH_SHORT).show();
                } else { // takes user to the next activity (ChoseGPAType)
                    Intent intent = new Intent(AddMarks.this, GPAScaleSet.class);
                    startActivity(intent);
                }
            }
        });
    }


    // Following methods is run when view btn is clicked
    public void viewAllData() {
        viewAllDataBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor res = markDatabase.getAllData();  // getting all the information

                if (res.getCount() == 0) {   // if no data ...
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddMarks.this);
                    builder.setCancelable(true);
                    builder.setTitle("Error");
                    builder.setMessage("No Data Found!");
                    builder.show();
                    return;
                }

                // if data is present, new activity is displayed (ShowListActivity)
                Intent intent = new Intent(AddMarks.this, ShowListActivity.class);
                startActivity(intent);
            }
        });
    }


    // following helper method checks to see if all the provided spaces is filled or not by the user
    public boolean allDataFill() {
        boolean result = true;

        if (courseText.getText().toString().equals("")) {
            courseText.setError("Missing Course Name/Code");
            result = false;
        }

        if (creditText.getText().toString().equals("")) {
            creditText.setError("Missing Credit");
            result = false;
        }

        if (markText.getText().toString().equals("") || Integer.parseInt(markText.getText().toString()) > 100 ||
                Integer.parseInt(markText.getText().toString()) < 0) {
            markText.setError("Enter Valid Percentage");
            result = false;
        }

        if (yearText.getText().toString().equals("")) {
            yearText.setError("Enter a Year!");
            result = false;
        }

        return result;
    }

    /* Database is static. Therefore there is only one copy for every instance. This makes allows to
       only have one copy of database. It is shared in different activities. */
    public static MarkDatabase getMarkDatabase() {
        return markDatabase;
    }

}

