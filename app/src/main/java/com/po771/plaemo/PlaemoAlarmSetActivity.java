package com.po771.plaemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.po771.plaemo.DB.BaseHelper;
import com.po771.plaemo.item.Item_AlarmList;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

//public class PlaemoAlarmSetActivity extends AppCompatActivity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_alarm_setting);
//
//        final TimePicker picker=(TimePicker)findViewById(R.id.timePicker);
//        picker.setIs24HourView(false);
//
//
//        // 앞서 설정한 값으로 보여주기
//        // 없으면 디폴트 값은 현재시간
//
//        SharedPreferences sharedPreferences = getSharedPreferences("daily alarm", MODE_PRIVATE);
//        long millis = sharedPreferences.getLong("nextNotifyTime", Calendar.getInstance().getTimeInMillis());
//
//        Calendar nextNotifyTime = new GregorianCalendar();
//        nextNotifyTime.setTimeInMillis(millis);
//
//        Date nextDate = nextNotifyTime.getTime();
//        String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(nextDate);
//        Toast.makeText(getApplicationContext(),"[처음 실행시] 다음 알람은 " + date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();
//
//
//        // 이전 설정값으로 TimePicker 초기화
//        Date currentTime = nextNotifyTime.getTime();
//        SimpleDateFormat HourFormat = new SimpleDateFormat("kk", Locale.getDefault());
//        SimpleDateFormat MinuteFormat = new SimpleDateFormat("mm", Locale.getDefault());
//
//        int pre_hour = Integer.parseInt(HourFormat.format(currentTime));
//        int pre_minute = Integer.parseInt(MinuteFormat.format(currentTime));
//
//
//        if (Build.VERSION.SDK_INT >= 23 ){
//            picker.setHour(pre_hour);
//            picker.setMinute(pre_minute);
//        }
//        else{
//            picker.setCurrentHour(pre_hour);
//            picker.setCurrentMinute(pre_minute);
//        }
//
//
//        Button button = (Button) findViewById(R.id.set_alarm);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//
//                int hour, hour_24, minute;
//                String am_pm;
//                if (Build.VERSION.SDK_INT >= 23 ){
//                    hour_24 = picker.getHour();
//                    minute = picker.getMinute();
//                }
//                else{
//                    hour_24 = picker.getCurrentHour();
//                    minute = picker.getCurrentMinute();
//                }
//                if(hour_24 > 12) {
//                    am_pm = "PM";
//                    hour = hour_24 - 12;
//                }
//                else
//                {
//                    hour = hour_24;
//                    am_pm="AM";
//                }
//
//                // 현재 지정된 시간으로 알람 시간 설정
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTimeInMillis(System.currentTimeMillis());
//                calendar.set(Calendar.HOUR_OF_DAY, hour_24);
//                calendar.set(Calendar.MINUTE, minute);
//                calendar.set(Calendar.SECOND, 0);
//
//                // 이미 지난 시간을 지정했다면 다음날 같은 시간으로 설정
//                if (calendar.before(Calendar.getInstance())) {
//                    calendar.add(Calendar.DATE, 1);
//                }
//
//                Date currentDateTime = calendar.getTime();
//                String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(currentDateTime);
//                Toast.makeText(getApplicationContext(),date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();
//
//                //  Preference에 설정한 값 저장
//                SharedPreferences.Editor editor = getSharedPreferences("daily alarm", MODE_PRIVATE).edit();
//                editor.putLong("nextNotifyTime", (long)calendar.getTimeInMillis());
//                editor.apply();
//
//
//                diaryNotification(calendar);
//            }
//
//        });
//    }
//
//
//    void diaryNotification(Calendar calendar)
//    {
////        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
////        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
////        Boolean dailyNotify = sharedPref.getBoolean(SettingsActivity.KEY_PREF_DAILY_NOTIFICATION, true);
//        Boolean dailyNotify = true; // 무조건 알람을 사용
//
//        PackageManager pm = this.getPackageManager();
//        ComponentName receiver = new ComponentName(this, DeviceBootReceiver.class);
//        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//
//        // 사용자가 매일 알람을 허용했다면
//        if (dailyNotify) {
//
//
//            if (alarmManager != null) {
//
//                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                        AlarmManager.INTERVAL_DAY, pendingIntent);
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//                }
//            }
//
//            // 부팅 후 실행되는 리시버 사용가능하게 설정
//            pm.setComponentEnabledSetting(receiver,
//                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//                    PackageManager.DONT_KILL_APP);
//
//        }
//    }
//
//}


public class PlaemoAlarmSetActivity extends AppCompatActivity {

    private Item_AlarmList new_alarm = new Item_AlarmList();
    BaseHelper baseHelper;

    int alarm_id;
    TimePicker timePicker;
    ToggleButton toggleButton_sun;
    ToggleButton toggleButton_mon;
    ToggleButton toggleButton_tue;
    ToggleButton toggleButton_wed;
    ToggleButton toggleButton_thu;
    ToggleButton toggleButton_fri;
    ToggleButton toggleButton_sat;
    EditText name;
    Switch switch_vibe;
    Switch switch_repeat;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_setting);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button alarm_set = (Button) findViewById(R.id.set_alarm);
        alarm_set.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 switch (v.getId()) {
                     case R.id.set_alarm:
                         //                new_alarm.set_id(alarm_id);
                         if (timePicker.getHour() >= 12) {// 오후면
                             new_alarm.setAmpm(1);
//                             new_alarm.setHour((timePicker.getHour()) - 12);
                         } else {// 오전이면
                             new_alarm.setAmpm(0);
//                             new_alarm.setHour(timePicker.getHour());
                         }
                         new_alarm.setHour(timePicker.getHour());
                         new_alarm.setMinute(timePicker.getMinute());
                         new_alarm.setAlarm_name(name.getText().toString());
                         if (switch_vibe.isChecked()) {// 진동 on
                             new_alarm.setVibrate(1);
                         } else {// 진동 off
                             new_alarm.setVibrate(0);
                         }

                         new_alarm.setIson(1);
                         baseHelper.insertAlarmSet(new_alarm);
                         // 이전 페이지로 이동
                         setResult(RESULT_OK);
                         finish();
                 }
             }
        });

        baseHelper = BaseHelper.getInstance(this);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        toggleButton_sun = (ToggleButton) findViewById(R.id.toggle_sun);
        toggleButton_mon = (ToggleButton) findViewById(R.id.toggle_mon);
        toggleButton_tue = (ToggleButton) findViewById(R.id.toggle_tue);
        toggleButton_wed = (ToggleButton) findViewById(R.id.toggle_wed);
        toggleButton_thu = (ToggleButton) findViewById(R.id.toggle_thu);
        toggleButton_fri = (ToggleButton) findViewById(R.id.toggle_fri);
        toggleButton_sat = (ToggleButton) findViewById(R.id.toggle_sat);

        name = (EditText) findViewById(R.id.set_alarm_name) ;
        switch_vibe = (Switch) findViewById(R.id.vibrate_switch);
//        switch_repeat = (Switch) findViewById(R.id.repeat_switch);

        alarm_id = getIntent().getIntExtra("alarm_id", 1);



        // 알람 on/off
//        PlaemoAlarmList_Adapter adapter = new PlaemoAlarmList_Adapter(new PlaemoAlarmList_Adapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                if ()
//                new_alarm.setIson();
//            }
//        }) ;

    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    //액션버튼을 클릭했을때의 동작
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_OK);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}