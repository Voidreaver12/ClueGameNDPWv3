package tests;

import java.util.Set;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import clueGame.Board;
import clueGame.BoardCell;


public class BoardAdjTargetTest {

	private static Board board;
	@BeforeClass
	public static void setUp() {	
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "Legend.txt");		
		board.initialize();
	}
	
		// Ensure that player does not move around within room
		// These cells are PURPLE on the planning spreadsheet
		@Test
		public void testAdjacenciesInsideRooms(){
			// Test a corner
			Set<BoardCell> testList = board.getAdjList(0, 0);
			assertEquals(0, testList.size());
			// Test one that has walkway underneath
			testList = board.getAdjList(3, 0);
			assertEquals(0, testList.size());
			// Test one that has walkway above
			testList = board.getAdjList(7, 1);
			assertEquals(0, testList.size());
			// Test one that is in middle of room
			testList = board.getAdjList(2, 7);
			assertEquals(0, testList.size());
			// Test one beside a door
			testList = board.getAdjList(14, 13);
			assertEquals(0, testList.size());
			// Test one in a corner of room
			testList = board.getAdjList(17, 2);
			assertEquals(0, testList.size());
		}
		// Ensure that the adjacency list from a doorway is only the walkway.
		// These tests are Light GREEN on the planning spreadsheet
		@Test
		public void testAdjacencyRoomExit(){
			// TEST DOORWAY RIGHT 
			Set<BoardCell> testList = board.getAdjList(9, 3);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCellAt(9, 4)));
			// TEST DOORWAY LEFT 
			testList = board.getAdjList(3, 5);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCellAt(3, 4)));
			//TEST DOORWAY DOWN
			testList = board.getAdjList(5, 7);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCellAt(6, 7)));
			//TEST DOORWAY UP
			testList = board.getAdjList(14, 11);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCellAt(13, 11)));
		}
		// Test adjacency at entrance to rooms
		// These tests are RED in planning spreadsheet
		@Test
		public void testAdjacencyDoorways(){
			// Test beside a door direction RIGHT
			Set<BoardCell> testList = board.getAdjList(3, 10);
			assertTrue(testList.contains(board.getCellAt(3, 9)));
			assertTrue(testList.contains(board.getCellAt(3, 11)));
			assertTrue(testList.contains(board.getCellAt(4, 10)));
			assertTrue(testList.contains(board.getCellAt(2, 10)));
			assertEquals(4, testList.size());
			// Test beside a door direction DOWN
			testList = board.getAdjList(6, 7);
			assertTrue(testList.contains(board.getCellAt(6, 8)));
			assertTrue(testList.contains(board.getCellAt(6, 6)));
			assertTrue(testList.contains(board.getCellAt(5, 7)));
			assertTrue(testList.contains(board.getCellAt(7, 7)));
			assertEquals(4, testList.size());
			// Test beside a door direction LEFT
			testList = board.getAdjList(10, 8);
			assertTrue(testList.contains(board.getCellAt(10, 9)));
			assertTrue(testList.contains(board.getCellAt(9, 8)));
			assertTrue(testList.contains(board.getCellAt(11, 8)));
			assertEquals(3, testList.size());
			// Test beside a door direction UP
			testList = board.getAdjList(16, 1);
			assertTrue(testList.contains(board.getCellAt(17, 1)));
			assertTrue(testList.contains(board.getCellAt(16, 0)));
			assertTrue(testList.contains(board.getCellAt(16, 2)));
			assertTrue(testList.contains(board.getCellAt(15, 1)));
			assertEquals(4, testList.size());
		}
		// Test a variety of walkway scenarios
		// These tests are BLUE-GREY on the planning spreadsheet
		@Test
		public void testAdjacencyWalkways(){
			// Test on top edge of board, just one walkway piece
			Set<BoardCell> testList = board.getAdjList(0, 3);
			assertTrue(testList.contains(board.getCellAt(0, 4)));
			assertEquals(1, testList.size());
			// Test on left edge of board, three walkway pieces
			testList = board.getAdjList(5, 0);
			assertTrue(testList.contains(board.getCellAt(4, 0)));
			assertTrue(testList.contains(board.getCellAt(5, 1)));
			assertTrue(testList.contains(board.getCellAt(6, 0)));
			assertEquals(3, testList.size());
			// Test between two rooms, on right edge, only walkway to left
			testList = board.getAdjList(13, 13);
			assertTrue(testList.contains(board.getCellAt(13, 12)));
			assertEquals(1, testList.size());
			// Test surrounded by 4 walkways
			testList = board.getAdjList(6,11);
			assertTrue(testList.contains(board.getCellAt(7, 11)));
			assertTrue(testList.contains(board.getCellAt(5, 11)));
			assertTrue(testList.contains(board.getCellAt(6, 12)));
			assertTrue(testList.contains(board.getCellAt(6, 10)));
			assertEquals(4, testList.size());			
			// Test on bottom edge of board, next to 1 room piece
			testList = board.getAdjList(19, 3);
			assertTrue(testList.contains(board.getCellAt(18, 3)));
			assertTrue(testList.contains(board.getCellAt(19, 4)));
			assertEquals(2, testList.size());			
			// Test on right edge of board, next to 1 room piece
			testList = board.getAdjList(8, 13);
			assertTrue(testList.contains(board.getCellAt(7, 13)));
			assertTrue(testList.contains(board.getCellAt(8, 12)));
			assertEquals(2, testList.size());
			// Test on walkway next to  door that is not in the needed
			// direction to enter
			testList = board.getAdjList(11, 3);
			assertTrue(testList.contains(board.getCellAt(12, 3)));
			assertTrue(testList.contains(board.getCellAt(11, 4)));
			assertEquals(2, testList.size());
		}
		// Tests of just walkways, 1 step, 
		// These are DARK GREEN on the planning spreadsheet
		@Test
		public void testTargetsOneStep() {
			board.calcTargets(19, 13, 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(2, targets.size());
			assertTrue(targets.contains(board.getCellAt(18, 13)));
			assertTrue(targets.contains(board.getCellAt(19, 12)));	
			
			board.calcTargets(14, 0, 1);
			targets= board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCellAt(14, 1)));
			assertTrue(targets.contains(board.getCellAt(13, 0)));	
			assertTrue(targets.contains(board.getCellAt(15, 0)));			
		}
		// Tests of just walkways, 2 steps
		// These are DARK GREEN on the planning spreadsheet
		@Test
		public void testTargetsTwoSteps() {
			board.calcTargets(19, 13, 2);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCellAt(18, 12)));
			assertTrue(targets.contains(board.getCellAt(19, 11)));
			assertTrue(targets.contains(board.getCellAt(17, 13)));
			
			board.calcTargets(14, 0, 2);
			targets= board.getTargets();
			assertEquals(4, targets.size());
			assertTrue(targets.contains(board.getCellAt(13, 1)));
			assertTrue(targets.contains(board.getCellAt(14, 2)));	
			assertTrue(targets.contains(board.getCellAt(15, 1)));
			assertTrue(targets.contains(board.getCellAt(16, 0)));
		}
		// Tests of just walkways, 4 steps
		// These are DARK GREEN on the planning spreadsheet
		@Test
		public void testTargetsFourSteps() {
			board.calcTargets(19, 13, 4);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(6, targets.size());
			assertTrue(targets.contains(board.getCellAt(19, 9)));
			assertTrue(targets.contains(board.getCellAt(18, 10)));
			assertTrue(targets.contains(board.getCellAt(17, 11)));
			assertTrue(targets.contains(board.getCellAt(17, 13)));
			assertTrue(targets.contains(board.getCellAt(18, 12)));
			assertTrue(targets.contains(board.getCellAt(19, 11)));
			
			board.calcTargets(14, 0, 4);
			targets= board.getTargets();
			assertEquals(9, targets.size());
			assertTrue(targets.contains(board.getCellAt(13, 3)));
			assertTrue(targets.contains(board.getCellAt(13, 1)));
			assertTrue(targets.contains(board.getCellAt(14, 2)));	
			assertTrue(targets.contains(board.getCellAt(14, 4)));	
			assertTrue(targets.contains(board.getCellAt(15, 3)));
			assertTrue(targets.contains(board.getCellAt(15, 1)));
			assertTrue(targets.contains(board.getCellAt(17, 1)));	
			assertTrue(targets.contains(board.getCellAt(16, 2)));	
			assertTrue(targets.contains(board.getCellAt(16, 0)));
		}	
		// Tests of just walkways plus one door, 6 steps
		// These are DARK GREEN on the planning spreadsheet
		@Test
		public void testTargetsSixSteps() {
			board.calcTargets(19, 13, 6);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(9, targets.size());
			assertTrue(targets.contains(board.getCellAt(18, 8)));
			assertTrue(targets.contains(board.getCellAt(18, 10)));	
			assertTrue(targets.contains(board.getCellAt(18, 12)));	
			assertTrue(targets.contains(board.getCellAt(19, 9)));	
			assertTrue(targets.contains(board.getCellAt(19, 11)));	
			assertTrue(targets.contains(board.getCellAt(17, 13)));	
			assertTrue(targets.contains(board.getCellAt(17, 11)));	
			assertTrue(targets.contains(board.getCellAt(17, 9)));
			assertTrue(targets.contains(board.getCellAt(16, 10)));
		}	
		// Test getting into a room
		// These are DARK GREEN on the planning spreadsheet
		@Test 
		public void testTargetsIntoRoom(){
			// One room is exactly 2 away
			board.calcTargets(5, 12, 2);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(7, targets.size());
			assertTrue(targets.contains(board.getCellAt(5, 10)));
			assertTrue(targets.contains(board.getCellAt(3, 12)));
			assertTrue(targets.contains(board.getCellAt(4, 13)));
			assertTrue(targets.contains(board.getCellAt(4, 11)));
			assertTrue(targets.contains(board.getCellAt(6, 13)));
			assertTrue(targets.contains(board.getCellAt(7, 12)));
			assertTrue(targets.contains(board.getCellAt(6, 11)));
		}
		// Test getting out of a room
		// These are DARK GREEN on the planning spreadsheet
		@Test
		public void testRoomExit(){
			// Take one step, essentially just the adj list
			board.calcTargets(17, 7, 1);
			Set<BoardCell> targets= board.getTargets();
			// Ensure doesn't exit through the wall
			assertEquals(1, targets.size());
			assertTrue(targets.contains(board.getCellAt(16, 7)));
			// Take two steps
			board.calcTargets(17, 7, 2);
			targets= board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCellAt(16, 6)));
			assertTrue(targets.contains(board.getCellAt(15, 7)));
			assertTrue(targets.contains(board.getCellAt(16, 8)));
		}
		// Test getting into room, doesn't require all steps
		// These are DARK GREEN on the planning spreadsheet
		@Test
		public void testTargetsIntoRoomShortcut() {
			board.calcTargets(12, 12, 3);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(7, targets.size());
			// stay in room even though more moves availible
			assertTrue(targets.contains(board.getCellAt(14, 12)));
			//normal door entry
			assertTrue(targets.contains(board.getCellAt(14, 11)));
			assertTrue(targets.contains(board.getCellAt(11, 12)));
			//normal walking
			assertTrue(targets.contains(board.getCellAt(13, 12)));
			assertTrue(targets.contains(board.getCellAt(13, 10)));
			assertTrue(targets.contains(board.getCellAt(12, 11)));
			assertTrue(targets.contains(board.getCellAt(10, 11)));
			
				
		}
}
