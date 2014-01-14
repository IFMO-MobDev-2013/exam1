package ru.ifmo.washer;

/**
 * Created by asus on 14.01.14.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by asus on 13.01.14.
 */
/**
 * Created by asus on 27.12.13.
 */



/**
 * Created with IntelliJ IDEA.
 * User: asus
 * Date: 06.11.13
 * Time: 19:15
 * To change this template use File | Settings | File Templates.
 */

/**
 * Created by asus on 27.12.13.
 */
public class InfoDatabase {

    private static final String TAG = "InfoDatabase";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;


    private String DATABASE_NAME;
    private String DATABASE_TABLE;

    private String DATABASE_CREATE;

    private static final int DATABASE_VERSION = 1;

    private final Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    public InfoDatabase(Context ctx) {
        this.mCtx = ctx;
        DATABASE_NAME = "Info";
        DATABASE_TABLE = "InfoDatabase";

        DATABASE_CREATE = "CREATE TABLE "+ DATABASE_TABLE + " (";
        for (int i = 0; i < Info.tags.length; i++){
            DATABASE_CREATE += Info.tags[i];
            if (i == 0) DATABASE_CREATE += " INTEGER PRIMARY KEY AUTOINCREMENT";
            if (i < Info.tags.length - 1) DATABASE_CREATE += ", ";
        }
        DATABASE_CREATE += ");";

    }

    public InfoDatabase open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }


    public long addItem(Info item) {
        ContentValues initialValues = new ContentValues();
        for (int i = 1; i < Info.tags.length; i++){
            initialValues.put(Info.tags[i], item.param[i]);
        }

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }


    public boolean deleteItem(Info item) {
        return mDb.delete(DATABASE_TABLE, Info.tags[0] + "=" + item.param[Info.ID], null) > 0;
    }


    public ArrayList<Info> getAllItems() {
        Cursor cursor = mDb.query(DATABASE_TABLE, null, null, null, null, null, null);
        ArrayList<Info> items = new ArrayList<Info>();
        Info curItem = new Info();
        while (cursor.moveToNext()){
            curItem.clear();
            for (int i = 0; i < Info.tags.length; i++) curItem.param[i] = cursor.getString(cursor.getColumnIndex(Info.tags[i]));
            items.add(curItem.makeCopy());
        }
        cursor.close();
        return items;
    }

    public Info getItem(int id) throws SQLException {

        Cursor cursor =
                mDb.query(true, DATABASE_TABLE, null, Info.tags[0] + "=" + id, null,
                        null, null, null, null);

        if (cursor == null) {
            Log.e(TAG, "Error getting item");
            return null;
        }

        cursor.moveToFirst();
        Info item = new Info();
        for (int i = 0; i < Info.tags.length; i++) item.param[i] = cursor.getString(cursor.getColumnIndex(Info.tags[i]));
        cursor.close();

        return item;
    }

    public boolean updateItem(Info item) {
        ContentValues args = new ContentValues();
        for (int i = 0; i < Info.tags.length; i++){
            args.put(Info.tags[i], item.param[i]);
        }

        return mDb.update(DATABASE_TABLE, args, Info.tags[0] + "=" + item.param[0], null) > 0;
    }


}


