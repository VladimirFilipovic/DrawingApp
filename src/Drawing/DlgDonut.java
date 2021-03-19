package Drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class DlgDonut extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtXCoordinate;
	private JTextField txtYCoordinate;
	private JTextField txtR;
	private JTextField txtInnerR;
	private boolean isOk;
	private Color fill;
	private Color edge;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgDonut dialog = new DlgDonut();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgDonut() {
		setModal(true);
		setTitle("Add donut");
		setBounds(100, 100, 275, 232);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JLabel lblDonutCenter = new JLabel("Donut center");
		lblDonutCenter.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JLabel lblXCoordinate = new JLabel("X coordinate: ");
		JLabel lblYCoordinate = new JLabel("Y coordinate: ");
		JLabel lblRadius = new JLabel("Radius:");
		JLabel lblInnerRadius = new JLabel("Inner radius:");

		txtXCoordinate = new JTextField();
		txtXCoordinate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					getToolkit().beep();
					e.consume();
				}
			}
		});
		txtXCoordinate.setColumns(10);

		txtYCoordinate = new JTextField();
		txtYCoordinate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					getToolkit().beep();
					e.consume();
				}
			}
		});
		txtYCoordinate.setColumns(10);

		txtR = new JTextField();
		txtR.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					getToolkit().beep();
					e.consume();
				}
			}
		});
		txtR.setColumns(10);

		txtInnerR = new JTextField();
		txtInnerR.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					getToolkit().beep();
					e.consume();
				}
			}
		});
		txtInnerR.setColumns(10);

		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDonutCenter)
						.addComponent(lblXCoordinate)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblYCoordinate)
								.addComponent(lblRadius))
							.addGap(31)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(txtR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtXCoordinate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtYCoordinate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtInnerR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(lblInnerRadius))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblDonutCenter)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblXCoordinate)
						.addComponent(txtXCoordinate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblYCoordinate)
						.addComponent(txtYCoordinate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRadius)
						.addComponent(txtR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblInnerRadius)
						.addComponent(txtInnerR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(116, Short.MAX_VALUE))
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
						if (txtXCoordinate.getText().isEmpty() || txtYCoordinate.getText().isEmpty()
								|| txtR.getText().isEmpty() || txtInnerR.getText().isEmpty()) {
							isOk = false;
							JOptionPane.showMessageDialog(contentPanel, "Some fields are blank.", "Error",
									JOptionPane.ERROR_MESSAGE);
						} else {
							if (Integer.parseInt(txtR.getText()) > Integer.parseInt(txtInnerR.getText())) {
								isOk = true;
								dispose();
							} else {
								JOptionPane.showMessageDialog(contentPanel, "The radius of the donut has to be smaller than the radius of it's hole.", "Error",
										JOptionPane.ERROR_MESSAGE);
							}
							
							
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
				btnCancel.setActionCommand("btnCancel");
				buttonPane.add(btnCancel);
			}
		}
	}

	public JTextField getTxtXCoordinate() {
		return txtXCoordinate;
	}

	public JTextField getTxtYCoordinate() {
		return txtYCoordinate;
	}

	public JTextField getTxtR() {
		return txtR;
	}

	public boolean isOk() {
		return isOk;
	}

	public Color getFill() {
		return fill;
	}

	public Color getEdge() {
		return edge;
	}

	public JTextField getTxtHoleR() {
		return txtInnerR;
	}

	public void setTxtHoleR(String txtHoleR) {
		this.txtInnerR.setText(txtHoleR);
	}

	public void setTxtXCoordinate(String txtXCoordinate) {
		this.txtXCoordinate.setText(txtXCoordinate);
	}

	public void setTxtYCoordinate(String txtYCoordinate) {
		this.txtYCoordinate.setText(txtYCoordinate);
	}

	public void setTxtR(String txtR) {
		this.txtR.setText(txtR);
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	public void setFill(Color fill) {
		this.fill = fill;
	}

	public void setEdge(Color edge) {
		this.edge = edge;
	}
}
