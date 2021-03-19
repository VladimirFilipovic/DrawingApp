package mvc;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.ConsoleHandler;

import javax.security.auth.x500.X500Principal;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

import Drawing.Circle;
import Drawing.DlgCircle;
import Drawing.DlgDonut;
import Drawing.DlgHexagon;
import Drawing.DlgLine;
import Drawing.DlgPoint;
import Drawing.DlgRectangle;
import Drawing.Donut;
import Drawing.Line;
import Drawing.Point;
import Drawing.Rectangle;
import Drawing.Shape;
import adapter.HexagonAdapter;
import command.CmdAddShape;
import command.CmdBringToFront;
import command.CmdRemoveShape;
import command.CmdSelectShape;
import command.CmdSendToBack;
import command.CmdToBack;
import command.CmdToFront;
import command.CmdUnselectShape;
import command.CmdUpdateCircle;
import command.CmdUpdateDonut;
import command.CmdUpdateHexagon;
import command.CmdUpdateLine;
import command.CmdUpdatePoint;
import command.CmdUpdateRectangle;
import command.CmdUpdateShape;
import command.Command;
import hexagon.Hexagon;

public class DrawingController implements PropertyChangeListener {
	private DrawingModel model;
	private DrawingMain frame;
	private Point startPoint;
	private PropertyChangeSupport propertyChangeSupport;
	private Color innerColor = Color.white;
	private Color outlineColor = Color.black;
	List<String> log;
	private List<Command> listOfCommands = new ArrayList<Command>();
	int indexOfCommand = 0;
	private int lineNum;

	public DrawingController(DrawingModel model, DrawingMain frame) {
		this.model = model;
		this.frame = frame;
		propertyChangeSupport = new PropertyChangeSupport(this);
	}

	public Color getInnerColor() {
		return innerColor;
	}

	public void setInnerColor(Color innerColor) {
		if (innerColor == null)
			this.innerColor = Color.white;
		else {
			this.innerColor = innerColor;
		}

	}

	public Color getOutlineColor() {
		return outlineColor;
	}

	public void setOutlineColor(Color outlineColor) {
		if (outlineColor == null)
			this.outlineColor = Color.black;
		else {
			this.outlineColor = outlineColor;
		}
	}

	public void executeCommand(Command command, String nameOfCommand, Shape shape1, Shape shape2) {
		// nakon undo i dodadas novi nema vise tih komandi ne mozes redo
		List<Command> newCommandsList = listOfCommands.subList(0, indexOfCommand);
		newCommandsList.add(command);
		indexOfCommand = newCommandsList.size();
		propertyChangeSupport.firePropertyChange("enable undo", null, new Object());
		propertyChangeSupport.firePropertyChange("disable redo", null, new Object());
		listOfCommands = newCommandsList;
		if (nameOfCommand == "UNSELECTALL") {
			for (Shape unselectedShape : model.getSeletedShapes()) {
				frame.getActionsTakenListModel().addElement("UNSELECTED " + unselectedShape);
			}
			command.execute();
			frame.getView().repaint();

		} else if (nameOfCommand == "UPDATE") {
			command.execute();
			frame.getView().repaint();
			frame.getActionsTakenListModel().addElement(nameOfCommand + " " + shape1 + " to " + shape2);
		} else if (nameOfCommand == "DELETE") {
			for (Shape deletedShape : model.getSeletedShapes()) {
				frame.getActionsTakenListModel().addElement("DELETE " + deletedShape);
			}
			command.execute();
			frame.getView().repaint();
		} else {
			command.execute();
			frame.getView().repaint();
			frame.getActionsTakenListModel().addElement(nameOfCommand + " " + shape1);
		}
	}

	public void mouseClicked(MouseEvent e) {
		if (frame.getTglbtnSelect().isSelected()) {
			Point p = new Point(e.getX(), e.getY());
			Shape selectedShape = null;
			Iterator<Shape> it = model.getShapes().listIterator();
			while (it.hasNext()) {
				Shape tempShape = it.next();
				if ((tempShape.contains(p))) {
					selectedShape = tempShape;
					if (tempShape.isSelected()) {
						// List<Shape> shapesForUnselectionList = new ArrayList<Shape>();
						CmdUnselectShape unselectShapeCommand = new CmdUnselectShape(tempShape, model, false);
						executeCommand(unselectShapeCommand, "UNSELECTED", selectedShape, null);
						return;
					}
				}
			}
			// sad prolazis i odselektujes
			if (selectedShape != null) {
				CmdSelectShape selectShapeCommand = new CmdSelectShape(selectedShape, model);
				executeCommand(selectShapeCommand, "SELECTED", selectedShape, null);
			} else {

				CmdUnselectShape unselectShapeCommand = new CmdUnselectShape(null, model, true);
				executeCommand(unselectShapeCommand, "UNSELECTALL", null, null);

			}
			frame.getView().repaint();
		}

		if (frame.getTglbtnPoint().isSelected()) {
			DlgPoint dlgPoint = new DlgPoint();
			dlgPoint.setTxtXCoordinate(Integer.toString(e.getX()));
			dlgPoint.setTxtYCoordinate(Integer.toString(e.getY()));
			dlgPoint.setVisible(true);
			if (dlgPoint.isOk()) {
				Point point = new Point(Integer.parseInt(dlgPoint.getTxtXCoordinate().getText()),
						Integer.parseInt(dlgPoint.getTxtYCoordinate().getText()), outlineColor);
				CmdAddShape addPointCommand = new CmdAddShape(point, model);
				executeCommand(addPointCommand, "ADD", point, null);
			}
		} else if (frame.getTglbtnLine().isSelected()) {

			if (startPoint == null)
				startPoint = new Point(e.getX(), e.getY());
			else {
				Point endPoint = new Point(e.getX(), e.getY());
				DlgLine dlgLine = new DlgLine();
				dlgLine.setTxtStartXCoordinate(Integer.toString(startPoint.getX()));
				dlgLine.setTxtStartYCoordinate(Integer.toString(startPoint.getY()));
				dlgLine.setTxtEndXCoordinate(Integer.toString(endPoint.getX()));
				dlgLine.setTxtEndYCoordinate(Integer.toString(endPoint.getY()));
				dlgLine.setVisible(true);
				if (dlgLine.isOk()) {
					Point p1 = new Point(Integer.parseInt(dlgLine.getTxtStartXCoordinate().getText()),
							Integer.parseInt(dlgLine.getTxtStartYCoordinate().getText()));
					Point p2 = new Point(Integer.parseInt(dlgLine.getTxtEndXCoordinate().getText()),
							Integer.parseInt(dlgLine.getTxtEndYCoordinate().getText()));
					Line line = new Line(p1, p2, outlineColor);
					dlgLine.dispose();
					CmdAddShape addLineCommand = new CmdAddShape(line, model);
					executeCommand(addLineCommand, "ADD", line, null);
				}
				startPoint = null;

			}
		} else if (frame.getTglbtnRectangle().isSelected()) {
			DlgRectangle dlgRectangle = new DlgRectangle();
			dlgRectangle.setTxtXCoordinate(Integer.toString(e.getX()));
			dlgRectangle.setTxtYCoordinate(Integer.toString(e.getY()));
			dlgRectangle.setVisible(true);
			if (dlgRectangle.isOk()) {
				Rectangle rectangle = new Rectangle(
						new Point(Integer.parseInt(dlgRectangle.getTxtXCoordinate().getText()),
								Integer.parseInt(dlgRectangle.getTxtYCoordinate().getText())),
						Integer.parseInt(dlgRectangle.getTxtHeight().getText()),
						Integer.parseInt(dlgRectangle.getTxtWidth().getText()), innerColor, outlineColor);
				CmdAddShape addRectangleCommand = new CmdAddShape(rectangle, model);
				executeCommand(addRectangleCommand, "ADD", rectangle, null);
			}
		} else if (frame.getTglbtnCircle().isSelected()) {
			Point center = new Point(e.getX(), e.getY());
			DlgCircle dlgCircle = new DlgCircle();
			dlgCircle.setTxtXCoordinate(Integer.toString(center.getX()));
			dlgCircle.setTxtYCoordinate(Integer.toString(center.getY()));
			dlgCircle.setVisible(true);
			if (dlgCircle.isOk()) {
				Circle circle = new Circle(
						new Point(Integer.parseInt(dlgCircle.getTxtXCoordinate().getText()),
								Integer.parseInt(dlgCircle.getTxtYCoordinate().getText())),
						Integer.parseInt(dlgCircle.getTxtR().getText()), innerColor, outlineColor);
				CmdAddShape addCircleCommand = new CmdAddShape(circle, model);
				executeCommand(addCircleCommand, "ADD", circle, null);
			}
		} else if (frame.getTglbtnDonut().isSelected()) {
			Point center = new Point(e.getX(), e.getY());
			DlgDonut dlgDonut = new DlgDonut();
			dlgDonut.setTxtXCoordinate(Integer.toString(center.getX()));
			dlgDonut.setTxtYCoordinate(Integer.toString(center.getY()));
			dlgDonut.setVisible(true);

			if (dlgDonut.isOk()) {
				int innerRadius = Integer.parseInt(dlgDonut.getTxtHoleR().getText());
				int outerRadius = Integer.parseInt(dlgDonut.getTxtR().getText());
				int x = Integer.parseInt(dlgDonut.getTxtXCoordinate().getText());
				int y = Integer.parseInt(dlgDonut.getTxtYCoordinate().getText());
				center = new Point(x, y);
				Donut donut = new Donut(center, outerRadius, innerRadius);
				donut.setInnerColor(innerColor);
				donut.setOutlineColor(outlineColor);
				CmdAddShape addDonutCommand = new CmdAddShape(donut, model);
				executeCommand(addDonutCommand, "ADD", donut, null);
			}
		}

		else if (frame.getTglbtnHexagon().isSelected()) {
			Point click = new Point(e.getX(), e.getY());
			DlgHexagon dlgHexagon = new DlgHexagon();
			dlgHexagon.setTxtXCoordinate(Integer.toString(click.getX()));
			dlgHexagon.setTxtYCoordinate(Integer.toString(click.getY()));
			dlgHexagon.setVisible(true);
			if (dlgHexagon.isOk()) {
				int length = Integer.parseInt(dlgHexagon.getTxtLength().getText());
				HexagonAdapter hexagonAdapter = new HexagonAdapter(click.getX(), click.getY(), length, innerColor,
						outlineColor);
				CmdAddShape addHexagonCommand = new CmdAddShape(hexagonAdapter, model);
				executeCommand(addHexagonCommand, "ADD", hexagonAdapter, null);
			}
		}
		// TODO za sada nek ostane ovako dok se ne odlucim za visestruku selekciju
		/*
		 * else if (testShape != null) { model.addToSelection(testShape); }
		 */
	}

	public void deleteShapes() {
		if (!model.getSeletedShapes().isEmpty() && frame.getTglbtnDelete().isEnabled()) {
			if (JOptionPane.showConfirmDialog(new JFrame(), "Are you sure you want to delete the selected shape?",
					"Yes", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				CmdRemoveShape cmdRemoveShape = new CmdRemoveShape(model);
				executeCommand(cmdRemoveShape, "DELETE", null, null);
			}
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "No shape selected.", "Error!", JOptionPane.WARNING_MESSAGE);
		}
	}

	// TODO modifikovati kad selektuje vise
	public void modifyShape() {
		if (!model.getSeletedShapes().isEmpty() && frame.getTglbtnModify().isEnabled()) {
			Shape temporaryShape = model.getSeletedShapes().get(0);
			Shape oldState = temporaryShape.clone();
			if (temporaryShape instanceof Point) {
				DlgPoint dlgPoint = new DlgPoint();
				dlgPoint.setTitle("Modifikacija tacke");
				dlgPoint.setTxtXCoordinate(Integer.toString(((Point) temporaryShape).getX()));
				dlgPoint.setTxtYCoordinate(Integer.toString(((Point) temporaryShape).getY()));
				dlgPoint.setVisible(true);
				if (dlgPoint.isOk()) {
					int x = Integer.parseInt(dlgPoint.getTxtXCoordinate().getText());
					int y = Integer.parseInt(dlgPoint.getTxtYCoordinate().getText());
					Point modifiedPoint = new Point(x, y, outlineColor);
					CmdUpdateShape cmdUpdatePoint = new CmdUpdateShape(temporaryShape, modifiedPoint);
					executeCommand(cmdUpdatePoint, "UPDATE", oldState, modifiedPoint);
				}
			} else if (temporaryShape instanceof Line) {
				DlgLine dlgLine = new DlgLine();
				dlgLine.setTitle("Modifikacija linije");
				dlgLine.setTxtStartXCoordinate(Integer.toString(((Line) temporaryShape).getStartPoint().getX()));
				dlgLine.setTxtStartYCoordinate(Integer.toString(((Line) temporaryShape).getStartPoint().getY()));
				dlgLine.setTxtEndXCoordinate(Integer.toString(((Line) temporaryShape).getEndPoint().getX()));
				dlgLine.setTxtEndYCoordinate(Integer.toString(((Line) temporaryShape).getEndPoint().getY()));
				dlgLine.setVisible(true);

				if (dlgLine.isOk()) {
					int startPointX = Integer.parseInt(dlgLine.getTxtStartXCoordinate().getText());
					int startPointY = Integer.parseInt(dlgLine.getTxtStartYCoordinate().getText());
					int endPointX = Integer.parseInt(dlgLine.getTxtEndXCoordinate().getText());
					int endPointY = Integer.parseInt(dlgLine.getTxtEndYCoordinate().getText());
					Point starPoint = new Point(startPointX, startPointY);
					Point endPoint = new Point(endPointX, endPointY);
					Line modifiedLine = new Line(starPoint, endPoint, outlineColor);
					CmdUpdateShape cmdUpdateLine = new CmdUpdateShape(temporaryShape, modifiedLine);
					executeCommand(cmdUpdateLine, "UPDATE", oldState, modifiedLine);
				}
			} else if (temporaryShape instanceof Rectangle) {
				DlgRectangle dlgRectangle = new DlgRectangle();
				dlgRectangle.setTitle("Modifikacija pravougaonika");
				dlgRectangle
						.setTxtXCoordinate(Integer.toString(((Rectangle) temporaryShape).getUpperLeftPoint().getX()));
				dlgRectangle
						.setTxtYCoordinate(Integer.toString(((Rectangle) temporaryShape).getUpperLeftPoint().getY()));
				dlgRectangle.setTxtWidth(Integer.toString(((Rectangle) temporaryShape).getWidth()));
				dlgRectangle.setTxtHeight(Integer.toString(((Rectangle) temporaryShape).getHeight()));
				dlgRectangle.setOk(false);
				dlgRectangle.setVisible(true);

				if (dlgRectangle.isOk()) {
					int width = Integer.parseInt(dlgRectangle.getTxtWidth().getText());
					int height = Integer.parseInt(dlgRectangle.getTxtHeight().getText());
					int x = Integer.parseInt(dlgRectangle.getTxtXCoordinate().getText());
					int y = Integer.parseInt(dlgRectangle.getTxtYCoordinate().getText());
					Point upperLeftPoint = new Point(x, y);
					Rectangle modifiedRectangle = new Rectangle(upperLeftPoint, height, width, innerColor,
							outlineColor);
					CmdUpdateShape cmdUpdateRectangle = new CmdUpdateShape(temporaryShape, modifiedRectangle);
					executeCommand(cmdUpdateRectangle, "UPDATE", oldState, modifiedRectangle);
				}

			} else if (temporaryShape instanceof Donut) {
				DlgDonut dlgDonut = new DlgDonut();
				dlgDonut.setTitle("Modify donut");
				dlgDonut.setTxtXCoordinate(Integer.toString(((Donut) temporaryShape).getCenter().getX()));
				dlgDonut.setTxtYCoordinate(Integer.toString(((Donut) temporaryShape).getCenter().getY()));
				dlgDonut.setTxtHoleR(Integer.toString(((Donut) temporaryShape).getInnerRadius()));
				dlgDonut.setTxtR(Integer.toString(((Donut) temporaryShape).getRadius()));
				dlgDonut.setVisible(true);

				if (dlgDonut.isOk()) {
					int x = Integer.parseInt(dlgDonut.getTxtXCoordinate().getText());
					int y = Integer.parseInt(dlgDonut.getTxtYCoordinate().getText());
					Point centerPoint = new Point(x, y);
					int radius = Integer.parseInt(dlgDonut.getTxtR().getText());
					int innerRadius = Integer.parseInt(dlgDonut.getTxtHoleR().getText());
					Donut modifiedDonut = new Donut(centerPoint, radius, innerRadius);
					// TODO dodati konstruktor za donut sa color
					modifiedDonut.setInnerColor(innerColor);
					modifiedDonut.setOutlineColor(outlineColor);
					CmdUpdateShape cmdUpdateDonut = new CmdUpdateShape(temporaryShape, modifiedDonut);
					executeCommand(cmdUpdateDonut, "UPDATE", oldState, modifiedDonut);
				}

			} else if (temporaryShape instanceof Circle) {
				DlgCircle dlgCircle = new DlgCircle();
				dlgCircle.setTxtXCoordinate(Integer.toString(((Circle) temporaryShape).getCenter().getX()));
				dlgCircle.setTxtYCoordinate(Integer.toString(((Circle) temporaryShape).getCenter().getY()));
				dlgCircle.setTxtR(Integer.toString(((Circle) temporaryShape).getRadius()));
				dlgCircle.setVisible(true);

				if (dlgCircle.isOk()) {
					int x = Integer.parseInt(dlgCircle.getTxtXCoordinate().getText());
					int y = Integer.parseInt(dlgCircle.getTxtYCoordinate().getText());
					Point centerPoint = new Point(x, y);
					int radius = Integer.parseInt(dlgCircle.getTxtR().getText());
					// TODO u constructoru stavi outline umesto outLineColor
					Circle modifiedCircle = new Circle(centerPoint, radius, innerColor, outlineColor);
					CmdUpdateShape cmdUpdateCircle = new CmdUpdateShape(temporaryShape, modifiedCircle);
					executeCommand(cmdUpdateCircle, "UPDATE", oldState, modifiedCircle);
				}
			} else if (temporaryShape instanceof HexagonAdapter) {
				DlgHexagon dlgHexagon = new DlgHexagon();
				dlgHexagon.setTxtXCoordinate(Integer.toString(((HexagonAdapter) temporaryShape).getX()));
				dlgHexagon.setTxtYCoordinate(Integer.toString(((HexagonAdapter) temporaryShape).getY()));
				dlgHexagon.setTxtLength(Integer.toString(((HexagonAdapter) temporaryShape).getLength()));
				dlgHexagon.setVisible(true);

				if (dlgHexagon.isOk()) {
					int x = Integer.parseInt(dlgHexagon.getTxtXCoordinate().getText());
					int y = Integer.parseInt(dlgHexagon.getTxtYCoordinate().getText());
					int length = Integer.parseInt(dlgHexagon.getTxtLength().getText());
					HexagonAdapter modifiedHexagonAdapter = new HexagonAdapter(x, y, length, innerColor, outlineColor);
					CmdUpdateShape cmdUpdateHexagon = new CmdUpdateShape(temporaryShape, modifiedHexagonAdapter);
					executeCommand(cmdUpdateHexagon, "UPDATE", oldState, modifiedHexagonAdapter);
				}
			}

		} else if (model.getSeletedShapes().isEmpty()) {
			JOptionPane.showMessageDialog(new JFrame(), "No shape selected.", "Error!", JOptionPane.WARNING_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "You can only modify one shape at the time", "Error!",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	public void undo() {
		if (indexOfCommand > 0) {
			indexOfCommand--;
			propertyChangeSupport.firePropertyChange("enable redo", null, new Object());
			if (indexOfCommand == 0)
				propertyChangeSupport.firePropertyChange("disable undo", null, new Object());
			Command command = listOfCommands.get(indexOfCommand);
			command.unexecute();
			frame.getActionsTakenListModel().addElement("UNDO " + command);
			frame.getPnlDrawing().repaint();
		} else {
			// TODO za sada ce izbacivati JOPTION PANE videcu kako da disablujem undo kad ne
			// moze
			JOptionPane.showMessageDialog(new JFrame(), "No action to undo", "Error!", JOptionPane.WARNING_MESSAGE);
		}
	}

	public List<Command> getListOfCommands() {
		return listOfCommands;
	}

	public void redo() {
		if (indexOfCommand < listOfCommands.size()) {
			Command command = listOfCommands.get(indexOfCommand);
			command.execute();
			indexOfCommand++;
			if (indexOfCommand == listOfCommands.size())
				propertyChangeSupport.firePropertyChange("disable redo", null, new Object());
			propertyChangeSupport.firePropertyChange("enable undo", null, new Object());
			frame.getActionsTakenListModel().addElement("REDO " + command);
			frame.getPnlDrawing().repaint();
		} else {
			// TODO za sada ce izbacivati JOPTION PANE videcu kako da disablujem undo kad ne
			// moze
			JOptionPane.showMessageDialog(new JFrame(), "No action to redo", "Error!", JOptionPane.WARNING_MESSAGE);
		}

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("enable deletion")) {
			frame.getTglbtnDelete().setEnabled(true);
		} else if (evt.getPropertyName().equals("disable deletion")) {
			frame.getTglbtnDelete().setEnabled(false);
		} else if (evt.getPropertyName().equals("enable modification")) {
			frame.getTglbtnModify().setEnabled(true);
		} else if (evt.getPropertyName().equals("disable modification")) {
			frame.getTglbtnModify().setEnabled(false);
		} else if (evt.getPropertyName().equals("enable to front")) {
			frame.getTglbtnToFront().setEnabled(true);
		} else if (evt.getPropertyName().equals("disable to front")) {
			frame.getTglbtnToFront().setEnabled(false);
		} else if (evt.getPropertyName().equals("enable to back")) {
			frame.getTglbtnToBack().setEnabled(true);
		} else if (evt.getPropertyName().equals("disable to back")) {
			frame.getTglbtnToBack().setEnabled(false);
		} else if (evt.getPropertyName().equals("enable send to back")) {
			frame.getTglbtnSendToBack().setEnabled(true);
		} else if (evt.getPropertyName().equals("disable send to back")) {
			frame.getTglbtnSendToBack().setEnabled(false);
		} else if (evt.getPropertyName().equals("enable bring to front")) {
			frame.getTglbtnBringToFront().setEnabled(true);
		} else if (evt.getPropertyName().equals("disable bring to front")) {
			frame.getTglbtnBringToFront().setEnabled(false);
		}
	}

	public void moveToFront() {
		if (!model.getSeletedShapes().isEmpty()) {

			if (model.getSeletedShapes().size() > 1) {
				JOptionPane.showMessageDialog(new JFrame(), "You can only move one shape at the time", "Error!",
						JOptionPane.WARNING_MESSAGE);
			} else {
				Shape movingShape = model.getSeletedShapes().get(0);
				if (model.getShapes().indexOf(movingShape) != model.getShapes().size() - 1) {
					CmdToFront cmdToFront = new CmdToFront(model, movingShape);
					executeCommand(cmdToFront, "FRONT", movingShape, null);
					if (model.getShapes().indexOf(movingShape) == model.getShapes().size() - 1)
						frame.getTglbtnToFront().setEnabled(false);
					frame.getTglbtnToBack().setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "Shape is already in front of all shapes", "Error!",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "No shape selected.", "Error!", JOptionPane.WARNING_MESSAGE);
		}

	}

	public void moveToBack() {
		if (!model.getSeletedShapes().isEmpty()) {
			if (model.getSeletedShapes().size() > 1) {
				JOptionPane.showMessageDialog(new JFrame(), "You can only move one shape at the time", "Error!",
						JOptionPane.WARNING_MESSAGE);
			} else {
				Shape movingShape = model.getSeletedShapes().get(0);
				if (model.getShapes().indexOf(movingShape) != 0) {
					CmdToBack cmdToBack = new CmdToBack(model, movingShape);
					executeCommand(cmdToBack, "BACK", movingShape, null);
					if (model.getShapes().indexOf(movingShape) == 0)
						frame.getTglbtnToBack().setEnabled(false);
					frame.getTglbtnToFront().setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "Shape is already behind all shapes", "Error!",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "No shape selected.", "Error!", JOptionPane.WARNING_MESSAGE);
		}

	}

	public void bringToFront() {

		if (!model.getSeletedShapes().isEmpty()) {
			if (model.getSeletedShapes().size() > 1) {
				JOptionPane.showMessageDialog(new JFrame(), "You can only move one shape at the time", "Error!",
						JOptionPane.WARNING_MESSAGE);
			} else {
				Shape movingShape = model.getSeletedShapes().get(0);
				if (model.getShapes().indexOf(movingShape) != model.getShapes().size() - 1) {
					CmdBringToFront cmdBringToFront = new CmdBringToFront(movingShape, model);
					executeCommand(cmdBringToFront, "TOFRONT", movingShape, null);
					frame.getTglbtnBringToFront().setEnabled(false);
					frame.getTglbtnSendToBack().setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "Shape is already in front of all shapes", "Error!",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "No shape selected.", "Error!", JOptionPane.WARNING_MESSAGE);
		}
	}

	public void sendToBack() {
		if (!model.getSeletedShapes().isEmpty()) {
			if (model.getSeletedShapes().size() > 1) {
				JOptionPane.showMessageDialog(new JFrame(), "You can only move one shape at the time", "Error!",
						JOptionPane.WARNING_MESSAGE);
			} else {
				Shape movingShape = model.getSeletedShapes().get(0);
				if (model.getShapes().indexOf(movingShape) != 0) {
					CmdSendToBack cmdSendToBack = new CmdSendToBack(model, movingShape);
					executeCommand(cmdSendToBack, "TOBACK", movingShape, null);
					frame.getTglbtnSendToBack().setEnabled(false);
					frame.getTglbtnBringToFront().setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "Shape is already behind all shapes", "Error!",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "No shape selected.", "Error!", JOptionPane.WARNING_MESSAGE);
		}
	}

	public void save(boolean saveLog, String destination) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(destination);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(model);
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (saveLog) {
			List<Object> text = Arrays.asList(frame.getActionsTakenListModel().toArray());
			try (PrintWriter out = new PrintWriter(destination + ".txt")) {
				for (Object line : text) {
					out.println(line);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public void open(String string, File file) {
		if (string.equals("drawing")) {

			FileInputStream fis;
			try {
				fis = new FileInputStream(file.getPath());
				ObjectInputStream oin = new ObjectInputStream(fis);
				model = (DrawingModel) oin.readObject();
				fis.close();
				frame.getView().setModel(model);
				frame.getView().repaint();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			// moracu nekako da napravim da cita liniju po liniju kad kliknem next
			try {
				log = Files.readAllLines(Paths.get(file.getPath()));
				propertyChangeSupport.firePropertyChange("enable next",null,new Object());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private Shape createShapeFromText(String[] arrOfStr, String shapeName) {
		switch (shapeName) {
		case "Point":
			int x = Integer.parseInt(arrOfStr[3]);
			int y = Integer.parseInt(arrOfStr[5]);
			Color color = new Color(Integer.parseInt(arrOfStr[7]));
			Point point = new Point(x, y, color);
			return point;
		case "Line":
			int x1 = Integer.parseInt(arrOfStr[4]);
			int y1 = Integer.parseInt(arrOfStr[7]);
			int x2 = Integer.parseInt(arrOfStr[10]);
			int y2 = Integer.parseInt(arrOfStr[13]);
			Color color1 = new Color(Integer.parseInt(arrOfStr[15]));
			Point startPoint = new Point(x1, y1);
			Point endPoint = new Point(x2, y2);
			Line line1 = new Line(startPoint, endPoint, color1);
			return line1;
		case "Rectangle":
			int xR = Integer.parseInt(arrOfStr[3]);
			int yR = Integer.parseInt(arrOfStr[5]);
			int height = Integer.parseInt(arrOfStr[7]);
			int width = Integer.parseInt(arrOfStr[9]);
			Color outlineColorR = new Color(Integer.parseInt(arrOfStr[12]));
			Color innerColorR = new Color(Integer.parseInt(arrOfStr[15]));
			Point upperLeftPoint = new Point(xR, yR);
			Rectangle rectangle = new Rectangle(upperLeftPoint, height, width, innerColorR, outlineColorR);
			return rectangle;
		case "Circle":
			int xC = Integer.parseInt(arrOfStr[4]);
			int yC = Integer.parseInt(arrOfStr[7]);
			int radius = Integer.parseInt(arrOfStr[9]);
			Color outlineColorC = new Color(Integer.parseInt(arrOfStr[12]));
			Color innerColorC = new Color(Integer.parseInt(arrOfStr[15]));
			Point center = new Point(xC, yC);
			Circle circle = new Circle(center, radius, innerColorC, outlineColorC);
			return circle;
		case "Donut":
			int xDonutCenter = Integer.parseInt(arrOfStr[4]);
			int yDonutCenter = Integer.parseInt(arrOfStr[7]);
			int outerRadius = Integer.parseInt(arrOfStr[10]);
			int innerRadius = Integer.parseInt(arrOfStr[13]);
			Color outlineColorDonut = new Color(Integer.parseInt(arrOfStr[16]));
			Color innerColorDonut = new Color(Integer.parseInt(arrOfStr[19]));
			Point centerDonut = new Point(xDonutCenter, yDonutCenter);
			Donut donut = new Donut(centerDonut, outerRadius, innerRadius);
			donut.setInnerColor(innerColorDonut);
			donut.setOutlineColor(outlineColorDonut);
			return donut;
		case "Hexagon":
			int xHex = Integer.parseInt(arrOfStr[3]);
			int yHex = Integer.parseInt(arrOfStr[5]);
			int length = Integer.parseInt(arrOfStr[7]);
			Color outlineColorHex = new Color(Integer.parseInt(arrOfStr[10]));
			Color innerColorHex = new Color(Integer.parseInt(arrOfStr[13]));
			HexagonAdapter hexagon = new HexagonAdapter(xHex, yHex, length, innerColorHex, outlineColorHex);
			return hexagon;
		default:
			break;
		}
		return null;
	}

	public void addListener(PropertyChangeListener l1) {
		propertyChangeSupport.addPropertyChangeListener(l1);
	}

	public void next() {
		if(!(log.isEmpty() || lineNum == log.size())) {
			String line = log.get(lineNum++);
			if(lineNum == log.size())
				propertyChangeSupport.firePropertyChange("disable next",null,new Object());
			String[] arrOfStr = line.split("[, :=]+"); 
			String commandName = arrOfStr[0];
			Shape shape = null;
			String shapeName=null;
			if(!(commandName.equals("UNDO") || commandName.equals("REDO")))
			{
		    shapeName = arrOfStr[1];
			shape = createShapeFromText(arrOfStr,shapeName);
			}
			int n=0;
			for (String line2 : arrOfStr) {
				System.out.println(n++ +" "+line2);
			}
			switch (commandName) {
			case "ADD":
				CmdAddShape cmdAddShape = new CmdAddShape(shape, model);
				executeCommand(cmdAddShape, commandName, shape, null);
				break;
			case "SELECTED":
				int index = model.getShapes().indexOf(shape);
				CmdSelectShape cmdSelectShape = new CmdSelectShape(model.getShapes().get(index), model);
				executeCommand(cmdSelectShape, commandName, shape,model.getShapes().get(index));
				break;
			case "UNSELECTALL":
			//	CmdUnselectShape unselectShapeCommand = new CmdUnselectShape(model.getSeletedShapes(), model);
				//executeCommand(unselectShapeCommand, "UNSELECTALL", null, null);
				break;
			case "UNSELECTED":
				int indexUnselected = model.getShapes().indexOf(shape);
				CmdUnselectShape cmdUnselectShape = new CmdUnselectShape(model.getShapes().get(indexUnselected),model,true);
				executeCommand(cmdUnselectShape, commandName, shape,model.getShapes().get(indexUnselected));
				break;
			case "FRONT":
				int index1 = model.getShapes().indexOf(shape);
				CmdToFront cmdToFront = new CmdToFront(model, model.get(index1));
				executeCommand(cmdToFront, commandName, shape,model.getShapes().get(index1));
				break;
			case "BACK":
				int index11 = model.getShapes().indexOf(shape);
				CmdToBack cmdToBack = new CmdToBack(model, model.get(index11));
				executeCommand(cmdToBack, commandName, shape,model.getShapes().get(index11));
				break;
			case "TOFRONT":
				int index111 = model.getShapes().indexOf(shape);
				CmdBringToFront cmdBringToFront = new CmdBringToFront(model.get(index111), model);
				executeCommand(cmdBringToFront, commandName, shape,model.getShapes().get(index111));
				break;
			case "TOBACK":
				int indextb = model.getShapes().indexOf(shape);
				CmdSendToBack cmdSendToBack = new CmdSendToBack(model, model.get(indextb));
				executeCommand(cmdSendToBack, commandName, shape,model.getShapes().get(indextb));
				break;
			case "UPDATE":
				int indexUpdate = model.getShapes().indexOf(shape);
				if(shapeName.equals("Point")) {
					arrOfStr = Arrays.copyOfRange(arrOfStr, 8, 16);//line
				}
				else if(shapeName.equals("Line") || shapeName.equals("Rectangle") || shapeName.equals("Circle")){
					arrOfStr = Arrays.copyOfRange(arrOfStr, 16, 32);//line
				}
				else if(shapeName.equals("Donut")) {
					arrOfStr = Arrays.copyOfRange(arrOfStr, 20, 40);//line
				}
				else if(shapeName.equals("Hexagon")) {
					arrOfStr = Arrays.copyOfRange(arrOfStr, 14, 28);//line
				}
				
				int i =0;
				for (String line2 : arrOfStr) {
					System.out.println(i++ +" "+line2);
				}
				String modifiedShapeName = arrOfStr[1];
				Shape newState = createShapeFromText(arrOfStr, modifiedShapeName);
				Shape oldState = model.getShapes().get(indexUpdate).clone();
				CmdUpdateShape cmdUpdateShape = new CmdUpdateShape(model.getShapes().get(indexUpdate), newState);
				executeCommand(cmdUpdateShape, "UPDATE", oldState, newState);
				//Shape newState = createShapeFromText(arrOfStr, shapeName)
			//	String
				//Shape newState = createShapeFromText(arrOfStr, shapeName)
			//	executeCommand(cmdSendToBack, commandName, shape,model.getShapes().get(indextb));
				break;
			case "UNDO":
				undo();
				break;
			case "REDO":
				redo();
				break;
			default:
				break;
			}
		}
		else if (log.isEmpty()) {
			JOptionPane.showMessageDialog(new JFrame(), "Unable to execute next command list is empty",
					"Error", JOptionPane.WARNING_MESSAGE);
		}
		else {
			JOptionPane.showMessageDialog(new JFrame(), "All commands are executed",
					"Error", JOptionPane.WARNING_MESSAGE);
		}
	}

}
