import java.util.ArrayList;

public interface PowerUps {
	
	//After landing a hit on the opponent, the player/CPU can use a power-up
	public void use(char[][] grid, Player p, Player o, int row, int col);
	public String getName();
	
}