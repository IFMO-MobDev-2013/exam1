package local.firespace.Carwash.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RecordDatabase extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "recorddb";
	public static final String ID = "id";

	public static final String CAR_BRAND = "brand";
	public static final String CAR_NUMBER = "number";
	public static final String CAR_COLOR = "color";
	public static final String RECORD_TIME = "time";
	public static final String TELEPHONE = "telephone";
	public static final String BOX_NUMBER = "box";
	public static final String CARWASH_NUMBER = "carwash";

	public static final String CREATE_DATABASE = "CREATE TABLE " + DATABASE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			CAR_BRAND + " TEXT, " + CAR_NUMBER + " TEXT, " + CAR_COLOR + " TEXT, " + RECORD_TIME + " INTEGER, " + TELEPHONE + " TEXT, " +
			BOX_NUMBER + " INTEGER," + CARWASH_NUMBER + " INTEGER);";

	public static final String  DROP_DATABASE = "DROP TABLE IF EXISTS";

	public RecordDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_DATABASE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (newVersion != oldVersion) {
			db.execSQL(DROP_DATABASE);
			onCreate(db);
		}
	}
}
