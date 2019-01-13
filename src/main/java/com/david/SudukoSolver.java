package com.david;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.DataInputStream;
import java.io.FileInputStream;


public class SudukoSolver {

    private Grid grid = new Grid();

    public static void main(String[] args) {
        SudukoSolver processor = new SudukoSolver();
        processor.process();
    }

    private void process(){
        String curLine; // = ""; // Line read from standard in

        InputStreamReader converter = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(converter);
        String command;
        String parameters;
        int commandEnd;

        for(;;){
            try {
                System.out.print("\n");
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
                    System.out.println("(h)elp");
                    System.out.println("(l)oad filename");
                    System.out.println("(s)et val r:c, val2 r2:c2, ...");
                    System.out.println("(r)ow r:456 9  12");
                    System.out.println("(c)lear");
                    System.out.println("(d)ump");
                    System.out.println("(q)uit");
                }

                else if (command.equals("s") || command.equals("set")){
                    if (processSetCommand(parameters)) {
                        grid.display();
                    }
                }

                else if (command.equals("c") || command.equals("clear")){
                    if (processClearCommand()) {
                        grid.display();
                    }
                }

                else if (command.equals("l") || command.equals("load")){
                    if (processLoadCommand(parameters)) {
                        grid.display();
                    }
                }

                else if (command.equals("r") || command.equals("row")){
                    if (processRowCommand(parameters)) {
                        grid.display();
                    }
                }

                else if (command.equals("d") || command.equals("dump")){
                    grid.dump();
                }

                else if (command.equals("")) {
                    grid.display();
                    System.out.println(grid.dumpAsString());
                }

                else if (command.equals("q") || command.equals("quit")){
                    System.out.println("Good Bye");
                    return;
                }

                else
                    System.out.println("Unknown command \"" + command + "\"");
            }
            catch (Exception e){
                System.out.println("Some other error");
            }
        }
    }

    private boolean processSetCommand(String params){

        try {
            String [] paramList = params.split(",");

            for (String param : paramList){
                param = param.trim();
                String [] vals = param.split("[ :]");
                int val = Integer.parseInt(vals[0]);
                int row = Integer.parseInt(vals[1]);
                int col = Integer.parseInt(vals[2]);

                grid.setVal(val, new Position(row, col), null);
            }
        }
        catch (Exception e){
            System.out.println("Error setting value");
            return(false);
        }
        return(true);
    }

    private boolean processLoadCommand(String params){

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
            return(false);
        }
        return(true);
    }

    private boolean processRowCommand(String args){

        System.out.println(args);

        try {

            StringBuilder str = new StringBuilder(args);

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
            return(false);
        }

        return(true);
    }

    private boolean processClearCommand(){
        grid = new Grid();
        return(true);
    }
}
