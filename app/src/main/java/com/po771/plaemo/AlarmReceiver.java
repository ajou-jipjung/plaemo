package com.po771.plaemo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int alarm_id = intent.getExtras().getInt("alarm_id",-1);
        int book_id = intent.getExtras().getInt("book_id",-1);

        Log.d("AlarmReceiver","get alarm id "+alarm_id);

//        Bundle bundle = intent.getExtras();
//        Intent serviceIntent = new Intent(context, PlaemoAlarmPopupActivity.class);
//        serviceIntent.putExtras(bundle);
//        Log.d("AlarmReceiver","get book id "+book_id);

        Intent serviceIntent = new Intent(context, AlarmService.class);
        serviceIntent.putExtra("alarm_id",alarm_id);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            context.startForegroundService(serviceIntent);
        }else{
            context.startService(serviceIntent);
        }
    }
}