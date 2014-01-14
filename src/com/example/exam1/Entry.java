package com.example.exam1;

/**
 * Created with IntelliJ IDEA.
 * User: satori
 * Date: 1/14/14
 * Time: 4:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class Entry {
    public int id;
    public String brand_color;
    public int time;
    public int box;
    public String car_number;
    public String phone_number;
    public Entry(int id, String brand_color, int time, int box, String car_number, String phone_number) {
        this.id = id;
        this.brand_color = brand_color;
        this.time = time;
        this.box = box;
        this.car_number = car_number;
        this.phone_number = phone_number;
    }
    @Override
    public String toString() {
        return brand_color + "  " + formatTime(time) + " " + box;
    }

    private String  formatTime(int t) {
        return t/60 + ":" + t%60;
    }
}
