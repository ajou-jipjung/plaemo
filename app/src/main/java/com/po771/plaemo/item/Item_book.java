package com.po771.plaemo.item;

import android.graphics.Bitmap;

public class Item_book {
    private int _id;
    private String book_name;
    private String book_uri;
    private int current_page;
    private int total_page;
    private String book_info;
    private int book_star;
    private String folder;
    private Bitmap image_bitmap;

    public Bitmap getImage_bitmap() {
        return image_bitmap;
    }

    public void setImage_bitmap(Bitmap image_bitmap) {
        this.image_bitmap = image_bitmap;
    }

    public Item_book() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_uri() {
        return book_uri;
    }

    public void setBook_uri(String book_uri) {
        this.book_uri = book_uri;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public String getBook_info() {
        return book_info;
    }

    public void setBook_info(String book_info) {
        this.book_info = book_info;
    }

    public int getBook_star() {
        return book_star;
    }

    public void setBook_star(int book_star) {
        this.book_star = book_star;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }
}
