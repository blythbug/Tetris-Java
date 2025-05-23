// class draws play area, manages tetrominoes,
// handles gameplay actions (score, lines etc.)
package main;

import tetromino.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameplayManager {

    // Play Area
    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    // Tetromino
    Tetromino currentMino;
    final int MINO_INIT_X;
    final int MINO_INIT_Y;

    // Next Tetromino

    Tetromino nextMino;
    final int NEXTMINO_X;
    final int NEXTMINO_Y;
    public static ArrayList<Block> staticBlocks = new ArrayList<>();


    // Tetromino drop
    public static int dropInterval = 60;   // Tetromino drops every 60 frames

    // game over
    public boolean gameOver;

    // score and level
    int level = 1;
    int lines;
    int score;

    public GameplayManager() {

        // Game Area Frame
        left_x = (GameArea.WIDTH/2) - (WIDTH/2); // 1280/2 - 360/2 = 460
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        // starting Tetromino position (centre of play area)
        MINO_INIT_X = left_x + (WIDTH/2) - Block.SIZE;
        MINO_INIT_Y = top_y + Block.SIZE;

        // next Tetromino
        NEXTMINO_X = right_x + 175;
        NEXTMINO_Y = top_y + 70;

        // initialising starting Tetromino
        currentMino = pickMino();         // call pickmino to generate a random Tetromino
        currentMino.setXY(MINO_INIT_X, MINO_INIT_Y);

        //  pick next random Tetromino
        nextMino = pickMino();
        nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);
    }
    private Tetromino pickMino(){

        // pick a random Tetromino Block
        Tetromino mino = null;
        int i = new Random().nextInt(7);

        mino = switch (i) {
            case 0 -> new L1Mino();
            case 1 -> new L2Mino();
            case 2 -> new BarMino();
            case 3 -> new Snake1Mino();
            case 4 -> new Snake2Mino();
            case 5 -> new T_Mino();
            case 6 -> new SquareMino();
            default -> throw new IllegalStateException("Unexpected value: " + i);
        };
        return mino;
    }



    public void update(){

        // check if current Tetromino is active
        if(!currentMino.active){

            // if Tetromino is not active, put into staticBlocks

            staticBlocks.add(currentMino.b[0]);
            staticBlocks.add(currentMino.b[1]);
            staticBlocks.add(currentMino.b[2]);
            staticBlocks.add(currentMino.b[3]);

            // check if game over
            if(currentMino.b[0].x == MINO_INIT_X && currentMino.b[0].y == MINO_INIT_Y){
                // current Tetromino immediately collided with Block and had no possible movement
                // when x y coord are the same with the next Tetromino , game over
                gameOver = true;
            }

            currentMino.sleep = false;

            // replace currentMino with nextMino
            currentMino = nextMino;
            currentMino.setXY(MINO_INIT_X, MINO_INIT_Y);
            nextMino = pickMino();
            nextMino.setXY(NEXTMINO_X,NEXTMINO_Y);

            // when a Tetromino becomes inactive, check if lines can be cleared
            clearValid();
        }
        else{
            currentMino.update();
        }
    }

    // check if lines are able to be cleared
    private void clearValid(){

        int x = left_x;
        int y = top_y;
        int blockCounter = 0;
        int lineCounter = 0;

        while( x < right_x && y < bottom_y){

            // scan game area by Block size
            for(int i = 0; i < staticBlocks.size(); i++)
                if (staticBlocks.get(i).x == x && staticBlocks.get(i).y == y) {
                    // if there is a static Block, increase Block counter
                    blockCounter++;
                }

            x += Block.SIZE;

            if(x == right_x){

                if(blockCounter == 12){   // check if Block counter hits 12 (max)
                    for(int i = staticBlocks.size()-1; i > -1; i--){
                        // remove all blocks in current y line
                        if(staticBlocks.get(i).y == y){
                            staticBlocks.remove(i);
                        }
                    }

                    // increase line counter and lines
                    lineCounter++;
                    lines++;

                    // if line score hits a certain number, increase drop speed
                    // 60 = Slowest 1 = Fastest
                    // Every 10 lines, level and drop speed increases [by -10]
                    if(lines % 10 == 0 && dropInterval > 1){

                        level++;
                        if(dropInterval > 10){
                            dropInterval -= 10;
                        }
                        // when drop interval is 10 or less, decrease by 1
                        else {
                            dropInterval -= 1;
                        }
                    }

                    //shift all lines down by 1
                    for(int i = 0; i < staticBlocks.size(); i++){
                        // if Block is above the current y, move it down by Block size
                        if(staticBlocks.get(i).y < y) {
                            staticBlocks.get(i).y += Block.SIZE;
                        }
                    }
                }
                blockCounter = 0;
                x = left_x;
                y += Block.SIZE;
            }

        }

        // Add Score
        if(lineCounter > 0){
            int singleLineScore = 10* level;
            score += singleLineScore * lineCounter;

        }

    }
    public void draw(Graphics2D g2){

        // Game Area
        g2.setColor(Color.MAGENTA);
        g2.setStroke(new BasicStroke(4f)); //4 pixels width
        g2.drawRect(left_x-4, top_y-4, WIDTH+8, HEIGHT+8); // Tetromino will collide with the inner
                                                                              // boundary instead of the outer
        // NEXT BLOCK frame
        int x = right_x + 100;
        int y = top_y - 5 ;
        g2.drawRect(x, y, 200, 200);
        // next label
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.drawString("NEXT", x + 70, y - 10); // Adjust text position

        // score frame
        g2.drawRect(x, bottom_y - 320, 200, 300);
        x += 20;
        y = 390;
        g2.setFont(new Font("Arial", Font.BOLD, 15));
        g2.drawString("LEVEL: " + level, x, y); y+= 70;
        g2.drawString("LINES CLEAR: " + lines, x, y); y+= 70;
        g2.drawString("SCORE: " + score, x, y); y+= 70;

        // draw current Tetromino
        if(currentMino != null) {         // check if null to avoid NullPointerException errors
            currentMino.draw(g2);

            //CENTRE TEST
            //g2.setColor(Color.RED);
            //g2.fillRect(MINO_INIT_X, MINO_INIT_Y, 10, 10);;
        }

        // draw next Tetromino
        nextMino.draw(g2);

        // draw static blocks  - scan the static blocks and draw them one by one
        for(int i = 0; i < staticBlocks.size(); i++){
            staticBlocks.get(i).draw(g2);
        }

        // "press P to pause"
        g2.setColor(Color.yellow);
        g2.setFont(new Font("Arial", Font.BOLD, 15));
        x = right_x + 140;
        y = top_y + 220;
        g2.drawString("Press P to pause!", x, y);

        // Pause button [P]
        g2.setColor(Color.yellow);
        g2.setFont(new Font("Arial", Font.BOLD, 50));

        // Game over Text
        if(gameOver){
            x = left_x + 30;
            y = top_y + 320;
            g2.drawString("GAME OVER", x, y);
        }
        if(KeyHandler.pausePressed){
            x = left_x + 80;
            y = top_y + 320;
            g2.drawString("PAUSED", x, y);
        }

    }
}