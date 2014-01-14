package com.example.exam1;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: dronov
 * Date: 14.01.14
 * Time: 16:20
 * To change this template use File | Settings | File Templates.
 */
public class Cars implements Serializable{
    private String carMark;
    private String color;
    private String time;
    private String window;

    public Cars() {}

    public String getCarMark() {
        return carMark;
    }

    public void setCarMark(String carMark) {
        this.carMark = carMark;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWindow() {
        return window;
    }

    public void setWindow(String window) {
        this.window = window;
    }

    public Cars(String carMark, String color, String time, String window) {

        this.carMark = carMark;
        this.color = color;
        this.time = time;
        this.window = window;
    }
}
