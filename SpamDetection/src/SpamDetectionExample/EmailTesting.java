package SpamDetectionExample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class EmailTesting {

	String [] word = {"make",
			"address",
			"all",
			"3d",
			"our",
			"over",
			"remove",
			"internet",
			"order",
			"mail",
			"receive",
			"will",
			"people",
			"report",
			"addresses",
			"free",
			"business",
			"email",
			"you",
			"credit",
			"your",
			"font",
			"000",
			"money",
			"hp",
			"hpl",
			"george",
			"650",
			"lab",
			"labs",
			"telnet",
			"857",
			"data",
			"415",
			"85",
			"technology",
			"1999",
			"parts",
			"pm",
			"direct",
			"cs",
			"meeting",
			"original",
			"project",
			"re",
			"edu",
			"table",
			"conference",
			";",
			"(",
			"[",
			"!",
			"$",
			"#",
			"average",
			"longest",
			"total"
};
	
	HashMap<String, Double> featureMap = new HashMap<String, Double> ();
	ArrayList<Integer> lengthList = new ArrayList<Integer>();
	double average=0,longest=0,total=0,wordCount=0;
	Train tr = null;
	
	private void initializeMap() {
		for(int i=0;i<word.length;i++) {
			//featureMap.put(word[i], 0.0);
			featureMap.put(word[i], tr.trainData[1812][i+1]);
		}
	}
	
	public void inputEmail(String inputEmailPath) throws IOException {
		
		CrossValidation cv = new CrossValidation();
		tr = new Train();
		tr.readTrainingData("spambase.data",cv.seperateTrainData());
		initializeMap();

		
		
		takeDecision();
	}
	
	private void takeDecision() {
		double spamProbability,nonSpamProbablity,wordSpamProbability,wordNonSpamProbability,frequency,spam=1,nonspam=1;
		spamProbability = tr.getSpamProbablity();
		nonSpamProbablity = tr.getNonSpamProbability();
		for(int i=0;i<word.length;i++) {
			frequency = featureMap.get(word[i]);
			if(frequency!=0.0) {
				wordSpamProbability = tr.probabilityForWord(1, i+1, frequency);
				wordNonSpamProbability = tr.probabilityForWord(0, i+1, frequency);

				spam *= spamProbability * wordSpamProbability /( (spamProbability * wordSpamProbability) +(wordNonSpamProbability * nonSpamProbablity));

				nonspam *= wordNonSpamProbability * nonSpamProbablity /  ( (wordNonSpamProbability * nonSpamProbablity) + (spamProbability * wordSpamProbability) );
			}
		}
		
		if(spam>nonspam)
			System.out.println("spam");
		else System.out.println("nonspam");
		
	}
	
}

/*
 * 
Scanner sc = new Scanner(new File(inputEmailPath));
		String text = "";
		while(sc.hasNext()) {
			text = sc.next();
			storeInTheMap(text);
			wordCount++;
		}
		sc.close();
		initializeFeatureFrequency();
private void uppercaseStringCheck(String str) {
int length;
if(str.matches("[A-Z]*")) {
	length = str.length();
	lengthList.add(length);
	longest = Math.max(length, longest);
	total += length;
}
}

private void storeInTheMap(String word) {
uppercaseStringCheck(word);
if(featureMap.containsKey(word.toLowerCase())){
	double count = featureMap.get(word.toLowerCase()) + 1 ;
	featureMap.put(word.toLowerCase(), count);
}
}

private void initializeFeatureFrequency() {
double sum = 0;
for(int i=0;i<lengthList.size();i++) sum+=lengthList.get(i); 
if (lengthList.size()>0)average = sum / lengthList.size();
featureMap.put("average", average);
featureMap.put("longest", longest);
featureMap.put("total", total);

for(int i=0;i<54;i++) {
	double value = featureMap.get(word[i]);
	featureMap.put(word[i], (value*100)/wordCount);
}	
}
*/


