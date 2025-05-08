// class draws play area, manages tetrominoes,
// handles gameplay actions (score, lines etc.)
package main;

import tetromino.*;

import java.awt.*;
import java.util.Random;

public class gameplayManage {

    // Play Area
    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    // Tetromino
    tetromino currentMino;
    final int MINO_INIT_X;
    final int MINO_INIT_Y;

    // tetromino drop
    public static int dropInterval = 60;   // tetromino drops every 60 frames

    public gameplayManage() {

        // Game Area Frame
        left_x = (GameArea.WIDTH/2) - (WIDTH/2); // 1280/2 - 360/2 = 460
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        // starting tetromino position (centre of play area)
        MINO_INIT_X = left_x + (WIDTH/2) - block.SIZE;
        MINO_INIT_Y = top_y + block.SIZE;

        // initialising starting tetromino
        currentMino = pickMino();         // call pickmino to generate a random tetromino
        currentMino.setXY(MINO_INIT_X, MINO_INIT_Y);
    }
    private tetromino pickMino(){

        // pick a random tetromino block
        tetromino mino = null;
        int i = new Random().nextInt(7);

        switch(i) {
            case 0: mino = new L1_mino();break;
            case 1: mino = new L2_mino();break;
            case 2: mino = new bar_mino();break;
            case 3: mino = new snake1_mino();break;
            case 4: mino = new snake2_mino();break;
            case 5: mino = new T_mino();break;
            case 6: mino = new square_mino();break;

            default:
                throw new IllegalStateException("Unexpected value: " + i);
        }
        return mino;
    }



    public void update(){

        currentMino.update();

    }
    public void draw(Graphics2D g2){

        // Game Area
        g2.setColor(Color.MAGENTA);
        g2.setStroke(new BasicStroke(4f)); //4 pixels width
        g2.drawRect(left_x-4, top_y-4, WIDTH+8, HEIGHT+8); // tetromino will collide with the inner
                                                                              // boundary instead of the outer
        // NEXT BLOCK frame
        int x = right_x + 100;
        int y = top_y - 5 ;
        g2.drawRect(x, y, 200, 200);
        // next label
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.drawString("NEXT", x + 70, y - 10); // Adjust text position

        // draw current tetromino
        if(currentMino != null) {         // check if null to avoid NullPointerException errors
            currentMino.draw(g2);

            //CENTRE TEST
            //g2.setColor(Color.RED);
            //g2.fillRect(MINO_INIT_X, MINO_INIT_Y, 10, 10);;
        }

        // "press P to pause"
        g2.setColor(Color.yellow);
        g2.setFont(new Font("Arial", Font.BOLD, 15));
        x = right_x + 115;
        y = top_y + 220;
        g2.drawString("Press P to pause!", x, y);

        // Pause button [P]
        g2.setColor(Color.yellow);
        g2.setFont(new Font("Arial", Font.BOLD, 50));
        if(KeyHandler.pausePressed){
            x = left_x + 80;
            y = top_y + 320;
            g2.drawString("PAUSED", x, y);
        }

    }
}
