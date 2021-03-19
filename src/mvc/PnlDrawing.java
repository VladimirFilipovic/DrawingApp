package mvc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Console;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import Drawing.Circle;
import Drawing.DlgCircle;
import Drawing.DlgDonut;
import Drawing.DlgLine;
import Drawing.DlgPoint;
import Drawing.DlgRectangle;
import Drawing.Donut;
import Drawing.Line;
import Drawing.Point;
import Drawing.Rectangle;
import Drawing.Shape;

public class PnlDrawing extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8811855528689691721L;
	private DrawingModel model = new DrawingModel();
	
	public void setModel(DrawingModel model) {
		this.model = model;
	}

	public PnlDrawing() {
	}
	// TODO selektovanje vise objekata koji se stavljaj u niz i mogu da se obrisu
			// odjednom svi
			// TODO primeniti observer obrazac da se ne vidi button modifikaciej ako je
			// selektovano vise objekata

	public void paint(Graphics g) {
		super.paint(g);
		Iterator<Shape> it = model.getShapes().listIterator();
		while (it.hasNext())
			it.next().draw(g);
	}

}
