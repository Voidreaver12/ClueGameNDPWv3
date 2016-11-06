// Noah Deibert, Patrick Weaver version 3
//Noah Deibert, Robert Schreibman version 2
// Original owners: CMCK
package clueGame;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {

	public static final int MAX_BOARD_SIZE = 50;
	private static int numRows;
	private static int numColumns;
	private static Board theInstance = new Board();
	private BoardCell[][] board;
	private Map<Character , String> rooms;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	private Set<BoardCell> visited;
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Card> cards = new ArrayList<Card>();
	private ArrayList<Card> deck = new ArrayList<Card>();
	private ArrayList<Card> cardsDealt = new ArrayList<Card>();
	private ArrayList<Card> weaponCards = new ArrayList<Card>();
	private ArrayList<Card> roomCards = new ArrayList<Card>();
	private ArrayList<Card> peopleCards = new ArrayList<Card>();
	private Set<String> disprovedCards = new HashSet<String>();
	private Solution theAnswer;

	private Board(){
		rooms = new HashMap<Character , String>();
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
	}
	public static Board getInstance() {
		return theInstance;
	}
	public void setConfigFiles(String string, String string2) {
		boardConfigFile = string;
		roomConfigFile = string2;
	}
	public void initialize() {
		FileReader reader = null;
		Scanner in = null;
		try {
			reader = new FileReader(boardConfigFile);
			in = new Scanner(reader);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			numRows = 0;
			while (in.hasNextLine()) {
				String line = in.nextLine();
				String[] pieces = line.split(" ");
				String temp = "";
				numColumns = 0;
				for(int i = 0; i < pieces[0].length(); i++){
					if (pieces[0].charAt(i) == ','){
						if(temp.length() == 1)
							board[numRows][numColumns] = new BoardCell(numRows, numColumns, temp.charAt(0), '!');
						else 
							board[numRows][numColumns] = new BoardCell(numRows, numColumns, temp.charAt(0), temp.charAt(1));
						temp = "";
						numColumns++;
					}
					else
						temp += pieces[0].charAt(i);
					if(i == pieces[0].length() - 1){
						if(temp.length() == 1)
							board[numRows][numColumns] = new BoardCell(numRows, numColumns, temp.charAt(0), '!');
						else 
							board[numRows][numColumns] = new BoardCell(numRows, numColumns, temp.charAt(0), temp.charAt(1));
						temp = "";
						numColumns++;
					}
				}
				numRows++;
			}
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
		}
		in.close();
		FileReader reader2 = null;
		Scanner in2 = null;
		try {
			reader2 = new FileReader(roomConfigFile);
			in2 = new Scanner(reader2);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			while (in2.hasNextLine()) {
				String line = in2.nextLine();
				String[] linePieces = line.split(", ");
				char temp = line.charAt(0);
				String tempsub = line.substring(3, line.length());
				rooms.put(temp, tempsub.substring(0, tempsub.indexOf(',')));
				Card card = new Card(tempsub.substring(0, tempsub.indexOf(',')), CardType.ROOM);
				if (linePieces[2].equals("Card")) {
					cards.add(card);
					roomCards.add(card);
				}
				
			}
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
		}
		calcAdjacencies();
		
		
		
		
	}
	public void calcAdjacencies(){
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numColumns; j++){
				Set<BoardCell> temp = new HashSet<BoardCell>();
				if(board[i][j].isRoom()){
					if(i - 1 >= 0 && ((board[i][j].isDoorway() && board[i][j].getDoorDirection() == DoorDirection.UP)))
						temp.add(board[i-1][j]);
					if(i + 1 < numRows && ((board[i][j].isDoorway() && board[i][j].getDoorDirection() == DoorDirection.DOWN)))
						temp.add(board[i+1][j]);
					if(j - 1 >= 0 &&((board[i][j].isDoorway() && board[i][j].getDoorDirection() == DoorDirection.LEFT)))
						temp.add(board[i][j-1]);
					if(j + 1 < numColumns && ((board[i][j].isDoorway() && board[i][j].getDoorDirection() == DoorDirection.RIGHT)))
						temp.add(board[i][j+1]);
					adjMatrix.put(board[i][j], temp);
				}
				else{
					if(i - 1 >= 0 && ( (board[i-1][j].isDoorway() && board[i-1][j].getDoorDirection() == DoorDirection.DOWN) || board[i-1][j].isWalkway() ))
						temp.add(board[i-1][j]);
					if(i + 1 < numRows && ( (board[i+1][j].isDoorway() && board[i+1][j].getDoorDirection() == DoorDirection.UP) || board[i+1][j].isWalkway() ))
						temp.add(board[i+1][j]);
					if(j - 1 >= 0 &&( (board[i][j-1].isDoorway() && board[i][j-1].getDoorDirection() == DoorDirection.RIGHT) || board[i][j-1].isWalkway() ))
						temp.add(board[i][j-1]);
					if(j + 1 < numColumns && ( (board[i][j+1].isDoorway() && board[i][j+1].getDoorDirection() == DoorDirection.LEFT) || board[i][j+1].isWalkway() ))
						temp.add(board[i][j+1]);
					adjMatrix.put(board[i][j], temp);
				}
			}
		}
	}
	public Map<Character, String> getLegend() {
		return rooms;
	}
	public int getNumRows() {
		return numRows;
	}
	public int getNumColumns() {
		return numColumns;
	}
	public BoardCell getCellAt(int i, int j) {
		return board[i][j];
	}
	public void loadRoomConfig() throws BadConfigFormatException, FileNotFoundException {
		FileReader roomFile = new FileReader(roomConfigFile);
		Scanner in = new Scanner(roomFile);
		while (in.hasNextLine()) {
			String line = in.nextLine();
			if(line.indexOf(',') != 1)
				throw new BadConfigFormatException("Room is represented by more than one character");
			String temp = line.substring(3, line.length());
			String check = temp.substring(temp.indexOf(',') + 2,temp.length());
			if( !check.equals("Card") && !check.equals("Other"))
				throw new BadConfigFormatException();
			
			
		}
		in.close();
	}
	public void loadBoardConfig() throws BadConfigFormatException, FileNotFoundException {
		FileReader roomFile = new FileReader(boardConfigFile);
		Scanner in = new Scanner(roomFile);
		int previousColumnNum = 0;
		String temp = "";
		while (in.hasNextLine()) {
			String line = in.nextLine();
			String[] pieces = line.split(" ");
			previousColumnNum = numColumns;
			numColumns = 0;
			for(int i = 0; i < pieces[0].length(); i++){
				if (pieces[0].charAt(i) == ','){
					numColumns++;
					if( !rooms.containsKey(temp.charAt(0)) )
						throw new BadConfigFormatException("You have a room in the clue board, that is not in the legend file");
					temp = "";
				}
				else
					temp += pieces[0].charAt(i);
			}
			numColumns++;
			if((numColumns != previousColumnNum) && (previousColumnNum != 0))
				throw new BadConfigFormatException("Different Number of columns per row!!");
		}
		in.close();
	}
	public Set<BoardCell> getAdjList(int i, int j) {
		return adjMatrix.get(board[i][j]);
	}
	public void calcTargets(BoardCell startCell, int pathLength){

		if(pathLength == 0){
			targets.add(startCell);
			return;
		}
		else{
			visited.add(startCell);
		}
		Iterator<BoardCell> adjacents = adjMatrix.get(startCell).iterator();
		while(adjacents.hasNext()){
			BoardCell next = adjacents.next();
			if(!visited.contains(next))
				calcTargets(next, pathLength-1);
			if(next.isDoorway() && !visited.contains(next)) {
				targets.add(next);
				visited.add(next);
			}
		}
		visited.remove(startCell);
	}
	public void calcTargets(int i, int j, int pathLength) {
		BoardCell startCell = getCellAt(i, j);
		targets.clear();
		if(startCell.isDoorway()){
			visited.add(startCell);
		}
		calcTargets(startCell,pathLength);
	}
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	public void loadPlayersConfigFiles(String configFileName) throws BadConfigFormatException, FileNotFoundException{	
		FileReader file = new FileReader(configFileName);
		Scanner in = new Scanner(file);
		while(in.hasNextLine()){
			String[] line = in.nextLine().split(" ");
			String name = line[0];
			int r = Integer.parseInt(line[2]);
			int c  = Integer.parseInt(line[3]);
			String strColor = line[1].trim();
			Color color = convertColor(strColor);
			if (name.equals("Trump")) {
				players.add(new HumanPlayer(name, r, c, color));
			}
			else {
				players.add(new ComputerPlayer(name, r, c, color));
			}
			Card card = new Card(name, CardType.PERSON);
			cards.add(card);
			peopleCards.add(card);
		}
		
		
		in.close();
				
	}
	
	public Color convertColor(String strColor) {
	    Color color; 
	    try {     
	        // We can use reflection to convert the string to a color
	        Field field = Class.forName("java.awt.Color").getField(strColor.trim());     
	        color = (Color)field.get(null); 
	    } catch (Exception e) {  
	        color = null; // Not defined  
	    }
	    return color;
	}

	public void loadWeaponsConfigFiles(String configFileName) throws FileNotFoundException{
		FileReader file = new FileReader(configFileName);
		Scanner in = new Scanner(file);
		while(in.hasNextLine()){
			String name = in.nextLine().trim();
			Card card = new Card(name, CardType.WEAPON);
			cards.add(card);
			weaponCards.add(card);
		}
		in.close();
	}
	
	public void dealCards() {
		deck = new ArrayList<Card>();
		for (Card c : cards) {
			deck.add(c);
		}
		Collections.shuffle(deck);
		Collections.shuffle(roomCards);
		Collections.shuffle(weaponCards);
		Collections.shuffle(peopleCards);
		Card weapon = weaponCards.get(0);
		Card person = peopleCards.get(0);
		Card room = roomCards.get(0);
		theAnswer = new Solution(person.getCardName(), room.getCardName(), weapon.getCardName());
		deck.remove(room);
		deck.remove(weapon);
		deck.remove(person);
		int i = 0;
		while(!deck.isEmpty()) {
			players.get(i).dealCard(deck.get(0));
			deck.remove(0);
			i++;
			if (i == players.size()) {
				i = 0;
			}
		}
		
	}
	
	
	public void selectAnswer(){
	}

	public Card handleSuggestion(Solution suggestion, Player p){
		int m = players.indexOf(p);
		for (int i = 0; i < players.size() - 1; i++) {
			m++;
			if (m == players.size()) {
				m = 0;
			}
			Card c = players.get(m).disproveSuggestion(suggestion);
			if (c != null) {
				return c;
			}
		}
		/*
		for(int i = m; i < m + players.size(); i++){
			int j = i % players.size();
			if (null != players.get(j).disproveSuggestion(suggestion)){
				Card cc = players.get(j).disproveSuggestion(suggestion);
				if (j == m ) return null;
				else return cc;
			}
		}
		*/
		return null;
	}
	public boolean checkAccusation(Solution accusation){
		return theAnswer.equals(accusation);
	}
	
	public Set<String> getDisprovedCards() {
		return disprovedCards;
	}
	
	// For Testing
	public void disprove(String c) {
		disprovedCards.add(c);
	}
	public ArrayList<Player> getPlayers() {
		return players;
	}
	public void setPlayers(ArrayList<Player> p){
		players = p;
	}
	
	public ArrayList<Card> getCards() {
		return cards;
	}
	public ArrayList<Card> getDeck() {
		return deck;
	}
	public ArrayList<Card> getWeaponCards() {
		return weaponCards;
	}
	public ArrayList<Card> getRoomCards() {
		return roomCards;
	}
	public ArrayList<Card> getPeopleCards() {
		return peopleCards;
	}
	
	public void setSolution(String name, String place, String weapon){
		Solution s = new Solution(name, place, weapon);
		theAnswer = s;
	}
	
	public Solution getAnswer() {
		return theAnswer;
	}
}
