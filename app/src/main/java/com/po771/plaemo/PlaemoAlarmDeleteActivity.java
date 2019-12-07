package com.po771.plaemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.po771.plaemo.DB.BaseHelper;
import com.po771.plaemo.item.Item_alarm;

import java.util.List;

public class PlaemoAlarmDeleteActivity extends AppCompatActivity {
    Item_alarm item_alarmList;
    BaseHelper baseHelper;
    PlaemoAlarmDelete_Adapter adapter;
    List<Item_alarm> alarmList;
    AlarmLoader alarmLoader;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plaemo_alarm_delete);

        baseHelper = BaseHelper.getInstance(this);
        alarmLoader = AlarmLoader.getInstance(this);
        alarmList= baseHelper.getAllalarm();

        RecyclerView recyclerView = findViewById(R.id.plaemoalarmdelete_recylcerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new PlaemoAlarmDelete_Adapter(alarmList,baseHelper);
        recyclerView.setAdapter(adapter);


        //액션바 타이틀 변경
        getSupportActionBar().setTitle("알람 삭제");

        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(0, 0);
                return true;
            case R.id.mainalarmaction_alarm_delete:
                for(int i=0; i<100; i++){
                    if(PlaemoAlarmDelete_Adapter.select_id_list[i] != 0){
                        baseHelper.deleteAlarm(PlaemoAlarmDelete_Adapter.select_id_list[i]);
                    }
                }
                finish();
                overridePendingTransition(0, 0);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.plaemoalarmdelete_action, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, 0);
    }

}
