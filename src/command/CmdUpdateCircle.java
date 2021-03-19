package command;

import Drawing.Circle;
import Drawing.Line;

public class CmdUpdateCircle implements Command {
	private Circle oldState;
	private Circle newState;
	private Circle original = new Circle();
	
	public CmdUpdateCircle(Circle oldState, Circle newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
//		original.setCenter(oldState.getCenter());
//		original.setRadius(oldState.getRadius());
//		original.setInnerColor(oldState.getInnerColor());
//		original.setOutlineColor(oldState.getOutlineColor());
		original = oldState.clone();
		oldState = newState.clone();
//		oldState.setCenter(newState.getCenter());
//		oldState.setRadius(newState.getRadius());
//		oldState.setInnerColor(newState.getInnerColor());
//		oldState.setOutlineColor(newState.getOutlineColor());
	}

	@Override
	public void unexecute() {
		oldState = original.clone();
	}
	
	
}
