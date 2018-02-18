package DecisionTreeExample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;

public class CreateTree {

	private String [][] traningData;
	private String [][] currentData;
	private double currentInfomationGain,entropy;
	public TreeNode root;
	double totalNoCount,totalYesCount;
	String answer;
	
	//HashSet<String> unique = new HashSet<String>();
	private ArrayList< HashMap<String, Integer> > uniqueAttributeList = new ArrayList< HashMap<String, Integer>  >();
	private HashMap<String, Integer> indexMap = new HashMap<String,Integer>();
	
	
	public CreateTree(String [][]currentData,int yesCount, int noCount ) {
		this.traningData = currentData;
		this.totalYesCount = (double) yesCount;
		this.totalNoCount = (double) noCount;
		this.currentData = traningData.clone();
		indexMaping();
		//this.currentInfomationGain = calculateInformationGain(totalYesCount,totalNoCount);
		//System.out.println(currentInfomationGain);
		treeBuild();
		//spliteUniqueAttributeOfAllColumn();
		//calculateEntropy();
	}
	
	public void indexMaping() {
		for(int i=1;i<currentData[1].length-1;i++) 
			for(int j=1;j<currentData.length;j++)		
					indexMap.put(currentData[j][i],i);	
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
	
	/*public void createRoot() {
		root.nodeData = currentData;
		
	}*/
	private void entropyZeroCondition(double [][] yesNoCount ){
		for(int i=0;i<yesNoCount.length;i++) {
			if( yesNoCount[i][1] >0 ) answer = "yes";
			else if (yesNoCount[i][2] >0) answer = "no";
		}
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
		if(entropy==0.0) entropyZeroCondition(yesNoCount);
		return entropy;
	}
	
	
	private HashMap<String, Integer> calculateEntropy() {
		double entropy,min=1;
		HashMap<String, Integer> minEntropy = null; 
		for(int i=0;i<uniqueAttributeList.size();i++) {
			HashMap<String, Integer> unique = uniqueAttributeList.get(i);
			entropy = calculateEntropyForAColumn( getIndex(unique),unique);			
			if(entropy<min) {
				min = entropy;
				minEntropy = unique;
			}
			System.out.println(entropy);
			min = Math.min(min, entropy);
		}	
		return minEntropy;
	}
	
	private int getIndex(HashMap<String, Integer> unique) {
		int ind;
		String keey = null;
		 for(String key : unique.keySet()) {
		 	keey = key;
		 	break;
		 }		 
		 ind = indexMap.get(keey);	
		return ind;
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
	
	

	
	public void treeBuild() {
		answer = null;
		currentInfomationGain = calculateInformationGain(totalYesCount,totalNoCount);
		spliteUniqueAttributeOfAllColumn();
		//calculateEntropy();
		TreeNode  x=createNode(calculateEntropy());
		System.out.println(x.child.size());
		
	}
	
	private TreeNode createNode( HashMap<String, Integer> temp) {
		
		HashMap<String, Integer> child = (HashMap<String, Integer>) temp.clone(); 
		HashMap<String,TreeNode> ch = new HashMap <String,TreeNode>();
		 String keey = null;
		 for(String key : child.keySet()) {
		 	keey = key;
			 ch.put(key, null);
		 	//break;
		 }	
		 
		 int index = indexMap.get(keey);
		 TreeNode tn = new TreeNode(index, ch, answer);
		 return tn;
	}
	
	
}




/*String keey = null;
for(String key : unique.keySet()) {
	keey = key;
	break;
}*/

///entropyMaping.put(entropy, indexMap.get(keey));