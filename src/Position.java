
public class Position {

	int	row;
	int column;
	
	
	/**
	 * @param row
	 * @param column
	 */
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
		StringBuffer str = new StringBuffer();
		str.append(new Integer(row).toString());
		str.append(":");
		str.append(new Integer(column).toString());
		return(str.toString());
	}
}
