package com.po771.plaemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.po771.plaemo.item.Item_alarm;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class AlarmLoader {
    public static AlarmLoader alarmLoader = null;
    public static Context context;

    public static AlarmLoader getInstance(Context context2){ // 싱글턴 패턴으로 구현하였다.
        if(alarmLoader == null){
            context=context2;
            alarmLoader = new AlarmLoader(context.getApplicationContext());
        }

        return alarmLoader;
    }

    public AlarmLoader(Context context) {
    }

    public void initAlarm(List<Item_alarm> alarmList){

        for(int i=0;i<alarmList.size();i++){
            Item_alarm item_alarm = alarmList.get(i);
            setAlarm(item_alarm);

        }
    }

    public void setAlarm(Item_alarm item_alarm){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, item_alarm.getHour());
        calendar.set(Calendar.MINUTE, item_alarm.getMinute());
        calendar.set(Calendar.SECOND, 0);
        int startIndex = getToday();
        int count = 0;
        int daysnum = item_alarm.getDaysnum();
        Log.d("settingalarm", item_alarm.getDaysoftheweek());
        Log.d("settingalarm", String.valueOf(daysnum));

        do {
            int index = (startIndex + count) % 7;
            if (item_alarm.checkday(index)) {
                Log.d("settingalarm", "setting day" + index);
                if (!calendar.before(Calendar.getInstance())) {
                    diaryNotification(calendar, item_alarm, index);
                    daysnum--;
                }
            }
            calendar.add(Calendar.DATE, 1);
            count++;
        } while (daysnum != 0);
    }

    private int getToday(){
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int startIndex = 0;
        switch (dayOfWeek) {
            case Calendar.MONDAY:
                startIndex = 0;
                break;
            case Calendar.TUESDAY:
                startIndex = 1;
                break;
            case Calendar.WEDNESDAY:
                startIndex = 2;
                break;
            case Calendar.THURSDAY:
                startIndex = 3;
                break;
            case Calendar.FRIDAY:
                startIndex = 4;
                break;
            case Calendar.SATURDAY:
                startIndex = 5;
                break;
            case Calendar.SUNDAY:
                startIndex = 6;
                break;
        }

        return startIndex;
    }

    void diaryNotification(Calendar calendar,Item_alarm item_alarm,int index)
    {
//        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
//        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//        Boolean dailyNotify = sharedPref.getBoolean(SettingsActivity.KEY_PREF_DAILY_NOTIFICATION, true);
        Boolean dailyNotify = true; // 무조건 알람을 사용

        PackageManager pm = context.getPackageManager();
        ComponentName receiver = new ComponentName(context, DeviceBootReceiver.class);
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        alarmIntent.putExtra("alarm_id",item_alarm.get_id());
        alarmIntent.putExtra("book_id",item_alarm.getBook_id());
        final int _id = item_alarm.getCase_id()+index;
        Log.d("settingalarm","case_id "+_id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, _id, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);


        // 사용자가 매일 알람을 허용했다면
        if (item_alarm.getIson()==1) {


            if (alarmManager != null) {
                Date currentDateTime = calendar.getTime();
                String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(currentDateTime);
                Log.d("settingalarm","calendar_time "+date_text);
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
        else{
            alarmManager.cancel(pendingIntent);
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
