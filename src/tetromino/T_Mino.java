package tetromino;

import java.awt.*;

public class T_Mino extends Tetromino {

    public T_Mino(){
        create(Color.RED);     //call create method from super class and pass a colour
    }
    public void setXY(int x, int y){

        // SHAPE CONSTRUCTOR
        //     X      <- b[1]
        //  X  X  X   <- b[2], [b0] , [b3]

        b[0].x = x;     // can auto update x and y of the other 3 blocks
        b[0].y = y;

        b[1].x = b[0].x;
        b[1].y = b[0].y - Block.SIZE;

        b[2].x = b[0].x - Block.SIZE;
        b[2].y = b[0].y;

        b[3].x = b[0].x + Block.SIZE;
        b[3].y = b[0].y;

    }

    public void getDirection1(){

        //    X
        // X  X  X

        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;

        tempB[1].x = b[0].x;
        tempB[1].y = b[0].y - Block.SIZE;

        tempB[2].x = b[0].x - Block.SIZE;
        tempB[2].y = b[0].y;

        tempB[3].x = b[0].x + Block.SIZE;
        tempB[3].y = b[0].y;

        updateXY(1);
    }
    public void getDirection2(){

        //  X
        //  X X
        //  X

        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;

        tempB[1].x = b[0].x + Block.SIZE;
        tempB[1].y = b[0].y;

        tempB[2].x = b[0].x;
        tempB[2].y = b[0].y - Block.SIZE;

        tempB[3].x = b[0].x;
        tempB[3].y = b[0].y + Block.SIZE;

        updateXY(2);
    }
    public void getDirection3(){

        // X X X
        //   X

        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;

        tempB[1].x = b[0].x;
        tempB[1].y = b[0].y + Block.SIZE;

        tempB[2].x = b[0].x + Block.SIZE;
        tempB[2].y = b[0].y;

        tempB[3].x = b[0].x - Block.SIZE;
        tempB[3].y = b[0].y;

        updateXY(3);
    }
    public void getDirection4(){

        //   X
        // X X
        //   X

        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;

        tempB[1].x = b[0].x - Block.SIZE;
        tempB[1].y = b[0].y;

        tempB[2].x = b[0].x;
        tempB[2].y = b[0].y  + Block.SIZE;

        tempB[3].x = b[0].x;
        tempB[3].y = b[0].y - Block.SIZE;

        updateXY(4);
    }

}
