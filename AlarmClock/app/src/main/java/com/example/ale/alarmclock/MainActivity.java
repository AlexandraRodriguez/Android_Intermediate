package com.example.ale.alarmclock;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.ale.alarmclock.db.AlarmDataBaseManager;
import com.rey.material.widget.ListView;


public class MainActivity extends ListActivity {

private AlarmAdapter adapter;
    private AlarmDataBaseManager dbManager;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main);
        dbManager = new AlarmDataBaseManager(this);
        adapter = new AlarmAdapter(this, dbManager.getAlarms());
        this.setListAdapter(adapter);
    }

    public void addNewAlarm(View v){
        startAlarmDetailsActivity(-1);

    }

    public void startAlarmDetailsActivity(long id){
        Intent intent = new Intent(this, AlarmDetailsActivity.class);
        intent.putExtra("id", id);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            updateUI();
        }
    }

    public void setEnabled(Long id, boolean b) {
        AlarmReceiver.cancelAlarms(this);
        Alarm alarm = dbManager.getAlarm(id);
        alarm.setEnabled(b);
        dbManager.updateAlarm(alarm);
        updateUI();
        AlarmReceiver.setAlarms(this);
    }

    public void updateUI(){
        adapter.setAlarms(dbManager.getAlarms());
        this.setListAdapter(adapter);
    }
}