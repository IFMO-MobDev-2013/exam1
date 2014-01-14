package com.ctd.CarWash;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by Alexei on 14.01.14.
 */
public class AddOrderActivity extends Activity {
    Spinner spinner;
    Button btnAddCar;
    EditText etCarName;
    EditText etCarNumber;
    EditText etCarColor;
    EditText etPhone;

    int box[];
    ArrayList<String> time;
    ArrayList<String> busyTime;
    ArrayList<Integer> busyBox;

    void readFromDataBase() {
        OrdersDataBaseHelper ordersDataBaseHelper = new OrdersDataBaseHelper(getApplicationContext());
        SQLiteDatabase sqLiteDatabase = ordersDataBaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(ordersDataBaseHelper.DATABASE_NAME, null, null, null, null, null, null);
        int timeColumn = cursor.getColumnIndex(OrdersDataBaseHelper.TIME);
        int boxColumn = cursor.getColumnIndex(OrdersDataBaseHelper.BOX);
        while (cursor.moveToNext()) {
            String time = cursor.getString(timeColumn);
            int box = cursor.getInt(boxColumn);
            busyTime.add(time);
            busyBox.add(box);
        }
        cursor.close();
        sqLiteDatabase.close();
        ordersDataBaseHelper.close();
    }

    ArrayAdapter<String> arrayAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_order_activity);
        btnAddCar = (Button) findViewById(R.id.btnAddCar);
        etCarNumber = (EditText) findViewById(R.id.etCarNumber);
        etCarColor = (EditText) findViewById(R.id.etCarColor);
        etCarName = (EditText) findViewById(R.id.etCarName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        btnAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrdersDataBaseHelper ordersDataBaseHelper = new OrdersDataBaseHelper(getApplicationContext());
                SQLiteDatabase sqLiteDatabase = ordersDataBaseHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(OrdersDataBaseHelper.CAR_NAME, etCarName.getText().toString());
                contentValues.put(OrdersDataBaseHelper.CAR_COLOR, etCarColor.getText().toString());
                contentValues.put(OrdersDataBaseHelper.CAR_NUMBER, etCarNumber.getText().toString());
                contentValues.put(OrdersDataBaseHelper.PHONE, etPhone.getText().toString());
                contentValues.put(OrdersDataBaseHelper.TIME, spinner.getSelectedItem().toString());
                contentValues.put(OrdersDataBaseHelper.BOX, box[spinner.getSelectedItemPosition()]);
                sqLiteDatabase.insert(OrdersDataBaseHelper.DATABASE_NAME, null, contentValues);
                sqLiteDatabase.close();
                ordersDataBaseHelper.close();
                finish();
            }
        });
        Intent intent = getIntent();
        int count = intent.getIntExtra(CarWashNameDataBaseHelper.COUNT, 0);
        time = new ArrayList<String>();
        busyTime = new ArrayList<String>();
        busyBox = new ArrayList<Integer>();
        box = new int[1000];
        for (int i = 0; i < 1000; i++)
            box[i] = -1;

        for (int i = 0; i < 14; i++) {
            time.add((i + 8) + ":00");
            time.add((i + 8) + ":30");
        }
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, time);
        readFromDataBase();
        for (int i = 0; i < time.size(); i++) {
            for (int j = 0; j < count; j++) {
                boolean t = true;
                for (int ii = 0; ii < busyBox.size(); ii++) {
                    if (time.get(i).equals(busyTime.get(ii)) && j == busyBox.get(ii)) {
                        t = false;
                        break;
                    }
                }
                if (t) {
                    box[i] = j;
                    break;
                }
            }
            if (box[i] == -1) {
                time.remove(i);
                i--;
            }

        }

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();


    }
}