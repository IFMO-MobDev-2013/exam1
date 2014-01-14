package com.example.Template;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class StartActivity extends Activity {

    private ListView listView;
    private TextView tvCarWashName;
    private TextView tvCarWashWindow;

    private ArrayList<HashMap<String, String>> getOrderList() {
        CarWashDataBase carWashDataBase = new CarWashDataBase(this);
        ArrayList<HashMap<String, String>> items = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;

        ArrayList<Order> result = carWashDataBase.getAllOrders();

        for (int i = 0; i < result.size(); i++) {
            map = new HashMap<String, String>();
            map.put("time", result.get(i).getTime());
            map.put("model_color", result.get(i).getModelColor());
            map.put("number_window", result.get(i).getNumberWindow());
            map.put("number_car", result.get(i).getNumberCar());
            map.put("number_phone", result.get(i).getPhone());
            items.add(map);
        }

        /*map = new HashMap<String, String>();
        map.put("time", "9:00");
        map.put("model_color", "AUDI(Red)");
        map.put("number_window", "Window number " + "2");
        items.add(map);

        map = new HashMap<String, String>();
        map.put("time", "10:00");
        map.put("model_color", "FORD(Grey)");
        map.put("number_window", "Window number " + "3");
        items.add(map);*/

        return items;
    }

    public void writeOrder() {
        CarWashDataBase carWashDataBase = new CarWashDataBase(this);

        Order order = new Order("8:00", "BMW(Black)", "Номер окна: " + "1", "XXX", "1234567890");
        carWashDataBase.addOrder(order);

        order = new Order("9:00", "AUDI(Red)", "Номер окна: " + "2", "YYY", "1111111111");
        carWashDataBase.addOrder(order);

        order = new Order("10:00", "FORD(Black)", "Номер окна: " + "3", "ZZZ", "999999999");
        carWashDataBase.addOrder(order);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = prefs.getBoolean("check", false);
        if(!previouslyStarted){
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean("check", true);
            edit.commit();
            writeOrder();
            Intent intent = new Intent(StartActivity.this, LaunchActivity.class);
            startActivity(intent);
        }

        tvCarWashName = (TextView) findViewById(R.id.main_carwash_name);
        tvCarWashWindow = (TextView) findViewById(R.id.main_window);
        String carWashName = prefs.getString("wash_car_name", "автомойка у дяди Вани");
        String carWashWindow = prefs.getString("wash_car_window", "3");

        tvCarWashName.setText("Вас приветствует " + carWashName + "!");
        tvCarWashWindow.setText("Количество доступных окон: " + carWashWindow);

        listView = (ListView) findViewById(R.id.list_view_main);

        ArrayList<HashMap<String, String>> items = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;

        items = getOrderList();

        SimpleAdapter adapter = new SimpleAdapter(StartActivity.this, items, R.layout.start_list_view,
                new String[]{"time", "model_color", "number_window"},
                new int[] {R.id.time, R.id.model_color, R.id.number_window});
        listView.setAdapter(adapter);
        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                Intent intent = new Intent(StartActivity.this, WorkActivity.class);

                Map map = (Map) parent.getAdapter().getItem(position);
                intent.putExtra("time", (String) map.get("time")).putExtra("modelColor", (String) map.get("model_color")).putExtra("numberWindow", (String) map.get("number_window")).putExtra("numberCar", (String) map.get("number_car")).putExtra("numberPhone", (String) map.get("number_phone"));
                startActivity(intent);
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.list_view_main) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle("Default name");
            String[] menuItems = {"Edit", "Delete", "trololo"};
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
            Adapter ad = ((ListView)v).getAdapter();
            Map map = (Map) ad.getItem(0);
            String lol = (String) map.get("text");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();

        //Log.d("pfffffffffff", new Integer(menuItemIndex).toString());
        return true;
    }
}
