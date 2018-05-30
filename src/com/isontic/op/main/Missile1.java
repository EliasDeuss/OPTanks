package com.isontic.op.main;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Missile1
{
	// Constant
	private final int MISSILE_SPEED = 2;

	private int missileWidth;
	private int missileHeight;
	private double missileXPos;
	private double missileYPos;

	private ImageIcon missileImage = new ImageIcon(getClass().getResource("missile.png"));
	private JLabel missileLabel = new JLabel(missileImage);

	// Constructor
	public Missile1(double x, double y)
	{
		missileWidth = missileImage.getIconWidth();
		missileHeight = missileImage.getIconHeight();
		missileXPos = x;
		missileYPos = y;
	}

	// Move the missile
	public void moveMissile(double x, double y)
	{
		x = missileXPos;
		y = missileYPos;
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

	public double getX()
	{
		return missileXPos;
	}

	public double getY()
	{
		return missileYPos;
	}
}
