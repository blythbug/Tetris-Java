package tetromino;

import java.awt.*;

public class L1_mino extends tetromino{

    public L1_mino(){
        create(Color.BLUE);     //call create method from super class and pass a colour
    }
    public void setXY(int x, int y){

        // SHAPE CONSTRUCTOR
        //     X      <- b[1]
        //     X      <- b[0]   this block does not change position when rotating
        //     X X    <- b[2] , b[3]

        b[0].x = x;     // can auto update x and y of the other 3 blocks
        b[0].y = y;

        b[1].x = b[0].x;
        b[1].y = b[0].y - block.SIZE;

        b[2].x = b[0].x;
        b[2].y = b[0].y + block.SIZE;

        b[3].x = b[0].x + block.SIZE;
        b[3].y = b[0].y + block.SIZE;

    }

    public void getDirection1(){

        //  X
        //  X
        //  X X

        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;

        tempB[1].x = b[0].x;
        tempB[1].y = b[0].y - block.SIZE;

        tempB[2].x = b[0].x;
        tempB[2].y = b[0].y + block.SIZE;

        tempB[3].x = b[0].x + block.SIZE;
        tempB[3].y = b[0].y + block.SIZE;

        updateXY(1);
    }
    public void getDirection2(){

        //
        //  X X X
        //  X

        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;

        tempB[1].x = b[0].x + block.SIZE;
        tempB[1].y = b[0].y;

        tempB[2].x = b[0].x - block.SIZE;
        tempB[2].y = b[0].y;

        tempB[3].x = b[0].x - block.SIZE;
        tempB[3].y = b[0].y + block.SIZE;

        updateXY(2);
    }
    public void getDirection3(){

        // X X
        //   X
        //   X

        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;

        tempB[1].x = b[0].x;
        tempB[1].y = b[0].y + block.SIZE;

        tempB[2].x = b[0].x;
        tempB[2].y = b[0].y - block.SIZE;

        tempB[3].x = b[0].x - block.SIZE;
        tempB[3].y = b[0].y - block.SIZE;

        updateXY(3);
    }
    public void getDirection4(){

        //     X
        // X X X

        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;

        tempB[1].x = b[0].x - block.SIZE;
        tempB[1].y = b[0].y;

        tempB[2].x = b[0].x + block.SIZE;
        tempB[2].y = b[0].y;

        tempB[3].x = b[0].x + block.SIZE;
        tempB[3].y = b[0].y - block.SIZE;

        updateXY(4);
    }

}
