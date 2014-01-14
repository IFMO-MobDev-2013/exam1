package com.example.exam;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class MyActivity extends Activity {
    String name = "";
    int win = -1;
    ArrayList<String> List = new ArrayList<String>();
    ArrayList<Record> List_more = new ArrayList<Record>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase rdb = dbHelper.getReadableDatabase();
        Cursor cursor = rdb.query(DBHelper.DATABASE_NAME, null, null, null, null, null, null, null);
        int mark_column = cursor.getColumnIndex(DBHelper.MARK);
        int color_column = cursor.getColumnIndex(DBHelper.COLOR);
        int time_column = cursor.getColumnIndex(DBHelper.TIME);
        while(cursor.moveToNext())
        {
            if (cursor.getString(mark_column)!=null && cursor.getString(mark_column).equals("FULL"))
            {
                name=cursor.getString(color_column);
                win=cursor.getInt(time_column);
            }
        }
        cursor.close();
        if (win==-1)
        {
           Intent intent=new Intent();
           intent.setClass(getApplicationContext(),New.class);
            startActivity(intent);

            Cursor cursor1 = rdb.query(DBHelper.DATABASE_NAME, null, null, null, null, null, null, null);
            while(cursor1.moveToNext())
            {
                if (cursor1.getString(mark_column)!=null && cursor1.getString(mark_column).equals("FULL"))
                {
                    name=cursor1.getString(color_column);
                    win=cursor1.getInt(time_column);
                }
            }
            cursor1.close();
        }
        TextView textView = (TextView) findViewById(R.id.textView_name);
        textView.setText(name);


        Button button_add = (Button) findViewById(R.id.button_add);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("name", name);
                intent.setClass(getApplicationContext(), Add.class);
                startActivity(intent);
                show();
            }
        });
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("mark", List_more.get(i).mark);
                intent.putExtra("color", List_more.get(i).color);
                intent.putExtra("time", List_more.get(i).time);
                intent.putExtra("number", List_more.get(i).number);
                intent.putExtra("phone", List_more.get(i).phone);
                intent.putExtra("window", List_more.get(i).window);

                intent.setClass(getApplicationContext(), Full.class);
                startActivity(intent);

            }
        });


    }

    public void show() {
        TextView textView = (TextView) findViewById(R.id.textView_name);
        textView.setText(name);
        List.clear();
        List_more.clear();
        ArrayList<Record> List_more1 = new ArrayList<Record>();
        ArrayList<String> List1 = new ArrayList<String>();
        ArrayList<String> List2 = new ArrayList<String>();
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase rdb = dbHelper.getReadableDatabase();
        Cursor cursor = rdb.query(DBHelper.DATABASE_NAME, null, null, null, null, null, null, null);
        int mark_column = cursor.getColumnIndex(DBHelper.MARK);
        int color_column = cursor.getColumnIndex(DBHelper.COLOR);
        int time_column = cursor.getColumnIndex(DBHelper.TIME);
        int window_column = cursor.getColumnIndex(DBHelper.WINDOW);
        int number_column = cursor.getColumnIndex(DBHelper.NUMBER);
        int phone_column = cursor.getColumnIndex(DBHelper.PHONE);
        while (cursor.moveToNext()) {
            if (cursor.getString(mark_column) != null && cursor.getString(color_column) != null &&
                    cursor.getString(time_column) != null && cursor.getString(window_column) != null) {
                List_more1.add(new Record(cursor.getString(mark_column), cursor.getString(color_column), cursor.getString(time_column),
                        cursor.getString(window_column), cursor.getString(number_column), cursor.getString(phone_column)));
                List2.add(cursor.getString(mark_column) + " " + cursor.getString(color_column) + " " + cursor.getString(time_column) + " Окно " + cursor.getString(window_column));
                List1.add(cursor.getString(time_column));
            }
        }
        cursor.close();
        for (int i = 8 * 60; i < 22 * 60; i += 30) {
            String q = new Integer(i / 60).toString() + ":" + new Integer(i % 60).toString();
            if (i%60==0)q+="0";
            for (int j = 0; j < List1.size(); j++) {
                if (List1.get(j).equals(q)) {
                    List.add(List2.get(j));
                    List_more.add(List_more1.get(j));
                }
            }
        }
        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, List);
        listView.setAdapter(arrayAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        show();
    }
}
