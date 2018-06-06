package com.isontic.op.main;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class test
{
	// Constant
	private final int MISSILE_SPEED = 1;

	private int missileWidth;
	private int missileHeight;
	private double missileXPos;
	private double missileYPos;
	private double missileA;

	private ImageIcon missileImage = new ImageIcon(getClass().getResource("missile.png"));
	private JLabel missileLabel = new JLabel(missileImage);

	// Constructor
	public test(double x, double y)
	{
		missileWidth = missileImage.getIconWidth();
		missileHeight = missileImage.getIconHeight();
		missileXPos = x;
		missileYPos = y;
	}

	// Move the missile
	public void moveMissile()
	{
		
	}

	public JLabel getMissileImage()
	{
		return missileLabel;
	}

	public int getWidth()
	{
		return missileWidth;
	}

	public int getHeight()
	{
		return missileHeight;
	}

	public int getX()
	{
		int i = (int) missileXPos;
		return i;
	}

	public int getY()
	{
		int i = (int) missileYPos;
		return i;
	}
}
