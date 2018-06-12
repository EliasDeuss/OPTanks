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
	private boolean newspawn = true;
	private int tankspawn = 0;
	private int spawnT = 0;
	private boolean playerHUD = true;
	private int PLAYER1KILLS = 0;
	private int PLAYER2KILLS = 0;
	private String win = "Player 1";
	private boolean w = false;
	
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
	private File file = new File("src/map.png");
	private BufferedImage image;
	
	//Power ups
	private int powerU = 1; //What powerup is coming next
	private int powerUClock = 0;
	
	private int lranC = 0;
	
	//Lazer 1
	private double lazer1X = 0;
	private double lazer1Y = 0;
	private int lazer1T = 0;
	private int tank1tm = 0;
	private boolean lazer1F = false, lazer1S = false;
	private Image lazer1image = new ImageIcon(getClass().getResource("lazer.png")).getImage(); //Lazer1 Image
	
	//Lazer 2
	private double lazer2X = 0;
	private double lazer2Y = 0;
	private int lazer2T = 0;
	private int tank2tm = 0;
	private boolean lazer2F = false, lazer2S = false;
	private Image lazer2image = new ImageIcon(getClass().getResource("lazer.png")).getImage(); //Lazer1 Image
		
	
	//Tank1
	private double tank1X = 400, tank1Y = 200; //X and Y for Tank 1
	private double tank1A = 4.7; //Angle of the tank1 (0-6)
	private boolean T1STOP = false;
	private boolean onewallUp = false;
	private boolean onewallDown = false;
	private boolean gmode = false, ge = false, fx = false;
	private boolean oneright = false, oneleft = false;
	private Image tank1image = new ImageIcon(getClass().getResource("tank1down.png")).getImage(); //Tank1 Image
	private JLabel lblPlayer1 = new JLabel("Player 1");
	private boolean tank1M = false, T1missileFired = false;
	private boolean onepressedLeft = false, onepressedRight = false, onepressedUp = false, onepressedDown = false;
	private int T1TIMEL = 0;
	
	//Color Check for Tank 1 A
	private int tank1checkA = 255;
	
	//Tank2
	private double tank2X = 400, tank2Y = 400; //X & Y for Tank 2
	private double tank2A = 1.56; //Angle of the tank2 (0-6)
	private boolean twowallUp = false;
	private boolean twowallDown = false;
	private boolean tworight = false, twoleft = false;
	private JLabel lblPlayer2 = new JLabel("Player 2");
	private Image image2 = new ImageIcon(getClass().getResource("tank2down.png")).getImage(); //Tank2 Image 
	
	private boolean tank2M = false, T2missileFired = false, T2STOP = false;
	private boolean twopressedLeft = false, twopressedRight = false, twopressedUp = false, twopressedDown = false;
	//Tank Size info
	private double sizeX = 1.0, sizeY = 1.0;
	private boolean controlKeyPressed = false;
	
	//JLable and Buttons
	public JLabel lblGameTitle, lblGameSelectorTitle, lblGamePlayerSTitle, lblKillsTank2, lblKillsTank1, lblMap, mLabel, lblKillsTank1img, lblKillsTank2img, lblAbout, lblEliasDeuss, lblNickJohnson, lblWin;
	public JButton startGame, leaderboardMainBTN, settingsMainBTN, normalGamemodeBTN, player1BTN, player2BTN, startGame2;
	
	//Menu Bar
	private JMenuBar menuBar;
	private JMenu menu, menu2;
	private JRadioButtonMenuItem rbMenuItem1, rbMenuItem2;
	private JMenuItem menuItem, menuItem2, menuItem3, about;
	
	//Array List
	ArrayList<Missile1> missile1s = new ArrayList<Missile1>();
	ArrayList<Missile2> missile2s = new ArrayList<Missile2>();
	ArrayList<LazerBox> lBoxes = new ArrayList<LazerBox>();
	ArrayList<Lazer1> lazer1s = new ArrayList<Lazer1>();
	ArrayList<Lazer2> lazer2s = new ArrayList<Lazer2>();
	
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
		setTitle("OPTanks V0.1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(FIELD_WIDTH, FIELD_HEIGHT);
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);
		getContentPane().setBackground(Color.WHITE);
		addKeyListener(this);
		setVisible(true);
	}
	
	public void about() 
	{
		 JFrame frame = new JFrame("About");  
		 frame.setSize(350, 200);
		 frame.setLocationRelativeTo(null);
		 frame.setLayout(null);
		 frame.setResizable(false);
		 frame.getContentPane().setBackground(Color.WHITE);
		 
		 lblAbout = new JLabel("Made by");
		 lblAbout.setSize(160, 45);
		 lblAbout.setFont(new Font("Serif", Font.PLAIN, 19));
		 lblAbout.setLocation(140, 5);
		 lblAbout.setOpaque(false);
		 lblAbout.setVisible(true);
		 
		 lblEliasDeuss = new JLabel("Elias Deuss");
		 lblEliasDeuss.setSize(148, 90);
		 lblEliasDeuss.setFont(new Font("Serif", Font.PLAIN, 14));
		 lblEliasDeuss.setLocation(140, 5);
		 lblEliasDeuss.setOpaque(false);
		 lblEliasDeuss.setVisible(true);
		 
		 lblNickJohnson = new JLabel("Nick Johnson");
		 lblNickJohnson.setSize(135, 123);
		 lblNickJohnson.setFont(new Font("Serif", Font.PLAIN, 14));
		 lblNickJohnson.setLocation(135, 5);
		 lblNickJohnson.setOpaque(false);
		 lblNickJohnson.setVisible(true);
		 
		 frame.add(lblNickJohnson);
		 frame.add(lblEliasDeuss);
		 frame.add(lblAbout);
		 frame.setVisible(true);
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
		leaderboardMainBTN.setVisible(false);
		
		settingsMainBTN = new JButton(imgSettMain);
		settingsMainBTN.setFont(new Font("Serif", Font.PLAIN, 25));
		settingsMainBTN.setSize(179, 40);
		settingsMainBTN.setLocation(345, 269);
		settingsMainBTN.setFocusable(false);
		settingsMainBTN.setActionCommand("settings");
		//startGame.addActionListener(this);
		settingsMainBTN.setVisible(false);
		
		//Add Items to Frame
		add(lblGameTitle);
		add(startGame);
		add(leaderboardMainBTN);
		add(settingsMainBTN);
		fx = true;
		
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
								
								hideGamemodeSelector();
								
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
		
		setUpTor();
	}
	
	public void setUpTor()
	{
		setContentPane(new JLabel(new ImageIcon(getClass().getResource("tr.png"))));
		setVisible(true);
		
		startGame2 = new JButton(imgStartMain);
		startGame2.setSize(181, 46);
		startGame2.setFont(new Font("Serif", Font.PLAIN, 25));
		startGame2.setLocation(355, 465);
		startGame2.setFocusable(false);
		startGame2.setActionCommand("startGame");
		startGame2.addActionListener(
				  new ActionListener() 
						{
					    public void actionPerformed(ActionEvent e) 
					    {
					    	if (e.getActionCommand().equals("startGame"))
							{
					    		hideTor();
					    		playGame = true;
							}
						}
				  }
				);
		startGame2.setVisible(true);
		add(startGame2);
		
		repaint();
	}
	
	public void hideTor()
	{
		fx = false;
		setUpMap1();
	}
	
	public void setUpMap1()
	{
		setUpTank1();
		setUpTank2();
		infoBoard();
		
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
		newspawn = true;
		
		lblPlayer1.setSize(50, 15);
		lblPlayer1.setFont(new Font("Serif", Font.PLAIN, 12));
		lblPlayer1.setOpaque(false);
		lblPlayer1.setForeground(Color.BLACK);
		lblPlayer1.setVisible(true);
		
		tank1move = false;

	}
	
	public void setUpTank2()
	{
		tank2move = true;
		// Set the shooter's initial position 
		newspawn = true;
		
		lblPlayer2.setSize(50, 15);
		lblPlayer2.setFont(new Font("Serif", Font.PLAIN, 12));
		lblPlayer2.setOpaque(false);
		lblPlayer2.setForeground(Color.BLUE);
		lblPlayer2.setVisible(true);
		
		tank2move = false;
	}
	
	
	public void keyPressed(KeyEvent event) {
		
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
		
		if (key == 80) // Q
		{
			if (fx == true)
			{
				ge = true;
			} else {
			}
		}
		
		if (key == 78) // N
		{
			if (ge == true)
			{
				gmode = true;
			} else {
			}
		}
		
	}

	public void keyReleased(KeyEvent event) {
		
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
		
		if (key == 78) // Q
		{
			gmode = false;
		}
	}

	public void keyTyped(KeyEvent event) {
		
		
	}
	
	
	public void actionPerformed(ActionEvent event) {
		
		if (PLAYER1KILLS >= 10)
		{
			win = "Player 1";
			w = true;
		}
		
		if (PLAYER2KILLS >= 10)
		{
			win = "Player 2";
			w = true;
		}
		
		if (w == true)
		{
			lblWin = new JLabel(win + " won");
			lblWin.setSize(400, 50);
			lblWin.setFont(new Font("Serif", Font.PLAIN, 40));
			lblWin.setForeground(Color.RED);
			lblWin.setLocation(330, 230);
			lblWin.setOpaque(false);
			lblWin.setVisible(true);
			
			lblPlayer1.setVisible(false);
			lblPlayer2.setVisible(false);
			
			repaint();
			
			timer.stop();
			
			add(lblWin);
		}
		
		if (playerHUD == true)
		{
			lblPlayer1.setLocation((int) tank1X - 22, (int) tank1Y - 93);
			lblPlayer2.setLocation((int) tank2X - 22, (int) tank2Y - 91);
			
			add(lblPlayer1);
			add(lblPlayer2);
		}
	if (gmode == true)	
	{
		for (int j = 0; j < missile1s.size(); j++)
		{
			if (missile1s.get(j).getX() <= 25 || missile1s.get(j).getX() >= 860 || missile1s.get(j).getY() >= 570 || missile1s.get(j).getY() <= 25)
			{
				getContentPane().remove(missile1s.get(j).getMissileImage());
				missile1s.remove(j);
			}
		}
	}
	
		for (int j = 0; j < lazer1s.size(); j++)
		{
			if (lazer1s.get(j).getX() <= 0 || lazer1s.get(j).getX() >= 900 || lazer1s.get(j).getY() >= 600 || lazer1s.get(j).getY() <= 0)
			{
				getContentPane().remove(lazer1s.get(j).getMissileImage());
				lazer1s.remove(j);
			}
		}
		
		for (int j = 0; j < lazer2s.size(); j++)
		{
			if (lazer2s.get(j).getX() <= 0 || lazer2s.get(j).getX() >= 900 || lazer2s.get(j).getY() >= 600 || lazer2s.get(j).getY() <= 0)
			{
				getContentPane().remove(lazer2s.get(j).getMissileImage());
				lazer2s.remove(j);
				
			}
		}
		
		//Power Ups
		
		lranC = lranC + 1;
		
		if (lranC >= 6)
			lranC = 0;
		
	
		//Spawning
		if (spawnT >= 4)
		{
			spawnT = 0;
		}
		
		if (spawnT == 0 && newspawn == true)
		{
			
			tank1X = 347.160;
			tank1Y = 93.148;
			tank1A = 1.60;
			lazer1F = false;
			lazer1S = false;
			
			tank2X = 850.026;
			tank2Y = 449.668;
			tank2A = 3.0999;
			lazer2F = false;
			lazer2S = false;
			
			newspawn = false;
			
			spawnT = spawnT +1;
		}
		
		if (spawnT == 1 && newspawn == true)
		{
			tank1X = 51.239;
			tank1Y = 295.424;
			tank1A = 6.238;
			lazer1F = false;
			lazer1S = false;
			
			tank2X = 762.323;
			tank2Y = 92.65;
			tank2A = 4.783;
			lazer2F = false;
			lazer2S = false;

			newspawn = false;
			
			spawnT = spawnT +1;
		}
		
		if (spawnT == 2 && newspawn == true)
		{
			tank1X = 640.909;
			tank1Y = 250.282;
			tank1A = 3.04999;
			lazer1F = false;
			lazer1S = false;
			
			tank2X = 130.706;
			tank2Y = 512.622;
			tank2A = 3.2499;
			lazer2F = false;
			lazer2S = false;

			newspawn = false;
			
			spawnT = spawnT +1;
		}
		
		if (spawnT == 3 && newspawn == true)
		{
			tank1X = 424.023;
			tank1Y = 246.949;
			tank1A = 3.7499;
			lazer1F = false;
			lazer1S = false;
			
			tank2X = 757.096;
			tank2Y = 440.279;
			tank2A = 3.233;
			lazer2F = false;
			lazer2S = false;
			
			newspawn = false;
			
			spawnT = spawnT +1;
		}
		
		//Tracking Missiles
		if (playGame == true)
		{
			powerUClock = powerUClock + 1;
		}
		
		
		
		//Spawns in powerup
		if (powerUClock >= 1600 && powerU == 1)
		{
			powerUClock = 0; // Resets clock
			powerU = 1;
			
			if (lranC == 1 && lBoxes.size() == 0 && lazer1F == false && lazer1S == false && lazer2F == false && lazer2S == false)
			{
				lBoxes.add(new LazerBox(555, 81));
			}
			
			if (lranC == 2 && lBoxes.size() == 0 && lazer1F == false && lazer1S == false && lazer2F == false && lazer2S == false)
			{
				lBoxes.add(new LazerBox(354, 447));
			}
			
			if (lranC == 3 && lBoxes.size() == 0 && lazer1F == false && lazer1S == false && lazer2F == false && lazer2S == false)
			{
				lBoxes.add(new LazerBox(846, 442));
			}
			
			if (lranC == 4 && lBoxes.size() == 0 && lazer1F == false && lazer1S == false && lazer2F == false && lazer2S == false)
			{
				lBoxes.add(new LazerBox(270, 231));
			}
			
			if (lranC == 5 && lBoxes.size() == 0 && lazer1F == false && lazer1S == false && lazer2F == false && lazer2S == false)
			{
				lBoxes.add(new LazerBox(45, 212));
			}
			
		}
		
		//Draws the Lazer Box
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
			if (oneleft == false)
				tank1A = tank1A -0.05;
		}
		if (onepressedRight && tank1X < FIELD_WIDTH  - 6 - 4)
		{
			if (oneright == false)
				tank1A = tank1A +0.05;
		}
		
		//Forwards
		if (onepressedUp && tank1X > 4)
		{	
			if (onewallUp == false)
			{
				tank1Y = tank1Y + (SHOOTER_SPEED) * (Math.cos(tank1A));
				tank1X = tank1X - (SHOOTER_SPEED) * (Math.sin(tank1A));
			}
		}
		
		//Backwards
		if (onepressedDown && tank1X < FIELD_WIDTH - 6 - 4)
		{
			if (onewallDown == false)
			{
				tank1Y = tank1Y - (SHOOTER_SPEED) * (Math.cos(tank1A));
				tank1X = tank1X + (SHOOTER_SPEED) * (Math.sin(tank1A));
			}
		}
		
		//Tank2
		
		if (twopressedLeft && tank2X > 4)
		{
			if (twoleft == false)
				tank2A = tank2A -0.05;
		}
		if (twopressedRight && tank2X < FIELD_WIDTH - 6 - 4)
		{
			if (oneright == false)
				tank2A = tank2A +0.05;
		}
				
		//Forwards
		if (twopressedUp && tank2X > 4)
		{	
			if (twowallUp == false)
			{
				tank2Y = tank2Y + (SHOOTER_SPEED) * (Math.cos(tank2A));
				tank2X = tank2X - (SHOOTER_SPEED) * (Math.sin(tank2A));
			}
		}
			
		//Backwards
		if (twopressedDown && tank2X < FIELD_WIDTH  - 6 - 4)
		{
			if (onewallDown == false)
			{
				tank2Y = tank2Y - (SHOOTER_SPEED) * (Math.cos(tank2A));
				tank2X = tank2X + (SHOOTER_SPEED) * (Math.sin(tank2A));	
			}
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
		
		//Lazer Tank1
		if (lazer1F == true)
		{
			tank1tm = tank1tm + 1;
			if (tank1M == true && tank1tm >= 2 || lazer1S == true && tank1tm >= 2)
			{
				lazer1S = true;
				tank1tm = 0;
				for (int j = 0; j < 1; j++)
				{
					// Set the starting position of the missile being launched 
					double x = tank1X - 7;
					double y = tank1Y - 45 ;//- 61;
					double a = tank1A;

					// Create a new 'Missile' object and add it to the 'missile1s' ArrayList 
					lazer1s.add(new Lazer1(x, y, a));

				}
				
			}
		}
		
		//Tank2 Missile
		if (lazer2F == false)
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
		
		//Lazer Tank2
		if (lazer2F == true)
		{
			tank2tm = tank2tm + 1;
			if (tank2M == true && tank2tm >= 2 || lazer2S == true && tank2tm >= 2)
			{
				lazer2S = true;
				tank2tm = 0;
				for (int j = 0; j < 1; j++)
				{
					// Set the starting position of the missile being launched 
					double x = tank2X - 7;
					double y = tank2Y - 45 ;//- 61;
					double a = tank2A;

					// Create a new 'Missile' object and add it to the 'missile1s' ArrayList 
					lazer2s.add(new Lazer2(x, y, a));
				}
				
			}
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
		
		// Move the existing lazer1 up the playing field
		for (int j = 0; j < lazer1s.size(); j++)
		{
			Lazer1 lazer = lazer1s.get(j);
			lazer.moveMissile();

			// If the missile gets past the top of the playing field, remove it
			if (lazer.getY() < 0 - lazer.getHeight())
			{
				getContentPane().remove(lazer.getMissileImage());
				lazer1s.remove(j);
			}
		}
				
		// Move the existing lazer2 up the playing field
		for (int j = 0; j < lazer2s.size(); j++)
		{
			Lazer2 lazer = lazer2s.get(j);
			lazer.moveMissile();

			// If the missile gets past the top of the playing field, remove it
			if (lazer.getY() < 0 - lazer.getHeight())
			{
				getContentPane().remove(lazer.getMissileImage());
				lazer2s.remove(j);
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
			JLabel lLabel = missile.getMissileImage();
			lLabel.setLocation(missile.getX(), missile.getY());
			lLabel.setSize(missile.getWidth(), missile.getHeight());
			add(lLabel);
		}
		
		//Draws the Lazer for Tank 1
		for (int i = 0; i < lazer1s.size(); i++)
		{
			Lazer1 lazer = lazer1s.get(i);
			JLabel lLabel2 = lazer.getMissileImage();
			lLabel2.setLocation(lazer.getX(), lazer.getY());
			lLabel2.setSize(lazer.getWidth(), lazer.getHeight());
			add(lLabel2);
		}
				
		//Draws the Lazer for Tank 2
		for (int i = 0; i < lazer2s.size(); i++)
		{
			Lazer2 lazer = lazer2s.get(i);
			JLabel lLabel = lazer.getMissileImage();
			lLabel.setLocation(lazer.getX(), lazer.getY());
			lLabel.setSize(lazer.getWidth(), lazer.getHeight());
			add(lLabel);
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
		
		lazer2X = tank2X;
		lazer2Y = tank2Y;
		
		//Keeps lazer1 out for only 2 seconds
		if (lazer1F && lazer1S == true)
		{
			lazer1T = lazer1T + 1;
			
			if (lazer1T >= 28)
			{
				lazer1F = false;
				lazer1S = false;
				lazer1T = 0;
			}
		}
		
		//Keeps lazer2 out for only 2 seconds
		if (lazer2F && lazer2S == true)
		{
			lazer2T = lazer2T + 1;
					
			if (lazer2T >= 28)
			{
				lazer2F = false;
				lazer2S = false;
				lazer2T = 0;
			}
		}
		
		
	}
	
	public void checkCollisions()
	{
		//Checks if tank1 is on black
		for (int i = 0; i < 1; i++)
		{
					           	 			    	 
		    	   double tankX = tank1X;
		    	   double tankY = tank1Y;
		    	   
		    	   double a = ((2 * Math.PI - tank1A) + Math.PI * 3 / 2) % (Math.PI * 2);
		    	
		    	   // Getting pixel color by position x and y 
		    	  
					double x = (int) tankX - 5 + Math.sin(a + .907098504) * 25;//31;// + 15;
					double y = (int) tankY - 47 + Math.cos(a + .907098504) * 25;//31;// - 50;
					
		    	  
					
					double x2 = (int) tankX - 5 - Math.sin(a + .907098504) * 25;//31;// + 15;
					double y2 = (int) tankY - 47 - Math.cos(a + .907098504) * 25;//31;// - 50;
					
					
					double x3 = (int) tankX - 5 + Math.sin(a - .907098504) * 25;//31;// + 15;
					double y3 = (int) tankY - 47 + Math.cos(a - .907098504) * 25;//31;// - 50;
				
				   
					double x4 = (int) tankX - 5 - Math.sin(a - .907098504) * 25;//31;// + 15;
					double y4 = (int) tankY - 47 - Math.cos(a - .907098504) * 25;//31;// - 50;
					
					
				   int one =  image.getRGB( (int) x + 5, (int) y + 25);
			       int two =  image.getRGB( (int) x2 + 5, (int) y2 + 25);
			       int three =  image.getRGB( (int) x3 + 5, (int) y3 + 25);
			       int four =  image.getRGB( (int) x4 + 5, (int) y4 + 25);
					
		    	   int  oneb  =  one & 0x000000ff;
		    	   if (oneb == 0)
		    	   {
		    		  // Top Right
		    		   onepressedUp = false;
		    		   onewallUp = true;
		    		   oneright = true;
		    	   }
		    	   
		    	   if (oneb == 255)
		    	   {
		    		  // Top Right
		    		   onewallUp = false;
		    		   oneright = false;
		    	   }
		    	   
		    	   int  twob  =  two & 0x000000ff;
		    	   if (twob == 0)
		    	   {
		    		  //Bottom left
		    		   onepressedDown = false;
		    		   onewallDown = true;
		    		   oneleft = true;
		    	   }
		    	   
		    	   if (twob == 255)
		    	   {
		    		  //Bottom left
		    		   onewallDown = false;
		    		   oneleft = false;
		    	   }	
		    	   
		    	   int  threeb  =  three & 0x000000ff;
		    	   if (threeb == 0)
		    	   {
		    		  //Bottom Right
		    		   onepressedDown = false;
		    		   onewallDown = true;
		    		   oneright = true;
		    	   }
		    	   
		    	   if (threeb == 255)
		    	   {
		    		  //Bottom Right
		    		   onewallDown = false;
		    		   oneright = false;
		    	   }
		    	   
		    	   int  fourb  =  four & 0x000000ff;
		    	   if (fourb == 0)
		    	   {
		    		//Top Left
		    		   onepressedUp = false;
		    		   onewallUp = true;
		    		   oneleft = true;
		    	   }
		    	   
		    	   if (fourb == 255)
		    	   {
		    		//Top Left
		    		   onewallUp = false;
		    		   oneleft = false;
		    	   }
			
		}
		
		//Checks if tank2 is on black
				for (int i = 0; i < 1; i++)
				{
							           	 			    	 
				    	   double tankX = tank2X;
				    	   double tankY = tank2Y;
				    	   
				    	   double a = ((2 * Math.PI - tank2A) + Math.PI * 3 / 2) % (Math.PI * 2);
				    	
				    	   // Getting pixel color by position x and y 
				    	  
							double x = (int) tankX - 5 + Math.sin(a + .907098504) * 25;//31;// + 15;
							double y = (int) tankY - 47 + Math.cos(a + .907098504) * 25;//31;// - 50;
							
				    	  
							double x2 = (int) tankX - 5 - Math.sin(a + .907098504) * 25;//31;// + 15;
							double y2 = (int) tankY - 47 - Math.cos(a + .907098504) * 25;//31;// - 50;
							
							
							double x3 = (int) tankX - 5 + Math.sin(a - .907098504) * 25;//31;// + 15;
							double y3 = (int) tankY - 47 + Math.cos(a - .907098504) * 25;//31;// - 50;
						
						   
							double x4 = (int) tankX - 5 - Math.sin(a - .907098504) * 25;//31;// + 15;
							double y4 = (int) tankY - 47 - Math.cos(a - .907098504) * 25;//31;// - 50;
							
							
						   int one =  image.getRGB( (int) x + 5, (int) y + 25);
					       int two =  image.getRGB( (int) x2 + 5, (int) y2 + 25);
					       int three =  image.getRGB( (int) x3 + 5, (int) y3 + 25);
					       int four =  image.getRGB( (int) x4 + 5, (int) y4 + 25);
							
				    	   int  oneb  =  one & 0x000000ff;
				    	   if (oneb == 0)
				    	   {
				    		  // Top Right
				    		   twopressedUp = false;
				    		   twowallUp = true;
				    		   tworight = true;
				    	   }
				    	   
				    	   if (oneb == 255)
				    	   {
				    		  // Top Right
				    		   twowallUp = false;
				    		   tworight = false;
				    	   }
				    	   
				    	   int  twob  =  two & 0x000000ff;
				    	   if (twob == 0)
				    	   {
				    		  //Bottom left
				    		   twopressedDown = false;
				    		   twowallDown = true;
				    		   twoleft = true;
				    	   }
				    	   
				    	   if (twob == 255)
				    	   {
				    		  //Bottom left
				    		   twowallDown = false;
				    		   twoleft = false;
				    	   }	
				    	   
				    	   int  threeb  =  three & 0x000000ff;
				    	   if (threeb == 0)
				    	   {
				    		  //Bottom Right
				    		   twopressedDown = false;
				    		   twowallDown = true;
				    		   tworight = true;
				    	   }
				    	   
				    	   if (threeb == 255)
				    	   {
				    		  //Bottom Right
				    		   twowallDown = false;
				    		   tworight = false;
				    	   }
				    	   
				    	   int  fourb  =  four & 0x000000ff;
				    	   if (fourb == 0)
				    	   {
				    		//Top Left
				    		   twopressedUp = false;
				    		   twowallUp = true;
				    		   twoleft = true;
				    	   }
				    	   
				    	   if (fourb == 255)
				    	   {
				    		//Top Left
				    		   twowallUp = false;
				    		   twoleft = false;
				    	   }
					
				}
		
		if (gmode == false) {
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
			} else {
				
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
										
						lazer2F = true;	
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
					Rectangle rTank2 = new Rectangle( (int) tank2X - 30 / 2, (int) tank2Y - 65, 30, 35);
							
					//If a Bomb Hits a player it will remove Health
					if (rMissile.intersects(rTank2))
					{
						getContentPane().remove(missile1s.get(j).getMissileImage());
						missile1s.remove(j);
						
						PLAYER1KILLS = PLAYER1KILLS + 1;
						
						lblKillsTank1.setText("Kills: " + PLAYER1KILLS);
						lblKillsTank2.setText("Kills: " + PLAYER2KILLS);
						
						newspawn = true;
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
					Rectangle rTank1 = new Rectangle( (int) tank1X - 40 / 2, (int) tank1Y - 76, 29, 40);
								
					//If a Bomb Hits a player it will remove Health
					if (rMissile.intersects(rTank1))
					{
						getContentPane().remove(missile2s.get(j).getMissileImage());
						missile2s.remove(j);
						
						PLAYER2KILLS = PLAYER2KILLS + 1;
						
						lblKillsTank1.setText("Kills: " + PLAYER1KILLS);
						lblKillsTank2.setText("Kills: " + PLAYER2KILLS);
						
						newspawn = true;
					}
				}
				catch (Exception error)
				{
				}
			}
		
		//Gets rid of lazer1 hits player 2
		for (int i = 0; i < 1; i++)
			for (int j = 0; j < lazer1s.size(); j++)
			{
				try
				{
					Rectangle rLazer = new Rectangle(lazer1s.get(j).getX(), lazer1s.get(j).getY(), 5 , 5);
					Rectangle rTank2 = new Rectangle( (int) tank2X - 30 / 2, (int) tank2Y - 65, 30, 35);
									
					//If a Bomb Hits a player it will remove Health
					if (rLazer.intersects(rTank2))
					{
						getContentPane().remove(lazer1s.get(j).getMissileImage());
						lazer1s.remove(j);
								
						PLAYER1KILLS = PLAYER1KILLS + 1;
						
						lblKillsTank1.setText("Kills: " + PLAYER1KILLS);
						lblKillsTank2.setText("Kills: " + PLAYER2KILLS);
						
						newspawn = true;
					}
				}
				catch (Exception error)
				{
				}
			}
				
		//Gets rid of lazer2 hits player 1
		for (int i = 0; i < 1; i++)
			for (int j = 0; j < lazer2s.size(); j++)
			{
				try
				{
					Rectangle rLazer = new Rectangle(lazer2s.get(j).getX(), lazer2s.get(j).getY(), 5 , 5);
					Rectangle rTank1 = new Rectangle( (int) tank1X - 40 / 2, (int) tank1Y - 76, 29, 40);
								
					//If a Bomb Hits a player it will remove Health
					if (rLazer.intersects(rTank1))
					{
						getContentPane().remove(lazer2s.get(j).getMissileImage());
						lazer2s.remove(j);
								
						PLAYER2KILLS = PLAYER2KILLS + 1;
						
						lblKillsTank1.setText("Kills: " + PLAYER1KILLS);
						lblKillsTank2.setText("Kills: " + PLAYER2KILLS);
					
						newspawn = true;
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
		
		menu2 = new JMenu("About");
		menu2.setMnemonic(KeyEvent.VK_A);
		menu2.getAccessibleContext().setAccessibleDescription("Game Difficulty");
		menuBar.add(menu2);		
		
		//a group of JMenuItems
		menuItem = new JMenuItem("Reset");
		menuItem.setActionCommand("reset");
		menuItem.addActionListener(
				  new ActionListener() 
						{
					    public void actionPerformed(ActionEvent e) 
					    {
					    	if (e.getActionCommand().equals("reset"))
							{
					    		tank2X = 850;
					    		tank2Y = 95;
					    		
					    		tank1X = 41;
					    		tank1Y = 284;
					    		
					    		w = false;
					    		timer.start();
					    		
					    		lblWin.setVisible(true);
					    		lblPlayer1.setVisible(true);
								lblPlayer2.setVisible(true);
					    		
					    		PLAYER1KILLS = 0;
					    		PLAYER2KILLS = 0;
							}
						}
				  }
				);
		
		menu.add(menuItem);
		menu.addSeparator();
		
		menuItem2 = new JMenuItem("Turn Names off");
		menuItem2.setActionCommand("namesoff");
		menuItem2.addActionListener(
		  new ActionListener() 
				{
			    public void actionPerformed(ActionEvent e) 
			    {
			    	if (e.getActionCommand().equals("namesoff"))
					{
			    		if (playerHUD == true) {
			    			playerHUD = false;
			    			lblPlayer1.setVisible(false);
			    			lblPlayer2.setVisible(false);
			    			menuItem2.setText("Turn Names on");
			    		} else {
			    			if (playerHUD == false) {
				    			playerHUD = true;
				    			lblPlayer1.setVisible(true);
				    			lblPlayer2.setVisible(true);
				    			menuItem2.setText("Turn Names off");
				    		}
			    		}
			    		
			    		
					}
				}
		  }
		);
		menu.add(menuItem2);
	
		//Difficulty Selector
		menu.addSeparator();
		
		about = new JMenuItem("About Page");
		about.setActionCommand("about");
		about.addActionListener(
						  new ActionListener() 
						  {
						    public void actionPerformed(ActionEvent e)
						    {
						    	if (e.getActionCommand().equals("about"))
								{
						    		about();
								}
						    }
						  }
						);
		about.setVisible(true);
		menu2.add(about);
	
		
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
		
		at.translate(tank1X - 29 / 2, tank1Y - 40 / 2);
		at2.translate(tank2X - 30 / 2, tank2Y - 35 / 2);
		
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

