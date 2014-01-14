package ru.marsermd.IpsumLotis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;
import ru.marsermd.IpsumLotis.CarModel;
import ru.marsermd.IpsumLotis.CarWashModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

/**
 * Created with IntelliJ IDEA.
 * User: misch_000
 * Date: 08.11.13
 * Time: 14:13
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns{

    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "IpsumLotis";

    // Table Names
    private static final String TABLE_WASHERS = "CarWasher";

    //column names
    private static final String KEY_TIME = "time";
    private static final String KEY_BOX = "box";
    private static final String KEY_NUMBER = "number";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_COLOR = "color";
    private static final String KEY_MODEL = "model";


    // Table Create Statement
    private static final String CREATE_TABLE_WASHERS = "CREATE TABLE " + TABLE_WASHERS
            + "(" + KEY_TIME + " INTEGER," + KEY_BOX + " INTEGER," +
            KEY_NUMBER + " TEXT," + KEY_PHONE + " TEXT," + KEY_COLOR + " TEXT," + KEY_MODEL + " TEXT"+ ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        onUpgrade(getWritableDatabase(), 1, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_WASHERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        if (oldVersion == newVersion)
            return;
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WASHERS);

        // create new tables
        onCreate(db);
    }

    public long addCar(CarModel car) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_WASHERS + " WHERE " +
                KEY_TIME + " = " + car.getTime() + " AND " +
                KEY_BOX + " = " +  car.getAssignedBox();

        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null && c.moveToFirst()) {
            if (c.getString(c.getColumnIndex(KEY_MODEL)).equals("")) {
                deleteCar(readFromCursor(c));
            }
            else {
                return c.getInt(c.getColumnIndex(KEY_TIME));
            }
        }

        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TIME, car.getTime());
        values.put(KEY_BOX, car.getAssignedBox());
        values.put(KEY_COLOR, car.getColor());
        values.put(KEY_PHONE, car.getPhone());
        values.put(KEY_NUMBER, car.getNumber());
        values.put(KEY_MODEL, car.getModel());

        // insert row
        long categoryId = db.insertWithOnConflict(TABLE_WASHERS, null, values, SQLiteDatabase.CONFLICT_IGNORE);

        return categoryId;
    }
    public int updateCar(CarModel car) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TIME, car.getTime());
        values.put(KEY_BOX, car.getAssignedBox());
        values.put(KEY_COLOR, car.getColor());
        values.put(KEY_PHONE, car.getPhone());
        values.put(KEY_NUMBER, car.getNumber());
        values.put(KEY_MODEL, car.getModel());

        // updating row
        return db.update(TABLE_WASHERS, values, KEY_TIME + " = ? AND " + KEY_BOX + " = ? ",
                new String[] { String.valueOf(car.getTime()), String.valueOf(car.getAssignedBox())});
    }

    public CarModel getCar(int time, int box) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_WASHERS + " WHERE " +
                KEY_TIME + " = " + time + " AND " +
                KEY_BOX + " = " +  box;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c == null || !c.moveToFirst())
            return null;

        return readFromCursor(c);
    }

    private CarModel readFromCursor(Cursor c) {

        CarModel car = new CarModel();
        car.setTime(c.getInt(c.getColumnIndex(KEY_TIME)));
        car.setAssignedBox(c.getInt(c.getColumnIndex(KEY_BOX)));
        car.setColor((c.getString(c.getColumnIndex(KEY_COLOR))));
        car.setModel(c.getString(c.getColumnIndex(KEY_MODEL)));
        car.setNumber(c.getString(c.getColumnIndex(KEY_NUMBER)));
        car.setPhone(c.getString(c.getColumnIndex(KEY_PHONE)));

        return car;
    }

    public List<CarModel> getAllCars() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<CarModel> carModels = new ArrayList<CarModel>();
        String selectQuery = "SELECT * FROM " + TABLE_WASHERS;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                CarModel tmp = readFromCursor(c);
                if (tmp.getModel().equals(""))
                    continue;
                carModels.add(tmp);
            } while (c.moveToNext());
        }

        return carModels;
    }

    public List<Integer> getFreeTimeAtBox(int boxID) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Integer> freeTime = new ArrayList<Integer>();
        String selectQuery = "SELECT * FROM " + TABLE_WASHERS;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                CarModel tmp = readFromCursor(c);
                if (tmp.getModel().equals("")) {
                    freeTime.add(tmp.getTime());
                    freeTime.add(tmp.getTime());
                }
            } while (c.moveToNext());
        }
        return freeTime;
    }

    public Integer[] getFreeTime() {
        TreeSet<Integer> freeTime = new TreeSet<Integer>();
        for (int i = 0; i < CarWashModel.getBoxCount(); i++) {
            freeTime.addAll(getFreeTimeAtBox(i));
        }
        return freeTime.toArray(new Integer[freeTime.size()]);
    }

    public int getBoxForTime(int t) {
        for (int i = 0; i < CarWashModel.getBoxCount(); i++) {
            if (getFreeTimeAtBox(i).contains(t)) return i;
        }
        return -1;
    }


    public void deleteCar(CarModel car) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WASHERS, KEY_TIME + " = ? AND " + KEY_BOX + " = ? ",
                new String[] { String.valueOf(car.getTime()), String.valueOf(car.getAssignedBox())});
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
