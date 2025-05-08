package tetromino;


import main.GameplayManager;
import main.KeyHandler;

import java.awt.*;
import java.util.stream.IntStream;

// super class for all tetrominoes  (inheritance DOC)
public class Tetromino {

    //Block arrays
    public Block[] b = new Block[4];
    public Block[] tempB = new Block[4];

    //Tetromino drop
    int autoDropCounter = 0;

    // Tetromino rotation directions
    public int direction = 1;   // directions - [1 , 2 , 3, 4]

    // collisions
    boolean collisionLeft, collisionRight, collisionBottom;
    public boolean active = true;

    // Tetromino 'slide'
    public boolean sleep;
    int sleepCounter = 0;


    //create method - instantiate arrays
    public void create(Color c) {
        b[0] = new Block(c);
        b[1] = new Block(c);
        b[2] = new Block(c);
        b[3] = new Block(c);
        tempB[0] = new Block(c);
        tempB[1] = new Block(c);
        tempB[2] = new Block(c);
        tempB[3] = new Block(c);

    }
    public void setXY(int x, int y){}
    public void updateXY(int direction){

        RotationCollisionValid();

        if(!collisionLeft && !collisionRight && !collisionBottom){
            this.direction = direction;

            // if collision occurs when the Tetromino is rotating, the rotation must be canceled
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

    // to override in each individual Tetromino class

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

        // check if colliding with static blocks
        StaticBlockCollisionValid();

        // scan Block array and check x value
        // if x value is equal to the game border's left x , Tetromino is touching the left wall
        if (IntStream.range(0, b.length).anyMatch(i -> b[i].x == GameplayManager.left_x)) {
            collisionLeft = true;
        }

        if (IntStream.range(0, b.length).anyMatch(i -> b[i].x + Block.SIZE == GameplayManager.right_x)) {
            collisionRight = true;
        }
        if (IntStream.range(0, b.length).anyMatch(i -> b[i].y + Block.SIZE == GameplayManager.bottom_y)) {
            collisionBottom = true;
        }
    }
    public void RotationCollisionValid() {

        // reset booleans
        collisionLeft = false;
        collisionRight = false;
        collisionBottom = false;

        // check if colliding with static blocks
        StaticBlockCollisionValid();

        if (IntStream.range(0, b.length).anyMatch(i -> tempB[i].x < GameplayManager.left_x)) {
            collisionLeft = true;
        }

        if (IntStream.range(0, b.length).anyMatch(i -> tempB[i].x + Block.SIZE > GameplayManager.right_x)) {
            collisionRight = true;
        }
        if (IntStream.range(0, b.length).anyMatch(i -> tempB[i].y + Block.SIZE > GameplayManager.bottom_y)) {
            collisionBottom = true;
        }
    }
    private void StaticBlockCollisionValid(){

        // scan staticBlocks array
        for(int i = 0; i < GameplayManager.staticBlocks.size(); i++){

            // get each Block's x and y and check left, right and down collision
            int block_x = GameplayManager.staticBlocks.get(i).x;
            int block_y = GameplayManager.staticBlocks.get(i).y;


            // bottom collision
            for(int j = 0; j < b.length; j++){
                if(b[j].y + Block.SIZE == block_y && b[j].x == block_x){   // if Tetromino x/ y coord + Block size = static Block x/y coord
                    collisionBottom = true;                                // then static Block is right below a current Tetromino ; collision occurs
                }
            }
            // right collision
            for(int j = 0; j < b.length; j++){
                if(b[j].x + Block.SIZE == block_x && b[j].y == block_y){
                    collisionRight = true;
                }
            }
            // left collision
            for(int j = 0; j < b.length; j++){
                if(b[j].x - Block.SIZE == block_x && b[j].y == block_y){
                    collisionLeft = true;
                }
            }
        }
    }

    public void update(){

        // call 'sleep' method for Block sliding
        if(sleep){
            sleep();
        }

        // check for collisions

        MovementCollisionValid();

        //  Movement

        if(KeyHandler.downPressed){
            // if Tetromino is not colliding at the bottom, move down
            if(!collisionBottom){
                b[0].y += Block.SIZE;
                b[1].y += Block.SIZE;
                b[2].y += Block.SIZE;
                b[3].y += Block.SIZE;

                // when moved down, reset autoDropCounter
                autoDropCounter = 0;
            }

            KeyHandler.downPressed = false;
        }

        if(KeyHandler.leftPressed){
            if(!collisionLeft){
                b[0].x -= Block.SIZE;       // when left is pressed, subtract x from Block size
                b[1].x -= Block.SIZE;
                b[2].x -= Block.SIZE;
                b[3].x -= Block.SIZE;
            }
            KeyHandler.leftPressed = false;

        }

        if(KeyHandler.rightPressed){
            if(!collisionRight){
                b[0].x += Block.SIZE;        // when right is pressed, add Block size to x
                b[1].x += Block.SIZE;
                b[2].x += Block.SIZE;
                b[3].x += Block.SIZE;
            }
            KeyHandler.rightPressed = false;

        }

        if(KeyHandler.upPressed){        // Tetromino rotation

            switch(direction){
                case 1: getDirection2();break;
                case 2: getDirection3();break;
                case 3: getDirection4();break;
                case 4: getDirection1();break;
            }
            KeyHandler.upPressed = false;
        }

        if(collisionBottom){
            sleep = true;
        }
        else{

            autoDropCounter++;     // counter increases in every frame
            if (autoDropCounter == GameplayManager.dropInterval){

                // Tetromino goes down
                b[0].y += Block.SIZE;
                b[1].y += Block.SIZE;
                b[2].y += Block.SIZE;
                b[3].y += Block.SIZE;
                autoDropCounter = 0; // reset drop counter
            }
        }

    }

    // allow Tetromino a brief time before becoming static; allows slides
    private void sleep(){
        sleepCounter++;

        // wait 45 frames until deactivate
        if(sleepCounter == 45){

            sleepCounter = 0;
            MovementCollisionValid();  // check if there is bottom collision

            // if the bottom is still colliding after 45 frames, make Tetromino static
            if(collisionBottom){
                active = false;
            }
        }
    }
    public void draw(Graphics2D g2){

        g2.setColor(b[0].c);
        g2.fillRect(b[0].x, b[0].y, Block.SIZE, Block.SIZE);
        g2.fillRect(b[1].x, b[1].y, Block.SIZE, Block.SIZE);
        g2.fillRect(b[2].x, b[2].y, Block.SIZE, Block.SIZE);
        g2.fillRect(b[3].x, b[3].y, Block.SIZE, Block.SIZE);

    }
}
