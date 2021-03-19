package Drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public abstract class ArealShape extends Shape implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2246068050996018246L;
	protected Color innerColor;
	protected Color outlineColor;

	public Color getInnerColor() {
		return innerColor;
	}

	public void setInnerColor(Color innerColor) {
		this.innerColor = innerColor;
	}

	public Color getOutlineColor() {
		return outlineColor;
	}

	public void setOutlineColor(Color outlineColor) {
		this.outlineColor = outlineColor;
	}

	public ArealShape() {

	}

	public ArealShape(Color innerColor, Color outlineColor) {
		this.innerColor = innerColor;
		this.outlineColor = outlineColor;
	}

	public abstract void fillShape(Graphics g, Color innerColor);

}
