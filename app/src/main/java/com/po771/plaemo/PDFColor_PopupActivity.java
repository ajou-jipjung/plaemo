package com.po771.plaemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Switch;

public class PDFColor_PopupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.palette_popup);

    }

    public void mOnColor(View v){
        Intent intent = new Intent();
        String value = "000000";
        switch (v.getId()){
            case R.id.color1:
                value = "FF1744";
                break;
            case R.id.color2:
                value = "FF6F00";
                break;
            case R.id.color3:
                value = "FFEA00";
                break;
            case R.id.color4:
                value = "388E3C";
                break;
            case R.id.color5:
                value = "0288D1";
                break;
            case R.id.color6:
                value = "3D5AFE";
                break;
            case R.id.color7:
                value = "651FFF";
                break;
            case R.id.color8:
                value = "FFFFFF";
                break;
            case R.id.color9:
                value = "5A5A5A";
                break;
            case R.id.color10:
                value = "000000";
                break;
            default:
                break;
        }
        intent.putExtra("result", value);
        setResult(RESULT_OK, intent);

        finish();
    }

    public void mOnClose(View v){
        Intent intent = new Intent();
        intent.putExtra("result", "취소");
        setResult(RESULT_OK, intent);

        finish();
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
