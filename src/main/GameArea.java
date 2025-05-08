package main;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class GameArea extends JPanel implements Runnable{

    final int FPS = 60;      // frames per second
    // Screen Size
    public static final int HEIGHT = 720;
    public static final int WIDTH = 1000;         // 1280

    Thread tetrisThread;
    GameplayManager gpm;             // instantiate GameplayManager class
    private Image backgroundImage; // Declare the backgroundImage field


    public GameArea() {

        //Area Settings
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setBackground(Color.black);   // background colour
        this.setLayout(null);              // no preset layout , so can be customised later

        // Add KeyListener
        this.addKeyListener(new KeyHandler());   // you can get a key input with KeyHandler
        this.setFocusable(true);                 // when you press a key while the window is focused

        gpm = new GameplayManager();

        setFocusable(true);
        requestFocusInWindow();


        // button to change background image
        JButton changeBackgroundButton = new JButton("Change Background");
        changeBackgroundButton.setBounds(WIDTH - 190,HEIGHT - 160, 150, 30); // Position and size of the button

        // customise button
        changeBackgroundButton.setForeground(Color.magenta); // Set the text color to magenta
        changeBackgroundButton.setBorder(BorderFactory.createLineBorder(Color.magenta, 2)); // Magenta border with thickness of 2
        changeBackgroundButton.setFocusPainted(false); // Remove the focus ring when clicked

        changeBackgroundButton.addActionListener(e -> chooseBackgroundImage()); // Add listener to button
        this.add(changeBackgroundButton);

        // Add this to your GameArea constructor
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                System.out.println("Game area gained focus");
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                System.out.println("Game area lost focus");
            }
        });
    }
    public void initGame(){
        tetrisThread = new Thread(this);
        tetrisThread.start();              // starting this thread will call the run() method

    }

    @Override
    public void run() {

        // Game Loop
        long now;
        long updateTime;
        long wait;

        final long OPTIMAL_TIME = 1000000000 / FPS;

        while (tetrisThread != null) {
            now = System.nanoTime();

            update();        // calling update()
            repaint();       // calling paintComponent

            updateTime = System.nanoTime() - now;
            wait = (OPTIMAL_TIME - updateTime) / 1000000;

            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
    // Update -> Update object positions, x y coordinates , score
    private void update(){

        // only update onscreen game information when the game is not paused
        if(!KeyHandler.pausePressed){
            gpm.update();   // call the update method in the GameplayManager class
        }


    }
    

    // Paint -> objects, UI
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; // convert graphics into graphics2D so we can call the draw method

        // Draw background image if available
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, WIDTH, HEIGHT, this); // Use backgroundImage
        }

        gpm.draw(g2); // call the draw method in the GameplayManager class and pass in Graphics2D as parameter
    }
    
    

    // load the background image from a file
    public void setBackgroundImage(String filePath) {
        try {
            backgroundImage = ImageIO.read(new File(filePath)); // Assign the image to backgroundImage
            repaint(); // Repaint to apply the new background immediately
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // player can choose a background image
    public void chooseBackgroundImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image Files", "jpg", "png", "gif"));

        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            setBackgroundImage(selectedFile.getAbsolutePath()); // Set the selected image as background
        }

        this.requestFocusInWindow();
    }
}