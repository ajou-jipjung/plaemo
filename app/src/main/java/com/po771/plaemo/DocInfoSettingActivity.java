package com.po771.plaemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.po771.plaemo.DB.BaseHelper;
import com.po771.plaemo.item.Item_book;
import com.po771.plaemo.item.Item_folder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class DocInfoSettingActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener

{

    BaseHelper baseHelper;
    int book_id;
    Item_book item_book;
    EditText et_bookname;
    EditText tv_page;
    EditText et_bookinfo;
    ImageView iv_bookimage;
    List<String> folderList;
    List<Boolean> folderChecklist=new ArrayList<Boolean>();
    PopupMenu menu;
    ChipGroup chipGroup;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.plaemodelete_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_OK);
                finish();
                return true;
            case R.id.book_delete:
                // 여기에 책 삭제 코드 작성하기
                Intent intent = new Intent(this, BookDelete_PopupActivity.class);
                intent.putExtra("book_id", book_id);
                startActivityForResult(intent, 1);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requsetCode, int resultCode, Intent data){
        if(requsetCode == 1){
            if(resultCode==RESULT_OK){
                //값 확인
                String result = data.getStringExtra("result");
                if(result.equals("삭제")){
                    baseHelper.deleteBook(book_id);
                    // 수정필요) 뒤로가기 두번 되도록
                    Intent intent = new Intent(this, PlaemoMainFolderActivity.class);
                    Toast.makeText(this, "삭제완료", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                // 아닐경우 "취소"이기 때문에 아무일도 일어나지 않음
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_info_setting);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        book_id = getIntent().getIntExtra("book_id",1);


        baseHelper = BaseHelper.getInstance(this);
        item_book = baseHelper.getBook(book_id);
        setTitle(item_book.getBook_name()+ " - 설정");

        findViewById(R.id.infosetting_cancle).setOnClickListener(this);
        findViewById(R.id.infosetting_register).setOnClickListener(this);
        findViewById(R.id.infosetting_folderlist).setOnClickListener(this);

        chipGroup=(ChipGroup)findViewById(R.id.infosetting_docchips);
        et_bookname=(EditText)findViewById(R.id.infosetting_title);
        tv_page=(EditText)findViewById(R.id.infosetting_pages);
        et_bookinfo=(EditText)findViewById(R.id.infosetting_info);
        iv_bookimage=(ImageView)findViewById(R.id.infosetting_image);

        et_bookname.setText(item_book.getBook_name());
        tv_page.setText("총 페이지 : "+item_book.getTotal_page() + " page");
        et_bookinfo.setText(item_book.getBook_info());
        iv_bookimage.setImageBitmap(loadImageFromInternalStorage(item_book.get_id()));

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int iv_width = (metrics.widthPixels-20)/3;
        int iv_height = (int)(iv_width*1.5);
        iv_bookimage.getLayoutParams().height=iv_height;
        folderList= baseHelper.getAllmemo();
        folderList.remove("전체");
        folderList.remove("즐겨찾기");

        folderChecklist.add(false);

        for(int i=0;i<folderList.size();i++){
            folderChecklist.add(false);
        }

        //chip추가
        String[] folders = item_book.getFolder().split("/");
        for(int i=0;i<folders.length;i++){
            if(folders[i].equals("")){
                break;
            }
            int id = folderList.indexOf(folders[i]);
            folderChecklist.set(id+1,true);
            Chip chip = new Chip(this);
            chip.setText(folders[i]);
            chip.setTextAppearanceResource(R.style.ChipTextStyle);
            chip.setChipBackgroundColorResource(R.color.chipbackground);
            chipGroup.addView(chip);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.infosetting_cancle:
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.infosetting_folderlist:
                menu = new PopupMenu(getApplicationContext(), v);
                menu.setOnMenuItemClickListener(this);
                menu.getMenu().add(Menu.NONE, 0, 0, "폴더 추가");

                for (int i = 1; i <= folderList.size(); i++) {
                    menu.getMenu().add(Menu.NONE, i, i, folderList.get(i - 1)).setCheckable(true).setChecked(folderChecklist.get(i));
                }
                menu.show();
                break;
            case R.id.infosetting_register:
                item_book.setBook_name(et_bookname.getText().toString());
                item_book.setBook_info(et_bookinfo.getText().toString());
                String folder = "";
                for (int i = 1; i < folderChecklist.size(); i++) {
                    if (folderChecklist.get(i)) {
                        folder += folderList.get(i - 1);
                        folder += "/";
                    }
                }
                item_book.setFolder(folder);
//                int id = baseHelper.insertBook(item_book);
                baseHelper.updateBook(item_book);
                baseHelper.clearFolder(item_book.get_id());
                for (int i = 1; i < folderChecklist.size(); i++) {
                    if (folderChecklist.get(i)) {
                        Item_folder item_folder = new Item_folder();
                        item_folder.setBook_id(item_book.get_id());
                        item_folder.setFolder_name(folderList.get(i - 1));
                        baseHelper.insertFolder(item_folder);
                    }
                }
                setResult(RESULT_OK);
                finish();
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

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
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
