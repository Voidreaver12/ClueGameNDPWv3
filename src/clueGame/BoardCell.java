package clueGame;

import java.awt.Color;
import java.awt.Graphics;

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
	
	public void draw(Graphics g, int i, int j) {
		final int size = 35;
		g.setColor(Color.YELLOW);
		g.fillRect(i*size, j*size, size, size);
		g.setColor(Color.BLACK);
		g.drawRect(i*size, j*size, size, size);
		if (initial != 'W') {
			g.setColor(Color.GRAY);
			g.fillRect(i*size, j*size, size, size);
		}
		g.setColor(Color.BLUE);
		switch(opening) {
		case UP:
			g.fillRect(i*size, j*size, size, size/5);
			break;
		case DOWN:
			g.fillRect(i*size, j*size+30, size, size/5);
			break;
		case RIGHT:
			g.fillRect(i*size+30, j*size, size/5, size);
			break;
		case LEFT:
			g.fillRect(i*size, j*size, size/5, size);
			break;
			
		}
		//System.out.println(i);
		//System.out.println("hello");
		
	}
	
	public void drawTarget(Graphics g) {
		final int size = 35;
		g.setColor(Color.CYAN);
		g.fillRect(getColumn()*size, getRow()*size, size, size);
		g.setColor(Color.BLACK);
		g.drawRect(getColumn()*size, getRow()*size, size, size);
		g.setColor(Color.BLUE);
		switch(opening) {
		case UP:
			g.fillRect(column*size, row*size, size, size/5);
			break;
		case DOWN:
			g.fillRect(column*size, row*size+30, size, size/5);
			break;
		case RIGHT:
			g.fillRect(column*size+30, row*size, size/5, size);
			break;
		case LEFT:
			g.fillRect(column*size, row*size, size/5, size);
			break;
			
		}
	}
}
