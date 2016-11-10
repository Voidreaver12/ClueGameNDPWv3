package gui;

import java.awt.GridLayout;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card;
import clueGame.CardType;

public class MyCardsPanel extends JPanel {
	//private String card1, card2, card3;
	private JTextArea people, rooms, weapons;
	private Set<Card> cards;
	public MyCardsPanel(Set<Card> cards) {
		this.cards = cards;
		setLayout(new GridLayout(3, 1));
		add(createPanel("People", people, CardType.PERSON));
		add(createPanel("Rooms", rooms, CardType.ROOM));
		add(createPanel("Weapons", weapons, CardType.WEAPON));
		setBorder(new TitledBorder (new EtchedBorder(), "My cards"));
	}
	
	
	private JPanel createPanel(String name, JTextArea cardDisplay, CardType cardType) {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder (new EtchedBorder(), name));
		cardDisplay = new JTextArea(3, 15);
		cardDisplay.setLineWrap(true);
		cardDisplay.setWrapStyleWord(true);
		String text = "";
		for (Card c : cards) {
			if (c.getCardType() == cardType) {
				text += c.getCardName();
				text += "\n";
			}
		}
		cardDisplay.setText(text);
		panel.add(cardDisplay);
		return panel;
	}
}
