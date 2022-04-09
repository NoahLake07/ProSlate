import acm.graphics.GLine;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import svu.csc213.Dialog;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

import static java.lang.String.valueOf;

public class ProSlate extends GraphicsProgram {

    // system variables
    String systemOS = OsUtils.getOS();

    // creating custom font
    Font font1Light = new Font("Sans Serif", Font.BOLD, 80);
    Font font2Light = new Font("Sans Serif", Font.PLAIN, 40);
    Font font1Dark = new Font("Sans Serif", Font.BOLD, 80);
    Font font2Dark = new Font("Sans Serif", Font.PLAIN, 40);

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
    JButton addScene = new JButton("(+) Scene");
    JButton subScene = new JButton("(-) Scene");
    JButton addShot = new JButton("(+) Shot");
    JButton subShot = new JButton("(-) Shot");
    JButton addTake = new JButton("(+) Take");
    JButton subTake = new JButton("(-) Take");

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
        new OsUtils();
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
                 // zephyrus 16 pathname
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
                updatePage();
                break;
        }
    }

    private void createSlateSystem(){
        // set up padding scalable ratio
        int padding = 10;

        // scene
        add(scene, getWidth()/padding,getHeight()/padding);
        scene.setFont(font1Light);
        scene.setSize(400,80);

        // take
        add(take,getWidth()/padding,getHeight()/padding + 100);
        take.setFont(font2Light);
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
        // graphic checkboxes
        add(darkMode,paddingFromLeft, secondFlash.getY() + paddingFromOption + paddingFromSection);
        darkMode.setSelected(false);
        // credits
        add(credit, navBar.getX() + NAV_BTN_PADDING/3, navBar.getY() - 30);
        credit.setFont(creditFont);
        credit.setSize(200,15);
    }

    private void windowsNavBar(){

        double navBtnY = navBar.getY() + navBar.getHeight()/2 - EST_BTN_HEIGHT/2 - navBar.getHeight()/7;

        // creating the main navBar
        navBar.setSize(getWidth() + (NAV_PADDING * 2),getHeight()/6);
        add(navBar,0-NAV_PADDING,getHeight()-navBar.getHeight());
        navBar.setVisible(true);
        navBar.setFilled(true);
        navBar.setFillColor(new Color(138, 182, 194));

        // adding the slate navButton
        add(slateButton, NAV_BTN_PADDING*1,navBar.getY() + navBar.getHeight()/2 - EST_BTN_HEIGHT/2 - navBar.getHeight()/7);

        // adding the settings navButton
        add(settingsButton,navBar.getWidth() - NAV_BTN_PADDING - 95 ,navBar.getY() + navBar.getHeight()/2 - EST_BTN_HEIGHT/2 - navBar.getHeight()/7);

        // adding the documents navButton
        add(docButton, getWidth()/2 - 116/2,navBar.getY() + navBar.getHeight()/2 - EST_BTN_HEIGHT/2 - navBar.getHeight()/7);

        // adding the action listeners to the buttons
        addActionListeners();
    }

    private void macOsNavBar(){
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

        // adding the documents navButton
        add(docButton, getWidth()/2 - 116/2,navBar.getY() + navBar.getHeight()/2 - EST_BTN_HEIGHT/2);
        // adding the action listeners to the buttons
        addActionListeners();
    }

    private void createNavBar(){

        // checks for OS compatibility
        if(systemOS.contains("Windows")){
            print("Windows System detected.");
            windowsNavBar();
        } else if (systemOS.contains("macOS") || systemOS.contains("Apple")){
            print("macOS System detected.");
            macOsNavBar();
        } else {
            print("SYSTEM NOT SUPPORTED.");
            Dialog.showMessage("THIS OPERATING SYSTEM IS NOT SUPPORTED.");
            System.exit(0);
        }

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
                take.setVisible(false);
                scene.setVisible(false);

                playSound("C:/Users/noahl/Documents/Documents/Programming/Projects/ProSlate/resources/Slate Sound WAV.wav");

                if(darkMode.isSelected()){
                    // first flash
                    if(flashScreen.isSelected()) {
                        flash.setFilled(true);
                        settingsButton.setVisible(false);
                        flash.sendToFront();
                        flash.setVisible(true);
                        flash.setFillColor(new Color(0, 124, 136));
                        slateButton.setVisible(false);
                        docButton.setVisible(false);
                        startSlateBtn.setVisible(false);
                        pause(130);

                        // second flash
                        if(secondFlash.isSelected()) {
                            flash.setFillColor(new Color(114, 9, 145));
                            pause(132);
                            flash.setVisible(false);
                        }
                        slateButton.setVisible(true);
                        settingsButton.setVisible(true);
                        docButton.setVisible(true);
                        startSlateBtn.setVisible(true);
                    }
                } else {
                    // first flash
                    if(flashScreen.isSelected()) {
                        flash.setFilled(true);
                        settingsButton.setVisible(false);
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
                        settingsButton.setVisible(true);
                        docButton.setVisible(true);
                        startSlateBtn.setVisible(true);
                    }
                }

                flash.setVisible(false);
                take.setVisible(true);
                scene.setVisible(true);
                destroy();
            }
        }).start();
    }

    private void updatePage(){

        // adjusting to dark mode
        if(darkMode.isSelected()){
            // changing the main background to a custom dark grey
            setBackground(new Color(44, 44, 44));
            // changing the slate text color to a custom light grey color
            take.setForeground(new Color(145, 145, 145));
            scene.setForeground(new Color(145, 145, 145));
            //changing other labels back to default
            settingsHeader.setForeground(new Color(145, 145, 145));
            credit.setForeground(new Color(145, 145, 145));
            // changing navBar details back to normal
            navBar.setFillColor(new Color(25, 35, 40));
            navIndication[0].setFillColor(new Color(51, 65, 75));
        } else {
            // changing the main background to default
            setBackground(Color.white);
            // changing the slate text color to default
            take.setForeground(Color.BLACK);
            scene.setForeground(Color.BLACK);
            //changing other labels back to default
            settingsHeader.setForeground(Color.BLACK);
            credit.setForeground(Color.BLACK);
            // changing navBar details back to normal
            navBar.setFillColor(new Color(138, 182, 194));
            navIndication[0].setFillColor(new Color(88, 126, 133));
        }

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
        darkMode.setVisible(false);
    }

    private void showSettings(){
        soundCkBox.setVisible(true);
        flashScreen.setVisible(true);
        secondFlash.setVisible(true);
        credit.setVisible(true);
        settingsHeader.setVisible(true);
        darkMode.setVisible(true);
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