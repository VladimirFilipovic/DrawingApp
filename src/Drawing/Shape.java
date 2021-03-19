package Drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public abstract class Shape implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -842510863257557764L;
	private boolean selected;

	public Shape() {
		
	}
	
	public Shape(boolean selected) {
		this.selected = selected;
	}
	
	public abstract boolean contains(Point p);
	public abstract void draw(Graphics g);
	public abstract void colorOutline (Graphics g, Color outlineColor);
	public abstract void selected(Graphics g);
	public abstract void moveBy(int byX, int byY);
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public abstract Shape clone(Shape oldState);
	public abstract Shape clone();

	
}
