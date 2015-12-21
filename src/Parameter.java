
public enum Parameter {
	unImped_Run,AMPeak,InterPeak;
	public static Parameter select(int option) {
		if(option == 1)
			return Parameter.unImped_Run;
		else if(option == 2)
			return Parameter.AMPeak;
		else if(option == 3)
			return Parameter.InterPeak;
		else
			return null;
	}
}
