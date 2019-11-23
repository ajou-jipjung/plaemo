package com.po771.plaemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.po771.plaemo.DB.BaseHelper;
import com.po771.plaemo.item.Item_memo;

import java.util.List;

public class PlaemoMemoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plaemo_memo_list);

        //액션바 설정하기//
        //액션바 타이틀 변경하기
        getSupportActionBar().setTitle("메모리스트");
        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BaseHelper baseHelper = BaseHelper.getInstance(this);
        List<Item_memo> memolistList= baseHelper.getMemos();

        RecyclerView recyclerView = findViewById(R.id.plemomemolist_recylcerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        PlaemoMemoList_Adapter adapter = new PlaemoMemoList_Adapter(memolistList);
        recyclerView.setAdapter(adapter);
    }
}