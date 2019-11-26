package com.po771.plaemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.po771.plaemo.DB.BaseHelper;
import com.po771.plaemo.item.Item_AlarmList;

import java.util.List;

public class PlaemoAlarmListActivity extends AppCompatActivity {

    Item_AlarmList item_alarmList;
    BaseHelper baseHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);

        BaseHelper baseHelper = BaseHelper.getInstance(this);
        List<Item_AlarmList> alarmList= baseHelper.getAllalarm();

        RecyclerView recyclerView = findViewById(R.id.plaemoalarm_recylcerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        PlaemoAlarmList_Adapter adapter = new PlaemoAlarmList_Adapter(alarmList);
        recyclerView.setAdapter(adapter);


        //액션바 타이틀 변경
        getSupportActionBar().setTitle("알람리스트");
        Button button = (Button)findViewById(R.id.btn_back);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.plaemomainalarm_action, menu);
        return true;
    }

    //알람 버튼 활성화
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mainalarmaction_alarm_add:
                Intent intent = new Intent(getApplicationContext(), PlaemoAlarmSetActivity.class);
//                intent.putExtra("alarm_id",(item_alarmList.get_id()));
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }






}
