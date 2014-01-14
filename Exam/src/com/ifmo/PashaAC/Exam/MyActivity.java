package com.ifmo.PashaAC.Exam;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.*;

import java.util.ArrayList;

public class MyActivity extends Activity {
    private DataBase dataBase;
    final Context context = this;
    private String AUTOWASHING_NAME = "";
    private int NUMBER_BOX = -1;
    private ArrayList<Order> orders = new ArrayList<Order>();
    private MyAdapter boxAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //deleteDatabase(DataBase.DATABASE_NAME);
        dataBase = new DataBase(this);
        dataBase.open();

        if (dataBase.isEmpty()) {
            LayoutInflater li = LayoutInflater.from(context);
            View promptsView = li.inflate(R.layout.prompts, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setView(promptsView);
            final EditText userInputName = (EditText) promptsView
                    .findViewById(R.id.editTextDialogUserInput);
            final EditText userInputBox = (EditText) promptsView
                    .findViewById(R.id.editTextDialogUserInput2);

            alertDialogBuilder
                    .setCancelable(false)
                    .setNeutralButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    if (userInputName.getText().toString().length() > 0 &&
                                            userInputBox.getText().toString().length() > 0) {
                                        AUTOWASHING_NAME = userInputName.getText().toString();
                                        NUMBER_BOX = Integer.parseInt(userInputBox.getText().toString());
                                    }
                                    else {
                                        AUTOWASHING_NAME = userInputName.getHint().toString();
                                        NUMBER_BOX = Integer.parseInt(userInputBox.getHint().toString());
                                    }
                                    dataBase.insertAutoWashingName(AUTOWASHING_NAME, NUMBER_BOX);
                                    dialog.cancel();
                                    solve();
                                }
                            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        else {
            AUTOWASHING_NAME = dataBase.getAutoWashingName();
            NUMBER_BOX = dataBase.getAutoWashingBox();
            solve();
        }
    }

    private void solve() {
        Button button = (Button) findViewById(R.id.button_add);
        TextView textView_name = (TextView) findViewById(R.id.text_name);
        textView_name.setText(AUTOWASHING_NAME);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this, WorkActivity.class);
                startActivity(intent);
            }
        });

        loadList();
    }

    private boolean cmp(String time1, String time2) {
        int time11 = Integer.parseInt(time1.substring(0, time1.indexOf(":")));
        int time21 = Integer.parseInt(time2.substring(0, time2.indexOf(":")));
        if (time11 > time21) return true;
        if (time11 < time21) return false;

        time11 = Integer.parseInt(time1.substring(time1.indexOf(":") + 1, time1.length()));
        time21 = Integer.parseInt(time2.substring(time2.indexOf(":") + 1, time2.length()));

        if (time11 > time21) return true;
        if (time11 < time21) return false;
        return true;
    }

    private void loadList() {
        Cursor cursor = dataBase.getCursor();
        orders = new ArrayList<Order>();

        while (cursor.moveToNext()) {
            String mark = cursor.getString(cursor.getColumnIndex(DataBase.KEY_AUTO));
            String colour = cursor.getString(cursor.getColumnIndex(DataBase.KEY_COLOUR));
            String auto_number = cursor.getString(cursor.getColumnIndex(DataBase.KEY_AUTO_NUMBER));
            String time = cursor.getString(cursor.getColumnIndex(DataBase.KEY_TIME));
            String telephone = cursor.getString(cursor.getColumnIndex(DataBase.KEY_TELEPHONE_NUMBER));
            String box = cursor.getString(cursor.getColumnIndex(DataBase.KEY_BOX_NUMBER));

            Order tmp = new Order(mark, colour, auto_number, time, telephone, box);
            orders.add(tmp);
        }

        for (int i = 0; i < orders.size(); ++i)
            for (int j = i + 1; j < orders.size(); ++j)
                if (cmp(orders.get(i).time, orders.get(j).time)) {
                    Order tmp = orders.get(i);
                    orders.set(i, orders.get(j));
                    orders.set(j, tmp);
                }

        boxAdapter = new MyAdapter(this, orders);
        ListView lvMain = (ListView) findViewById(R.id.listView);
        lvMain.setAdapter(boxAdapter);
    }
}
