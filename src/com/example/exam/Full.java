package com.example.exam;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class Full extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full);
        TextView textView1=(TextView)findViewById(R.id.textView_mark);
        TextView textView2=(TextView)findViewById(R.id.textView_color);
        TextView textView3=(TextView)findViewById(R.id.textView_time);
        TextView textView4=(TextView)findViewById(R.id.textView_number);
        TextView textView5=(TextView)findViewById(R.id.textView_phone);
        TextView textView6=(TextView)findViewById(R.id.textView_window);

        textView1.setText("Марка:"+getIntent().getStringExtra("mark"));
        textView2.setText("Цвет:"+getIntent().getStringExtra("color"));
        textView3.setText("Время:"+getIntent().getStringExtra("time"));
        textView5.setText("Гос. номер:"+getIntent().getStringExtra("number"));
        textView6.setText("Телефон:"+getIntent().getStringExtra("phone"));
        textView4.setText("Окно:"+getIntent().getStringExtra("window"));
    }
}
