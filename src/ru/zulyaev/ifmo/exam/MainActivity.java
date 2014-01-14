package ru.zulyaev.ifmo.exam;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends Activity {
    private ListView view;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = new ListView(this);
        setContentView(view);


    }
}