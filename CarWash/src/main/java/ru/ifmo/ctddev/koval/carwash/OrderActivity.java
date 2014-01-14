package ru.ifmo.ctddev.koval.carwash;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends Activity {

    private ListView orders;
    private CursorAdapter adapter;
    private Store store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity);

        store = new Store(this);
        orders = (ListView) findViewById(R.id.order_list);
        adapter = new OrdersCursorAdapter(this, store.getAllOrders());
        orders.setAdapter(adapter);

        loadCarWashParams();

    }

    private void loadCarWashParams() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        Constants.NAME = preferences.getString(
                getString(R.string.pref_name),
                null
        );
        Constants.BOX_COUNT = preferences.getInt(
                getString(R.string.pref_box_count),
                0
        );

        if (Constants.NAME == null) {
            //First launch
            showFirstLaunchLayout();
        } else {
            setTitle(Constants.NAME);
        }
    }

    private void showFirstLaunchLayout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.create_carwash);
        final View v = getLayoutInflater().inflate(R.layout.create_carwash_dialog, null);
        builder.setView(v);
        builder.setPositiveButton(R.string.action_create, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = ((EditText) v.findViewById(R.id.carwash_name)).getText().toString();
                int count = Integer.parseInt(
                        ((EditText) v.findViewById(R.id.box_count)).getText().toString()
                );

                //add to private preferences
                SharedPreferences preferences = getPreferences(MODE_PRIVATE);
                preferences.edit().putString(getString(R.string.pref_name), name);
                preferences.edit().putInt(getString(R.string.pref_box_count), count);
                preferences.edit().commit();
                //Edit Constants
                Constants.NAME = name;
                Constants.BOX_COUNT = count;
                //Set title
                setTitle(Constants.NAME);
            }
        });

        builder.setCancelable(false);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_new:
                showAddOrderDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAddOrderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.action_add_car);
        final View v = getLayoutInflater().inflate(R.layout.add_car_dialog, null);

        final Spinner spinner = (Spinner) v.findViewById(R.id.spinner);
        List<String> times = new ArrayList<>();

        for (int i = 0; i <= 28; i++) {
            String s = i / 2 + 8 + ":" + ((i % 2) == 0 ? "00" : "30");
            if (store.getFreeBox(s) < Constants.BOX_COUNT) {
                times.add(s);
            }
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, times);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        builder.setView(v);
        builder.setPositiveButton(R.string.action_register, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String make = ((EditText) v.findViewById(R.id.make)).getText().toString();
                String color = ((EditText) v.findViewById(R.id.color)).getText().toString();
                String number = ((EditText) v.findViewById(R.id.number)).getText().toString();
                String phone = ((EditText) v.findViewById(R.id.phone)).getText().toString();

                Car car = new Car(make, color, number, phone);

                int carId = (int) store.addCar(car);
                String time = spinner.getSelectedItem().toString();
                int boxOrdinal = store.getFreeBox(time);

                Order order = new Order(boxOrdinal, carId, time);
                store.addOrder(order);

                adapter.changeCursor(store.getAllOrders());
            }
        });
        builder.show();
    }

}
