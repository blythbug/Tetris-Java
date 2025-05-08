package tetromino;

import java.awt.*;

// each tetromino is made of 4 blocks
public class block extends Rectangle {

    public int x, y;
    public static final int SIZE = 30;    // 30 x 30 block
    public Color c;

    public block(Color c){          // each tetromino will have a different colour

        this.c = c;

    }
    public void draw(Graphics2D g2){       // draw block and fill it with colour
        g2.setColor(c);
        g2.fillRect(x, y, SIZE, SIZE);
    }
}
