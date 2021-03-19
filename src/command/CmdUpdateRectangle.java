package command;

import Drawing.Line;
import Drawing.Point;
import Drawing.Rectangle;

public class CmdUpdateRectangle implements Command {

	private Rectangle oldState;
	private Rectangle newState;
	private Rectangle original = new Rectangle(new Point(4,5),5,5, null, null);
	
	 public CmdUpdateRectangle (Rectangle oldState, Rectangle newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
//		original.getUpperLeftPoint().setX(oldState.getUpperLeftPoint().getX());
//		original.getUpperLeftPoint().setY(oldState.getUpperLeftPoint().getY());
//		original.setHeight(oldState.getHeight());
//		original.setWidth(oldState.getWidth());
//		original.setOutlineColor(oldState.getOutlineColor());
//		original.setInnerColor(oldState.getInnerColor());
		original = oldState.clone();
		oldState = newState.clone();
//		oldState.getUpperLeftPoint().setX(newState.getUpperLeftPoint().getX());
//		oldState.getUpperLeftPoint().setY(newState.getUpperLeftPoint().getY());
//		oldState.setHeight(newState.getHeight());
//		oldState.setOutlineColor(newState.getOutlineColor());
//		oldState.setInnerColor(newState.getInnerColor());
	}

	@Override
	public void unexecute() {
		oldState = original.clone();
//		oldState.getUpperLeftPoint().setX(original.getUpperLeftPoint().getX());
//		oldState.getUpperLeftPoint().setY(original.getUpperLeftPoint().getY());
//		oldState.setHeight(original.getHeight());
//		oldState.setOutlineColor(original.getOutlineColor());
//		oldState.setInnerColor(original.getInnerColor());
	}

}
