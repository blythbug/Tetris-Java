package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class KeyHandler extends KeyAdapter {

    public static boolean upPressed, downPressed, leftPressed, rightPressed, pausePressed;

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();
        if(code == KeyEvent.VK_P){
            if(pausePressed){
                pausePressed = false;
            }
            else{
                pausePressed = true;
            }
        }

        if(code == KeyEvent.VK_W){
            upPressed = true;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_S){
            downPressed = true;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = true;
        }
    }

}
