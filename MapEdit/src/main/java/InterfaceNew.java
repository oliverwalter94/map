import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;

public class InterfaceNew extends JFrame {

	private JPanel contentPane;
	public static JTextField textField;
	static String FileName = "Map";
	static String EditerVersion = "0.3.0.4";
	private static String Features = "*More and finally editable extras :D \r\n*Extras will be saved now and can also  be loaded\r\n*Some bugfixes";
	private JFrame frmTest;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				new InterfaceNew();
				Installer.runInstaller();
//				JOptionPane.showMessageDialog(null,Features, EditerVersion + "   New Features", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	private InterfaceNew() {
		
		frmTest = new JFrame("2D Game");
		frmTest.setIconImage(Toolkit.getDefaultToolkit().getImage(InterfaceNew.class.getResource("/sources/Environment/icon.png")));
		frmTest.setBackground(Color.LIGHT_GRAY);
		frmTest.setTitle("Map Editor " + EditerVersion);
		final Board board = new Board();
		frmTest.getContentPane().add(board);
		board.setLayout(null);

//
		frmTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTest.setSize(1101, 765);
		frmTest.setVisible(true);
		frmTest.setLocationRelativeTo(null);

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
				for (String extension : extensions) {
					if ((path.endsWith(extension) && (path.charAt(path.length() - extension.length() - 1)) == '.'))
						return true;
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
