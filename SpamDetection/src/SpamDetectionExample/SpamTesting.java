package SpamDetectionExample;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class SpamTesting {

	private double [][] testData;
	private double [][] traningData;
	private double featureSpamProbability,featureNonSpamProbability,spam=1,nonSpam=1,spamProbability,nonSpamPobablility,totalSpam,totalNonSpam;
	
	ArrayList<Integer> testAsnwer = new ArrayList<Integer>();
	ArrayList<Integer> originalAsnwer = new ArrayList<Integer>();
	SeparateTraningAndTestData s;
	
	
	public void getTestAndTraningData(String path) throws FileNotFoundException {
		s = new SeparateTraningAndTestData(path);	
		for(int i=0;i<10;i++) {
			initialize();
			takeDecision();
			new ResultEvaluation(testAsnwer, originalAsnwer);	
		}
	}
	
	private void initialize() {
		testAsnwer.clear();
		originalAsnwer.clear();
		testData = s.getTestData();
		traningData = s.getTraningData();
		spamProbability = s.getSpamProbability();
		nonSpamPobablility = s.getNonSpamPobablility();
		totalSpam = s.getTotalSpam();
		totalNonSpam =s.getTotalNonSpam();
	}
	
	public double probabilityWithinARange(double emailtype,int pos,double frequency){
		double occurance=0;
		double start,end;
		start = frequency - frequency*0.05;
		end = frequency + frequency*0.05;	
		for(int i=1;i<traningData.length;i++) {
			if(traningData[i][traningData[i].length-1]==emailtype && traningData[i][pos] > start && traningData[i][pos]<=end && frequency!=0.0) {
				occurance ++;
			}
		}	
		return occurance;
	}
	
	
	public double probabilityForFeature(double emailType,int pos,double frequency) {
		double occurance = 0;
		for(int i=1;i<traningData.length;i++) {
			if(traningData[i][traningData[i].length-1]==emailType && traningData[i][pos]==frequency && frequency!=0.0) {
				occurance ++;
			}
		}
		if(occurance == 0) occurance = probabilityWithinARange(emailType,pos,frequency);
		return occurance;
	}
	
	
	private void takeDecision(){
		
		for(int i=1;i<testData.length;i++) {
			spam=1;
			nonSpam=1;
			//System.out.println(testData[i].length);
			for(int j=1;j<testData[i].length;j++) {
				
				if(testData[i][j]!=0) {
					featureSpamProbability  = probabilityForFeature(1,j,testData[i][j]) / totalSpam;
					featureNonSpamProbability = probabilityForFeature(0,j,testData[i][j]) / totalNonSpam;
					
					//System.out.println(featureSpamProbability+"       /       "+featureNonSpamProbability);
					if(featureSpamProbability!=0 || featureNonSpamProbability!=0) {
						spam *= spamProbability * featureSpamProbability /( (spamProbability * featureSpamProbability) +(featureNonSpamProbability * nonSpamPobablility));
						nonSpam *= featureNonSpamProbability * nonSpamPobablility /  ( (featureNonSpamProbability * nonSpamPobablility) + (spamProbability * featureSpamProbability) );
					}
				}
			}
			originalAsnwer.add((int) testData[i][testData[i].length-1]);
			//System.out.println(spam+"        "+nonSpam);
			if(spam/spam+nonSpam >0.8) {
				testAsnwer.add(1);
				//System.out.println(testData[i][testData[i].length-1] +"     "+1);
			}
			else {
				testAsnwer.add(0);
				//System.out.println(testData[i][testData[i].length-1] +"     "+0);
			}
		}
		
	}

	
}
