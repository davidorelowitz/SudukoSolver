
import java.util.*;

public class Output {

	static LinkedList <String> outputList = new LinkedList<String>();
	
	static void add(String line){
		//if(outputList.size() > 30)
			//outputList.removeFirst();
		outputList.add(line);
	}
	
	static void clear(){
		outputList.clear();
	}
	
	static void display(){	
		for(String str : outputList)
			System.out.print(str + "\n");
	}
	
}
