package local.firespace.Carwash.Activity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import local.firespace.Carwash.R;
import local.firespace.Carwash.database.RecordDatabase;

public class OrderListActivity extends Activity {

	ListView orderList;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orderlistlayout);
		orderList = (ListView) findViewById(R.id.order_list);

		RecordDatabase recordDatabase = new RecordDatabase(this);
		SQLiteDatabase database = recordDatabase.getReadableDatabase();
		Cursor cursor = database.query(RecordDatabase.DATABASE_NAME, null, null, null, null, null, null);
		int numbCarwashDatabase = getIntent().getIntExtra("carwash", 0);
	}
}