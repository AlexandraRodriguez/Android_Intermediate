package com.example.ale.alarmclock;

import android.app.Activity;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

import com.example.ale.alarmclock.db.AlarmDataBaseManager;
import com.rey.material.app.TimePickerDialog;
import com.rey.material.widget.EditText;
import com.rey.material.widget.TextView;

public class AlarmDetailsActivity extends Activity {

    private TextView time;
    private TextView ringtoneSelection;
    private Alarm alarm;
    private AlarmDataBaseManager dbManager;
    private EditText label;
    private ToggleButton toggSunday;
    private ToggleButton toggMonday;
    private ToggleButton toggTuesday;
    private ToggleButton toggWednesday;
    private ToggleButton toggThursday;
    private ToggleButton toggFriday;
    private ToggleButton toggSaturday;

    private long id;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.alarm_details);
        dbManager = new AlarmDataBaseManager(this);
        setReferences();
        id = getIntent().getExtras().getLong("id");
        if (id == -1) {
            alarm = new Alarm();
            setTimePicker();
        } else {
            alarm = dbManager.getAlarm(id);
            setAlarmDetails();
        }

    }

    private void setAlarmDetails() {
        time.setText(alarm.getHour() + " : " + alarm.getMinutes());
        label.setText(alarm.getName());
        ringtoneSelection.setText(RingtoneManager.getRingtone(this, alarm.getAlarmTone()).getTitle(this));

    }

    public void setReferences() {
        time = (TextView) findViewById(R.id.hours);
        label = (EditText) findViewById(R.id.label);
        ringtoneSelection = (TextView) findViewById(R.id.ringtones);
        toggSunday = (ToggleButton) findViewById(R.id.toggSunday);
        toggMonday = (ToggleButton) findViewById(R.id.toggMonday);
        toggTuesday = (ToggleButton) findViewById(R.id.toggTuesday);
        toggWednesday = (ToggleButton) findViewById(R.id.toggWednesday);
        toggThursday = (ToggleButton) findViewById(R.id.toggThursday);
        toggFriday = (ToggleButton) findViewById(R.id.toggFriday);
        toggSaturday = (ToggleButton) findViewById(R.id.toggSaturday);
    }

    public void setTimePicker() {
        final TimePickerDialog dialog = new TimePickerDialog(this);
        dialog.positiveAction("Ok");
        dialog.negativeAction("Cancel");
        dialog.positiveActionClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hh = dialog.getHour();
                int mm = dialog.getMinute();
                time.setText(hh + " : " + mm);
                alarm.setHour(hh);
                alarm.setMinutes(mm);
                dialog.dismiss();
            }
        });
        dialog.negativeActionClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void setLabel(View v) {
        alarm.setName(label.getEditableText().toString());
        dbManager.updateAlarm(alarm);
    }

    public void setRingtone(View v) {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1: {
                    Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    alarm.setAlarmTone(uri);
                    ringtoneSelection.setText(RingtoneManager.getRingtone(this, alarm.getAlarmTone()).getTitle(this));
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }

    public void onTimeTextSelected(View v) {
        setTimePicker();
    }

    public void saveAlarm(View v) {
        if (id == -1) {
            alarm.setEnabled(true);
            id = dbManager.createAlarm(alarm);
            dbManager.getAlarm(id).setId(id);
        }else{
            alarm.setEnabled(true);
            dbManager.updateAlarm(alarm);
        }

        finish();
    }

    public void deleteAlarm(View v) {
        if(id != -1){
            AlarmReceiver.cancelAlarms(this);
            dbManager.deleteAlarm(alarm.getId());
            AlarmReceiver.setAlarms(this);
        }
        finish();
    }
}
