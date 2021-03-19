package Drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Circle extends ArealShape implements Serializable,Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7610671624156237649L;
	private Point center;
	private int radius;
	protected boolean drawForDonut; 
	

	public Circle() {
		
	}

	public Circle(Point center, int radius, Color innerColor, Color outLineColor) {
		super(innerColor, outLineColor);
		this.center = center;
		this.radius = radius;
	}

	public Circle(Point center, int radius, Color innerColor, Color outLineColor, boolean selected) {
		this(center, radius, innerColor, outLineColor);
		setSelected(selected);
	}

	@Override
	public void draw(Graphics g) {
		
		this.fillShape(g, innerColor);
		
		this.colorOutline(g, outlineColor);

		if (isSelected()) {
			this.selected(g);
		}
	}

	@Override
	public void selected(Graphics g) {
		g.setColor(Color.BLUE);
		g.drawRect(getCenter().getX() - 3, getCenter().getY() - 3, 6, 6);
		g.drawRect(getCenter().getX() + getRadius() - 3, getCenter().getY() - 3, 6, 6);
		g.drawRect(getCenter().getX() - getRadius() - 3, getCenter().getY() - 3, 6, 6);
		g.drawRect(getCenter().getX() - 3, getCenter().getY() + getRadius() - 3, 6, 6);
		g.drawRect(getCenter().getX() - 3, getCenter().getY() - getRadius() - 3, 6, 6);
		g.setColor(Color.BLACK);
		
	}

	@Override
	public void moveBy(int byX, int byY) {
		center.moveBy(byX, byY);
		
	}
	
	
	public int compareTo(Object o) {
		if (o instanceof Circle) {
			return (this.radius - ((Circle) o).radius);
		}
		return 0;
	}
	
	public boolean contains(Point p) {
		return center.distance(p.getX(), p.getY()) <= radius;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Circle) {
			Circle c = (Circle) obj;
			if (this.center.equals(c.getCenter()) && this.radius == c.getRadius()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public double area() {
		return radius * radius * Math.PI;
	}
	
	public Point getCenter() {
		return center;
	}
	public void setCenter(Point center) {
		this.center = center;
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		if(radius>=0)
			this.radius = radius;
	}
	
	public String toString() {
		return "Circle: " + "center x= " + center.getX() + ", center y= " + center.getY() + ", radius= " + radius +
				", Outline color= " + outlineColor.getRGB() + ", Inner color= " + innerColor.getRGB();
	}

	@Override
	public void fillShape(Graphics g, Color innerColor) {
		g.setColor(innerColor);
		g.fillOval(this.getCenter().getX() - this.radius, getCenter().getY() - getRadius(), this.getRadius()*2, this.getRadius()*2);
		
	}

	@Override
	public void colorOutline(Graphics g, Color outlineColor) {
		g.setColor(outlineColor);
		g.drawOval(this.getCenter().getX() - this.radius, getCenter().getY() - getRadius(), this.getRadius()*2, this.getRadius()*2);
	}
	
	public Circle clone() {
		Circle circle = new Circle();
		circle.setCenter(this.getCenter());
		circle.setRadius(this.getRadius());
		circle.setInnerColor(this.getInnerColor());
		circle.setOutlineColor(this.getOutlineColor());
		return circle;
	}

	@Override
	public Shape clone(Shape oldState) {
		Circle circle = (Circle)oldState;
		circle.setCenter(this.getCenter());
		circle.setRadius(this.getRadius());
		circle.setInnerColor(this.getInnerColor());
		circle.setOutlineColor(this.getOutlineColor());
		return circle;
	}
	
}
