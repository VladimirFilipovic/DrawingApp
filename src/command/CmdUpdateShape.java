package command;

import Drawing.Shape;
import adapter.HexagonAdapter;

public class CmdUpdateShape implements Command {
	
	private Shape oldState;
	private Shape newState;
	private Shape original;
	
	public CmdUpdateShape(Shape oldState, Shape newState) {
		this.oldState = oldState;
		this.newState = newState;
		this.original = oldState.clone(); 
	//	this.original = oldState.clone(original);
		//origigi
	}
	

	@Override
	public void execute() {
		oldState = newState.clone(oldState);
	}

	@Override
	public void unexecute() {
		oldState = original.clone(oldState);
	}
	
	public String toString() {
		return "UPDATE";
	}

}
