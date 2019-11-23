package com.po771.plaemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PlaemoEditMemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_memo);

        //액션바 설정하기//
        //액션바 타이틀 변경하기
        getSupportActionBar().setTitle("메모수정");
        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
