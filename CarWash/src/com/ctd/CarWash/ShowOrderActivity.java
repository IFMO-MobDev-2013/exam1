package com.ctd.CarWash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Alexei on 14.01.14.
 */
public class ShowOrderActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_order_activity);
        Intent intent = getIntent();
        TextView tvCarName = (TextView) findViewById(R.id.textView6);
        TextView tvCarColor = (TextView) findViewById(R.id.textView7);
        TextView tvCarNumber = (TextView) findViewById(R.id.textView8);
        TextView tvPhone = (TextView) findViewById(R.id.textView9);
        TextView tvTime = (TextView) findViewById(R.id.textView10);
        TextView tvBox = (TextView) findViewById(R.id.textView11);
        tvCarName.setText(intent.getStringExtra(OrdersDataBaseHelper.CAR_NAME));
        tvCarColor.setText(intent.getStringExtra(OrdersDataBaseHelper.CAR_COLOR));
        tvCarNumber.setText(intent.getStringExtra(OrdersDataBaseHelper.CAR_NUMBER));
        tvPhone.setText(intent.getStringExtra(OrdersDataBaseHelper.PHONE));
        tvTime.setText(intent.getStringExtra(OrdersDataBaseHelper.TIME));
        tvBox.setText(String.valueOf(intent.getIntExtra(OrdersDataBaseHelper.BOX, 0) + 1));


    }
}