package tetris.model;

import java.awt.*;

public class Board {

    private final int width;
    private final int height;
    private final int[][] grid; // 0 = empty; >0 = color index (or ARGB)

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new int[height][width];
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public void clearAll() {
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                grid[r][c] = 0;
            }
        }
    }

    public boolean isValidMove(int[][] shape, int x, int y) {
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[r].length; c++) {
                if (shape[r][c] != 0) {
                    int bx = x + c;
                    int by = y + r;
                    if (bx < 0 || bx >= width || by < 0 || by >= height) return false;
                    if (grid[by][bx] != 0) return false;
                }
            }
        }
        return true;
    }

    public void merge(Tetromino t) {
        int[][] s = t.getShape();
        for (int r = 0; r < s.length; r++) {
            for (int c = 0; c < s[r].length; c++) {
                if (s[r][c] != 0) {
                    int bx = t.getX() + c;
                    int by = t.getY() + r;
                    if (by >= 0 && by < height && bx >= 0 && bx < width) {
                        grid[by][bx] = t.getColor().getRGB();
                    }
                }
            }
        }
    }

    public int clearLines() {
        int cleared = 0;
        for (int r = height - 1; r >= 0; r--) {
            if (isLineComplete(r)) {
                removeLine(r);
                cleared++;
                r++; // re-check same row after collapse
            }
        }
        return cleared;
    }

    private boolean isLineComplete(int row) {
        for (int c = 0; c < width; c++) {
            if (grid[row][c] == 0) return false;
        }
        return true;
    }

    private void removeLine(int row) {
        for (int r = row; r > 0; r--) {
            System.arraycopy(grid[r - 1], 0, grid[r], 0, width);
        }
        for (int c = 0; c < width; c++) grid[0][c] = 0;
    }

    public int getCell(int r, int c) {
        return grid[r][c];
    }
}