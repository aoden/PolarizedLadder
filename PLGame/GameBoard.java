import java.awt.*;

public class GameBoard {

    public static final String BLANK_CELL = " ";
    public static final int ROWS = 8;
    public static final int COLS = 14;

    String[][] board;
    int heuristic;

    Point lastBoardPosition;

    int enptyCells;

    public GameBoard() {

        lastBoardPosition = new Point();

        board = new String[ROWS][COLS];

        for (int i = 0; i < ROWS; i++) {

            for (int j = 0; j < COLS; j++) {

                if ((j == 0) && (i == 0)) {
                    board[i][j] = " ";
                } else if ((i == 0) && (j >= 1)) {

                    board[i][j] = Integer.toString(j);
                    board[i][j] = Character.toString(Character.forDigit(j + 9, Character.MAX_RADIX)).toUpperCase();
                } else if ((j == 0) && (i >= 1)) {
                    board[i][j] = Integer.toString(i);
                } else if ((i >= 1) && (j >= 1)) {
                    board[i][j] = BLANK_CELL;
                }

                resetBoard();
            }
        }
    }


    public void resetBoard() {

        enptyCells = 49;

        for (int i = ROWS - 1; i >= 1; i--) {

            int jFrom = i;
            int jTo = COLS - i;

            for (; jFrom <= jTo; jFrom++) {

                board[i][jFrom] = "_";
            }
        }
    }

    public void print() {

        for (int i = ROWS - 1; i >= 0; i--) {

            for (int j = 0; j < COLS; j++) {
                System.out.print(board[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    private boolean isLegal(int i, int j) {

        try {
            if (board[j][i].equalsIgnoreCase(BLANK_CELL)) {
                return false;
            } else if (!board[j][i].equalsIgnoreCase("_")) {
                return false;
            } else {
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException aiob) {
            return false;
        }
    }

    public boolean setPosition(int i, int j, String mark) {

        if (isLegal(i, j)) {

            lastBoardPosition = new Point(i, j);

            board[j][i] = mark;
            enptyCells--;

            return true;
        } else {

            return false;
        }
    }

    public boolean isBoardFull() {

        if (enptyCells == 0) {

            return true;

        } else {

            return false;
        }
    }

    public String[][] getState() {
        return board.clone();
    }

    public void setState(String[][] board) {
        this.board = board;
    }

    public String[][] copyArray() {
        int length = board.length;
        String[][] target = new String[length][board[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(board[i], 0, target[i], 0, board[i].length);
        }
        return target;
    }

    public boolean setObjectPosition(Position p) {

        return setPosition(p.getX(), p.getY(), p.getMark());
    }

    public int getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

    public Point getLastBoardPosition() {
        return lastBoardPosition;
    }

    public void setLastBoardPosition(Point lastBoardPosition) {
        this.lastBoardPosition = lastBoardPosition;
    }

    public int getEnptyCells() {
        return enptyCells;
    }

    public void setEnptyCells(int enptyCells) {
        this.enptyCells = enptyCells;
    }
}
