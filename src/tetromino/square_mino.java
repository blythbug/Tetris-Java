package tetromino;

import java.awt.*;

public class square_mino extends tetromino{

    public square_mino(){
        create(Color.WHITE);     //call create method from super class and pass a colour
    }
    public void setXY(int x, int y){

        // SHAPE CONSTRUCTOR
        //   X X
        //   X X

        b[0].x = x;     // can auto update x and y of the other 3 blocks
        b[0].y = y;

        b[1].x = b[0].x;
        b[1].y = b[0].y + block.SIZE;

        b[2].x = b[0].x + block.SIZE;
        b[2].y = b[0].y;

        b[3].x = b[0].x + block.SIZE;
        b[3].y = b[0].y + block.SIZE;

    }

    // This tetromino is a square, so there are no possible rotations

    public void getDirection1(){}
    public void getDirection2(){}
    public void getDirection3(){}
    public void getDirection4(){}

}
