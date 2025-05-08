package tetromino;

import java.awt.*;

public class BarMino extends Tetromino {

    public BarMino(){
        create(Color.CYAN);     //call create method from super class and pass a colour
    }
    public void setXY(int x, int y){

        // SHAPE CONSTRUCTOR
        // X X X X

        b[0].x = x;     // can auto update x and y of the other 3 blocks
        b[0].y = y;

        b[1].x = b[0].x - Block.SIZE;
        b[1].y = b[0].y;

        b[2].x = b[0].x + Block.SIZE;
        b[2].y = b[0].y;

        b[3].x = b[0].x + Block.SIZE;
        b[3].y = b[0].y;

    }

    public void getDirection1(){

        //  X X X X


        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;

        tempB[1].x = b[0].x - Block.SIZE;
        tempB[1].y = b[0].y;

        tempB[2].x = b[0].x + Block.SIZE;
        tempB[2].y = b[0].y;

        tempB[3].x = b[0].x + Block.SIZE;
        tempB[3].y = b[0].y;

        updateXY(1);
    }
    public void getDirection2(){

        //  X
        //  X
        //  X
        //  X

        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;

        tempB[1].x = b[0].x;
        tempB[1].y = b[0].y - Block.SIZE;

        tempB[2].x = b[0].x;
        tempB[2].y = b[0].y + Block.SIZE;

        tempB[3].x = b[0].x;
        tempB[3].y = b[0].y + Block.SIZE*2;

        updateXY(2);
    }

    // The bar Tetromino only has 2 possible rotations, so direction 3 and 4 just call direction 1 and 2
    // respectively

    public void getDirection3(){
        getDirection1();
    }
    public void getDirection4(){
        getDirection2();
    }

}
