package DecisionTreeExample;


import java.io.IOException;

public class Controller {

	private String [][] testData;
	private String [][] traningData;
	
	public void getInputData(String path) throws IOException {
		SeparateTraningAndTestData s = new SeparateTraningAndTestData(path);
		testData = s.getTestData();
		traningData = s.getTraningData();
		//new CreateTree(traningData,s.gettotalYes(),s.gettotalNo());
		BuildDecisionTree bt = new BuildDecisionTree(traningData);
		TreeNode root = bt.getRoot();
		
		//print(root);
	}
	
	public void print(TreeNode root) {
		
	//System.out.println(root.child.toString());
		
		if(!root.child.isEmpty())
			for(String key: root.child.keySet()) {
				System.out.println(key);
				print(root.child.get(key));
			}
				
		
	}
	
	
	
	
	
}
