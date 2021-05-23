public class Submarine extends Ships {

	private String name;
	private int health;
	
	public Submarine() {
		// TODO Auto-generated constructor stub
		name = "Submarine";
		health = 3;
	}
	public Submarine(int r, int c, char hov)
	{
		super(r, c, hov);
		name = "submarine";
		health = 3;
	}
	
	@Override
	/**
	 * @return name
	 */
	public String getName()
	{
		return name;
	}
	
	@Override
	/**
	 * @return health
	 */
	public int getHealth()
	{
		return health;
	}
	
	@Override
	/**
	 * sets parameter h equal to health
	 * @param int h
	 */
	public void setHealth(int h)
	{
		health = h;
	}
	
	@Override
	/**
	 * when a ship gets hit, its health is depleted and it checks to see if its health is at 0, if so, it sets sink to true
	 */
	public void beenHit()
	{
		health--;
		if(health == 0)
		{
			setSunk(true);
			System.out.println("Ship sunk");
		}
	}

}