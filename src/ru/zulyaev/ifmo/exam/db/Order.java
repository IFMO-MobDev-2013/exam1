package ru.zulyaev.ifmo.exam.db;

public class Order {
    public final long id;
    public final String model;
    public final String color;
    public final String phone;
    public final long time;
    public final long box;

    public Order(long id, String model, String color, String phone, long time, long box) {
        this.id = id;
        this.model = model;
        this.color = color;
        this.phone = phone;
        this.time = time;
        this.box = box;
    }
}
