package src;

import java.util.concurrent.Callable;
import com.google.gson.Gson;


public class MultiThread implements Callable<String>{

	static Gson gson = new Gson();
	private String input;
	
	public MultiThread(String input) {
		this.input = input;
	}
	
	
	public String call(){
		try {
			String result = getProcessingResult(input);
			return result;
		} catch(Exception e2) {
			e2.printStackTrace();
		}	
		return null;
	}
	
	public String getProcessingResult(String s){
		return s+"\t"+String.valueOf(Integer.parseInt(s) + 1) + "\t" + "processed";
	}

}