package ru.ifmo.ctddev.isaev.CarWash.orm;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * User: Xottab
 * Date: 05.12.13
 */
@DatabaseTable
public class ScheduleEntry implements Serializable {

    @DatabaseField(generatedId = true)
    private int Id;

    @DatabaseField(canBeNull = false)
    private String brandName;

    @DatabaseField(canBeNull = false)
    private String color;

    @DatabaseField(canBeNull = false)
    private String carNumber;

    @DatabaseField(canBeNull = false)
    private String phoneNumber;

    @DatabaseField(canBeNull = false)
    private int scheduleTime;

    @DatabaseField(canBeNull = false)
    private int boxNumber;


    public ScheduleEntry() {
    }

    public ScheduleEntry(String s, String s1, String s2, String s3, Integer scheduleTime, int boxNumber) {
        this.brandName = s;
        this.color = s1;
        this.carNumber = s2;
        this.phoneNumber = s3;
        this.scheduleTime = scheduleTime;
        this.boxNumber = boxNumber;
    }


    public String getBrandName() {
        return brandName;
    }

    public String getColor() {
        return color;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getScheduleTime() {
        return scheduleTime;
    }

    public int getBoxNumber() {
        return boxNumber;
    }
}
