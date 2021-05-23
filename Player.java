import java.util.ArrayList;

public abstract class Player 
{
	private ArrayList<Ships> playerShips = new ArrayList<Ships>();
	private ArrayList<PowerUps> playerPowerUps = new ArrayList<PowerUps>();
	private int shipsRemaining;
	
	public Player()
	{
		shipsRemaining = 5;
		Ships pCarrier = new CarrierShip();
		Ships pBattleship = new BattleShip();
		Ships pCruiser = new CruiserShip();
		Ships pSubmarine = new Submarine();
		Ships pDestroyer = new DestroyerShip();
		playerShips.add(pCarrier);
		playerShips.add(pBattleship);
		playerShips.add(pCruiser);
		playerShips.add(pSubmarine);
		playerShips.add(pDestroyer);
	}
	
	public Player(ArrayList<Ships> ps)
	{
		shipsRemaining = 5;
		for(int i = 0; i < ps.size(); i++)
		{
			playerShips.add(ps.get(i));
		}
	}
	
	/**
	 * if the player loses a ship, depletes the number of remaining ships
	 * 
	 */
	public void setShipsRemaining()
	{
		shipsRemaining = 5;
		for(int i = 0; i < playerShips.size(); i++)
		{
			if(playerShips.get(i).getHealth() == 0)
			{
				shipsRemaining--;
			}
		}
	}
	
	/**
	 * returns the amount of ships left 
	 * @return shipsRemaining 
	 */
	public int getShipsRemaining()
	{
		return shipsRemaining;
	}
	
	/**
	 * if the player hits a powerup, it gets added to their arraylist of powerups
	 * @param p, a powerup the player hit
	 */
	public void acquirePowerUp(PowerUps p)
	{
		playerPowerUps.add(p);
	}
	
	/**
	 * returns the entire ArrayList of Power-Ups
	 * @return playerPowerUps
	 */
	public ArrayList<PowerUps> getPowerUpsList() {
		return playerPowerUps;
	}
	
	/**
	 * prints out the list of Power-Ups
	 */
	public void printPowerUpsList()
	{
		for(int i = 0; i < playerPowerUps.size(); i++)
		{
			System.out.println(playerPowerUps.get(i).getName());
		}
	}
	
	public String toStringPowerUpList()
	{
		String s = "";
		for(int i = 0; i < playerPowerUps.size(); i++)
		{
			s += i+1;
			s += ". ";
			s += playerPowerUps.get(i).getName();
			s += " ";
		}
		return s;
	}
	
	/**
	 * returns a specific Power-Up through the parameter int i
	 * @param i
	 * @return playerPowerUps.get(i)
	 */
	public PowerUps getPowerUp(int i)
	{
		return playerPowerUps.get(i);
	}
	
	/**
	 * 
	 * @return playerShips
	 */
	public ArrayList<Ships> getPlayerShips()
	{
		return playerShips;
	}
	
	/**
	 * returns the name of every ship in the ArrayList of Ships
	 * @return s
	 */
	public String toStringShipsList()
	{
		String s = " ";
		for(int i = 0; i < playerShips.size(); i++)
		{
			s += i+1;
			s += ". ";
			s += playerShips.get(i).getName();
			s += " ";
		}
		return s;
	}
	
	/**
	 * returns a specific Ship through the parameter int i
	 * @param i
	 * @return playerShips.get(i)
	 */
	public Ships getShip(int i)
	{
		return playerShips.get(i);
	}
	
	abstract public boolean attack(char[][] g, Player c, int row, int col);
	abstract public void usePowerUp(char[][] grid, Player p, Player o, int sel);
}