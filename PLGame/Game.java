
import java.awt.Point;
import java.util.Scanner;
import java.util.Calendar;

public class Game {

	SearchLists searchList;
	private boolean gameOver;
	private int playerTurn;

	public Game() {
		searchList = new SearchLists();
	}

	private static void printGameOver(Player player, GameBoard gameBoard) {

		gameBoard.print();
		System.out.printf("%s Wins the Game! \n", player.getPlayerName());
	}

	public void startGame(Game game, GameBoard gameBoard, Player[] players, WinningStrategy detectWin, int gameType) {
		if(gameType == 1)
			game.startManualGame(gameBoard, players, detectWin);
		if(gameType == 2)
			game.startHumanVsAIGame(gameBoard, players, detectWin);
	}

	public void startManualGame(GameBoard gameBoard, Player[] players, WinningStrategy detectWin) {

		setGameOver(false);
		setPlayerTurn(0);

		while (!isGameOver()) {

			gameBoard.print();

			if (gameBoard.isBoardFull()) {

				System.out.printf("The game ended in a draw!\n");
				setGameOver(true);
			}
			if (getPlayerTurn() == 0) {
				onNextMove(players, detectWin, gameBoard, getPlayerTurn());
			} else {
				onNextMove(players, detectWin, gameBoard, getPlayerTurn());
			}
		}

		System.out.printf("Game over!\n");
	}

	public void startHumanVsAIGame(GameBoard gameBoard, Player[] players, WinningStrategy detectWin) {

		setGameOver(false);
		setPlayerTurn(0);

		while (!isGameOver()) {

			gameBoard.print();

			// TODO: add win condition check
			if (gameBoard.isBoardFull()) {

				System.out.printf("The game ended in a draw!\n");
				setGameOver(true);
			}

			if (playerTurn == 0) {
				onNextMove(players, detectWin, gameBoard, getPlayerTurn());

			} else {

				Calendar calStartTime = Calendar.getInstance();

				computeNextMove(players, detectWin, gameBoard, getPlayerTurn());

				Calendar calEndTime = Calendar.getInstance();
				System.out.printf("AI Move Duration = %.2f %s\n",    // output move calculation duration
						(float) (calEndTime.getTimeInMillis() - calStartTime.getTimeInMillis()) / 1000,
						"seconds");
			}
		}

		System.out.printf("Game over!\n");
	}

	public void onNextMove(Player[] players, WinningStrategy detectWin, GameBoard gameBoard, int playerTurn) {

		Point playerMove = new Point();

		while (!(players[playerTurn].setDisc(playerMove.y, playerMove.x))) {

			playerMove = players[playerTurn].doPlayerTurn(players[playerTurn]);
		}

		int opPlayerTurn = (playerTurn == 0) ? 1 : 0;
		if (detectWin.detectLadder(players[playerTurn].getPlayerToken(),
				players[opPlayerTurn].getPlayerToken(), playerMove) ){

			printGameOver(players[playerTurn], gameBoard);
			setGameOver(true);
		}
		setNextPlayerTurn(playerTurn);
	}

	public void computeNextMove(Player[] players, WinningStrategy detectWin, GameBoard gameBoard, int playerTurn) {

		Point AIPlayerMove = ((AIPlayer) players[playerTurn]).doAIPlayerTurn((AIPlayer)players[playerTurn], players[getNextPlayerTurn(playerTurn)], searchList);

		if (players[playerTurn].setDisc(AIPlayerMove.y, AIPlayerMove.x) == false)
		{
			((AIPlayer) players[playerTurn]).doAIPlayerTurn((AIPlayer)players[playerTurn], players[getNextPlayerTurn(playerTurn)], searchList);
		}

		int opPlayerTurn = (playerTurn == 0) ? 1 : 0;
		if (detectWin.detectLadder(players[playerTurn].getPlayerToken(),
				players[opPlayerTurn].getPlayerToken(), AIPlayerMove)) {
			printGameOver(players[playerTurn], gameBoard);
			setGameOver(true);
		}
		setNextPlayerTurn(playerTurn);
	}

	public int displayMainMenu() {

		System.out.println("Polarized Ladders Game");
		System.out.println("Please select game type:");
		System.out.println("1 - Human vs Human");
		System.out.println("2 - Human vs AI");

		Scanner input = new Scanner(System.in);
		return input.nextInt();
	}

	Player[] setGameType(int gameType, GameBoard gameBoard, WinningStrategy detectWin) {

		Player[] players = new Player[2];

		if (gameType == 1) {

			// instantiate human players
			players[0] = new Player("Player One", 'o', 22, gameBoard);
			players[1] = new Player("Player Two", '*', 22, gameBoard);
		}

		if(gameType == 2){

			players[0] = new Player("Player One", 'o', 22, gameBoard);
			players[1] = new AIPlayer("Player Two", '*', 22, gameBoard);
		}

		if(gameType == 3){

			players[0] = new AIPlayer("Player One", 'o', 22, gameBoard);
			players[1] = new Player("Player Two", '*', 22, gameBoard);
		}

		else if (gameType == 4) {

			System.out.println("Quiting Game.");
			System.exit(0);

		} else if (gameType > 4)  {

			System.out.println("Game type not availabe at this time.");
			System.exit(0);
		}

		return players;

	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public int getPlayerTurn() {
		return playerTurn;
	}

	public void setPlayerTurn(int playerTurn) {
		this.playerTurn = playerTurn;
	}

	public void setNextPlayerTurn(int playerTurn){
		if(playerTurn == 0)
			setPlayerTurn(1);
		else 
			if(playerTurn == 1)
				setPlayerTurn(0);
	}
	public int getNextPlayerTurn(int playerTurn){
		int nextPlayer=0;
		if(playerTurn == 0)
			nextPlayer =1;
		else 
			if(playerTurn == 1)
				nextPlayer =0;
		return nextPlayer;
	}
}
