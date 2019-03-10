package com.example.came.cameselleabreujavier_proyecto;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//import static com.example.came.cameselleabreujavier_proyecto.MainActivity.withSound;
//import static com.example.came.cameselleabreujavier_proyecto.MainActivity.withVibration;

public class DataBase extends SQLiteOpenHelper {
    String sqlCreateTableRecords = "CREATE TABLE records (name TEXT,distance INTEGER)";//Query to create table
//    String sqlCreateTableOptions = "CREATE TABLE options (sound  INTEGER,vibration  INTEGER)";

    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateTableRecords);
        db.execSQL("INSERT INTO records (name,distance) values ('EXP',125)");
//        db.execSQL(sqlCreateTableOptions);
//        db.execSQL("INSERT INTO options (sound,vibration) values (" + ((withSound==true)?1:0) + "," + ((withVibration==true)?1:0) + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS records");
//        db.execSQL("DROP TABLE IF EXISTS options");
        db.execSQL(sqlCreateTableRecords);
//        db.execSQL(sqlCreateTableOptions);
    }
}
