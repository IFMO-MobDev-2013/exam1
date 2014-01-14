package local.firespace.Carwash.Activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import local.firespace.Carwash.Carwash;
import local.firespace.Carwash.R;
import local.firespace.Carwash.database.CarwashDatabase;

public class MainActivity extends Activity {

	EditText editTextNumbBoxes;
	EditText editTextCarwashName;
	Carwash selectCarwash;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainlayout);
		editTextNumbBoxes = (EditText) findViewById(R.id.carwash_num_boxes);
		editTextCarwashName = (EditText) findViewById(R.id.carwash_name);
		editTextCarwashName.setText(Carwash.DEFAULT_CARWASH_NAME);
		editTextNumbBoxes.setText(Carwash.DEFAULT_NUMB_BOXES.toString());
	}

	public void onClick(View view) {
		String carwashName = editTextCarwashName.getText().toString();
		int numbBoxes = Integer.parseInt(editTextNumbBoxes.getText().toString());
		selectCarwash = new Carwash(carwashName, numbBoxes);
		CarwashDatabase carwashDatabase = new CarwashDatabase(this);
		SQLiteDatabase database = carwashDatabase.getWritableDatabase();
		Cursor cursor = database.query(CarwashDatabase.DATABASE_NAME, null, null, null, null, null, null);
		int numbCarwashDatabase = -1;
		if (cursor.moveToFirst()) {
			int idIndex = cursor.getColumnIndex(CarwashDatabase.ID);
			int nameIndex = cursor.getColumnIndex(CarwashDatabase.CARWASH_NAME);
			do {
				if (cursor.getString(nameIndex).equals(carwashName)) {
					numbCarwashDatabase = cursor.getInt(idIndex);
					break;
				}
			} while (cursor.moveToNext());
		}

		if (numbCarwashDatabase == -1) {
			ContentValues contentValues = new ContentValues();
			contentValues.put(CarwashDatabase.CARWASH_NAME, carwashName);
			contentValues.put(CarwashDatabase.CARWASH_BOX_NUMBER, numbBoxes);
			database.insert(CarwashDatabase.DATABASE_NAME, null, contentValues);
			numbCarwashDatabase = 0;
		}

		Intent intent = new Intent(view.getContext(), OrderListActivity.class);
		intent.putExtra("carwash", numbCarwashDatabase);
		startActivity(intent);
	}
}