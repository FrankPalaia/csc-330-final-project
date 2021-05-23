
public class SaveGame {
	
	private char[][] playerCharArray;
	private char[][] cpuCharArray;

	public SaveGame(char [][] pcArr, char[][] cpuArr) {
		// TODO Auto-generated constructor stub
		playerCharArray = pcArr;
		cpuCharArray = cpuArr;
		
	}
	
	public char[][] getPlayerCharArray() {
		return playerCharArray;
	}
	
	public char[][] getCPUCharArray() {
		return cpuCharArray;
	}

}
