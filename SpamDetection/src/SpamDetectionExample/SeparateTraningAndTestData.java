package SpamDetectionExample;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class SeparateTraningAndTestData {
	
	private String line;
	private double [][] totalData;
	private double [][] testData;
	private double [][] traningData;
	private int i,totalFileSize,testSize,traningSize,number,totalSpam=0,totalNonSpam=0;
	

	ArrayList<String> totalDataString = new ArrayList<String>(); 
	HashMap<Integer, Boolean> testDataPositonMap = new HashMap<Integer,Boolean>();
	HashMap<Integer, Boolean> alltestDataPositonMap = new HashMap<Integer,Boolean>();
	
	public SeparateTraningAndTestData(String path) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(path));
		while(sc.hasNext()){
			line = sc.next();
			totalDataString.add(line);
			i++;
		}
		totalFileSize = i;
		testSize = (int) (totalFileSize * 0.1);
		traningSize = totalFileSize - testSize;
		storeInArray();
		sc.close();
	}
	
	private void storeInArray() {
		totalData = new double[totalFileSize+1][];
		for(int i=0;i<totalDataString.size();i++)
			totalData[i+1] = splitRowData( totalDataString.get(i));
	}
	
	private double []splitRowData(String line) {
		String [] row = line.split(",");
		double [] rowData = new double[row.length+1];
		for(int i=0;i<row.length;i++) {
			rowData[i+1] = Double.parseDouble(row[i]);
		}
		return rowData;
	}
	
	
	public void selectTestDataRandomly(int low,int high) {
		Random rand = new Random();
		i=1;
		testData = new double [testSize+1][];
		testDataPositonMap.clear();
		while(i<=testSize) {
			number = rand.nextInt(high-low)+low+1;
			if(!alltestDataPositonMap.containsKey(number)) {
				testData[i++] = totalData[number];
				testDataPositonMap.put(number, true);
				alltestDataPositonMap.put(number, true);
			}
		}
		
	}
	
	public double[][] getTestData() {
		selectTestDataRandomly(0,totalFileSize);
		return testData;
	}
	
	public double[][] getTraningData() {
		i=1;
		traningData = new double [traningSize+1][];
		for(int j=1;j<totalData.length;j++) {
			if(testDataPositonMap.containsKey(j)) {}
			else {
				traningData[i++] = totalData[j];
				if(totalData[j][totalData[j].length-1]==1) totalSpam++;
				else totalNonSpam++;
			}
		}			
		return traningData;
	}

	public double getSpamProbability() {
		return (double) totalSpam/ (double) traningSize;
	}

	public double getNonSpamPobablility() {
		return (double)totalNonSpam/ (double)traningSize;
	}
	public double getTotalSpam() {
		return (double)totalSpam;
	}

	public double getTotalNonSpam() {
		return (double) totalNonSpam;
	}
	
}
