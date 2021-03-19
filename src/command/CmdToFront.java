package command;

import Drawing.Shape;
import mvc.DrawingModel;

public class CmdToFront implements Command{
	private DrawingModel model;
	private Shape movingShape; //shape we want moved
	private Shape shapeInFront; 
	private int oldIndex;
	private int newIndex;
	
	public CmdToFront(DrawingModel model, Shape shape) {
		this.model = model;
		this.movingShape = shape;
		oldIndex = model.getShapes().indexOf(shape);
		newIndex = oldIndex + 1;
		this.shapeInFront = model.getShapes().get(newIndex);
	}

	@Override
	public void execute() {
		model.getShapes().set(newIndex, movingShape);
		model.getShapes().set(oldIndex, shapeInFront);
	}

	@Override
	public void unexecute() {
		model.getShapes().set(oldIndex, movingShape);
		model.getShapes().set(newIndex, shapeInFront);
	}
	public String toString() {
		return "FRONT";
	}
}
