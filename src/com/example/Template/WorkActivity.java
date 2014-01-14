package com.example.Template;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: Александр
 * Date: 14.01.14
 * Time: 13:07
 */
public class WorkActivity extends Activity {

    private TextView tvTime;
    private TextView tvModelColor;
    private TextView tvNumberWindow;
    private TextView tvNumberCar;
    private TextView tvNumberPhone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work);

        tvTime = (TextView) findViewById(R.id.work_time);
        tvModelColor = (TextView) findViewById(R.id.work_model_color);
        tvNumberWindow = (TextView) findViewById(R.id.work_number_window);
        tvNumberCar = (TextView) findViewById(R.id.work_number_car);
        tvNumberPhone = (TextView) findViewById(R.id.work_number_phone);

        String text = getIntent().getStringExtra("text");

        tvTime.setText("Начало мойки в " + getIntent().getStringExtra("time"));
        tvModelColor.setText("Модель машины: " + getIntent().getStringExtra("modelColor"));
        tvNumberWindow.setText(getIntent().getStringExtra("numberWindow"));
        tvNumberCar.setText("Номер машины: " + getIntent().getStringExtra("numberCar"));
        tvNumberPhone.setText("Номер телефона: " + getIntent().getStringExtra("numberPhone"));
    }
}
