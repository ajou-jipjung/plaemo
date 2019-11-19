package com.po771.plaemo.item;

public class Item_folder {
    private int _id;
    private String folder_name;
    private int book_id;

    public Item_folder(int _id, String folder_name, int book_id) {
        this._id = _id;
        this.folder_name = folder_name;
        this.book_id = book_id;
    }

    public Item_folder(String folder_name, int book_id) {
        this.folder_name = folder_name;
        this.book_id = book_id;
    }

    public Item_folder() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getFolder_name() {
        return folder_name;
    }

    public void setFolder_name(String folder_name) {
        this.folder_name = folder_name;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }
}
