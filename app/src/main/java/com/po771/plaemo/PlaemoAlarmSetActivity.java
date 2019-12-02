package com.po771.plaemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.po771.plaemo.DB.BaseHelper;
import com.po771.plaemo.item.Item_alarm;
import com.po771.plaemo.item.Item_book;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PlaemoAlarmSetActivity extends AppCompatActivity implements View.OnClickListener {

    private Item_alarm new_alarm = new Item_alarm();
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
    ImageView bookImage;
    EditText name;
    Switch switch_vibe;
    TextView tvBookname;
    TextView tvBookpage;
    Switch switch_repeat;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_setting);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = getSharedPreferences("daily alarm", MODE_PRIVATE);
        long millis = sharedPreferences.getLong("nextNotifyTime", Calendar.getInstance().getTimeInMillis());

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

                         String days="";
                         if(toggleButton_sun.isChecked()){
                             days+="일/";
                         }
                         if(toggleButton_mon.isChecked()){
                             days+="월/";
                         }
                         if(toggleButton_tue.isChecked()){
                             days+="화/";
                         }
                         if(toggleButton_wed.isChecked()){
                             days+="수/";
                         }
                         if(toggleButton_thu.isChecked()){
                             days+="목/";
                         }
                         if(toggleButton_fri.isChecked()){
                             days+="금/";
                         }
                         if(toggleButton_sat.isChecked()){
                             days+="토/";
                         }
                         new_alarm.setDaysoftheweek(days);

                         baseHelper.insertAlarm(new_alarm);

                         Calendar calendar = Calendar.getInstance();
                         calendar.setTimeInMillis(System.currentTimeMillis());
                         calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                         calendar.set(Calendar.MINUTE, timePicker.getMinute());
                         calendar.set(Calendar.SECOND, 0);

                         // 이미 지난 시간을 지정했다면 다음날 같은 시간으로 설정
                         if (calendar.before(Calendar.getInstance())) {
                             calendar.add(Calendar.DATE, 1);
                         }

                         Date currentDateTime = calendar.getTime();
                         String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(currentDateTime);
                         Toast.makeText(getApplicationContext(),date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();

                         setResult(RESULT_OK);
                         finish();
                 }
             }
        });

        findViewById(R.id.alarmset_imagebutton).setOnClickListener(this);

        baseHelper = BaseHelper.getInstance(this);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        toggleButton_sun = (ToggleButton) findViewById(R.id.toggle_sun);
        toggleButton_mon = (ToggleButton) findViewById(R.id.toggle_mon);
        toggleButton_tue = (ToggleButton) findViewById(R.id.toggle_tue);
        toggleButton_wed = (ToggleButton) findViewById(R.id.toggle_wed);
        toggleButton_thu = (ToggleButton) findViewById(R.id.toggle_thu);
        toggleButton_fri = (ToggleButton) findViewById(R.id.toggle_fri);
        toggleButton_sat = (ToggleButton) findViewById(R.id.toggle_sat);
        bookImage = (ImageView) findViewById(R.id.alarmset_imagebutton);
        name = (EditText) findViewById(R.id.set_alarm_name) ;
        switch_vibe = (Switch) findViewById(R.id.vibrate_switch);
        tvBookname = (TextView)findViewById(R.id.alarmset_bookname);
        tvBookpage = (TextView)findViewById(R.id.alarmset_bookpage);
//        switch_repeat = (Switch) findViewById(R.id.repeat_switch);

        alarm_id = getIntent().getIntExtra("alarm_id", 1);

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int iv_height = metrics.heightPixels/5;
        bookImage.getLayoutParams().height=iv_height;



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

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.alarmset_imagebutton:
                Intent intent = new Intent(this,PlaemoAlarmSetFolderActivity.class);
                startActivityForResult(intent,200);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            switch (requestCode) {
                case 200: //책 상태 변경 + 아래 메모 변경

                    int getBookid = data.getExtras().getInt("book_id");
                    Log.d("selectbookid", String.valueOf(getBookid));
                    new_alarm.setBook_id(getBookid);
                    Item_book item_book = baseHelper.getBook(getBookid);
                    Bitmap bitmap = loadImageFromInternalStorage(getBookid);
                    bookImage.setImageTintList(null);
                    bookImage.setScaleType(ImageButton.ScaleType.FIT_CENTER);
                    bookImage.setImageBitmap(bitmap);
                    tvBookname.setText(item_book.getBook_name());
                    tvBookpage.setText(item_book.getTotal_page() + " page");
                    break;
            }
        }
    }

    private Bitmap loadImageFromInternalStorage(int fileName)
    {

        try {
            File f=new File(getDataDir().getAbsolutePath()+"/app_imageDir", fileName+".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}