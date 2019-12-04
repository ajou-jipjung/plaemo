package com.po771.plaemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class PDFBorder_PopupActivity extends Activity {

    EditText border_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pdfborder_popup);

        border_num = (EditText)findViewById(R.id.set_border);
    }

    public void mOnCloseCancle(View v){
        Intent intent = new Intent();
        intent.putExtra("result", "취소");
        setResult(RESULT_OK, intent);

        finish();
    }

    public void mOnCloseSetting(View v){
        Intent intent = new Intent();
        if(border_num.getText().toString().length() == 0){
            Toast.makeText(this, "숫자를 입력해주세요.", Toast.LENGTH_SHORT).show();
        } else if(border_num.getText().toString().equals("0")){
            Toast.makeText(this, "숫자는 0보다 커야합니다.", Toast.LENGTH_SHORT).show();
        }
        else{
            intent.putExtra("result", border_num.getText().toString());
            setResult(RESULT_OK, intent);

            finish();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}
