package com.po771.plaemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.loader.content.CursorLoader;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.po771.plaemo.DB.BaseHelper;
import com.po771.plaemo.item.Item_book;
import com.po771.plaemo.item.Item_folder;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class AddDocActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private Item_book item_book = new Item_book();
    BaseHelper baseHelper;
    Uri uri=null;
    Bitmap bitmap=null;
    EditText et_bookname;
    EditText tv_page;
    EditText et_bookinfo;
    ImageView iv_bookimage;
    List<String> folderList;
    List<Boolean> folderChecklist=new ArrayList<Boolean>();
    PopupMenu menu;
    ChipGroup chipGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doc);

        setTitle("새 문서");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        baseHelper=BaseHelper.getInstance(this);


        findViewById(R.id.adddoc_image).setOnClickListener(this);
        findViewById(R.id.adddoc_cancle).setOnClickListener(this);
        findViewById(R.id.adddoc_register).setOnClickListener(this);
        findViewById(R.id.adddoc_folderlist).setOnClickListener(this);

        chipGroup=(ChipGroup)findViewById(R.id.adddoc_docchips);
        et_bookname=(EditText)findViewById(R.id.adddoc_title);
        tv_page=(EditText)findViewById(R.id.adddoc_pages);
        et_bookinfo=(EditText)findViewById(R.id.adddoc_info);
        iv_bookimage=(ImageView)findViewById(R.id.adddoc_image);

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int iv_width = (metrics.widthPixels-20)/3;
        int iv_height = (int)(iv_width*1.5);
        iv_bookimage.getLayoutParams().height=iv_height;

        BaseHelper baseHelper = BaseHelper.getInstance(this);
        folderList= baseHelper.getAllmemo();
        folderList.remove("전체");
        folderList.remove("즐겨찾기");

        folderChecklist.add(false);

        for(int i=0;i<folderList.size();i++){
            folderChecklist.add(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_OK);
                finish();
                overridePendingTransition(0, 0);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.adddoc_image:
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT );
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setType("application/pdf");
                try {
                    startActivityForResult(intent, 3000);
                } catch (ActivityNotFoundException e) {
                    //alert user that file manager not working
                    Toast.makeText(this, "Unable to pick file. Check status of file manager.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.adddoc_cancle:
                setResult(RESULT_OK);
                finish();
                overridePendingTransition(0, 0);
                break;
            case R.id.adddoc_register:
                if(bitmap!=null){
                    String bookname =et_bookname.getText().toString();
                    bookname = bookname.replace(" ", "\u00A0");
                    item_book.setBook_name(bookname);

                    String bookinfo =et_bookinfo.getText().toString();
                    bookinfo = bookinfo.replace(" ", "\u00A0");
                    item_book.setBook_info(bookinfo);
                    String folder="";
                    for(int i=1;i<folderChecklist.size();i++){
                        if(folderChecklist.get(i)){
                            folder+=folderList.get(i-1);
                            folder+="/";
                        }
                    }
                    registerDoc(this,uri,item_book.getBook_name());
                    item_book.setFolder(folder);
                    int id = baseHelper.insertBook(item_book);
                    for(int i=1;i<folderChecklist.size();i++){
                        if(folderChecklist.get(i)){
                            Item_folder item_folder = new Item_folder();
                            item_folder.setBook_id(id);
                            item_folder.setFolder_name(folderList.get(i-1));
                            baseHelper.insertFolder(item_folder);
                        }
                    }
                    saveToInternalStorage(bitmap,id);
                    setResult(RESULT_OK);
                    finish();
                    overridePendingTransition(0, 0);
                }
                break;
            case R.id.adddoc_folderlist:
                menu = new PopupMenu(getApplicationContext(),v);
                menu.setOnMenuItemClickListener(this);
                menu.getMenu().add(Menu.NONE, 0, 0, "폴더 추가");

                for(int i=1;i<=folderList.size();i++){
                    menu.getMenu().add(Menu.NONE, i, i, folderList.get(i-1)).setCheckable(true).setChecked(folderChecklist.get(i));
                }
                menu.show();
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) { //메뉴 클릭 이벤트

        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        menuItem.setActionView(new View(this));
        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return false;
            }
        });
//        return false;

        int id = menuItem.getItemId();
        if(id>0){
            if(menuItem.isChecked()){
                menuItem.setChecked(false);
                folderChecklist.set(id,false);
                for(int i=0;i<chipGroup.getChildCount();i++){
                    View childChip = chipGroup.getChildAt(i);
                    String chipTitle = ((Chip)childChip).getText().toString();
                    if(chipTitle.equals(menuItem.getTitle().toString())){
                        chipGroup.removeViewAt(i);
                        break;
                    }

                }
            }
            else {
                menuItem.setChecked(true);
                folderChecklist.set(id,true);
                Chip chip = new Chip(this);
                chip.setText(menuItem.getTitle().toString());
                chip.setTextAppearanceResource(R.style.ChipTextStyle);
                chip.setChipBackgroundColorResource(R.color.chipbackground);
                chipGroup.addView(chip);
            }
            switch (menuItem.getItemId()){
                case 1:
                    break;
            }
        }
        else{ //추가
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View addFolderview = layoutInflater.inflate(R.layout.addfolder,null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(addFolderview);

            final EditText folderTitle = (EditText)addFolderview.findViewById(R.id.addfolder_foldertitle);

            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("추가",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    //추가버튼 이벤트
//                                    result.setText(userInput.getText());
                                    folderList.add(folderTitle.getText().toString());
                                    folderChecklist.add(false);
                                    int i=folderList.size();
                                    menu.getMenu().add(Menu.NONE, i, i, folderList.get(i-1)).setCheckable(true).setChecked(folderChecklist.get(i));
                                }
                            })
                    .setNegativeButton("취소",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    dialog.cancel();
                                }
                            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }
        return false;
    }

    String filename;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Toast.makeText(getApplicationContext(),String.valueOf(resultCode), Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(),String.valueOf(resultCode), Toast.LENGTH_SHORT).show();
        if(resultCode==RESULT_OK){
            switch (requestCode) {
                case 3000:
                    uri = data.getData();
                    Log.d("check_uri",uri.toString());
//                test(this,uri);
                    setItem_book(uri);
                    break;

            }
        }
    }

    void setItem_book(Uri pdfUri) {
        String book_name = uri2filename(pdfUri);
        int total_page=0;

//        item_book.setBook_uri(pdfUri.toString());
        item_book.setBook_name(book_name);
        et_bookname.setText(book_name);

        item_book.setCurrent_page(1);

        int pageNumber = 0;
        PdfiumCore pdfiumCore = new PdfiumCore(this);
        try {
            //http://www.programcreek.com/java-api-examples/index.php?api=android.os.ParcelFileDescriptor
            ParcelFileDescriptor fd = getContentResolver().openFileDescriptor(pdfUri, "r");
            PdfDocument pdfDocument = pdfiumCore.newDocument(fd);
            total_page = pdfiumCore.getPageCount(pdfDocument);
            item_book.setTotal_page(total_page);
            tv_page.setText("총 페이지 : "+total_page + " page");
            pdfiumCore.openPage(pdfDocument, pageNumber);
            int width = pdfiumCore.getPageWidthPoint(pdfDocument, pageNumber);
            int height = pdfiumCore.getPageHeightPoint(pdfDocument, pageNumber);
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            pdfiumCore.renderPageBitmap(pdfDocument, bitmap, pageNumber, 0, 0, width, height);
            iv_bookimage.getLayoutParams().height= ViewGroup.LayoutParams.WRAP_CONTENT;
            iv_bookimage.setImageTintList(null);
            iv_bookimage.setScaleType(ImageButton.ScaleType.FIT_CENTER);
            iv_bookimage.requestLayout();
            iv_bookimage.setImageBitmap(bitmap);
//            iv_bookimage.setBackground(null);
            pdfiumCore.closeDocument(pdfDocument); // important!
        } catch(Exception e) {
            //todo with exception
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage,int fileName){

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int layoutwidth = displaymetrics.widthPixels / 3;

        Bitmap resized = resizeBitmapImage(bitmapImage,layoutwidth);


        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, fileName + ".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            resized.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    public Bitmap resizeBitmapImage(Bitmap source, int maxResolution)
    {
        int width = source.getWidth();
        int height = source.getHeight();
        int newWidth = width;
        int newHeight = height;
        float rate = 0.0f;

        if(width > height)
        {
            if(maxResolution < width)
            {
                rate = maxResolution / (float) width;
                newHeight = (int) (height * rate);
                newWidth = maxResolution;
            }
        }
        else
        {
            if(maxResolution < height)
            {
                rate = maxResolution / (float) height;
                newWidth = (int) (width * rate);
                newHeight = maxResolution;
            }
        }

        return Bitmap.createScaledBitmap(source, newWidth, newHeight, true);
    }

    private String uri2filename(Uri uri) {
        Cursor returnCursor = getContentResolver().query(uri, null, null, null, null);
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        filename = returnCursor.getString(nameIndex);
        return filename;
    }

    public void registerDoc(Context context,Uri uri,String bookname){
        Cursor returnCursor = getContentResolver().query(uri, null, null, null, null);
        returnCursor.moveToFirst();
        String sourcePath = getExternalFilesDir(null).toString();
        try {
            copyFileStream(new File(sourcePath + "/" + bookname), uri,this);
            Log.d("saveFile",sourcePath + "/" + bookname);
            item_book.setBook_uri(sourcePath + "/" + bookname);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void copyFileStream(File dest, Uri uri, Context context)
            throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = context.getContentResolver().openInputStream(uri);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;

            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            is.close();
            os.close();
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
        finish();
        overridePendingTransition(0, 0);
    }
}
