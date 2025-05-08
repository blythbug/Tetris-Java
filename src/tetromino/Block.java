package tetromino;

import java.awt.*;

// each Tetromino is made of 4 blocks
public class Block extends Rectangle {

    public int x, y;
    public static final int SIZE = 30;    // 30 x 30 Block
    public Color c;

    public Block(Color c){          // each Tetromino will have a different colour

        this.c = c;

    }
    public void draw(Graphics2D g2){       // draw Block and fill it with colour
        g2.setColor(c);
        g2.fillRect(x, y, SIZE, SIZE);
    }
}
