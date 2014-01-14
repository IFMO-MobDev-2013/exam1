package ru.ifmo.washer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    ListView listView;
    Button addButton;
    CarAdapter adapter;
    ArrayList<Car> items = new ArrayList<Car>();
    CarsDatabase db;
    InfoDatabase dbInfo;

    final static int TOTAL_TIME_SPACES = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        db = new CarsDatabase(this);
        db.open();
        dbInfo = new InfoDatabase(this);
        dbInfo.open();

        listView = (ListView) findViewById(R.id.carListView);
        addButton = (Button) findViewById(R.id.AddButton);

        items = db.getAllItems();
        adapter = new CarAdapter(this, items, db);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);

        ArrayList <Info> infos = dbInfo.getAllItems();
        if (infos.size() == 0){
            Intent intent = new Intent(MainActivity.this, HelloActivity.class);
            startActivityForResult(intent, 0);
        } else {
            ((TextView) findViewById(R.id.Title)).setText(infos.get(0).param[Info.NAME]);
        }


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCarActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        /*listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int a = 5;

            }
        }); */

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        dbInfo.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            copyTo(db.getAllItems(), items);
            adapter.notifyDataSetChanged();
            ((TextView) findViewById(R.id.Title)).setText(dbInfo.getAllItems().get(0).param[Info.NAME]);
        }
    }

    void copyTo(ArrayList<Car> a, ArrayList<Car> b){
        b.clear();
        for (int i = 0; i < a.size(); i++){
            b.add(a.get(i));
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.carListView) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle(items.get(info.position).param[Car.NAME]);
            String[] menuItems = new String[]{"Edit", "Delete"};
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        final Car choosedCar = items.get(info.position);
        if (item.getItemId() == 0){

        } else if (item.getItemId() == 1){
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Confirm")
                    .setMessage("Delete " + choosedCar.param[Car.NAME] + "(" + choosedCar.param[Car.COLOR] + ")" + " on " + choosedCar.getTime() + "?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            db.deleteItem(choosedCar);
                            copyTo(db.getAllItems(), items);
                            adapter.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {

                        }
                    })
                    .setCancelable(true)
                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                        public void onCancel(DialogInterface dialog) {

                        }
                    }).show();

        }


        return true;
    }


}
