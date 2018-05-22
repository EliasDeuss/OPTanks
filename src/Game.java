import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
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
import javax.swing.JTextField;
import javax.swing.Timer;

public class Game implements ActionListener, KeyListener {
	
	//Sever
	private String ip = "localhost";
	private int port = 12345;
	
	private Socket socket;
	private DataOutputStream dos;
	private DataInputStream dis;
	private ServerSocket serverSocket;
	
	private boolean accepted = false;
	private boolean unableToCommunicateWithOpponent = false;
	
	private JTextField portPicker;
	
	// Global Constants
	public static final int FIELD_WIDTH = 900;
	public static final int FIELD_HEIGHT = 600;
	
	//Timer
	private final int TIMER_SPEED = 10;
	private Timer timer;
	
	//Private Constants
	private JFrame gameFrame = new JFrame("OP Tanks V0.1.0");
	
	//Stat's and game settings
	private int GAMEMODE = 0; //What game mode you are in
	private int PLAYERS = 2; //How many players are in the Game
	private int MAP = 1; //What map the player is in
	
 	
	private int PLAYER1KILLS = 0;
	private int PLAYER2KILLS = 0;
	
	private int SHOOTER_SPEED = 2;
	
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
	
	//Players
	private ImageIcon imgTank1 = new ImageIcon(getClass().getResource("tank1.png"));
	private JLabel lblTank1 = new JLabel(imgTank1);
	
	private ImageIcon imgTank2 = new ImageIcon(getClass().getResource("tank2.png"));
	private JLabel lblTank2 = new JLabel(imgTank2);
	
	private int tank1X, tank1Y, tank2X, tank2Y;	
	
	private boolean pressedLeft = false, pressedRight = false, pressedUp = false, pressedDown = false, pressedSpace = false;
	private boolean controlKeyPressed = false, missileFired = false;

	
	//JLable and Buttons
	public JLabel lblGameTitle, lblGameSelectorTitle, lblGamePlayerSTitle, lblKillsTank2, lblKillsTank1;
	public JButton startGame, leaderboardMainBTN, settingsMainBTN, normalGamemodeBTN, player1BTN, player2BTN;
	
	//Menu Bar
	private JMenuBar menuBar;
	private JMenu menu, menu2;
	private JRadioButtonMenuItem rbMenuItem1, rbMenuItem2;
	private JMenuItem menuItem, menuItem2, menuItem3;
	
	//Array List
	ArrayList<Missile> missiles = new ArrayList<Missile>();
	
	public static void main(String[] args) 
	{
		new Game();
	}
	
	public Game()
	{
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
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setSize(FIELD_WIDTH, FIELD_HEIGHT);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setLayout(null);
		gameFrame.setResizable(false);
		gameFrame.getContentPane().setBackground(Color.WHITE);
		gameFrame.addKeyListener(this);
		gameFrame.setVisible(true);
	}
	
	//Server Stuff
	
	
	public void waitingLobby()
	{
		
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
		
		gameFrame.add(lblKillsTank1);
		gameFrame.add(lblKillsTank2);
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
								//playerSelector();
							}
						}
				  }
				);
		normalGamemodeBTN.setVisible(true);
		
		gameFrame.add(lblGameSelectorTitle);
		gameFrame.add(normalGamemodeBTN);
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
	
	public void setUpMap1()
	{
		setUpTank1();
		
	}
	
	// Set the size and starting position of the player's shooter
	public void setUpTank1()
	{
		// Set the size of the JLabel that contains the shooter image
		lblTank1.setSize(imgTank1.getIconWidth(), imgTank1.getIconHeight());

		// Set the shooter's initial position 
		tank1X = 15;
		tank1Y = 170;
		lblTank1.setLocation(tank1X, tank1Y);

		// Add the shooter JLabel to the JFrame
		gameFrame.add(lblTank1);
	}
	
	public void keyPressed(KeyEvent event) {
		// TODO Auto-generated method stub
		int key = event.getKeyCode();
		
		if (key == KeyEvent.VK_LEFT) // LEFT arrow
			pressedLeft = true;
		if (key == KeyEvent.VK_RIGHT) // RIGHT arrow
			pressedRight = true;

		if (key == KeyEvent.VK_SPACE) // SPACE bar
			pressedSpace = true;
		
		if (key == KeyEvent.VK_UP) // Up arrow
			pressedUp = true;
		if (key == KeyEvent.VK_DOWN) // RIGHT arrow
			pressedDown = true;
	}

	public void keyReleased(KeyEvent event) {
		// TODO Auto-generated method stub
		int key = event.getKeyCode();

		if (key == KeyEvent.VK_CONTROL) // CONTROL key
			controlKeyPressed = false;

		if (key == KeyEvent.VK_LEFT) // LEFT arrow
			pressedLeft = false;
		if (key == KeyEvent.VK_RIGHT) // RIGHT arrow
			pressedRight = false;
		
		if (key == KeyEvent.VK_UP) // Up arrow
			pressedUp = false;
		if (key == KeyEvent.VK_DOWN) // RIGHT arrow
			pressedDown = false;
		
		if (key == KeyEvent.VK_SPACE) // SPACE bar
		{
			pressedSpace = false;
			missileFired = false;
		}
	}

	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void actionPerformed(ActionEvent event) {
		
		
		// Move the existing missiles up the playing field
//		for (int j = 0; j < missiles.size(); j++)
//		{
//		Missile missile = missiles.get(j);
//		missile.moveMissile();
//
//		// If the missile gets past the top of the playing field, remove it
//		if (missile.getY() < 0 - missile.getHeight())
//		{
//				gameFrame.getContentPane().remove(missile.getMissileImage());
//				missiles.remove(j);
//			}
//		}
		
		
		//Prevents user from pressing and holding space bar
//		if (pressedSpace && !missileFired)
//		{
//		// Determine the width and height of the missile being launched
//		Missile tempMissile = new Missile(0, 0);
//		int missileWidth = tempMissile.getWidth();
//		int missileHeight = tempMissile.getHeight();
//		// Set the starting position of the missile being launched 
//		int x = shooterX + (lblShooter.getWidth() / 2) - (missileWidth / 2);
//		int y = FIELD_HEIGHT - lblShooter.getHeight() - 30 - missileHeight;
//
//		// Create a new 'Missile' object and add it to the 'missiles' ArrayList 
//		missiles.add(new Missile(x, y));
//		missileFired = true;
//				}
		
		if (pressedLeft && tank1X > 4)
			tank1X -= SHOOTER_SPEED;
		if (pressedRight && tank1X < FIELD_WIDTH - lblTank1.getWidth() - 6 - 4)
			tank1X += SHOOTER_SPEED;
		if (pressedUp && tank1X > 4)
			tank1Y -= SHOOTER_SPEED;
		if (pressedDown && tank1X < FIELD_WIDTH - lblTank1.getWidth() - 6 - 4)
			tank1Y += SHOOTER_SPEED;
		
		lblTank1.setLocation(tank1X, tank1Y);
		
		gameFrame.repaint();
		
		checkCollisions();
		
		Toolkit.getDefaultToolkit().sync();
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
						    		gameFrame.dispose();
									System.exit(0);
								}
						    }
						  }
						);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Map1");
		menuItem.setActionCommand("map1");
		menuItem.addActionListener(
						  new ActionListener() 
						  {
						    public void actionPerformed(ActionEvent e)
						    {
						    	if (e.getActionCommand().equals("map1"))
								{
						    		setUpMap1();
								}
						    }
						  }
						);
		menu.add(menuItem);
		gameFrame.setJMenuBar(menuBar);
		rbMenuItem2.setEnabled(true);
	}
}
