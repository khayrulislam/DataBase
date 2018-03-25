package ApriorExample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class InputGenerator {
	
	public ArrayList< ArrayList<String> >  getInput(String path) throws IOException {
		
		String line;
		ArrayList< ArrayList<String> > inputList = new ArrayList< ArrayList<String> >();		
		
		BufferedReader br = new BufferedReader( new FileReader(new File(path)) );
		while( ( line = br.readLine() ) != null ){ 
			inputList.add(seperateLineToArraylist( line) );	
		}
		br.close();
		return inputList;
	}
	
	
	private ArrayList<String> seperateLineToArraylist(String line){
		return new ArrayList<String>(Arrays.asList( line.split(",") ) );
	}
	
	
}
