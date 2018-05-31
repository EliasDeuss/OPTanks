package com.isontic.op.main;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Missile2
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
	public Missile2(double x, double y, double a)
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
		missileXPos = missileXPos - 3 * (Math.sin(missileA));
		missileYPos = missileYPos + 3 * (Math.cos(missileA));
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