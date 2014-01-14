package com.example.exam1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: satori
 * Date: 1/14/14
 * Time: 6:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShowAcitivity extends Activity {
    private TextView brand;
    private TextView time;
    private TextView box;
    private TextView number;
    private TextView phone;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showlayout);
        brand = (TextView)findViewById(R.id.show_brand);
        time = (TextView)findViewById(R.id.show_time);
        box = (TextView)findViewById(R.id.show_box);
        number = (TextView)findViewById(R.id.show_number);
        phone = (TextView)findViewById(R.id.show_phone);
        Intent intent1 = getIntent();
        String mybrand_color = intent1.getStringExtra("brand");
        int mytime = intent1.getIntExtra("time", 480);
        int mybox = intent1.getIntExtra("box", 0);
        String mynumber = intent1.getStringExtra("number");
        String myphone = intent1.getStringExtra("phone");
        brand.setText(mybrand_color);
        this.time.setText(mytime/60 + "" + mytime%60);
        box.setText(mybox + "");
        number.setText(mynumber);
        phone.setText(myphone);
    }

}