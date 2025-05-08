package main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("TetraClone");   // window title
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     // close window
        window.setResizable(false);    // window size is fixed

        // adding GameArea to window
        main.GameArea ga = new main.GameArea();
        window.add(ga);
        window.pack();

        window.setLocationRelativeTo(null);         // no set specific location for screen; defaults to the centre
        window.setVisible(true);        // window is visible to user

        ga.initGame();
    }
}