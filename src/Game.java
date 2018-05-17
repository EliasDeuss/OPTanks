import java.awt.Color;
import java.awt.Font;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

public class Game implements ActionListener, KeyListener {
	
	// Global Constants
	public static final int FIELD_WIDTH = 900;
	public static final int FIELD_HEIGHT = 600;
	
	//Private Constants
	private JFrame gameFrame = new JFrame("OP Tanks V0.1.0");
	
	//Stat's and game settings
	private int GAMEMODE = 0; //What game mode you are in
	private int PLAYERS = 1; //How many players are in the Game
	private int MAP = 1; //What map the player is in
	
	
	private int PLAYER1KILLS = 0;
	private int PLAYER2KILLS = 0;
	
	
	//JLable and Buttons
	public JLabel lblGameTitle, lblGameSelectorTitle, lblGamePlayerSTitle;
	public JButton startGame, leaderboardMainBTN, settingsMainBTN, normalGamemodeBTN, player1BTN, player2BTN;
	
	private JMenuBar menuBar;
	private JMenu menu, menu2;
	private JRadioButtonMenuItem rbMenuItem1, rbMenuItem2;
	private JMenuItem menuItem, menuItem2;
	
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

		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the first menu.
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription("File");
		menuBar.add(menu);
		
		menu2 = new JMenu("Difficulty");
		menu2.setMnemonic(KeyEvent.VK_A);
		menu2.getAccessibleContext().setAccessibleDescription("Game Difficulty");
		menuBar.add(menu2);		
		
		//a group of JMenuItems
		menuItem = new JMenuItem("Reset");
		menuItem.setActionCommand("reset");
		
		menu.add(menuItem);
		menu.addSeparator();
		
		menuItem2 = new JMenuItem("Reset HighScore");
		menuItem2.setActionCommand("resetScore");
		menuItem2.addActionListener(
		  new ActionListener() 
				{
			    public void actionPerformed(ActionEvent e) 
			    {
			    	if (e.getActionCommand().equals("resetScore"))
					{
			    		
					}
				}
		  }
		);
		menu.add(menuItem2);
	
		//Difficulty Selector
		menu.addSeparator();
		
		ButtonGroup group = new ButtonGroup();
		rbMenuItem1 = new JRadioButtonMenuItem("Normal");
		rbMenuItem1.setSelected(true);
		rbMenuItem1.setMnemonic(KeyEvent.VK_R);
		group.add(rbMenuItem1);
			
		menu2.add(rbMenuItem1);

		rbMenuItem2 = new JRadioButtonMenuItem("Hard");
		rbMenuItem2.setMnemonic(KeyEvent.VK_O);
		group.add(rbMenuItem2);
		rbMenuItem2.setActionCommand("hard");
		menu2.add(rbMenuItem2);
		
		//Exit Menu
		//menu.addSeparator();
		menuItem = new JMenuItem("Exit");
		menuItem.setActionCommand("Exit");
		menuItem.addActionListener(
						  new ActionListener() 
						  {
						    public void actionPerformed(ActionEvent e)
						    {
						    	if (e.getActionCommand().equals("Exit"))
								{
						    		gameFrame.dispose();
									System.exit(0);
								}
						    }
						  }
						);
		menu.add(menuItem);
		gameFrame.setJMenuBar(menuBar);
		rbMenuItem2.setEnabled(true);
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
		
		normalGamemodeBTN = new JButton("Normal");
		normalGamemodeBTN.setSize(179, 40);
		normalGamemodeBTN.setFont(new Font("Serif", Font.PLAIN, 25));
		normalGamemodeBTN.setLocation(345, 170);
		normalGamemodeBTN.setFocusable(false);
		normalGamemodeBTN.setActionCommand("normalGame");
		normalGamemodeBTN.addActionListener(this);
		normalGamemodeBTN.setVisible(true);
		
		gameFrame.add(lblGameSelectorTitle);
		gameFrame.add(normalGamemodeBTN);
	}
	
	public void hideGamemodeSelector()
	{
		lblGameSelectorTitle.setVisible(false);
		normalGamemodeBTN.setVisible(false);
	}
	
	public void playerSelector()
	{
		lblGamePlayerSTitle = new JLabel("How many Players?");
		lblGamePlayerSTitle.setSize(300, 45);
		lblGamePlayerSTitle.setFont(new Font("Serif", Font.PLAIN, 35));
		lblGamePlayerSTitle.setLocation(310, 100);
		lblGamePlayerSTitle.setOpaque(false);
		lblGamePlayerSTitle.setVisible(true);
		
		player1BTN = new JButton("One Player");
		player1BTN.setSize(179, 40);
		player1BTN.setFont(new Font("Serif", Font.PLAIN, 25));
		player1BTN.setLocation(345, 170);
		player1BTN.setFocusable(false);
		player1BTN.setActionCommand("oneplayerGame");
		player1BTN.addActionListener(this);
		player1BTN.setVisible(true);
		
		player2BTN = new JButton("Two Player's");
		player2BTN.setSize(179, 40);
		player2BTN.setFont(new Font("Serif", Font.PLAIN, 25));
		player2BTN.setLocation(345, 220);
		player2BTN.setFocusable(false);
		player2BTN.setActionCommand("twoplayersGame");
		player2BTN.addActionListener(this);
		player2BTN.setVisible(true);
		
		gameFrame.add(lblGamePlayerSTitle);
		gameFrame.add(player1BTN);
		gameFrame.add(player2BTN);
	}
	
	public void hidePlayerSelector()
	{
		lblGamePlayerSTitle.setVisible(false);
		player1BTN.setVisible(false);
		player2BTN.setVisible(false);
	}
	
	public void map1()
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
		
		if (event.getActionCommand().equals("normalGame"))
		{
			GAMEMODE = 0;
			
			System.out.println("Gamemode set to " + GAMEMODE);
			
			hideGamemodeSelector();
			playerSelector();
		}
		
		if (event.getActionCommand().equals("oneplayerGame"))
		{
			PLAYERS = 1;
			
			System.out.println("Selected " + PLAYERS + " player('s)");
			
		}
		
		if (event.getActionCommand().equals("twoplayersGame"))
		{
			PLAYERS = 2;
			
			System.out.println("Selected " + PLAYERS + " player('s)");
			
		}
		
	}
}
