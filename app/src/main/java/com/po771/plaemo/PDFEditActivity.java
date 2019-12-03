package com.po771.plaemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class PDFEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfedit);
        //액션바 설정하기//
        //액션바 타이틀 변경하기
        String pdfFileName = getIntent().getStringExtra("pdfFileName");
        getSupportActionBar().setTitle(pdfFileName);
        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.plaemopdfedit_action, menu);
        return true;
    }

    //액션버튼을 클릭했을때의 동작
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // 취소됨
                finish();
                return true;
            case R.id.pdf_edit_finish:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
