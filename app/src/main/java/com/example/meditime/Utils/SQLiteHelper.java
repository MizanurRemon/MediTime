package com.example.meditime.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class SQLiteHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    private static final String DATABASE_NAME = "MEDICINE_DATABASE";
    private static final String SCHEDULE_TABLE = "SCHEDULE_TABLE";
    private static final int VERSION = 1;
    private static final String id = "ID";
    private static final String Schedule_Name = "ScheduleName";
    private static final String Schedule_Time = "ScheduleTime";

    private final Context context;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + SCHEDULE_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , ScheduleName TEXT , ScheduleTime TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SCHEDULE_TABLE);
        onCreate(db);
    }

    public void insertData(String name, String time) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Schedule_Name, name);
        values.put(Schedule_Time, time);
        long result = db.insert(SCHEDULE_TABLE, null, values);

        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readData() {
        db = this.getWritableDatabase();
        String query = "select * from " + SCHEDULE_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }


}
