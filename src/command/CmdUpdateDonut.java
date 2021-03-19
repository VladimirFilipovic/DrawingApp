package command;

import Drawing.Donut;

public class CmdUpdateDonut implements Command {
	private Donut oldState;
	private Donut newState;
	private Donut original = new Donut();
	
	public CmdUpdateDonut(Donut oldState, Donut newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
//		original.setCenter(oldState.getCenter());
//		original.setRadius(oldState.getRadius());
//		original.setInnerRadius(oldState.getInnerRadius());
//		original.setInnerColor(oldState.getInnerColor());
//		original.setOutlineColor(oldState.getOutlineColor());
		original = oldState.clone();
		oldState = newState.clone();
//		oldState.setCenter(newState.getCenter());
//		oldState.setRadius(newState.getRadius());
//		oldState.setInnerRadius(newState.getInnerRadius());
//		oldState.setInnerColor(newState.getInnerColor());
//		oldState.setOutlineColor(newState.getOutlineColor());
	}

	@Override
	public void unexecute() {
		oldState = original.clone();
//		oldState.setCenter(original.getCenter());
//		oldState.setRadius(original.getRadius());
//		oldState.setInnerRadius(original.getInnerRadius());
//		oldState.setInnerColor(original.getInnerColor());
//		oldState.setOutlineColor(original.getOutlineColor());
	}
}
