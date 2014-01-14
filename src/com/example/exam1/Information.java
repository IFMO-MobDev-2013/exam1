package com.example.exam1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: dronov
 * Date: 14.01.14
 * Time: 18:06
 * To change this template use File | Settings | File Templates.
 */
public class Information extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.information);
        Intent intent = getIntent();
        Cars cars = (Cars) intent.getSerializableExtra("result");
        TextView textView = (TextView) findViewById(R.id.textView);
        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textView3 = (TextView) findViewById(R.id.textView3);

        textView.setText(cars.getCarMark());
        textView1.setText(cars.getColor());
        textView2.setText(cars.getWindow());
        textView3.setText(cars.getTime());
    }
}
