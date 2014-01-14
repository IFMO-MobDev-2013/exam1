package local.firespace.Carwash.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CarwashDatabase extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;
	public static final String ID = "id";
	public static final String DATABASE_NAME = "carwashdb";
	public static final String CARWASH_NAME = "name";
	public static final String CARWASH_BOX_NUMBER = "numbox";

	public static final String CREATE_DATABASE = "CREATE TABLE " + DATABASE_NAME + " (" + ID +
			" INTEGER PRIMARY KEY AUTOINCREMENT, " + CARWASH_NAME + " TEXT, " + CARWASH_BOX_NUMBER + " INTEGER);";

	public static final String DROP_DATABASE = "DROP TABLE IF EXISTS";

	public CarwashDatabase(Context context) {
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
