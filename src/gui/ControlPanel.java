package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Board;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class ControlPanel extends JPanel {
	private String playerNameStr;
	private String guessPerson;
	private String guessWeapon;
	private String guessRoom;
	private String guessResponse;
	private int intRoll;
	private JTextField playerName;
	private JButton nextPlayer;
	private JButton accuse;
	private JTextField roll;
	private JTextField guess;
	private JTextField response;
	
	public ControlPanel() {
		setLayout(new GridLayout(2, 1));
		add(createTurnPanel());
		add(createGuessPanel());
	}
	
	// Panel for displaying whose turn it is,
	// a button for moving on to the next player,
	// and a button for making an accusation
	private JPanel createTurnPanel() {
		JPanel panel = new JPanel();
		// whose turn
		JLabel whoseTurn = new JLabel("Whose turn: ");
		panel.add(whoseTurn);
		playerName = new JTextField(playerNameStr, 10);
		playerName.setFont(new Font("SansSerif", Font.BOLD, 12));
		playerName.setEditable(false);
		panel.add(playerName);
		// next player button
		nextPlayer = new JButton("Next Player");
		nextPlayer.addActionListener(new NextPlayer());
		panel.add(nextPlayer);
		// accuse button
		accuse = new JButton("Make an Accusation");
		accuse.addActionListener(new Accuse());
		panel.add(accuse);
		//updateTurn();
		return panel;
	}
	
	// Next Player button
	private class NextPlayer implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Board board = Board.getInstance();
			if (board.turnInProgress()) {
				// display error
			}
			else {
				board.incrementTurn();
				Random random = new Random();
				intRoll = random.nextInt(6) + 1;
				updateTurn();
				updateRoll();
				Player current = board.getPlayers().get(board.getTurn());
				
				if (current instanceof ComputerPlayer) {
					board.clearTargets();
					board.calcTargets(board.getCellAt(current.getRow(), current.getColumn()), intRoll);
					current.move(((ComputerPlayer) current).pickLocation(board.getTargets()));
					board.repaint();
					if (board.getCellAt(current.getRow(), current.getColumn()).isRoom()) {
						Solution suggestion = current.createSuggestion();
						updateGuess(suggestion);
					}
					else {
						Solution suggestion = new Solution();
						updateGuess(suggestion);
					}
					
				}
				else if (current instanceof HumanPlayer) {
					board.clearTargets();
					board.calcTargets(board.getCellAt(current.getRow(), current.getColumn()), intRoll);
					Solution suggestion = new Solution();
					updateGuess(suggestion);
					board.doPlayerTurn();
					board.repaint();
				}
				
			}
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
	private JPanel createGuessPanel() {
		JPanel panel = new JPanel();
		// roll
		JLabel label = new JLabel("Roll: ");
		roll = new JTextField(String.valueOf(intRoll), 5);
		roll.setFont(new Font("SansSerif", Font.BOLD, 12));
		roll.setEditable(false);
		panel.add(label);
		panel.add(roll);
		// guess
		label = new JLabel("Guess: ");
		guess = new JTextField(guessRoom + ", " + guessWeapon + ", " + guessPerson, 30);
		guess.setFont(new Font("SansSerif", Font.BOLD, 12));
		guess.setEditable(false);
		panel.add(label);
		panel.add(guess);
		// response
		label = new JLabel("Guess Response: ");
		response = new JTextField(guessResponse, 10);
		response.setFont(new Font("SansSerif", Font.BOLD, 12));
		response.setEditable(false);
		panel.add(label);
		panel.add(response);
		//updateGuess();
		//updateRoll();
		return panel;
	}	
		
	public void updateGuess(Solution suggestion) {
		guess.setText(suggestion.room + ", " + suggestion.weapon + ", " + suggestion.person);
	}
	
	public void updateRoll() {
		roll.setText(String.valueOf(intRoll));
	}
	public void updateTurn() {
		playerName.setText(Board.getInstance().getPlayers().get(Board.getInstance().getTurn()).getPlayerName());
	}
	
}
