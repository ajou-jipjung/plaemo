package com.po771.plaemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.po771.plaemo.DB.BaseHelper;
import com.po771.plaemo.item.Item_book;
import com.po771.plaemo.item.Item_memo;

public class PlaemoBookNewMemoActivity extends AppCompatActivity {

    private Item_memo new_memo = new Item_memo();
    BaseHelper baseHelper;

    int book_id;
    TextView date;
    EditText start_page;
    EditText end_page;
    EditText context;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.plaemomemoadd_action, menu);
        return true;
    }

    //액션버튼을 클릭했을때의 동작
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.memoadd:
                // 시작, 종료, 제목, 내용을 입력했는지 확인
                if(start_page.getText().toString().getBytes().length <= 0){//빈값이 넘어올때의 처리
                    Toast.makeText(this, "시작페이지를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else if(end_page.getText().toString().getBytes().length <= 0){//빈값이 넘어올때의 처리
                    Toast.makeText(this, "종료페이지를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else if(context.getText().toString().getBytes().length <= 0){//빈값이 넘어올때의 처리
                    Toast.makeText(this, "내용을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    new_memo.setBoook_id(book_id);
                    new_memo.setPage_start(Integer.parseInt(start_page.getText().toString()));
                    new_memo.setPage_end(Integer.parseInt(end_page.getText().toString()));
                    new_memo.setContent(context.getText().toString());
                    new_memo.setDate("2019-11-26 03:17:88");
                    baseHelper.insertMemoList(new_memo);
                    // 이전 페이지로 이동
                    finish();
                    return true;
                }
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_memo);

        //액션바 설정하기//
        //액션바 타이틀 변경하기
        getSupportActionBar().setTitle("새메모");
        //액션바 배경색 변경
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        baseHelper=BaseHelper.getInstance(this);

        start_page=(EditText)findViewById(R.id.start_page);
        end_page=(EditText)findViewById(R.id.end_page);
        context = (EditText)findViewById(R.id.memo_context);

        book_id = getIntent().getIntExtra("book_id", 1);
    }

}
