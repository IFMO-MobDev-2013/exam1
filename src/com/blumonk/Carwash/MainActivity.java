package com.blumonk.Carwash;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    private DbAdapter dbAdapter;
    private String carwashTitle = "";
    private int carwashBoxesCount;
    private ListView ordersList;
    private Cursor cursor;
    private SimpleCursorAdapter cursorAdapter;
    private TextView carwashNameField;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbAdapter = new DbAdapter(this);
        dbAdapter.open();
        Cursor registered = dbAdapter.getRegisterData();
        try {
            registered.moveToFirst();
            carwashTitle = registered.getString(1);
            carwashBoxesCount = registered.getInt(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (carwashTitle.length() == 0 || carwashTitle == null) {
            setContentView(R.layout.signup);
        } else {
            setContentView(R.layout.main);
            carwashNameField = (TextView) findViewById(R.id.carwashtitle);
            carwashNameField.setText(carwashTitle);

            ordersList = (ListView) findViewById(R.id.listView);
            cursor = dbAdapter.getAllOrders();
            startManagingCursor(cursor);
            String[] from = new String[] {DbAdapter.KEY_CAR_MODEL, DbAdapter.KEY_CAR_COLOUR, DbAdapter.KEY_BOX_NUMBER, DbAdapter.KEY_ORDER_TIME};
            int[] to = new int[] {R.id.carmodel, R.id.carcolour, R.id.carboxnumber, R.id.cartime};
            cursorAdapter = new SimpleCursorAdapter(this, R.layout.orderadapter, cursor, from, to);
            ordersList.setAdapter(cursorAdapter);
            ordersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(view.getContext(), LookActivity.class);
                    cursor.moveToPosition(position);
                    intent.putExtra("model", cursor.getString(cursor.getColumnIndexOrThrow("model")));
                    intent.putExtra("colour", cursor.getString(cursor.getColumnIndexOrThrow("colour")));
                    intent.putExtra("number", cursor.getString(cursor.getColumnIndexOrThrow("number")));
                    intent.putExtra("phone", cursor.getString(cursor.getColumnIndexOrThrow("phone")));
                    intent.putExtra("box", cursor.getInt(cursor.getColumnIndexOrThrow("box")));
                    intent.putExtra("time", cursor.getString(cursor.getColumnIndexOrThrow("time")));
                    startActivity(intent);

                }
            });
            registerForContextMenu(ordersList);

        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if ("Edit".equals(item.getTitle())) {
            cursor.moveToPosition((int) adapterContextMenuInfo.position);
            String model = cursor.getString(cursor.getColumnIndexOrThrow("model"));
            String colour = cursor.getString(cursor.getColumnIndexOrThrow("colour"));
            int box = cursor.getInt(cursor.getColumnIndexOrThrow("box"));
            int id = (int) adapterContextMenuInfo.id;

            Intent intent = new Intent(this, EditOrderActivity.class);
            intent.putExtra("model", model);
            intent.putExtra("colour", colour);
            intent.putExtra("box", box);
            intent.putExtra("id", id);
            intent.putExtra("maxboxes", carwashBoxesCount);
            intent.putExtra("action", "edit");

            startActivity(intent);
        } else if ("Delete".equals(item.getTitle())){
            dbAdapter.deleteOrder((int) adapterContextMenuInfo.id);
            cursor.requery();
        } else {
            return false;
        }
        return true;
    }

    public void signUp(View view) {
        EditText titleEdit = (EditText) findViewById(R.id.signuptitle);
        EditText countEdit = (EditText) findViewById(R.id.signupcount);
        String title = titleEdit.getText().toString();
        String count = countEdit.getText().toString();
        if (title.length() == 0 || count.length() == 0) {
            Toast t = Toast.makeText(getApplicationContext(), "Please, fill all the fields", Toast.LENGTH_SHORT);
            t.show();
        } else {
            dbAdapter.signUp(title, Integer.parseInt(count));
        }
        carwashTitle = title;
        carwashBoxesCount = Integer.parseInt(count);
        setContentView(R.layout.main);
        carwashNameField = (TextView) findViewById(R.id.carwashtitle);
        carwashNameField.setText(carwashTitle);

        ordersList = (ListView) findViewById(R.id.listView);
        cursor = dbAdapter.getAllOrders();
        startManagingCursor(cursor);
        String[] from = new String[] {DbAdapter.KEY_CAR_MODEL, DbAdapter.KEY_CAR_COLOUR, DbAdapter.KEY_BOX_NUMBER, DbAdapter.KEY_ORDER_TIME};
        int[] to = new int[] {R.id.carmodel, R.id.carcolour, R.id.carboxnumber, R.id.cartime};
        cursorAdapter = new SimpleCursorAdapter(this, R.layout.orderadapter, cursor, from, to);
        ordersList.setAdapter(cursorAdapter);
        ordersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), LookActivity.class);
                cursor.moveToPosition(position);
                intent.putExtra("model", cursor.getString(cursor.getColumnIndexOrThrow("model")));
                intent.putExtra("colour", cursor.getString(cursor.getColumnIndexOrThrow("colour")));
                intent.putExtra("number", cursor.getString(cursor.getColumnIndexOrThrow("number")));
                intent.putExtra("phone", cursor.getString(cursor.getColumnIndexOrThrow("phone")));
                intent.putExtra("box", cursor.getInt(cursor.getColumnIndexOrThrow("box")));
                intent.putExtra("time", cursor.getString(cursor.getColumnIndexOrThrow("time")));
                startActivity(intent);

            }
        });
        registerForContextMenu(ordersList);

    }

    public void addNewOrder(View view) {
        Intent intent = new Intent(this, EditOrderActivity.class);
        intent.putExtra("action", "add");
        intent.putExtra("maxboxes", carwashBoxesCount);
        startActivity(intent);
    }

}
