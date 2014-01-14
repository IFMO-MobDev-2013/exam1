package com.ctd.CarWash;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    String name;
    int count;

    boolean readFromDataBase() {
        CarWashNameDataBaseHelper carWashNameDataBaseHelper = new CarWashNameDataBaseHelper(getApplicationContext());
        SQLiteDatabase sqLiteDatabase = carWashNameDataBaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(CarWashNameDataBaseHelper.DATABASE_NAME, null, null, null, null, null, null);

        int nameColumn = cursor.getColumnIndex(CarWashNameDataBaseHelper.NAME);
        int countColumn = cursor.getColumnIndex(CarWashNameDataBaseHelper.COUNT);
        name = "";
        count = -1;

        while (cursor.moveToNext()) {
            name = cursor.getString(nameColumn);
            count = cursor.getInt(countColumn);
        }
        cursor.close();
        sqLiteDatabase.close();
        carWashNameDataBaseHelper.close();
        if (count == -1 && "".equals(name)) {
            return false;
        } else return true;
    }


    Button btnOK;
    EditText etName;
    EditText etCount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        etCount = (EditText) findViewById(R.id.etCount);
        etName = (EditText) findViewById(R.id.etName);
        btnOK = (Button) findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!readFromDataBase()) {
                    CarWashNameDataBaseHelper carWashNameDataBaseHelper = new CarWashNameDataBaseHelper(getApplicationContext());
                    SQLiteDatabase sqLiteDatabase = carWashNameDataBaseHelper.getReadableDatabase();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(CarWashNameDataBaseHelper.NAME, etName.getText().toString());
                    contentValues.put(CarWashNameDataBaseHelper.COUNT, etCount.getText().toString());
                    sqLiteDatabase.insert(CarWashNameDataBaseHelper.DATABASE_NAME, null, contentValues);
                }
                Intent intent = new Intent();
                name = etName.getText().toString();
                count = Integer.parseInt(etCount.getText().toString());
                intent.putExtra(CarWashNameDataBaseHelper.NAME, name);
                intent.putExtra(CarWashNameDataBaseHelper.COUNT, count);
                intent.setClass(getApplicationContext(), OrdersActivity.class);
                startActivity(intent);
            }
        });

        if (readFromDataBase()) {
            btnOK.performClick();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (readFromDataBase()) {
            finish();
        }
    }
}
