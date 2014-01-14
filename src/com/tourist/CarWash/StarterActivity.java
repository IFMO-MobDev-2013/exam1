package com.tourist.CarWash;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class StarterActivity extends Activity {

    private DBAdapter myDBAdapter;
    private Cursor cursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDBAdapter = new DBAdapter(this);
        myDBAdapter.open();
        cursor = myDBAdapter.getWashName();
        startManagingCursor(cursor);
        if (cursor.moveToFirst()) {
            setMainView();
        } else {
            setContentView(R.layout.starter);
        }
    }

    public void starterSubmit(View v) {
        EditText editText = (EditText) findViewById(R.id.carWashName);
        String washName = editText.getText().toString();
        editText = (EditText) findViewById(R.id.boxCount);
        int boxCount = Integer.parseInt(editText.getText().toString());
        myDBAdapter.setWashName(washName, boxCount);
        cursor = myDBAdapter.getWashName();
        startManagingCursor(cursor);
        cursor.moveToFirst();
        setMainView();
    }

    public static final String BOX_COUNT = "box_count";
    int boxCount;

    public void doAddCar(View v) {
        Intent intent = new Intent(v.getContext(), AddCarActivity.class);
        intent.putExtra(BOX_COUNT, "" + boxCount);
        startActivity(intent);
    }

    private void showCars() {
        cursor = myDBAdapter.getWashName();
        startManagingCursor(cursor);
        if (!cursor.moveToFirst()) {
            return;
        }
        cursor = myDBAdapter.fetchCars();
        String[] from = new String[]{DBAdapter.KEY_BRAND, DBAdapter.KEY_TIME, DBAdapter.KEY_BOX};
        int[] to = new int[]{R.id.car_row_brand, R.id.car_row_time, R.id.car_row_box};
        SimpleCursorAdapter cars = new SimpleCursorAdapter(this, R.layout.car_row, cursor, from, to);
        ListView carsView = (ListView) findViewById(R.id.cars_view);
        carsView.setAdapter(cars);
    }

    private void setMainView() {
        setContentView(R.layout.main);
        String washName = cursor.getString(cursor.getColumnIndexOrThrow(DBAdapter.KEY_WASH_NAME));
        boxCount = cursor.getInt(cursor.getColumnIndexOrThrow(DBAdapter.KEY_BOX_COUNT));
        TextView textView = (TextView) findViewById(R.id.mainWashName);
        textView.setText(washName);
        showCars();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showCars();
    }

    @Override
    protected void onDestroy() {
        myDBAdapter.close();
        super.onDestroy();
    }
}
