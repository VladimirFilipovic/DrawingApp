package command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Drawing.Shape;
import mvc.DrawingModel;

public class CmdRemoveShape implements Command {
	List <Shape> shapesBeforeDeletion;
	List <Shape> selectedBeforeDeletion;
	//sad ce ovu listu da setuje pri execute ili u consturktrou na selected listu i onda ce unexcute da prodje i da doda iz te liste u model
	DrawingModel model;
	
	public CmdRemoveShape(DrawingModel model) {
		shapesBeforeDeletion = new ArrayList<Shape>();
		selectedBeforeDeletion = new ArrayList<Shape>();
		Iterator <Shape> it = model.getShapes().iterator();
		while(it.hasNext())
			shapesBeforeDeletion.add(it.next());
		Iterator <Shape> it2 = model.getSeletedShapes().iterator();
		while(it2.hasNext())
			selectedBeforeDeletion.add(it2.next());
		this.model = model;
	}

	@Override
	public void execute() {
		model.deleteSelection();
	}

	@Override
	public void unexecute() {
		model.getShapes().clear();
		Iterator<Shape> it  = shapesBeforeDeletion.iterator();
		while(it.hasNext()) {
			model.add(it.next());
		}
		Iterator<Shape> it2  = selectedBeforeDeletion.iterator();
		while(it2.hasNext()) {
			model.addToSelection(it2.next());
		}
	}
	
	public String toString() {
		return "DELETE";
	}
	
}
