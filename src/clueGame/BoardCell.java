package clueGame;

public class BoardCell {

	private int row;
	private int column;
	private char initial;
	private DoorDirection opening;
	
	public BoardCell(int row, int column, char initial, char doorLetter) {
		super();
		this.row = row;
		this.column = column;
		this.initial = initial;
		if(doorLetter == 'R')
			opening = DoorDirection.RIGHT;
		else if(doorLetter == 'L')
			opening = DoorDirection.LEFT;
		else if(doorLetter == 'U')
			opening = DoorDirection.UP;
		else if(doorLetter == 'D')
			opening = DoorDirection.DOWN;
		else 
			opening = DoorDirection.NONE;
	}
	public boolean isWalkway() {
		if (initial == 'W')
			return true;
		return false;
	}
	public boolean isRoom() {
		if (initial == 'W' || initial == 'X')
			return false;
		return true;
	}
	public boolean isDoorway() {
		if(opening.equals(DoorDirection.NONE))
			return false;
		return true;
	}
	public DoorDirection getDoorDirection() {
		return opening;
	}
	public char getInitial() {
		return initial;
	}
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
	
	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + "]";
	}
	
	
}
