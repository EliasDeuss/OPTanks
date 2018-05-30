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
	private double missileA;

	private ImageIcon missileImage = new ImageIcon(getClass().getResource("missile.png"));
	private JLabel missileLabel = new JLabel(missileImage);

	// Constructor
	public Missile1(double x, double y, double a)
	{
		missileWidth = missileImage.getIconWidth();
		missileHeight = missileImage.getIconHeight();
		missileXPos = x;
		missileYPos = y;
		missileA = a;
	}

	// Move the missile
	public void moveMissile()
	{
		missileXPos = missileXPos - 2 * (Math.sin(missileA));
		missileYPos = missileYPos + 2 * (Math.cos(missileA));
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
		return (int) missileXPos;
	}

	public int getY()
	{
		return (int) missileYPos;
	}
}
