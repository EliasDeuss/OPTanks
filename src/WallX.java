import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class WallX
{
	private int wallxWidth;
	private int wallxHeight;
	private int wallXPos;
	private int wallYPos;

	private ImageIcon imgWallX = new ImageIcon(getClass().getResource("WallX.png"));
	private JLabel WallXLabel = new JLabel(imgWallX);


	public WallX(int xPos, int yPos)
	{
		wallxWidth = imgWallX.getIconWidth();
		wallxHeight = imgWallX.getIconHeight();
		wallXPos = xPos;
		wallYPos = yPos;
	}

	// Move the missile 'MISSILE_SPEED' pixels up the playing field

	public JLabel getBunkerImage()
	{
		return WallXLabel;
	}
	
	public int getWidth()
	{
		return wallxWidth;
	}

	public int getHeight()
	{
		return wallxHeight;
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