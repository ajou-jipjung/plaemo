package com.po771.plaemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class AlarmLoader {
    public static AlarmLoader alarmLoader = null;
    public static Context context;

    public static AlarmLoader getInstance(Context context2){ // 싱글턴 패턴으로 구현하였다.
        if(alarmLoader == null){
            context=context2;
            alarmLoader = new AlarmLoader(context.getApplicationContext());
            alarmLoader.initAlarm();
        }

        return alarmLoader;
    }

    public AlarmLoader(Context context) {
    }

    public void initAlarm(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        int hour=14;
        int minute=24;
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        // 이미 지난 시간을 지정했다면 다음날 같은 시간으로 설정
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }


        diaryNotification(calendar);

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute+3);
        calendar.set(Calendar.SECOND, 0);

        // 이미 지난 시간을 지정했다면 다음날 같은 시간으로 설정
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }


        diaryNotification(calendar);
    }

    void diaryNotification(Calendar calendar)
    {
//        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
//        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//        Boolean dailyNotify = sharedPref.getBoolean(SettingsActivity.KEY_PREF_DAILY_NOTIFICATION, true);
        Boolean dailyNotify = true; // 무조건 알람을 사용

        PackageManager pm = context.getPackageManager();
        ComponentName receiver = new ComponentName(context, DeviceBootReceiver.class);
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        final int _id = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, _id, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);


        // 사용자가 매일 알람을 허용했다면
        if (dailyNotify) {


            if (alarmManager != null) {

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
            }

            // 부팅 후 실행되는 리시버 사용가능하게 설정
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);

        }
//        else { //Disable Daily Notifications
//            if (PendingIntent.getBroadcast(this, 0, alarmIntent, 0) != null && alarmManager != null) {
//                alarmManager.cancel(pendingIntent);
//                //Toast.makeText(this,"Notifications were disabled",Toast.LENGTH_SHORT).show();
//            }
//            pm.setComponentEnabledSetting(receiver,
//                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                    PackageManager.DONT_KILL_APP);
//        }
    }

}
