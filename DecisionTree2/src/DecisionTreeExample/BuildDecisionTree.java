package DecisionTreeExample;

import java.util.ArrayList;
import java.util.HashMap;

public class BuildDecisionTree {

	
	private String [][] traningData;
	private TreeNode root= new TreeNode();
	
	public BuildDecisionTree(String [][] traningData) {
		this.traningData=traningData;
		initialize();
	}
	
	public void initialize() {
		ArrayList< ArrayList< String > > columnValueList = seperateValueByColumn(traningData);
		ArrayList< HashMap<String, Boolean> > uniqueValueOfColumn = seperateUniqueValueFromCloumn(columnValueList);
		//calculateEntropy(columnValueList,uniqueValueOfColumn);
		TreeCreation(root,columnValueList, uniqueValueOfColumn) ;
	}
	
	
	private ArrayList< HashMap<String, Boolean> > seperateUniqueValueFromCloumn( ArrayList< ArrayList< String > > columnValueList) {
		
		ArrayList< HashMap<String, Boolean> > uniqueValue = new ArrayList< HashMap<String, Boolean>  >();
		
		for(int i=0;i<columnValueList.size()-1;i++) {
			HashMap<String, Boolean> uvc = new HashMap<String,Boolean>();
			ArrayList<String> temp = columnValueList.get(i);
			for(int j=0;j<temp.size();j++)
				uvc.put(temp.get(j), true);
			//System.out.println(uvc.toString());
			uniqueValue.add(uvc);
		}
		return uniqueValue;
	}
	
	private ArrayList< ArrayList< String > > seperateValueByColumn(String [][] inputTraningData) {
		
		ArrayList< ArrayList< String > > columnValue = new ArrayList< ArrayList< String > >();

		for(int i=1;i<inputTraningData[1].length;i++) {
			ArrayList< String > singleColumn = new ArrayList< String >();
			for(int j=1;j<inputTraningData.length;j++)
				singleColumn.add(inputTraningData[j][i]);
			//System.out.println(singleColumn.toString());
			columnValue.add(singleColumn);
		}
		return columnValue;
	}
	
	private HashMap<String, Integer>  getUniqueValue(HashMap<String, Boolean> uvc){
		HashMap<String, Integer>  temp = new HashMap<String, Integer>();
		int i=0;
		for(String key: uvc.keySet())
			temp.put(key, i++);
		return temp;
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
	
	
	private double calculateEntropyForAcolumn(ArrayList<String> answer, ArrayList<String> column , HashMap<String, Boolean> uvc, double totalYesCount, double totalNoCount ) {
		
		HashMap<String, Integer>  uniqueValue = getUniqueValue(uvc);
		
		double [][] yesNoCount = new double [uniqueValue.size()][3];
		int index;
		double entropy=0;
		
		for(int i=0;i<column.size();i++) {
			index = uniqueValue.get(column.get(i));
			if (answer.get(i).equals("yes")) yesNoCount[index][0]++;
			else if (answer.get(i).equals("no")) yesNoCount[index][1]++;
		}
		
		for(int i=0;i<uniqueValue.size();i++) {
			yesNoCount[i][2] =  calculateInformationGain(yesNoCount[i][0],yesNoCount[i][1]);
			entropy += (  ( yesNoCount[i][0] + yesNoCount[i][1] ) * yesNoCount[i][2]  ) /  (totalNoCount+totalYesCount);
		}
		
		return entropy;
	}
	
	
	
	private int calculateEntropy(ArrayList< ArrayList< String > > columnValueList,ArrayList< HashMap<String, Boolean> > uniqueValueOfColumn) {
		int index = -1;
		double totalNoCount=0,totalYesCount=0,min=1,entropy = 0;
		ArrayList<String> answerList = columnValueList.get(columnValueList.size()-1);
		for(int i=0;i<answerList.size();i++) {
			if(answerList.get(i).equals("yes")) totalYesCount++;
			else if (answerList.get(i).equals("no")) totalNoCount++;
		}
		if(totalYesCount==0 || totalNoCount==0)
			return -1;		
		for(int i=0;i<columnValueList.size()-1;i++) {
			entropy =  calculateEntropyForAcolumn(answerList, columnValueList.get(i), uniqueValueOfColumn.get(i) ,totalYesCount,totalNoCount ) ;
			if(entropy<min) {
				min = entropy;
				index = i;
			}			
		}
		return index;	
	}
	
	
	private TreeNode TreeCreation(TreeNode currentNode, ArrayList< ArrayList< String > > columnValueList, ArrayList< HashMap<String, Boolean> > uniqueValueOfColumn) {
		
		int splitIndex = calculateEntropy(columnValueList,uniqueValueOfColumn);
		
		if(columnValueList.isEmpty())
			return null; 
		if(splitIndex==-1) {
			currentNode.isLeaf = true;
			currentNode.answer = columnValueList.get(columnValueList.size()-1).get(0);
			//System.out.println(currentNode.answer);
			return currentNode;
		}
		
		else {
			currentNode.index = splitIndex;
			
			HashMap<String, Boolean> splitingUnique = uniqueValueOfColumn.get(splitIndex);
			//System.out.println(splitingUnique.toString());
			
			for(String key : splitingUnique.keySet()) {
				//System.out.println(splitIndex);
				//System.out.println(key+">>>>>>>>>>>>>>>>>>");
				TreeNode next = new TreeNode();
				ArrayList< ArrayList< String > > nextColumnList = getNextColumnList(splitIndex,key,columnValueList);
				ArrayList< HashMap<String, Boolean> > nextUniqueValueOfColumn = seperateUniqueValueFromCloumn(nextColumnList);
				
				TreeNode temp = null;
				temp=TreeCreation(next, nextColumnList, nextUniqueValueOfColumn);
				//System.out.println(key+"========"+temp.answer+"--------"+temp.child.size());
				currentNode.child.put(key, temp);
				
			}
			 
		}
		
		return currentNode;
	}
	
	

	
	private ArrayList< ArrayList< String > >  getNextColumnList(int splitIndex,String key, ArrayList< ArrayList< String > > columnValueList){
		
		ArrayList< String > removingColumn = columnValueList.get(splitIndex);
		HashMap<Integer, Boolean> index = new HashMap<Integer, Boolean>();
		ArrayList< ArrayList< String > > newColumnValueList = new  ArrayList< ArrayList< String > >();
		
		for(int i=0;i<removingColumn.size();i++)
			if(removingColumn.get(i).equals(key)) {
				index.put(i,true);
				
			}
				
		for(int i=0;i<columnValueList.size();i++) {
			ArrayList<String> temp = new ArrayList<String>();
			ArrayList<String> temp2 = columnValueList.get(i); 
			
			if(i!=splitIndex) {			
				for(int j=0;j<temp2.size();j++) {
					Integer I = new Integer(j);
					if(index.get(I)!=null)
						temp.add(temp2.get(j));
				}
					
				//System.out.println(temp);
				newColumnValueList.add(temp);
			}
			
		}		
		return newColumnValueList;
	}
	
	public TreeNode getRoot() {
		return root;
	}
	
	
}
