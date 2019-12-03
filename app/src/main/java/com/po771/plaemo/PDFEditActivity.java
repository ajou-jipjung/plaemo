package com.po771.plaemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class PDFEditActivity extends AppCompatActivity implements View.OnClickListener{

    Paint paintColor = new Paint();
    TextView txt;
    ImageButton colorButton, eraserButton, backButton, fowardButton;

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

        txt = findViewById(R.id.checkresult);

        colorButton = findViewById(R.id.pdf_color);
        eraserButton = findViewById(R.id.pdf_eraser);
        backButton = findViewById(R.id.pdf_back);
        fowardButton = findViewById(R.id.pdf_foward);

        colorButton.setOnClickListener(this);
        eraserButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        fowardButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.pdf_color:
                Intent intent = new Intent(this, PDFColor_PopupActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.pdf_eraser:
                break;
            case R.id.pdf_back:
                break;
            case R.id.pdf_foward:
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                String result = data.getStringExtra("result");
                if(result.equals("취소")){
                    txt.setText(result);
                    return;
                }
                else{
                    txt.setText(result);
                }
            }
        }
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
