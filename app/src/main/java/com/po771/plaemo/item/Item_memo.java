package com.po771.plaemo.item;

public class Item_memo {
    private  int _id;
    private int boook_id;
    private int page_start;
    private int page_end;
    private String content;
    private String date;

    public Item_memo(int _id, int boook_id, int page_start, int page_end, String content, String date){
        this._id = _id;
        this.boook_id = boook_id;
        this.page_start = page_start;
        this.page_end = page_end;
        this.content = content;
        this.date = date;
    }

    public Item_memo(int boook_id, int page_start, int page_end, String content, String date){
        this.boook_id = boook_id;
        this.page_start = page_start;
        this.page_end = page_end;
        this.content = content;
        this.date = date;
    }

    public Item_memo(int page_start, String content, String date){
        this.page_start = page_start;
        this.content = content;
        this.date = date;
    }

    public Item_memo(){ }

    public int get_id() { return _id;}

    public void set_id(int _id) {this._id = _id; }

    public int getBoook_id() {return boook_id;}

    public void setBoook_id(int boook_id) {this.boook_id = boook_id;}

    public int getPage_start() { return page_start;}

    public void setPage_start(int page_start) { this.page_start = page_start;}

    public int getPage_end() { return page_end; }

    public void setPage_end(int page_end) {this.page_end = page_end; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

}

