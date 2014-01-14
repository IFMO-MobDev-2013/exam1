package com.example.exam;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Add extends Activity {
    int wind=1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_l);
        Button button=(Button)findViewById(R.id.button_a);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv=new ContentValues();
                EditText editText_mark=(EditText)findViewById(R.id.editText_mark);
                EditText editText_number=(EditText)findViewById(R.id.editText_number);
                EditText editText_color=(EditText)findViewById(R.id.editText_color);
                EditText editText_phone=(EditText)findViewById(R.id.editText_phone);
                EditText editText_time=(EditText)findViewById(R.id.editText_time);
                cv.put(DBHelper.NAME,getIntent().getStringExtra("name"));
                cv.put(DBHelper.MARK,editText_mark.getText().toString());
                cv.put(DBHelper.NUMBER,editText_number.getText().toString());
                cv.put(DBHelper.COLOR,editText_color.getText().toString());
                cv.put(DBHelper.TIME,editText_time.getText().toString());

                cv.put(DBHelper.PHONE,editText_phone.getText().toString());

                DBHelper dbHelper=new DBHelper(getApplicationContext());
                SQLiteDatabase rdb=dbHelper.getReadableDatabase();
                Cursor cursor=rdb.query(DBHelper.DATABASE_NAME, null, null, null, null, null, null, null);
                int time_column=cursor.getColumnIndex(DBHelper.TIME);
                final int[] Q=new int[300];
                int w=1;
                while(cursor.moveToNext()){
                    if (cursor.getString(time_column)!=null)
                    {
                        for(int i=8*60;i<22*60;i+=30)
                        {
                            String q= new Integer(i/60).toString()+":"+new Integer(i%60).toString();
                            if (i%60==0)q+="0";
                            if (cursor.getString(time_column).equals(q)){
                                Q[(i-8*60)/30]++;

                            }
                        }
                    }
                }
                int ans=0;

                Log.d("ttt",new Integer(w).toString());
                cursor.close();
                cv.put(DBHelper.WINDOW,wind);
                SQLiteDatabase wdb=dbHelper.getWritableDatabase();
                wdb.insert(DBHelper.DATABASE_NAME,null,cv);
                finish();
            }
        });




    }
}
