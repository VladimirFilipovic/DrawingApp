package Drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.function.LongSupplier;

public class Point extends Shape implements Serializable, Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5911295653534513250L;
	private int x;
	private int y;
	private Color outlineColor;
	


	
	public Color getOutlineColor() {
		return outlineColor;
	}

	public void setOutlineColor(Color outlineColor) {
		this.outlineColor = outlineColor;
	}

	public Point() {
		
	}
	
	public Point(int x, int y) {
		this.x = x;
		setY(y);
	}
	
	public Point(int x, int y, boolean selected) {
		this(x, y);
		setSelected(selected);
	}

	public Point(int x, int y, Color outLineColor) {
		this(x,y);
		this.outlineColor = outLineColor;
	}

	@Override
	public void draw(Graphics g) {
		
		this.colorOutline(g, outlineColor);
		
		if (isSelected()) {
			this.selected(g);
		}
	}
	
	@Override
	public void colorOutline(Graphics g, Color outlineColor) {
		g.setColor(outlineColor);		
		g.drawLine(this.x-2, this.y, this.x+2, this.y);	
		g.drawLine(this.x, this.y-2, this.x, this.y+2);
		
	}

	@Override
	public void selected(Graphics g) {
		g.setColor(Color.BLUE);
		g.drawRect(this.x-3, this.y-3, 6, 6);
		g.setColor(Color.BLACK);
	}
		

	
	public void moveBy(int byX, int byY) {
	    this.x = this.x + byX;
		this.y += byY;
	}

	
	public int compareTo(Object o) {
		if (o instanceof Point) {
			Point start = new Point(0, 0);
			return (int) (this.distance(start.getX(), start.getY()) - ((Point) o).distance(start.getX(), start.getY()));
		}
		return 0;
	}
	
	public boolean contains(Point p) {
		return this.distance(p.getX(), p.getY()) <=3;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Point) {
			Point p = (Point) obj;
			if (this.x == p.getX() &&
					this.y == p.getY()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public double distance(int x2, int y2) {
		double dx = this.x - x2;
		double dy = this.y - y2;
		double d = Math.sqrt(dx*dx + dy*dy);
		return d;
	}
	
	public int getX() {
		return this.x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public String toString() {
		return "Point: " + "x= " +  this.x + "," + " y= " + this.y	+ ", Color= " + this.outlineColor.getRGB();
	}
	@Override
	public Point clone() {
			Point point = new Point();
			point.setX(this.getX()); 
			point.setY(this.getY());
			point.setOutlineColor(this.getOutlineColor());
			return point;
		}

	@Override
	public Shape clone(Shape oldState) {
		Point point = (Point)oldState;
		point.setX(this.getX()); 
		point.setY(this.getY());
		point.setOutlineColor(this.getOutlineColor());
		return point;
	}
}
