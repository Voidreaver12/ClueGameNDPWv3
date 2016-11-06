package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

public class GameActionTests {
	
	private static Board board;
	@BeforeClass
	public static void setUp() {	
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "Legend.txt");		
		board.initialize();
		try {
			board.loadPlayersConfigFiles("Players.txt");
			board.loadWeaponsConfigFiles("Weapons.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void makeAccusationTest() {
		// solution that is correct test
		Solution accusation = new Solution("Bernie","Library", "Sponge");	
		board.setSolution("Bernie","Library", "Sponge");
		assertTrue(board.checkAccusation(accusation));
		//accusation with wrong person
		accusation = new Solution("Trump","Library", "Sponge");	
		assertFalse(board.checkAccusation(accusation));
		//accusation with wrong room
		accusation = new Solution("Bernie","Bar", "Sponge");
		assertFalse(board.checkAccusation(accusation));
		//accusation with wrong weapon
		accusation = new Solution("Bernie","Library", "Belt");
		assertFalse(board.checkAccusation(accusation));
	}
	
	@Test
	public void selectTargetTest() {
		board.calcAdjacencies();
		ComputerPlayer bernie = new ComputerPlayer();
		
		// Test that each of the targets is selected at least once
		// for targets from a walkway that does not include a room
		bernie.setRow(6);
		bernie.setColumn(3);
		board.calcTargets(6, 3, 1);
		Set<BoardCell> targets = board.getTargets();
		Set<BoardCell> targetsSelected = new HashSet<BoardCell>();
		for (int i = 0; i < 100; i++) {
			targetsSelected.add(bernie.pickLocation(targets));
		}
		assertEquals(4, targetsSelected.size());
		assertTrue(targetsSelected.contains(board.getCellAt(6, 4)));
		assertTrue(targetsSelected.contains(board.getCellAt(6, 2)));
		assertTrue(targetsSelected.contains(board.getCellAt(7, 3)));
		assertTrue(targetsSelected.contains(board.getCellAt(5, 3)));
		
		
		
		// Test that the target selected from a walkway is a room
		// when the doorway is one of the targets
		bernie.setRow(3);
		bernie.setColumn(4);
		board.calcTargets(3, 4, 1);
		targets = board.getTargets();
		assertEquals(board.getCellAt(3, 5), bernie.pickLocation(targets));
		
		
		
		// Test that when moving from a doorway, another doorway into that same room
		// is randomly selected with other spaces outside the room in targets
		bernie.setRow(9);
		bernie.setColumn(3);
		board.calcTargets(9, 3, 3);
		targets = board.getTargets();
		targetsSelected.clear();
		for (int i = 0; i < 100; i++) {
			targetsSelected.add(bernie.pickLocation(targets));
		}
		assertEquals(4, targetsSelected.size());
		assertTrue(targetsSelected.contains(board.getCellAt(7, 4)));
		assertTrue(targetsSelected.contains(board.getCellAt(11, 4)));
		assertTrue(targetsSelected.contains(board.getCellAt(10, 3)));
		assertTrue(targetsSelected.contains(board.getCellAt(8, 3)));
	
	}
	
	@Test
	public void createSuggestionTest() {
		// initialization
		ComputerPlayer bernie = new ComputerPlayer("Bernie", 10, 3, Color.BLUE);
		// Test to make sure suggested room matches location
		Solution suggestion = bernie.createSuggestion();
		assertTrue(suggestion.room.equals("Bar"));
		
		// Test for multiple weapons/people not seen,
		// that they are selected randomly
		Set<String> suggestedWeapons = new HashSet<String>();
		Set<String> suggestedPeople = new HashSet<String>();
		for (int i = 0; i < 500; i++) {
			suggestion = bernie.createSuggestion();
			suggestedWeapons.add(suggestion.weapon);
			suggestedPeople.add(suggestion.person);
		}
		assertTrue(suggestedWeapons.contains("HandSaw"));
		assertTrue(suggestedWeapons.contains("Scooter"));
		assertTrue(suggestedWeapons.contains("Fork"));
		assertTrue(suggestedWeapons.contains("Belt"));
		assertTrue(suggestedWeapons.contains("Hamster"));
		assertTrue(suggestedWeapons.contains("Sponge"));
		assertTrue(suggestedPeople.contains("Trump"));
		assertTrue(suggestedPeople.contains("Hillary"));
		assertTrue(suggestedPeople.contains("Bernie"));
		assertTrue(suggestedPeople.contains("Kanye"));
		assertTrue(suggestedPeople.contains("Miley"));
		assertTrue(suggestedPeople.contains("Gaga"));
		
		// Test that if only one weapon/person has 
		// not been seen, they are suggested
		board.disprove("HandSaw");
		board.disprove("Scooter");
		board.disprove("Belt");
		board.disprove("Hamster");
		board.disprove("Sponge");
		board.disprove("Trump");
		board.disprove("Bernie");
		board.disprove("Kanye");
		board.disprove("Miley");
		board.disprove("Gaga");
		suggestion = bernie.createSuggestion();
		assertTrue(suggestion.weapon.equals("Fork"));
		assertTrue(suggestion.person.equals("Hillary"));
	}
	
	@Test public void disproveSuggestionTest() {
		ComputerPlayer bernie = new ComputerPlayer("Bernie", 10, 3, Color.BLUE);
		ArrayList<Card> cards = board.getCards();
		
		// Testing that bernie returns fork since that is all he has
		for (Card c : cards) {
			if (c.getCardName().equals("Fork")) {
				bernie.dealCard(c);
			}
		}
		Solution suggestion = new Solution("Trump", "Library", "Fork");
		Card card = bernie.disproveSuggestion(suggestion);
		assertTrue(card.getCardName().equals("Fork"));
				
		
		// Testing that bernie returns things randomly when he has more than 1 match
		for (Card c : cards) {
			if (c.getCardName().equals("Trump")) {
				bernie.dealCard(c);
			}
		}
		int trump = 0;
		int fork = 0;
		for (int i = 0; i < 10; i++) {
			Card c = bernie.disproveSuggestion(suggestion);
			if (c.getCardName().equals("Fork")) {
				fork++;
			}
			if (c.getCardName().equals("Trump")) {
				trump++;
			}
		}
		assertTrue(trump >= 1);
		assertTrue(fork >= 1);
		assertEquals(10, (trump + fork));
		
		// Test that bernie returns null if he does not have a match
		suggestion = new Solution("Hillary", "Library", "Belt");
		assertEquals(bernie.disproveSuggestion(suggestion), null);
	}
	
	@Test
	public void handleSuggestionTest() {
		ComputerPlayer bernie = new ComputerPlayer("Bernie", 10, 3, Color.BLUE);
		board.dealCards();
		ArrayList<Player> players = board.getPlayers();
		// Test the solution, none should have a match
		Solution suggestion = board.getAnswer();
		assertEquals(null, board.handleSuggestion(suggestion, players.get(1)));
		
		
		// Explicitly give people certain cards
		
		// solution
		Solution solution = new Solution("Trump", "Balcony", "Handsaw");
		board.setSolution("Trump", "Balcony", "Handsaw");
		// player 1 Trump
		Set<Card> cards = new HashSet<Card>();
		Card fork = new Card("Fork", CardType.WEAPON);
		cards.add(fork);
		cards.add(new Card("Scooter", CardType.WEAPON));
		cards.add(new Card("Belt", CardType.WEAPON));
		players.get(0).setCards(cards);
		// player 2 Hillary
		cards = new HashSet<Card>();
		cards.add(new Card("Hamster", CardType.WEAPON));
		cards.add(new Card("Sponge", CardType.WEAPON));
		cards.add(new Card("Hillary", CardType.PERSON));
		players.get(1).setCards(cards);
		// player 3 Bernie
		cards = new HashSet<Card>();
		Card bern = new Card("Bernie", CardType.PERSON);
		cards.add(bern);
		cards.add(new Card("Miley", CardType.PERSON));
		cards.add(new Card("Kanye", CardType.PERSON));
		players.get(2).setCards(cards);
		// player 4 Miley
		cards = new HashSet<Card>();
		cards.add(new Card("Gaga", CardType.PERSON));
		cards.add(new Card("Gym", CardType.ROOM));
		cards.add(new Card("Art gallery", CardType.ROOM));
		players.get(3).setCards(cards);
		// player 5 Kanye
		cards = new HashSet<Card>();
		cards.add(new Card("Computer Lab", CardType.ROOM));
		cards.add(new Card("Bathroom", CardType.ROOM));
		cards.add(new Card("Game room", CardType.ROOM));
		players.get(4).setCards(cards);
		// player 6 Gaga
		cards = new HashSet<Card>();
		Card library = new Card("Library", CardType.ROOM);
		cards.add(library);
		cards.add(new Card("Bar", CardType.ROOM));
		cards.add(new Card("Office room", CardType.ROOM));
		players.get(5).setCards(cards);
		
		board.setPlayers(players);
		
		// Test suggestion that only accusing player (hillary) can disprove
		suggestion = new Solution("Trump", "Balcony", "Sponge");
		assertEquals(null, board.handleSuggestion(suggestion, players.get(1)));
		// Only trump can disprove(human)
		suggestion = new Solution("Trump", "Balcony", "Fork");
		assertEquals(fork, board.handleSuggestion(suggestion, players.get(1)));
		// Only trump can disprove, and trump is accusing
		suggestion = new Solution("Trump", "Balcony", "Fork");
		assertEquals(null, board.handleSuggestion(suggestion, players.get(0)));
		// Bernie and Miley (both computers) can disprove, but trump is accusing and gets a card from bernie first
		suggestion = new Solution("Bernie", "Gym", "Handsaw");
		assertEquals(bern, board.handleSuggestion(suggestion, players.get(0)));
		// Kanye is accusing, Gaga(computer) and Trump(human) can disprove, but gaga is next and returns a card
		suggestion = new Solution("Trump", "Library", "Fork");
		assertEquals(library, board.handleSuggestion(suggestion, players.get(4)));
	}

}
