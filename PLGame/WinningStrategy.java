import java.awt.*;

public class WinningStrategy extends GameHeuristics {
    private GameBoard gameBoard;
    private String[][] boardState;

    private Point leftSidePolarity[] = {new Point(1, 1), new Point(2, 2), new Point(3, 3),
            new Point(4, 4), new Point(5, 5), new Point(6, 6), new Point(7, 7)};

    private Point rightSidePolarity[] = {new Point(13, 1), new Point(12, 2), new Point(11, 3),
            new Point(10, 4), new Point(9, 5), new Point(8, 6), new Point(7, 7)};

    public WinningStrategy(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public WinningStrategy(String[][] board) {
        this.gameBoard = new GameBoard();
        this.gameBoard.setState(board);
    }

    public boolean detectLadder(String playerToken, String opposingPlayerToken, Point currPoint) {
        String token = " ";
        boardState = gameBoard.getState();

        for (int i = 0; i < winPatterns.length; i++) {
            int confirmedPoints = 0;

            for (int j = 0; j < winPatterns[0].length; j++) {
                int xPos = currPoint.x + winPatterns[i][j].x;
                int yPos = currPoint.y + winPatterns[i][j].y;

                try {
                    token = boardState[yPos][xPos];
                } catch (Exception e) {

                    token = " ";
                }

                if (token.equalsIgnoreCase(playerToken)) {
                    confirmedPoints++;
                } else {
                    confirmedPoints = 0;
                    break;
                }
            }

            if (confirmedPoints == 5) {

                Point[] ladder = new Point[5];
                String ladderDirection = (i > 4) ? "LEFT" : "RIGHT";

                for (int j = 0; j < winPatterns[0].length; j++) {
                    ladder[j] = new Point(currPoint.x + winPatterns[i][j].x, currPoint.y + winPatterns[i][j].y);
                }

                if (isPolarized(ladder, ladderDirection)) {

                    return true;
                } else {

                    return isWinBlocked(ladder, ladderDirection, opposingPlayerToken) ? false : true;
                }
            }
        }

        return false;
    }

    public boolean isPolarized(Point[] ladder, String ladderDirection) {

        if ((ladder[0].y == 1) && (ladder[1].y == 1)) {
            return true;
        } else if (ladderDirection.equalsIgnoreCase("RIGHT")) {
            for (int j = 0; j < (leftSidePolarity.length - 1); j++) {
                if (ladder[0].equals(leftSidePolarity[j])
                        && ladder[2].equals(leftSidePolarity[j + 1])
                        && ladder[4].equals(leftSidePolarity[j + 2])) {
                    System.out.println("Right Ladder Polarized!");
                    return true;
                }
            }
        } else if (ladderDirection.equalsIgnoreCase("LEFT")) {
            for (int j = 0; j < (rightSidePolarity.length - 1); j++) {
                if (ladder[0].equals(rightSidePolarity[j])
                        && ladder[2].equals(rightSidePolarity[j + 1])
                        && ladder[4].equals(rightSidePolarity[j + 2])) {
                    System.out.println("Left Ladder Polarized!");
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isWinBlocked(Point[] ladder, String ladderDirection, String opposingPlayerToken) {
        String[][] boardState = gameBoard.getState();

        if (ladderDirection.equalsIgnoreCase("LEFT")) {
            Point p1 = new Point(ladder[0].x - 2, ladder[0].y);
            Point p2 = new Point(ladder[0].x, ladder[0].y + 2);

            if ((boardState[p1.y][p1.x].equalsIgnoreCase(opposingPlayerToken)) &&
                    (boardState[p2.y][p2.x].equalsIgnoreCase(opposingPlayerToken))) {

                System.out.println("Left Win Blocked!");
                return true;
            }
        } else if (ladderDirection.equalsIgnoreCase("RIGHT")) {
            Point p1 = new Point(ladder[0].x + 2, ladder[0].y);
            Point p2 = new Point(ladder[0].x, ladder[0].y + 2);
            if ((boardState[p1.y][p1.x].equalsIgnoreCase(opposingPlayerToken)) &&
                    (boardState[p2.y][p2.x].equalsIgnoreCase(opposingPlayerToken))) {
                // win blocked
                System.out.println("Right Win Blocked!");
                return true;
            }
        }

        return false;
    }

}
