package com.ctd.CarWash;

/**
 * Created by Alexei on 14.01.14.
 */
public class Order {
    String carName;
    String carNumber;
    String carColor;
    String phone;
    String time;
    int box;

    Order(String carName, String carNumber, String carColor, String phone, String time, int box) {
        this.box = box;
        this.carName = carName;
        this.carColor = carColor;
        this.carNumber = carNumber;
        this.time = time;
        this.phone = phone;
    }
}
