package tetris.model;

import java.awt.*;

public abstract class Tetromino {

    protected int[][] shape;
    protected Color color;
    protected int x;
    protected int y;

    public Tetromino() { }

    public void spawn(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int[][] getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int nx) { this.x = nx; }
    public void setY(int ny) { this.y = ny; }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    // Rotation helpers
    public int[][] peekRotate() {
        int rows = shape.length;
        int cols = shape[0].length;
        int[][] rotated = new int[cols][rows];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                rotated[c][rows - 1 - r] = shape[r][c];
            }
        }
        return rotated;
    }

    public void applyRotate() {
        this.shape = peekRotate();
    }
}