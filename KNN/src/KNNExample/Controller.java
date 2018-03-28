package KNNExample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class Controller {

	
	private String [][] testData;
	private String [][] traningData;
	
	private SeparateTraningAndTestData statd;
	private int nearest;
	
	public void getInput(String path) throws IOException {
		statd = new SeparateTraningAndTestData("data.txt");
		
		for(int i=0;i<10;i++) {
			getTestAndTraningData();
			nearest = getTheNearestNumber();
			testAndCompareResult();
		}
	}
	
	private void testAndCompareResult() {
		
		ArrayList<String> orginalanswer = new ArrayList<String>();
		ArrayList<String> testingAnswer = new ArrayList<String>();
		for(int i=0;i<testData.length;i++) {
			orginalanswer.add(  testData[i][testData[i].length-1]   );
			testingAnswer.add(  getResultTheClassOfTheTestData(testData[i] )  );			
		}
		new ResultEvaluation(testingAnswer, orginalanswer);
	}
	
	
	private double claculateDistance(double x1, double x2) {
		return (x1-x2) * (x1-x2);
	}
	
	private ArrayList<Distance> getDisanceList(String[] testData2) {
		ArrayList<Distance> distanceList = new ArrayList<Distance>(); 
		for(int i=0;i<traningData.length;i++) {
			double distance = 0;
			for(int j=0;j<traningData[i].length-1;j++) 
				distance += claculateDistance(Double.parseDouble(testData2[j]), Double.parseDouble(traningData[i][j])  );	
			distanceList.add(new Distance( Math.sqrt( distance ), traningData[i][traningData[i].length-1]));
			
		}
		return distanceList;
	}
	
	private String getResultTheClassOfTheTestData(String[] testData2){
		
		HashMap<String, Integer> answer = new HashMap<String,Integer>();
		ArrayList<Distance> distanceList2 = getDisanceList( testData2);
		Collections.sort(distanceList2);
		int max=-1;
		String finalAnswer = null;
		
		for(int i=0;i<nearest;i++) {
			if(answer.containsKey( distanceList2.get(i).answerClass )) answer.put( distanceList2.get(i).answerClass , 1+answer.get(distanceList2.get(i).answerClass));
			else answer.put(  distanceList2.get(i).answerClass , 1);
		}
		
		for(String key : answer.keySet()) {
			if(answer.get(key) > max) {
				max = answer.get(key);
				finalAnswer = key;
			}
		}
		return finalAnswer;
	}
	
	
	
	private void getTestAndTraningData() {
		testData = statd.getTestData();
		traningData = statd.getTraningData();
	}
	
	private int getTheNumberOfClass() {
		HashSet<String> classSet = new HashSet<String>();
		for(int i=0;i<traningData.length;i++) classSet.add(traningData[i][traningData[i].length-1]);
		return classSet.size();
	}
	
	private int getTheNearestNumber() {
		int numberOfClass = getTheNumberOfClass();
		Random rand = new Random();
		int nearestNumber = rand.nextInt(numberOfClass*3 - 1 ) + 1;
		return nearestNumber*numberOfClass + 1 ;
	}
	
	
	
}
