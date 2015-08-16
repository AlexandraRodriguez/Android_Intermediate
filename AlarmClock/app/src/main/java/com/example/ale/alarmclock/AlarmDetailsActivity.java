package com.example.ale.alarmclock;

import android.app.Activity;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.rey.material.app.TimePickerDialog;
import com.rey.material.widget.EditText;
import com.rey.material.widget.TextView;

public class AlarmDetailsActivity extends Activity {

    private TextView time;
    private TextView ringtoneSelection;
    private Alarm alarm;
    private EditText label;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.alarm_details);
        alarm = new Alarm();
        time = (TextView) findViewById(R.id.hours);
        label = (EditText) findViewById(R.id.label);
        ringtoneSelection = (TextView) findViewById(R.id.ringtones);
        setTimePicker();

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

    public void setTime() {

        //dialog.show();

        /*day = (TextView)findViewById(R.id.day);
        month = (TextView)findViewById(R.id.month);
        year = (TextView)findViewById(R.id.year);

new TimePickerDialog(getActivity(),this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));

        final DatePickerDialog dialog = (DatePickerDialog)new DatePickerDialog.Builder().build(this);
        dialog.positiveAction("Ok")
                .negativeAction("Cancel")
                .positiveActionClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int mday = dialog.getDay();
                        int mmonth = dialog.getMonth();
                        int myear = dialog.getYear();
                        Log.e("tag", "Date: " + mday + "/" + mmonth + "/" + myear);
                        day.setText("Day: " + mday);
                        month.setText("Month: " + mmonth);
                        year.setText("Year: " + myear);
                        dialog.dismiss();
                    }
                })
                .negativeActionClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                })
                .show();*/
    }

    public void onTimeTextSelected(View v) {
        setTimePicker();
    }
}
