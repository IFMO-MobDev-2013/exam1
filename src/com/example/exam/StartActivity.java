package com.example.exam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class StartActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    EditText name, boxcount;
    DBName db;
    DBBoxes dbb;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        name = (EditText) findViewById(R.id.editText);
        boxcount = (EditText) findViewById(R.id.editText2);
        name.setText(getResources().getString(R.string.typename));
        boxcount.setText(getResources().getString(R.string.typeboxcount));
        db = new DBName(this);
        dbb = new DBBoxes(this);
        if (db.checkName()) {
            Intent intent = new Intent(this, OrdersActivity.class);
            startActivity(intent);
            this.finish();
        }
    }


    public void enterName(View v) {
        String bc = boxcount.getText().toString();
        String nn = name.getText().toString();
        if (!allDigits(bc) || bc.equals("")) {
            Toast.makeText(this, getResources().getString(R.string.toastalldigits), Toast.LENGTH_SHORT);
            return;
        }
        if (nn.equals("")) {
            Toast.makeText(this, getResources().getString(R.string.toastnonnull), Toast.LENGTH_SHORT);
            return;
        }
        db.insertName(nn, bc);
        dbb.insert(getResources().getStringArray(R.array.times), bc);
        Intent intent = new Intent(this, OrdersActivity.class);
        startActivity(intent);
        this.finish();
    }

    private boolean allDigits(String bc) {
        for (int i = 0; i < bc.length(); i++) {
            if (!Character.isDigit(bc.charAt(i))) return false;
        }
        return true;
    }
}
