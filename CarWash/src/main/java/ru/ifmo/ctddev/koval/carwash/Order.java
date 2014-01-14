package ru.ifmo.ctddev.koval.carwash;

public class Order {
    private int boxOrdinal;
    private int carId;
    private String time;

    public Order(int boxOrdinal, int carId, String time) {
        this.boxOrdinal = boxOrdinal;
        this.carId = carId;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getBoxOrdinal() {
        return boxOrdinal;
    }

    public void setBoxOrdinal(int boxOrdinal) {
        this.boxOrdinal = boxOrdinal;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }
}
