package mvc;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Drawing.Shape;

public class DrawingModel implements Serializable {
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 9120251303893575205L;
	private List <Shape> shapes = new ArrayList<Shape>();
	private List <Shape> selectedShapes = new ArrayList<Shape>();
	
	private PropertyChangeSupport propertyChangeSupport;	

	public DrawingModel() {
		this.propertyChangeSupport = new PropertyChangeSupport(this);
	}

	public List<Shape> getShapes() {
		return shapes;
	}
	
	public void setShapes(List <Shape> shapes) {
		this.shapes = shapes;
	}
	
	public void  setSelected(List<Shape> shapes) {
		this.selectedShapes = shapes;
	}
	
	public void add(Shape p) {
		shapes.add(p);
	}
	
	public void remove(Shape p) {
		shapes.remove(p);
	}
	
	public Shape get(int i) {
		return shapes.get(i);
	}
	
	public void addToSelection(Shape s) {
		selectedShapes.add(s);
		s.setSelected(true);
		int index = shapes.indexOf(s);
		propertyChangeSupport.firePropertyChange("enable deletion", null, s);
		if(selectedShapes.size() == 1) {
			propertyChangeSupport.firePropertyChange("enable modification", null, s);
			if(index != 0 && index != shapes.size() - 1) {
				propertyChangeSupport.firePropertyChange("enable to front", null, s);
				propertyChangeSupport.firePropertyChange("enable to back", null, s);
				propertyChangeSupport.firePropertyChange("enable bring to front", null, s);
				propertyChangeSupport.firePropertyChange("enable send to back", null, s);
			}
			else if (index != 0) {
				propertyChangeSupport.firePropertyChange("enable to back", null, s);
				propertyChangeSupport.firePropertyChange("enable send to back", null, s);
			}
			else if (index != shapes.size() - 1) {
				propertyChangeSupport.firePropertyChange("enable to front", null, s);
				propertyChangeSupport.firePropertyChange("enable bring to front", null, s);
			}
	}
		else {
			propertyChangeSupport.firePropertyChange("disable modification", null, new Object());
			propertyChangeSupport.firePropertyChange("disable to front", null, s);
			propertyChangeSupport.firePropertyChange("disable to back", null, s);
			propertyChangeSupport.firePropertyChange("disable bring to front", null, s);
			propertyChangeSupport.firePropertyChange("disable send to back", null, s);

		}
	}
	
	public void deleteSelection() {
		Iterator <Shape> it = selectedShapes.iterator();
		while (it.hasNext()) {
			shapes.remove(it.next());
		}
		selectedShapes.clear();
		propertyChangeSupport.firePropertyChange("disable deletion", null, new Object());
		propertyChangeSupport.firePropertyChange("disable modification", null, new Object());
		propertyChangeSupport.firePropertyChange("disable to front", null, new Object());
		propertyChangeSupport.firePropertyChange("disable to back", null, new Object());
		propertyChangeSupport.firePropertyChange("disable bring to front", null, new Object());
		propertyChangeSupport.firePropertyChange("disable send to back", null, new Object());
	}
	
	public void unselectAllShapes() {
		Iterator <Shape> it = selectedShapes.iterator();
		while (it.hasNext()) {
			Shape shape = it.next();
			shape.setSelected(false);
			it.remove();
		}
		propertyChangeSupport.firePropertyChange("disable deletion", null, new Object());
		propertyChangeSupport.firePropertyChange("disable modification", null, new Object());
		propertyChangeSupport.firePropertyChange("disable to front", null, new Object());
		propertyChangeSupport.firePropertyChange("disable to back", null, new Object());
		propertyChangeSupport.firePropertyChange("disable bring to front", null, new Object());
		propertyChangeSupport.firePropertyChange("disable send to back", null, new Object());
	}
	
	public void unselectShape(Shape shape) {
		shape.setSelected(false);
		selectedShapes.remove(shape);
		int index = shapes.indexOf(shape);
		if(selectedShapes.isEmpty()) {
			propertyChangeSupport.firePropertyChange("disable deletion", null, new Object());
			propertyChangeSupport.firePropertyChange("disable modification", null, new Object());
			propertyChangeSupport.firePropertyChange("disable to front", null, new Object());
			propertyChangeSupport.firePropertyChange("disable to back", null, new Object());
			propertyChangeSupport.firePropertyChange("disable bring to front", null, new Object());
			propertyChangeSupport.firePropertyChange("disable send to back", null, new Object());
		}
		if(selectedShapes.size() == 1) {
			propertyChangeSupport.firePropertyChange("enable modification", null, new Object());
			if(index != 0 && index != shapes.size() - 1) {
				propertyChangeSupport.firePropertyChange("enable to front", null, shape);
				propertyChangeSupport.firePropertyChange("enable to back", null, shape);
				propertyChangeSupport.firePropertyChange("enable bring to front", null, shape);
				propertyChangeSupport.firePropertyChange("enable send to back", null, shape);
			}
			else if (index != 0) {
				propertyChangeSupport.firePropertyChange("enable to back", null, shape);
				propertyChangeSupport.firePropertyChange("enable send to back", null, shape);
			}
			else if (index != shapes.size() - 1) {
				propertyChangeSupport.firePropertyChange("enable to front", null, shape);
				propertyChangeSupport.firePropertyChange("enable bring to front", null, shape);
			}
		}

	}

	public List<Shape> getSeletedShapes() {
		return selectedShapes;
	}
	
	public void addListener (PropertyChangeListener l1) {
		propertyChangeSupport.addPropertyChangeListener(l1);
	}
}
