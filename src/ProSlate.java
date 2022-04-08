import acm.graphics.GLine;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

import static java.lang.String.valueOf;

public class ProSlate extends GraphicsProgram {

    //creating checkboxes for settings
    JCheckBox soundCkBox = new JCheckBox("Sound");
    JCheckBox flashScreen = new JCheckBox("Flash");
    JCheckBox secondFlash = new JCheckBox("Second Flash");
    JCheckBox darkMode = new JCheckBox("Dark Mode");

    // creating labels for settings
    JLabel credit = new JLabel("Created by Lakeside Films");
    JLabel settingsHeader = new JLabel("Settings");

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
    final double EST_BTN_HEIGHT = 29;

    // instantiating the navigation and slate JButtons
    JButton slateButton = new JButton("Slate");
    JButton docButton = new JButton("Documents");
    JButton startSlateBtn = new JButton("Start");
    JButton settingsButton = new JButton("Settings");

    // instantiating the navigation bar
    GRect navBar = new GRect(0,0); // will be resized later

    // instantiating the navIndication GRects and GLines
    GRect[] navIndication = new GRect[]{
            new GRect(getWidth()/3,navBar.getHeight()),
            new GRect(getWidth()/3,navBar.getHeight()),
            new GRect(getWidth()/3,navBar.getHeight()),
            new GRect(getWidth()/3,navBar.getHeight())};
    GLine divider1 = new GLine(navIndication[1].getX(),navBar.getY(),navIndication[1].getX(),navBar.getY() + navBar.getHeight());
    GLine divider2 = new GLine(navIndication[2].getX(),navBar.getY(),navIndication[2].getX(),navBar.getY() + navBar.getHeight());

    @Override
    public void init(){
        createNavBar();
        positionNavIndication();
        createSlateSystem();
        createDocumentSystem();
        createSettings();
        updatePage();
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
                updatePage();
                break;
            case "Documents":
                print("Documents button pressed");
                currentPg = "Documents";
                print("Page changed to: " + currentPg);
                updatePage();
                break;
            case "Start":
                // * one of these method calls will output an error message due to not being on both computers at once.
                playSound("/Users/NL21320/Documents/ProgrammingProjects/ProSlate/sounds/Slate Sound WAV.wav"); // macbook air pathname
                 // playSound("/Users/NL21320/Documents/ProgrammingProjects/ProSlate/sounds/Slate Sound WAV.wav"); // zephyrus 16 pathname
                flashScreen();
                break;
            case "Settings":
                print("Settings button pressed");
                currentPg = "Settings";
                print("Page changed to: " + currentPg);
                updatePage();
                break;
            case "Dark Mode":
                print("Dark Mode Enabled");
                break;
        }
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

    private void createDocumentSystem(){

    }

    private void createSettings(){
        Font creditFont = new Font("Sans Serif", Font.PLAIN, 10);
        Font headerFont = new Font ("Sans Serif",Font.BOLD,34);

        int paddingFromLeft = getWidth()/10;
        int paddingFromSection = getWidth()/60;
        int paddingFromOption = getWidth()/35;

        // header
        add(settingsHeader,getWidth()/15,getHeight()/14);
        settingsHeader.setFont(headerFont);
        settingsHeader.setSize(300,40);
        // sound checkbox
        add(soundCkBox, paddingFromLeft,getHeight()/14 + 45 + paddingFromSection);
        soundCkBox.setSelected(true);
        // flash checkboxes
        add(flashScreen, paddingFromLeft, soundCkBox.getY() + paddingFromOption + paddingFromSection);
        flashScreen.setSelected(true);
        add(secondFlash, paddingFromLeft, flashScreen.getY() + paddingFromOption);
        flashScreen.setSelected(true);
        // credits
        add(credit, navBar.getX() + NAV_BTN_PADDING/3, navBar.getY() - 30);
        credit.setFont(creditFont);
        credit.setSize(200,15);
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

        // adding the settings navButton
        add(settingsButton,navBar.getWidth() - NAV_BTN_PADDING - 95 ,navBar.getY() + navBar.getHeight()/2 - EST_BTN_HEIGHT/2);
        print("settingsButton.getHeight = " + settingsButton.getHeight());
        print("settingsButton.getWidth = " + settingsButton.getWidth());

        // adding the documents navButton
        add(docButton, getWidth()/2 - 116/2,navBar.getY() + navBar.getHeight()/2 - EST_BTN_HEIGHT/2);
        print("docBtn.getWidth = " + docButton.getWidth());
        // adding the action listeners to the buttons
        addActionListeners();
    }

    private void positionNavIndication() {
        for (int i = 0; i < 3; i++) {
            navIndication[i].setSize(navBar.getWidth()/3,navBar.getHeight());
            add(navIndication[i],navBar.getX() + navBar.getWidth()/3 * i,navBar.getY());
            navIndication[i].setFilled(true);
            navIndication[i].setFillColor(new Color(88, 126, 133));
            navIndication[i].sendToFront();
            navIndication[i].setVisible(false);
        }
        add(divider1);
        add(divider2);
    }

    private void flashScreen(){
        new Thread(new Runnable() {
            public void run() {
                // instantiating and adding the flash
                GRect flash = new GRect(getWidth(), getHeight());
                add(flash,0,0);

                // first flash
                if(flashScreen.isSelected()) {
                    flash.setFilled(true);
                    flash.sendToFront();
                    flash.setVisible(true);
                    flash.setFillColor(new Color(238, 255, 0));
                    slateButton.setVisible(false);
                    docButton.setVisible(false);
                    startSlateBtn.setVisible(false);
                    pause(130);

                    // second flash
                    if(secondFlash.isSelected()) {
                        flash.setFillColor(new Color(255, 0, 251));
                        pause(132);
                        flash.setVisible(false);
                    }
                    slateButton.setVisible(true);
                    docButton.setVisible(true);
                    startSlateBtn.setVisible(true);
                }
                flash.setVisible(false);
                destroy();
            }
        }).start();
    }

    private void updatePage(){
        if (currentPg.equals("Slate")) {

            // hiding other page components
            hideSettings();
            hideDocument();

            // showing current page components
            showSlate();

            // updating the navIndication GRects
            navIndication[0].setVisible(true);
            navIndication[1].setVisible(false);
            navIndication[2].setVisible(false);

        }
        if (currentPg.equals("Documents")) {

            // hiding other page components
            hideSlate();
            hideSettings();

            // showing current page components

            // updating the navIndication GRects
            navIndication[0].setVisible(false);
            navIndication[1].setVisible(true);
            navIndication[2].setVisible(false);

        }
        if (currentPg.equals("Settings")) {

            // hiding other page components
            hideSlate();

            // showing current page components
            showSettings();

            // updating the navIndication GRects
            navIndication[0].setVisible(false);
            navIndication[1].setVisible(false);
            navIndication[2].setVisible(true);

        }
        divider1.sendToFront();
        divider2.sendToFront();
    }

    private void hideSlate(){
        take.setVisible(false);
        scene.setVisible(false);
        startSlateBtn.setVisible(false);
    }

    private void showSlate(){
        take.setVisible(true);
        scene.setVisible(true);
        startSlateBtn.setVisible(true);
    }

    private void hideDocument(){

    }

    private void showDocument(){

    }

    private void hideSettings(){
        soundCkBox.setVisible(false);
        flashScreen.setVisible(false);
        secondFlash.setVisible(false);
        credit.setVisible(false);
        settingsHeader.setVisible(false);
    }

    private void showSettings(){
        soundCkBox.setVisible(true);
        flashScreen.setVisible(true);
        secondFlash.setVisible(true);
        credit.setVisible(true);
        settingsHeader.setVisible(true);
    }

    public void playSound(String pathname){
        if(soundCkBox.isSelected() == false) {
            File file = new File(pathname);
            try {
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(file));
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
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