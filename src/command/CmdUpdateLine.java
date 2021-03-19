package command;

import Drawing.Line;
import Drawing.Point;

public class CmdUpdateLine implements Command {
	private Line oldState;
	private Line newState;
	private Line original = new Line(new Point(),new Point());
	
	public CmdUpdateLine(Line oldState, Line newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		original.getStartPoint().setX(oldState.getStartPoint().getX());
		original.getStartPoint().setY(oldState.getStartPoint().getY());
		original.getEndPoint().setX(oldState.getEndPoint().getX());
		original.getEndPoint().setY(oldState.getEndPoint().getY());
		original.setOutlineColor(oldState.getOutlineColor());
		
		oldState.getStartPoint().setX(newState.getStartPoint().getX());
		oldState.getStartPoint().setY(newState.getStartPoint().getY());
		oldState.getEndPoint().setX(newState.getEndPoint().getX());
		oldState.getEndPoint().setY(newState.getEndPoint().getY());
		oldState.setOutlineColor(newState.getOutlineColor());
	}

	@Override
	public void unexecute() {
		oldState.getStartPoint().setX(original.getStartPoint().getX());
		oldState.getStartPoint().setY(original.getStartPoint().getY());
		oldState.getEndPoint().setX(original.getEndPoint().getX());
		oldState.getEndPoint().setY(original.getEndPoint().getY());
		oldState.setOutlineColor(original.getOutlineColor());
	}
}
