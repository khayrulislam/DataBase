package DecisionTreeExample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

public class CreateTree {

	private String [][] traningData;
	private String [][] currentData;
	private double currentInfomationGain;
	public TreeNode root;
	double totalNoCount,totalYesCount;
	
	//HashSet<String> unique = new HashSet<String>();
	private ArrayList< HashMap<String, Integer> > uniqueAttributeList = new ArrayList< HashMap<String, Integer>  >();
	
	public CreateTree(String [][]currentData,int yesCount, int noCount ) {
		this.traningData = currentData;
		this.totalYesCount = (double) yesCount;
		this.totalNoCount = (double) noCount;
		this.currentData = traningData.clone();
		this.currentInfomationGain = calculateInformationGain(totalYesCount,totalNoCount);
		System.out.println(currentInfomationGain);
		spliteUniqueAttributeOfAllColumn();
		calculateEntropy();
	}
	
	private double log2(double x) {
		return Math.log(x) / Math.log(2.0d);
	}
	
	public double calculateInformationGain(double yesCount,double noCount) {
		double x,y;
		if(yesCount==0 || noCount==0) return 0;
		x =  ( yesCount / (yesCount + noCount ) ) ;
		y =   ( noCount / (yesCount + noCount ) ) ;	
		return - x * ( log2(yesCount) - log2 (yesCount + noCount ) ) -  y *  ( log2(noCount ) - log2(yesCount + noCount ) );		
	}
	
	
	
	private double calculateEntropyForAColumn(int col,HashMap<String, Integer> unique) {
		
		double [][] yesNoCount = new double [unique.size()][4];
		double entropy = 0;

		for(int j=1;j<currentData.length;j++) {
			int index = unique.get(currentData[j][col]);			
			if( currentData[j][currentData[j].length-1].equals("yes") ) yesNoCount[index][1]++;
			else yesNoCount[index][2]++;
		}
		
		for(int i=0;i<unique.size();i++) {
			yesNoCount[i][3] =  calculateInformationGain(yesNoCount[i][1],yesNoCount[i][2]);
			entropy += (  ( yesNoCount[i][1] + yesNoCount[i][2] ) * yesNoCount[i][3]  ) /  (totalNoCount+totalYesCount);
		}
		return entropy;
	}
	
	
	private void calculateEntropy() {
		double entropy;
		TreeMap< Double, Integer> entropyMaping =new TreeMap<Double,Integer>();
		for(int i=0;i<uniqueAttributeList.size();i++) {
			entropy = calculateEntropyForAColumn(i+1,uniqueAttributeList.get(i));
			entropyMaping.put(entropy, i);
		}
	}
	
	
	
	
	
	
	private void spliteUniqueAttributeOfAllColumn() {	
		uniqueAttributeList.clear();
		System.out.println(currentData[1].length);
		for(int i=1;i<currentData[1].length-1;i++) {
			HashMap<String, Integer> unique = new HashMap<String,Integer>();
			int k=0;
			for(int j=1;j<currentData.length;j++)
				if(!unique.containsKey(currentData[j][i])) 
					unique.put(currentData[j][i], k++);
			
			uniqueAttributeList.add(unique);
		}
	}
	
	

	
	public void start() {
		
		
		
	}
	
	
}
