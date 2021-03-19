package command;

import Drawing.Shape;
import mvc.DrawingModel;

public class CmdBringToFront implements Command {
	private Shape movingShape;
    private Shape shapeInFront; 
	private DrawingModel model;
	private int oldIndex;
	private int newIndex;

	public CmdBringToFront(Shape movingShape, DrawingModel model) {
		this.model = model;
		this.movingShape = movingShape;
		oldIndex = model.getShapes().indexOf(movingShape);
		newIndex = model.getShapes().size() - 1;
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
		return "TOFRONT";
	}

}
