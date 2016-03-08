import java.util.Iterator;

public class MiniMaxAIPlayer {

	GameBoard maxGameBoard;
	GameBoard minGameBoard;

	public GameBoard getMaxMove(Node<GameBoard> n) {
		
		int max =  Integer.MIN_VALUE;
		int maxtemp = Integer.MIN_VALUE;

		Iterator<Node<GameBoard>> it = n.getChildren().iterator();
		maxGameBoard = it.next().getData();
		max = maxGameBoard.getHeuristic();

		while( it.hasNext() )
		{
			GameBoard gameBoardTemp = it.next().getData();
			maxtemp = gameBoardTemp.getHeuristic();
			if(maxtemp != 0){
			if(maxtemp >= max){
				maxGameBoard = gameBoardTemp;
				 max = maxtemp;
				 }
		    }
			n.getData().setHeuristic(max);
			maxGameBoard.setHeuristic(max);
		}
		return maxGameBoard;
	}

	public GameBoard getMinMove(Node<GameBoard> n) {

		int min =  Integer.MAX_VALUE;
		int mintemp = Integer.MAX_VALUE;
		Iterator<Node<GameBoard>> it = n.getChildren().iterator();
		minGameBoard = it.next().getData();
		min = minGameBoard.getHeuristic();

		while( it.hasNext() )
		{
			GameBoard gameBoardTemp = it.next().getData();
			mintemp = gameBoardTemp.getHeuristic();
			if(mintemp <= min){
				minGameBoard = gameBoardTemp;
				min = mintemp;
			}
			n.getData().setHeuristic(min);
			minGameBoard.setHeuristic(min);
		}
		return minGameBoard;
	}
}
