package tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class FileInitTests {
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 20;
	public static final int NUM_COLUMNS = 14;
	
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "Legend.txt");		
		board.initialize();
	}
	@Test
	// tested all rooms because why not
	public void testRooms() {
		Map<Character, String> legend = board.getLegend();
		assertEquals(LEGEND_SIZE, legend.size());
		assertEquals("Balcony", legend.get('B'));
		assertEquals("Gym", legend.get('G'));
		assertEquals("Art gallery", legend.get('A'));
		assertEquals("Computer Lab", legend.get('C'));
		assertEquals("Game room", legend.get('J'));
		assertEquals("Library", legend.get('L'));
		assertEquals("Bar", legend.get('D'));
		assertEquals("Bathroom", legend.get('P'));
		assertEquals("Office room", legend.get('O'));
		assertEquals("Walkway", legend.get('W'));
		assertEquals("Closet", legend.get('X'));
	}
	@Test
	public void testBoardDimensions() {
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}
	@Test
	public void FourDoorDirections() {
		BoardCell room = board.getCellAt(2, 3);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(3, 12);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		room = board.getCellAt(3, 5);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		room = board.getCellAt(17, 6);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		room = board.getCellAt(0, 0);
		assertFalse(room.isDoorway());	
		BoardCell cell = board.getCellAt(9, 0);
		assertFalse(cell.isDoorway());		
	}
	@Test
	public void testNumberOfDoorways() {
		int numDoors = 0;
		for (int row=0; row < board.getNumRows(); row++)
			for (int col=0; col < board.getNumColumns(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(23, numDoors);
	}
	@Test
	public void testRoomInitials() {
		assertEquals('A', board.getCellAt(0, 0).getInitial());
		assertEquals('L', board.getCellAt(0, 5).getInitial());
		assertEquals('G', board.getCellAt(0, 12).getInitial());
		assertEquals('C', board.getCellAt(19, 0).getInitial());
		assertEquals('P', board.getCellAt(16, 13).getInitial());
		assertEquals('W', board.getCellAt(10, 8).getInitial());
		assertEquals('X', board.getCellAt(10,6).getInitial());
	}
	
	
	
	

}
