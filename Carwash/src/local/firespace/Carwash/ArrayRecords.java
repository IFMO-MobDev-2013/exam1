package local.firespace.Carwash;

import java.util.ArrayList;

public class ArrayRecords {
	public static final int ALLOWED_TIMES_NUMBER = 28;
	public static final MyTime[] ALLOWED_TIMES = {
			new MyTime(8, 0), new MyTime(8, 30), new MyTime(9, 0), new MyTime(9, 30), new MyTime(10, 0), new MyTime(10, 30),
			new MyTime(11, 0), new MyTime(11, 30), new MyTime(12, 0), new MyTime(12, 30), new MyTime(13, 0), new MyTime(13, 30),
			new MyTime(14, 0), new MyTime(14, 30), new MyTime(15, 0), new MyTime(15, 30), new MyTime(16, 0), new MyTime(16, 30),
			new MyTime(17, 0), new MyTime(17, 30), new MyTime(18, 0), new MyTime(18, 30), new MyTime(19, 0), new MyTime(19, 30),
			new MyTime(20, 0), new MyTime(20, 30), new MyTime(21, 0), new MyTime(21, 30)};
	private ArrayList<Record> records;
	private int numb_boxes;
	private int[] numbOccupiedBoxes = new int[ALLOWED_TIMES_NUMBER];

	public ArrayRecords(int numb_boxes) {
		this.numb_boxes = numb_boxes;
	}

	private int getIterByTime(MyTime myTime) {
		for (int i = 0; i < ALLOWED_TIMES_NUMBER; i++) {
			if (myTime.equals(ALLOWED_TIMES[i]))
				return i;
		}

		return -1;
	}

	public boolean addRecord(Record record) {
		if (numbOccupiedBoxes[getIterByTime(record.getTime())] < numb_boxes) {
			numbOccupiedBoxes[getIterByTime(record.getTime())]++;
			records.add(record);
			return true;
		} else {
			return false;
		}
	}

	public ArrayList<Record> getRecords() {
		return records;
	}
}