package ru.marsermd.IpsumLotis;

import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: misch_000
 * Date: 14.01.14
 * Time: 16:02
 * To change this template use File | Settings | File Templates.
 */
public class CarModel {


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        if (time >= 16 && time < 44)
            this.time = time;
    }

    public int getAssignedBox() {
        return assignedBox;
    }

    public void setAssignedBox(int assignedBox) {
        if(assignedBox > 0 && assignedBox < CarWashModel.getBoxCount())
            this.assignedBox = assignedBox;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    private String model = "";
    private String color = "";
    private String number = "";
    private String phone = "";
    private int time = 0;
    private int assignedBox = 0;

    public void setNameV(TextView nameV) {
        this.nameV = nameV;
        nameV.setText(model);
    }

    public void setTimeV(TextView timeV) {
        this.timeV = timeV;
        timeV.setText(CarWashModel.getFormatedTime(time));
    }

    public void setBoxV(TextView boxV) {
        this.boxV = boxV;
        boxV.setText(assignedBox + 1);
    }

    private TextView nameV, timeV, boxV;


}
