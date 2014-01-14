package com.example.exam;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Kirill on 14.01.14.
 */
public class Information extends Activity {
    DBAdapter db;
    String number;
    Car car;
    TextView mark, numb, color, phone, time, box;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        db = new DBAdapter(this);
        number = getIntent().getStringExtra(getResources().getString(R.string.number));
        car = db.getCar(number);
        setInformation();
    }

    private void setInformation() {
        mark = (TextView) findViewById(R.id.mark);
        numb = (TextView) findViewById(R.id.number);
        color = (TextView) findViewById(R.id.color);
        phone = (TextView) findViewById(R.id.phone);
        box = (TextView) findViewById(R.id.box);
        time = (TextView) findViewById(R.id.time);
        mark.setText(mark.getText().toString()+car.mark);
        numb.setText(numb.getText().toString()+car.number);
        color.setText(color.getText().toString()+car.color);
        phone.setText(phone.getText().toString()+car.phone);
        time.setText(time.getText().toString()+car.time);
        box.setText(box.getText().toString()+Integer.toString(car.box));
    }
}
