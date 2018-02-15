package SpamDetectionExample;

import java.io.FileNotFoundException;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		SpamTesting st = new SpamTesting();
		st.getTestAndTraningData("spambase.data");
	}
}
