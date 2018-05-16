import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Game implements ActionListener, KeyListener {
	
	// Global Constants
	public static final int FIELD_WIDTH = 900;
	public static final int FIELD_HEIGHT = 600;
	
	//Private Constants
	private JFrame gameFrame = new JFrame("OP Tanks V0.1.0");
	
	
	//JLable and Buttons
	public JLabel lblGameTitle, lblGameSelectorTitle;
	public JButton startGame, leaderboardMainBTN, settingsMainBTN;
	
	public static void main(String[] args) 
	{
		new Game();
	}
	
	public Game()
	{
		MenuBar();
		setUpGameFrame();
		StartScreen();
	}
	
	public void setUpGameFrame()
	{	
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setSize(FIELD_WIDTH, FIELD_HEIGHT);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setLayout(null);
		gameFrame.setResizable(false);
		gameFrame.getContentPane().setBackground(Color.WHITE);
		
		gameFrame.setVisible(true);
	}
	
	public void MenuBar()
	{
		
	}
	
	public void StartScreen()
	{
		lblGameTitle = new JLabel("OP Tanks");
		lblGameTitle.setSize(210, 65);
		lblGameTitle.setFont(new Font("Serif", Font.PLAIN, 39));
		lblGameTitle.setLocation(360, 100);
		lblGameTitle.setOpaque(false);
		lblGameTitle.setVisible(true);
		
		startGame = new JButton("Start Game");
		startGame.setSize(179, 40);
		startGame.setFont(new Font("Serif", Font.PLAIN, 25));
		startGame.setLocation(345, 170);
		startGame.setFocusable(false);
		startGame.setActionCommand("startGame");
		startGame.addActionListener(this);
		startGame.setVisible(true);
		
		leaderboardMainBTN = new JButton("LeaderBoard");
		leaderboardMainBTN.setFont(new Font("Serif", Font.PLAIN, 25));
		leaderboardMainBTN.setSize(179, 40);
		leaderboardMainBTN.setLocation(345, 215);
		leaderboardMainBTN.setFocusable(false);
		leaderboardMainBTN.setActionCommand("leaderBoard");
		//startGame.addActionListener(this);
		leaderboardMainBTN.setVisible(true);
		
		settingsMainBTN = new JButton("Settings");
		settingsMainBTN.setFont(new Font("Serif", Font.PLAIN, 25));
		settingsMainBTN.setSize(179, 40);
		settingsMainBTN.setLocation(345, 260);
		settingsMainBTN.setFocusable(false);
		settingsMainBTN.setActionCommand("settings");
		//startGame.addActionListener(this);
		settingsMainBTN.setVisible(true);
		
		//Add Items to Frame
		gameFrame.add(lblGameTitle);
		gameFrame.add(startGame);
		gameFrame.add(leaderboardMainBTN);
		gameFrame.add(settingsMainBTN);
	}
	
	public void hideStartScreen()
	{
		lblGameTitle.setVisible(false);
		startGame.setVisible(false);
		leaderboardMainBTN.setVisible(false);
		settingsMainBTN.setVisible(false);
	}
	
	public void gamemodeSelector()
	{
		lblGameSelectorTitle = new JLabel("Select Gamemode");
		lblGameSelectorTitle.setSize(300, 45);
		lblGameSelectorTitle.setFont(new Font("Serif", Font.PLAIN, 35));
		lblGameSelectorTitle.setLocation(320, 100);
		lblGameSelectorTitle.setOpaque(false);
		lblGameSelectorTitle.setVisible(true);
		
		gameFrame.add(lblGameSelectorTitle);
	}
	
	public void hideGamemodeSelector()
	{
		
	}
	
	public void playerSelector()
	{
		
	}
	
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals("startGame"))
		{
			hideStartScreen();
			gamemodeSelector();
		}
		
	}
}
