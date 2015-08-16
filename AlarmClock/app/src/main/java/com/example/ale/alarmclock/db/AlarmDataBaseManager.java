package com.example.ale.alarmclock.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.ale.alarmclock.Alarm;

import java.util.ArrayList;
import java.util.List;

public class AlarmDataBaseManager {
    public static final String TABLE_NAME = "ALARM_REGISTER";

    public static final String _ID = "_id";
    public static final String CN_NAME = "name";
    public static final String CN_TIME_HOUR = "hours";
    public static final String CN_TIME_MINUTE = "minutes";
    public static final String CN_ALARM_TONE = "tone";
    public static final String CN_ENABLED = "enabled";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CN_NAME + " TEXT,"
            + CN_TIME_HOUR + " INTEGER NOT NULL,"
            + CN_TIME_MINUTE + " INTEGER NOT NULL,"
            + CN_ENABLED + " BOOLEAN,"
            + CN_ALARM_TONE + " TEXT);";

    public static final String SQL_DELETE_ALARM =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    private AlarmDBHelper helper;
    private SQLiteDatabase db;

    public AlarmDataBaseManager(Context context) {
        helper = new AlarmDBHelper(context);
        db = helper.getWritableDatabase();
    }

    private ContentValues generateContentValues(Alarm alarm) {
        ContentValues values = new ContentValues();
        values.put(CN_NAME, alarm.getName());
        values.put(CN_TIME_HOUR, alarm.getHour());
        values.put(CN_TIME_MINUTE, alarm.getMinutes());
        values.put(CN_ENABLED, alarm.isEnabled());
        values.put(CN_ALARM_TONE, alarm.getAlarmToneName());

        return values;
    }

    public long createAlarm(Alarm alarm) {
        return db.insert(TABLE_NAME, null, generateContentValues(alarm));
    }

    public long updateAlarm(Alarm alarm) {
        return db.update(TABLE_NAME, generateContentValues(alarm), _ID + " = ?", new String[]{String.valueOf(alarm.getId())});
    }

    public int deleteAlarm(long id) {
        return db.delete(TABLE_NAME, _ID + " = ?", new String[]{String.valueOf(id)});
    }

    public Alarm getAlarm(long id) {
        String select = "SELECT * FROM " + TABLE_NAME + " WHERE " + _ID + " = " + id;
        Cursor c = db.rawQuery(select, null);
        if (c.moveToNext()) {
            return buildAlarm(c);
        }
        return null;

    }

    public List<Alarm> getAlarms() {
        List<Alarm> res = new ArrayList<Alarm>();
        String select = "SELECT * FROM " + TABLE_NAME;
        Cursor c = db.rawQuery(select, null);

        while (c.moveToNext()) {
            res.add(buildAlarm(c));
        }
        if (res.isEmpty()) {
            res = null;
        }
        return res;
    }

    private Alarm buildAlarm(Cursor c) {
        Alarm alarm = new Alarm();
        alarm.setId(c.getLong(c.getColumnIndex(_ID)));
        alarm.setName(c.getString(c.getColumnIndex(CN_NAME)));
        alarm.setHour(c.getInt(c.getColumnIndex(CN_TIME_HOUR)));
        alarm.setMinutes(c.getInt(c.getColumnIndex(CN_TIME_MINUTE)));
        alarm.setEnabled(c.getInt(c.getColumnIndex(CN_ENABLED)) == 0 ? false : true  );
        if (c.getString(c.getColumnIndex(CN_ALARM_TONE)) != "")
            alarm.setAlarmTone(Uri.parse(c.getString(c.getColumnIndex(CN_ALARM_TONE))));
        else
            alarm.setAlarmTone(null);

        return alarm;
    }


}
