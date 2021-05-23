import java.util.ArrayList;
import java.util.Scanner;

public class HumanPlayer extends Player {

	public HumanPlayer() {
		// TODO Auto-generated constructor stub
	}

	public HumanPlayer(ArrayList<Ships> ps) {
		super(ps);
		// TODO Auto-generated constructor stub
	}

	@Override
	/**
	 * Human Player attacks their opponent
	 * 
	 * @param char [][] g
	 * @param Player c
	 * @param int row
	 * @param int col 
	 */
	public boolean attack(char[][] g, Player c, int row, int col) {
		// TODO Auto-generated method stub
		if(g[row-1][col-1] == 'C') {
			System.out.println("Missile hit your opponent's carrier ship!");
			g[row-1][col-1] = 'X';
			//Subtracts health from CPU ship
			c.getShip(0).beenHit();
			return true;
		}
		else if(g[row-1][col-1] == 'b')
		{
			System.out.println("Missile hit your opponent's battleship!");
			g[row-1][col-1] = 'X';
			//Subtracts health from CPU ship
			c.getShip(1).beenHit();
			return true;
		}
		else if(g[row-1][col-1] == 'c')
		{
			System.out.println("Missile hits your opponent's cruiser ship!");
			g[row-1][col-1] = 'X';
			//Subtracts health from CPU ship
			c.getShip(2).beenHit();
			return true;
		}
		else if(g[row-1][col-1] == 's')
		{
			System.out.println("Missile hits your opponent's submarine!");
			g[row-1][col-1] = 'X';
			//Subtracts health from CPU ship
			c.getShip(3).beenHit();
			return true;
		}
		else if(g[row-1][col-1] == 'd')
		{
			System.out.println("Missile hits your opponent's destroyer ship!");
			g[row-1][col-1] = 'X';
			//Subtracts health from CPU ship
			c.getShip(4).beenHit();
			return true;
		}
		else if (g[row-1][col-1] == 'T') {
			System.out.println("You landed on the DoubleTurn Power-Up.  Adding Power-Up to your list of Power-Ups!");
			PowerUps dt = new DoubleTurn();
			acquirePowerUp(dt);
			return true;
		}
		else if(g[row-1][col-1] == 'R') {
			System.out.println("You landed on the RestoreHealth Power-Up.  Adding Power-Up to your list of Power-Ups!");
			PowerUps rh = new RestoreHealth();
			acquirePowerUp(rh);
			return true;
		}
		else {
			//g[rowChoice][colChoice] = 'M';
			System.out.println("You missed!");
			return false;
		}
	}

	@Override
	/**
	 * Human Player receives a Power-Up if they attacked a location that has a Power-Up located there
	 * Human Player choose from a list of Power-Up options after they land a hit on their opponent
	 */
	public void usePowerUp(char[][] grid, Player p, Player o, int sel) {
		// TODO Auto-generated method stub
		System.out.println("Power-Up List: ");
		printPowerUpsList();
		if(getPowerUpsList().isEmpty()==false) {
		int powerUpChoice; 
		System.out.print("Please select a Power-Up: ");
		Scanner choice = new Scanner(System.in);
		powerUpChoice = choice.nextInt();
		if(powerUpChoice == 1) {
			//getPowerUp(0).use(grid, p, o);
			//DoubleAttack.use();
		} 
		else if(powerUpChoice == 2) {
		//	getPowerUp(1).use(grid, p, o);
			//DoubleTurn.use();
		}
		else {
			System.out.println("Option does not exist!");
		}
	}
		else if(getPowerUpsList().isEmpty()==true) {
			System.out.println("No power ups available!");
		}
	}

}