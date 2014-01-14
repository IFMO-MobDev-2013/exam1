package com.tourist.CarWash;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class AddCarActivity extends Activity {

    private String time;

    private static final int SLOTS = 28;
    private int[] boxID;

    private int timeToID(String time) {
        int hour = (time.charAt(0) - '0') * 10 + (time.charAt(1) - '0');
        int minute = (time.charAt(3) - '0') * 10 + (time.charAt(4) - '0');
        return (2 * (hour - 8) + (minute / 30));
    }

    private String idToTime(int timeID) {
        int hour = 8 + (timeID / 2);
        int minute = 30 * (timeID % 2);
        return ("" + (hour / 10) + (hour % 10) + ":" + (minute / 10) + "0");
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int boxCount = Integer.parseInt(intent.getStringExtra(StarterActivity.BOX_COUNT));
        boxID = new int[SLOTS];
        boolean[][] occupied = new boolean[SLOTS][boxCount];
        DBAdapter myDBAdapter = new DBAdapter(this);
        myDBAdapter.open();
        Cursor cursor = myDBAdapter.fetchCars();
        if (cursor.moveToFirst()) {
            do {
                String carTime = cursor.getString(cursor.getColumnIndexOrThrow(DBAdapter.KEY_TIME));
                int carBox = cursor.getInt(cursor.getColumnIndexOrThrow(DBAdapter.KEY_BOX));
                int carTimeID = timeToID(carTime);
                occupied[carTimeID][carBox - 1] = true;
            } while (cursor.moveToNext());
        }
        myDBAdapter.close();
        ArrayList<String> times = new ArrayList<String>();
        for (int timeID = 0; timeID < SLOTS; timeID++) {
            for (int box = 0; box < boxCount; box++) {
                if (!occupied[timeID][box]) {
                    boxID[timeID] = box + 1;
                    times.add(idToTime(timeID));
                    break;
                }
            }
        }
        if (times.isEmpty()) {
            setContentView(R.layout.come_tomorrow);
            return;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, times);
        setContentView(R.layout.add_car);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                time = ((String) parent.getItemAtPosition(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    public void goAway(View v) {
        this.finish();
    }

    public void addCar(View v) {
        DBAdapter myDBAdapter = new DBAdapter(this);
        myDBAdapter.open();
        String brand = ((EditText) findViewById(R.id.addBrand)).getText().toString();
        String color = ((EditText) findViewById(R.id.addColor)).getText().toString();
        String number = ((EditText) findViewById(R.id.addNumber)).getText().toString();
        String phone = ((EditText) findViewById(R.id.addPhone)).getText().toString();
        int box = boxID[timeToID(time)];
        myDBAdapter.addCar(brand, color, number, phone, time, box);
        myDBAdapter.close();
        this.finish();
    }
}