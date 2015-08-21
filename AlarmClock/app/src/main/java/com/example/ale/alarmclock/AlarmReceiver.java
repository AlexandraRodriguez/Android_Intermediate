package com.example.ale.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.ale.alarmclock.db.AlarmDataBaseManager;

import java.util.Calendar;
import java.util.List;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String HOUR = "hour";
    public static final String MINUTE = "minutes";
    public static final String TONE = "alarmTone";
    public static final String LABEL = "label";
    public static final String DAY_OF_MONTH = "day";
    public static final String MONTH = "month";
    public static final String YEAR = "year";


    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "inside onReceive", Toast.LENGTH_SHORT);
    }

    public static void setAlarms(Context context) {
        AlarmDataBaseManager dbManager = new AlarmDataBaseManager(context);
        List<Alarm> alarms = dbManager.getAlarms();
        for (Alarm alarm : alarms) {
            if (alarm.isEnabled()) {
                PendingIntent pi = createPI(context, alarm);
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
                calendar.set(Calendar.MINUTE, alarm.getMinutes());
                calendar.set(Calendar.SECOND, 00);
               calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
               // calendar.set(alarm.getYear(), alarm.getMonth(), alarm.getDayOfMonth());

                setAlarm(context, calendar, pi);


            }
        }
    }

    private static void setAlarm(Context context, Calendar calendar, PendingIntent pIntent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        }
    }

    public static void cancelAlarms(Context context) {
        AlarmDataBaseManager dbManager = new AlarmDataBaseManager(context);
        List<Alarm> alarms =  dbManager.getAlarms();
        if (alarms != null) {
            for (Alarm alarm : alarms) {
                if (alarm.isEnabled()) {
                    PendingIntent pi = createPI(context, alarm);
                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    alarmManager.cancel(pi);
                }
            }
        }
    }

    private static PendingIntent createPI(Context context, Alarm alarm) {
                              Intent intent = new Intent(context, AlarmService.class);
        intent.putExtra(ID, alarm.getId());
        intent.putExtra(NAME, alarm.getName());
        intent.putExtra(HOUR, alarm.getHour());
        intent.putExtra(MINUTE, alarm.getMinutes());
        intent.putExtra(TONE, alarm.getAlarmToneName());
        //intent.putExtra(DAY_OF_MONTH, alarm.getDayOfMonth());
        //intent.putExtra(MONTH, alarm.getMonth());
        //intent.putExtra(YEAR, alarm.getYear());

        return PendingIntent.getService(context,
                (int) alarm.getId(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }


}
