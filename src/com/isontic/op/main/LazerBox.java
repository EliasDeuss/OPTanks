package com.isontic.op.main;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class LazerBox
{
	private int boxWidth;
	private int boxHeight;
	private int boxXPos;
	private int boxYPos;

	private ImageIcon imgBox = new ImageIcon(getClass().getResource("tmbox.png"));
	private JLabel boxLabel = new JLabel(imgBox);


	public LazerBox(int xPos, int yPos)
	{
		boxWidth = imgBox.getIconWidth();
		boxHeight = imgBox.getIconHeight();
		boxXPos = xPos;
		boxYPos = yPos;
	}

	// Move the missile 'MISSILE_SPEED' pixels up the playing field

	public JLabel getBoxImage()
	{
		return boxLabel;
	}
	
	public int getWidth()
	{
		return boxWidth;
	}

	public int getHeight()
	{
		return boxHeight;
	}

	public int getX()
	{
		return boxXPos;
	}

	public int getY()
	{
		return boxYPos;
	}
}