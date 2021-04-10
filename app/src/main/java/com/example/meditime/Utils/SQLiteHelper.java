package com.example.meditime.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class SQLiteHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    private static final int VERSION = 5;
    private static final String DATABASE_NAME = "MEDICINE_DATABASE";

    private static final String SCHEDULE_TABLE = "SCHEDULE_TABLE";
    private static final String id = "ID";
    private static final String Schedule_Name = "ScheduleName";
    private static final String Schedule_Time = "ScheduleTime";

    private static final String MEDICINE_TABLE = "MEDICINE_TABLE";
    private static final String Schedule_ID = "Schedule_ID";
    private static final String Med_name = "Med_name";
    private static final String Amount = "Amount";
    private static final String Type = "Type";


    private final Context context;


    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + SCHEDULE_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , ScheduleName TEXT , ScheduleTime TEXT )");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + MEDICINE_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , Schedule_ID TEXT , Med_name TEXT , Amount TEXT , Type TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SCHEDULE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MEDICINE_TABLE);
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


    public void insertMedicine(String schedule_id, String MedicineName, String Med_Amount, String Med_Type) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Schedule_ID, schedule_id);
        values.put(Med_name, MedicineName);
        values.put(Amount, Med_Amount);
        values.put(Type, Med_Type);

        long result = db.insert(MEDICINE_TABLE, null, values);

        //Toast.makeText(context, schedule_id + " " + MedicineName + " " + Med_Amount + " " + Med_Type, Toast.LENGTH_SHORT).show();

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

    public Cursor readMedicine(String schedule_id) {
        db = this.getWritableDatabase();
        String query = "SELECT * FROM MEDICINE_TABLE where Schedule_ID = schedule_id";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }


}
