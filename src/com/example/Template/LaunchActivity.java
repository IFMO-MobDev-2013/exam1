package com.example.Template;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created with IntelliJ IDEA.
 * User: Александр
 * Date: 14.01.14
 * Time: 16:20
 */
public class LaunchActivity extends Activity {

    private EditText etName;
    private EditText etWindow;
    private Button register;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch);

        etName = (EditText) findViewById(R.id.launch_name);
        etWindow = (EditText) findViewById(R.id.launch_window);
        register = (Button) findViewById(R.id.launch_register);

        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String window = etWindow.getText().toString();
                if (!"".equals(name)) {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    SharedPreferences.Editor edit = prefs.edit();
                    edit.putString("wash_car_name", name);
                    edit.putString("wash_car_window", window);
                    edit.commit();
                }
                Intent intent = new Intent(LaunchActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });

    }
}
