package com.po771.plaemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.po771.plaemo.DB.BaseHelper;
import com.po771.plaemo.item.Item_book;
import com.po771.plaemo.item.Item_memo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class DocInfoActivity extends AppCompatActivity implements View.OnClickListener {

    Item_book item_book;
    Button btn_star;
    BaseHelper baseHelper;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.plaemodocinfo_action, menu);
        return true;
    }

    //액션버튼을 클릭했을때의 동작
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.bookaction_addmemo:
                Intent settingIntent = new Intent(this, PlaemoBookNewMemoActivity.class);
                startActivity(settingIntent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_info);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String book_name = getIntent().getStringExtra("book_name");
        int book_id = getIntent().getIntExtra("book_id",1);

        setTitle(book_name+ " - 정보");

        baseHelper = BaseHelper.getInstance(this);
        item_book = baseHelper.getBook(book_id);

        TextView info_bookname = (TextView)findViewById(R.id.info_bookname);
        TextView info_bookpage = (TextView)findViewById(R.id.info_bookpage);
        TextView info_bookinfo = (TextView)findViewById(R.id.info_bookinfo);
        ImageView imageView = (ImageView)findViewById(R.id.info_bookimage);

        btn_star = (Button)findViewById(R.id.info_star);
        btn_star.setOnClickListener(this);
        if(item_book.getBook_star()==1){
            btn_star.setBackground(getDrawable(R.drawable.ic_blackstar_24px));
        }
        findViewById(R.id.info_setting).setOnClickListener(this);

        info_bookname.setText(item_book.getBook_name());
        String pageState=""+item_book.getCurrent_page() + " / "+item_book.getTotal_page();
        info_bookpage.setText(pageState);
        info_bookinfo.setText(item_book.getBook_info());
        imageView.setImageBitmap(loadImageFromInternalStorage(item_book.get_id()));

        BaseHelper baseHelper = BaseHelper.getInstance(this);
        List<Item_memo> memolistList= baseHelper.getBookMemo(book_id);

        RecyclerView recyclerView = findViewById(R.id.info_bookmemolist_recylcerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        PlameoDocInfoMemo_Adapter adapter = new PlameoDocInfoMemo_Adapter(memolistList);
        recyclerView.setAdapter(adapter);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.info_star:
                if(item_book.getBook_star()==1){
                    btn_star.setBackground(getDrawable(R.drawable.ic_star_border_24px));
                    item_book.setBook_star(-1);
                    baseHelper.changeStar(item_book.get_id(),-1);
                }
                else{
                    btn_star.setBackground(getDrawable(R.drawable.ic_blackstar_24px));
                    item_book.setBook_star(1);
                    baseHelper.changeStar(item_book.get_id(),1);
                }
                break;
        }
    }
}
