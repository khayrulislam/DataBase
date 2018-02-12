package SpamDetectionExample;


import java.util.HashMap;
import java.util.Random;

public class CrossValidation {
	
	HashMap<Integer, Boolean> previousChoose = new HashMap<Integer, Boolean>();
	private int tenPersent = 4601*10 / 100;
	
	public HashMap<Integer, Boolean> seperateTrainData() {
		
		HashMap<Integer, Boolean> test = new HashMap<Integer, Boolean>();
		Random rand = new Random();
		int  n = rand.nextInt(4601) + 1;
		int i = 0;
		while(true) {
			if(!previousChoose.containsKey(n)) {
				test.put(n, true);
				previousChoose.put(n, true);
				i++;
			}
			if (i==tenPersent) break;
		}
		return test;
	}
	

}
