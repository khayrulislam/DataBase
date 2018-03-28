package KNNExample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SeparateTraningAndTestData {
	private String spliteCharacter;
	private String [][] allDataIn2dArray;
	private String [][] testDataIn2dArray;
	private String [][] traningDataIn2dArray;
	private int fileSize,testSize,traningSize;
	
	ArrayList<String> allInputList = new ArrayList<String>(); 
	HashMap<Integer, Boolean> testDataPositonMap = new HashMap<Integer,Boolean>();
	HashMap<Integer, Boolean> alltestDataPositonMap = new HashMap<Integer,Boolean>();
	
	public SeparateTraningAndTestData(String path) throws IOException {

		fatchInputFile(path);
		spliteCharacter = getTheSplitingChatater(allInputList.get(0));
		calculateTestAndTraningDataSize();
		storeAllInputDataIn2dArray();
		
	}
	
	private void fatchInputFile(String path) throws IOException {
		String line;
		BufferedReader br = new BufferedReader( new FileReader(new File(path)) );
		while( ( line = br.readLine() ) != null ){ allInputList.add(line);	}
		br.close();
	}
	
	private String getTheSplitingChatater(String line) {
		String [] splitingCharArray = { ",",";"," "};
		for(int i=0;i<splitingCharArray.length;i++) if(line.contains(splitingCharArray[i])) return  splitingCharArray[i];
		return null;
	}
	
	private void calculateTestAndTraningDataSize() {
		fileSize = allInputList.size();
		testSize = (int) (fileSize * 0.1);
		traningSize = fileSize - testSize;
	}
	
	private String[][] initialized2dArray(int size){
		String [][] temp = new String [size][];
		return temp;
	}
	
	
	private void storeAllInputDataIn2dArray() {
		allDataIn2dArray = initialized2dArray(fileSize);
		for(int i=0;i<allInputList.size();i++)
			allDataIn2dArray[i] = allInputList.get(i).split(spliteCharacter);
	}
	
	
	private void selectTestDataRandomly() {
		Random rand = new Random();
		int number,i=0;
		testDataIn2dArray = initialized2dArray(testSize);
		testDataPositonMap.clear();
		while(i<testSize) {
			number = rand.nextInt( fileSize );
			if(!alltestDataPositonMap.containsKey(number)) {
				testDataIn2dArray[i++] = allDataIn2dArray[number];
				testDataPositonMap.put(number, true);
				alltestDataPositonMap.put(number, true);
			}
		}
		
	}
	
	public String[][] getTestData() {
		selectTestDataRandomly();
		return testDataIn2dArray;
	}
	
	public String[][] getTraningData() {
		int i=0;
		traningDataIn2dArray = initialized2dArray(traningSize);
		for(int j=0;j<allDataIn2dArray.length;j++) 
			if(!testDataPositonMap.containsKey(j)) traningDataIn2dArray[i++] = allDataIn2dArray[j];	
		return traningDataIn2dArray;
	}
	
}
