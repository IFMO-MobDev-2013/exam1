package com.ctd.CarWash;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Alexei on 14.01.14.
 */
public class OrdersActivity extends Activity {
    String name;
    int count;

    TextView tvName;
    Button btnAdd;
    ListView listView;

    ArrayList<Order> arrayList;
    OrderAdapter orderAdapter;

    void readFromDataBase() {
        OrdersDataBaseHelper ordersDataBaseHelper = new OrdersDataBaseHelper(getApplicationContext());
        SQLiteDatabase sqLiteDatabase = ordersDataBaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(ordersDataBaseHelper.DATABASE_NAME, null, null, null, null, null, null);
        int timeColumn = cursor.getColumnIndex(OrdersDataBaseHelper.TIME);
        int carNameColumn = cursor.getColumnIndex(OrdersDataBaseHelper.CAR_NAME);
        int carNumberColumn = cursor.getColumnIndex(OrdersDataBaseHelper.CAR_NUMBER);
        int carColorColumn = cursor.getColumnIndex(OrdersDataBaseHelper.CAR_COLOR);
        int phoneColumn = cursor.getColumnIndex(OrdersDataBaseHelper.PHONE);
        int boxColumn = cursor.getColumnIndex(OrdersDataBaseHelper.BOX);
        arrayList.clear();
        while (cursor.moveToNext()) {
            arrayList.add(new Order(cursor.getString(carNameColumn), cursor.getString(carNumberColumn), cursor.getString(carColorColumn), cursor.getString(phoneColumn), cursor.getString(timeColumn), cursor.getInt(boxColumn)));
        }
        cursor.close();
        sqLiteDatabase.close();
        ordersDataBaseHelper.close();
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_activity);
        arrayList = new ArrayList<Order>();
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra(OrdersDataBaseHelper.CAR_NAME, arrayList.get(position).carName);
                intent.putExtra(OrdersDataBaseHelper.CAR_COLOR, arrayList.get(position).carColor);
                intent.putExtra(OrdersDataBaseHelper.CAR_NUMBER, arrayList.get(position).carNumber);
                intent.putExtra(OrdersDataBaseHelper.PHONE, arrayList.get(position).phone);
                intent.putExtra(OrdersDataBaseHelper.TIME, arrayList.get(position).time);
                intent.putExtra(OrdersDataBaseHelper.BOX, arrayList.get(position).box);
                intent.setClass(getApplicationContext(), ShowOrderActivity.class);
                startActivity(intent);
            }
        });
        orderAdapter = new OrderAdapter(getApplicationContext(), arrayList);
        listView.setAdapter(orderAdapter);
        tvName = (TextView) findViewById(R.id.tvName);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(CarWashNameDataBaseHelper.COUNT, count);
                intent.setClass(getApplicationContext(), AddOrderActivity.class);
                startActivity(intent);
            }
        });
        Intent intent = getIntent();
        name = intent.getStringExtra(CarWashNameDataBaseHelper.NAME);
        count = intent.getIntExtra(CarWashNameDataBaseHelper.COUNT, 0);
        tvName.setText(name);
        readFromDataBase();
        orderAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        readFromDataBase();
        orderAdapter.notifyDataSetChanged();
    }
}