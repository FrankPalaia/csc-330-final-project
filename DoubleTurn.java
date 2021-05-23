import java.util.Scanner; 

public class DoubleTurn implements PowerUps {
	
	private String name;

	public DoubleTurn() {
		// TODO Auto-generated constructor stub
		name = "Double Turn";
	}

	@Override
	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	@Override
	/**
	 * player/CPU is able go an additional turn before the opponent goes
	 * @param char [][] grid
	 * @param Player p
	 * @param Player o
	 * @param int row
	 * @param int col
	 */
	public void use(char[][] grid, Player p, Player o, int row, int col) {
		p.attack(grid, o, row, col);
	}
}