package Drawing;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Shape {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2259979842510802821L;
	private Point startPoint;
	private Point endPoint;
	private Color outlineColor;
	
	

	public Color getOutlineColor() {
		return outlineColor;
	}

	public void setOutlineColor(Color outlineColor) {
		this.outlineColor = outlineColor;
	}

	public Line() {
		
	}
	
	public Line(Point startPoint, Point endPoint) {
		this.startPoint = startPoint;
		setEndPoint(endPoint);
	}
	
	public Line(Point startPoint, Point endPoint, boolean selected) {
		this(startPoint, endPoint);
		setSelected(selected);
	}

	public Line(Point p1, Point p2, Color outLineColor) {
		this(p1,p2);
		this.outlineColor = outLineColor;
	}

	@Override
	public void draw(Graphics g) {
		this.colorOutline(g, outlineColor);
		
		if (isSelected()) {
			this.selected(g);
		}
		
	}

	public void selected(Graphics g) {
		g.setColor(Color.BLUE);
		g.drawRect(getStartPoint().getX()  - 3, getStartPoint().getY() - 3, 6, 6);
		g.drawRect(getEndPoint().getX() - 3, getEndPoint().getY() - 3, 6, 6);
		g.drawRect(middleOfLine().getX() - 3, middleOfLine().getY() - 3, 6, 6);
		g.setColor(Color.BLACK);
		
	}

	public Point middleOfLine() {
		int middleByX = (this.getStartPoint().getX() + this.getEndPoint().getX()) / 2;
		int middleByY = (this.getStartPoint().getY() + this.getEndPoint().getY()) / 2;
		Point p = new Point(middleByX, middleByY);
		return p;
	}
	
	
	public int compareTo(Object o) {
		if (o instanceof Line) {
			return (int) (this.length() - ((Line) o).length());
		}
		return 0;
	}
	
	@Override
	public void moveBy(int byX, int byY) {
		startPoint.moveBy(byX, byY);
		endPoint.moveBy(byX, byY);
	}
	public boolean contains(Point p) {
		if((startPoint.distance(p.getX(), p.getY()) + endPoint.distance(p.getX(), p.getY())) - length() <= 0.05)
			return true;
		return false;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Line) {
			Line l = (Line) obj;
			if (this.startPoint.equals(l.getStartPoint()) &&
					this.endPoint.equals(l.getEndPoint())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public double length() {
		return startPoint.distance(endPoint.getX(), endPoint.getY());
	}
	
	public Point getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}
	public Point getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}
	
	public String toString() {
		return "Line: " + "start x= " +  startPoint.getX() + ", " + "start y= " + this.startPoint.getY() + 
				", end x= " +  endPoint.getX() + ", " + "end y= " + this.endPoint.getY() + ", Color= " + this.outlineColor.getRGB();
	}

	@Override
	public void colorOutline(Graphics g, Color outlineColor) {
		g.setColor(outlineColor);
		g.drawLine(this.getStartPoint().getX(), getStartPoint().getY(), this.getEndPoint().getX(), this.getEndPoint().getY());
		
	}

	public Shape clone() {
		Line line = new Line();
		line.setStartPoint(this.startPoint);
		line.setEndPoint(this.endPoint);
		line.getStartPoint().setX(this.getStartPoint().getX());
		line.getStartPoint().setY(this.getStartPoint().getY());
		line.getEndPoint().setX(this.getEndPoint().getX());
		line.getEndPoint().setY(this.getEndPoint().getY());
		line.setOutlineColor(this.getOutlineColor());
		return line;
	}
	@Override
	public Shape clone(Shape oldState) {
		Line line = (Line)oldState;
		line.setStartPoint(this.startPoint);
		line.setEndPoint(this.endPoint);
		line.getStartPoint().setX(this.getStartPoint().getX());
		line.getStartPoint().setY(this.getStartPoint().getY());
		line.getEndPoint().setX(this.getEndPoint().getX());
		line.getEndPoint().setY(this.getEndPoint().getY());
		line.setOutlineColor(this.getOutlineColor());
		return line;
	}
}
