package com.example.Template;

/**
 * Created with IntelliJ IDEA.
 * User: Александр
 * Date: 14.01.14
 * Time: 15:25
 */
public class Order {
    private String modelColor;
    private String time;
    private String numberWindow;
    private String numberCar;
    private String phone;

    public Order(String time, String modelColor, String numberWindow, String numberCar, String phone)  {
        this.time = time;
        this.modelColor = modelColor;
        this.numberWindow = numberWindow;
        this.numberCar = numberCar;
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }
    public String getModelColor() {
        return modelColor;
    }
    public String getNumberWindow() {
        return numberWindow;
    }
    public String getNumberCar() {
        return numberCar;
    }
    public String getPhone() {
        return phone;
    }
}
