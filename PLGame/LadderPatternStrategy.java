import java.awt.*;

public class LadderPatternStrategy extends GameHeuristics {
    private Point ladderPatterns[][] = winPatterns;

    private int[] ladderDiscWeights = {0, 1, 2, 20, 100, 1000};

    public static void main(String[] args) {

        // Heuristics Test Case 1
        GameBoard gameBoardA = new GameBoard();

        gameBoardA.setPosition(2, 2, "*");
        gameBoardA.setPosition(3, 2, "*");

        gameBoardA.setPosition(7, 3, "o");
        gameBoardA.setPosition(8, 6, "o");

        gameBoardA.print();

        LadderPatternStrategy hA = new LadderPatternStrategy();
        Player pA = new Player("Player 1", '*', 22, gameBoardA);
        Player oA = new Player("Player 1", 'o', 22, gameBoardA);
        int scoreA = hA.calculate(pA, oA, gameBoardA);
        System.out.printf("Board A Heuristics Score = %d\n", scoreA);

        GameBoard gameBoardB = new GameBoard();

        gameBoardB.setPosition(8, 2, "*");
        gameBoardB.setPosition(8, 3, "*");
        gameBoardB.setPosition(9, 3, "*");
        gameBoardB.setPosition(8, 5, "*");

        gameBoardB.setPosition(6, 3, "o");
        gameBoardB.setPosition(7, 3, "o");
        gameBoardB.setPosition(7, 4, "o");
        gameBoardB.setPosition(8, 4, "o");
        gameBoardB.print();

        LadderPatternStrategy hB = new LadderPatternStrategy();
        Player pB = new Player("Player 1", '*', 22, gameBoardB);
        Player oB = new Player("Player 2", 'o', 22, gameBoardB);
        int scoreB = hB.calculate(pB, oB, gameBoardB);

        System.out.printf("Board B Heuristics Score = %d\n", scoreB);
    }

    public int calculate(Player p, Player o, GameBoard b) {
        int numPossLadders = 0;
        int ladderDiscs = 0;

        String[][] board = b.getState();

        String playerToken = p.getPlayerToken();
        String opponentToken = o.getPlayerToken();

        for (int i = b.ROWS - 1; i >= 1; i--) {
            int jFrom = i;
            int jTo = b.COLS - i;

            for (; jFrom <= jTo; jFrom++) {
                if ((board[i][jFrom]).equalsIgnoreCase(playerToken)) {

                    numPossLadders += detectPossibleLadders(playerToken, opponentToken, board, new Point(jFrom, i));
                    ladderDiscs += numberOfLadderDiscs(playerToken, opponentToken, board, new Point(jFrom, i));

                } else if ((board[i][jFrom]).equalsIgnoreCase(opponentToken)) {

                    numPossLadders -= detectPossibleLadders(opponentToken, playerToken, board, new Point(jFrom, i));
                    ladderDiscs -= numberOfLadderDiscs(opponentToken, playerToken, board, new Point(jFrom, i));
                }
            }
        }

        return (numPossLadders + ladderDiscs);
    }

    private int detectPossibleLadders(String playerToken, String opponentToken, String[][] board, Point boardPoint) {
        int possibleLadders = 0;

        for (int row = 0; row < ladderPatterns.length; row++) {

            Boolean currLadderBlocked = false;

            for (int col = 0; col < ladderPatterns[0].length; col++) {
                int xPos = boardPoint.x + ladderPatterns[row][col].x;
                int yPos = boardPoint.y + ladderPatterns[row][col].y;

                try {
                    if (col == 2) {
                        currLadderBlocked = isLadderPolarityBlocked(playerToken, opponentToken, board, new Point(xPos, yPos));

                        if (currLadderBlocked) {
                            break;
                        }
                    }

                    if ((board[yPos][xPos].equalsIgnoreCase(playerToken)) ||
                            (board[yPos][xPos].equalsIgnoreCase("_"))) {
                    } else {
                        currLadderBlocked = true;
                        break;
                    }
                } catch (Exception e) {
                    currLadderBlocked = true;
                    break;
                }
            }

            possibleLadders = (currLadderBlocked == true) ? possibleLadders : possibleLadders + 1;
        }

        return possibleLadders;
    }

    private int numberOfLadderDiscs(String playerToken, String opponentToken, String[][] board, Point boardPoint) {
        int numOfLadderDiscs = 0;

        for (int row = 0; row < ladderPatterns.length; row++)
        {
            Boolean currLadderBlocked = false;
            int numOfLadDiscsDetected = 0;

            for (int col = 0; col < ladderPatterns[0].length; col++)
            {
                int xPos = boardPoint.x + ladderPatterns[row][col].x;
                int yPos = boardPoint.y + ladderPatterns[row][col].y;

                try {
                    if (col == 2) {

                        currLadderBlocked = isLadderPolarityBlocked(playerToken, opponentToken, board, new Point(xPos, yPos));
                        if (currLadderBlocked) {
                            numOfLadDiscsDetected = 0;
                            break;
                        }
                    }

                    if ((board[yPos][xPos].equalsIgnoreCase(playerToken)) ||
                            (board[yPos][xPos].equalsIgnoreCase("_"))) {
                        if ((board[yPos][xPos].equalsIgnoreCase(playerToken))) {
                            numOfLadDiscsDetected++;
                        }
                    } else {
                        currLadderBlocked = true;
                        numOfLadDiscsDetected = 0;
                        break;
                    }
                } catch (Exception e) {

                    currLadderBlocked = true;
                    numOfLadDiscsDetected = 0;
                    break;
                }
            }

            numOfLadderDiscs = (currLadderBlocked == false) ?
                    numOfLadderDiscs + ladderDiscWeights[numOfLadDiscsDetected] :
                    numOfLadderDiscs;
        }

        return numOfLadderDiscs;
    }

    public boolean isLadderPolarityBlocked(String playerToken, String opponentToken, String[][] board, Point boardPoint) {

        Point p1 = new Point(boardPoint.x - 1, boardPoint.y - 1);
        Point p2 = new Point(boardPoint.x + 1, boardPoint.y + 1);

        if ((board[p1.y][p1.x].equalsIgnoreCase(opponentToken)) &&
                (board[p2.y][p2.x].equalsIgnoreCase(opponentToken))) {
            // ladder polarized?
            boolean isPolarizable = isPolarizable(playerToken, opponentToken, board, boardPoint);

            if (isPolarizable) {
                return false;
            }
            return true;
        }

        Point p3 = new Point(boardPoint.x - 1, boardPoint.y + 1);
        Point p4 = new Point(boardPoint.x + 1, boardPoint.y - 1);

        if ((board[p3.y][p3.x].equalsIgnoreCase(opponentToken)) &&
                (board[p4.y][p4.x].equalsIgnoreCase(opponentToken))) {

            boolean isPolarizable = isPolarizable(playerToken, opponentToken, board, boardPoint);

            if (isPolarizable) {
                return false;
            }
            return true;
        }

        return false;
    }

    public boolean isPolarizable(String playerToken, String opponentToken, String[][] board, Point boardPoint) {
        if (!(board[boardPoint.y - 1][boardPoint.x].equalsIgnoreCase(opponentToken)) &&
                !(board[boardPoint.y - 1][boardPoint.x - 1].equalsIgnoreCase(opponentToken)) &&
                (boardPoint.y - 1 == 1)) {

            return true;
        }
        if (!(board[boardPoint.y - 1][boardPoint.x].equalsIgnoreCase(opponentToken)) &&
                !(board[boardPoint.y - 1][boardPoint.x + 1].equalsIgnoreCase(opponentToken)) &&
                (boardPoint.y - 1 == 1)) {
            return true;
        }
        return false;
    }
}
