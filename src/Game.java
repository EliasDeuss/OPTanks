import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Game {
	
	// Global Constants
	public static final int FIELD_WIDTH = 900;
	public static final int FIELD_HEIGHT = 600;
	
	//Private Constants
	private JFrame gameFrame = new JFrame("OP Tanks V0.1.0");
	
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
		JLabel lblGameTitle = new JLabel("OP Tanks");
		lblGameTitle.setSize(210, 65);
		lblGameTitle.setFont(new Font("Serif", Font.PLAIN, 39));
		lblGameTitle.setLocation(360, 100);
		lblGameTitle.setOpaque(false);
		lblGameTitle.setVisible(true);
		
		JButton startGame = new JButton("Start Game");
		startGame.setSize(149, 30);
		startGame.setLocation(360, 170);
		startGame.setFocusable(false);
		startGame.setActionCommand("startGame");
		//startGame.addActionListener(this);
		startGame.setVisible(true);
		
		//Add Items to Frame
		gameFrame.add(lblGameTitle);
		gameFrame.add(startGame);
	}
}
