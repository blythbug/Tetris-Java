package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyHandler extends KeyAdapter {

    public static boolean upPressed, downPressed, leftPressed, rightPressed, pausePressed;
    
    // Debug flags
    private static final boolean DEBUG_MODE = true;             // change for production
    private static int keyPressCount = 0;
    private static long lastKeyPressTime = 0;
    
    // Method to get current state
    public String getInputState() {
        return String.format("Up: %b, Down: %b, Left: %b, Right: %b, Pause: %b", 
            upPressed, downPressed, leftPressed, rightPressed, pausePressed);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        
        if (DEBUG_MODE) {
            keyPressCount++;
            long currentTime = System.currentTimeMillis();
            long timeSinceLastPress = currentTime - lastKeyPressTime;
            lastKeyPressTime = currentTime;
            
            System.out.printf("[KeyDebug] Key pressed: %s (code: %d)%n", 
                KeyEvent.getKeyText(code), code);
            System.out.printf("[KeyDebug] Total presses: %d, Time since last press: %dms%n", 
                keyPressCount, timeSinceLastPress);
        }

        if(code == KeyEvent.VK_P) {
            pausePressed = !pausePressed;
            if (DEBUG_MODE) {
                System.out.printf("[KeyDebug] Game %s%n", 
                    pausePressed ? "PAUSED" : "RESUMED");
            }
        }

        if(code == KeyEvent.VK_W) {
            upPressed = true;
            if (DEBUG_MODE) System.out.println("[KeyDebug] Up pressed");
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = true;
            if (DEBUG_MODE) System.out.println("[KeyDebug] Left pressed");
        }
        if(code == KeyEvent.VK_S) {
            downPressed = true;
            if (DEBUG_MODE) System.out.println("[KeyDebug] Down pressed");
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = true;
            if (DEBUG_MODE) System.out.println("[KeyDebug] Right pressed");
        }
        
        if (DEBUG_MODE) {
            System.out.println("[KeyDebug] Current state: " + getInputState());
        }
    }
    
    // Reset all keys (useful for debugging)
    public static void resetKeys() {
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
        if (DEBUG_MODE) {
            System.out.println("[KeyDebug] All keys reset");
            System.out.println("[KeyDebug] Current state: " + 
                new KeyHandler().getInputState());
        }
    }
    
    // Get statistics
    public static String getDebugStats() {
        return String.format("Total key presses: %d, Last press: %dms ago", 
            keyPressCount, 
            System.currentTimeMillis() - lastKeyPressTime);
    }
}