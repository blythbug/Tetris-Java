package tetromino;

import main.GameplayManager;
import main.KeyHandler;

import java.awt.*;
import java.util.Arrays;
import java.util.logging.Logger;

// super class for all tetrominoes
// handles movement, rotation, collision detection, rotation

public abstract class Tetromino {
    // logger for debugging
    private static final Logger LOGGER = Logger.getLogger(Tetromino.class.getName());
    // controls debug output and features
    private static final boolean DEBUG_MODE = true;

    // Block arrays with final size
    public final Block[] b = new Block[4];
    // temporary array for rotation calculations (so we are able to return to original X Y coords)
    protected final Block[] tempB = new Block[4];

    // Constants
    // max time a piece can 'sleep' before becoming static
    protected static final int MAX_SLEEP_TIME = 45;
    protected static final int DIRECTIONS = 4;            // total number of possible rotations
    
    // Tetromino state
    protected int autoDropCounter = 0;
    protected int direction = 1;          // current rotation
    public boolean active = true;         // if a tetromino is active or static
    public boolean sleep;                 // sleep buffer for sliding feature
    protected int sleepCounter = 0;

    // Collision states
    protected boolean collisionLeft, collisionRight, collisionBottom;

    // Debug counters
    private int rotationCount = 0;
    private int moveCount = 0;

    // create a new tetromino
    protected void create(Color c) {
        for (int i = 0; i < 4; i++) {
            b[i] = new Block(c);
            tempB[i] = new Block(c);
        }
        if (DEBUG_MODE) {
            LOGGER.info("Created new Tetromino with color: " + c);
        }
    }

    // Abstract methods that must be overridden by subclasses
    public abstract void setXY(int x, int y);
    protected abstract void getDirection1();
    protected abstract void getDirection2();
    protected abstract void getDirection3();
    protected abstract void getDirection4();


    // updates piece position after rotation attempt
    protected void updateXY(int newDirection) {
        if (DEBUG_MODE) {
            rotationCount++;
            LOGGER.info(String.format("Rotation attempt #%d: %d -> %d", 
                rotationCount, direction, newDirection));
        }

        checkRotationCollision();

        if (!hasCollisions()) {
            direction = newDirection;
            copyTempToMain();
        }
    }

    // rotation collisions
    protected void checkRotationCollision() {
        resetCollisionFlags();
        checkStaticBlockCollision();
        checkBoundaryCollisions();
    }

    // movement collisions
    protected void checkMovementCollision() {
        resetCollisionFlags();
        checkStaticBlockCollision();
        checkBoundaryCollisions();
    }

    // reset collision flags
    private void resetCollisionFlags() {
        collisionLeft = false;
        collisionRight = false;
        collisionBottom = false;
    }

    // returns true if collision is detected
    private boolean hasCollisions() {
        return collisionLeft || collisionRight || collisionBottom;
    }

    // copies temporary block positions to main blocks after successful rotation
    private void copyTempToMain() {
        for (int i = 0; i < 4; i++) {
            b[i].x = tempB[i].x;
            b[i].y = tempB[i].y;
        }
    }


    // game boundary collisions
    protected void checkBoundaryCollisions() {
        for (Block block : b) {
            if (block.x == GameplayManager.left_x) collisionLeft = true;
            if (block.x + Block.SIZE == GameplayManager.right_x) collisionRight = true;
            if (block.y + Block.SIZE == GameplayManager.bottom_y) collisionBottom = true;
        }
    }

    // static block collisions
    protected void checkStaticBlockCollision() {
        GameplayManager.staticBlocks.forEach(staticBlock -> {
            for (Block block : b) {
                checkBlockCollisions(block, staticBlock);
            }
        });
    }

    // collisions between current tetromino and static blocks
    private void checkBlockCollisions(Block block, Block staticBlock) {
        if (block.y + Block.SIZE == staticBlock.y && block.x == staticBlock.x) {
            collisionBottom = true;
        }
        if (block.x + Block.SIZE == staticBlock.x && block.y == staticBlock.y) {
            collisionRight = true;
        }
        if (block.x - Block.SIZE == staticBlock.x && block.y == staticBlock.y) {
            collisionLeft = true;
        }
    }

    public void update() {
        if (sleep) {
            handleSleep();
            return;
        }

        checkMovementCollision();
        handleMovement();
        handleAutoDrop();
    }

    private void handleMovement() {
        if (KeyHandler.downPressed && !collisionBottom) {
            moveDown();
        }
        if (KeyHandler.leftPressed && !collisionLeft) {
            moveLeft();
        }
        if (KeyHandler.rightPressed && !collisionRight) {
            moveRight();
        }
        if (KeyHandler.upPressed) {
            rotate();
        }
    }

    private void handleSleep() {
        sleepCounter++;
        if (sleepCounter >= MAX_SLEEP_TIME) {
            sleepCounter = 0;
            checkMovementCollision();
            if (collisionBottom) {
                active = false;
                if (DEBUG_MODE) {
                    LOGGER.info("Tetromino deactivated after sleep");
                }
            }
        }
    }

    protected void moveDown() {
        moveCount++;
        Arrays.stream(b).forEach(block -> block.y += Block.SIZE);
        autoDropCounter = 0;
        KeyHandler.downPressed = false;
        if (DEBUG_MODE) {
            LOGGER.info("Move down #" + moveCount);
        }
    }

    protected void moveLeft() {
        moveCount++;
        Arrays.stream(b).forEach(block -> block.x -= Block.SIZE);
        KeyHandler.leftPressed = false;
        if (DEBUG_MODE) {
            LOGGER.info("Move left #" + moveCount);
        }
    }

    protected void moveRight() {
        moveCount++;
        Arrays.stream(b).forEach(block -> block.x += Block.SIZE);
        KeyHandler.rightPressed = false;
        if (DEBUG_MODE) {
            LOGGER.info("Move right #" + moveCount);
        }
    }

    protected void rotate() {
        switch(direction) {
            case 1 -> getDirection2();
            case 2 -> getDirection3();
            case 3 -> getDirection4();
            case 4 -> getDirection1();
        }
        KeyHandler.upPressed = false;
    }

    private void handleAutoDrop() {
        if (collisionBottom) {
            sleep = true;
            return;
        }

        autoDropCounter++;
        if (autoDropCounter >= GameplayManager.dropInterval) {
            moveDown();
        }
    }

    // draw tetrominoes
    public void draw(Graphics2D g2) {
        g2.setColor(b[0].c);
        Arrays.stream(b).forEach(block -> 
            g2.fillRect(block.x, block.y, Block.SIZE, Block.SIZE));
        
        if (DEBUG_MODE) {
            drawDebugInfo(g2);
        }
    }
    private void drawDebugInfo(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.drawString("Dir: " + direction, b[0].x, b[0].y - 5);
        g2.drawString("Active: " + active, b[0].x, b[0].y - 20);
    }

    // Debug methods
    public String getDebugInfo() {
        return String.format("""
            Tetromino Debug Info:
            Position: (%d, %d)
            Direction: %d
            Active: %b
            Sleep: %b
            Moves: %d
            Rotations: %d
            Collisions: L:%b R:%b B:%b
            """,
            b[0].x, b[0].y, direction, active, sleep,
            moveCount, rotationCount,
            collisionLeft, collisionRight, collisionBottom);
    }

    public void resetDebugCounters() {
        moveCount = 0;
        rotationCount = 0;
    }
}