package com.po771.plaemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.po771.plaemo.DB.BaseHelper;
import com.po771.plaemo.item.Item_alarm;
import com.po771.plaemo.item.Item_book;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PlaemoAlarmPopupActivity extends Activity implements View.OnClickListener {
    TextView book_info;
    TextView book_title;
    ImageView book_cover;
    Button btn_move_to_book;
    Button btn_cancel;
    int book_id;
    int alarm_id;

    BaseHelper baseHelper;
    Item_alarm item_alarm;
    Item_book item_book;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_alarm_popup);

        WindowManager.LayoutParams layoutParams= new WindowManager.LayoutParams();
        layoutParams.flags= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//        layoutParams.dimAmount= 0.8f;
        getWindow().setAttributes(layoutParams);


        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = (int) (dm.widthPixels * 0.732); // Display 사이즈의 90%
        int height = (int) (dm.heightPixels * 0.25); // Display 사이즈의 90%
        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;

        //바탕화면 까맣고 팝업창만 출력되도록.

        alarm_id = getIntent().getIntExtra("alarm_id",-1);

        baseHelper = BaseHelper.getInstance(this);
        item_alarm = baseHelper.getAlarm(alarm_id);
        book_id = item_alarm.getBook_id();
        item_book = baseHelper.getBook(book_id);

        book_info = (TextView)findViewById(R.id.book_info);
        book_title = (TextView)findViewById(R.id.book_title);
        book_cover = (ImageView)findViewById(R.id.book_image);

        Bitmap bitmap = loadImageFromInternalStorage(item_book.get_id());

        book_info.setText(item_book.getBook_info());
        book_title.setText(item_book.getBook_name());
        book_cover.setImageBitmap(bitmap);


        btn_move_to_book = (Button)findViewById(R.id.move_to_book);
        btn_cancel = (Button)findViewById(R.id.cancel_alarm);

        btn_move_to_book.setOnClickListener(this);

    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.move_to_book:
                Intent pdfintent = new Intent (this, PDFViewerActivity.class);
                pdfintent.putExtra("bookId",item_book.get_id());
                pdfintent.putExtra("readState","resume");
                startActivityForResult(pdfintent,200);
                finish();
                break;
            case R.id.cancel_alarm:
                finish();
                break;
        }

    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result", "Close Popup");
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
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

    private Bitmap loadImageFromInternalStorage(int fileName)
    {

        try {
            File f=new File(getDataDir().getAbsolutePath()+"/app_imageDir", fileName+".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }



}
