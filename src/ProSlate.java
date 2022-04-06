import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ProSlate extends GraphicsProgram {

    // creating global variables for the navigation system
    public final String[] pages = new String[]{ "Slate","Documents" };
    public String currentPg = "Slate";

    // creating local variables to assist with UI
    final double NAV_PADDING = 5;
    final double NAV_BTN_PADDING = 48;
    final double EST_BTN_HEIGHT = 30;

    // instantiating the navigation JButtons
    JButton slateButton = new JButton("Slate");

    // instantiating the navigation bar
    GRect navBar = new GRect(getWidth() + (NAV_PADDING * 2),getHeight()/7);

    // instantiating the navIndication GRect
    GRect navIndication = new GRect(30,3);

    @Override
    public void init(){
        createNavBar();
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

    private void createNavBar(){

        // creating the main navBar
        add(navBar,0-NAV_PADDING,getHeight()-navBar.getHeight());
        navBar.setVisible(true);
        navBar.setFilled(true);
        navBar.setFillColor(new Color(138, 182, 194));

        // adding the slate navButton
        add(slateButton, NAV_BTN_PADDING,navBar.getY() + navBar.getHeight()/2 - EST_BTN_HEIGHT/2);

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
        add(navIndication,buttonXPos,navBar.getY() - (navBar.getHeight()/4 * 3));
    }

    public void print(String text){
        System.out.println("ProSlate: [" + text + "]");
    }

    public static void main(String[] args) {
        ProSlate x = new ProSlate();
        x.start();
    }

}
