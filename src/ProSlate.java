import javax.swing.*;
import static javax.swing.SwingConstants.SOUTH;

public class ProSlate extends JPanel {

    JFrame frame = new JFrame("JFrame Example");
    JPanel panel = new JPanel();

    private void createMenu() {

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
    }

    public static void main(String s[]) {
        ProSlate x = new ProSlate();
        x.startup();
    }
}  