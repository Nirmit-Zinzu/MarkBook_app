package com.nirmit.markbook;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;



public class ShowListActivity extends AppCompatActivity {

    MarkDatabase myDB = AddMarks.getMarkDatabase();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);

        populateListView();   // populates the information into list view

    }

    // following method populates the information into list view
    public void populateListView() {

        Cursor cursor = myDB.getAllData();  // gets all the information from the database

        // allow activity to manage lifetime
        //  startManagingCursor(cursor);

        // making a string array to store the titles from the database chart
        String[] fromFieldNames = new String[]
                {myDB.COL_1, myDB.COL_2, myDB.COL_3, myDB.COL_4,
                        myDB.COL_5, myDB.COL_6};

        // making an integer array to correspond with the information from the database
        int[] toViewIDs = new int[]{R.id.ID, R.id.course, R.id.courseCredit, R.id.year, R.id.courseMark, R.id.term};

        //setting the adapter
        SimpleCursorAdapter myCursorAdapter;
        // transferring the data from the table to the designated id
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.list_view_layout, cursor, fromFieldNames, toViewIDs, 0);

        // set adapter for list view
        ListView myList = (ListView) findViewById(R.id.listItems);

        myList.setAdapter(myCursorAdapter);  // setting the final adapter

    }

}