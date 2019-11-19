package com.po771.plaemo.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import com.po771.plaemo.DB.DbSchema.*;
import com.po771.plaemo.item.Item_folder;

public class BaseHelper extends SQLiteOpenHelper {

    public static BaseHelper baseHelper = null;
    private static SQLiteDatabase db;

    public static BaseHelper getInstance(Context context){ // 싱글턴 패턴으로 구현하였다.
        if(baseHelper == null){
            baseHelper = new BaseHelper(context.getApplicationContext());
            db = baseHelper.getWritableDatabase();
        }

        return baseHelper;
    }

    private static final int version = 1;
    private static final String DATABASE_NAME = "po771.db";
    public BaseHelper(Context context) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase create_db) {

        //알람 테이블
        create_db.execSQL("create table " + AlarmTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                AlarmTable.Cols.ALARMNAME + ", " +
                AlarmTable.Cols.HOUR + ", " +
                AlarmTable.Cols.MINUTE + ", " +
                AlarmTable.Cols.REPEAT + ", " +
                AlarmTable.Cols.DAYSOFTHEWEEK + ", " +
                AlarmTable.Cols.ON +
                ")"
        );

        //책 테이블
        create_db.execSQL("create table " + BookTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                BookTable.Cols.BOOKNAME + ", " +
                BookTable.Cols.BOOKURI + ", " +
                BookTable.Cols.CURRNETPAGE + ", " +
                BookTable.Cols.TOTALPAGE + ", " +
                BookTable.Cols.BOOKINFO + ", " +
                BookTable.Cols.STAR + "," +
                BookTable.Cols.FOLDER +
                ")"
        );

        //메모 테이블
        create_db.execSQL("create table " + BookMemo.NAME + "(" +
                " _id integer primary key autoincrement, " +
                BookMemo.Cols.BOOKID + ", " +
                BookMemo.Cols.PAGESTART + ", " +
                BookMemo.Cols.PAGEEND + ", " +
                BookMemo.Cols.CONTENT + ", " +
                BookMemo.Cols.DATA +
                ")"
        );

        //폴더 테이블
        create_db.execSQL("create table " + Folder.NAME + "(" +
                " _id integer primary key autoincrement, " +
                Folder.Cols.FOLDERNAME+ ", " +
                BookMemo.Cols.BOOKID +
                ")"
        );

        //이미지 테이블
        String CREATE_TABLE_IMAGE = "CREATE TABLE " + BookImage.NAME + "("+
                " _id integer primary key autoincrement, " +
                BookImage.Cols.IMAGE + " BLOB);";
        create_db.execSQL(CREATE_TABLE_IMAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

   ///////////////////////////////api들 ////////////////////////////////

    public void initDB(){ // 최초 설치시만 작동하도록

        String query = "SELECT count(*) FROM "+Folder.NAME;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            if (count > 0) {
                return;
            } else {
                Item_folder folder = new Item_folder();

                folder.setBook_id(-1); // 나중에 -1이면 전체 부를것
                folder.setFolder_name("전체");
                baseHelper.insertFolder(folder);

                folder.setBook_id(-2); // 나중에 -3이면 즐겨찾기 부를것
                folder.setFolder_name("즐겨찾기");
                baseHelper.insertFolder(folder);

                //여기서부턴 더미
                folder.setBook_id(1);
                folder.setFolder_name("집교1");
                baseHelper.insertFolder(folder);

                folder.setBook_id(1);
                folder.setFolder_name("집교2");
                baseHelper.insertFolder(folder);

                folder.setBook_id(1);
                folder.setFolder_name("더미1");
                baseHelper.insertFolder(folder);

                folder.setBook_id(1);
                folder.setFolder_name("더미2");
                baseHelper.insertFolder(folder);

                folder.setBook_id(1);
                folder.setFolder_name("더미3");
                baseHelper.insertFolder(folder);

                folder.setBook_id(1);
                folder.setFolder_name("더미4");
                baseHelper.insertFolder(folder);

                folder.setBook_id(1);
                folder.setFolder_name("더미5");
                baseHelper.insertFolder(folder);

                folder.setBook_id(1);
                folder.setFolder_name("더미6");
                baseHelper.insertFolder(folder);

                folder.setBook_id(1);
                folder.setFolder_name("더미7");
                baseHelper.insertFolder(folder);

                folder.setBook_id(1);
                folder.setFolder_name("더미8");
                baseHelper.insertFolder(folder);

                folder.setBook_id(1);
                folder.setFolder_name("더미823123123123");
                baseHelper.insertFolder(folder);

                folder.setBook_id(2);
                folder.setFolder_name("집교2");
                baseHelper.insertFolder(folder);

                folder.setBook_id(2);
                folder.setFolder_name("HCI");
                baseHelper.insertFolder(folder);
            }
        }
    }


    //////////////////folder
    public void insertFolder(Item_folder folder){
        ContentValues values = new ContentValues();
        values.put(Folder.Cols.BOOKID,folder.getBook_id());
        values.put(Folder.Cols.FOLDERNAME,folder.getFolder_name());
        db.insert(Folder.NAME,null,values);
    }

    public List<String> getAllmemo(){
        List<String> folderList = new ArrayList<String>();
        String query = "SELECT DISTINCT "+Folder.Cols.FOLDERNAME+" FROM "+Folder.NAME;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                String folder_name = new String();
                folder_name = cursor.getString(0);
                folderList.add(folder_name);
            } while (cursor.moveToNext());
        }

        return folderList;
    }



//    public void addBook(Item_book book){
////        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(BookTable.Cols.BOOKNAME,book.getBook_name());
//        values.put(BookTable.Cols.BOOKURI,book.getBook_uri());
//        values.put(BookTable.Cols.CURRNETPAGE,book.getCurrent_page());
//        values.put(BookTable.Cols.TOTALPAGE,book.getTotal_page());
//        values.put(BookTable.Cols.STATE,book.getState());
//        values.put(BookTable.Cols.BOOKINFO,book.getBook_info());
//        values.put(BookTable.Cols.IMAGEID,book.getImage_id());
//
//        db.insert(BookTable.NAME,null,values);
//    }

//    public List<Item_book> getAllbooks(){
//        List<Item_book> bookList = new ArrayList<Item_book>();
//
//        String query = "SELECT * FROM "+BookTable.NAME;
//
////        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                Item_book item_book = new Item_book();
//                item_book.set_id(Integer.valueOf(cursor.getString(0)));
//                item_book.setBook_name(cursor.getString(1));
//                item_book.setBook_uri(cursor.getString(2));
//                item_book.setCurrent_page(Integer.valueOf(cursor.getString(3)));
//                item_book.setTotal_page(Integer.valueOf(cursor.getString(4)));
//                item_book.setState(cursor.getString(5));
//                item_book.setBook_info(cursor.getString(6));
//                item_book.setImage_id(Integer.valueOf(cursor.getString(7)));
//                bookList.add(item_book);
//            } while (cursor.moveToNext());
//        }
//
//        return bookList;
//    }

//    public List<Item_image> getAllimages(){
//        List<Item_image> imageList = new ArrayList<Item_image>();
//
//        String query = "SELECT * FROM "+BookImage.NAME;
//
////        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                Item_image item_image = new Item_image();
//                item_image.setImage_id(cursor.getInt(0));
//                byte[] bytes = cursor.getBlob(1);
//                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//                item_image.setImage_bitmap(bitmap);
//
//                imageList.add(item_image);
//            } while (cursor.moveToNext());
//        }
//
//        return imageList;
//    }

//    public int addImage(byte[] image){
//        ContentValues values = new ContentValues();
//        values.put(BookImage.Cols.IMAGE,     image);
//        return (int) db.insert( BookImage.NAME, null, values );
//    }
//
//    public int loadFirstimageid(){
//        String query = "SELECT * FROM "+BookImage.NAME;
//
//        int id=-1;
////        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        if (cursor.moveToFirst()) {
//            do {
//                id = Integer.valueOf(cursor.getString(0));
//            } while (cursor.moveToNext());
//        }
//        return id;
//    }
}
