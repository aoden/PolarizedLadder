import java.awt.*;
import java.util.Iterator;


public class AIPlayer extends Player {

    private char AIPlayerToken;
    private String AIPlayerString;
    private String OpponentString;
    private int maxDepth;
    private MiniMaxAIPlayer minimax;

    private LadderPatternStrategy heuristics;

    private AIPlayer aip;
    private Player p;

    private GameBoard nextGameBoard;

    public AIPlayer() {

        super();
    }

    public AIPlayer(String playerName, char playerToken, int discs, GameBoard gameBoard) {

        super.playerName = playerName;
        super.playerToken = playerToken;
        super.discs = discs;
        this.gameBoard = gameBoard;
        nextGameBoard = new GameBoard();
        heuristics = new LadderPatternStrategy();
        minimax = new MiniMaxAIPlayer();
        AIPlayerToken = playerToken;
        AIPlayerString = String.valueOf(AIPlayerToken);

        System.out.println("AIPlayer " + playerName);
    }

    public Point doAIPlayerTurn(AIPlayer ai, Player p, SearchLists searchList) {

        Point discCoordinates;

        this.aip = ai;
        this.p = p;
        this.OpponentString = String.valueOf(p.playerToken);


        try {
            discCoordinates = aip.move(this.gameBoard, searchList);
            searchList.removePoint(discCoordinates, searchList);
            return discCoordinates;
        } catch (ArrayIndexOutOfBoundsException aiob) {
            System.out.println("Invalid move.");
            return doPlayerTurn(ai);
        } catch (NumberFormatException nf) {
            System.out.println("Invalid move.");
            return doPlayerTurn(ai);
        }

    }

    private Point move(GameBoard gameBoard, SearchLists searchList) {

        int startTreeDepth = 1;
        int emptySpaces = gameBoard.getEnptyCells();
        if (emptySpaces > 32) {
            maxDepth = 2;
            System.out.println("Depth : " + maxDepth);
        } else if (emptySpaces > 12) {
            maxDepth = 3;
            System.out.println("Depth : " + maxDepth);
        } else if (emptySpaces > 4) {


            maxDepth = 4;
            System.out.println("Depth : " + maxDepth);
        } else {

            maxDepth = 1;
            System.out.println("Depth : " + maxDepth);
        }

        TreeImpl<GameBoard> searchTreeImpl = createTree(gameBoard);

        createStateSpace(searchTreeImpl.getRoot(), searchList, startTreeDepth, AIPlayerString, gameBoard);

        return nextGameBoard.getLastBoardPosition();

    }

    public TreeImpl<GameBoard> createTree(GameBoard gameBoard) {

        TreeImpl<GameBoard> searchTreeImpl = new TreeImpl<GameBoard>(gameBoard);
        Node<GameBoard> root = new Node<GameBoard>();

        searchTreeImpl.setRoot(root);
        root.setData(gameBoard);

        return searchTreeImpl;
    }


    public void createStateSpace(Node<GameBoard> parentNode, SearchLists searchList, int depthOfTree, String currPlayer, GameBoard oldState) {

        Iterator<Point> openPoints = searchList.getIterator();


        if (depthOfTree == maxDepth) {
            while (openPoints.hasNext()) {
                GameBoard newGameBoard = new GameBoard();
                newGameBoard.setState(oldState.copyArray());

                Point nextPoint = openPoints.next();
                Position nextPosition = new Position(nextPoint, currPlayer);

                if (newGameBoard.setObjectPosition(nextPosition)) {
                    newGameBoard.setHeuristic(heuristics.calculate((Player) aip, this.p, newGameBoard));
                    Node<GameBoard> nextChild = new Node<GameBoard>();
                    nextChild.setParent(parentNode);
                    nextChild.setData(newGameBoard);
                    parentNode.addChild(nextChild);

                }
            }

            miniMaxMove(parentNode, depthOfTree);    // minimax

        } else {
            while (openPoints.hasNext()) {

                GameBoard newGameBoard = new GameBoard();
                newGameBoard.setState(oldState.copyArray());
                Point nextPoint = openPoints.next();
                Position nextPosition = new Position(nextPoint, currPlayer);                            // get next move

                if (newGameBoard.setObjectPosition(nextPosition)) {
                    Node<GameBoard> nextChild = new Node<GameBoard>();
                    nextChild.setParent(parentNode);
                    nextChild.setData(newGameBoard);
                    parentNode.addChild(nextChild);
                }
            }

            for (Node<GameBoard> childNode : parentNode.getChildren()) {

                String nextToken = (currPlayer == AIPlayerString) ? OpponentString : AIPlayerString;

                createStateSpace(childNode, searchList, depthOfTree + 1, nextToken, childNode.getData());
            }
        }
    }

    public void miniMaxMove(Node<GameBoard> parentNode, int depthOfTree) {

        if (depthOfTree == 1) {
            GameBoard maxGameBoard = minimax.getMaxMove(parentNode);
            parentNode.setData(maxGameBoard);
            nextGameBoard = maxGameBoard;

            return;
        }
        if (depthOfTree % 2 == 1) {

            GameBoard maxGameBoard = minimax.getMaxMove(parentNode);

            parentNode.setHeuristic(maxGameBoard.getHeuristic());
            miniMaxMove(parentNode.getParent(), depthOfTree - 1);

        } else if (depthOfTree % 2 == 0) {

            GameBoard minGameBoard = minimax.getMinMove(parentNode);
            parentNode.setHeuristic(minGameBoard.getHeuristic());
            miniMaxMove(parentNode.getParent(), depthOfTree - 1);
        }

    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }


}
