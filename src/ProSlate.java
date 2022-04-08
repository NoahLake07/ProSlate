import acm.graphics.GRect;
import acm.program.GraphicsProgram;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

import static java.lang.String.valueOf;

public class ProSlate extends GraphicsProgram {

    // creating global variables for slate
    int currentScene = 1;
    char currentShot = 'a';
    int currentTake = 1;
    JLabel take = new JLabel("Take " + currentTake);
    JLabel scene = new JLabel("Scene " + currentScene + currentShot);

    // creating global variables for the navigation system
    public final String[] pages = new String[]{ "Slate","Documents" };
    public String currentPg = "Slate";

    // creating local variables to assist with UI
    final double NAV_PADDING = 5;
    final double NAV_BTN_PADDING = 75;
    final double EST_BTN_HEIGHT = 32;

    // instantiating the navigation and slate JButtons
    JButton slateButton = new JButton("Slate");
    JButton docButton = new JButton("Documents");
    JButton startSlateBtn = new JButton("Start");

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
                print("Slate button pressed");
                currentPg = "Slate";
                print("Page changed to: " + currentPg);
                take.setVisible(true);
                scene.setVisible(true);
                startSlateBtn.setVisible(true);
                break;
            case "Documents":
                print("Documents button pressed");
                currentPg = "Documents";
                print("Page changed to: " + currentPg);
                take.setVisible(false);
                scene.setVisible(false);
                startSlateBtn.setVisible(false);
                break;
            case "Start":
                // * one of these method calls will output an error message due to not being on both computers at once.
                playSound("/Users/NL21320/Documents/ProgrammingProjects/ProSlate/sounds/Slate Sound WAV.wav"); // macbook air pathname
                playSound("insert pathname here"); // zephyrus 16 pathname
                flashScreen();
                break;
        }
    }

    private void flashScreen(){
        new Thread(new Runnable() {
            public void run() {
                GRect flash = new GRect(getWidth(), getHeight());
                add(flash,0,0);
                flash.setFilled(true);
                flash.sendToFront();
                flash.setVisible(true);
                flash.setFillColor(new Color(238, 255, 0));
                slateButton.setVisible(false);
                docButton.setVisible(false);
                startSlateBtn.setVisible(false);
                pause(130);
                flash.setFillColor(new Color(0, 140, 255));
                pause(132);
                flash.setVisible(false);
                slateButton.setVisible(true);
                docButton.setVisible(true);
                startSlateBtn.setVisible(true);
            }
        }).start();
    }

    private void createSlateSystem(){
        // set up padding scalable ratio
        int padding = 10;

        // creating custom font
        Font font1 = new Font("Sans Serif", Font.BOLD, 80);
        Font font2 = new Font("Sans Serif", Font.PLAIN, 40);

        // scene
        add(scene, getWidth()/padding,getHeight()/padding);
        scene.setFont(font1);
        scene.setSize(400,80);

        // take
        add(take,getWidth()/padding,getHeight()/padding + 100);
        take.setFont(font2);
        take.setSize(400,50);

        // adding the start button to the screen
        add(startSlateBtn, navBar.getX()+navBar.getWidth() - (75 + 30),navBar.getY()-50);
        print(startSlateBtn.getWidth());

        addActionListeners();
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

        // adding the documents navButton
        add(docButton, getWidth() - NAV_BTN_PADDING - 116,navBar.getY() + navBar.getHeight()/2 - EST_BTN_HEIGHT/2);
        print(docButton.getWidth());
        // adding the action listeners to the buttons
        addActionListeners();
    }

    private void positionNavIndication() {
        navIndication.setSize(navBar.getWidth()/3,navBar.getHeight());
        add(navIndication,navBar.getX(),navBar.getY());
        navIndication.setFilled(true);
        navIndication.setFillColor(new Color(88, 126, 133));
        navIndication.sendToFront();
    }

    public void playSound(String pathname){
        File lol = new File(pathname);
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(lol));
            clip.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void print(String text){
        System.out.println("ProSlate: [" + text + "]");
    }

    public static void main(String[] args) {
        ProSlate x = new ProSlate();
        x.start();
    }

}