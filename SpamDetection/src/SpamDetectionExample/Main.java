package SpamDetectionExample;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		//Train t = new Train();
		//t.readTrainingData("spambase.data");
		EmailTesting e = new EmailTesting();
		e.inputEmail("input.txt");
	}
}
