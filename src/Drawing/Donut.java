package Drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Donut extends Circle implements Cloneable {
/**
	 * 
	 */
	private static final long serialVersionUID = -6573902525232809995L;
private int innerRadius;
	

	public Donut() {
		
	}
	
	public Donut(Point center, int radius, int innerRadius) {
		super(center, radius, Color.white, Color.white);
		this.innerRadius = innerRadius;
		
		
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected) {
		this(center, radius, innerRadius);
		setSelected(selected);
	}
	
	public void draw(Graphics g) {
		
		
		Area donut = createDonut();
		
		this.fillShape(g, innerColor, donut);
		
		this.colorOutline(g, outlineColor, donut);

		if (isSelected()) {
			this.selected(g);
		}
	}
	
	private Area createDonut() {
		int centerX = super.getCenter().getX();
		int centerY = super.getCenter().getY();
		int outerRadius = super.getRadius();
		//zasto se oduzima outer radius -> zbog toga sto drawoval popunjava krug u okviru pravouganika 
		//posto imamo gornju levo pravougaonika 
		Ellipse2D outer = new Ellipse2D.Double(
	            centerX - outerRadius, 
	            centerY - outerRadius,
	            2 * outerRadius, 
	            2 * outerRadius);
		 Ellipse2D inner = new Ellipse2D.Double(
		            centerX - innerRadius , 
		            centerY - innerRadius,
		            2 * innerRadius, 
		            2 * innerRadius);
		Area outerCircleArea = new Area(outer);
		outerCircleArea.subtract(new Area(inner));
		return outerCircleArea;
	}
	
	public int compareTo(Object o) {
		if (o instanceof Donut) {
			return (int) (this.area() - ((Donut) o).area());
		}
		return 0;
	}
	public boolean contains(Point p) {
		double dFromCenter = this.getCenter().distance(p.getX(), p.getY());
		return dFromCenter > innerRadius &&
				super.contains(p);
	}
	
	public double area() {
		return super.area() - innerRadius * innerRadius * Math.PI;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Donut) {
			Donut d = (Donut) obj;
			if (this.getCenter().equals(d.getCenter()) &&
					this.getRadius() == d.getRadius() &&
					this.innerRadius == d.getInnerRadius()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public int getInnerRadius() {
		return innerRadius;
	}
	public void setInnerRadius(int innerRadius) {
		this.innerRadius = innerRadius;
		
	}
	
	public String toString() {
		return "Donut: " + "center x= " + super.getCenter().getX() + ", center y= " + super.getCenter().getY() + ", outer radius= " 
				+ super.getRadius() + ", inner radius= " + innerRadius + ", Outline color= " + 
				this.outlineColor.getRGB() + ", Inner color= " + this.innerColor.getRGB();
				
	}

	
	private void fillShape(Graphics g, Color innerColor, Area donut) {
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setColor(innerColor);
		graphics2d.fill(donut);
		
	}

	private void colorOutline(Graphics g, Color outlineColor, Area donut) {
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setColor(outlineColor);
		graphics2d.draw(donut);
		
	}
	
	public void selected(Graphics g) {
		g.setColor(Color.BLUE);
		g.drawRect(getCenter().getX() - 3, getCenter().getY() - 3, 6, 6);
		g.drawRect(getCenter().getX() + getRadius() - 3, getCenter().getY() - 3, 6, 6);
		g.drawRect(getCenter().getX() - getRadius() - 3, getCenter().getY() - 3, 6, 6);
		g.drawRect(getCenter().getX() - 3, getCenter().getY() + getRadius() - 3, 6, 6);
		g.drawRect(getCenter().getX() - 3, getCenter().getY() - getRadius() - 3, 6, 6);
		g.setColor(Color.BLACK);
	}
	public Donut clone() {
		Donut donut = new Donut();
		donut.setCenter(this.getCenter());
		donut.setRadius(this.getRadius());
		donut.setInnerRadius(this.getInnerRadius());
		donut.setInnerColor(this.getInnerColor());
		donut.setOutlineColor(this.getOutlineColor());
		return donut;
	}
	public Donut clone(Shape oldState) {
		Donut donut = (Donut)oldState;
		donut.setCenter(this.getCenter());
		donut.getCenter().setX(this.getCenter().getX());
		donut.getCenter().setY(this.getCenter().getY());
		donut.setRadius(this.getRadius());
		donut.setInnerRadius(this.getInnerRadius());
		donut.setInnerColor(this.getInnerColor());
		donut.setOutlineColor(this.getOutlineColor());
		return donut;
	}
	
}
