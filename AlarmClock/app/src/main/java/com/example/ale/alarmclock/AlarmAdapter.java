package com.example.ale.alarmclock;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;

import com.rey.material.widget.Switch;
import com.rey.material.widget.TextView;

import java.util.List;

public class AlarmAdapter extends BaseAdapter {

    private Context context;
    private List<Alarm> alarms;

    public AlarmAdapter(Context context, List<Alarm> alarms) {
        this.context = context;
        this.alarms = alarms;
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
    }

    @Override
    public int getCount() {
        int res = -1;
        if (alarms != null)
            res = alarms.size();
        return res;
    }

    @Override
    public Object getItem(int position) {
        Object item = null;
        if (alarms != null)
            item = alarms.get(position);
        return item;
    }

    @Override
    public long getItemId(int position) {
        long id = -1;
        if (alarms != null)
            id = alarms.get(position).getId();
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.alarm_item_list, parent, false);
        }
        final Alarm alarm = (Alarm) getItem(position);
        TextView txtHours = (TextView) convertView.findViewById(R.id.hourFromList);
        txtHours.setText(alarm.getHour() + " : " + alarm.getMinutes());

        Switch switchFromList = (Switch) convertView.findViewById(R.id.swithFromList);
        switchFromList.setChecked(alarm.isEnabled());
        //switchFromList.setTag((long) alarm.getId());
        switchFromList.setTag(Long.valueOf(alarm.getId()));
        switchFromList.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Switch aSwitch, boolean b) {
                ((MainActivity) context).setEnabled((Long) aSwitch.getTag(), b);

            }
        });
        convertView.setTag(Long.valueOf(alarm.getId()));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)context).startAlarmDetailsActivityWithId((long)v.getTag());
            }
        });


        return convertView;
    }
}
