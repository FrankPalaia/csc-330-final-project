import java.util.ArrayList;

public abstract class Ships {
	
	private boolean sunk;
	private int rowValue;
	private int colValue;
	private char orientation;
	
	public Ships() {
		sunk = false;
	}
	
	public Ships(int row, int col, char hov)
	{
		sunk = false;
		rowValue = row;
		colValue = col;
		orientation = hov;
	}
	
	/**
	 * returns a boolean sunk 
	 * @return sunk
	 */
	public boolean getSunk() {
		return sunk;
	}
	
	/**
	 * sets parameter s equal to sunk
	 * @param s
	 */
	public void setSunk(boolean s) {
		sunk = s;
	}
	
	/**
	 * returns a char orientation
	 * @return orientation
	 */
	public char getOrientation() {
		return orientation;
	}
	
	/**
	 * 
	 * @return rowValue
	 */
	public int getRow()
	{
		return rowValue;
	}
	
	/**
	 * 
	 * @return colValue
	 */
	public int getCol()
	{
		return colValue;
	}
	
	/**
	 * 
	 * @return orientation
	 */
	public char getHOV()
	{
		return orientation;
	}
	
	abstract public String getName();
	abstract public int getHealth();
	abstract public void setHealth(int h);
	abstract public void beenHit();
}