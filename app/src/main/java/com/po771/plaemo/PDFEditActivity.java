package com.po771.plaemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.content.res.Resources;

import com.github.barteksc.pdfviewer.PDFView;
import com.po771.plaemo.DB.BaseHelper;
import com.po771.plaemo.item.Item_book;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static android.os.ParcelFileDescriptor.MODE_READ_ONLY;

public class PDFEditActivity extends AppCompatActivity implements View.OnClickListener{

    BaseHelper baseHelper;
    PDFEidtView view;

    ImageButton colorButton, borderButton, eraserButton, backButton, fowardButton;
    ImageView pdfimage;

    int bookId;
    int current_page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfedit);
        view = new PDFEidtView(this);
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
        pdfimage = findViewById(R.id.pdfimage);
        borderButton.setOnClickListener(this);
        colorButton.setOnClickListener(this);
        eraserButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        fowardButton.setOnClickListener(this);
      
        colorButton.setBackgroundResource(R.drawable.round_button2);

        bookId = getIntent().getIntExtra("book_id",1);
        current_page = getIntent().getIntExtra("current_page",1);
        baseHelper = BaseHelper.getInstance(this);
        Item_book item_book = baseHelper.getBook(bookId);
        int readcount=0;

        File pdffile = new File(item_book.getBook_uri());
        Bitmap bitmap;
        try {
            ParcelFileDescriptor fd = ParcelFileDescriptor.open(pdffile,MODE_READ_ONLY);
            PdfiumCore pdfiumCore = new PdfiumCore(this);
            PdfDocument pdfDocument = pdfiumCore.newDocument(fd);
            pdfiumCore.openPage(pdfDocument, current_page);
            int width = pdfiumCore.getPageWidthPoint(pdfDocument, current_page);
            int height = pdfiumCore.getPageHeightPoint(pdfDocument, current_page);
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            pdfiumCore.renderPageBitmap(pdfDocument, bitmap, current_page, 0, 0, width, height);
            ImageView imageView = (ImageView)findViewById(R.id.pdfimage);
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.pdf_color:
                Intent intent = new Intent(this, PDFColor_PopupActivity.class);
                startActivityForResult(intent, 1);
                colorButton.setBackgroundResource(R.drawable.round_button2);
                eraserButton.setBackgroundResource(R.color.blank);
                view.SetPenState(1);
                break;
            case R.id.pdf_pen_border:
                Intent intent2 = new Intent(this, PDFBorder_PopupActivity.class);
                startActivityForResult(intent2, 2);
                break;
            case R.id.pdf_eraser:
                view.SetPenState(2);
                colorButton.setBackgroundResource(R.color.blank);
                eraserButton.setBackgroundResource(R.drawable.round_button2);
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
                overridePendingTransition(0, 0);
                return true;
            case R.id.pdf_edit_finish:
                // 저장
                String fileName = bookId +"_"+current_page;
                savePicture(fileName);
                finish();
                overridePendingTransition(0, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void savePicture(String fileName) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("pdfImageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File file = new File(directory, fileName + ".png"); //임의로 sdcard에 test.png로 저장
        FileOutputStream outputStream = null;

        try {
            file.createNewFile(); // 파일이 있는 경우에만 파일을 생성
            outputStream = new FileOutputStream(file);

            view.buildDrawingCache();
            Bitmap bitmap = view.getDrawingCache();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        } catch (Exception e) {
            Log.w("이미지 저장", "저장 오류");
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
