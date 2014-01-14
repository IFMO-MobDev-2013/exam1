package com.example.CarCleaning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MyActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        if (databaseHandler.getBoxNum() == 0) {
            Intent intent = new Intent(this, InputActivity.class);
            startActivityForResult(intent, 1);
        }
        setContentView(R.layout.main);
        upd();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.listView) {
            menu.add("Edit");
            menu.add("Delete");
        }
    }

    private void upd() {
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(databaseHandler.getCName());
        List<Car> cars = databaseHandler.getCars();
        String[] array = new String[cars.size()];
        for (int i = 0; i < cars.size(); i++) {
            array[i] = cars.get(i).color + " " + cars.get(i).name + " " + getResources().getStringArray(R.array.time)[cars.get(i).time] + " " + cars.get(i).box;
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array);
        ListView listView = (ListView) findViewById(R.id.listView);
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
            }
        }); */
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        upd();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Edit") {
        } else if (item.getTitle() == "Delete") {
        } else {
            return false;
        }
        return true;
    }

    public void onAdd(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivityForResult(intent, 1);
    }
}
