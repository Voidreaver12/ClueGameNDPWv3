package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;

public class gameSetupTests {
	
	private static Board board;
	@BeforeClass
	public static void setUp() {	
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "Legend.txt");		
		board.initialize();
		/*
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
		*/
	}
	
	@Test
	public void loadPeopleTest() {
		ArrayList<Player> players = board.getPlayers();
		// 6 players
		assertEquals(6, players.size());
		// Trump
		System.out.println(players.get(0));
		assertEquals(4, players.get(0).getRow());
		assertEquals(0, players.get(0).getColumn());
		assertEquals(Color.RED, players.get(0).getColor());
		assertEquals("Trump", players.get(0).getPlayerName());
		// Kanye
		assertEquals(19, players.get(4).getRow());
		assertEquals(4, players.get(4).getColumn());
		assertEquals(Color.BLACK, players.get(4).getColor());
		assertEquals("Kanye", players.get(4).getPlayerName());
	}

	
	@Test
	public void loadCardsTest() {
		ArrayList<Card> cards = board.getCards();
		ArrayList<Card> weapons = board.getWeaponCards();
		ArrayList<Card> rooms = board.getRoomCards();
		ArrayList<Card> people = board.getPeopleCards();
		
		// Test that the correct number of each card is loaded
		assertEquals(21, cards.size());
		assertEquals(6, weapons.size());
		assertEquals(6, people.size());
		assertEquals(9, rooms.size());
		
		// Test that the belt weapon card is loaded
		int x = 0;
		Card c = new Card("Belt", CardType.WEAPON);
		for (int i = 0; i < weapons.size(); i++) {
			if (weapons.get(i).equals(c)) { x++; }
		}
		assertEquals(1, x);
		
		// Test that the trump person card is loaded
		x = 0;
		c = new Card("Trump", CardType.PERSON);
		for (int i = 0; i < people.size(); i++) {
			if (people.get(i).equals(c)) { x++; }
		}
		assertEquals(1, x);
		
		// Test that the bathroom room card is loaded
		x = 0;
		c = new Card("Bathroom", CardType.ROOM);
		for (int i = 0; i < rooms.size(); i++) {
			if (rooms.get(i).equals(c)) { x++; }
		}
		assertEquals(1, x);
	}
	
	@Test
	public void dealCardsTest() {
		ArrayList<Player> players = board.getPlayers();
		ArrayList<Card> deck = board.getDeck();
		//board.dealCards();
		// make sure that the cards left is 0
		assertEquals(0, deck.size());
		// Check to make sure everyone has 3 cards. 21 cards, 3 in solution, 18/6 = 3
		Set<Integer> sizes = new HashSet<Integer>();
		for (int i = 0; i < players.size(); i++) {
			sizes.add(players.get(i).getCards().size());
		}
		assertTrue(sizes.size() <= 2);
		// Check to make sure noone has the same card
		Set<Card> cardsDealt = new HashSet<Card>();
		for (int i = 0; i < players.size(); i++) {
			Set<Card> myCards = players.get(i).getCards();
			for (Card c : myCards) {
				if (cardsDealt.contains(c)) {
					fail();
				}
				else {
					cardsDealt.add(c);
				}
			}
		}
		assertTrue(true);
		
		
	}
}
