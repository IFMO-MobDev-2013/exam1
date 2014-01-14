package com.example.exam1.databases;

/**
 * Created with IntelliJ IDEA.
 * User: dronov
 * Date: 14.01.14
 * Time: 15:58
 * To change this template use File | Settings | File Templates.
 */
public class CarWash {
    private String name;
    private int number;

    public CarWash() {}
    public CarWash(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
