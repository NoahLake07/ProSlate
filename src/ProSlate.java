import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ProSlate extends JPanel {

    JFrame frame = new JFrame("JFrame Example");
    JPanel panel = new JPanel();

    private void createMenu() {
        //Where the GUI is created:
        JMenuBar menuBar;
        JMenu documentMenu, programMenu;
        JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;

        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the first menu.
        programMenu = new JMenu("ProSlate CE");
        programMenu.setMnemonic(KeyEvent.VK_A);


        documentMenu = new JMenu("Documents");
        documentMenu.setMnemonic(KeyEvent.VK_A);

        menuBar.add(programMenu);
        menuBar.add(documentMenu);

//a group of radio button menu items
        programMenu.addSeparator();
        ButtonGroup group = new ButtonGroup();
        rbMenuItem = new JRadioButtonMenuItem("Light Mode");
        rbMenuItem.setSelected(true);
        rbMenuItem.setMnemonic(KeyEvent.VK_M);
        group.add(rbMenuItem);
        programMenu.add(rbMenuItem);

        rbMenuItem = new JRadioButtonMenuItem("Dark Mode");
        rbMenuItem.setMnemonic(KeyEvent.VK_M);
        group.add(rbMenuItem);
        programMenu.add(rbMenuItem);

        //a group of check box menu items
        programMenu.addSeparator();
        cbMenuItem = new JCheckBoxMenuItem("Sound");
        cbMenuItem.setMnemonic(KeyEvent.VK_C);
        programMenu.add(cbMenuItem);

        frame.setJMenuBar(menuBar);
    }


    private void startup(){

        // adding additional components to the panel
        createMenu();

        JButton slatePgBtn = new JButton("Slate");
        JButton documentsPgBtn = new JButton("Documents");
        slatePgBtn.setVisible(true);

        // adding the panel to the frame
        frame.add(panel);

        // specifying behaviors and positions
        frame.setSize(900, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);

        // turn on menu items
        
    }

    public static void main(String s[]) {
        ProSlate x = new ProSlate();
        x.startup();
    }
}  