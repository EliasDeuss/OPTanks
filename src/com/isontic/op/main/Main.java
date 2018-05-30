package com.isontic.op.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.Timer;

public class Main extends JFrame implements ActionListener, KeyListener
{
	// Global Constants
	public static final int FIELD_WIDTH = 900;
	public static final int FIELD_HEIGHT = 600;
	public static final int SHOOTER_SPEED = 2;
	
	//Timer
	private final int TIMER_SPEED = 10;
	private Timer timer;
	
	//Private Constants
	
	//Stat's and game settings
	private int GAMEMODE = 0; //What game mode you are in
	private int PLAYERS = 2; //How many players are in the Game
	private int MAP = 1; //What map the player is in
	private boolean playGame = false; //Players and game stats will spawn in if set to True
	private boolean tank1move = false; //Tracks it the player is moving
	private boolean tank2move = false; //Tracks it the player is moving
	
	private int PLAYER1KILLS = 0;
	private int PLAYER2KILLS = 0;
	
	//Images
	private ImageIcon imgLogoMain = new ImageIcon(getClass().getResource("logo.png"));
	private ImageIcon imgStartMain = new ImageIcon(getClass().getResource("startMain.png"));
	private ImageIcon imgLeadMain = new ImageIcon(getClass().getResource("leadMain.png"));
	private ImageIcon imgSettMain = new ImageIcon(getClass().getResource("settMain.png"));
	private ImageIcon imgSGMain = new ImageIcon(getClass().getResource("sgMain.png"));
	private ImageIcon imgNormalMain = new ImageIcon(getClass().getResource("normalMain.png"));
	private ImageIcon imgPlMain = new ImageIcon(getClass().getResource("plMain.png"));
	private ImageIcon imgOnePlMain = new ImageIcon(getClass().getResource("OnePlMain.png"));
	private ImageIcon imgTwoPlMain = new ImageIcon(getClass().getResource("TwoPlMain.png"));
	private ImageIcon imgTank1Score = new ImageIcon(getClass().getResource("tank1score.png"));
	private ImageIcon imgTank2Score = new ImageIcon(getClass().getResource("tank2score.png"));
	
	//Tank1
	private double tank1X, tank1Y; //X and Y for Tank 1
	private double tank1A = 4.7; //Angle of the tank1 (0-6)
	private boolean T1STOP = false;
	
	private Image tank1image = new ImageIcon(getClass().getResource("tank1down.png")).getImage(); //Tank1 Image
	
	private boolean tank1M = false, T1missileFired = false;
	private boolean onepressedLeft = false, onepressedRight = false, onepressedUp = false, onepressedDown = false;
	
	//Tank2
	private double tank2X, tank2Y; //X & Y for Tank 2
	private double tank2A = 1.56; //Angle of the tank2 (0-6)
	
	private Image image2 = new ImageIcon(getClass().getResource("tank2down.png")).getImage(); //Tank2 Image 
	
	private boolean twopressedLeft = false, twopressedRight = false, twopressedUp = false, twopressedDown = false;
	//Tank Size info
	private double sizeX = 1.0, sizeY = 1.0;
	private boolean controlKeyPressed = false;
	
	//JLable and Buttons
	public JLabel lblGameTitle, lblGameSelectorTitle, lblGamePlayerSTitle, lblKillsTank2, lblKillsTank1;
	public JButton startGame, leaderboardMainBTN, settingsMainBTN, normalGamemodeBTN, player1BTN, player2BTN;
	
	//Menu Bar
	private JMenuBar menuBar;
	private JMenu menu, menu2;
	private JRadioButtonMenuItem rbMenuItem1, rbMenuItem2;
	private JMenuItem menuItem, menuItem2, menuItem3;
	
	//Array List
	ArrayList<Missile1> missiles = new ArrayList<Missile1>();

	public static void main(String[] args) 
	{
		new Main();
	}
	
	public Main()
	{
		//SetUp Game
		MenuBar();
		setUpGameFrame();
		StartScreen();
		//infoBoard();
		
		
		
		//Set up / start timer
		timer = new Timer(TIMER_SPEED, this);
		//timer.setInitialDelay(TIMER_DELAY);
		timer.start();
	}
	
	public void setUpGameFrame()
	{	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(FIELD_WIDTH, FIELD_HEIGHT);
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);
		getContentPane().setBackground(Color.WHITE);
		addKeyListener(this);
		setVisible(true);
	}
	
	//Info of player kills in game
	public void infoBoard()
	{
		lblKillsTank1 = new JLabel(imgTank1Score);
		lblKillsTank1.setSize(35, 45);
		lblKillsTank1.setFont(new Font("Serif", Font.PLAIN, 39));
		lblKillsTank1.setLocation(1, 1);
		lblKillsTank1.setOpaque(false);
		lblKillsTank1.setVisible(true);
		
		lblKillsTank2 = new JLabel(imgTank2Score);
		lblKillsTank2.setSize(35, 45);
		lblKillsTank2.setFont(new Font("Serif", Font.PLAIN, 39));
		lblKillsTank2.setLocation(1, 1);
		lblKillsTank2.setOpaque(false);
		lblKillsTank2.setVisible(true);
		
		add(lblKillsTank1);
		add(lblKillsTank2);
	}
	
	public void StartScreen()
	{
		lblGameTitle = new JLabel(imgLogoMain);
		lblGameTitle.setSize(310, 70);
		lblGameTitle.setFont(new Font("Serif", Font.PLAIN, 39));
		lblGameTitle.setLocation(280, 100);
		lblGameTitle.setOpaque(false);
		lblGameTitle.setVisible(true);
		
		startGame = new JButton(imgStartMain);
		startGame.setSize(181, 46);
		startGame.setFont(new Font("Serif", Font.PLAIN, 25));
		startGame.setLocation(345, 170);
		startGame.setFocusable(false);
		startGame.setActionCommand("startGame");
		startGame.addActionListener(
				  new ActionListener() 
						{
					    public void actionPerformed(ActionEvent e) 
					    {
					    	if (e.getActionCommand().equals("startGame"))
							{
					    		hideStartScreen();
								gamemodeSelector();
							}
						}
				  }
				);
		startGame.setVisible(true);
		
		leaderboardMainBTN = new JButton(imgLeadMain);
		leaderboardMainBTN.setFont(new Font("Serif", Font.PLAIN, 25));
		leaderboardMainBTN.setSize(181, 46);
		leaderboardMainBTN.setLocation(345, 219);
		leaderboardMainBTN.setFocusable(false);
		leaderboardMainBTN.setActionCommand("leaderBoard");
		//startGame.addActionListener(this);
		leaderboardMainBTN.setVisible(true);
		
		settingsMainBTN = new JButton(imgSettMain);
		settingsMainBTN.setFont(new Font("Serif", Font.PLAIN, 25));
		settingsMainBTN.setSize(179, 40);
		settingsMainBTN.setLocation(345, 269);
		settingsMainBTN.setFocusable(false);
		settingsMainBTN.setActionCommand("settings");
		//startGame.addActionListener(this);
		settingsMainBTN.setVisible(true);
		
		//Add Items to Frame
		add(lblGameTitle);
		add(startGame);
		add(leaderboardMainBTN);
		add(settingsMainBTN);
		
		repaint();
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
		lblGameSelectorTitle = new JLabel(imgSGMain);
		lblGameSelectorTitle.setSize(300, 45);
		lblGameSelectorTitle.setFont(new Font("Serif", Font.PLAIN, 35));
		lblGameSelectorTitle.setLocation(290, 100);
		lblGameSelectorTitle.setOpaque(false);
		lblGameSelectorTitle.setVisible(true);
		
		normalGamemodeBTN = new JButton(imgNormalMain);
		normalGamemodeBTN.setSize(180, 40);
		normalGamemodeBTN.setFont(new Font("Serif", Font.PLAIN, 25));
		normalGamemodeBTN.setLocation(345, 170);
		normalGamemodeBTN.setFocusable(false);
		normalGamemodeBTN.setActionCommand("normalGame");
		normalGamemodeBTN.addActionListener(
				  new ActionListener() 
						{
					    public void actionPerformed(ActionEvent e) 
					    {
					    	if (e.getActionCommand().equals("normalGame"))
							{
					    		GAMEMODE = 0;
								
								System.out.println("Gamemode set to " + GAMEMODE);
								
								hideGamemodeSelector();
								playGame = true;
								//playerSelector();
							}
						}
				  }
				);
		normalGamemodeBTN.setVisible(true);
		
		add(lblGameSelectorTitle);
		add(normalGamemodeBTN);
		repaint();
	}
	
	public void hideGamemodeSelector()
	{
		lblGameSelectorTitle.setVisible(false);
		normalGamemodeBTN.setVisible(false);
		
		setUpMap1();
		
	}
	
	public void playerSelector()
	{
		lblGamePlayerSTitle = new JLabel(imgPlMain);
		lblGamePlayerSTitle.setSize(300, 45);
		lblGamePlayerSTitle.setFont(new Font("Serif", Font.PLAIN, 35));
		lblGamePlayerSTitle.setLocation(290, 100);
		lblGamePlayerSTitle.setOpaque(false);
		lblGamePlayerSTitle.setVisible(true);
		
		player1BTN = new JButton(imgOnePlMain);
		player1BTN.setSize(179, 40);
		player1BTN.setFont(new Font("Serif", Font.PLAIN, 25));
		player1BTN.setLocation(345, 170);
		player1BTN.setFocusable(false);
		player1BTN.setActionCommand("oneplayerGame");
		player1BTN.addActionListener(
				  new ActionListener() 
						{
					    public void actionPerformed(ActionEvent e) 
					    {
					    	if (e.getActionCommand().equals("oneplayerGame"))
							{
					    		PLAYERS = 1;
								
								System.out.println("Selected " + PLAYERS + " player('s)");
							}
						}
				  }
				);
		player1BTN.setVisible(true);
		
		player2BTN = new JButton(imgTwoPlMain);
		player2BTN.setSize(179, 40);
		player2BTN.setFont(new Font("Serif", Font.PLAIN, 25));
		player2BTN.setLocation(345, 220);
		player2BTN.setFocusable(false);
		player2BTN.setActionCommand("twoplayersGame");
		player2BTN.addActionListener(
				  new ActionListener() 
						{
					    public void actionPerformed(ActionEvent e) 
					    {
					    	if (e.getActionCommand().equals("twoplayersGame"))
							{
					    		PLAYERS = 2;
								
								System.out.println("Selected " + PLAYERS + " player('s)");
							}
						}
				  }
				);
		player2BTN.setVisible(true);
		
		add(lblGamePlayerSTitle);
		add(player1BTN);
		add(player2BTN);
	}
	
	public void hidePlayerSelector()
	{
		lblGamePlayerSTitle.setVisible(false);
		player1BTN.setVisible(false);
		player2BTN.setVisible(false);
	}
	
	
	
	public void setUpMap1()
	{
		setUpTank1();
		setUpTank2();
		//infoBoard();
		
		repaint();
	}
	

	// Set the size and starting position of the player's tank
	public void setUpTank1()
	{
		tank1move = true;
		// Set the shooter's initial position 
		tank1X = 41;
		tank1Y = 280;
		
		tank1move = false;

	}
	
	public void setUpTank2()
	{
		tank2move = true;
		// Set the shooter's initial position 
		tank2X = 850;
		tank2Y = 280;
		
		tank2move = false;
	}
	
	public void keyPressed(KeyEvent event) {
		// TODO Auto-generated method stub
		int key = event.getKeyCode();
		
		//Tank1
		if (key == KeyEvent.VK_LEFT) // LEFT arrow
		{
			onepressedLeft = true;
			tank1move = true;
		}
		
		if (key == KeyEvent.VK_RIGHT) // RIGHT arrow
		{
			onepressedRight = true;
			tank1move = true;
		}
		
		if (key == KeyEvent.VK_UP) // Up arrow
		{
			onepressedUp = true;
			tank1move = true;
		}
		if (key == KeyEvent.VK_DOWN) // RIGHT arrow
		{
			onepressedDown = true;
			tank1move = true;
		}
		
		if (key == 77)
		{
			tank1M = true; 
			T1missileFired = false;
		}
		
		//Tank2
		if (key == 65) // A
		{
			twopressedLeft = true;
			tank2move = true;
		}
		if (key == 68) // S
		{
			twopressedRight = true;
			tank2move = true;
		}

		if (key == 87) // W
		{
			twopressedUp = true;
			tank2move = true;
		}
		if (key == 83) // D
		{
			twopressedDown = true;
			tank2move = true;
		}
		
	}

	public void keyReleased(KeyEvent event) {
		// TODO Auto-generated method stub
		int key = event.getKeyCode();

		if (key == KeyEvent.VK_CONTROL) // CONTROL key
			controlKeyPressed = false;
		
		//Tank1
		if (key == KeyEvent.VK_LEFT) // LEFT arrow
		{
			onepressedLeft = false;
			tank1move = false;
		}
		if (key == KeyEvent.VK_RIGHT) // RIGHT arrow
		{
			onepressedRight = false;
			tank1move = false;
		}
		
		if (key == KeyEvent.VK_UP) // Up arrow
		{
			onepressedUp = false;
			tank1move = false;
		}
		if (key == KeyEvent.VK_DOWN) // RIGHT arrow
		{
			onepressedDown = false;
			tank1move = false;
		}
		
		if (key == 77)
		{
			tank1M = false; 
			T1missileFired = true;
		}
		
		//Tank2
		if (key == 65) // A
		{
			twopressedLeft = false;
			tank2move = false;
		}
		if (key == 68) // S
		{
			twopressedRight = false;
			tank2move = false;
		}
	
		if (key == 87) // W
		{
			twopressedUp = false;
			tank2move = false;
		}
		if (key == 83) // D
		{
			twopressedDown = false;
			tank2move = false;
		}
		
		
		
		if (key == KeyEvent.VK_SPACE) // SPACE bar
		{
//			onepressedSpace = false;
//			onemissileFired = false;
		}
	}

	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void actionPerformed(ActionEvent event) {
		
		
		//Tank1
		
		if (onepressedLeft && tank1X > 4)
		{
			tank1A = tank1A -0.03;
		}
		if (onepressedRight && tank1X < FIELD_WIDTH  - 6 - 4)
		{
			tank1A = tank1A +0.03;
		}
		
		//Forwards
		if (onepressedUp && tank1X > 4)
		{	
			tank1Y = tank1Y + (SHOOTER_SPEED) * (Math.cos(tank1A));
			tank1X = tank1X - (SHOOTER_SPEED) * (Math.sin(tank1A));
		}
		
		//Backwards
		if (onepressedDown && tank1X < FIELD_WIDTH - 6 - 4)
		{
			tank1Y = tank1Y - (SHOOTER_SPEED) * (Math.cos(tank1A));
			tank1X = tank1X + (SHOOTER_SPEED) * (Math.sin(tank1A));
		}
		
		//Tank2
		
		if (twopressedLeft && tank2X > 4)
		{
			tank2A = tank2A -0.03;
		}
		if (twopressedRight && tank2X < FIELD_WIDTH - 6 - 4)
		{
			tank2A = tank2A +0.03;
		}
				
		//Forwards
		if (twopressedUp && tank2X > 4)
		{	
			tank2Y = tank2Y + (SHOOTER_SPEED) * (Math.cos(tank2A));
			tank2X = tank2X - (SHOOTER_SPEED) * (Math.sin(tank2A));
		}
			
		//Backwards
		if (twopressedDown && tank2X < FIELD_WIDTH  - 6 - 4)
		{
			tank2Y = tank2Y - (SHOOTER_SPEED) * (Math.cos(tank2A));
			tank2X = tank2X + (SHOOTER_SPEED) * (Math.sin(tank2A));
		}
		
		if (tank1M && !T1missileFired && T1STOP == false)
		{
			// Set the starting position of the missile being launched 
			double x = tank1X;
			double y = tank1Y - 61;
			double a = tank1A;

			// Create a new 'Missile' object and add it to the 'missiles' ArrayList 
			missiles.add(new Missile1(x, y, a));

			T1missileFired = true;
		}
		
		//Move Missiles
		
		// Move the existing missiles up the playing field
		for (int j = 0; j < missiles.size(); j++)
		{
			Missile1 missile = missiles.get(j);
			missile.moveMissile();

			// If the missile gets past the top of the playing field, remove it
			if (missile.getY() < 0 - missile.getHeight())
			{
				getContentPane().remove(missile.getMissileImage());
				missiles.remove(j);
			}
		}
		
		//Draws the Missile
		for (int i = 0; i < missiles.size(); i++)
		{
			Missile1 missile = missiles.get(i);
			JLabel mLabel = missile.getMissileImage();
			mLabel.setLocation(missile.getX(), missile.getY());
			mLabel.setSize(missile.getWidth(), missile.getHeight());
			add(mLabel);
		}
		
		//Limits missiles to 5
		if (missiles.size() >= 5)
		{
		//	T1STOP = true;
		}
		
		//Checks if missile is out of bounds
		for (int i = 0; i < missiles.size(); i++)
		{
			//if (missiles.getY() < 0 - missiles.getHeight())
		}
		
		checkCollisions();
		
		Toolkit.getDefaultToolkit().sync();
		
		if (tank1A > 2 * Math.PI) {
			tank1A = 0;
	      } else if (tank1A < 0) {
	    	  tank1A = 2 * Math.PI;}
		
		if (playGame)
		{
			//if (tank1move == true || tank2move == true)
			//{
				repaint();
			//}
		}
	}
	
	public void checkCollisions()
	{
		
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
						    		dispose();
									System.exit(0);
								}
						    }
						  }
						);
		menu.add(menuItem);
		
		
		setJMenuBar(menuBar);
		menu2.setVisible(false);
		
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponents(g);
	}

	public void paint(Graphics g)
	{
		paintComponent(g);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Graphics2D g2D = (Graphics2D) g;
	
		AffineTransform at = AffineTransform.getRotateInstance(tank1A, tank1X, tank1Y);
		AffineTransform at2 = AffineTransform.getRotateInstance(tank2A, tank2X, tank2Y);
		
		at.translate(tank1X - 20, tank1Y - 24);
		at2.translate(tank2X - 20, tank2Y - 24);

		at.scale(sizeX, sizeY);
		at2.scale(sizeX, sizeY);

		// Draw the updated image
		if (playGame)
		{
			g2D.drawImage(tank1image, at, this);
			g2D.drawImage(image2, at2, this);
		}

		Toolkit.getDefaultToolkit().sync();
	}
}

