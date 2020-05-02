package com.david;

public class Position {

	private final int	row;
	private final int column;
	
	public Position(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public int getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	public String toString(){
		return(row + ":" + column);
	}
}
