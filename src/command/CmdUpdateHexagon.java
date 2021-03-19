package command;

import Drawing.Rectangle;
import adapter.HexagonAdapter;

public class CmdUpdateHexagon implements Command {

	private HexagonAdapter oldState;
	private HexagonAdapter newState;
	private HexagonAdapter original = new HexagonAdapter();
	
	 public CmdUpdateHexagon (HexagonAdapter oldState, HexagonAdapter newState) {
		this.oldState=oldState.clone();
		 this.oldState = oldState;
		this.newState = newState;
	}
	
	@Override
	public void execute() {
//		original.setX(oldState.getX());
//		original.setY(oldState.getY());
//		original.setLength(oldState.getLength());
//		original.setOutlineColor(oldState.getOutlineColor());
//		original.setInnerColor(oldState.getInnerColor());
		original = oldState.clone();
		oldState = newState.clone();
//		oldState.setX(newState.getX());
//		oldState.setY(newState.getY());
//		oldState.setLength(newState.getLength());
//		oldState.setOutlineColor(newState.getOutlineColor());
//		oldState.setInnerColor(newState.getInnerColor());
	}

	@Override
	public void unexecute() {
		oldState = original.clone();
//		oldState.setX(original.getX());
//		oldState.setY(original.getY());
//		oldState.setLength(original.getLength());
//		oldState.setOutlineColor(original.getOutlineColor());
//		oldState.setInnerColor(original.getInnerColor());
	}

}
