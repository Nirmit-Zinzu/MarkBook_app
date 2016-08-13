package com.nirmit.markbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ninizinzu on 16-07-08.
 */
public class MarkDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "marks.db";    // name of the database
    public static final int DATABASE_VERSION = 1;             // version
    public static final String TABLE_NAME  = "mark_table";    // name of the table

    public static final String COL_1 = "_id";                    // column 1 = ID
    public static final String COL_2 = "Course";                 // column 2 = Course
    public static final String COL_3 = "Credit";                 // column 3 = Credit
    public static final String COL_4 = "Year";                   // column 4 = Year
    public static final String COL_5 = "Mark";                   // column 5 = Mark
    public static final String COL_6 = "Term";                   // column 6 = Term

    // Constructor (necessary)
    public MarkDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // onCreate (necessary)
    @Override
    public void onCreate(SQLiteDatabase db) {

        // executing SQL database
        db.execSQL("create table " + TABLE_NAME + " ("
                + COL_1 + " integer primary key autoincrement, "
                + COL_2 + " text, "
                + COL_3 + " double, "
                + COL_4 + " int, "
                + COL_5 + " double, "
                + COL_6 + " text);"
        );


    }

    // onUpgrade (necessary)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);  // delete old table
        onCreate(db);   // execute onCreate and form new table
    }


    public boolean insertData (String course, String credit, String year, String mark, String term) {
        SQLiteDatabase database = this.getWritableDatabase();  // getting database to edit
        ContentValues contentValues = new ContentValues();     // instantiating ContentValues class
        contentValues.put(COL_2, course);                      // inserting course info
        contentValues.put(COL_3, credit);                      // inserting credit info
        contentValues.put(COL_4, year);                        // inserting year info
        contentValues.put(COL_5, mark);                        // inserting mark info
        contentValues.put(COL_6, term);                        // inserting term info

        long result = database.insert(TABLE_NAME, null, contentValues);   // return -1 for incomplete insertion


        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    // grabbing all the data from the database
    public Cursor getAllData() {
        SQLiteDatabase database = this.getWritableDatabase();    // getting database to edit

        // selecting all(*) data from the table
        Cursor res = database.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    // updating the database
    public boolean updateData (String id, String course, String credit, String year, String mark, String term) {
        SQLiteDatabase database = this.getWritableDatabase();   // getting database to edit
        ContentValues contentValues = new ContentValues();      // instantiating ContentValues class
        contentValues.put(COL_1, id);                           // getting specific id#
        contentValues.put(COL_2, course);                       // new course info
        contentValues.put(COL_3, credit);                       // new credit info
        contentValues.put(COL_4, year);                         // new year info
        contentValues.put(COL_5, mark);                         // new mark info
        contentValues.put(COL_6, term);                         // new term info

        // updating data using the object method
        int changeMade = database.update(TABLE_NAME, contentValues, "_id = ?", new String[]{id});

        if (changeMade > 0) {
            return true;
        } else {
            return false;
        }
    }

    // deleting specific information from the database
    public int deleteData (String id) {
        SQLiteDatabase database = this.getWritableDatabase();   // getting database to edit
        return database.delete(TABLE_NAME, "_id = ?", new String[] { id }); //deleting specific data
    }

    // deleting all information from the database
    public void deleteAll() {
        SQLiteDatabase database = this.getWritableDatabase(); // getting database to edit
        database.delete(TABLE_NAME, null, null);                //deleting all data

        // allows ID numbering to start from 1.
        database.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_NAME + "'");

        database.close(); // closing database

    }

}



