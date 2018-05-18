import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class WallY
{
	private int wallyWidth;
	private int wallyHeight;
	private int wallXPos;
	private int wallYPos;

	private ImageIcon imgWallX = new ImageIcon(getClass().getResource("WallX.png"));
	private JLabel WallYLabel = new JLabel(imgWallX);


	public WallY(int xPos, int yPos)
	{
		wallyWidth = imgWallX.getIconWidth();
		wallyHeight = imgWallX.getIconHeight();
		wallXPos = xPos;
		wallYPos = yPos;
	}

	// Move the missile 'MISSILE_SPEED' pixels up the playing field

	public JLabel getBunkerImage()
	{
		return WallYLabel;
	}
	
	public int getWidth()
	{
		return wallyWidth;
	}

	public int getHeight()
	{
		return wallyHeight;
	}

	public int getX()
	{
		return wallXPos;
	}

	public int getY()
	{
		return wallYPos;
	}
}