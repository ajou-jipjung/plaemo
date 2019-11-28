package com.po771.plaemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.po771.plaemo.DB.BaseHelper;
import com.po771.plaemo.item.Item_memo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlaemoEditMemoActivity extends AppCompatActivity {

    BaseHelper baseHelper;
    Item_memo memo = new Item_memo();

    int memo_id;
    int book_id;
    TextView date;
    EditText start_page;
    EditText end_page;
    EditText context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_memo);

        //액션바 설정하기//
        //액션바 타이틀 변경하기
        getSupportActionBar().setTitle("메모수정");
        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        baseHelper=BaseHelper.getInstance(this);
        memo_id = getIntent().getIntExtra("memo_id", 1);
//        Log.w("넘겨받은 memo_id", String.valueOf(memo_id));
        memo = baseHelper.getEditMemo(memo_id);
//        Log.w("DB에서 받은 memo_id", String.valueOf(memo_id));
        date = (TextView)findViewById(R.id.memodate);
        start_page = (EditText)findViewById(R.id.start_page);
        end_page = (EditText)findViewById(R.id.end_page);
        context = (EditText)findViewById(R.id.memo_context);

        date.setText("마지막 수정시간: "+memo.getDate());
        start_page.setText(String.valueOf(memo.getPage_start()));
        end_page.setText(String.valueOf(memo.getPage_end()));
        context.setText(memo.getContent());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.plaemomemoedit_action, menu);
        return true;
    }

    //액션버튼을 클릭했을때의 동작
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.memoedit:
                // 내용 수정 저장
                memo.set_id(memo_id);
                memo.setBoook_id(book_id);
                memo.setPage_start(Integer.parseInt(start_page.getText().toString()));
                memo.setPage_end(Integer.parseInt(end_page.getText().toString()));
                memo.setContent(context.getText().toString());

                SimpleDateFormat nowtime = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
                Date time = new Date();
                memo.setDate(nowtime.format(time));

                baseHelper.editBookMemo(memo);
                Toast.makeText(this, "수정 완료", Toast.LENGTH_SHORT).show();
                // 이전 페이지로 이동
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
