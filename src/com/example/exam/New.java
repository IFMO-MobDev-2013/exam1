package com.example.exam;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class New extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ne);
        Button button=(Button)findViewById(R.id.button_ok);
        final EditText editText1=(EditText)findViewById(R.id.editText_n_name);
        final EditText editText2=(EditText)findViewById(R.id.editText_n_wind);
        editText1.setText("автомойка у дяди Вани");
        editText2.setText("3");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv=new ContentValues();

                cv.put(DBHelper.MARK,"FULL");
                cv.put(DBHelper.COLOR,editText1.getText().toString());
                cv.put(DBHelper.TIME,editText2.getText().toString());
                DBHelper dbHelper = new DBHelper(getApplicationContext());
                SQLiteDatabase wdb = dbHelper.getWritableDatabase();
                wdb.insert(DBHelper.DATABASE_NAME, null, cv);
                finish();
            }
        });

    }
}
