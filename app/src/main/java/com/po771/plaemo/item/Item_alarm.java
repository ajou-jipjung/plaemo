package com.po771.plaemo.item;

public class Item_alarm {

    private int _id;
    private String alarm_name;
    private int book_id;
    private int hour;
    private int minute;
    private int repeat;
    private String daysoftheweek;
    private int ison;
    private String tone;
    private int snooze;
    private int vibrate;
    private int ampm;

    public Item_alarm() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getAlarm_name() {
        return alarm_name;
    }

    public void setAlarm_name(String alarm_name) {
        this.alarm_name = alarm_name;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public String getDaysoftheweek() {
        return daysoftheweek;
    }

    public void setDaysoftheweek(String daysoftheweek) {
        this.daysoftheweek = daysoftheweek;
    }

    public int getIson() {
        return ison;
    }

    public void setIson(int ison) {
        this.ison = ison;
    }

    public String getTone() {
        return tone;
    }

    public void setTone(String tone) {
        this.tone = tone;
    }

    public int getSnooze() {
        return snooze;
    }

    public void setSnooze(int snooze) {
        this.snooze = snooze;
    }

    public int getVibrate() {
        return vibrate;
    }

    public void setVibrate(int vibrate) {
        this.vibrate = vibrate;
    }

    public int getAmpm() {
        return ampm;
    }

    public void setAmpm(int ampm) {
        this.ampm = ampm;
    }
}
