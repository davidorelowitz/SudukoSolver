
import java.util.*;

public class Output {

	LinkedList <String> outputList = new LinkedList<String>();

	void add(String line){
		//if(outputList.size() > 30)
			//outputList.removeFirst();
		outputList.add(line);
	}

	void clear(){
		outputList.clear();
	}

	void display(){
		for(String str : outputList)
			System.out.print(str + "\n");
	}
}
