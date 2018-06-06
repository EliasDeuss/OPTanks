package com.isontic.op.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
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
	public static final int SHOOTER_SPEED = 1;
	
	
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
	private ImageIcon map = new ImageIcon(getClass().getResource("map.png")); //Map Image 
	
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
	private File file = new File("src/com/isontic/op/main/map.png");
	private BufferedImage image;
	
	//Power ups
	private int powerU = 1; //What powerup is coming next
	private int powerUClock = 0;
	
	//Lazer
	private double lazer1X = 0;
	private double lazer1Y = 0;
	private int lazerT = 0;
	private boolean lazer1F = false, lazer1S = false;
	private Image lazer1image = new ImageIcon(getClass().getResource("lazer.png")).getImage(); //Lazer1 Image
	
	//Tank1
	private double tank1X = 400, tank1Y = 200; //X and Y for Tank 1
	private double tank1A = 4.7; //Angle of the tank1 (0-6)
	private boolean T1STOP = false;
	
	private Image tank1image = new ImageIcon(getClass().getResource("tank1down.png")).getImage(); //Tank1 Image
	
	private boolean tank1M = false, T1missileFired = false;
	private boolean onepressedLeft = false, onepressedRight = false, onepressedUp = false, onepressedDown = false;
	private int T1TIMEL = 0;
	
	//Color Check for Tank 1 A
	private int tank1checkA = 255;
	
	//Tank2
	private double tank2X, tank2Y; //X & Y for Tank 2
	private double tank2A = 1.56; //Angle of the tank2 (0-6)
	
	private Image image2 = new ImageIcon(getClass().getResource("tank2down.png")).getImage(); //Tank2 Image 
	
	private boolean tank2M = false, T2missileFired = false, T2STOP = false;
	private boolean twopressedLeft = false, twopressedRight = false, twopressedUp = false, twopressedDown = false;
	//Tank Size info
	private double sizeX = 1.0, sizeY = 1.0;
	private boolean controlKeyPressed = false;
	
	//JLable and Buttons
	public JLabel lblGameTitle, lblGameSelectorTitle, lblGamePlayerSTitle, lblKillsTank2, lblKillsTank1, lblMap, mLabel, lblKillsTank1img, lblKillsTank2img;
	public JButton startGame, leaderboardMainBTN, settingsMainBTN, normalGamemodeBTN, player1BTN, player2BTN;
	
	//Menu Bar
	private JMenuBar menuBar;
	private JMenu menu, menu2;
	private JRadioButtonMenuItem rbMenuItem1, rbMenuItem2;
	private JMenuItem menuItem, menuItem2, menuItem3;
	
	//Array List
	ArrayList<Missile1> missile1s = new ArrayList<Missile1>();
	ArrayList<Missile2> missile2s = new ArrayList<Missile2>();
	ArrayList<LazerBox> lBoxes = new ArrayList<LazerBox>();
	
	ArrayList<test> tests = new ArrayList<test>();
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
		infoBoard();
		
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		lblKillsTank1img = new JLabel(imgTank1Score);
		lblKillsTank1img.setSize(35, 45);
		lblKillsTank1img.setFont(new Font("Serif", Font.PLAIN, 39));
		lblKillsTank1img.setLocation(15, 504);
		lblKillsTank1img.setOpaque(false);
		lblKillsTank1img.setVisible(true);
		
		lblKillsTank1 = new JLabel("Kills: " + PLAYER1KILLS);
		lblKillsTank1.setSize(85, 30);
		lblKillsTank1.setFont(new Font("Serif", Font.PLAIN, 25));
		lblKillsTank1.setLocation(50, 508);
		lblKillsTank1.setForeground(Color.white);
		lblKillsTank1.setVisible(true);
		
		lblKillsTank2img = new JLabel(imgTank2Score);
		lblKillsTank2img.setSize(35, 45);
		lblKillsTank2img.setFont(new Font("Serif", Font.PLAIN, 39));
		lblKillsTank2img.setLocation(135, 504);
		lblKillsTank2img.setOpaque(false);
		lblKillsTank2img.setVisible(true);
		
		lblKillsTank2 = new JLabel("Kills: " + PLAYER2KILLS);
		lblKillsTank2.setSize(85, 30);
		lblKillsTank2.setFont(new Font("Serif", Font.PLAIN, 25));
		lblKillsTank2.setLocation(175, 508);
		lblKillsTank2.setForeground(Color.white);
		lblKillsTank2.setVisible(true);
		
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
	
	public void setUpMap1()
	{
		setUpTank1();
		setUpTank2();
		//infoBoard();
		
		if (MAP == 1)
		{
			setContentPane(new JLabel(new ImageIcon(getClass().getResource("map.png"))));
			setVisible(true);
		}
		
		if (MAP == 2)
		{
			
		}
		
		add(lblKillsTank1);
		add(lblKillsTank1img);
		add(lblKillsTank2);
		add(lblKillsTank2img);
		
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
		tank2Y = 95;
		
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
		
		if (key == 81) // Q
		{
			tank2M = true; 
			T2missileFired = false;
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
		
		if (key == 81) // Q
		{
			tank2M = false; 
			T2missileFired = true;
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
		
		//Power Ups
		
		//Tracking Missiles
		if (playGame == true)
			powerUClock = powerUClock + 1;
		
		if (powerUClock >= 1600 && powerU == 1)
		{
			powerUClock = 0; // Resets clock
			powerU = 0;
			lBoxes.add(new LazerBox(20, 25));
			
			System.out.println("Spawned In Box");
		}
		
		//Draws the TrackingMBox
		for (int i = 0; i < lBoxes.size(); i++)
		{
			LazerBox boxs = lBoxes.get(i);
			mLabel = boxs.getBoxImage();
			mLabel.setLocation(boxs.getX(), boxs.getY());
			mLabel.setSize(boxs.getWidth(), boxs.getHeight());
			add(mLabel);
		}
		
		//Tank1
		
		if (onepressedLeft && tank1X > 4)
		{
			tank1A = tank1A -0.05;
		}
		if (onepressedRight && tank1X < FIELD_WIDTH  - 6 - 4)
		{
			tank1A = tank1A +0.05;
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
			tank2A = tank2A -0.05;
		}
		if (twopressedRight && tank2X < FIELD_WIDTH - 6 - 4)
		{
			tank2A = tank2A +0.05;
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
		
		//Tank1 Missile
		if (lazer1F == false)
		{
			if (tank1M && !T1missileFired && T1STOP == false)
		
			{
				// Set the starting position of the missile being launched 
				double x = tank1X - 7;
				double y = tank1Y - 45 ;//- 61;
				double a = tank1A;

				// Create a new 'Missile' object and add it to the 'missile1s' ArrayList 
				missile1s.add(new Missile1(x, y, a));

				T1missileFired = true;
			}
		} 
		
		if (lazer1F == true)
		{
			if (tank1M == true)
			{
				lazer1S = true;
				
			}
		}
		
		//Tank2 Missile
		if (tank2M && !T2missileFired && T2STOP == false)
		{
			// Set the starting position of the missile being launched 
			double x = tank2X - 4;
			double y = tank2Y - 45;
			double a = tank2A;

			// Create a new 'Missile' object and add it to the 'missile1s' ArrayList 
			missile2s.add(new Missile2(x, y, a));

			T2missileFired = true;
		}
		
		// Move the existing missile1 up the playing field
		for (int j = 0; j < missile1s.size(); j++)
		{
			Missile1 missile = missile1s.get(j);
			missile.moveMissile();

			// If the missile gets past the top of the playing field, remove it
			if (missile.getY() < 0 - missile.getHeight())
			{
				getContentPane().remove(missile.getMissileImage());
				missile1s.remove(j);
			}
		}
		
		// Move the existing missile2 up the playing field
		for (int j = 0; j < missile2s.size(); j++)
		{
			Missile2 missile = missile2s.get(j);
			missile.moveMissile();

			// If the missile gets past the top of the playing field, remove it
			if (missile.getY() < 0 - missile.getHeight())
			{
				getContentPane().remove(missile.getMissileImage());
				missile2s.remove(j);
			}
		}
		
		//Draws the Missile for Tank 1
		for (int i = 0; i < missile1s.size(); i++)
		{
			Missile1 missile = missile1s.get(i);
			mLabel = missile.getMissileImage();
			mLabel.setLocation(missile.getX(), missile.getY());
			mLabel.setSize(missile.getWidth(), missile.getHeight());
			add(mLabel);
		}
		
		//Draws the Missile for Tank 2
		for (int i = 0; i < missile2s.size(); i++)
		{
			Missile2 missile = missile2s.get(i);
			JLabel mLabel2 = missile.getMissileImage();
			mLabel2.setLocation(missile.getX(), missile.getY());
			mLabel2.setSize(missile.getWidth(), missile.getHeight());
			add(mLabel2);
		}
		
		//Checks if missile is out of bounds
		for (int i = 0; i < missile1s.size(); i++)
		{
			
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
		
		lazer1X = tank1X;
		lazer1Y = tank1Y;
		
		//Keeps lazer out for only 2 seconds
		if (lazer1F && lazer1S == true)
		{
			lazerT = lazerT + 1;
			
			if (lazerT >= 80)
			{
				lazer1F = false;
				lazer1S = false;
			}
		}
	}
	
	public void checkCollisions()
	{
		//Checks if player is on black
		for (int i = 0; i < 1; i++)
		{
			if (onepressedUp == true)
			{
				
				// The the pixel color information at 20, 20			           	 			    	 
		    	   double tankX = tank1X;
		    	   double tankY = tank1Y;
		    	   
		    	   tankX = tankX + (Math.cos(tank1A)) * (29/2);
		    	   //System.out.println(tank1A);
		    	   tankY = tankY + (Math.cos(tank1A)) * (55/2);
		    	   
		    	   
		    	   // Getting pixel color by position x and y 
		    	   int clr=  image.getRGB( (int) tankX + 15, (int) tankY -50); 
		    	   
		    	   
		    	   //test
					double x = (int) tankX + 15;
					double y = (int) tankY - 50;
					//tests.add(new test(x, y));
		    	   //
				   
		    	   
		    	   int  blue  =  clr & 0x000000ff;
		    	   
		    	   if (blue == 0)
		    	   {
		    		  //System.out.println("nick");
		    	   }				   
			}
		}
		
		//Draws the Missile for Tank 1
				for (int i = 0; i < tests.size(); i++)
				{
					test test = tests.get(i);
					mLabel = test.getMissileImage();
					mLabel.setLocation(test.getX(), test.getY());
					mLabel.setSize(test.getWidth(), test.getHeight());
					add(mLabel);
				}
		
		//Checks if missile1 is on black
		for (int i = 0; i < 1; i++)
		{    
		           // The the pixel color information at 20, 20			       
			       for (int j = 0 ; j < missile1s.size(); j++)
			       {			    	 			    	 
			    	   
			    	   // Getting pixel color by position x and y 
			    	   int clr=  image.getRGB(missile1s.get(j).getX() + 3,missile1s.get(j).getY() + 25); 
			    	 
			    	   int  blue  =  clr & 0x000000ff;
			    	   
			    	   if (blue == 0)
			    	   {
			    		   getContentPane().remove(missile1s.get(j).getMissileImage());
			    		   missile1s.remove(j);
			    	   }				   
			       }
		}
		
		//Checks if missile2 is on black
				for (int i = 0; i < 1; i++)
				{    
				           // The the pixel color information at 20, 20			       
					       for (int j = 0 ; j < missile2s.size(); j++)
					       {			    	 			    	 
					    	   
					    	   // Getting pixel color by position x and y 
					    	   int clr=  image.getRGB(missile2s.get(j).getX() + 3,missile2s.get(j).getY() + 25); 
					    	 
					    	   int  blue  =  clr & 0x000000ff;
					    	   
					    	   if (blue == 0)
					    	   {
					    		   getContentPane().remove(missile2s.get(j).getMissileImage());
					    		   missile2s.remove(j);
					    	   }				   
					       }
				}
		
		//If Tank1 hit TM Box
		for (int i = 0; i < 1; i++)
				try
				{
					Rectangle rTMBox = new Rectangle(lBoxes.get(0).getX(), lBoxes.get(0).getY(), 25 , 25);
					Rectangle rTank1 = new Rectangle( (int) tank1X - 20, (int) tank1Y - 84, 30, 40);
									
					if (rTMBox.intersects(rTank1))
					{
						getContentPane().remove(lBoxes.get(0).getBoxImage());
						lBoxes.remove(0);
						
						lazer1F = true;
						
					}
				}
				catch (Exception error)
				{
				}
			
		
		//If Tank2 hit TM Box
		for (int i = 0; i < 1; i++)
				try
				{
					Rectangle rTMBox = new Rectangle(lBoxes.get(0).getX(), lBoxes.get(0).getY(), 25 , 25);
					Rectangle rTank2 = new Rectangle( (int) tank2X - 20, (int) tank2Y - 87, 30, 35);
											
					if (rTMBox.intersects(rTank2))
					{
						getContentPane().remove(lBoxes.get(0).getBoxImage());
						lBoxes.remove(0);
										
								
					}
				}
				catch (Exception error)
				{
				}
					
		
		//Gets rid of missile1 hits player 2
		for (int i = 0; i < 1; i++)
			for (int j = 0; j < missile1s.size(); j++)
			{
				try
				{
					Rectangle rMissile = new Rectangle(missile1s.get(j).getX(), missile1s.get(j).getY(), 5 , 5);
					Rectangle rTank2 = new Rectangle( (int) tank2X - 20, (int) tank2Y - 87, 30, 35);
							
					//If a Bomb Hits a player it will remove Health
					if (rMissile.intersects(rTank2))
					{
						getContentPane().remove(missile1s.get(j).getMissileImage());
						missile1s.remove(j);
						
						PLAYER1KILLS = PLAYER1KILLS + 1;
						
						lblKillsTank1.setText("Kills: " + PLAYER1KILLS);
						lblKillsTank2.setText("Kills: " + PLAYER2KILLS);
						
						tank1X = 41;
						tank1Y = 280;
						tank2X = 850;
						tank2Y = 95;
						
						
						//PLAYER_LIVES = PLAYER_LIVES - 1;
						//lblPlayerLives.setText("Lives: " + PLAYER_LIVES);
					}
				}
				catch (Exception error)
				{
				}
			}
		
		//Gets rid of missile2 hits player 1
		for (int i = 0; i < 1; i++)
			for (int j = 0; j < missile2s.size(); j++)
			{
				try
				{
					Rectangle rMissile = new Rectangle(missile2s.get(j).getX(), missile2s.get(j).getY(), 5 , 5);
					Rectangle rTank1 = new Rectangle( (int) tank1X - 20, (int) tank1Y - 84, 30, 40);
								
					//If a Bomb Hits a player it will remove Health
					if (rMissile.intersects(rTank1))
					{
						getContentPane().remove(missile2s.get(j).getMissileImage());
						missile2s.remove(j);
						
						PLAYER2KILLS = PLAYER2KILLS + 1;
						
						lblKillsTank1.setText("Kills: " + PLAYER1KILLS);
						lblKillsTank2.setText("Kills: " + PLAYER2KILLS);
						
						tank1X = 41;
						tank1Y = 280;
						tank2X = 850;
						tank2Y = 95;
						
						//PLAYER_LIVES = PLAYER_LIVES - 1;
						//lblPlayerLives.setText("Lives: " + PLAYER_LIVES);
					}
				}
				catch (Exception error)
				{
				}
			}
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
		AffineTransform at3 = AffineTransform.getRotateInstance(tank1A, lazer1X, lazer1Y);
		
		at.translate(tank1X - 29 / 2, tank1Y - 40 / 2);
		at2.translate(tank2X - 30 / 2, tank2Y - 35 / 2);
		at3.translate(lazer1X - 2, lazer1Y + 14);
		
		at.scale(sizeX, sizeY);
		at2.scale(sizeX, sizeY);
		at3.scale(1, 1);
		
		// Draw the updated image
		if (playGame)
		{
			g2D.drawImage(tank1image, at, this);
			g2D.drawImage(image2, at2, this);
			
			if (lazer1S == true)
			{
				g2D.drawImage(lazer1image, at3, this);
			}
		}

		Toolkit.getDefaultToolkit().sync();
	}
}

