package com.po771.plaemo;

import com.po771.plaemo.item.Item_book;
import com.po771.plaemo.item.Item_folder;

import java.util.List;

public class DataManager {
    public static DataManager item_data = null;

    public static DataManager getInstance(){ // 싱글턴 패턴으로 구현하였다.
        if(item_data == null){
            item_data = new DataManager();
        }

        return item_data;
    }
    private static List<Item_folder> folderList;
    private static List<Item_book> bookList;

    public static List<Item_book> getBookList() {
        return bookList;
    }

    public static void setBookList(List<Item_book> bookList) {
        DataManager.bookList = bookList;
    }

    public static List<Item_folder> getFolderList() {
        return folderList;
    }

    public static void setFolderList(List<Item_folder> folderList) {
        DataManager.folderList = folderList;
    }

}
