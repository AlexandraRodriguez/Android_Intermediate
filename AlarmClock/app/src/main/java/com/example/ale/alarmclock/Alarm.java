package com.example.ale.alarmclock;


import android.net.Uri;
import android.os.Parcelable;

public class Alarm {

    private long id ;
    private int hour;
    private int minutes;
    private String name;
    private Uri alarmTone;
    private boolean enabled;
    private String label;
    private int dayOfMonth;
    private int month;
    private int year;

    public Alarm() {
        id = -1;
        hour = 0;
        minutes = 0;
        name = "";
        alarmTone = null;
        enabled = false;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getAlarmTone() {
        return alarmTone;
    }

    public void setAlarmTone(Uri ringtone) {
        alarmTone = ringtone;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getAlarmToneName(){
        String res  = "";
        if(alarmTone != null)
            res = alarmTone.toString();
        return res;
    }

}
