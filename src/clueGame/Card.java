package clueGame;

public class Card {
	private String cardName;
	private CardType cardType;
	public boolean equals(Card c){
		if (c.getCardName().equals(cardName)){
			if (c.getCardType() == cardType){
				return true;
			}
		}
		return false;
	}
	public Card() {
		// TODO Auto-generated constructor stub
	}
	public Card(String name, CardType type) {
		cardName = name;
		cardType = type;
	}
	
	public String getCardName(){
		return cardName;
	}
	public CardType getCardType(){
		return cardType;
	}
}
