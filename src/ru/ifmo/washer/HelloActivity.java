package ru.ifmo.washer;

/**
 * Created by asus on 14.01.14.
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class HelloActivity extends Activity {

    InfoDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello);

        db = new InfoDatabase(this);
        db.open();

        final EditText name = (EditText) findViewById(R.id.userName);
        final EditText boxes = (EditText) findViewById(R.id.boxesText);
        Button button = (Button) findViewById(R.id.helloButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Info info = new Info();
                info.param[Info.NAME] = name.getText().toString();
                info.param[Info.BOXES] = boxes.getText().toString();
                db.addItem(info);
                setResult(RESULT_OK);
                finish();
            }
        });



    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }


}

