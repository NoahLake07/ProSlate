import acm.graphics.GImage;
import acm.graphics.GLine;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import svu.csc213.Dialog;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;

import static java.lang.String.valueOf;

public class ProSlate extends GraphicsProgram {

    // system variables
    String systemOS = OsUtils.getOS();

    // instantiating the slate variable changers
    GImage logo, scenePlus, sceneSub, takeAdd, takeSub,shotAdd,shotSub,markerBtn;
    char[] alphabet = new char[]{
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
    };

    // instantiating the settings reset images
    GImage resetSettings, resetUI;

    // creating custom fonts
    Font largeHeader = new Font("Sans Serif", Font.BOLD, 80);
    Font largeSubHeader = new Font("Sans Serif", Font.PLAIN, 40);

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
    int currentShot = 1;
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
    GRect[] navIndication = new GRect[]   {
    new GRect(getWidth()/3,navBar.getHeight()),
    new GRect(getWidth()/3,navBar.getHeight()),
    new GRect(getWidth()/3,navBar.getHeight()),
    new GRect(getWidth()/3,navBar.getHeight())    };
    GLine divider1 = new GLine(navIndication[1].getX(),navBar.getY(),navIndication[1].getX(),navBar.getY() + navBar.getHeight());
    GLine divider2 = new GLine(navIndication[2].getX(),navBar.getY(),navIndication[2].getX(),navBar.getY() + navBar.getHeight());

    @Override
    public void init(){
        OsUtils utils = new OsUtils();
        importFrom(utils.getOS());

        createNavBar();
        positionNavIndication();
        createSlateSystem();
        createDocumentSystem();
        createSettings();

        updatePage();
    }

    @Override
    public void mouseClicked(MouseEvent me) {

        Object source = me.getSource();

        if (scenePlus.equals(source)) {

            System.out.println("add scene");
            currentScene++;

        } else if (sceneSub.equals(source)) {
            if(currentScene >1) {
                System.out.println("sub scene");
                currentScene--;
            }
        } else if (takeAdd.equals(source)) {

            System.out.println("add take");
            currentTake++;

        } else if (takeSub.equals(source)) {
            if (currentTake > 1) {
                System.out.println("sub take");
                currentTake--;
            }
        } else if (shotAdd.equals(source)) {
            if (currentShot < 25) {
                System.out.println("add shot");
                currentShot++;
            }
        } else if (shotSub.equals(source)) {
            if (currentShot > 0) {
                System.out.println("sub shot");
                currentShot--;
            }
        }
        updateCounterLabels();
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
                flashScreen();
                break;
            case "Settings":
                print("Settings button pressed");
                currentPg = "Settings";
                print("Page changed to: " + currentPg);
                updatePage();
                break;
            case "C:/Users/noahl/IdeaProjects/ProSlate/images/GRY Right Arrow Btn.png":
                print("new scene");
                break;
        }
    }

    private void importFrom(String systemOS){
        if (systemOS.contains("Mac OS")){
            // MacBook path-names
            logo = new GImage("/Users/NL21320/Documents/ProgrammingProjects/ProSlate/images/ProSlate Logo Large.png");
            scenePlus = new GImage("/Users/NL21320/Documents/ProgrammingProjects/ProSlate/images/GRY Right Arrow Btn.png");
            sceneSub = new GImage("/Users/NL21320/Documents/ProgrammingProjects/ProSlate/images/GRY Left Arrow Btn.png");
            takeAdd = new GImage("/Users/NL21320/Documents/ProgrammingProjects/ProSlate/images/GRY Right Arrow Btn.png");
            takeSub = new GImage("/Users/NL21320/Documents/ProgrammingProjects/ProSlate/images/GRY Left Arrow Btn.png");

        } else if (systemOS.contains("Windows")){
            // Zephyrus path-names
            logo = new GImage("/C://Users//noahl//IdeaProjects//ProSlate//images//ProSlate Logo Large.png/");
            scenePlus = new GImage("C:/Users/noahl/IdeaProjects/ProSlate/images/GRY Right Arrow Btn.png");
            sceneSub = new GImage("C:/Users/noahl/IdeaProjects/ProSlate/images/GRY Left Arrow Btn.png");
            takeAdd = new GImage("C:/Users/noahl/IdeaProjects/ProSlate/images/GRY Right Arrow Btn.png");
            takeSub = new GImage("C:/Users/noahl/IdeaProjects/ProSlate/images/GRY Left Arrow Btn.png");
            shotAdd = new GImage("C:/Users/noahl/IdeaProjects/ProSlate/images/PS-AddShotBtn.png");
            shotSub = new GImage("C:/Users/noahl/IdeaProjects/ProSlate/images/PS-SubShotBtn.png");
            markerBtn = new GImage("C:/Users/noahl/IdeaProjects/ProSlate/images/PS-MarkerBtn.png");
        }
    }

    private void createSlateCounters(){

        int btnSpacing = 15;
        int imageResizing = 10;

        scenePlus.setSize(scenePlus.getWidth()/imageResizing, scenePlus.getHeight()/imageResizing);
        add(scenePlus,scene.getX() + scene.getWidth() + btnSpacing,scene.getY() + scene.getHeight()/2 - scenePlus.getHeight()/2);

        sceneSub.setSize(sceneSub.getWidth()/imageResizing, sceneSub.getHeight()/imageResizing);
        add(sceneSub,scene.getX() - (btnSpacing + sceneSub.getWidth()),scene.getY() + scene.getHeight()/2 - sceneSub.getHeight()/2);

        takeAdd.setSize(takeAdd.getWidth()/imageResizing, takeAdd.getHeight()/imageResizing);
        add(takeAdd,take.getX() + btnSpacing + take.getWidth(),take.getY() + take.getHeight()/2 - takeAdd.getHeight()/2);

        takeSub.setSize(takeSub.getWidth()/imageResizing, takeSub.getHeight()/imageResizing);
        add(takeSub,take.getX() - (btnSpacing + takeSub.getWidth()),take.getY() + take.getHeight()/2 - takeSub.getHeight()/2);

        shotSub.setSize(shotSub.getWidth()/(imageResizing * 2.5),shotSub.getHeight()/(imageResizing * 2.5));
        add(shotSub,startSlateBtn.getX() + getWidth()/6,startSlateBtn.getY() + shotSub.getHeight()/8);

        shotAdd.setSize(shotAdd.getWidth()/(imageResizing * 2.5),shotAdd.getHeight()/(imageResizing * 2.5));
        add(shotAdd,shotSub.getX() + shotSub.getWidth() + getWidth()/40,startSlateBtn.getY() + shotAdd.getHeight()/8);

        scenePlus.addMouseListener(this);
        sceneSub.addMouseListener(this);
        takeAdd.addMouseListener(this);
        takeSub.addMouseListener(this);
        shotAdd.addMouseListener(this);
        shotSub.addMouseListener(this);
    }

    private void updateCounterLabels(){
        take.setText("Take " + currentTake);
        scene.setText("Scene " + currentScene + alphabet[currentShot]);
        System.out.println(alphabet[currentShot]);
    }

    private void createSlateSystem(){
        // set up padding scalable ratio
        int padding = 10;

        // add logo
        add(logo);
        double logoScaling = 0.4;
        logo.setSize(logo.getWidth()*logoScaling,logo.getHeight()*logoScaling);
        logo.move(getWidth()-padding-logo.getWidth(),getHeight()- navBar.getHeight()-padding-logo.getHeight());

        // scene
        add(scene, getWidth()/padding,getHeight()/padding);
        scene.setFont(largeHeader);
        scene.setSize(400,80);

        // take
        add(take,getWidth()/padding,getHeight()/padding + 100);
        take.setFont(largeSubHeader);
        take.setSize(400,50);

        // adding the start button to the screen
        add(startSlateBtn, navBar.getX() + padding * 3,navBar.getY() - (padding * 4) - startSlateBtn.getHeight());

        createSlateCounters();

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

        Color navBarColor = new Color(138, 182, 194);
        Color navBtnColorLight = new Color(204, 225, 239);
        Color navBtnColorDark = new Color(94, 116, 131);

        double navBtnY = navBar.getY() + navBar.getHeight()/2 - EST_BTN_HEIGHT/2 - navBar.getHeight()/7;

        // creating the main navBar
        navBar.setSize(getWidth() + (NAV_PADDING * 2),getHeight()/6);
        add(navBar,0-NAV_PADDING,getHeight()-navBar.getHeight());
        navBar.setVisible(true);
        navBar.setFilled(true);
        navBar.setFillColor(navBarColor);

        // adding the slate navButton
        add(slateButton, NAV_BTN_PADDING*1,navBar.getY() + navBar.getHeight()/2 - EST_BTN_HEIGHT/2 - navBar.getHeight()/7);

        // adding the settings navButton
        add(settingsButton,navBar.getWidth() - NAV_BTN_PADDING - 95 ,navBar.getY() + navBar.getHeight()/2 - EST_BTN_HEIGHT/2 - navBar.getHeight()/7);

        // adding the documents navButton
        add(docButton, getWidth()/2 - 116/2,navBar.getY() + navBar.getHeight()/2 - EST_BTN_HEIGHT/2 - navBar.getHeight()/7);

        docButton.setBackground(navBtnColorLight);
        settingsButton.setBackground(navBtnColorLight);
        slateButton.setBackground(navBtnColorLight);
        startSlateBtn.setBackground(Color.white);

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
        } else if (systemOS.contains("macOS") || systemOS.contains("Mac OS")){
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
                GRect flash = new GRect(getWidth() + getWidth()/10, getHeight() + getHeight()/10);
                add(flash,-getWidth()/20,-getWidth()/20);
                take.setVisible(false);
                scene.setVisible(false);

                if(systemOS.contains("Windows")){
                    playSound("C:/Users/noahl/Documents/Documents/Programming/Projects/ProSlate/resources/Slate Sound WAV.wav");
                } else if (systemOS.contains("Mac OS")){
                    playSound("/Users/NL21320/Documents/ProgrammingProjects/ProSlate/sounds/Slate Sound WAV.wav");
                }

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

        Color darkModeText = new Color(145, 145, 145);
        Color darkNavIndication = new Color(51, 65, 75);
        Color darkBackground = new Color(44, 44, 44);

        // adjusting to dark mode
        if(darkMode.isSelected()){
            // changing the main background to a custom dark grey
            setBackground(darkBackground);
            // changing the slate text color to a custom light grey color
            take.setForeground(darkModeText);
            scene.setForeground(darkModeText);
            //changing other labels back to default
            settingsHeader.setForeground(darkModeText);
            credit.setForeground(darkModeText);
            // changing navBar details back to normal
            navBar.setFillColor(new Color(25, 35, 40));
            navIndication[0].setFillColor(darkNavIndication);
            navIndication[1].setFillColor(darkNavIndication);
            navIndication[2].setFillColor(darkNavIndication);
            // changing the checkbox colors
            darkMode.setForeground(darkModeText);
            darkMode.setBackground(darkBackground);
            soundCkBox.setForeground(darkModeText);
            soundCkBox.setBackground(darkBackground);
            flashScreen.setForeground(darkModeText);
            flashScreen.setBackground(darkBackground);
            secondFlash.setForeground(darkModeText);
            secondFlash.setBackground(darkBackground);

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
            navIndication[1].setFillColor(new Color(88, 126, 133));
            navIndication[2].setFillColor(new Color(88, 126, 133));
            // changing the checkbox colors
            darkMode.setForeground(Color.BLACK);
            darkMode.setBackground(Color.WHITE);
            soundCkBox.setForeground(Color.BLACK);
            soundCkBox.setBackground(Color.WHITE);
            flashScreen.setForeground(Color.BLACK);
            flashScreen.setBackground(Color.WHITE);
            secondFlash.setForeground(Color.BLACK);
            secondFlash.setBackground(Color.WHITE);

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
            showDocument();

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
        logo.setVisible(false);
        take.setVisible(false);
        scene.setVisible(false);
        startSlateBtn.setVisible(false);
        scenePlus.setVisible(false);
        sceneSub.setVisible(false);
        takeAdd.setVisible(false);
        takeSub.setVisible(false);
        shotSub.setVisible(false);
        shotAdd.setVisible(false);
    }

    private void showSlate(){
        logo.setVisible(true);
        take.setVisible(true);
        scene.setVisible(true);
        startSlateBtn.setVisible(true);
        scenePlus.setVisible(true);
        sceneSub.setVisible(true);
        takeAdd.setVisible(true);
        takeSub.setVisible(true);
        shotSub.setVisible(true);
        shotAdd.setVisible(true);
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
        if(soundCkBox.isSelected() == true) {
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