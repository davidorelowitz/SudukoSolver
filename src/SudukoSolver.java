import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.DataInputStream;
import java.io.FileInputStream;


public class SudukoSolver {

	static Output output = new Output();
	static Grid grid = new Grid();
	
	public static void main(String[] args) {
			
		String curLine; // = ""; // Line read from standard in

		InputStreamReader converter = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(converter);
		String command;
		String parameters;
		int commandEnd;
		
		for(;;){
			try {
				grid.display();	
				System.out.print("\n");
				Output.display();
				Output.clear();
				System.out.print(">");
				
				curLine = in.readLine();
				
				commandEnd = curLine.indexOf(" ");
				
				if(commandEnd == -1){
					command = curLine.toLowerCase();
					parameters = "";
				}
				else{
					command = curLine.substring(0, commandEnd).toLowerCase();
					parameters = curLine.substring(commandEnd).trim();
				}
				
				if (command.equals("h") || command.equals("help")){
					Output.add("(h)elp");
					Output.add("(l)oad filename");
					Output.add("(s)et val r:c, val2 r2:c2, ...");
					Output.add("(r)ow r:456 9  12");
					Output.add("(c)lear");
					Output.add("(d)ump");
					Output.add("(q)uit");
					continue;
				}

				else if (command.equals("s") || command.equals("set")){
					processSetCommand(parameters);
				}

				else if (command.equals("c") || command.equals("clear")){
					processClearCommand(parameters);
				}
				
				else if (command.equals("l") || command.equals("load")){
					processLoadCommand(parameters);
				}

				else if (command.equals("r") || command.equals("row")){
					processRowCommand(parameters);
				}

				else if (command.equals("d") || command.equals("dump")){
					grid.dump();
				}

				else if (command.equals(""))
					;
				
				else if (command.equals("q") || command.equals("quit")){
					System.out.println("Good Bye");
					return;					
				}
				
				else
					Output.add("Unknown command \"" + command + "\"");
			}
			catch (Exception e){
				Output.add("Some other error");
			}
		}
	}

	static void processSetCommand(String params){
		
		try {
			String [] paramList = params.split(",");
		
			for (String param : paramList){
				param = param.trim();
				String [] vals = param.split("[ :]");
				Integer val = new Integer(vals[0]);
				Integer row = new Integer(vals[1]);
				Integer col = new Integer(vals[2]);
				
				grid.setVal(val, new Position(row, col), null);
			}
		}
		catch (Exception e){
			Output.add("Error setting value");
		}
	}

	static void processLoadCommand(String params){
		
		try {
		
			FileInputStream fstream = new FileInputStream(params);
		    // Get the object of DataInputStream
		    DataInputStream in = new DataInputStream(fstream);
		    BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    String strLine;
		    
		    //Read File Line By Line
		    int row = 1;
		    while ((strLine = br.readLine()) != null)   {
		    	System.out.println (strLine);

		    	char [] buff = strLine.toCharArray();
			    int		len = buff.length;
		    	
			    for (int col = 1; col <= len; col++){
			    	char ch = buff[col - 1];
			    	int val = Character.digit(ch, 10);
			    	if (val >= 1 && val <= 9){
			    		grid.setVal(val, new Position(row, col), null);
			    	}
			    }
			    row++;
		    }
	    in.close();
		}
	    catch (Exception e){
	        System.err.println("Error: " + e.getMessage());
		}
	}

	static void processRowCommand(String args){

		System.out.println(args);

		try {

			StringBuffer str = new StringBuffer(args);

			if (str.length() < 3 || str.length() > 11 || str.charAt(1) != ':') {
				throw new Exception("Command should be in format r(ow) x:xy..");
			}

			int len = str.length() - 2;

			int row = Character.digit(str.charAt(0), 10);

			for (int col = 1; col <= len; col++) {
				char ch = str.charAt(col + 1);
				if (ch >= '0' && ch <= '9') {
					int val = Character.digit(ch, 10);
					grid.setVal(val, new Position(row, col), null);
				}
			}
		}
		catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}


	}

	static void processClearCommand(String param){
		grid = new Grid();
	}
}
