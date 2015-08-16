package com.example.ale.alarmclock;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;

import com.example.ale.alarmclock.db.AlarmDBHelper;
import com.example.ale.alarmclock.db.AlarmDataBaseManager;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.Dialog;
import com.rey.material.widget.TextView;


public class MainActivity extends ListActivity {

private AlarmAdapter adapter;
    private AlarmDataBaseManager manager;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main);
        manager = new AlarmDataBaseManager(this);
        adapter = new AlarmAdapter(this, manager.getAlarms());
        setListAdapter(adapter);


    }

    public void startAlarmDetailsActivity(View v){
        Intent intent = new Intent(this, AlarmDetailsActivity.class);
        //intent.putExtra("id", id);
        //startActivityForResult(intent, 0);
        startActivity(intent);
    }


    public void startAlarmDetailsActivityWithId(long id) {
        Intent intent = new Intent(this, AlarmDetailsActivity.class);
        intent.putExtra("id", id);
        startActivityForResult(intent, 0);
    }


}