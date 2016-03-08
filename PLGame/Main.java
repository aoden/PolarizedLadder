

public class Main {

public static void main(String[] args) {

	Game game = new Game();
	GameBoard gameBoard = new GameBoard();

	WinningStrategy detectWin = new WinningStrategy(gameBoard);
		int gameType = game.displayMainMenu();
	Player[] players = game.setGameType(gameType, gameBoard, detectWin);
	game.startGame(game, gameBoard, players, detectWin, gameType);

		}
}
