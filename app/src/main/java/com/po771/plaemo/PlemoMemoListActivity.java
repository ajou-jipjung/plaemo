package com.po771.plaemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import com.po771.plaemo.R;

public class PlemoMemoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plemo_memo_list);

        //액션바 설정하기//
        //액션바 타이틀 변경하기
        getSupportActionBar().setTitle("메모리스트");
        //액션바 배경색 변경
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
