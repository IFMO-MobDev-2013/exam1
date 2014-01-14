package com.example.WashYourCars;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class FirstTimeActivity extends Activity {

    EditText nameEditText;
    EditText boxesEditText;
    Button okButton;

    View.OnClickListener okClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            MyDataBaseHelper myDataBaseHelper = new MyDataBaseHelper(getApplicationContext());
            SQLiteDatabase sqLiteDatabase = myDataBaseHelper.getWritableDatabase();

            String ourName = nameEditText.getText().toString();
            String ourBoxes = boxesEditText.getText().toString();

            ContentValues contentValues = new ContentValues();
            contentValues.put(MyDataBaseHelper.NAME, ourName);
            contentValues.put(MyDataBaseHelper.BOXES, ourBoxes);

            sqLiteDatabase.insert(MyDataBaseHelper.DATABASE_NAME, null, contentValues);

            sqLiteDatabase.close();
            myDataBaseHelper.close();

            finish();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firsttime);

        nameEditText = (EditText) findViewById(R.id.editTextFirstName);
        boxesEditText = (EditText) findViewById(R.id.editTextFirstBoxes);
        okButton = (Button) findViewById(R.id.buttonFirstOk);
        okButton.setOnClickListener(okClick);
    }
}
