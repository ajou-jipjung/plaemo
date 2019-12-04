package com.po771.plaemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.content.res.Resources;

public class PDFEditActivity extends AppCompatActivity implements View.OnClickListener{

    PDFView view;
    int tColor, n=0;

    Paint paintColor = new Paint();
    ImageButton colorButton, borderButton, eraserButton, backButton, fowardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfedit);
        view = new PDFView(this);
        LinearLayout container = findViewById(R.id.viewContainer);
        Resources res = getResources();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        container.addView(view, params);
        //액션바 설정하기//
        //액션바 타이틀 변경하기
        String pdfFileName = getIntent().getStringExtra("pdfFileName");
        getSupportActionBar().setTitle(pdfFileName);
        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //버튼 설정
        borderButton = findViewById(R.id.pdf_pen_border);
        colorButton = findViewById(R.id.pdf_color);
        eraserButton = findViewById(R.id.pdf_eraser);
        backButton = findViewById(R.id.pdf_back);
        fowardButton = findViewById(R.id.pdf_foward);

        borderButton.setOnClickListener(this);
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
                view.SetPenState(1);
                break;
            case R.id.pdf_pen_border:
                Intent intent2 = new Intent(this, PDFBorder_PopupActivity.class);
                startActivityForResult(intent2, 2);
                break;
            case R.id.pdf_eraser:
                view.SetPenState(2);
                break;
            case R.id.pdf_back:
                break;
            case R.id.pdf_foward:
                break;
            default:
                break;
        }
    }

    //색깔 설정해주는 부분
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                String result = data.getStringExtra("result");
                if(result.equals("취소")){
                    return;
                }
                else{
                    int r = Integer.parseInt(result.substring(0,2), 16);
                    int g = Integer.parseInt(result.substring(2,4), 16);
                    int b = Integer.parseInt(result.substring(4,6), 16);;

                    view.setColor(Color.rgb(r,g,b));
                }
            }
        }
        else if(requestCode == 2){
            if(resultCode == RESULT_OK){
                String result = data.getStringExtra("result");
                if(result.equals("취소")){
                    return;
                }
                else{
                    int strokeWidth = Integer.parseInt(result);
                    view.setStrokeWidth(strokeWidth);
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
