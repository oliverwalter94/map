import Data.Installer;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;

public class InterfaceNew extends JFrame {

    static JTextField textField;
    static String editorVersion = "0.5";

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                new InterfaceNew();
                Installer.runInstaller();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    private InterfaceNew() {

        JFrame frmTest = new JFrame("2D Game");
        frmTest.setIconImage(Toolkit.getDefaultToolkit().getImage(InterfaceNew.class.getResource("/sources/Environment/icon.png")));
        frmTest.setBackground(Color.LIGHT_GRAY);
        frmTest.setTitle("MapGen.Map Editor " + editorVersion);
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

        ExtensionFileFilter(String description, String extensions[]) {
            if (description == null) this.description = extensions[0];
            else this.description = description;
            this.extensions = extensions.clone();
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

}
