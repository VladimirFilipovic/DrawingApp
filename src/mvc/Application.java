package mvc;

import javax.swing.JFrame;

public class Application {

	public static void main(String[] args) {
		DrawingModel model = new DrawingModel();
		DrawingMain frame = new DrawingMain();
		frame.getView().setModel(model);
		DrawingController controller = new DrawingController(model, frame);
		model.addListener(controller);
		frame.setController(controller);
		controller.addListener(frame);
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

}
