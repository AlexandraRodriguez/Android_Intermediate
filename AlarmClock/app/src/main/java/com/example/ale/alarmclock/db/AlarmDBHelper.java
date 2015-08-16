package com.example.ale.alarmclock.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlarmDBHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "AlarmClock";
    private static final int DB_SCHEME_VERSION = 1;

    public AlarmDBHelper(Context context) {
        super(context, DB_NAME, null, DB_SCHEME_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AlarmDataBaseManager.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(AlarmDataBaseManager.SQL_DELETE_ALARM);
        onCreate(db);
    }
}
