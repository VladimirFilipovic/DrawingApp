package command;

import Drawing.Shape;
import mvc.DrawingModel;

public class CmdSendToBack implements Command {

	private DrawingModel model;
	private Shape movingShape; //shape we want moved
	private Shape shapeInBehind; 
	private int oldIndex;
	private int newIndex;
	
	public CmdSendToBack (DrawingModel model, Shape shape) {
		this.model = model;
		this.movingShape = shape;
		oldIndex = model.getShapes().indexOf(shape);
		newIndex = 0;
		this.shapeInBehind= model.getShapes().get(newIndex);
	}

	@Override
	public void execute() {
		model.getShapes().set(newIndex, movingShape);
		model.getShapes().set(oldIndex, shapeInBehind);
	}

	@Override
	public void unexecute() {
		model.getShapes().set(oldIndex, movingShape);
		model.getShapes().set(newIndex, shapeInBehind);
	}
	
	public String toString() {
		return "TOBACK";
	}
}
