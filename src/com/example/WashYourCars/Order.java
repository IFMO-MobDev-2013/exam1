package com.example.WashYourCars;

import android.content.ContentValues;
import android.content.Intent;

public class Order {

    public static final String MODEL = "model";
    public static final String COLOR = "color";
    public static final String TIME = "time";
    public static final String BOX = "box";
    public static final String PHONE = "phone";
    public static final String SIGN = "sign";
    public static final String NEWORDER = "new_order";
    public static final String EDITABLE = "editable";
    public static final String USESTABLE = "usestable";
    public static final String OURBOXES = "ourboxes";
    public static final String HAVENEWORDER = "haveneworder";

    public String model;
    public String color;
    public String time;
    public String box;
    public String phone;
    public String sign;

    Order() {
        time = "";
        box = "";
        model = "";
        color = "";
        phone = "";
        sign = "";
    }

    Order(String _time, String _box, String _model, String _color, String _phone, String _sign) {
        time = _time;
        box = _box;
        model = _model;
        color = _color;
        phone = _phone;
        sign = _sign;
    }

    static String makeTime(int x) {
        String hours = "" + (x / 2 + 8);
        if (hours.length() < 2)
            hours = "0" + hours;
        return hours + (x % 2 == 1 ? ":30" : ":00");
    }

    static int makeIntFromTime(String s) {
        return (Integer.parseInt(s.substring(0, 2)) - 8) * 2 + (s.charAt(3) == '3' ? 1 : 0);
    }

    static String makeTime(String s) {
        return makeTime(Integer.parseInt(s));
    }

    Order(ContentValues c) {
        time = c.getAsString(TIME);
        box = c.getAsString(BOX);
        model = c.getAsString(MODEL);
        color = c.getAsString(COLOR);
        phone = c.getAsString(PHONE);
        sign = c.getAsString(SIGN);
    }

    ContentValues getContentValues() {
        ContentValues c = new ContentValues();
        c.put(TIME, time);
        c.put(BOX, box);
        c.put(MODEL, model);
        c.put(COLOR, color);
        c.put(PHONE, phone);
        c.put(SIGN, sign);

        return c;
    }

    Order(Intent intent) {
        time = intent.getStringExtra(TIME);
        box = intent.getStringExtra(BOX);
        model = intent.getStringExtra(MODEL);
        color = intent.getStringExtra(COLOR);
        phone = intent.getStringExtra(PHONE);
        sign = intent.getStringExtra(SIGN);
    }
}
