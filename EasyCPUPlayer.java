import java.util.ArrayList;
//import java.util.Scanner;
import java.util.Scanner;

public class EasyCPUPlayer extends Player {

	public EasyCPUPlayer() {
		// TODO Auto-generated constructor stub
	}

	public EasyCPUPlayer(ArrayList<Ships> ps) {
		super(ps);
		// TODO Auto-generated constructor stub
	}

	@Override
	/**
	 * Depending on what ship the CPU attacks, it prints out the appropriate statement
	 * 
	 * @param char [][] g
	 * @param Player c
	 * @param int row
	 * @param int col
	 */
	public boolean attack(char[][] g, Player c, int row, int col) {
		// TODO Auto-generated method stub
		if(g[row][col] == 'C') {
			System.out.println("Opponent hit your Carrier!");
			g[row][col] = 'X';
			//Subtracts health from CPU ship
			c.getShip(0).beenHit();
			return true;
		}
		else if(g[row][col] == 'b')
		{
			System.out.println("Opponent hit your Battleship!");
			g[row][col] = 'X';
			//Subtracts health from CPU ship
			c.getShip(1).beenHit();
			return true;		
		}
		else if(g[row][col] == 'c')
		{
			System.out.println("Opponent hits your Cruiser!");
			g[row][col] = 'X';
			//Subtracts health from CPU ship
			c.getShip(2).beenHit();
			return true;		
		}
		else if(g[row][col] == 's')
		{
			System.out.println("Opponent hit your Submarine!");
			g[row][col] = 'X';
			//Subtracts health from CPU ship
			c.getShip(3).beenHit();
			return true;
		}
		else if(g[row][col] == 'd')
		{
			System.out.println("Opponent hit your Destroyer!");
			g[row][col] = 'X';
			//Subtracts health from CPU ship
			c.getShip(4).beenHit();
			return true;
		}
		else if (g[row][col] == 'T') {
			System.out.println("Opponent landed on the DoubleTurn Power-Up.  Adding Power-Up to opponent's list of Power-Ups!");
			PowerUps dt = new DoubleTurn();
			acquirePowerUp(dt);
			return true;
		}
		else if(g[row][col] == 'R') {
			System.out.println("Opponent landed on the RestoreHealth Power-Up.  Adding Power-Up to opponent's list of Power-Ups!");
			PowerUps rh = new RestoreHealth();
			acquirePowerUp(rh);
			return true;
		}
		else {
			g[row][col] = 'M';
			System.out.println("Opponent missed!");
			return false;
		}
	
		//printBoard(board);
	}

	@Override
	/**
	 * Easy CPU Player randomly selects a number 1 to 3.  
	 * Based on what number he picked, he takes that Power-Up to use in the future
	 */
	public void usePowerUp(char [][] b, Player h, Player o, int sel) {
		// TODO Auto-generated method stub
		System.out.println("Power-Up List: ");
		getPowerUpsList();
		
		if(sel == 1) {
			int randRow = (int)(Math.random() * 10);
			int randCol = (int)(Math.random() * 10);
			getPowerUp(0).use(b, h, o, randRow, randCol);
		} 
		else if(sel == 2) {
			int randRow = (int)(Math.random() * 10);
			int randCol = (int)(Math.random() * 10);
			getPowerUp(1).use(b, h, o, randRow, randCol);
		}
		else {
			System.out.println("Option does not exist!");
		}
	}

}