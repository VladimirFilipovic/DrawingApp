package command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Drawing.Shape;
import mvc.DrawingModel;

public class CmdUnselectShape implements Command{
	List<Shape>shapes = new ArrayList<Shape>();
	Shape unselectedShape;
	DrawingModel model;
	boolean unselectAll;
	
	public  CmdUnselectShape(Shape unselectedShape,DrawingModel model,boolean unselectAll) {
		this.model = model;
		this.unselectAll = unselectAll;
		if(unselectAll)
		{
			for (Shape shape : model.getSeletedShapes()) {
				shapes.add(shape);
			}
			
		}
		else {
			this.unselectedShape = unselectedShape;
		}
	}
	
	@Override
	public void execute() {
		if(!unselectAll)
			model.unselectShape(unselectedShape);
		else {
			model.unselectAllShapes();
		}
			
	}

	@Override
	public void unexecute() {
		if(unselectAll) {
		for (Shape shape : shapes) {
			model.addToSelection(shape);
		}
		}
		else {
			model.addToSelection(unselectedShape);
		}
	}
	
	public String toString() {
		return "UNSELECTED";
	}

}
