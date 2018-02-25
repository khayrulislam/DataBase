package DecisionTreeExample;


import java.io.IOException;
import java.util.ArrayList;


public class Controller {

	private String [][] testData;
	private String [][] traningData;
	ArrayList< ArrayList< String > > modifiedTestData = new ArrayList< ArrayList< String >  >();
	
	public void getInputData(String path) throws IOException {
		SeparateTraningAndTestData s = new SeparateTraningAndTestData(path);
		testData = s.getTestData();
		traningData = s.getTraningData();
		
		BuildDecisionTree bt = new BuildDecisionTree(traningData);
		TreeNode root = bt.getRoot();
		/*modifyTestData();
			
		for(int i=0;i<modifiedTestData.size();i++) {
			System.out.println("orginal answer    "+testData[i+1][testData[i+1].length-1]);
			System.out.println("my answer    "+traversTree(root, modifiedTestData.get(i) ));
		}*/
		//print(root);
		
		printTree(root);
		
	}
	
	
	public void modifyTestData() {
		System.out.println(testData.length);
		for(int i=1;i<testData.length;i++) {
			ArrayList<String> temp = new ArrayList<String>();
			for(int j=1;j<testData[i].length-1;j++)
				temp.add(testData[i][j]);
			//System.out.println(testData[i][testData[i].length-1]);
			
			modifiedTestData.add(temp);
		}
		//System.out.println(modifiedTestData.toString());
	}
	
	public void printTree(TreeNode current) {
		
		if(current.child.size()>0) {
			
			for(String key : current.child.keySet())
				System.out.print(key+"    ");
			System.out.println();
			for(String key : current.child.keySet()) {
				System.out.println(key+"***************"+current.index+"-------->>>>>>>>>"+current.answer);printTree(current.child.get(key));
			}			
		}
		
		if(current.child.size()==0)
			System.out.println("ans========="+current.answer+"------"+current.index);
		
	}
	

	public String traversTree(TreeNode current , ArrayList<String> test) {
				
		
		//System.out.println(current.index+"   "+current.child.size()+"       "+current.child.toString()+"      "+test.size());
		int index = current.index;	
		String str = test.get(index);
		test.remove(str);
		//System.out.println(test.toString());
		
		if(current.child.size()==0) return current.answer;
		if(current.child.get(str)==null) {
			System.out.println("                                                             "+current.answer);
			return current.answer;}
		return traversTree(current.child.get(str), test);
		
	}
	

	
	
	
}
