import java.awt.*;
import java.util.Hashtable;
import java.util.Iterator;

public abstract class GameHeuristics {

    public String open = "open";
    public Hashtable<Point, String> openPoints;
    public Point winPatterns[][] = {

            {new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(2, 2)},
            {new Point(-1, 0), new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)},
            {new Point(-1, -1), new Point(0, -1), new Point(0, 0), new Point(1, 0), new Point(1, 1)},
            {new Point(-2, -1), new Point(-1, -1), new Point(-1, 0), new Point(0, 0), new Point(0, 1)},
            {new Point(-2, -2), new Point(-1, -2), new Point(-1, -1), new Point(0, -1), new Point(0, 0)},
            {new Point(0, 0), new Point(-1, 0), new Point(-1, 1), new Point(-2, 1), new Point(-2, 2)},
            {new Point(1, 0), new Point(0, 0), new Point(0, 1), new Point(-1, 1), new Point(-1, 2)},
            {new Point(1, -1), new Point(0, -1), new Point(0, 0), new Point(-1, 0), new Point(-1, 1)},
            {new Point(2, -1), new Point(1, -1), new Point(1, 0), new Point(0, 0), new Point(0, 1)},
            {new Point(2, -2), new Point(1, -2), new Point(1, -1), new Point(0, -1), new Point(0, 0)},
    };


    public GameHeuristics() {

        openPoints = new Hashtable<Point, String>();
        openPoints = initializeOpenPoints(openPoints);
    }

    Hashtable<Point, String> initializeOpenPoints(Hashtable<Point, String> openPoints) {

        openPoints = new Hashtable<Point, String>();
        Point p;

        for (int i = GameBoard.ROWS - 1; i >= 1; i--) {

            int to = GameBoard.COLS - i;
            for (int from = i; from <= to; from++) {

                p = new Point(from, i);
                openPoints.put(p, open);
            }
        }
        return openPoints;
    }

    void addPoint(Point p) {

        openPoints.put(p, open);
    }

    void addFinalPoint(Point p) {

        openPoints.put(p, open);
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public Iterator<Point> getIterator() {
        return openPoints.keySet().iterator();
    }

    public Hashtable<Point, String> getOpenPoints() {
        return openPoints;
    }

    public void setOpenPoints(Hashtable<Point, String> openPoints) {
        this.openPoints = openPoints;
    }

    public Point[][] getWinPatterns() {
        return winPatterns;
    }

    public void setWinPatterns(Point[][] winPatterns) {
        this.winPatterns = winPatterns;
    }
}
