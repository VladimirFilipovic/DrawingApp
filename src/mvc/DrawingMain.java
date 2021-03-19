package mvc;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

import Drawing.Circle;
import Drawing.DlgCircle;
import Drawing.DlgDonut;
import Drawing.DlgLine;
import Drawing.DlgPoint;
import Drawing.DlgRectangle;
import Drawing.Donut;
import Drawing.Line;
import Drawing.Point;
import Drawing.Rectangle;
import Drawing.Shape;

import javax.swing.JToolBar;
import javax.swing.GroupLayout;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.BorderLayout;
import javax.swing.JButton;

public class DrawingMain extends JFrame implements PropertyChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7L;
	private JPanel contentPane;
	private JToggleButton tglbtnPoint;
	private JToggleButton tglbtnLine;
	private JToggleButton tglbtnRectangle;
	private JToggleButton tglbtnCircle;
	private JToggleButton tglbtnDonut;
	private JToggleButton tglbtnSelect;
	private JToggleButton tglbtnModify;
	private JToggleButton tglbtnDelete;
	private JToggleButton tglbtnUndo;
	private JToggleButton tglbtnRedo;
	private JToggleButton tglbtnToFront;
	private JToggleButton tglbtnToBack;
	private JToggleButton tglbtnBringToFront;
	private JToggleButton tglbtnSendToBack;
	private PnlDrawing pnlDrawing = new PnlDrawing();
	DefaultListModel<String> actionsTakenListModel = new DefaultListModel<String>();
	private DrawingController controller;
	private JToggleButton tglbtnHexagon;
	private JScrollPane scrollPane;
	private JList<String> actionsTakenList;
	JFileChooser fileChooser;
	private int dialogResult;
	private int userSelection;
	private JPanel panel;
	private JPanel panel_1;
	private JButton btnNext;

	public JToggleButton getTglbtnHexagon() {
		return tglbtnHexagon;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DrawingMain drawing = new DrawingMain();
					drawing.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DrawingMain() {
		setTitle("Fake Paint");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 200, 800, 600);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save");

		JMenu mnNew = new JMenu("File");
		menuBar.add(mnNew);

		JMenuItem mntmOpenLog = new JMenuItem("Open Log..");
		mntmOpenLog.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (fileChooser.showOpenDialog(pnlDrawing) == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					controller.open("log", file);
				}
			}
		});
		mnNew.add(mntmOpenLog);

		JMenuItem mntmOpenDrawing = new JMenuItem("Open Drawing..");
		mntmOpenDrawing.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (fileChooser.showOpenDialog(pnlDrawing) == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					controller.open("drawing", file);
				}
			}
		});
		mnNew.add(mntmOpenDrawing);

		JMenuItem mntmSaveAs = new JMenuItem("Save as..");
		mntmSaveAs.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				dialogResult = JOptionPane.showConfirmDialog(pnlDrawing, "Do you want to save log of commands");
				userSelection = fileChooser.showSaveDialog(pnlDrawing);
				if (userSelection == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					if (dialogResult == JOptionPane.YES_OPTION) {
						controller.save(true, file.getPath());
					} else {
						controller.save(false, file.getPath());
					}
				}
			}
		});
		mnNew.add(mntmSaveAs);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		getPnlDrawing().setPreferredSize(new Dimension(600, 800));
		contentPane.add(getPnlDrawing());

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);

		ButtonGroup btnGroup = new ButtonGroup();

		tglbtnPoint = new JToggleButton("Point");
		toolBar.add(tglbtnPoint);
		btnGroup.add(tglbtnPoint);

		tglbtnLine = new JToggleButton("Line");
		toolBar.add(tglbtnLine);
		btnGroup.add(tglbtnLine);

		tglbtnRectangle = new JToggleButton("Rectangle");
		toolBar.add(tglbtnRectangle);
		btnGroup.add(tglbtnRectangle);

		tglbtnCircle = new JToggleButton("Circle");
		toolBar.add(tglbtnCircle);
		btnGroup.add(tglbtnCircle);

		tglbtnDonut = new JToggleButton("Donut");
		btnGroup.add(tglbtnDonut);
		toolBar.add(tglbtnDonut);

		tglbtnHexagon = new JToggleButton("Hexagon");
		btnGroup.add(tglbtnHexagon);
		toolBar.add(tglbtnHexagon);

		tglbtnSelect = new JToggleButton("Select");
		btnGroup.add(tglbtnSelect);
		toolBar.add(tglbtnSelect);

		tglbtnToFront = new JToggleButton("To Front");
		tglbtnToFront.setEnabled(false);
		tglbtnToFront.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.moveToFront();
			}
		});
		btnGroup.add(tglbtnToFront);
		toolBar.add(tglbtnToFront);

		tglbtnToBack = new JToggleButton("To Back");
		tglbtnToBack.setEnabled(false);
		tglbtnToBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.moveToBack();
			}
		});

		tglbtnBringToFront = new JToggleButton("Bring To Front");
		tglbtnBringToFront.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.bringToFront();
			}
		});
		tglbtnBringToFront.setEnabled(false);
		toolBar.add(tglbtnBringToFront);
		btnGroup.add(tglbtnToBack);
		toolBar.add(tglbtnToBack);

		tglbtnSendToBack = new JToggleButton("Send To Back");
		tglbtnSendToBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.sendToBack();
			}
		});
		tglbtnSendToBack.setEnabled(false);
		toolBar.add(tglbtnSendToBack);

		tglbtnModify = new JToggleButton("Modify");
		tglbtnModify.setEnabled(false);
		btnGroup.add(tglbtnModify);
		toolBar.add(tglbtnModify);

		tglbtnModify.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				controller.modifyShape();
			}
		});

		tglbtnDelete = new JToggleButton("Delete");
		tglbtnDelete.setEnabled(false);
		btnGroup.add(tglbtnDelete);
		toolBar.add(tglbtnDelete);
		tglbtnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				controller.deleteShapes();
			}
		});

		tglbtnUndo = new JToggleButton("Undo");
		btnGroup.add(tglbtnUndo);
		tglbtnUndo.setEnabled(false);
		tglbtnUndo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.undo();
			}
		});
		toolBar.add(tglbtnUndo);

		tglbtnRedo = new JToggleButton("Redo");
		btnGroup.add(tglbtnRedo);
		tglbtnRedo.setEnabled(false);
		tglbtnRedo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.redo();
			}
		});
		toolBar.add(tglbtnRedo);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(pnlDrawing, BorderLayout.CENTER);
		pnlDrawing.setLayout(new BorderLayout(0, 0));
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		btnNext = new JButton("Next");
		btnNext.setEnabled(false);
		btnNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.next();
			}
		});
		toolBar.add(btnNext);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
								
										JLabel lblArea = new JLabel("Area");
										panel.add(lblArea);
						
								JPanel areaColorPanel = new JPanel();
								panel.add(areaColorPanel);
								areaColorPanel.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseClicked(MouseEvent e) {
										controller.setInnerColor(JColorChooser.showDialog(pnlDrawing, "Choose the outline color", Color.white));
										areaColorPanel.setBackground(controller.getInnerColor());
									}
								});
								areaColorPanel.setBackground(Color.WHITE);
										
												JLabel lblEdge = new JLabel("Edge");
												panel.add(lblEdge);
								
										JPanel edgeColorPanel = new JPanel();
										panel.add(edgeColorPanel);
										edgeColorPanel.addMouseListener(new MouseAdapter() {
											@Override
											public void mouseClicked(MouseEvent e) {
												controller
														.setOutlineColor(JColorChooser.showDialog(pnlDrawing, "Choose the outline color", Color.white));
												edgeColorPanel.setBackground(controller.getOutlineColor());
											}
										});
										edgeColorPanel.setBackground(Color.BLACK);
										
										panel_1 = new JPanel();
										panel.add(panel_1);
												panel_1.setLayout(new BorderLayout(0, 0));
										
												scrollPane = new JScrollPane();
												panel_1.add(scrollPane);
												
														actionsTakenList = new JList<String>();
														scrollPane.setViewportView(actionsTakenList);
														actionsTakenList.setModel(actionsTakenListModel);

		getPnlDrawing().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (!controller.getListOfCommands().isEmpty())
					tglbtnUndo.setEnabled(true);
				controller.mouseClicked(arg0);
			}
		});

	}

	public void setController(DrawingController controller) {
		this.controller = controller;
	}

	public JToggleButton getTglbtnPoint() {
		return tglbtnPoint;
	}

	public JToggleButton getTglbtnLine() {
		return tglbtnLine;
	}

	public JToggleButton getTglbtnRectangle() {
		return tglbtnRectangle;
	}

	public JToggleButton getTglbtnCircle() {
		return tglbtnCircle;
	}

	public JToggleButton getTglbtnDonut() {
		return tglbtnDonut;
	}

	public JToggleButton getTglbtnSelect() {
		return tglbtnSelect;
	}

	public JToggleButton getTglbtnModify() {
		return tglbtnModify;
	}

	public JToggleButton getTglbtnDelete() {
		return tglbtnDelete;
	}

	public JToggleButton getTglbtnUndo() {
		return tglbtnUndo;
	}

	public void setTglbtnUndo(JToggleButton tglbtnUndo) {
		this.tglbtnUndo = tglbtnUndo;
	}

	public JToggleButton getTglbtnRedo() {
		return tglbtnRedo;
	}

	public void setTglbtnRedo(JToggleButton tglbtnRedo) {
		this.tglbtnRedo = tglbtnRedo;
	}

	public JToggleButton getTglbtnToFront() {
		return tglbtnToFront;
	}

	public void setTglbtnToFront(JToggleButton tglbtnToFront) {
		this.tglbtnToFront = tglbtnToFront;
	}

	public JToggleButton getTglbtnToBack() {
		return tglbtnToBack;
	}

	public void setTglbtnToBack(JToggleButton tglbtnToBack) {
		this.tglbtnToBack = tglbtnToBack;
	}

	public JToggleButton getTglbtnBringToFront() {
		return tglbtnBringToFront;
	}

	public void setTglbtnBringToFront(JToggleButton tglbtnBringToFront) {
		this.tglbtnBringToFront = tglbtnBringToFront;
	}

	public JToggleButton getTglbtnSendToBack() {
		return tglbtnSendToBack;
	}

	public void setTglbtnSendToBack(JToggleButton tglbtnBringToBack) {
		this.tglbtnSendToBack = tglbtnBringToBack;
	}

	public DefaultListModel<String> getActionsTakenListModel() {
		return actionsTakenListModel;
	}

	public PnlDrawing getView() {
		return this.getPnlDrawing();
	}

	public PnlDrawing getPnlDrawing() {
		return pnlDrawing;
	}

	public void setPnlDrawing(PnlDrawing pnlDrawing) {
		this.pnlDrawing = pnlDrawing;
	}

	public JList<String> getActionsTakenList() {
		return actionsTakenList;
	}

	public void setActionsTakenList(JList<String> actionsTakenList) {
		this.actionsTakenList = actionsTakenList;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("enable undo")) {
			tglbtnUndo.setEnabled(true);
		}
		else if(evt.getPropertyName().equals("enable redo")) {
			tglbtnRedo.setEnabled(true);
		}
		else if(evt.getPropertyName().equals("disable undo")) {
			tglbtnUndo.setEnabled(false);
		}
		else if(evt.getPropertyName().equals("disable redo")) {
			tglbtnRedo.setEnabled(false);
		}
		else if(evt.getPropertyName().equals("enable next")) {
			btnNext.setEnabled(true);
		}
		else if(evt.getPropertyName().equals("disable next")) {
			btnNext.setEnabled(false);
		}

		
	}
}
