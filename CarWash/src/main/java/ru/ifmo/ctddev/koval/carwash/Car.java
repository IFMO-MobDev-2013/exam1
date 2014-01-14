package ru.ifmo.ctddev.koval.carwash;

public class Car {

    private String make;
    private String color;
    private String number;
    private String phone;

    public Car(String make, String color, String number, String phone) {
        this.make = make;
        this.color = color;
        this.number = number;
        this.phone = phone;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
