package local.firespace.Carwash;

public class Carwash {
	public static final String DEFAULT_CARWASH_NAME = "автомойка у Дяди Вани";
	public static final Integer DEFAULT_NUMB_BOXES = 3;
	private String name;
	private int numbBoxes;

	public Carwash(String name, int numbBoxes) {
		this.name = name;
		this.numbBoxes = numbBoxes;
	}

	public Carwash() {
		name = DEFAULT_CARWASH_NAME;
		numbBoxes = DEFAULT_NUMB_BOXES;
	}

	public String getName() {
		return name;
	}

	public int getNumbBoxes() {
		return numbBoxes;
	}
}
