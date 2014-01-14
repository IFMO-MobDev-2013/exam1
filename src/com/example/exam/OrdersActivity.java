package com.example.exam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class OrdersActivity extends Activity {
    private String TIME, MARK, COLOR, BOX, NUMBER, PHONE;
    DBAdapter db;
    DBName dbn;
    TextView carwash;
    ListView listView;
    Car[] cars;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders);
        TIME = getResources().getString(R.string.time);
        MARK = getResources().getString(R.string.mark);
        COLOR = getResources().getString(R.string.color);
        BOX = getResources().getString(R.string.box);
        NUMBER = getResources().getString(R.string.number);
        PHONE = getResources().getString(R.string.phone);
        carwash = (TextView) findViewById(R.id.textView);
        listView = (ListView) findViewById(R.id.listView);
        db = new DBAdapter(this);
        dbn = new DBName(this);
        carwash.setText(dbn.getName());
        cars = db.getAllCars();
        if (cars != null) makeList();
    }


    private void makeList() {
        sortCar();
        listView = (ListView) findViewById(R.id.listView);
        ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>(
                cars.length);
        Map<String, String> m;
        for (int i = 0; i < cars.length; i++) {
            m = new HashMap<String, String>();
            m.put(MARK, cars[i].mark);
            m.put(COLOR, cars[i].color);
            m.put(TIME, cars[i].time);
            m.put(BOX, Integer.toString(cars[i].box));
            data.add(m);
        }
        String[] from = {MARK, COLOR, TIME, BOX};
        int[] to = {R.id.mark, R.id.color, R.id.time, R.id.box};
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.listitem, from, to);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> l, View view,
                                    int position, long id) {
                Intent intent = new Intent(OrdersActivity.this, Information.class);
                intent.putExtra(NUMBER, cars[position].number);
                startActivity(intent);
            }
        });
    }

    public void addCar(View v) {
        Intent i = new Intent(this, AddCarActivity.class);
        startActivity(i);
        this.finish();
    }
    private void sortCar() {
        Car temp;
        for (int i = 0; i < cars.length; i++) {
            for (int j = i + 1; j < cars.length; j++) {
                if (time(cars[j].time) < time(cars[i].time)) {
                    temp = cars[i];
                    cars[i] = cars[j];
                    cars[j] = temp;
                }
            }
        }
    }
    private int time(String tt) {
        for (int i = 0; i < tt.length(); i++) {
            if (tt.charAt(i) == ':') tt = tt.substring(0, i) + tt.substring(i + 1, tt.length());
        }
        return Integer.parseInt(tt);
    }
}
