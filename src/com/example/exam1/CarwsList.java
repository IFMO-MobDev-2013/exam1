package com.example.exam1;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.example.exam1.databases.CarsSchedule;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: dronov
 * Date: 14.01.14
 * Time: 16:03
 * To change this template use File | Settings | File Templates.
 */
public class CarwsList extends Activity {
    public static final int DELETE = 0;

    private TextView textView;
    private ListView listView;
    private Cursor cursor;
    private SimpleCursorAdapter adapter;
    private CarsSchedule database;
    private Button button;
    static ArrayList<Cars> carses;
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.carws_list);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        textView = (TextView) findViewById(R.id.carWashName);
        textView.setText(name);
        listView = (ListView) findViewById(R.id.listView);
        button = (Button) findViewById(R.id.addButton);

        database = new CarsSchedule(this);
        database.open();

        cursor = database.getAllData();
        carses = database.getAll();
        startManagingCursor(cursor);

        String [] from = new String[] {
                CarsSchedule.COLUMN_CAR,
                CarsSchedule.COLUMN_WINDOW,
                CarsSchedule.COLUMN_TIME
        };

        int [] to = new int[] {
                R.id.carName,
                R.id.carWindowNumber,
                R.id.carTime
        };

        adapter = new SimpleCursorAdapter(this, R.layout.adapter, cursor, from, to);
        listView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextIntent = new Intent(view.getContext(), AddCarActivity.class);
                startActivity(nextIntent);
            }
        });

        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = ((TextView)view.findViewById(R.id.carName)).getText().toString();
                CarsSchedule carsSchedule = new CarsSchedule(view.getContext());
                carsSchedule.open();
                Cars result = carsSchedule.selectCars(name);
                Intent currentIntent = new Intent(view.getContext(), Information.class);
                currentIntent.putExtra("result", result);
                startActivity(currentIntent);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);    //To change body of overridden methods use File | Settings | File Templates.
        menu.add(0, DELETE, 0, getString(R.string.DELETE_RSS));
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (DELETE == item.getItemId()) {
            AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            database.deleteChannel((int) adapterContextMenuInfo.id);

            CarsSchedule database1 = new CarsSchedule(this);
            database1.open();
            database1.deleteChannel(adapterContextMenuInfo.id);

            cursor.requery();
            carses = database1.getAll();

            database1.close();
            return true;
        }

        return super.onContextItemSelected(item);
    }
}
