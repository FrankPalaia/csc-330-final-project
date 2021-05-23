import java.util.Scanner;
import java.util.ArrayList;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.scene.Node;

public class BattleShipRunner extends Application {
	
	private static final int NUM_ROWS = 10;
	private static final int NUM_COLS = 10;
	private static int row, col;
	int placeButtonCounter = 0;
	int doubleAttackButtonCounter = 0;
	int selection;
	char hov;
	boolean gameNotOver = true;
	ArrayList<Ships> tempShipsList = new ArrayList<Ships>();
	Player hPlayer, cPlayer;
	GridPane humanGridPane = new GridPane();
	GridPane humanHitGrid = new GridPane();
	char[][] cpuBoard = new char[NUM_ROWS][NUM_COLS];
	char[][] playerBoard = new char[NUM_ROWS][NUM_COLS];
	Label rowLabel = new Label("Select a row: ");
	TextField rowTextField = new TextField();
	Label colLabel = new Label("Select a column: ");
	TextField colTextField = new TextField();
	Label hovLabel = new Label("Ship placed horizontal(h) or vertical(v): ");
	TextField hovTextField = new TextField();
	Label instructionLabel = new Label(" ");
	Button placeButton = new Button("Place ship");
	Button attackButton = new Button("Attack");
	Button powerUpButton = new Button("Power-Ups");
	Button usePowerUpButton = new Button("      ");
	Button saveGame = new Button("Save the Game");

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		//Set stage title 
		primaryStage.setTitle("Welcome to Battleship!");
		
		//Button beginButton = new Button("Click Button to Begin Battleship!");
		//beginButton.setOnAction(new BeginButtonHandler());
		
		//Creating humanPlayer grid & board;
		initializeGrid(humanGridPane);
		initializeBoard(playerBoard);
		
		//Creating cpuPlayer grid
		initializeBoard(cpuBoard);
		ArrayList<Ships> cpShips = new ArrayList<Ships>();
		//Carrier
		setPositionCPU(cpuBoard, 5, 'C');
		Ships cCarrier = new CarrierShip();
		cpShips.add(cCarrier);
		//Battleship
		setPositionCPU(cpuBoard, 4, 'b');
		Ships cBattleship = new BattleShip();
		cpShips.add(cBattleship);
		//Cruiser
		setPositionCPU(cpuBoard, 3, 'c');
		Ships cCruiser = new CruiserShip();
		cpShips.add(cCruiser);
		//Submarine
		setPositionCPU(cpuBoard, 3, 's');
		Ships cSubmarine = new Submarine();
		cpShips.add(cSubmarine);
		//Destroyer
		setPositionCPU(cpuBoard, 2, 'd');
		Ships cDestroyer = new DestroyerShip();
		cpShips.add(cDestroyer);
		cPlayer = new EasyCPUPlayer(cpShips);
		//Placing cpu players powerups
		boolean c = true;
		while(c) {
			row = (int)(Math.random() * 9);
			col = (int)(Math.random() * 9);
			if(checkPowerUpPosition(row, col, cpuBoard)) {
				cpuBoard[row][col] = 'T';
				c = false; 
			}
		}
		boolean d = true;
		while(d) {
			row = (int)(Math.random() * 9);
			col = (int)(Math.random() * 9);
			if(checkPowerUpPosition(row, col, cpuBoard)) {
				cpuBoard[row][col] = 'R';
				d = false;
			}
		}
		
		printBoard(cpuBoard);
		
		
		//Creating humanHitGrid
		initializeGrid(humanHitGrid);
		
		//Creating selectionHBox
		HBox selectionHBox = new HBox(20, rowLabel, rowTextField, colLabel, colTextField, hovLabel, hovTextField);
		
		//Creating optionsHBox
		placeButton.setOnAction(new placeButtonHandler());
		attackButton.setOnAction(new attackButtonHandler());
		powerUpButton.setOnAction(new powerUpButtonHandler());
		saveGame.setOnAction(new saveGameButtonHandler());
		HBox optionsHBox = new HBox (300, placeButton, attackButton, powerUpButton, saveGame);
		
		//Creating VBox for scene
		instructionLabel.setText("Place Carrier");
		VBox gridsVBox = new VBox(20, humanHitGrid, humanGridPane);
		gridsVBox.setAlignment(Pos.CENTER);
		//VBox instructVBox = new VBox(10, instructionLabel, selectionHBox, optionsHBox);
		VBox vbox = new VBox (10, gridsVBox, instructionLabel, selectionHBox, optionsHBox);
		vbox.setAlignment(Pos.CENTER);
		
		//Create the scene
		Scene scene = new Scene(vbox);
		
		//scene.getStylesheets().add("mystyles.css");
		
		//Add scene to the stage
		primaryStage.setScene(scene);
		
		//Display the window
		primaryStage.show();
	}
	
	class saveGameButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent arg0) {
			try {
				//Get the file output
				SaveGame save = new SaveGame(playerBoard, cpuBoard);
				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(new File("Battleship.txt")));
				output.writeObject(save);
				output.close();
			} catch (Exception e) { 
				//error message 
				System.out.println("An error has occurred when saving the game!");
			}
			
			try {
				//Get the file input
				SaveGame save2 = new SaveGame(playerBoard, cpuBoard);
				ObjectInputStream input = new ObjectInputStream(new FileInputStream(new File("Battleship.txt")));
				input.readObject();
				input.close();
			} catch (Exception e) {
				System.out.println("An error occured when reading from the file!");
			}
		}
	}
	
	/**
	 * establishes grid and sets each element in the grid to "|\t"
	 * @param grid a grid to be initialized
	 */
	public void initializeGrid(GridPane grid) {
		for(int i = 0; i < NUM_ROWS; i++) {
			for(int j = 0; j < NUM_COLS; j++) {
				Label label = new Label ("|\t-");
				if(j == 9)
				{
					label.setText(label.getText() + "\t|");
				}
				label.setPadding(new Insets(0, 0, 20, 20));
				grid.add(label, j, i);
			}
		}
	}
	
	/**
	 * Initializes the board to have '-' in every spot that is empty
	 * @param board a board to be initialized
	 */
	public void initializeBoard(char [][] board) {
		for(int r = 0; r < NUM_ROWS; r++) {
			for(int c = 0; c < NUM_COLS; c++) {
				board[r][c] = '-';
			}
		}
	}
	
	/**
	 * 
	 * Prints the board
	 * 
	 */
	public void printBoard(char [][] board) {		
		for(int r = 0; r < NUM_ROWS; r++) {			
			System.out.print('|');
			for (int c = 0; c < NUM_COLS; c++) {
				System.out.print(board[r][c]);
				System.out.print("|");
			}
			System.out.println();
		}
	}
	
	/**
	 * sets the positions of the ships on the grid
	 * @param g the grid that the ships are being placed on
	 * @param s the number of spaces the ship occupies
	 * @param hov represents if the ship is horizontal or vertical
	 * @param sl the letter that represents the ship
	 */
	public void setGridPosition(GridPane g, int s, char hov, char sl) {
		try {
			getRow();
		} catch (BattleShipException e) {
			e.printStackTrace();
		}
		try {
			getCol();
		} catch (BattleShipException e) {
			e.printStackTrace();
		}
		if(hov == 'h')
		{
			for(int i = 0; i < s; i++ )
			{
				Node myNode = getNodeByRowColumnIndex(row-1, col-1 + i, humanGridPane);
				Label tempLabel = (Label)myNode;
				//Label tempLabel = new Label("\t" + sl);
				tempLabel.setText("|\t" + sl);
				if(col+i==10) {
					tempLabel.setText(tempLabel.getText()+"\t|");
				}
				tempLabel.setPadding(new Insets(0, 0, 20, 20));
				//g.add(tempLabel, col-1 + i, row-1);
			}
		}
		if(hov == 'v')
		{
			for(int i = 0; i < s; i++ )
			{
				Node myNode = getNodeByRowColumnIndex(row-1+i, col-1, humanGridPane);
				Label tempLabel = (Label)myNode;
				//Label tempLabel = new Label("|\t" + sl);
				tempLabel.setText("|\t" + sl);
				if(col==10) {
					tempLabel.setText(tempLabel.getText()+"\t|");
				}
				tempLabel.setPadding(new Insets(0, 0, 20, 20));
				//g.add(tempLabel, col-1, row-1 + i);
			}
		}
	}
	
	/**
	 * set the positions of the ships on the board
	 * @param b the board that the ships are being placed on
	 * @param s the size of the ship
	 * @param sl the letter that represents the ship
	 */
	public void setBoardPosition(char[][] b, int s, char hov, char sl) {
		try {
			getRow();
		} catch (BattleShipException e) {
			e.printStackTrace();
		}
		try {
			getCol();
		} catch (BattleShipException e) {
			e.printStackTrace();
		}
		if(hov == 'h')
		{
			for(int i = 0; i < s; i++ )
			{
				b[row-1][col-1+i] = sl;
			}
		}
		if(hov == 'v')
		{
			for(int i = 0; i < s; i++ )
			{
				b[row-1+i][col-1] = sl;
			}
		}
	}
	
	/**
	 * sets the positions of the cpu player's ships
	 * @param b the board the ships are being placed on
	 * @param s the number of spaces that the ship occupies
	 * @param sl letter that represents the ship
	 */
	public void setPositionCPU(char[][] b, int s, char sl) {
		int cpuroll;
		cpuroll = (int)(Math.random()*10)+1;
		if(cpuroll % 2 == 0) 
		{
			row=(int)(Math.random()*9);
			col=(int)(Math.random()*9);
			while (checkHShip(b, s))
			{	
				row=(int)(Math.random()*9);
				col=(int)(Math.random()*9);
			}
			try {
				if(isValid(b)) {
					for(int i = 0; i < s; i++) {
						b[row][col+i] = sl;
					}	
				}
			} catch (BattleShipException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println();
		}
	
		else if(cpuroll % 1 == 0) 
		{
			row=(int)(Math.random()*9);
			col=(int)(Math.random()*9);
			while (checkVShip(b, s))
			{	
				row=(int)(Math.random()*9);
				col=(int)(Math.random()*9);
			}
			try {
				if(isValid(b)) {
					for(int i = 0; i < s; i++) {
						b[row+i][col] = sl;
					}	
				}
			} catch (BattleShipException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println();
		}
	}
	
	/**
	 * a method to check if the cpu is placing the ship in a legal space
	 * @param b the board that the ships are on
	 * @param s the size of the ship
	 * @return true if it's illegal, false if it's legal
	 */
	public boolean checkHShip(char b[][], int s)
	{
		if(s + col > 10)
		{
			return true;
		}
		for(int i = 0; i < s; i++)
		{
			if (b[row][col+i] != '-') 
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * a method to check if the cpu is placing the ship in a legal space
	 * @param b the board that the ships are on
	 * @param s the size of the ship
	 * @return true if it's illegal, false if it's legal
	 */
	public boolean checkVShip(char b[][], int s)
	{
		if(s + row > 10)
		{
			return true;
		}
		for(int i = 0; i < s; i++)
		{
			if (b[row+i][col] != '-') 
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * a function to check if a power-up can be placed
	 * @param row the row its being placed
	 * @param col the column its being placed
	 * @param board the board its being placed on
	 * @return true if it can be placed and false if it can't
	 */
	public boolean checkPowerUpPosition(int row, int col, char [][] board) {
		if(board[row][col] == '-') {
			return true;
		} else {
			System.out.println("Re-enter a value, location is already occupied!");
			return false;
		}
	}
	
	public Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
	    Node result = null;
	    ObservableList<Node> childrens = gridPane.getChildren();

	    for (Node node : childrens) {
	        if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
	            result = node;
	            break;
	        }
	    }

	    return result;
	}
	
	/**
	 * checks if the row is out of bounds
	 * @throws BattleShipException
	 */
	public void getRow() throws BattleShipException {
		if (row < 0 || row >= NUM_ROWS) throw new BattleShipException("That row is out of bounds.");				
	}
	
	/**
	 * checks if the column is out of bounds
	 * @throws BattleShipException
	 */
	public void getCol() throws BattleShipException {
		if (col < 0 || col >= NUM_COLS) throw new BattleShipException("That column is out of bounds.");
	}
	
	/**
	 * checks if a ship is being place in a valid location
	 * @param board the board that is being checked
	 * @return true if it's a valid location
	 * @throws BattleShipException
	 */
	public boolean isValid(char [][] board) throws BattleShipException {
		if (board[row][col] == '-') return true;
		else throw new BattleShipException("That location has already been picked.");
	}
	
	class placeButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent arg0) {
			hov = hovTextField.getText().charAt(0);
			row = Integer.parseInt(rowTextField.getText());
			col = Integer.parseInt(colTextField.getText());
			rowTextField.setText("");
			colTextField.setText("");
			hovTextField.setText("");
			placeButtonCounter++;
			if(placeButtonCounter == 1)
			{
				setGridPosition(humanGridPane, 5, hov, 'C');
				setBoardPosition(playerBoard, 5, hov, 'C');
				Ships hCarrier = new CarrierShip(row, col, hov);
				tempShipsList.add(hCarrier);
				instructionLabel.setText("Place Battleship");
			}
			else if( placeButtonCounter == 2)
			{
				setGridPosition(humanGridPane, 4, hov, 'b');
				setBoardPosition(playerBoard, 4, hov, 'b');
				Ships hBattleship = new BattleShip(row, col, hov);
				tempShipsList.add(hBattleship);
				instructionLabel.setText("Place Cruiser");
			}
			else if( placeButtonCounter == 3)
			{
				setGridPosition(humanGridPane, 3, hov, 'c');
				setBoardPosition(playerBoard, 3, hov, 'c');
				Ships hCruiser = new CruiserShip(row, col, hov);
				tempShipsList.add(hCruiser);
				instructionLabel.setText("Place Submarine");
			}
			else if( placeButtonCounter == 4)
			{
				setGridPosition(humanGridPane, 3, hov, 's');
				setBoardPosition(playerBoard, 3, hov, 's');
				Ships hSubmarine = new Submarine(row, col, hov);
				tempShipsList.add(hSubmarine);
				instructionLabel.setText("Place Destroyer");
			}
			else if( placeButtonCounter == 5)
			{
				setGridPosition(humanGridPane, 2, hov, 'd');
				setBoardPosition(playerBoard, 2, hov, 'd');
				Ships hDestroyer = new DestroyerShip(row, col, hov);
				tempShipsList.add(hDestroyer);
				placeButton.setText("Place Power-Up");
				instructionLabel.setText("Place Double Turn Power-Up");
				hPlayer = new HumanPlayer(tempShipsList);
				printBoard(playerBoard);
				hovTextField.setText(" ");
			}
			else if(placeButtonCounter == 6)
			{
				Node myNode = getNodeByRowColumnIndex(row-1, col-1, humanGridPane);
				Label tempLabel = (Label)myNode;
				tempLabel.setText("|\t" + "T");
				if(col==10) {
					tempLabel.setText(tempLabel.getText()+"\t|");
				}
				tempLabel.setPadding(new Insets(0, 0, 20, 20));
				playerBoard[row-1][col-1] = 'T';
				instructionLabel.setText("Place Restore Health Power-Up");
				hovTextField.setText(" ");
			}
			else if(placeButtonCounter == 7)
			{
				Node myNode = getNodeByRowColumnIndex(row-1, col-1, humanGridPane);
				Label tempLabel = (Label)myNode;
				tempLabel.setText("|\t" + "R");
				if(col==10) {
					tempLabel.setText(tempLabel.getText()+"\t|");
				}
				tempLabel.setPadding(new Insets(0, 0, 20, 20));
				playerBoard[row-1][col-1] = 'R';
				instructionLabel.setText("Attack!");
			}
		}
	}
	
	class attackButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent arg0) {
			row = Integer.parseInt(rowTextField.getText());
			col = Integer.parseInt(colTextField.getText());
			rowTextField.setText("");
			colTextField.setText("");
			if(gameNotOver)
			{
				if(hPlayer.attack(cpuBoard, cPlayer, row, col))
				{
					instructionLabel.setText("You hit an opponent!");
					Node myNode = getNodeByRowColumnIndex(row-1, col-1, humanHitGrid);
					Label tempLabel = (Label)myNode;
					tempLabel.setText("|\tH");
					if(col==10) {
						tempLabel.setText(tempLabel.getText()+"\t|");
					}
					tempLabel.setPadding(new Insets(0, 0, 20, 20));
					//humanHitGrid.add(tempLabel, col-1, row-1);
					cPlayer.setShipsRemaining();
					if(cPlayer.getShipsRemaining() == 0)
					{
						gameNotOver = false;
						instructionLabel.setText("You win!");
					}
					else
					{
						row = (int)(Math.random() * 10); 
						col = (int)(Math.random() * 10);
						if(cPlayer.attack(playerBoard, hPlayer, row, col))
						{
							instructionLabel.setText("You got hit by the opponent");
							myNode = getNodeByRowColumnIndex(row, col, humanGridPane);
							Label tempCLabel = (Label)myNode;
							tempCLabel.setText("|\tx");
							if(col==9) {
								tempCLabel.setText(tempCLabel.getText()+"\t|");
							}
							tempCLabel.setPadding(new Insets(0, 0, 20, 20));
							//humanGridPane.add(tempCLabel, col, row);
							hPlayer.setShipsRemaining();
							if(hPlayer.getShipsRemaining() == 0)
							{
								gameNotOver = false;
								instructionLabel.setText("You lost");
							}
						}
						else
						{
							instructionLabel.setText("CPU missed");
							myNode = getNodeByRowColumnIndex(row, col, humanGridPane);
							Label tempCLabel = (Label)myNode;
							tempCLabel.setText("|\tx");
							if(col==9) {
								tempCLabel.setText(tempCLabel.getText()+"\t|");
							}
							tempCLabel.setPadding(new Insets(0, 0, 20, 20));
							//humanGridPane.add(tempCLabel, col, row);
						}
					}
				} 
				else
				{
					instructionLabel.setText("You miss");
					Node myNode = getNodeByRowColumnIndex(row-1, col-1, humanHitGrid);
					Label tempLabel = (Label)myNode;
					tempLabel.setText("|\tM");
					if(col==10) {
						tempLabel.setText(tempLabel.getText()+"\t|");
					}
					tempLabel.setPadding(new Insets(0, 0, 20, 20));
					//humanHitGrid.add(tempLabel, col-1, row-1);
					row = (int)(Math.random() * 10); 
					col = (int)(Math.random() * 10);
					if(cPlayer.attack(playerBoard, hPlayer, row, col))
					{
						instructionLabel.setText("You got hit by the opponent");
						myNode = getNodeByRowColumnIndex(row, col, humanGridPane);
						Label tempCLabel = (Label)myNode;
						tempCLabel.setText("|\tx");
						if(col==9) {
							tempCLabel.setText(tempCLabel.getText()+"\t|");
						}
						tempCLabel.setPadding(new Insets(0, 0, 20, 20));
						//humanGridPane.add(tempCLabel, col, row);
						hPlayer.setShipsRemaining();
						if(hPlayer.getShipsRemaining() == 0)
						{
							gameNotOver = false;
							instructionLabel.setText("You lost");
						}
					}
					else
					{
						instructionLabel.setText("CPU missed");
						myNode = getNodeByRowColumnIndex(row, col, humanGridPane);
						Label tempCLabel = (Label)myNode;
						tempCLabel.setText("|\tx");
						if(col==9) {
							tempCLabel.setText(tempCLabel.getText()+"\t|");
						}
						tempCLabel.setPadding(new Insets(0, 0, 20, 20));
						//humanGridPane.add(tempCLabel, col, row);
					}
				}
			}
		}
	}
	
	class powerUpButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent arg0) {
			if(hPlayer.getPowerUpsList().isEmpty())
			{
				instructionLabel.setText("You don't have any Power-Ups");
			}
			else
			{
				instructionLabel.setText("Which Power-Up would you like to use: " + hPlayer.toStringPowerUpList());
				rowLabel.setText("Selection: ");
				powerUpButton.setOnAction(new usePowerUpButtonHandler());
			}
		}
	}
	
	class usePowerUpButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent arg0) {
			selection = Integer.parseInt(rowTextField.getText());
			rowTextField.setText("");
			if (selection == 1)
			{
				if (hPlayer.getPowerUp(0).getName().equals("Restore Health"))
				{
					instructionLabel.setText("Please select a ship to use this Power-Up on: " + hPlayer.toStringShipsList());
					powerUpButton.setOnAction(new shipIsSelectedButtonHandler());
				}
				else
				{
					instructionLabel.setText("Double Attack!");
					rowLabel.setText("Select a Row:");
					powerUpButton.setText("Double Attack!");
					powerUpButton.setOnAction(new doubleAttackButtonHandler());
				}
			}
			else if (selection == 2)
			{
				if (hPlayer.getPowerUp(1).getName().equals("Restore Health"))
				{
					instructionLabel.setText("Please select a ship to use this Power-Up on: " + hPlayer.toStringShipsList());
					powerUpButton.setOnAction(new shipIsSelectedButtonHandler());
				}
				else
				{
					instructionLabel.setText("Double Attack!");
					rowLabel.setText("Select a Row:");
					powerUpButton.setText("Double Attack!");
					powerUpButton.setOnAction(new doubleAttackButtonHandler());
				}
			}
			else
			{
				instructionLabel.setText("You entered an invalid Power-Up. Please select another one\n" + hPlayer.toStringPowerUpList());
			}
		}
	}
	
	class shipIsSelectedButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent arg0) {
			int shipSelection = Integer.parseInt(rowTextField.getText());
			hPlayer.getPowerUp(selection-1).use(playerBoard, hPlayer, cPlayer, shipSelection, col);
			hPlayer.getPowerUpsList().remove(selection-1);
			rowLabel.setText("Row: ");
			instructionLabel.setText("Power-Up used");
			rowTextField.setText("");
			powerUpButton.setOnAction(new powerUpButtonHandler());
			row = hPlayer.getShip(shipSelection - 1).getRow();
			col = hPlayer.getShip(shipSelection - 1).getCol();
			setBoardPosition(playerBoard, hPlayer.getShip(shipSelection - 1).getHealth(), hPlayer.getShip(shipSelection - 1).getHOV(), hPlayer.getShip(shipSelection - 1).getName().charAt(0));
			for(int i = 0; i < hPlayer.getShip(shipSelection - 1).getHealth(); i++)
			{
				if(hPlayer.getShip(shipSelection-1).getHOV() == 'h')
				{
					Node myNode = getNodeByRowColumnIndex(row-1, col-1+i, humanGridPane);
					((Label)myNode).setText("|\t" + hPlayer.getShip(shipSelection - 1).getName().charAt(0));
				}
				else if(hPlayer.getShip(shipSelection-1).getHOV() == 'v')
				{
					Node myNode = getNodeByRowColumnIndex(row-1+i, col-1, humanGridPane);
					((Label)myNode).setText("|\t" + hPlayer.getShip(shipSelection - 1).getName().charAt(0));
				}
			}
			row = (int)(Math.random() * 10); 
			col = (int)(Math.random() * 10);
			if(cPlayer.attack(playerBoard, hPlayer, row, col))
			{
				instructionLabel.setText("You got hit by the opponent");
				Node myNode = getNodeByRowColumnIndex(row, col, humanGridPane);
				Label tempCLabel = (Label)myNode;
				tempCLabel.setText("|\tx");
				tempCLabel.setPadding(new Insets(0, 0, 20, 20));
				//humanGridPane.add(tempCLabel, col, row);
				hPlayer.setShipsRemaining();
				if(hPlayer.getShipsRemaining() == 0)
				{
					gameNotOver = false;
					instructionLabel.setText("You lost");
				}
			}
			else
			{
				instructionLabel.setText("CPU missed");
				Node myNode = getNodeByRowColumnIndex(row, col, humanGridPane);
				Label tempCLabel = (Label)myNode;
				tempCLabel.setText("|\tx");
				tempCLabel.setPadding(new Insets(0, 0, 20, 20));
				//humanGridPane.add(tempCLabel, col, row);
			}
			printBoard(playerBoard);
		}
	}
	
	class doubleAttackButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent arg0) {
			row = Integer.parseInt(rowTextField.getText());
			col = Integer.parseInt(colTextField.getText());
			rowTextField.setText("");
			colTextField.setText("");
			doubleAttackButtonCounter++;
			if(doubleAttackButtonCounter % 2 == 1)
			{
				if(hPlayer.attack(cpuBoard, cPlayer, row, col))
				{
					instructionLabel.setText("You hit an opponent!");
					Node myNode = getNodeByRowColumnIndex(row-1, col-1, humanHitGrid);
					Label tempLabel = (Label)myNode;
					tempLabel.setText("|\tH");
					tempLabel.setPadding(new Insets(0, 0, 20, 20));
					//humanHitGrid.add(tempLabel, col-1, row-1);
					cPlayer.setShipsRemaining();
					if(cPlayer.getShipsRemaining() == 0)
					{
						gameNotOver = false;
						instructionLabel.setText("You win!");
					}
				}
				else
				{
					instructionLabel.setText("You miss");
					Node myNode = getNodeByRowColumnIndex(row-1, col-1, humanHitGrid);
					Label tempLabel = (Label)myNode;
					tempLabel.setText("|\tM");
					tempLabel.setPadding(new Insets(0, 0, 20, 20));
				}
			}
			else if(doubleAttackButtonCounter % 2 == 0)
			{
				hPlayer.getPowerUpsList().remove(selection-1);
				if(hPlayer.attack(cpuBoard, cPlayer, row, col))
				{
					instructionLabel.setText("You hit an opponent!");
					Node myNode = getNodeByRowColumnIndex(row-1, col-1, humanHitGrid);
					Label tempLabel = (Label)myNode;
					tempLabel.setText("|\tH");
					tempLabel.setPadding(new Insets(0, 0, 20, 20));
					//humanHitGrid.add(tempLabel, col-1, row-1);
					cPlayer.setShipsRemaining();
					if(cPlayer.getShipsRemaining() == 0)
					{
						gameNotOver = false;
						instructionLabel.setText("You win!");
					}
				}
				else
				{
					instructionLabel.setText("You miss");
					Node myNode = getNodeByRowColumnIndex(row-1, col-1, humanHitGrid);
					Label tempLabel = (Label)myNode;
					tempLabel.setText("|\tM");
					tempLabel.setPadding(new Insets(0, 0, 20, 20));
				}
				powerUpButton.setText("Power-Ups");
				powerUpButton.setOnAction(new powerUpButtonHandler());
				row = (int)(Math.random() * 10); 
				col = (int)(Math.random() * 10);
				if(cPlayer.attack(playerBoard, hPlayer, row, col))
				{
					instructionLabel.setText("You got hit by the opponent");
					Node myNode = getNodeByRowColumnIndex(row, col, humanGridPane);
					Label tempCLabel = (Label)myNode;
					tempCLabel.setText("|\tx");
					tempCLabel.setPadding(new Insets(0, 0, 20, 20));
					//humanGridPane.add(tempCLabel, col, row);
					hPlayer.setShipsRemaining();
					if(hPlayer.getShipsRemaining() == 0)
					{
						gameNotOver = false;
						instructionLabel.setText("You lost");
					}
				}
				else
				{
					instructionLabel.setText("CPU missed");
					Node myNode = getNodeByRowColumnIndex(row, col, humanGridPane);
					Label tempCLabel = (Label)myNode;
					tempCLabel.setText("|\tx");
					tempCLabel.setPadding(new Insets(0, 0, 20, 20));
					//humanGridPane.add(tempCLabel, col, row);
				}
			}
		}
	}
}