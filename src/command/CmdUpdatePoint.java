package command;

import Drawing.Point;

public class CmdUpdatePoint implements Command {
	private Point oldState;
	private Point newState;
	private Point original = new Point();
	
	public CmdUpdatePoint(Point oldState, Point newState) {
		this.oldState = oldState;
		this.newState = newState;
	}
	

	@Override
	public void execute() {
		/*
			 * original.setX(oldState.getX()); original.setY(oldState.getY());
			 * original.setOutlineColor(oldState.getOutlineColor());
		 */
		original = oldState.clone();
		oldState = newState.clone();
	}

	@Override
	public void unexecute() {
		oldState = original.clone();
	}
}
