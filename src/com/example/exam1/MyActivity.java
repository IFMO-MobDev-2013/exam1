package com.example.exam1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.exam1.databases.CarWash;
import com.example.exam1.databases.CarWashInfo;

import java.util.ArrayList;

public class MyActivity extends Activity {

    private EditText editText1, editText2;
    private CarWashInfo database;
    private Button button;
    public static int windowsNumber;
    public static String NAME;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        database = new CarWashInfo(this);
        database.open();

        ArrayList<CarWash> carWashs = database.getAll();
        if (!carWashs.isEmpty()) {
            Intent intent = new Intent(this, CarwsList.class);
            intent.putExtra("name", carWashs.get(0).getName());
            windowsNumber = carWashs.get(0).getNumber();
            NAME = carWashs.get(0).getName();
            startActivity(intent);
            finish();
        }

        button = (Button) findViewById(R.id.OK);
        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText1.getText().toString();
                String number = editText2.getText().toString();
                database.addChannel(name, number);
                Intent intent = new Intent(view.getContext(), CarwsList.class);
                intent.putExtra("name", name);
                NAME = name;
                windowsNumber = Integer.parseInt(number);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
        database.close();
    }
}
