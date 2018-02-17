package DecisionTreeExample;


import java.io.IOException;

public class Controller {

	private String [][] testData;
	private String [][] traningData;
	
	public void getInputData(String path) throws IOException {
		SeparateTraningAndTestData s = new SeparateTraningAndTestData(path);
		testData = s.getTestData();
		traningData = s.getTraningData();
		new CreateTree(traningData,s.gettotalYes(),s.gettotalNo());
		
	}
	
	
	
	
	
}
