package algorithm;

//This class will check if the movement code is a valid movement code in the form:
//1,1x5,5
public class Code {
	private String code;

	private int[] coordinates;

	public Code(String s) {
		coordinates = new int[4];
		this.code = s;

		coordinates[0] = coordinates[1] = coordinates[2] = coordinates[3] = 0;
	}

	// This is used to check if the entered code is a valid code or not
	public boolean ValidCode() {
		if (code.length() != 7)
			return false;
		String[] codeSplit = code.split("x");
		if (codeSplit.length != 2)
			return false;
		if (codeSplit[0].length() != 3 && codeSplit[1].length() != 3)
			return false;
		String[] codeSplitFrom = codeSplit[0].split(",");
		String[] codeSplitTo = codeSplit[1].split(",");
		if (codeSplitFrom.length != 2 && codeSplitTo.length != 2)
			return false;
		coordinates[0] = Integer.parseInt(codeSplitFrom[0]);
		coordinates[1] = Integer.parseInt(codeSplitFrom[1]);
		coordinates[2] = Integer.parseInt(codeSplitTo[0]);
		coordinates[3] = Integer.parseInt(codeSplitTo[1]);
		if (coordinates[0] < 0 || coordinates[0] > 7)
			return false;
		if (coordinates[1] < 0 || coordinates[1] > 7)
			return false;
		if (coordinates[2] < 0 || coordinates[2] > 7)
			return false;
		if (coordinates[3] < 0 || coordinates[3] > 7)
			return false;
		
		return true;
	}

	/* Some return method to return the values of the code */
	// This will return the coordinates of the movement. The return value will
	// hold the values like this x1,y1,x2,y2
	public int[] GetCoordinates() {
		return this.coordinates;
	}

	// Will return the code entered
	public String GetCode() {
		return this.code;
	}
}
