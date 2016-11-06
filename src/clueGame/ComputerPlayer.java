package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ComputerPlayer extends Player {
	Board board = Board.getInstance();

	public ComputerPlayer(String name, int r, int c, Color color) {
		super(name, r, c, color);
	}
	
	
	public ComputerPlayer() {
	}


	public BoardCell pickLocation(Set<BoardCell> targets){
		ArrayList<BoardCell> targetsToChooseFrom = new ArrayList<BoardCell>();
		ArrayList<BoardCell> roomsToChooseFrom = new ArrayList<BoardCell>();
		//char room = board.getCellAt(getRow(), getColumn()).getInitial();
		
		
		if (board.getCellAt(getRow(), getColumn()).isDoorway()) {
			targetsToChooseFrom.addAll(targets);
			Collections.shuffle(targetsToChooseFrom);
			return targetsToChooseFrom.get(0);
			
		}
		else {
			for (BoardCell cell : targets) {
				//System.out.println(cell);
				if (cell.isDoorway()) {
					//System.out.println("door");
					roomsToChooseFrom.add(cell);
				}
				else {
					//System.out.println("not a door");
					targetsToChooseFrom.add(cell);
				}
			}
			//System.out.println("hello");
			//System.out.println(roomsToChooseFrom.size());
			//System.out.println(targetsToChooseFrom.size());
			if (!roomsToChooseFrom.isEmpty()) {
				Collections.shuffle(roomsToChooseFrom);
				return roomsToChooseFrom.get(0);
			}
			else {
				Collections.shuffle(targetsToChooseFrom);
				return targetsToChooseFrom.get(0);
			}
		}
	}
	
	public void makeAccusation(){
		
	}
	public Solution createSuggestion() {
		// choose room
		BoardCell myPosition = board.getCellAt(getRow(), getColumn());
		String room = board.getLegend().get(myPosition.getInitial());
		// choose weapon
		ArrayList<Card> weapons = new ArrayList<Card>();
		for (Card c : board.getWeaponCards()) {
			if (!getCards().contains(c) && !board.getDisprovedCards().contains(c.getCardName())) {
				weapons.add(c);
			}
		}
		Collections.shuffle(weapons);
		String weapon = weapons.get(0).getCardName();
		// choose person
		ArrayList<Card> people = new ArrayList<Card>();
		for (Card c : board.getPeopleCards()) {
			if (!getCards().contains(c) && !board.getDisprovedCards().contains(c.getCardName())) {
				people.add(c);
			}
		}
		Collections.shuffle(people);
		String person = people.get(0).getCardName();
		
		Solution suggestion = new Solution(person, room, weapon);
		return suggestion;
	}
	
	
	@Override
	public Card disproveSuggestion(Solution suggestion){
		ArrayList<Card> cardsToReveal = new ArrayList<Card>();
		for (Card c : getCards()) {
			if (c.getCardName().equals(suggestion.weapon) || c.getCardName().equals(suggestion.person) || c.getCardName().equals(suggestion.room)) {
				cardsToReveal.add(c);
			}
		}
		if (cardsToReveal.isEmpty()) {
			return null;
		}
		Collections.shuffle(cardsToReveal);
		return cardsToReveal.get(0);
	}

}
