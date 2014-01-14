package ru.zulyaev.ifmo.exam;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import ru.zulyaev.ifmo.exam.db.DbHelper;
import ru.zulyaev.ifmo.exam.db.OrderTable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity implements AddDialog.AddListener {
    private static final DateFormat FORMAT = new SimpleDateFormat("k:mm");
    private static final int WORK_FROM = 8;
    private static final int WORK_TILL = 22;



    private ListView view;
    private DbHelper helper;
    private OrderTable table;
    private DefaultSettings settings;
    private OrdersAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settings = new DefaultSettings(this);
        setTitle(settings.getCarWashName());

        view = new ListView(this);
        setContentView(view);

        helper = new DbHelper(this);
        table = new OrderTable(helper.getWritableDatabase());
        adapter = new OrdersAdapter(this, table);
        view.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        helper.close();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                new AddDialog(this, getFreeTime()).show(getFragmentManager(), "TAG");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    List<String> getFreeTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, WORK_FROM);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        List<Long> notFree = table.getNotFreeTime(settings.getBoxNumber());
        int cur = 0;

        List<String> freeTime = new ArrayList<String>();

        while (calendar.get(Calendar.HOUR_OF_DAY) < WORK_TILL) {
            long time = calendar.getTimeInMillis();
            while (notFree.size() > cur && notFree.get(cur) < time) {
                cur++;
            }
            if (notFree.size() <= cur || notFree.get(cur) != calendar.getTimeInMillis()) {
                freeTime.add(FORMAT.format(new Date(time)));
            }
            calendar.add(Calendar.MINUTE, 30);
        }

        return freeTime;
    }

    @Override
    public void onAdd(String model, String color, String number, String phone, String time) {
        try {
            long offset = FORMAT.parse(time).getTime() + FORMAT.getTimeZone().getRawOffset();
            table.addOrder(model, color, number, phone, offset, getFreeBox(offset));
            adapter.refresh();
        } catch (ParseException e) {
            Log.e("MainActivity", "wat", e);
        }
    }

    private long getFreeBox(long offset) {
        List<Long> notFree = table.getNotFreeBoxes(offset);
        int boxes = settings.getBoxNumber();
        boolean[] used = new boolean[boxes + 1];
        for (long l : notFree) {
            used[(int) l] = true;
        }
        for (int i = 1; i <= boxes; ++i) {
            if (!used[i]) {
                return i;
            }
        }
        return -1;
    }
}