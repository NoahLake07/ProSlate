import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import static java.lang.String.valueOf;

public class ProSlate extends GraphicsProgram {

    // creating global variables for the navigation system
    public final String[] pages = new String[]{ "Slate","Documents" };
    public String currentPg = "Slate";

    // creating local variables to assist with UI
    final double NAV_PADDING = 5;
    final double NAV_BTN_PADDING = 75;
    final double EST_BTN_HEIGHT = 32;

    // instantiating the navigation JButtons
    JButton slateButton = new JButton("Slate");

    // instantiating the navigation bar
    GRect navBar = new GRect(0,0); // will be resized later

    // instantiating the navIndication GRects
    GRect navIndication = new GRect(0,0); // will be resized later

    Font x = new Font("Serif", Font.PLAIN, 50);

    @Override
    public void init(){
        createNavBar();
        positionNavIndication();
        createSlateSystem();
    }

    @Override
    public void actionPerformed(ActionEvent ae){
        // checking to see which button was pressed and acting accordingly
        switch(ae.getActionCommand()){
            // navigation
            case "Slate":
                print("Slate Button Pressed");
                currentPg = "Slate";
                print("Page changed to: " + currentPg);
                break;

            // settings
            // TODO: ADD SETTINGS
        }
    }

    private void createSlateSystem(){
        JLabel scene = new JLabel("Scene");
        add(scene, getWidth()/15,getHeight()/15);
        scene.setFont(x);
    }

    private void createNavBar(){

        // creating the main navBar
        navBar.setSize(getWidth() + (NAV_PADDING * 2),getHeight()/6);
        add(navBar,0-NAV_PADDING,getHeight()-navBar.getHeight());
        navBar.setVisible(true);
        navBar.setFilled(true);
        navBar.setFillColor(new Color(138, 182, 194));

        // adding the slate navButton
        add(slateButton, NAV_BTN_PADDING*1,navBar.getY() + navBar.getHeight()/2 - EST_BTN_HEIGHT/2);

        // adding the action listeners to the buttons
        addActionListeners();

    }

    private void positionNavIndication(){

        // setting the navigation reference x position to whatever the page is
        double buttonXPos = 0;
        switch(currentPg){
            case "Slate":
                buttonXPos = slateButton.getX();
                break;
        }

        // positioning the navIndication
        navIndication.setSize(navBar.getWidth()/3,navBar.getHeight());
        add(navIndication,navBar.getX(),navBar.getY());
        navIndication.setFilled(true);
        navIndication.setFillColor(new Color(88, 126, 133));
        navIndication.sendToFront();
    }

    public void print(String text){
        System.out.println("ProSlate: [" + text + "]");
    }

    public static void main(String[] args) {
        ProSlate x = new ProSlate();
        x.start();
    }

}