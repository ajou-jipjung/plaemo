package com.po771.plaemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
//        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Bundle bundle = intent.getExtras();
            Intent sIntent = new Intent(context, AlarmService.class);
            sIntent.putExtras(bundle);

//        sIntent.putExtra("ison", intent.getExtras().getInt("ison"));
//        sIntent.putExtra("isvibrate", intent.getExtras().getInt("isvibrate"));
//        sIntent.putExtra("vibrate", intent.getIntExtra("vibrate", PlaemoAlarmSetActivity.new_alarm.getVibrate());
            //Oreo(26) 버전 이후부터 background 실행X. foreground에서 실행해야함
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(sIntent);
            } else {
                context.startService(sIntent);
            }
        }
//    }
}