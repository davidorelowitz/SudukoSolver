package com.david;

import java.util.LinkedList;

public class Grid {

    private Node[][] grid;
    private int numSet;
    private int indent;

    public Grid() {
        grid = new Node[9][9];
        for (int row = 1; row <= 9; row++) {
            for (int col = 1; col <= 9; col++) {
                grid[row - 1][col - 1] = new Node(new Position(row, col));
            }
        }
    }

    boolean setVal(int val, Position location, String reason) {

        try {
            // Calculating the comment indentation
            StringBuilder indentBuffer = new StringBuilder();
            int tempIndent = indent;
            while(tempIndent > 0){
                indentBuffer.append("-");
                tempIndent--;
            }

            if(val < 1 || val > 9){
                System.out.println(indentBuffer.toString() + "Invalid value " + val);
                return(false);
            }

            if(location.getRow() < 1 || location.getRow() > 9){
                System.out.println(indentBuffer.toString() + "Invalid row " + location.getRow());
                return(false);
            }

            if(location.getColumn() < 1 || location.getColumn() > 9){
                System.out.println(indentBuffer.toString() + "Invalid column " + location.getColumn());
                return(false);
            }

            Node nodeToSet = grid[location.getRow() - 1][location.getColumn() - 1];

            if(nodeToSet.getValue() == val){
                System.out.println(indentBuffer.toString() + location.getRow() + ":" + location.getColumn() + " already set to this value");
                return(true);
            }

            if(nodeToSet.getValue() != 0){
                System.out.println(indentBuffer.toString() + location.getRow() + ":" + location.getColumn() + " already set to a different value");
                return(false);
            }

            // Is value in row
            LinkedList<Node> rowList = getOtherNodesInRow(location);
            for (Node n : rowList){
                if(val == n.getValue()){
                    System.out.println(indentBuffer.toString() + val + " is already in row " + location.getRow());
                    return(false);
                }
            }

            // Is value in column
            LinkedList<Node> columnList = getOtherNodesInColumn(location);
            for (Node n : columnList){
                if(val == n.getValue()){
                    System.out.println(indentBuffer.toString() + val + " is already in column " + location.getColumn());
                    return(false);
                }
            }

            // Is value in block
            LinkedList<Node> blockList = getOtherNodesInBlock(location);
            for (Node n : blockList){
                if(val == n.getValue()){
                    System.out.println(indentBuffer.toString() + val + " is already in block around " + location.toString());
                    return(false);
                }
            }

            // OK - set the Node to value val
            LinkedList<Integer> possibleValues = nodeToSet.setValue(val);
            if(possibleValues == null){
                System.out.println(indentBuffer.toString() + "Error setting value already set at " + location.toString());
                return(false);
            }

            if (reason == null)
                System.out.println(indentBuffer.toString() + "set " + val + " at " + location.toString());
            else
                System.out.println(indentBuffer.toString() + "set " + val + " at " + location.toString() + " by strategy " + reason);

            indent++;
            numSet++;
            if(numSet >= 81){
                System.out.println("!!! ALL DONE - WELL DONE !!!");
                return(true);
            }

//			for (int valToRemove = 1; valToRemove <= 9; valToRemove++){
//
//			}

            Strategy.RemoveValueFromAllAffectedPossibileValueLists(nodeToSet, this);

//			Strategy.UniquePossibleValue(nodeToSet, this);
        }

//		catch (Exception e){
//
//		}


        finally{
            indent--;
        }

        return(true);

    }


    //	public Boolean removeFromPossibleList(int val, Position location, String reason){
//
//
//		return(true);
//	}
//
    public LinkedList<Node> getAllNodesInRow(Position pos){
        return(getNodesInRow(pos, false));
    }

    public LinkedList<Node> getOtherNodesInRow(Position pos){
        return(getNodesInRow(pos, true));
    }

    public LinkedList<Node> getAllNodesInColumn(Position pos){
        return(getNodesInColumn(pos, false));
    }

    public LinkedList<Node> getAllNodesInBlockFromBlockPosition(int blockRowPosition, int blockColumnPosition){

        if (blockRowPosition < 1 || blockRowPosition > 3)
            return(null);

        if(blockColumnPosition < 1 || blockColumnPosition > 3)
            return(null);

//      int row = (blockRowPosition * 3) - 2;
//      int col = (blockColumnPosition * 3) - 2;

        Position pos = new Position(blockRowPosition, blockColumnPosition);

        return(getAllNodesInBlock(pos));
    }

    //public Position getBlock(Position location){
    //
    //	return(new Position(((location.getRow() - 1) / 3) + 1,((location.getColumn() - 1) / 3) + 1));
    //}

    public LinkedList<Node> getOtherNodesInColumn(Position pos){
        return(getNodesInColumn(pos, true));
    }
    public LinkedList<Node> getOtherNodesInBlock(Position pos){
        return(getNodesInBlock(pos, true));
    }
    private LinkedList<Node> getAllNodesInBlock(Position pos){
        return(getNodesInBlock(pos, false));
    }

    private LinkedList<Node> getNodesInRow(Position pos, boolean excludeSelf){

        LinkedList<Node> rowList = new LinkedList<>();
        for(int i = 1; i <= 9; i++) {
            if (excludeSelf && pos.getColumn() == i){
                continue;
            }
            rowList.add(grid[pos.getRow() - 1][i - 1]);
        }
        return(rowList);
    }

    private LinkedList<Node> getNodesInColumn(Position pos, boolean excludeSelf){

        LinkedList<Node> colList = new LinkedList<>();
        for(int i = 1; i <= 9; i++){
            if (excludeSelf && pos.getRow() == i){
                continue;
            }
            colList.add(grid[i - 1][pos.getColumn() - 1]);
        }
        return(colList);
    }

    //public LinkedList<Node> getNodesInBlockFromLocation(Position location){
    //	Position block = getBlock(location);
    //	return(getNodesInBlock(block));
    //}

    private LinkedList<Node> getNodesInBlock(Position pos, boolean excludeSelf){

        int startRow = ((pos.getRow() - 1) / 3) * 3 + 1;
        int endRow = startRow + 2;
        int startColumn = ((pos.getColumn() - 1) / 3) * 3 + 1;
        int endColumn = startColumn + 2;

        LinkedList<Node> nodeList = new LinkedList<>();

        for(int row = startRow; row <= endRow; row++){
            for(int column = startColumn; column <= endColumn; column++) {
                if (excludeSelf && pos.getRow() == row && pos.getColumn() == column){
                    continue;
                }
                nodeList.add(grid[row - 1][column - 1]);
            }
        }
        return(nodeList);
    }


    public void display(){
        for(int i = 0; i < 9; i++){
            if(i == 3 || i == 6)
                System.out.print("-----------------------------\n");
            for(int j = 0; j < 9; j++){
                if(j == 3 || j == 6)
                    System.out.print("|");
                if(grid[i][j] != null)
                    System.out.print(" " + grid[i][j].toString() + " ");
                else
                    System.out.print("   ");
                if(j == 8)
                    System.out.print("\n");
            }
        }
    }

    public void dump(){
        for(int row = 1; row <= 9; row++){
            for(int col = 1; col <= 9; col++){
                Node node = grid[row - 1][col - 1];
                System.out.println(node.getPosition().toString() + " | " + node.toString() + " | " + node.possibleValuesToString());
            }
        }
    }

    public String dumpAsString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                str.append(grid[i][j]);
            }
        }
        return (str.toString());
    }
}

