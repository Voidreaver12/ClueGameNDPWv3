package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public abstract class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private Set<Card> myCards = new HashSet<Card>();
	//private Set<Card> seenCards = new HashSet<Card>();
	
	
	public Card disproveSuggestion(Solution suggestion){
		for (Card c : myCards){
			if (c.equals(suggestion)){
				return c;
			}
		}
		return null;
	}

	public Player(String name, int row, int column, Color color) {
		this.playerName = name;
		this.row = row;
		this.column = column;
		this.color = color;
	}
	
	public Player() {
		
	}
	
	public void dealCard(Card c){
		myCards.add(c);
	}

	public abstract Solution createSuggestion();
	
	// For Testing
	public String getPlayerName() {
		return playerName;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
	
	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	public Color getColor() {
		return color;
	}

	public Set<Card> getCards() {
		return myCards;
	}

	public void setCards(Set<Card> cards) {
		myCards = cards;
	}

	public void move(BoardCell location) {
		setRow(location.getRow());
		setColumn(location.getColumn());
	}
}
