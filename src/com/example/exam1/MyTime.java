package com.example.exam1;

/**
 * Created with IntelliJ IDEA.
 * User: satori
 * Date: 1/14/14
 * Time: 5:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyTime {
    int time;
    public MyTime(int time) {
        this.time = time;
    }
    @Override
    public String toString() {
        return time/60 + ":" + time%60;
    }
}
