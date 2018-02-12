package SpamDetectionExample;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Train {
	 double [][]trainData = new double[4602][59];
	private String line;
	private int i,totalSpam=0,totalNonSpam=0;
	private double spamProbability,nonSpamProbablity;
	
	private double []splitRowData(String line) {
		String [] row= new String[58];
		double [] rowData = new double[59];
		row = line.split(",");
		for(int i=0;i<58;i++) {
			rowData[i+1] = Double.parseDouble(row[i]);
		}
		return rowData;
	}
	
	public void readTrainingData(String path,HashMap<Integer, Boolean> test) throws FileNotFoundException {
		File file = new File(path);
		Scanner sc = new Scanner(file);
		i = 1;		
		while(sc.hasNext()) {
			line = sc.next();
			if(!test.containsKey(i)) {
				trainData[i++] =  splitRowData(line);
				if(trainData[i-1][58]==1) totalSpam++;
				else totalNonSpam++;
			}
		}		
		sc.close();
	}
	
	public double getSpamProbablity() {
		spamProbability = (double) totalSpam/ (double) i-1;
		return spamProbability;
	}
	
	public double getNonSpamProbability() {
		nonSpamProbablity = (double) totalNonSpam / (double) i-1;
		return nonSpamProbablity;
	}

	public int probabilityWithinARange(int emailtype,int pos,double frequency){
		int occurance=0;
		double start,end;
		start = frequency - frequency*0.05;
		end = frequency + frequency*0.05;	
		for(int i=1;i<4602;i++) {
			if(trainData[i][58]==emailtype && trainData[i][pos] > start && trainData[i][pos]<=end && frequency!=0.0) {
				occurance ++;
			}
		}	
		return occurance;
	}
	
	public int probabilityForWord(int emailType,int pos,double frequency) {
		int occurance = 0;
		for(int i=1;i<4602;i++) {
			if(trainData[i][58]==emailType && trainData[i][pos]==frequency && frequency!=0.0) {
				occurance ++;
			}
		}
		if(occurance == 0) occurance = probabilityWithinARange(1,pos,frequency);
		return occurance;
	}
	
}
