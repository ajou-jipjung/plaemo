package com.po771.plaemo;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.po771.plaemo.DB.BaseHelper;
import com.po771.plaemo.item.Item_AlarmList;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AlarmStartManager extends Activity {

    private static AlarmStartManager alarmStartManager = null;

    public AlarmStartManager(Context context) {

    }


    public static AlarmStartManager getInstance(Context context){
        if(alarmStartManager == null) {
            alarmStartManager = new AlarmStartManager(context.getApplicationContext());
        }
        return alarmStartManager;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    Context context;
    private PendingIntent pendingIntent;
//    private Intent intent;
    private AlarmManager alarmManager;
    BaseHelper baseHelper;

    int ison;
    int isvibrate;
    Item_AlarmList alarm = new Item_AlarmList();
    int alarm_id;

    public void start() {
        // 시간 설정

        baseHelper=BaseHelper.getInstance(this);
        //여기서 alarm_id로 안들어감
        alarm_id = getIntent().getIntExtra("alarm_id", 0);
        alarm = baseHelper.insertAlarmtoManager(alarm_id);
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
        calendar.set(Calendar.MINUTE, alarm.getMinute());
        calendar.set(Calendar.SECOND, 0);

        // 현재 시간보다 이전이면
        if (calendar.before(Calendar.getInstance())){
            //다음날로 설정
            calendar.add(Calendar.DATE, 1);
        }

        //receiver 설정
        Intent intent = new Intent(this,AlarmReceiver.class);
        Bundle bundle = new Bundle();
        // state 값이 on 이면 알람시작, off 이면 중지
        ison = alarm.getIson();
        isvibrate = alarm.getVibrate();

//        IntentData intentData = new IntentData(ison, isvibrate);
//        intent.putExtra("data", intentData);


//        intent.putExtra("ison", new_alarm.getIson());
        bundle.putInt("ison", ison);
        bundle.putInt("isvibrate", isvibrate);
        intent.putExtras(bundle);


        this.pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // 알람 설정
//        this.alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        this.alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000*30 ,pendingIntent);

        // Toast 보여주기
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault());
        Toast.makeText(this, "Alarm : " + format.format(calendar.getTime()), Toast.LENGTH_SHORT).show();

    }

    public void stop() {

        if(pendingIntent == null){
            return;
        }


        // 알람 취소
        this.alarmManager.cancel(this.pendingIntent);

        ison = alarm.getIson();
        isvibrate = alarm.getVibrate();

        // 알람 중지
        Intent intent = new Intent(this, AlarmReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ison", ison);
        bundle.putInt("isvibrate", isvibrate);
        intent.putExtras(bundle);

        sendBroadcast(intent);

        this.pendingIntent = null;

    }
}
