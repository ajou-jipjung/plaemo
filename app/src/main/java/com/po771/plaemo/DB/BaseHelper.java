package com.po771.plaemo.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.po771.plaemo.DB.DbSchema.*;
import com.po771.plaemo.item.Item_book;
import com.po771.plaemo.item.Item_folder;
import com.po771.plaemo.item.Item_memo;

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
        create_db.execSQL("create table " + BookList.NAME + "(" +
                " _id integer primary key autoincrement, " +
                BookList.Cols.BOOKNAME + ", " +
                BookList.Cols.BOOKURI + ", " +
                BookList.Cols.CURRNETPAGE + ", " +
                BookList.Cols.TOTALPAGE + ", " +
                BookList.Cols.BOOKINFO + ", " +
                BookList.Cols.STAR + "," +
                BookList.Cols.FOLDER +
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    ///////////////////////////////api들 ////////////////////////////////

    public boolean initDB(){ // 최초 설치시만 작동하도록

        String query = "SELECT count(*) FROM "+Folder.NAME;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            if (count > 0) {
                return false;
            } else {
                //////////////////////////메모리스트////////////////////////////////
                Item_memo memolist = new Item_memo();

                memolist.setPage_start(789);
                memolist.setPage_end(987);
                memolist.setContent("안녕하세요. 메모 리스트입니다.");
                memolist.setDate("2019-11-12 10:00:35");
                baseHelper.insertMemoList(memolist);

                memolist.setPage_start(123);
                memolist.setPage_end(321);
                memolist.setContent("어려워요 ㅜㅜㅜ. 메모입니다. 어디까지 입력을 받을 수 있는 지 볼까요??!!");
                memolist.setDate("2019-11-20 09:24:00");
                baseHelper.insertMemoList(memolist);

                memolist.setPage_start(456);
                memolist.setPage_end(654);
                memolist.setContent("마지막. 메모입니다. 내용이 초과되면 어디까지 출력되는지 보기위해서 내용을 많이 입력해보도록 하겠습니다. 내용이 초과될때는 한번 터치하면 전체를 출력하고 다시 터치하면 잘린 내용을 출력하도록 제가 한번 구현해보도록 하겠습니다.");
                memolist.setDate("2019-11-30 12:41:00");
                baseHelper.insertMemoList(memolist);

                memolist.setPage_start(789);
                memolist.setPage_end(987);
                memolist.setContent("안녕하세요. 메모 리스트입니다. 이번에는 엄청 긴 글의 메모리스트를 만들어 볼 예정이에요. 맨 아래안녕하세요. 메모 리스트입니다. 이번에는 엄청 긴 글의 메모리스트를 만들어 볼 예정이에요. 맨 아래안녕하세요. 메모 리스트입니다. 이번에는 엄청 긴 글의 메모리스트를 만들어 볼 예정이에요. 맨 아래안녕하세요. 메모 리스트입니다. 이번에는 엄청 긴 글의 메모리스트를 만들어 볼 예정이에요. 맨 아래안녕하세요. 메모 리스트입니다. 이번에는 엄청 긴 글의 메모리스트를 만들어 볼 예정이에요. 맨 아래안녕하세요. 메모 리스트입니다. 이번에는 엄청 긴 글의 메모리스트를 만들어 볼 예정이에요. 맨 아래안녕하세요. 메모 리스트입니다. 이번에는 엄청 긴 글의 메모리스트를 만들어 볼 예정이에요. 맨 아래안녕하세요. 메모 리스트입니다. 이번에는 엄청 긴 글의 메모리스트를 만들어 볼 예정이에요. 맨 아래안녕하세요. 메모 리스트입니다. 이번에는 엄청 긴 글의 메모리스트를 만들어 볼 예정이에요. 맨 아래안녕하세요. 메모 리스트입니다. 이번에는 엄청 긴 글의 메모리스트를 만들어 볼 예정이에요. 맨 아래안녕하세요. 메모 리스트입니다. 이번에는 엄청 긴 글의 메모리스트를 만들어 볼 예정이에요. 맨 아래안녕하세요. 메모 리스트입니다. 이번에는 엄청 긴 글의 메모리스트를 만들어 볼 예정이에요. 맨 아래안녕하세요. 메모 리스트입니다. 이번에는 엄청 긴 글의 메모리스트를 만들어 볼 예정이에요. 맨 아래에 있는 애만 이상하게 안되는 거 같더라구요. 왜그러는 걸까요 ㅜㅜ 저는 개발을 잘하고 싶어요~ 돈도 많이 벌고 싶어요. 그래서 이렇게 데이터를 집어넣는 중이랍니다.");
                memolist.setDate("2019-11-12 10:00:35");
                baseHelper.insertMemoList(memolist);

                memolist.setPage_start(123);
                memolist.setPage_end(321);
                memolist.setContent("어려워요 ㅜㅜㅜ. 메모입니다. 여기에는 내용을 진짜 많이 입력할 수 있어요. 어디까지 입력을 받을 수 있는 지 볼까요??!! 아마 여기까지는 전부다 보일겁니다~ 잘보이시죠? 문장을 길게 쓴다는 일은 참 어려운 거 같아요. 오늘 점심으로 저는 치즈돈까스를 먹었는데 너무 맛있었어요.");
                memolist.setDate("2019-11-20 09:24:00");
                baseHelper.insertMemoList(memolist);

                memolist.setPage_start(456);
                memolist.setPage_end(654);
                memolist.setContent("마지막. 메모입니다. 내용이 초과되면 어디까지 출력되는지 보기위해서 내용을 많이 입력해보도록 하겠습니다. 내용이 초과될때는 한번 터치하면 전체를 출력하고 다시 터치하면 잘린 내용을 출력하도록 제가 한번 구현해보도록 하겠습니다.");
                memolist.setDate("2019-11-30 12:41:00");
                baseHelper.insertMemoList(memolist);

                memolist.setPage_start(1);
                memolist.setPage_end(2);
                memolist.setContent("안녕하세요. 메모 리스트입니다. 이번에는 엄청 긴 글의 메모리스트를 만들어 볼 예정이에요. 맨 아래에 있는 애만 이상하게 안되는 거 같더라구요. 왜그러는 걸까요 ㅜㅜ 저는 개발을 잘하고 싶어요~ 돈도 많이 벌고 싶어요. 그래서 이렇게 데이터를 집어넣는 중이랍니다.");
                memolist.setDate("2019-11-12 10:00:35");
                baseHelper.insertMemoList(memolist);

                memolist.setPage_start(3);
                memolist.setPage_end(4);
                memolist.setContent("어려워요 ㅜㅜㅜ. 메모입니다. 여기에는 내용을 진짜 많이 입력할 수 있어요. 어디까지 입력을 받을 수 있는 지 볼까요??!! 아마 여기까지는 전부다 보일겁니다~ 잘보이시죠? 문장을 길게 쓴다는 일은 참 어려운 거 같아요. 오늘 점심으로 저는 치즈돈까스를 먹었는데 너무 맛있었어요.");
                memolist.setDate("2019-11-20 09:24:00");
                baseHelper.insertMemoList(memolist);

                memolist.setPage_start(5);
                memolist.setPage_end(6);
                memolist.setContent("마지막. 메모입니다. 내용이 초과되면 어디까지 출력되는지 보기위해서 내용을 많이 입력해보도록 하겠습니다. 내용이 초과될때는 한번 터치하면 전체를 출력하고 다시 터치하면 잘린 내용을 출력하도록 제가 한번 구현해보도록 하겠습니다.");
                memolist.setDate("2019-11-30 12:41:00");
                baseHelper.insertMemoList(memolist);

                memolist.setPage_start(7);
                memolist.setPage_end(8);
                memolist.setContent("안녕하세요. 메모 리스트입니다. 이번에는 엄청 긴 글의 메모리스트를 만들어 볼 예정이에요. 맨 아래에 있는 애만 이상하게 안되는 거 같더라구요. 왜그러는 걸까요 ㅜㅜ 저는 개발을 잘하고 싶어요~ 돈도 많이 벌고 싶어요. 그래서 이렇게 데이터를 집어넣는 중이랍니다.");
                memolist.setDate("2019-11-12 10:00:35");
                baseHelper.insertMemoList(memolist);

                memolist.setPage_start(11);
                memolist.setPage_end(12);
                memolist.setContent("어려워요 ㅜㅜㅜ. 메모입니다. 여기에는 내용을 진짜 많이 입력할 수 있어요. 어디까지 입력을 받을 수 있는 지 볼까요??!! 아마 여기까지는 전부다 보일겁니다~ 잘보이시죠? 문장을 길게 쓴다는 일은 참 어려운 거 같아요. 오늘 점심으로 저는 치즈돈까스를 먹었는데 너무 맛있었어요.");
                memolist.setDate("2019-11-20 09:24:00");
                baseHelper.insertMemoList(memolist);

                memolist.setPage_start(43);
                memolist.setPage_end(355);
                memolist.setContent("마지막. 메모입니다. 내용이 초과되면 어디까지 출력되는지 보기위해서 내용을 많이 입력해보도록 하겠습니다. 내용이 초과될때는 한번 터치하면 전체를 출력하고 다시 터치하면 잘린 내용을 출력하도록 제가 한번 구현해보도록 하겠습니다.");
                memolist.setDate("2019-11-30 12:41:00");
                baseHelper.insertMemoList(memolist);
                return true;
            }
        }
        return true;
    }


    //////////////////folder
    public void insertFolder(Item_folder item_folder){
        ContentValues values = new ContentValues();
        values.put(Folder.Cols.BOOKID,item_folder.getBook_id());
        values.put(Folder.Cols.FOLDERNAME,item_folder.getFolder_name());
        db.insert(Folder.NAME,null,values);
    }
    public void insertMemoList(Item_memo memolist){
        ContentValues values = new ContentValues();
        values.put(BookMemo.Cols.PAGESTART,memolist.getPage_start());
        values.put(BookMemo.Cols.PAGEEND,memolist.getPage_end());
        values.put(BookMemo.Cols.CONTENT,memolist.getContent());
        values.put(BookMemo.Cols.DATA, memolist.getDate());
        db.insert(BookMemo.NAME,null,values);
    }

    public List<Item_memo> getMemos(){
        List<Item_memo> memoList = new ArrayList<Item_memo>();
        String query = "SELECT "+
                BookMemo.Cols.PAGESTART+", "+BookMemo.Cols.PAGEEND+", "+BookMemo.Cols.CONTENT+", "+BookMemo.Cols.DATA+
                " FROM "+BookMemo.NAME;
        //String query = "SELECT * FROM "+BookMemo.NAME;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Log.d("qweqwe",cursor.getString(0) + cursor.getString(1));
                // String pagestart = new String(cursor.getString(0));
                //  String content = new String(cursor.getString(1));
                // String date = new String(cursor.getString(2));
                Item_memo item = new Item_memo();
                item.setPage_start(cursor.getInt(0));
                item.setPage_end(cursor.getInt(1));
                //  item.setPage_start(Integer.getInteger(cursor.getString(0)));
                item.setContent(cursor.getString(2));
                item.setDate(cursor.getString(3));
                memoList.add(item);
                //memoList.add(folder_name);
            } while (cursor.moveToNext());
        }

        return memoList;
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

    ////////////////book

    public List<Item_book> getAllbookinfolder(String folder_name){

        List<Item_book> bookList = new ArrayList<Item_book>();
        String query1 = "SELECT * FROM "+BookList.NAME;
        String query2 = "SELECT * FROM "+BookList.NAME +" WHERE book_star=1";
        String query3 = "select * from "+BookList.NAME+" WHERE "+BookList.NAME+"._id in (SELECT "+Folder.Cols.BOOKID+" FROM "+Folder.NAME +" WHERE "+Folder.Cols.FOLDERNAME+" = "+ "\""+folder_name+"\")";
        String query=null;
        if(folder_name.equals("전체")){
            query=query1;
        }
        else if(folder_name.equals("즐겨찾기")){
            query=query2;
        }
        else{
            query=query3;
        }
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Item_book item_book = new Item_book();
                item_book.set_id(cursor.getInt(0));
                item_book.setBook_name(cursor.getString(1));
                item_book.setBook_uri(cursor.getString(2));
                item_book.setCurrent_page(cursor.getInt(3));
                item_book.setTotal_page(cursor.getInt(4));
                item_book.setBook_info(cursor.getString(5));
                item_book.setBook_star(cursor.getInt(6));
                item_book.setFolder(cursor.getString(7));
                bookList.add(item_book);
            } while (cursor.moveToNext());
        }
        return  bookList;
    }

    public int insertBook(Item_book item_book){
        ContentValues values = new ContentValues();
        values.put(BookList.Cols.BOOKNAME,item_book.getBook_name());
        values.put(BookList.Cols.BOOKURI,item_book.getBook_uri());
        values.put(BookList.Cols.CURRNETPAGE,item_book.getCurrent_page());
        values.put(BookList.Cols.TOTALPAGE,item_book.getTotal_page());
        values.put(BookList.Cols.BOOKINFO,item_book.getBook_info());
        values.put(BookList.Cols.STAR,item_book.getBook_star());
        values.put(BookList.Cols.FOLDER,item_book.getFolder());
        int id = (int)db.insert(BookList.NAME,null,values);
        return id;
    }

    public void changeStar(int id,int starState){
//        String query = "UPDATE "+BookList.NAME+" SET "+BookList.Cols.STAR+" = "+starState+" WHERE _id="+id;
//        db.rawQuery(query,null);
        ContentValues values = new ContentValues();
        values.put(BookList.Cols.STAR,starState);         // 바꿀값
        db.update(BookList.NAME,values,"_id="+id,null);
    }

    public void changePage(int id,int page){
//        String query = "UPDATE "+BookList.NAME+" SET "+BookList.Cols.STAR+" = "+starState+" WHERE _id="+id;
//        db.rawQuery(query,null);
        ContentValues values = new ContentValues();
        values.put(BookList.Cols.CURRNETPAGE,page);         // 바꿀값
        db.update(BookList.NAME,values,"_id="+id,null);
    }

    public Item_book getBook(int id){
        String query = "SELECT * FROM "+BookList.NAME +" WHERE _id="+id;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            Item_book item_book = new Item_book();
            item_book.set_id(cursor.getInt(0));
            item_book.setBook_name(cursor.getString(1));
            item_book.setBook_uri(cursor.getString(2));
            item_book.setCurrent_page(cursor.getInt(3));
            item_book.setTotal_page(cursor.getInt(4));
            item_book.setBook_info(cursor.getString(5));
            item_book.setBook_star(cursor.getInt(6));
            item_book.setFolder(cursor.getString(7));
            return item_book;
        }
        return null;
    }

    public List<Item_book> getAllBook(){
        List<Item_book> bookList = new ArrayList<Item_book>();
        String query = "SELECT * FROM "+BookList.NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Item_book item_book = new Item_book();
                item_book.set_id(cursor.getInt(0));
                item_book.setBook_name(cursor.getString(1));
                item_book.setBook_uri(cursor.getString(2));
                item_book.setCurrent_page(cursor.getInt(3));
                item_book.setTotal_page(cursor.getInt(4));
                item_book.setBook_info(cursor.getString(5));
                item_book.setBook_star(cursor.getInt(6));
                item_book.setFolder(cursor.getString(7));
                bookList.add(item_book);
            } while (cursor.moveToNext());
        }
        return  bookList;
    }

    public List<Item_book> getStarBook(){
        List<Item_book> bookList = new ArrayList<Item_book>();
        String query = "SELECT * FROM "+BookList.NAME +" WHERE book_star=1";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Item_book item_book = new Item_book();
                item_book.set_id(cursor.getInt(0));
                item_book.setBook_name(cursor.getString(1));
                item_book.setBook_uri(cursor.getString(2));
                item_book.setCurrent_page(cursor.getInt(3));
                item_book.setTotal_page(cursor.getInt(4));
                item_book.setBook_info(cursor.getString(5));
                item_book.setBook_star(cursor.getInt(6));
                item_book.setFolder(cursor.getString(7));
                bookList.add(item_book);
            } while (cursor.moveToNext());
        }
        return  bookList;
    }

    public List<Item_folder> getAllFolder(){
        List<Item_folder> folderList = new ArrayList<Item_folder>();
        String query = "SELECT * FROM "+Folder.NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Item_folder item_folder = new Item_folder();
                item_folder.set_id(cursor.getInt(0));
                item_folder.setFolder_name(cursor.getString(1));
                item_folder.setBook_id(cursor.getInt(2));
                folderList.add(item_folder);
            } while (cursor.moveToNext());
        }
        return  folderList;
    }

    public void check2(){
        return;
    }
}
