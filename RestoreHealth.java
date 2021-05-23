import java.util.Scanner;
import java.util.ArrayList;

public class RestoreHealth implements PowerUps {

	private String name;
	
	public RestoreHealth() {
		// TODO Auto-generated constructor stub
		name="Restore Health";
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
	 * player/CPU is able to restore health to one of their ships
	 * @param char [][] board
	 * @param Player p, the user
	 * @param Player o, the CPU player
	 * @param int row
	 * @param int col
	 */
	public void use(char[][] board, Player p, Player o, int row, int col) {
		// TODO Auto-generated method stub
		boolean complete = false;
		do
		{
			if(row == 1) {
				if(p.getShip(0).getSunk())
				{
					System.out.println("Cannot heal a broken ship");
				}
				else
				{
					p.getShip(0).setHealth(5);
					System.out.println("Health of "+ p.getShip(0).getName()+":"+p.getShip(0).getHealth());
					complete = true;
				}
			}
			//Restores health to ship 2
			else if (row == 2) {
				if(p.getShip(1).getSunk())
				{
					System.out.println("Cannot heal a broken ship");
				}
				else
				{
					p.getShip(1).setHealth(4);
					
					System.out.println("Health of "+ p.getShip(1).getName()+":"+p.getShip(1).getHealth());
					complete = true;
				}
			}
			//Restores health to ship 3
			else if (row == 3) {
				if(p.getShip(2).getSunk())
				{
					System.out.println("Cannot heal a broken ship");
				}
				else
				{
					p.getShip(2).setHealth(3);
					System.out.println("Health of "+ p.getShip(2).getName()+":"+p.getShip(2).getHealth());
					complete = true; 
				}
			}
			//Restores health to ship 4
			else if (row == 4) {
				if(p.getShip(3).getSunk())
				{
					System.out.println("Cannot heal a broken ship");
				}
				else
				{
					p.getShip(3).setHealth(3);
					System.out.println("Health of "+ p.getShip(3).getName()+":"+p.getShip(3).getHealth());
					complete = true;
				}
			}
			//Restores health to ship 5
			else if (row == 5) {
				if(p.getShip(4).getSunk())
				{
					System.out.println("Cannot heal a broken ship");
				}
				else
				{
					p.getShip(4).setHealth(2);
					System.out.println("Health of "+ p.getShip(4).getName()+":"+p.getShip(4).getHealth());
					complete = true;
				}
			}
			else {
				System.out.println("Ship " + row + " does not exist!");
			}
		} while (complete == false);
	}

}