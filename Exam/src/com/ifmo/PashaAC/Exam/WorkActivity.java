package com.ifmo.PashaAC.Exam;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class WorkActivity extends Activity {
    private DataBase dataBase;
    private Button button;
    private int boxes;
    private int correctTime;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work);
        dataBase = new DataBase(this);
        dataBase.open();

        TextView textViewName = (TextView) findViewById(R.id.text_name);
        textViewName.setText(dataBase.getAutoWashingName());
        button = (Button) findViewById(R.id.button_add);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextMark = (EditText) findViewById(R.id.editText_mark);
                EditText editTextColor = (EditText) findViewById(R.id.editText_auto_colour);
                EditText editTextAutoNumber = (EditText) findViewById(R.id.editText_auto_number);
                EditText editTextTime = (EditText) findViewById(R.id.editText_time);
                EditText editTextTelephone = (EditText) findViewById(R.id.editText_telephone_number);

                String textMark = editTextMark.getText().toString();
                String textColour = editTextColor.getText().toString();
                String textAutoNumber = editTextAutoNumber.getText().toString();
                String textTime = editTextTime.getText().toString();
                textTime = getBox(textTime);
                String textTelephone = editTextTelephone.getText().toString();
                String textBox = "" + (correctTime % boxes + 1);

                if (textMark.length() == 0 || textColour.length() == 0 || textAutoNumber.length() == 0
                    || textTime.length() == 0 || textTelephone.length() == 0) {
                    Toast toast = Toast.makeText(WorkActivity.this, "Вы ввели неполные данные!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                } else {
                    dataBase.insertAuto(textMark, textColour, textAutoNumber, textTime, textTelephone, textBox);
                    Intent intent = new Intent(WorkActivity.this, MyActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    private String getBox(String time) {
        int size = 24*60 + 10;
        int[] atTime = new int[size];
        for (int i = 0; i < size; ++i)
            atTime[i] = 0;

        correctTime = getCorrectTime(time);
        Cursor cursor = dataBase.getCursor();
        boxes = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DataBase.KEY_BOX_NUMBER)));

        while (cursor.moveToNext()) {
            int ttt = getCorrectTime(cursor.getString(cursor.getColumnIndex(DataBase.KEY_TIME)));
            for (int i = ttt; i < ttt + 30; ++i)
                atTime[i]++;
        }

        for (int i = correctTime; i < size; ++i)
            if (atTime[i] < boxes) {
                correctTime = i;
                break;
            }
        String r = "";
        r = r + correctTime/60;;
        if (correctTime/60 < 10) r = "0" + r;
        r = r + ":";
        if (correctTime%60 < 10) r = r + "0";
        r = r + correctTime%60;
        return r;
    }

    private int getCorrectTime(String time) {
        return Integer.parseInt(time.substring(0, time.indexOf(":")))*60 + Integer.parseInt(time.substring(time.indexOf(":") + 1, time.length()));
    }
}
