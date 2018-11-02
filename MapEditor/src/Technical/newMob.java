package Technical;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JRadioButton;

public class newMob extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String Name;
	boolean naturalSpawn;
	int spawningRate; //mob spawns per minute
	int aggroRange; 
	float health;
	int type; //0=passive 1=friendly 2=aggressive
	int stamina;
	float velocity;
	int dropID;
	
	private final JPanel contentPanel = new JPanel();
	JTextField txtName;
	JTextField txtDropID;
	JTextField txtImagePath;
	JLabel lblType;
	JLabel lblSpawningRate;
	JLabel lblAggroRange;
	JSlider sliderSpawnRate;
	JSlider sliderAggroRange;
	JSlider sliderType;
	JSlider sliderHealth;
	JLabel lblinfState;
	JLabel lblinfNaturalSpawn;
	JLabel lblinfName;
	JLabel lblinfHealth;
	JLabel lblinfAggroRange;
	JRadioButton rdbtnNaturalSpawn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			newMob dialog = new newMob();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public newMob() {
		setTitle("Create new Mob");
		setBounds(100, 100, 595, 334);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblName = new JLabel("Name :");
			lblName.setBounds(10, 11, 46, 14);
			contentPanel.add(lblName);
		}
		{
			txtName = new JTextField();
			txtName.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					lblinfName.setText(txtName.getText());
				}
			});
			txtName.setText("Mob Name");
			txtName.setBounds(52, 8, 86, 20);
			contentPanel.add(txtName);
			txtName.setColumns(10);
		}
		{
			JLabel lblHealth = new JLabel("Health:");
			lblHealth.setBounds(10, 39, 46, 14);
			contentPanel.add(lblHealth);
		}
		{
			JLabel lblDropID = new JLabel("Drop ID:");
			lblDropID.setBounds(10, 67, 46, 14);
			contentPanel.add(lblDropID);
		}
		{
			sliderType = new JSlider();
			sliderType.setMinorTickSpacing(1);
			sliderType.setSnapToTicks(true);
			sliderType.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
				    if (!sliderType.getValueIsAdjusting() && lblType != null && lblinfState!=null) {
				        int state = (int)sliderType.getValue();
				        if (state == 0){
				        	lblinfState.setText("Type: Passive");
				        	sliderAggroRange.setEnabled(false);
				        	lblinfAggroRange.setEnabled(false);
				        	lblAggroRange.setEnabled(false);
				        }
						else if (state == 1){
				        	lblinfState.setText("Type: Friendly");
				        	sliderAggroRange.setEnabled(false);
				        	lblinfAggroRange.setEnabled(false);
				        	lblAggroRange.setEnabled(false);
						}
						else{
				        	lblinfState.setText("Type: Aggressive");
				        	sliderAggroRange.setEnabled(true);
				        	lblinfAggroRange.setEnabled(true);
				        	lblAggroRange.setEnabled(true);
						}
				    }
				}
			});
			sliderType.setValue(0);
			sliderType.setMaximum(2);
			sliderType.setBounds(252, 8, 101, 20);
			contentPanel.add(sliderType);
		}
		{
			txtDropID = new JTextField();
			txtDropID.setHorizontalAlignment(SwingConstants.CENTER);
			txtDropID.setText("0");
			txtDropID.setColumns(10);
			txtDropID.setBounds(52, 64, 86, 20);
			contentPanel.add(txtDropID);
		}
		{
			sliderHealth = new JSlider();
			sliderHealth.setMinorTickSpacing(1);
			sliderHealth.setSnapToTicks(true);
			sliderHealth.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					if(lblinfHealth!=null)lblinfHealth.setText("Health: " + sliderHealth.getValue());
				}
			});
			sliderHealth.setMinimum(1);
			sliderHealth.setValue(10);
			sliderHealth.setMaximum(20);
			sliderHealth.setBounds(52, 36, 86, 20);
			contentPanel.add(sliderHealth);
		}
		{
			lblType = new JLabel("Type:");
			lblType.setBounds(148, 11, 101, 14);
			contentPanel.add(lblType);
		}
		{
			JSeparator separator = new JSeparator();
			separator.setOrientation(SwingConstants.VERTICAL);
			separator.setBounds(399, 0, 2, 239);
			contentPanel.add(separator);
		}
		{
			lblinfName = new JLabel("Mob Name");
			lblinfName.setBounds(411, 10, 158, 14);
			contentPanel.add(lblinfName);
		}
		{
			JLabel lblImageFile = new JLabel("");
			lblImageFile.setIcon(new ImageIcon(newMob.class.getResource("/sources/NPCs/newImg/zombie.png")));
			lblImageFile.setBounds(353, 196, 32, 32);
			contentPanel.add(lblImageFile);
		}
		{
			JLabel lblImage = new JLabel("Image Path:");
			lblImage.setBounds(10, 207, 68, 14);
			contentPanel.add(lblImage);
		}
		{
			txtImagePath = new JTextField();
			txtImagePath.setToolTipText("Work in progress...");
			txtImagePath.setText("C:\\Users\\Oliver\\2D Game\\Textures\\Zombie.png");
			txtImagePath.setBounds(88, 204, 255, 20);
			contentPanel.add(txtImagePath);
			txtImagePath.setColumns(10);
		}
		
		rdbtnNaturalSpawn = new JRadioButton("Natural Spawn");
		rdbtnNaturalSpawn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnNaturalSpawn.isSelected()){
					lblSpawningRate.setEnabled(true);
					sliderSpawnRate.setEnabled(true);
					lblinfNaturalSpawn.setText("Natural Spawn true: " + sliderSpawnRate.getValue()+ "/min");
				}
				else{
					lblSpawningRate.setEnabled(false);
					sliderSpawnRate.setEnabled(false);
					lblinfNaturalSpawn.setText("Natural Spawn false");
				}
			}
		});
		rdbtnNaturalSpawn.setSelected(true);
		rdbtnNaturalSpawn.setBounds(144, 35, 109, 23);
		contentPanel.add(rdbtnNaturalSpawn);
		
		lblSpawningRate = new JLabel("Spawning Rate:");
		lblSpawningRate.setBounds(148, 67, 101, 14);
		contentPanel.add(lblSpawningRate);
		
		sliderSpawnRate = new JSlider();
		sliderSpawnRate.setToolTipText("Amount of Mobspawns / minute");
		sliderSpawnRate.setSnapToTicks(true);
		sliderSpawnRate.setMinorTickSpacing(1);
		sliderSpawnRate.setMinimum(1);
		sliderSpawnRate.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				if(lblinfNaturalSpawn != null)lblinfNaturalSpawn.setText("Natural Spawn true: " + sliderSpawnRate.getValue()+ "/min");
			}
		});
		sliderSpawnRate.setValue(1);
		sliderSpawnRate.setMaximum(15);
		sliderSpawnRate.setBounds(252, 64, 101, 20);
		contentPanel.add(sliderSpawnRate);
		
		lblinfHealth = new JLabel("Health: 10");
		lblinfHealth.setBounds(411, 30, 158, 14);
		contentPanel.add(lblinfHealth);
		{
			lblinfState = new JLabel("Type: Passive");
			lblinfState.setBounds(411, 50, 158, 14);
			contentPanel.add(lblinfState);
		}
		{
			lblinfNaturalSpawn = new JLabel("Natural Spawn true: 1/min");
			lblinfNaturalSpawn.setBounds(411, 70, 158, 14);
			contentPanel.add(lblinfNaturalSpawn);
		}
		{
			JSeparator separator = new JSeparator();
			separator.setBounds(0, 194, 401, 2);
			contentPanel.add(separator);
		}
		{
			lblAggroRange = new JLabel("Aggro Range:");
			lblAggroRange.setEnabled(false);
			lblAggroRange.setBounds(148, 95, 101, 14);
			contentPanel.add(lblAggroRange);
		}
		{
			sliderAggroRange = new JSlider();
			sliderAggroRange.setEnabled(false);
			sliderAggroRange.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					if(lblinfAggroRange!=null)lblinfAggroRange.setText("Aggro Range: " + sliderAggroRange.getValue() + " Blocks");
				}
			});
			sliderAggroRange.setValue(3);
			sliderAggroRange.setSnapToTicks(true);
			sliderAggroRange.setMinorTickSpacing(1);
			sliderAggroRange.setMinimum(1);
			sliderAggroRange.setMaximum(10);
			sliderAggroRange.setBounds(252, 92, 101, 20);
			contentPanel.add(sliderAggroRange);
		}
		{
			lblinfAggroRange = new JLabel("Aggro Range: 3 Blocks");
			lblinfAggroRange.setBounds(411, 90, 158, 14);
			contentPanel.add(lblinfAggroRange);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new LineBorder(new Color(0, 0, 0)));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Save");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						writeNewMob();
					}
				});
				okButton.setToolTipText("");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		{
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			{
				JMenu mnFile = new JMenu("File");
				menuBar.add(mnFile);
				{
					JMenuItem mntmLoadMob = new JMenuItem("Load Mob");
					mntmLoadMob.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
						}
					});
					mnFile.add(mntmLoadMob);
				}
			}
		}
	}
	
	BufferedWriter writer;

	public void writeNewMob() 
	{

	    File file = new File("C:/Users/Felix/2D Game/Technical/Mobs/" + txtName.getText()+".mob");
	    try (PrintWriter pw = new PrintWriter(file)) {
	        pw.println("NAME: " + txtName.getText());
		    pw.println("TYPE: " + sliderType.getValue());
		    pw.println("HEALTH: " + sliderHealth.getValue());
		    pw.println("DROPID: " + txtDropID.getText());
		    if(rdbtnNaturalSpawn.isSelected()){
		    	pw.println("NATURALSPAWN: true");
			    pw.println("SPAWNRATE: " + sliderSpawnRate.getValue());
		    }
		    else pw.println("NATURALSPAWN: false");
		    pw.println("SPAWNRATE: " + sliderSpawnRate.getValue());
		    pw.println("AGGRORANGE: " + sliderAggroRange.getValue());
		    pw.println("IMAGEPATH: " + txtImagePath.getText());
	    }
	    catch(FileNotFoundException e)
	    {
	        System.out.println("File Not Found");
	        System.exit( 1 );
	    }
	}
}
