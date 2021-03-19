package adapter;

import java.awt.Color;
import java.awt.Graphics;

import Drawing.ArealShape;
import Drawing.Line;
import Drawing.Point;
import Drawing.Shape;
import hexagon.Hexagon;

public class HexagonAdapter extends ArealShape {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8711591139127721474L;
	private Hexagon hexagon;

	public HexagonAdapter() {
		hexagon = new Hexagon(0, 0, 0);
	}

	public HexagonAdapter(int x, int y, int lenght, Color areaColor, Color borderColor) {
		hexagon = new Hexagon(x, y, lenght);
		hexagon.setAreaColor(areaColor);
		hexagon.setBorderColor(borderColor);
	}

	@Override
	public void fillShape(Graphics g, Color innerColor) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean contains(Point p) {
		return hexagon.doesContain(p.getX(), p.getY());
	}

	@Override
	public void draw(Graphics g) {
		hexagon.paint(g);
	}

	@Override
	public void colorOutline(Graphics g, Color outlineColor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void selected(Graphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSelected(boolean selected) {
		hexagon.setSelected(selected);
	}

	@Override
	public void moveBy(int byX, int byY) {
		// TODO Auto-generated method stub

	}

	public int getX() {
		return hexagon.getX();
	}

	public int getY() {
		return hexagon.getY();
	}

	public int getLength() {
		return hexagon.getR();
	}

	@Override
	public Color getOutlineColor() {
		return hexagon.getBorderColor();
	}

	@Override
	public Color getInnerColor() {
		return hexagon.getAreaColor();
	}

	public void setX(int x) {
		hexagon.setX(x);
	}

	public void setY(int y) {
		hexagon.setY(y);
	}

	public void setLength(int length) {
		hexagon.setR(length);
	}
	
	public void setOutlineColor(Color outlineColor) {
		hexagon.setBorderColor(outlineColor);
	}

	@Override
	public void setInnerColor(Color innerColor) {
		hexagon.setAreaColor(innerColor);
	}

	public boolean equals(Object obj) {
		if (obj instanceof HexagonAdapter) {
			HexagonAdapter hexagon2 = (HexagonAdapter) obj;
			if (this.getX() == hexagon2.getX() && this.getY() == hexagon2.getY()
					&& this.getLength() == hexagon2.getLength()) {
				System.out.println("trueee hex");
				return true;
			}
		}
		return false;
	}

	public String toString() {
		return "Hexagon: " + "x= " + hexagon.getX() + ", y= " + hexagon.getY() + ", length= " + hexagon.getR()
				+ ", Outline color= " + hexagon.getBorderColor().getRGB() + ", Inner color="
				+ hexagon.getAreaColor().getRGB();

	}
	public HexagonAdapter clone() {
		HexagonAdapter hexagon2 = new HexagonAdapter();
		hexagon2.setX(this.getX());
		hexagon2.setY(this.getY());
		hexagon2.setLength(this.getLength());
		hexagon2.setOutlineColor(this.getOutlineColor());
		hexagon2.setInnerColor(this.getInnerColor());
		return hexagon2;
	}

	@Override
	public Shape clone(Shape oldState) {
		HexagonAdapter hexagon2 = (HexagonAdapter) oldState;
		hexagon2.setX(this.getX());
		hexagon2.setY(this.getY());
		hexagon2.setLength(this.getLength());
		hexagon2.setOutlineColor(this.getOutlineColor());
		hexagon2.setInnerColor(this.getInnerColor());
		return hexagon2;
	}

}
