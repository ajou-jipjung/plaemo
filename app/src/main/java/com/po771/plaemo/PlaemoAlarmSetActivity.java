package com.po771.plaemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import java.util.Locale;

public class PlaemoAlarmSetActivity extends AppCompatActivity {

    Item_AlarmList new_alarm = new Item_AlarmList();
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private AlarmStartManager alarmStartManager;
    BaseHelper baseHelper;
//    Context context;


    int ison;
    int isvibrate;

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
    Button button_alarm_set;
    Button button_alarm_cancel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_setting);

//        alarmStartManager = AlarmStartManager.getInstance(context);
        this.alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        baseHelper = BaseHelper.getInstance(this);
        timePicker = (TimePicker) findViewById(R.id.timePicker);

        toggleButton_sun = (ToggleButton) findViewById(R.id.toggle_sun);
        toggleButton_mon = (ToggleButton) findViewById(R.id.toggle_mon);
        toggleButton_tue = (ToggleButton) findViewById(R.id.toggle_tue);
        toggleButton_wed = (ToggleButton) findViewById(R.id.toggle_wed);
        toggleButton_thu = (ToggleButton) findViewById(R.id.toggle_thu);
        toggleButton_fri = (ToggleButton) findViewById(R.id.toggle_fri);
        toggleButton_sat = (ToggleButton) findViewById(R.id.toggle_sat);
        button_alarm_set = (Button) findViewById(R.id.set_alarm);
        button_alarm_cancel = (Button) findViewById(R.id.cancel_alarm);

        name = (EditText) findViewById(R.id.set_alarm_name) ;
        switch_vibe = (Switch) findViewById(R.id.vibrate_switch);
//        switch_repeat = (Switch) findViewById(R.id.repeat_switch);


//        alarm_id = getIntent().getIntExtra("alarm_id", 1);


        button_alarm_set.setOnClickListener(mClickListener);
        button_alarm_cancel.setOnClickListener(mClickListener);
    }

    private void start() {
        // 시간 설정
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
        calendar.set(Calendar.MINUTE, timePicker.getMinute());
        calendar.set(Calendar.SECOND, 0);

        // 현재 시간보다 이전이면
        if (calendar.before(Calendar.getInstance())){
            //다음날로 설정
            calendar.add(Calendar.DATE, 1);
        }

        //receiver 설정
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, AlarmReceiver.class);
        // state 값이 on 이면 알람시작, off 이면 중지
        ison = new_alarm.getIson();
        isvibrate = new_alarm.getVibrate();

//        IntentData intentData = new IntentData(ison, isvibrate);
//        intent.putExtra("data", intentData);


//        intent.putExtra("ison", new_alarm.getIson());
        bundle.putInt("ison", ison);
        bundle.putInt("isvibrate", isvibrate);
        intent.putExtras(bundle);


        this.pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // 알람 설정
        // 한번만 울리는 알람
//        this.alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        // 24시간 후에 울리는 알람 설정
        this.alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000*60*60*24 ,pendingIntent);
        // Toast 보여주기
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault());
        Toast.makeText(this, "Alarm : " + format.format(calendar.getTime()), Toast.LENGTH_SHORT).show();

    }

    private void stop() {

        if(pendingIntent == null){
            return;
        }


        // 알람 취소
        this.alarmManager.cancel(this.pendingIntent);

        ison = new_alarm.getIson();
        isvibrate = new_alarm.getVibrate();

        // 알람 중지
        Intent intent = new Intent(this, AlarmReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ison", ison);
        bundle.putInt("isvibrate", isvibrate);
        intent.putExtras(bundle);

        sendBroadcast(intent);

        this.pendingIntent = null;

    }


        View.OnClickListener mClickListener = new View.OnClickListener() {
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
                             Log.d("진동 ON", "진동 ON");

                         } else {// 진동 off
                             new_alarm.setVibrate(0);
                             Log.d("진동 OFF", "진동 OFF");

                         }

                         new_alarm.setIson(1);
                         baseHelper.insertAlarmSet(new_alarm);

                        //알람 시작

//                         alarmStartManager.start();
//                         alarmStartManager로 값이 넘어가지 않음.
                         start();


                         // 이전 페이지로 이동
                         setResult(RESULT_OK);

                         break;
                     case R.id.cancel_alarm:
                         new_alarm.setIson(0);
                         baseHelper.insertAlarmSet(new_alarm);
                         stop();

                         setResult(RESULT_OK);

                         break;
                 }
             }
        };

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