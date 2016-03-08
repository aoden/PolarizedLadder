

import java.awt.Point;

public class Position {

	int x;
	int y;
	String mark; 
	Point point;


	public Position(int x, int y, String mark) {

		this.point.x = x;
		this.point.y = y;
	  this.mark = mark;
		this.point = new Point(x, y);
 }

 public Position(Point p, String mark){
	 this.x = p.x;
	 this.y = p.y;
	  this.mark = mark;
	 
}

	public int getX() {
		return x;
}


	public void setX(int x) {
		this.x = x;
}


	public int getY() {
		return y;
}


	public void setY(int y) {
		this.y = y;
}


public String getMark() {
	return mark;
}


public void setMark(String mark) {
	this.mark = mark;
}

public Point getPoint() {
	return point;
}

public void setPoint(Point point) {
	this.point = point;
}
 
 
}
