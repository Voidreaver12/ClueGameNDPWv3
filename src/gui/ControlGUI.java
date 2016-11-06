package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ControlGUI extends JFrame {
	private String playerNameStr;
	private String guessPerson;
	private String guessWeapon;
	private String guessRoom;
	private String guessResponse;
	private int intRoll;
	
	
	public ControlGUI() {
		setTitle("Clue Control");
		setSize(new Dimension(800, 120));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		TurnPanel tPanel = new TurnPanel();
		add(tPanel, BorderLayout.NORTH);
		GuessPanel gPanel = new GuessPanel();
		add(gPanel, BorderLayout.CENTER);
	}
	
	// Panel for displaying whose turn it is,
	// a button for moving on to the next player,
	// and a button for making an accusation
	private class TurnPanel extends JPanel {
		private JTextField playerName;
		private JButton nextPlayer;
		private JButton accuse;
		public TurnPanel() {
			// whose turn
			JLabel whoseTurn = new JLabel("Whose turn: ");
			add(whoseTurn);
			playerName = new JTextField(playerNameStr, 10);
			playerName.setFont(new Font("SansSerif", Font.BOLD, 12));
			playerName.setEditable(false);
			add(playerName);
			// next player button
			nextPlayer = new JButton("Next Player");
			nextPlayer.addActionListener(new NextPlayer());
			add(nextPlayer);
			// accuse button
			accuse = new JButton("Make an Accusation");
			accuse.addActionListener(new Accuse());
			add(accuse);
		}
	}
	
	// Next Player button
	private class NextPlayer implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Next Player button pressed.");
		}
	}
	// Accusation button
	private class Accuse implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Make an Accusation button pressed.");
		}
	}
	
	// Panel to display the roll of the die,
	// the 3 things guessed, and the response returned for that guess
	private class GuessPanel extends JPanel {
		private JTextField roll;
		private JTextField guess;
		private JTextField response;
		public GuessPanel() {
			// roll
			JLabel label = new JLabel("Roll: ");
			roll = new JTextField(String.valueOf(intRoll), 5);
			roll.setFont(new Font("SansSerif", Font.BOLD, 12));
			roll.setEditable(false);
			add(label);
			add(roll);
			// guess
			label = new JLabel("Guess: ");
			guess = new JTextField(guessRoom + ", " + guessWeapon + ", " + guessPerson, 30);
			guess.setFont(new Font("SansSerif", Font.BOLD, 12));
			guess.setEditable(false);
			add(label);
			add(guess);
			// response
			label = new JLabel("Guess Response: ");
			response = new JTextField(guessResponse, 10);
			response.setFont(new Font("SansSerif", Font.BOLD, 12));
			response.setEditable(false);
			add(label);
			add(response);
		}
	}	
		
		
	public static void main(String[] args) {
		ControlGUI gui = new ControlGUI();
		gui.setVisible(true);

	}

}
