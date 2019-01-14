package com.david;

import java.util.LinkedList;


public class Strategy {

	////////////////////////////////////////////////////////////////////////////////////////////
	//
	//	RemoveValueFromAllAffectedPossibileValueLists
	//	
	//	Traverses each node in each row, column and block in which this node value exists and removes
	//	the value from the possible values list.
	//
	//	If only one possible value remains in the possibility list, that node is set to that value.
	//
	//  It is called recursively on Grid.setVal
	//

	static Boolean RemoveValueFromAllAffectedPossibileValueLists(Node node, Grid grid){
		
		// Remove value from possible Values in nodes in row
		LinkedList<Node> rowListPossible = grid.getOtherNodesInRow(node.getPosition());
		for (Node n : rowListPossible){
			if(n.getValue() == 0){  // Value not set
				if(n.removePossibleValue(node.getValue())){
					if(n.getPossibleValues().size() == 1){
						grid.setVal(n.getPossibleValues().get(0), n.getPosition(), "Only Possible - row");
					}
				}
			}
		}

		// Remove value from possible Values in nodes in column
		LinkedList<Node> columnListPossible = grid.getOtherNodesInColumn(node.getPosition());
		for (Node n : columnListPossible){
            if(n.getValue() == 0){  // Value not set
                if(n.removePossibleValue(node.getValue())){
                    if(n.getPossibleValues().size() == 1){
                        grid.setVal(n.getPossibleValues().get(0), n.getPosition(), "Only Possible - column");
                    }
                }
            }
		}
		
		// Remove value from possible Values in nodes in block
		LinkedList<Node> blockListPossible = grid.getOtherNodesInBlock(node.getPosition());
		for (Node n : blockListPossible){
            if(n.getValue() == 0){  // Value not set
                if(n.removePossibleValue(node.getValue())){
                    if(n.getPossibleValues().size() == 1){
                        grid.setVal(n.getPossibleValues().get(0), n.getPosition(), "Only Possible - block");
                    }
                }
            }
		}
		return(true);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////
	//
	//	UniquePossibleValue
	//
	//	Traverses all rows, columns and blocks to determine if there exists a unique possible
	//  number.
	//
	//  If so, it sets that node to the unique number.
	//
	//	This not the most optimum algorithm. It searches the whole grid each time. It could
	//  be optimized to search only nodes that are affectd by the setVal.
	//
	//  It is called recursively on Grid.setVal
    //
//
//
//	static Boolean UniquePossibleValue(Node node, Grid grid){
//
//		// Looks for unique possible values in the row
//		for( int row = 1; row <= 9; row++){
//			LinkedList<Node> rowNodeList = grid.getAllNodesInRow(new Position(row, 1));
//			processUniqueNodesInList(rowNodeList, grid);
//		}
//
//		// Looks for unique possible values in the column
//		for( int column = 1; column <= 9; column++){
//			LinkedList<Node> columnNodeList = grid.getAllNodesInColumn(new Position(1, column));
//			processUniqueNodesInList(columnNodeList, grid);
//		}
//
//		// Looks for unique possible values in the block
//		for( int blockRow = 1; blockRow <= 3; blockRow++){
//			for( int blockColumn = 1; blockColumn <= 3; blockColumn++){
//				LinkedList<Node> blockNodeList = grid.getAllNodesInBlockFromBlockPosition(blockRow, blockColumn);
//				processUniqueNodesInList(blockNodeList, grid);
//			}
//		}
//		return(true);
//	}
//
//
//	static private Boolean processUniqueNodesInList(LinkedList<Node> nodeList, Grid grid){
//
//		for (int val = 1; val <= 9; val++){
//			Node nodeWithUniqueValue = null;
//			Integer valToFind = val;
//
//			for (Node n : nodeList){
//				if(n.getValue() == val){ // Value already set in a node, cannot be in any possible list
//					nodeWithUniqueValue = null;
//					break;
//				}
//				if(n.possibleValues.contains(valToFind)){ // Node contains the value on its possible List
//					if(nodeWithUniqueValue != null){ // Has already found this value
//						nodeWithUniqueValue = null;
//						break;
//					}
//					else
//						nodeWithUniqueValue = n; // Node contains value in possible list
//				}
//			}
//
//			// If there is only a single node with this value on the possible list
//			//	   - set the node value to this value
//			if(nodeWithUniqueValue != null)
//				grid.setVal(val, nodeWithUniqueValue.getPosition(), "UV");
//
//		}
//		return(true);
//	}
}
				