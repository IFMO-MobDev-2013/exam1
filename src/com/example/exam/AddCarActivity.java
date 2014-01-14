package com.example.exam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;


public class AddCarActivity extends Activity {
    EditText mark, color, phone, number;
    Spinner time;
    DBBoxes dbb;
    DBAdapter db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_car);
        dbb = new DBBoxes(this);
        db = new DBAdapter(this);
        number = (EditText) findViewById(R.id.numb);
        mark = (EditText) findViewById(R.id.mark);
        color = (EditText) findViewById(R.id.color);
        phone = (EditText) findViewById(R.id.phone);
        time = (Spinner) findViewById(R.id.time);
        String[] times = dbb.getTimes(getResources().getStringArray(R.array.times));
        String[] tt = new String[Integer.parseInt(times[0])];
        for (int i = 1; i <= Integer.parseInt(times[0]); i++) {
            tt[i - 1] = times[i];
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tt);
        time.setAdapter(adapter);
    }


    public void addCar(View v) {
        String mm = mark.getText().toString();
        String tt = time.getSelectedItem().toString();
        int bb = dbb.getBox(time.getSelectedItem().toString());
        String cc = color.getText().toString();
        String nn = number.getText().toString();
        String pp = phone.getText().toString();
        db.insert(mm, cc, tt, bb, nn, pp);
        Intent i = new Intent(this, OrdersActivity.class);
        startActivity(i);
        this.finish();
    }
}
