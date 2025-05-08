package tetromino;


import main.KeyHandler;
import main.gameplayManage;

import java.awt.*;
import java.util.stream.IntStream;

// super class for all tetrominoes  (inheritance DOC)
public class tetromino {

    //block arrays
    public block[] b = new block[4];
    public block[] tempB = new block[4];

    //tetromino drop
    int autoDropCounter = 0;

    // tetromino rotation directions
    public int direction = 1;   // directions - [1 , 2 , 3, 4]

    // collisions
    boolean collisionLeft, collisionRight, collisionBottom;
    public boolean active = true;


    //create method - instantiate arrays
    public void create(Color c) {
        b[0] = new block(c);
        b[1] = new block(c);
        b[2] = new block(c);
        b[3] = new block(c);
        tempB[0] = new block(c);
        tempB[1] = new block(c);
        tempB[2] = new block(c);
        tempB[3] = new block(c);

    }
    public void setXY(int x, int y){}
    public void updateXY(int direction){

        RotationCollisionValid();

        if(!collisionLeft && !collisionRight && !collisionBottom){
            this.direction = direction;

            // if collision occurs when the tetromino is rotating, the rotation must be canceled
            // original position is stored in tempB array instead of updating X and Y
            // original position can then be restored

            b[0].x = tempB[0].x;
            b[0].y = tempB[0].y;
            b[1].x = tempB[1].x;
            b[1].y = tempB[1].y;
            b[2].x = tempB[2].x;
            b[2].y = tempB[2].y;
            b[3].x = tempB[3].x;
            b[3].y = tempB[3].y;

        }

    }

    // to override in each individual tetromino class

    public void getDirection1(){}
    public void getDirection2(){}
    public void getDirection3(){}
    public void getDirection4(){}

    // check for collisions

    public void MovementCollisionValid() {

        // reset booleans
        collisionLeft = false;
        collisionRight = false;
        collisionBottom = false;

        // check game area boundary collisions
        // left border

        // scan block array and check x value
        // if x value is equal to the game border's left x , tetromino is touching the left wall
        if (IntStream.range(0, b.length).anyMatch(i -> b[i].x == gameplayManage.left_x)) {
            collisionLeft = true;
        }

        if (IntStream.range(0, b.length).anyMatch(i -> b[i].x + block.SIZE == gameplayManage.right_x)) {
            collisionRight = true;
        }
        if (IntStream.range(0, b.length).anyMatch(i -> b[i].y + block.SIZE == gameplayManage.bottom_y)) {
            collisionBottom = true;
        }
    }
    public void RotationCollisionValid() {

        if (IntStream.range(0, b.length).anyMatch(i -> tempB[i].x < gameplayManage.left_x)) {
            collisionLeft = true;
        }

        if (IntStream.range(0, b.length).anyMatch(i -> tempB[i].x + block.SIZE > gameplayManage.right_x)) {
            collisionRight = true;
        }
        if (IntStream.range(0, b.length).anyMatch(i -> tempB[i].y + block.SIZE > gameplayManage.bottom_y)) {
            collisionBottom = true;
        }
    }

    public void update(){

        // check for collisions

        MovementCollisionValid();

        //  Movement

        if(KeyHandler.downPressed){
            // if tetromino is not colliding at the bottom, move down
            if(!collisionBottom){
                b[0].y += block.SIZE;
                b[1].y += block.SIZE;
                b[2].y += block.SIZE;
                b[3].y += block.SIZE;

                // when moved down, reset autoDropCounter
                autoDropCounter = 0;
            }

            KeyHandler.downPressed = false;
        }

        if(KeyHandler.leftPressed){
            if(!collisionLeft){
                b[0].x -= block.SIZE;       // when left is pressed, subtract x from block size
                b[1].x -= block.SIZE;
                b[2].x -= block.SIZE;
                b[3].x -= block.SIZE;
            }
            KeyHandler.leftPressed = false;

        }

        if(KeyHandler.rightPressed){
            if(!collisionRight){
                b[0].x += block.SIZE;        // when right is pressed, add block size to x
                b[1].x += block.SIZE;
                b[2].x += block.SIZE;
                b[3].x += block.SIZE;
            }
            KeyHandler.rightPressed = false;

        }

        if(KeyHandler.upPressed){        // tetromino rotation

            switch(direction){
                case 1: getDirection2();break;
                case 2: getDirection3();break;
                case 3: getDirection4();break;
                case 4: getDirection1();break;
            }
            KeyHandler.upPressed = false;
        }

        if(collisionBottom){
            active = false;
        }
        else{

            autoDropCounter++;     // counter increases in every frame
            if (autoDropCounter == gameplayManage.dropInterval){

                // tetromino goes down
                b[0].y += block.SIZE;
                b[1].y += block.SIZE;
                b[2].y += block.SIZE;
                b[3].y += block.SIZE;
                autoDropCounter = 0; // reset drop counter
            }
        }

    }
    public void draw(Graphics2D g2){

        g2.setColor(b[0].c);
        g2.fillRect(b[0].x, b[0].y, block.SIZE, block.SIZE);
        g2.fillRect(b[1].x, b[1].y, block.SIZE, block.SIZE);
        g2.fillRect(b[2].x, b[2].y, block.SIZE, block.SIZE);
        g2.fillRect(b[3].x, b[3].y, block.SIZE, block.SIZE);

    }
}
