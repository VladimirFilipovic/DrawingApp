package Drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Rectangle extends ArealShape {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4847654358858524893L;
	private Point upperLeftPoint;
	private int width;
	private int height;


	public Color getOutlineColor() {
		return outlineColor;
	}

	public void setOutlineColor(Color outlineColor) {
		this.outlineColor = outlineColor;
	}

	public Color getInnerColor() {
		return innerColor;
	}

	public void setInnerColor(Color innerColor) {
		this.innerColor = innerColor;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Rectangle() {

	}

	public Rectangle(Point upperLeftPoint, int height, int width, Color innerColor, Color outlineColor) {
		super(innerColor, outlineColor);
		this.upperLeftPoint = upperLeftPoint;
		this.height = height;
		this.width = width;

	}

	public Rectangle(Point upperLeftPoint, int height, int width, boolean selected, Color innerColor,
			Color outlineColor) {
		this(upperLeftPoint, height, width, innerColor, outlineColor);

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
	public void colorOutline(Graphics g, Color outlineColor) {
		g.setColor(outlineColor);
		g.drawRect(this.getUpperLeftPoint().getX(), this.getUpperLeftPoint().getY(), this.getWidth(), this.height);

	}

	@Override
	public void selected(Graphics g) {
		g.setColor(Color.BLUE);
		g.drawRect(getUpperLeftPoint().getX() - 3, getUpperLeftPoint().getY() - 3, 6, 6);
		g.drawRect(this.getUpperLeftPoint().getX() - 3 + getWidth(), this.getUpperLeftPoint().getY() - 3, 6, 6);
		g.drawRect(this.getUpperLeftPoint().getX() - 3, this.getUpperLeftPoint().getY() - 3 + getHeight(), 6, 6);
		g.drawRect(this.getUpperLeftPoint().getX() + getWidth() - 3, this.getUpperLeftPoint().getY() + getHeight() - 3,
				6, 6);
		g.setColor(Color.BLACK);

	}

	@Override
	public void fillShape(Graphics g, Color innerColor) {
		g.setColor(innerColor);
		g.fillRect(this.getUpperLeftPoint().getX(), this.getUpperLeftPoint().getY(), this.getWidth(), this.height);

	}

	@Override
	public void moveBy(int byX, int byY) {
		upperLeftPoint.moveBy(byX, byY);

	}

	public int compareTo(Object o) {
		if (o instanceof Rectangle) {
			return (this.area() - ((Rectangle) o).area());
		}
		return 0;
	}

	public boolean contains(Point p) {
		if (this.getUpperLeftPoint().getX() <= p.getX() && p.getX() <= this.getUpperLeftPoint().getX() + width
				&& this.getUpperLeftPoint().getY() <= p.getY()
				&& p.getY() <= this.getUpperLeftPoint().getY() + height) {
			return true;
		} else {
			return false;
		}
	}

	public boolean equals(Object obj) {
		if (obj instanceof Rectangle) {
			Rectangle r = (Rectangle) obj;
			if (this.upperLeftPoint.equals(r.getUpperLeftPoint()) && this.height == r.getHeight()
					&& this.width == r.getWidth()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public int area() {
		return width * height;
	}

	public Point getUpperLeftPoint() {
		return upperLeftPoint;
	}

	public void setUpperLeftPoint(Point upperLeftPoint) {
		this.upperLeftPoint = upperLeftPoint;
	}

	public String toString() {
		return "Rectangle: " + "x= " + upperLeftPoint.getX() + ", y= " + upperLeftPoint.getY() +  ", height= " + height + ", width= " + width 
				+ ", Outline color= " + outlineColor.getRGB() + ", Inner color= " + innerColor.getRGB();
	}
	public Rectangle clone() {
		Rectangle rectangle = new Rectangle(new Point(), height, width, innerColor, outlineColor);
		rectangle.getUpperLeftPoint().setX(this.getUpperLeftPoint().getX());
		rectangle.getUpperLeftPoint().setY(this.getUpperLeftPoint().getY());
		rectangle.setHeight(this.getHeight());
		rectangle.setWidth(this.getWidth());
		rectangle.setOutlineColor(this.getOutlineColor());
		rectangle.setInnerColor(this.getInnerColor());
		return rectangle;
	}

	@Override
	public Shape clone(Shape oldState) {
		Rectangle rectangle = (Rectangle) oldState;
		rectangle.setUpperLeftPoint(this.getUpperLeftPoint());
		rectangle.getUpperLeftPoint().setX(this.getUpperLeftPoint().getX());
		rectangle.getUpperLeftPoint().setY(this.getUpperLeftPoint().getY());
		rectangle.setHeight(this.getHeight());
		rectangle.setWidth(this.getWidth());
		rectangle.setOutlineColor(this.getOutlineColor());
		rectangle.setInnerColor(this.getInnerColor());
		return rectangle;
	}

}
