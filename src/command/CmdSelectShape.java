package command;

import javax.management.modelmbean.ModelMBean;

import Drawing.Shape;
import mvc.DrawingModel;

public class CmdSelectShape implements Command {

	Shape shape;
	DrawingModel model;
	
	public  CmdSelectShape(Shape shape,DrawingModel model) {
		this.shape = shape;
		this.model = model;
	}
	
	@Override
	public void execute() {
		model.addToSelection(shape);
	}

	@Override
	public void unexecute() {
		model.unselectShape(shape);
	}
	
	public String toString() {
		return "SELECTED";
	}

}
