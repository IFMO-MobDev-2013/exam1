package ru.ifmo.ctddev.isaev.CarWash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StartActivity extends Activity {
    Button startButton;
    EditText nameEdit;
    EditText numEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences preferences = getSharedPreferences(General.PREFERENCES, Context.MODE_PRIVATE);
        General.REAL_NAME = preferences.getString(General.NAME, null);
        General.NUMBER_OF_BOXES = preferences.getInt(General.BOX_NUMBER, 0);
        final Intent intent = new Intent(this, ListActivity.class);
        DatabaseManager.getInstance().init(getApplicationContext());
        if (General.REAL_NAME == null) {
            setContentView(R.layout.main);
            startButton = (Button) findViewById(R.id.button);
            nameEdit = (EditText) findViewById(R.id.editText);
            numEdit = (EditText) findViewById(R.id.editText2);
            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = nameEdit.getText().toString();
                    String num = numEdit.getText().toString();
                    if (name.length() == 0 || num.length() == 0) {
                        Toast.makeText(getApplicationContext(), R.string.fill_correct, Toast.LENGTH_SHORT);
                    } else {
                        SharedPreferences.Editor editor = preferences.edit();
                        General.REAL_NAME = name;
                        General.NUMBER_OF_BOXES = Integer.parseInt(num);
                        editor.putString(General.NAME, General.REAL_NAME);
                        editor.putInt(General.BOX_NUMBER, General.NUMBER_OF_BOXES);
                        editor.commit();
                        startActivity(intent);
                    }
                }
            });
        } else {
            startActivity(intent);
        }
    }
}
