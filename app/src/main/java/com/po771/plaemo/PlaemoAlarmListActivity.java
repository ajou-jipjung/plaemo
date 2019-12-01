package com.po771.plaemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.po771.plaemo.DB.BaseHelper;
import com.po771.plaemo.item.Item_AlarmList;

import java.util.List;

public class PlaemoAlarmListActivity extends AppCompatActivity {

    Item_AlarmList item_alarmList;
    BaseHelper baseHelper;
    PlaemoAlarmList_Adapter adapter;
    List<Item_AlarmList> alarmList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);

        baseHelper = BaseHelper.getInstance(this);
        alarmList= baseHelper.getAllalarm();

        RecyclerView recyclerView = findViewById(R.id.plaemoalarm_recylcerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new PlaemoAlarmList_Adapter(alarmList);
        recyclerView.setAdapter(adapter);


        //액션바 타이틀 변경
        getSupportActionBar().setTitle("알람리스트");
        Button button = (Button)findViewById(R.id.btn_back);


        //Item 터치이벤트


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
                startActivityForResult(intent,200);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==200){
//            Toast.makeText(this,"check!",Toast.LENGTH_SHORT).show();
            alarmList= baseHelper.getAllalarm();
            adapter.update(alarmList);
        }
    }

}
