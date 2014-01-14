package ru.zulyaev.ifmo.exam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class WelcomeActivity extends Activity {
    private DefaultSettings settings;
    private EditText name;
    private EditText boxNumber;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = new DefaultSettings(this);

        if (settings.isInitialized()) {
            done();
            return;
        }

        setContentView(R.layout.welcome);
        name = (EditText) findViewById(R.id.name);
        boxNumber = (EditText) findViewById(R.id.box_number);
    }

    public void onSave(View view) {
        settings.init(name.getText().toString(), Integer.parseInt(boxNumber.getText().toString()));
        done();
    }

    private void done() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}