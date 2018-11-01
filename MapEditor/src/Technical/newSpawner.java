package Technical;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class newSpawner extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField xCoordTxt;
	private JTextField yCoordTxt;
	private JTextField MobNameTxt;

	public static void main(String[] args) {
		try {
			newSpawner dialog = new newSpawner();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public newSpawner() {
		setTitle("Create new Spawner");
		setBounds(100, 100, 506, 343);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 1, 250);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		JSlider slider = new JSlider();
		slider.setMaximum(2);
		slider.setBounds(374, 36, 106, 26);
		contentPanel.add(slider);
		
		JLabel lblMobType = new JLabel("Mob type");
		lblMobType.setBounds(399, 11, 46, 14);
		contentPanel.add(lblMobType);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 250, 490, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton saveButton = new JButton("Save");
				saveButton.setActionCommand("OK");
				buttonPane.add(saveButton);
				getRootPane().setDefaultButton(saveButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		xCoordTxt = new JTextField();
		xCoordTxt.setText("0");
		xCoordTxt.setBounds(31, 11, 48, 20);
		getContentPane().add(xCoordTxt);
		xCoordTxt.setColumns(10);
		
		yCoordTxt = new JTextField();
		yCoordTxt.setText("0");
		yCoordTxt.setBounds(31, 42, 48, 20);
		getContentPane().add(yCoordTxt);
		yCoordTxt.setColumns(10);
		
		JLabel lblX = new JLabel("X:");
		lblX.setBounds(10, 14, 20, 14);
		getContentPane().add(lblX);
		
		JLabel lblY = new JLabel("Y:");
		lblY.setBounds(11, 45, 20, 14);
		getContentPane().add(lblY);
		
		
		MobNameTxt = new JTextField();
		MobNameTxt.setBounds(369, 11, 111, 20);
		getContentPane().add(MobNameTxt);
		MobNameTxt.setColumns(10);
		
		JLabel lblSpawnerName = new JLabel("Spawner Name:");
		lblSpawnerName.setBounds(236, 14, 111, 14);
		getContentPane().add(lblSpawnerName);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Zombie", "Skeleton", "Cow"}));
		comboBox.setBounds(110, 11, 105, 20);
		getContentPane().add(comboBox);
		{
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			{
				JMenu mnFile = new JMenu("File");
				menuBar.add(mnFile);
				
				JMenuItem mntmLoadSpawner = new JMenuItem("Load Spawner");
				mntmLoadSpawner.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				mnFile.add(mntmLoadSpawner);
				
				JMenuItem mntmImportSpawner = new JMenuItem("Import Spawner");
				mnFile.add(mntmImportSpawner);
				
				JMenuItem mntmImportMob = new JMenuItem("Import Mob");
				mnFile.add(mntmImportMob);
			}
		}
	}
}
