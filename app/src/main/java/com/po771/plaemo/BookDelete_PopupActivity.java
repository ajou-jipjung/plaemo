package com.po771.plaemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.po771.plaemo.DB.BaseHelper;
import com.po771.plaemo.item.Item_book;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BookDelete_PopupActivity extends Activity {

    TextView booktitle;
    ImageView bookimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.book_delete_popup);

        //UI 객체생성
        booktitle = (TextView)findViewById(R.id.book_title);
        bookimg = (ImageView)findViewById(R.id.book_image);

        //데이터 가져오기
        Intent intent = getIntent();
        BaseHelper baseHelper = BaseHelper.getInstance(this);
        int book_id = intent.getIntExtra("book_id",1);
        Item_book item_book = baseHelper.getBook(book_id);


        bookimg.setImageBitmap(loadImageFromInternalStorage(item_book.get_id()));
        booktitle.setText(item_book.getBook_name());
    }

    //취소 버튼 클릭
    public void mOnCloseCancle(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result", "취소");
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }

    //삭제 버튼 클릭
    public void mOnCloseDelete(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result", "삭제");
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
