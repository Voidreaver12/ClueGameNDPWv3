package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

public class HumanPlayer extends Player{

	public HumanPlayer(String name, int r, int c, Color color) {
		super(name, r, c, color);
	}

	@Override
	public Solution createSuggestion() {
		// TODO Auto-generated method stub
		return null;
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
