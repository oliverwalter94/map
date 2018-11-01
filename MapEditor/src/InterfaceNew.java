import javax.swing.*;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.EventQueue;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;

import java.awt.Font;
import java.io.File;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.eclipse.wb.swing.FocusTraversalOnArray;

import Technical.newMob;

import java.awt.Component;


import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.border.EmptyBorder;

public class InterfaceNew extends JFrame {

	private JPanel contentPane;
	public static JTextField textField;
	static String FileName = "Map";
	JLabel lblChosenID;
	JLabel lblChosenExtra;
	final ButtonGroup buttonGroup = new ButtonGroup();
	static String EditerVersion = "0.3.0.4";
	static String Features = "*More and finally editable extras :D \r\n*Extras will be saved now and can also  be loaded\r\n*Some bugfixes";
	private JFrame frmTest;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceNew EDIT = new InterfaceNew();
					Installer.runInstaller();
					JOptionPane.showMessageDialog(null,Features, EditerVersion + "   New Features", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InterfaceNew() {
		
		frmTest = new JFrame("2D Game");
		frmTest.setIconImage(Toolkit.getDefaultToolkit().getImage(InterfaceNew.class.getResource("/sources/Environment/icon.png")));
		frmTest.setBackground(Color.LIGHT_GRAY);
		frmTest.setTitle("Map Editor " + EditerVersion);
		final Board board = new Board();
		frmTest.getContentPane().add(board);
		board.setLayout(null);
		
//		JPanel panel = new JPanel();
//		panel.setLayout(null);
//		tabbedPane.addTab("Settings", null, panel, null);
		
//		textField = new JTextField();
//		textField.setToolTipText("This will be the filename.\r\nIf the chosen name already exists, the old files will\r\nbe overwritten.");
//		textField.setText("NewMap");
//		textField.setColumns(10);
//		textField.setBounds(10, 26, 159, 20);
//		panel.add(textField);
		
//		JLabel label = new JLabel("Map Name:");
//		label.setToolTipText("");
//		label.setHorizontalAlignment(SwingConstants.CENTER);
//		label.setBounds(10, 11, 159, 14);
//		panel.add(label);
//		
//		JLabel lblcOliverWalter = new JLabel("\u00A9 Oliver Walter 2013");
//		lblcOliverWalter.setHorizontalAlignment(SwingConstants.CENTER);
//		lblcOliverWalter.setBounds(10, 445, 159, 14);
//		panel.add(lblcOliverWalter);
//		
//		JPanel panel_1 = new JPanel();
//		tabbedPane.addTab("Fields", null, panel_1, null);
//		panel_1.setLayout(null);
		
//		final JLabel lblSeaWater = new JLabel("Sea Water");
//		lblSeaWater.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent arg0) {
//				Board.IDsel = true;
//				Board.selID = 1;
//				lblChosenID.setIcon(Images.IdIcons[0][1]);
//				lblChosenID.setText("Sea Water");
//			}
//		});
//		lblSeaWater.setHorizontalAlignment(SwingConstants.LEFT);
//		lblSeaWater.setIcon(new ImageIcon(Images.IdIcons[0][1].getImage()));
//		lblSeaWater.setFont(new Font("Tahoma", Font.BOLD, 13));
//		lblSeaWater.setBounds(10, 54, 159, 32);
//		panel_1.add(lblSeaWater);
//
//		final JLabel lblFreshWater = new JLabel("Fresh Water");
//		lblFreshWater.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent arg0) {
//				Board.IDsel = true;
//				Board.selID = 1;
//				lblChosenID.setIcon(Images.IdIcons[0][2]);
//				lblChosenID.setText("Fresh Water");
//			}
//		});
//		lblFreshWater.setHorizontalAlignment(SwingConstants.LEFT);
//		lblFreshWater.setIcon(new ImageIcon(Images.IdIcons[0][2].getImage()));
//		lblFreshWater.setFont(new Font("Tahoma", Font.BOLD, 13));
//		lblFreshWater.setBounds(10, 97, 159, 32);
//		panel_1.add(lblFreshWater);
		
//		final JLabel lblFloatingWater = new JLabel("Floating Water");
//		lblFloatingWater.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent arg0) {
//				Board.IDsel = true;
//				Board.selID = 2;
//				lblChosenID.setIcon(Images.IdIcons[0][3]);
//				lblChosenID.setText("Floating Water");
//			}
//		});
//		lblFloatingWater.setHorizontalAlignment(SwingConstants.LEFT);
//		lblFloatingWater.setIcon(new ImageIcon(Images.IdIcons[0][3].getImage()));
//		lblFloatingWater.setFont(new Font("Tahoma", Font.BOLD, 13));
//		lblFloatingWater.setBounds(10, 140, 159, 32);
//		panel_1.add(lblFloatingWater);
//		
//		final JLabel lblGrass = new JLabel("Grass");
//		lblGrass.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent arg0) {
//				Board.IDsel = true;
//				Board.selID = 3;
//				lblChosenID.setIcon(Images.IdIcons[0][4]);
//				lblChosenID.setText("Grass");
//			}
//		});
//		lblGrass.setHorizontalAlignment(SwingConstants.LEFT);
//		lblGrass.setIcon(new ImageIcon(Images.IdIcons[0][4].getImage()));
//		lblGrass.setFont(new Font("Tahoma", Font.BOLD, 13));
//		lblGrass.setBounds(10, 183, 159, 32);
//		panel_1.add(lblGrass);
		
//		final JLabel lblSand = new JLabel("Sand");
//		lblSand.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent arg0) {
//				Board.IDsel = true;
//				Board.selID = 4;
//				lblChosenID.setIcon(Images.IdIcons[0][5]);
//				lblChosenID.setText("Sand");
//			}
//		});
//		lblSand.setHorizontalAlignment(SwingConstants.LEFT);
//		lblSand.setIcon(new ImageIcon(Images.IdIcons[0][5].getImage()));
//		lblSand.setFont(new Font("Tahoma", Font.BOLD, 13));
//		lblSand.setBounds(10, 226, 159, 32);
//		panel_1.add(lblSand);
//		
//		final JLabel lblGravel = new JLabel("Gravel");
//		lblGravel.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent arg0) {
//				Board.IDsel = true;
//				Board.selID = 5;
//				lblChosenID.setIcon(Images.IdIcons[0][6]);
//				lblChosenID.setText("Gravel");
//			}
//		});
//		lblGravel.setHorizontalAlignment(SwingConstants.LEFT);
//		lblGravel.setIcon(new ImageIcon(Images.IdIcons[0][6].getImage()));
//		lblGravel.setFont(new Font("Tahoma", Font.BOLD, 13));
//		lblGravel.setBounds(10, 269, 159, 32);
//		panel_1.add(lblGravel);
		
//		final JLabel lblFarmland = new JLabel("Farmland");
//		lblFarmland.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent arg0) {
//				Board.IDsel = true;
//				Board.selID = 6;
//				lblChosenID.setIcon(Images.IdIcons[0][7]);
//				lblChosenID.setText("Farmland");
//			}
//		});
//		lblFarmland.setHorizontalAlignment(SwingConstants.LEFT);
//		lblFarmland.setIcon(new ImageIcon(Images.IdIcons[0][7].getImage()));
//		lblFarmland.setFont(new Font("Tahoma", Font.BOLD, 13));
//		lblFarmland.setBounds(10, 312, 159, 32);
//		panel_1.add(lblFarmland);
		
//		lblChosenID = new JLabel("---");
//		lblChosenID.setIcon(new ImageIcon(Images.IdIcons[0][1].getImage()));
//		lblChosenID.setHorizontalAlignment(SwingConstants.LEFT);
//		lblChosenID.setFont(new Font("Tahoma", Font.BOLD, 13));
//		lblChosenID.setBounds(10, 11, 159, 32);
//		panel_1.add(lblChosenID);
		
//		JSeparator separator = new JSeparator();
//		separator.setBounds(0, 47, 179, 9);
//		panel_1.add(separator);
		
//		JPanel panel_2 = new JPanel();
//		panel_2.setLayout(null);
//		tabbedPane.addTab("Biomes", null, panel_2, null);
		
//		final JLabel lblOcean = new JLabel("Ocean");
//		lblOcean.setIcon(new ImageIcon(Images.BiomeIcons[0][1].getImage()));
//		lblOcean.setHorizontalAlignment(SwingConstants.LEFT);
//		lblOcean.setFont(new Font("Tahoma", Font.BOLD, 13));
//		lblOcean.setBounds(10, 11, 159, 32);
//		panel_2.add(lblOcean);
//		
//		final JLabel lblGrassland = new JLabel("Grassland");
//		lblGrassland.setIcon(new ImageIcon(Images.BiomeIcons[0][2].getImage()));
//		lblGrassland.setHorizontalAlignment(SwingConstants.LEFT);
//		lblGrassland.setFont(new Font("Tahoma", Font.BOLD, 13));
//		lblGrassland.setBounds(10, 54, 159, 32);
//		panel_2.add(lblGrassland);
//		
//		final JLabel lblDesert = new JLabel("Desert");
//		lblDesert.setIcon(new ImageIcon(Images.BiomeIcons[0][3].getImage()));
//		lblDesert.setHorizontalAlignment(SwingConstants.LEFT);
//		lblDesert.setFont(new Font("Tahoma", Font.BOLD, 13));
//		lblDesert.setBounds(10, 97, 159, 32);
//		panel_2.add(lblDesert);
//		
//		final JLabel lblPath = new JLabel("Path");
//		lblPath.setIcon(new ImageIcon(Images.BiomeIcons[0][6].getImage()));
//		lblPath.setHorizontalAlignment(SwingConstants.LEFT);
//		lblPath.setFont(new Font("Tahoma", Font.BOLD, 13));
//		lblPath.setBounds(10, 140, 159, 32);
//		panel_2.add(lblPath);
//		
//		final JLabel lblBeach = new JLabel("Beach");
//		lblBeach.setIcon(new ImageIcon(Images.BiomeIcons[0][4].getImage()));
//		lblBeach.setHorizontalAlignment(SwingConstants.LEFT);
//		lblBeach.setFont(new Font("Tahoma", Font.BOLD, 13));
//		lblBeach.setBounds(10, 183, 159, 32);
//		panel_2.add(lblBeach);
//		
//		final JLabel lblForrest = new JLabel("Forest");
//		lblForrest.setIcon(new ImageIcon(Images.BiomeIcons[0][5].getImage()));
//		lblForrest.setHorizontalAlignment(SwingConstants.LEFT);
//		lblForrest.setFont(new Font("Tahoma", Font.BOLD, 13));
//		lblForrest.setBounds(10, 226, 159, 32);
//		panel_2.add(lblForrest);
//		
//		final JLabel lblRiver = new JLabel("River");
//		lblRiver.setIcon(new ImageIcon(Images.BiomeIcons[0][7].getImage()));
//		lblRiver.setHorizontalAlignment(SwingConstants.LEFT);
//		lblRiver.setFont(new Font("Tahoma", Font.BOLD, 13));
//		lblRiver.setBounds(10, 269, 159, 32);
//		panel_2.add(lblRiver);
		
//		final JLabel lblLake = new JLabel("Lake");
//		lblLake.setIcon(new ImageIcon(Images.BiomeIcons[0][8].getImage()));
//		lblLake.setHorizontalAlignment(SwingConstants.LEFT);
//		lblLake.setFont(new Font("Tahoma", Font.BOLD, 13));
//		lblLake.setBounds(10, 312, 159, 32);
//		panel_2.add(lblLake);
//		
//		JPanel panel_3 = new JPanel();
//		panel_3.setLayout(null);
//		tabbedPane.addTab("Extras", null, panel_3, null);
		
//		final JLabel lblTree = new JLabel("Tree");
//		lblTree.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent arg0) {
//				Board.Extrasel = true;
//				Board.selEXTRA = 1;
//				lblChosenExtra.setIcon(Images.ExtraIcons[0][1]);
//				lblChosenExtra.setText("Tree");
//			}
//		});
//		lblTree.setIcon(new ImageIcon(Images.ExtraIcons[0][1].getImage()));
//		lblTree.setHorizontalAlignment(SwingConstants.LEFT);
//		lblTree.setFont(new Font("Tahoma", Font.BOLD, 13));
//		lblTree.setBounds(10, 110, 159, 32);
//		panel_3.add(lblTree);
//		
//		JLabel lblFlowers = new JLabel("Flowers");
//		lblFlowers.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent arg0) {
//				Board.Extrasel = true;
//				Board.selEXTRA = 3;
//				lblChosenExtra.setIcon(Images.ExtraIcons[0][3]);
//				lblChosenExtra.setText("Flowers");
//			}
//		});
//		lblFlowers.setHorizontalAlignment(SwingConstants.LEFT);
//		lblFlowers.setIcon(new ImageIcon(Images.ExtraIcons[0][3].getImage()));
//		lblFlowers.setFont(new Font("Tahoma", Font.BOLD, 13));
//		lblFlowers.setBounds(10, 196, 159, 32);
//		panel_3.add(lblFlowers);
//		
//		JSeparator separator_1 = new JSeparator();
//		separator_1.setBounds(0, 47, 179, 9);
//		panel_3.add(separator_1);
//		
//		lblChosenExtra = new JLabel("---");
//		lblChosenExtra.setHorizontalAlignment(SwingConstants.LEFT);
//		lblChosenExtra.setFont(new Font("Tahoma", Font.BOLD, 13));
//		lblChosenExtra.setBounds(10, 11, 159, 32);
//		panel_3.add(lblChosenExtra);
		
//		JLabel lblCactus = new JLabel("Cactus");
//		lblCactus.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent arg0) {
//				Board.Extrasel = true;
//				Board.selEXTRA = 4;
//				lblChosenExtra.setIcon(Images.ExtraIcons[0][4]);
//				lblChosenExtra.setText("Cactus");
//			}
//		});
//		lblCactus.setHorizontalAlignment(SwingConstants.LEFT);
//		lblCactus.setIcon(new ImageIcon(Images.ExtraIcons[0][4].getImage()));
//		lblCactus.setFont(new Font("Tahoma", Font.BOLD, 13));
//		lblCactus.setBounds(10, 239, 159, 32);
//		panel_3.add(lblCactus);
//		
//		JLabel lblBush = new JLabel("Bush");
//		lblBush.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent arg0) {
//				Board.Extrasel = true;
//				Board.selEXTRA = 5;
//				lblChosenExtra.setIcon(Images.ExtraIcons[0][5]);
//				lblChosenExtra.setText("Bush");
//			}
//		});
//		lblBush.setHorizontalAlignment(SwingConstants.LEFT);
//		lblBush.setIcon(new ImageIcon(Images.ExtraIcons[0][5].getImage()));
//		lblBush.setFont(new Font("Tahoma", Font.BOLD, 13));
//		lblBush.setBounds(10, 282, 159, 32);
//		panel_3.add(lblBush);
		
//		JLabel lblFir = new JLabel("Fir");
//		lblFir.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent arg0) {
//				Board.Extrasel = true;
//				Board.selEXTRA = 2;
//				lblChosenExtra.setIcon(Images.ExtraIcons[0][2]);
//				lblChosenExtra.setText("Fir");
//			}
//		});
//		lblFir.setHorizontalAlignment(SwingConstants.LEFT);
//		lblFir.setIcon(new ImageIcon(Images.ExtraIcons[0][2].getImage()));
//		lblFir.setFont(new Font("Tahoma", Font.BOLD, 13));
//		lblFir.setBounds(10, 153, 159, 32);
//		panel_3.add(lblFir);
//		
//		JLabel lblNothing = new JLabel("Nothing");
//		lblNothing.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent arg0) {
//				Board.Extrasel = true;
//				Board.selEXTRA = 0;
//				lblChosenExtra.setIcon(Images.ExtraIcons[0][0]);
//				lblChosenExtra.setText("Nothing");
//			}
//		});
//		lblNothing.setHorizontalAlignment(SwingConstants.LEFT);
//		lblNothing.setIcon(new ImageIcon(Images.ExtraIcons[0][0].getImage()));
//		lblNothing.setFont(new Font("Tahoma", Font.BOLD, 13));
//		lblNothing.setBounds(10, 67, 159, 32);
//		panel_3.add(lblNothing);
//		board.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{panel, /*toggleButton,*/ textField, tabbedPane, panel_1, label, lblSeaWater, lblFreshWater, lblFloatingWater, lblGrass, lblSand, lblGravel, lblFarmland, lblChosenID, panel_2, lblOcean, lblGrassland, lblDesert, lblPath, lblBeach, lblForrest, lblRiver, lblLake, panel_3, lblTree, lblFlowers}));
		
		frmTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTest.setSize(1101, 765);
		frmTest.setVisible(true);
		frmTest.setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		frmTest.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
//		JMenuItem menuItem = new JMenuItem("Save");
//		menuItem.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				if(Board.MapOpen == true)
//				MapWriter.writeFiles(0);
//			}
//		});
//		mnFile.add(menuItem);
//		
//		JMenuItem menuItem_2 = new JMenuItem("New Map");
//		mnFile.add(menuItem_2);
//		menuItem_2.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				MapGenerator.generateNewMap();
//				MapRender.screenx = 0;
//				MapRender.screeny = 0;
//				Board.MapOpen = true;
//				Map.mapChange = true;
//				JOptionPane.showMessageDialog(null,"Which Size shall your map have?","Map Size", JOptionPane.QUESTION_MESSAGE);
//			}
//		});
		
		JMenuItem menuItem_3 = new JMenuItem("New Empty Map");
		mnFile.add(menuItem_3);
		menuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Board.MapOpen = true;
				Board.mapGen.generateNewEmptyMap();
				Map.mapChange = true;
				MapRender.screenx = 0;
				MapRender.screeny = 0;
			}
		});
		
//		JMenuItem menuItem_4 = new JMenuItem("Load Map");
//		mnFile.add(menuItem_4);
//		menuItem_4.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				JFileChooser chooser = new JFileChooser();
//				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//				FileFilter type = new ExtensionFileFilter(".map (recommended)",new String[]{"MAP"});
//				chooser.setFileFilter(type);
//				chooser.setSelectedFile(new File(System.getProperty("user.home") + "/2D Game/Maps"));
//				int returnVal = chooser.showOpenDialog(board);
//				if(returnVal == JFileChooser.APPROVE_OPTION) 
//				MapReader.readMap(chooser.getSelectedFile().getAbsolutePath());
//			}
//		});
		
//		JMenuItem mntmBiome = new JMenuItem("Biome");
//		mntmBiome.setIcon(new ImageIcon(InterfaceNew.class.getResource("/sources/Environment/Lake.png")));
//		mntmBiome.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				if (Board.MapOpen){
//				Map.mapDrawState =1;
//				Map.mapChange = true;
//				MapRender.drawMap(Board.g2d, board.getSize());
//				}
//			}
//		});
//		mnAnsicht.add(mntmBiome);
		
//		final JMenuItem mntmBlock = new JMenuItem("ID");
//		mntmBlock.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if (Board.MapOpen){
//					Map.mapDrawState =0;
//					Board.Mapstate = "ID";
//					Map.mapChange = true;
//					MapRender.drawMap(Board.g2d, board.getSize());
//					}
//			}
//		});
//		mntmBlock.setIcon(new ImageIcon(Images.IdIcons[0][4].getImage()));
//		mnAnsicht.add(mntmBlock);
		
//		final JMenuItem mntmExtra = new JMenuItem("Extra");
//		mntmExtra.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				Board.Mapstate = "EXTRA";
//			}
//		});
//		mntmExtra.setIcon(new ImageIcon(Images.ExtraIcons[0][1].getImage()));
//		mnAnsicht.add(mntmExtra);
		
//		final JMenuItem mntmViewSwitch = new JMenuItem("Switch View");
//		mntmViewSwitch.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				if (Board.MapOpen){
//				if (Map.miniMap)Map.miniMap = false;
//				else Map.miniMap = true;
//				Map.mapChange = true;
//				MapRender.drawMap(Board.g2d, board.getSize());
//				}
//			}
//		});
//		mntmViewSwitch.setIcon(new ImageIcon(Images.OtherIcons[0][2].getImage()));
//		mnAnsicht.add(mntmViewSwitch);
		
//		final JMenuItem mntmReloadTex = new JMenuItem("Reload Textures");
//		mntmReloadTex.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
////				Images.loadAllImages();
////				mntmBlock.setIcon(new ImageIcon(Images.IdIcons[0][4].getImage()));
////				lblGrass.setIcon(new ImageIcon(Images.IdIcons[0][4].getImage()));
////				lblSand.setIcon(new ImageIcon(Images.IdIcons[0][3].getImage()));
////				mntmExtra.setIcon(new ImageIcon(Images.ExtraIcons[0][1].getImage()));
////				lblTree.setIcon(new ImageIcon(Images.ExtraIcons[0][1].getImage()));
////				lblOcean.setIcon(new ImageIcon(Images.BiomeIcons[0][1].getImage()));
////				lblGrassland.setIcon(new ImageIcon(Images.BiomeIcons[0][2].getImage()));
////				lblDesert.setIcon(new ImageIcon(Images.BiomeIcons[0][3].getImage()));
////				lblBeach.setIcon(new ImageIcon(Images.BiomeIcons[0][4].getImage()));
////				lblForrest.setIcon(new ImageIcon(Images.BiomeIcons[0][5].getImage()));
////				lblPath.setIcon(new ImageIcon(Images.BiomeIcons[0][6].getImage()));
////				lblRiver.setIcon(new ImageIcon(Images.BiomeIcons[0][7].getImage()));
////				lblLake.setIcon(new ImageIcon(Images.BiomeIcons[0][8].getImage()));
////				mntmReloadTex.setIcon(new ImageIcon(Images.OtherIcons[0][1].getImage()));
////				mntmViewSwitch.setIcon(new ImageIcon(Images.OtherIcons[0][2].getImage()));
//			}
//		});
//		mntmReloadTex.setIcon(new ImageIcon(Images.OtherIcons[0][1].getImage()));
//		mnAnsicht.add(mntmReloadTex);
		
		
	}

	class ExtensionFileFilter extends FileFilter {
		  String description;
		  String extensions[];
		  public ExtensionFileFilter(String description, String extension) {
		    this(description, new String[] { extension });
		  }

		  public ExtensionFileFilter(String description, String extensions[]) {
		    if (description == null) this.description = extensions[0];
		    else this.description = description;
		    this.extensions = (String[]) extensions.clone();
		    toLower(this.extensions);
		  }

		  private void toLower(String array[]) {
		    for (int i = 0, n = array.length; i < n; i++) {
		      array[i] = array[i].toLowerCase();
		    }
		  }
		  public String getDescription() {
		    return description;
		  }
		  public boolean accept(File file) {
		    if (file.isDirectory()) return true;
		    else {
		      String path = file.getAbsolutePath().toLowerCase();
		      for (int i = 0, n = extensions.length; i < n; i++) {
		        String extension = extensions[i];
		        if ((path.endsWith(extension) && (path.charAt(path.length() - extension.length() - 1)) == '.')) return true;
		      }
		    }
		    return false;
		  }
		}
	
	public boolean getFrmTestVisible() {
		return frmTest.isVisible();
	}
	public void setFrmTestVisible(boolean visible) {
		frmTest.setVisible(visible);
	}
}
