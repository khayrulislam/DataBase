package DecisionTreeExample;

import java.util.HashMap;

public class TreeNode {
	int index;
	HashMap<String,TreeNode> child = new HashMap <String,TreeNode>();
	//boolean isLeaf;
	String answer;
	public TreeNode(int index, HashMap<String, TreeNode> child, String answer) {
		super();
		this.index = index;
		this.child = child;
		this.answer = answer;
	}
	
}
