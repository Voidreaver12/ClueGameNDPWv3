package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Board;
import clueGame.Solution;

public class ClueGame extends JFrame {
	Board board = Board.getInstance();
	ControlPanel control;
	public ClueGame() {
		setTitle("Clue");
		setSize(new Dimension(850, 850));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		control = new ControlPanel();
		add(control, BorderLayout.SOUTH);
		
		board.setConfigFiles("ClueLayout.csv", "Legend.txt");
		board.initialize();
		add(board, BorderLayout.CENTER);
		
		//DetectiveNotes detNotes = new DetectiveNotes();
		//add(detNotes, BorderLayout.EAST);
		JMenuBar bar = new JMenuBar();
		setJMenuBar(bar);
		JMenu menu = new JMenu("File");
		menu.add(detectiveNotes());
		menu.add(createFileExitItem());
		bar.add(menu);
		
		MyCardsPanel cards = new MyCardsPanel(board.getPlayers().get(0).getCards());
		add(cards, BorderLayout.EAST);
	}
	/*
	private JMenu fileMenu(){
		DetectiveNotes detNotes = new DetectiveNotes();
		JMenuBar bar = new JMenuBar();
		setJMenuBar(bar);
		JMenu menu = new JMenu("File");
		menu.add(detNotes);
		menu.add(createFileExitItem());
		bar.add(menu);
		return menu;
	}
	*/
	private JMenuItem createFileExitItem(){
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}
	public void updateControl(Solution suggestion) {
		control.updateGuess(suggestion);
	}
	public static void main(String[] args) {
		ClueGame clue = new ClueGame();
		clue.setVisible(true);
		JOptionPane.showMessageDialog(clue,"You are Trump! Press Next Player to begin playing.", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public JMenuItem detectiveNotes () {
		JMenuItem item = new JMenuItem("Notes");
		final JDialog notes = new JDialog();
		notes.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		notes.setLayout(new GridLayout(3,3));
		notes.setBounds(1000, 200, 600, 600);


		JPanel people = new JPanel();
		people.setLayout(new GridLayout(3,2));
		people.add(new JRadioButton("Trump"));
		people.add(new JRadioButton("Hillary"));
		people.add(new JRadioButton("Bernie"));
		people.add(new JRadioButton("Miley"));
		people.add(new JRadioButton("Kanye"));
		people.add(new JRadioButton("Gaga"));
		people.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		notes.add(people);

		JPanel rooms = new JPanel();
		rooms.setLayout(new GridLayout(5,2));
		rooms.add(new JRadioButton("Balcony"));
		rooms.add(new JRadioButton("Gym"));
		rooms.add(new JRadioButton("Art Gallery"));
		rooms.add(new JRadioButton("Computer Lab"));
		rooms.add(new JRadioButton("Bathroom"));
		rooms.add(new JRadioButton("Game Room"));
		rooms.add(new JRadioButton("Library"));
		rooms.add(new JRadioButton("Bar"));
		rooms.add(new JRadioButton("Office"));
		rooms.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		notes.add(rooms);


		JPanel weapons = new JPanel();
		weapons.setLayout(new GridLayout(3,2));
		weapons.add(new JRadioButton("Handsaw"));
		weapons.add(new JRadioButton("Scooter"));
		weapons.add(new JRadioButton("Fork"));
		weapons.add(new JRadioButton("Belt"));
		weapons.add(new JRadioButton("Hamster"));
		weapons.add(new JRadioButton("Sponge"));
		weapons.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		notes.add(weapons);


		JComboBox<String> persong = new JComboBox<String>();
		persong.addItem("Trump");
		persong.addItem("Hillary");
		persong.addItem("Bernie");
		persong.addItem("Miley");
		persong.addItem("Kanye");
		persong.addItem("Gaga");
		persong.setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));
		notes.add(persong);


		JComboBox<String> roomg = new JComboBox<String>();
		roomg.addItem("Balcony");
		roomg.addItem("Gym");
		roomg.addItem("Art Gallery");
		roomg.addItem("Computer Lab");
		roomg.addItem("Bathroom");
		roomg.addItem("Game Room");
		roomg.addItem("Library");
		roomg.addItem("Bar");
		roomg.addItem("Office");
		roomg.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
		notes.add(roomg);


		JComboBox<String> weapg = new JComboBox<String>();
		weapg.addItem("Handsaw");
		weapg.addItem("Scooter");
		weapg.addItem("Fork");
		weapg.addItem("Belt");
		weapg.addItem("Hamster");
		weapg.addItem("Sponge");
		weapg.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
		notes.add(weapg);
		
			class MenuItemListener implements ActionListener {

			public void actionPerformed(ActionEvent e)

			{

			notes.setVisible(true);

			}

			}
			item.addActionListener(new MenuItemListener());
			
			return item;		
	}
	

}
