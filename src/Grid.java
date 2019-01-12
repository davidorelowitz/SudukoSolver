import java.util.LinkedList;

public class Grid {

    //static Output output;
    Node grid [][] = new Node[9][9];
    int	numSet;
    int	indent;

    // Initialize all locations to zero
    {
        for(int row = 1; row <= 9; row ++){
            for(int col = 1; col <= 9; col++)
                grid[row - 1][col - 1] = new Node(new Position(row, col));
        }
    }

    Boolean setVal(int val, Position location, String reason){

        try{
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

            if(location.row < 1 || location.row > 9){
                System.out.println(indentBuffer.toString() + "Invalid row " + location.row);
                return(false);
            }

            if(location.column < 1 || location.column > 9){
                System.out.println(indentBuffer.toString() + "Invalid column " + location.column);
                return(false);
            }

            Node nodeToSet = grid[location.row - 1][location.column - 1];

            if(nodeToSet.getValue() != 0){
                System.out.println(indentBuffer.toString() + location.row + ":" + location.column + " already set");
                return(false);
            }

            // Is value in row
            LinkedList<Node> rowList = getOtherNodesInRow(location);
            for (Node n : rowList){
                if(val == n.value){
                    System.out.println(indentBuffer.toString() + val + " is already in row " + location.row);
                    return(false);
                }
            }

            // Is value in column
            LinkedList<Node> columnList = getOtherNodesInColumn(location);
            for (Node n : columnList){
                if(val == n.value){
                    System.out.println(indentBuffer.toString() + val + " is already in column " + location.column);
                    return(false);
                }
            }

            // Is value in block
            LinkedList<Node> blockList = getOtherNodesInBlock(location);
            for (Node n : blockList){
                if(val == n.value){
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
    //	return(new Position(((location.row - 1) / 3) + 1,((location.column - 1) / 3) + 1));
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
            if (excludeSelf && pos.column == i){
                continue;
            }
            rowList.add(grid[pos.row - 1][i - 1]);
        }
        return(rowList);
    }

    private LinkedList<Node> getNodesInColumn(Position pos, boolean excludeSelf){

        LinkedList<Node> colList = new LinkedList<>();
        for(int i = 1; i <= 9; i++){
            if (excludeSelf && pos.row == i){
                continue;
            }
            colList.add(grid[i - 1][pos.column - 1]);
        }
        return(colList);
    }

    //public LinkedList<Node> getNodesInBlockFromLocation(Position location){
    //	Position block = getBlock(location);
    //	return(getNodesInBlock(block));
    //}

    private LinkedList<Node> getNodesInBlock(Position pos, boolean excludeSelf){

        int startRow = ((pos.row - 1) / 3) * 3 + 1;
        int endRow = startRow + 2;
        int startColumn = ((pos.column - 1) / 3) * 3 + 1;
        int endColumn = startColumn + 2;

        LinkedList<Node> nodeList = new LinkedList<>();

        for(int row = startRow; row <= endRow; row++){
            for(int column = startColumn; column <= endColumn; column++) {
                if (excludeSelf && pos.row == row && pos.column == column){
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
                System.out.println(node.position.toString() + " | " + node.toString() + " | " + node.possibleValuesToString());
            }
        }
    }

}

