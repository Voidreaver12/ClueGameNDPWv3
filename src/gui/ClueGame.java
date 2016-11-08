package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import clueGame.Board;

public class ClueGame extends JFrame {
	public ClueGame() {
		setTitle("Clue");
		setSize(new Dimension(850, 850));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ControlPanel control = new ControlPanel();
		add(control, BorderLayout.SOUTH);
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "Legend.txt");
		board.initialize();
		add(board, BorderLayout.CENTER);
	}
	public static void main(String[] args) {
		ClueGame clue = new ClueGame();
		clue.setVisible(true);
	}

}
