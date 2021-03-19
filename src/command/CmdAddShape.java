package command;

import Drawing.Shape;
import mvc.DrawingModel;

public class CmdAddShape implements Command{
	private Shape shape;
	private DrawingModel model;
	
	public CmdAddShape(Shape shape, DrawingModel model) {
		this.shape = shape;
		this.model = model;
	}

	@Override
	public void execute() {
		model.add(shape);
		
	}

	@Override
	public void unexecute() {
		model.remove(shape);
	}
	
	public String toString() {
		return "ADD";
	}
	
	
}
