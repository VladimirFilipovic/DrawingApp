package Drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class DlgLine extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblStartPoint;
	private JTextField txtStartXCoordinate;
	private JTextField txtStartYCoordinate;
	private JTextField txtEndXCoordinate;
	private JTextField txtEndYCoordinate;
	private boolean isOk;
	private Color edge;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgLine dialog = new DlgLine();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgLine() {
		setModal(true);
		setTitle("Add line");
		setBounds(100, 100, 255, 240);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			lblStartPoint = new JLabel("Start point");
			lblStartPoint.setFont(new Font("Tahoma", Font.PLAIN, 14));
		}
		JLabel lblXCoordinate = new JLabel("X coordinate: ");
		JLabel lblYCoordinate = new JLabel("Y coordinate: ");
		JLabel lblEndPoint = new JLabel("End point");
		lblEndPoint.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JLabel lblXCoordinate_1 = new JLabel("X coordinate");
		JLabel lblYCoordinate_1 = new JLabel("Y coordinate: ");

		txtStartXCoordinate = new JTextField();
		txtStartXCoordinate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					getToolkit().beep();
					e.consume();
				}
			}
		});
		txtStartXCoordinate.setColumns(10);

		txtStartYCoordinate = new JTextField();
		txtStartYCoordinate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					getToolkit().beep();
					e.consume();
				}
			}
		});
		txtStartYCoordinate.setColumns(10);

		txtEndXCoordinate = new JTextField();
		txtEndXCoordinate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					getToolkit().beep();
					e.consume();
				}
			}
		});
		txtEndXCoordinate.setColumns(10);

		txtEndYCoordinate = new JTextField();
		txtEndYCoordinate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					getToolkit().beep();
					e.consume();
				}
			}
		});
		txtEndYCoordinate.setColumns(10);

		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(lblXCoordinate)
									.addGap(18)
									.addComponent(txtStartXCoordinate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(lblYCoordinate)
									.addGap(18)
									.addComponent(txtStartYCoordinate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
									.addGroup(gl_contentPanel.createSequentialGroup()
										.addComponent(lblXCoordinate_1)
										.addPreferredGap(ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
										.addComponent(txtEndXCoordinate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_contentPanel.createSequentialGroup()
										.addComponent(lblYCoordinate_1)
										.addGap(18)
										.addComponent(txtEndYCoordinate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(63)
							.addComponent(lblStartPoint))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(65)
							.addComponent(lblEndPoint)))
					.addContainerGap(48, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblStartPoint)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblXCoordinate)
						.addComponent(txtStartXCoordinate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblYCoordinate)
						.addComponent(txtStartYCoordinate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblEndPoint)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblXCoordinate_1)
						.addComponent(txtEndXCoordinate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblYCoordinate_1)
						.addComponent(txtEndYCoordinate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(85, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnAdd = new JButton("Add");
				btnAdd.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (txtStartXCoordinate.getText().isEmpty() || txtStartYCoordinate.getText().isEmpty()
								|| txtEndXCoordinate.getText().isEmpty() || txtEndYCoordinate.getText().isEmpty()) {
							isOk = false;
							JOptionPane.showMessageDialog(contentPanel, "Some fields are blank.", "Error",
									JOptionPane.ERROR_MESSAGE);
						} else {
							isOk = true;
							dispose();
						}
					}
				});
				btnAdd.setActionCommand("OK");
				buttonPane.add(btnAdd);
				getRootPane().setDefaultButton(btnAdd);
			}
			{
				JButton btnCancel = new JButton("Cancel");
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnCancel.setActionCommand("Cancel");
				buttonPane.add(btnCancel);
			}
		}
	}

	public JTextField getTxtStartXCoordinate() {
		return txtStartXCoordinate;
	}

	public JTextField getTxtStartYCoordinate() {
		return txtStartYCoordinate;
	}

	public JTextField getTxtEndXCoordinate() {
		return txtEndXCoordinate;
	}

	public JTextField getTxtEndYCoordinate() {
		return txtEndYCoordinate;
	}
	
	public void setTxtStartXCoordinate(String txtStartXCoordinate) {
		this.txtStartXCoordinate.setText(txtStartXCoordinate);
	}

	public void setTxtStartYCoordinate(String txtStartYCoordinate) {
		this.txtStartYCoordinate.setText(txtStartYCoordinate);
	}
	
	public void setTxtEndXCoordinate(String txtEndXCoordinate) {
		this.txtEndXCoordinate.setText(txtEndXCoordinate);
	}

	public void setTxtEndYCoordinate(String txtEndYCoordinate) {
		this.txtEndYCoordinate.setText(txtEndYCoordinate);
	}

	public boolean isOk() {
		return isOk;
	}

	public Color getEdge() {
		return edge;
	}

	public void setEdge(Color edge) {
		this.edge = edge;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}
	
	

}
