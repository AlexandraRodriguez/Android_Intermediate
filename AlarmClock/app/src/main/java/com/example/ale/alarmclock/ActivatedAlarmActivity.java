package com.example.ale.alarmclock;


import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.rey.material.widget.TextView;

import java.io.IOException;

public class ActivatedAlarmActivity extends Activity {

    private PowerManager.WakeLock wakeLock;
    private MediaPlayer mediaPlayer;
    private TextView txtTime;
    private TextView txtLabel;
    private TextView txtDate;


    private static final int WAKELOCK_TIMEOUT = 60 * 1000;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        this.setContentView(R.layout.activated_alarm);

        String name = getIntent().getStringExtra(AlarmReceiver.NAME);
        int timeHour = getIntent().getIntExtra(AlarmReceiver.HOUR, 0);
        int timeMinute = getIntent().getIntExtra(AlarmReceiver.MINUTE, 0);
        String tone = getIntent().getStringExtra(AlarmReceiver.TONE);
        int dayOfMonth  = getIntent().getIntExtra(AlarmReceiver.DAY_OF_MONTH, 0);
        int month = getIntent().getIntExtra(AlarmReceiver.MONTH, 0);
        int year = getIntent().getIntExtra(AlarmReceiver.YEAR, 0);

        txtTime = (TextView)findViewById(R.id.txtTime);
        txtTime.setText(timeHour + " : " + timeMinute);
        txtLabel = (TextView)findViewById(R.id.txtLabel);
        txtLabel.setText(name);
        txtDate = (TextView) findViewById(R.id.txtAlarmDate);
        txtDate.setText(dayOfMonth+"/"+month+"/"+year);




        Button btnStop = (Button) findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                finish();
            }
        });

        playAlarm(tone);

        Runnable releaseWakelock = new Runnable() {

            @Override
            public void run() {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

                if (wakeLock != null && wakeLock.isHeld()) {
                    wakeLock.release();
                }
            }
        };
        new Handler().postDelayed(releaseWakelock, WAKELOCK_TIMEOUT);

    }


    private void playAlarm(String tone) {
        mediaPlayer = new MediaPlayer();
        try{
            if(tone != null && !tone.equals("")){
                Uri uri = Uri.parse(tone);
                if(uri != null){
                    mediaPlayer.setDataSource(this, uri);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                    mediaPlayer.setLooping(false);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);


        PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        if (wakeLock == null) {
            wakeLock = pm.newWakeLock((PowerManager.FULL_WAKE_LOCK | PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "");
        }

        if (!wakeLock.isHeld()) {
            wakeLock.acquire();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
        }
    }
}
