package local.firespace.Carwash;

public class Record {
	private String carBrand;
	private String carColor;
	private MyTime time;
	private int boxNum;
	private String carNumber;
	private String telephoneNumber;

	public Record(String carBrand, String carColor, MyTime time, int boxNum, String carNumber, String telephoneNumber) {
		this.carBrand = carBrand;
		this.carColor = carColor;
		this.time = time;
		this.boxNum = boxNum;
		this.carNumber = carNumber;
		this.telephoneNumber = telephoneNumber;
	}

	public String getCarBrand() {
		return carBrand;
	}

	public String getCarColor() {
		return carColor;
	}

	public MyTime getTime() {
		return time;
	}

	public int getBoxNum() {
		return boxNum;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTime(MyTime time) {
		this.time = time;
	}
}
