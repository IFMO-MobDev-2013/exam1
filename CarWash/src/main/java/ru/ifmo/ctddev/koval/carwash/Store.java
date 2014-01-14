package ru.ifmo.ctddev.koval.carwash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class Store {

    private Context context;
    private DatabaseOpenHelper dbOpenHelper;

    public Store(Context context) {
        this.context = context;
        dbOpenHelper = new DatabaseOpenHelper(context);
    }

    public Cursor getAllOrders() {
        String query = "SELECT * FROM " + dbOpenHelper.ORDERS_TABLE_NAME
                + " WHERE " + dbOpenHelper.CAR_ID_COLUMN + " >= 0";
        return dbOpenHelper.getReadableDatabase().rawQuery(query, null);
    }

    public long addOrder(Order order) {
        ContentValues values = new ContentValues();
        values.put(dbOpenHelper.TIME_COLUMN, order.getTime());
        values.put(dbOpenHelper.BOX_COLUMN, order.getBoxOrdinal());
        values.put(dbOpenHelper.CAR_ID_COLUMN, order.getCarId());
        return dbOpenHelper.getWritableDatabase().insert(dbOpenHelper.ORDERS_TABLE_NAME, null, values);
    }

    public long addCar(Car car) {
        ContentValues values = new ContentValues();
        values.put(dbOpenHelper.MAKE_COLUMN, car.getMake());
        values.put(dbOpenHelper.COLOR_COLUMN, car.getColor());
        values.put(dbOpenHelper.NUMBER_COLUMN, car.getNumber());
        values.put(dbOpenHelper.PHONE_COLUMN, car.getPhone());
        return dbOpenHelper.getWritableDatabase().insert(dbOpenHelper.CARS_TABLE_NAME, null, values);
    }

    public Car getCar(int carId) {
        String query = "SELECT * FROM " + dbOpenHelper.CARS_TABLE_NAME
                + " WHERE " + dbOpenHelper.ID_COLUMN + " = " + carId;
        Cursor cursor = dbOpenHelper.getReadableDatabase().rawQuery(query, null);
        cursor.moveToFirst();

        String make = cursor.getString(cursor.getColumnIndex(dbOpenHelper.MAKE_COLUMN));
        String color = cursor.getString(cursor.getColumnIndex(dbOpenHelper.COLOR_COLUMN));
        String phone = cursor.getString(cursor.getColumnIndex(dbOpenHelper.PHONE_COLUMN));
        String number = cursor.getString(cursor.getColumnIndex(dbOpenHelper.NUMBER_COLUMN));

        return new Car(make, color, number, phone);
    }

    public int getFreeBox(String time) {
        String query = "SELECT * FROM " + dbOpenHelper.ORDERS_TABLE_NAME
                + " WHERE " + dbOpenHelper.TIME_COLUMN + " = " + "\"" + time + "\"";
        return dbOpenHelper.getReadableDatabase().rawQuery(query, null).getCount();
    }

}
