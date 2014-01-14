package com.example.WashYourCars;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyActivity extends Activity {

    public static final int MAXBOXES = 20;

    MyAdapter adapter;
    ArrayList<Order> array;
    ListView listView;
    int useTime[];

    class MyAdapter extends ArrayAdapter<Order> {
        private Context context;

        public MyAdapter(Context context, int textViewResourceId, ArrayList<Order> items) {
            super(context, textViewResourceId, items);
            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            //TODO   fillparent listview height

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.orderitem, null);
            TextView textViewOrderTime = (TextView) rowView.findViewById(R.id.textViewOrderTime);
            TextView textViewOrderBox = (TextView) rowView.findViewById(R.id.textViewOrderBox);
            TextView textViewOrderModel = (TextView) rowView.findViewById(R.id.textViewOrderModel);
            TextView textViewOrderColor = (TextView) rowView.findViewById(R.id.textViewOrderColor);
            TextView textViewOrderPhone = (TextView) rowView.findViewById(R.id.textViewOrderPhone);

            Order item = getItem(position);

            if (item == null) {
                return rowView;
            }
            textViewOrderTime.setText(item.time);
            textViewOrderBox.setText(item.box);
            textViewOrderModel.setText(item.model);
            textViewOrderColor.setText(item.color);
            textViewOrderPhone.setText(item.phone);

            return rowView;
        }
    }

    /*
    public AdapterView.OnItemClickListener goToOrder = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {

            Intent intent = new Intent();

            Order item = array.get(position);
            intent.putExtra(Order.TIME, item.time);
            intent.putExtra(Order.BOX, item.box);
            intent.putExtra(Order.MODEL, item.model);
            intent.putExtra(Order.COLOR, item.color);
            intent.putExtra(Order.PHONE, item.phone);

            intent.setClass(getApplicationContext(), OrderActivity.class);

            startActivity(intent);
        }
    };
      */
    void checkFirstTime() {
        MyDataBaseHelper myDataBaseHelper = new MyDataBaseHelper(getApplicationContext());
        SQLiteDatabase sqLiteDatabase = myDataBaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(myDataBaseHelper.DATABASE_NAME, null, null, null, null, null, null);
        String ourName = "";
        int ourBoxes = 0;
        while (cursor.moveToNext()) {
            ourName = cursor.getString(cursor.getColumnIndex(MyDataBaseHelper.NAME));
            try {
                ourBoxes = Integer.parseInt(cursor.getString(cursor.getColumnIndex(MyDataBaseHelper.BOXES)));
            } catch (Exception e) {
                ourBoxes = 0;
            }
        }
        cursor.close();
        sqLiteDatabase.close();
        myDataBaseHelper.close();
        if ("".equals(ourName) || ourBoxes <= 0) {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), FirstTimeActivity.class);


            startActivity(intent);
        }
    }

    void showOrders() {
        MyDataBaseCarsHelper myDataBaseCarsHelper = new MyDataBaseCarsHelper(getApplicationContext());
        SQLiteDatabase sqLiteDatabase = myDataBaseCarsHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(myDataBaseCarsHelper.DATABASE_NAME, null, null, null, null, null, null);

        int time_column = cursor.getColumnIndex(MyDataBaseCarsHelper.TIME);
        int box_column = cursor.getColumnIndex(MyDataBaseCarsHelper.BOX);
        int model_column = cursor.getColumnIndex(MyDataBaseCarsHelper.MODEL);
        int color_column = cursor.getColumnIndex(MyDataBaseCarsHelper.COLOR);
        int phone_column = cursor.getColumnIndex(MyDataBaseCarsHelper.PHONE);
        int sign_column = cursor.getColumnIndex(MyDataBaseCarsHelper.SIGN);

        array = new ArrayList<Order>();
        useTime = new int[50];
        for (int i = 0; i < useTime.length; i++)
            useTime[i] = 0;

        while (cursor.moveToNext()) {
            if (cursor.getString(time_column) == null || "".equals(cursor.getString(time_column)))
                continue;
            array.add(new Order(Order.makeTime(Integer.parseInt(cursor.getString(time_column))), cursor.getString(box_column), cursor.getString(model_column),
                    cursor.getString(color_column), cursor.getString(phone_column), cursor.getString(sign_column)));
            useTime[Integer.parseInt(cursor.getString(time_column))]++;
        }
        cursor.close();
        sqLiteDatabase.close();
        myDataBaseCarsHelper.close();

        adapter = new MyAdapter(getApplicationContext(), R.layout.orderitem, array);

        listView = (ListView) findViewById(R.id.listViewMain);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(goToOrder);

        adapter.notifyDataSetChanged();
    }

    public AdapterView.OnItemClickListener goToOrder = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {

            Intent intent = new Intent();

            Order order = array.get(position);
            intent.putExtra(Order.TIME, order.time);
            intent.putExtra(Order.MODEL, order.model);
            intent.putExtra(Order.COLOR, order.color);
            intent.putExtra(Order.PHONE, order.phone);
            intent.putExtra(Order.SIGN, order.sign);
            intent.putExtra(Order.BOX, order.box);

            intent.putExtra(Order.NEWORDER, false);
            intent.putExtra(Order.EDITABLE, false);
            intent.putExtra(Order.USESTABLE, useTime);
            intent.setClass(getApplicationContext(), MakingOrder.class);
            startActivity(intent);
        }
    };

    public void newOrder(View view) {
        Intent intent = new Intent();

        intent.putExtra(Order.NEWORDER, true);
        intent.putExtra(Order.EDITABLE, true);
        intent.putExtra(Order.USESTABLE, useTime);
        intent.setClass(getApplicationContext(), MakingOrder.class);
        getIntent().putExtra(Order.HAVENEWORDER, true);
        startActivity(intent);

    }

    public void addNewOrder() {

        Order order = new Order(getIntent());
        ContentValues contentValues = order.getContentValues();
        MyDataBaseCarsHelper myDataBaseCarsHelper = new MyDataBaseCarsHelper(getApplicationContext());
        SQLiteDatabase sqLiteDatabase = myDataBaseCarsHelper.getWritableDatabase();
        sqLiteDatabase.insert(MyDataBaseCarsHelper.DATABASE_NAME, null, contentValues);

        sqLiteDatabase.close();
        myDataBaseCarsHelper.close();
    }

    @Override
    public void onStart() {
        super.onStart();
        checkFirstTime();
        if (getIntent().getBooleanExtra(Order.HAVENEWORDER, false)) {
            getIntent().putExtra(Order.HAVENEWORDER, false);
            addNewOrder();
        }
        showOrders();
    }

    @Override
    public void onResume() {
        super.onResume();
        checkFirstTime();
        if (getIntent().getBooleanExtra(Order.HAVENEWORDER, false)) {
            getIntent().putExtra(Order.HAVENEWORDER, false);
            addNewOrder();
        }
        showOrders();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkFirstTime();
        setContentView(R.layout.main);
        if (getIntent().getBooleanExtra(Order.HAVENEWORDER, false)) {
            getIntent().putExtra(Order.HAVENEWORDER, false);
            addNewOrder();
        }
        showOrders();
    }
}
