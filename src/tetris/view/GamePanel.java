package tetris.view;

import tetris.controller.GameEngine;
import tetris.model.Board;
import tetris.model.Tetromino;

import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

public class GamePanel extends JPanel {

    private final Board board;
    private final Supplier<Tetromino> currentSupplier;
    private final int cellSize;
    private boolean paused = false;
    private boolean gameOver = false;

    public GamePanel(Board board, Supplier<Tetromino> currentSupplier, int cellSize) {
        this.board = board;
        this.currentSupplier = currentSupplier;
        this.cellSize = cellSize;
        setPreferredSize(new Dimension(board.getWidth() * cellSize, board.getHeight() * cellSize));
        setBackground(Color.BLACK);
    }

    public void setPaused(boolean p) { this.paused = p; }
    public void setGameOver(boolean go) { this.gameOver = go; }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        drawBoard(g);
        drawCurrentPiece(g);

        if (paused) drawOverlay(g, "PAUSADO (P para retomar)");
        if (gameOver) drawOverlay(g, "GAME OVER (R para reiniciar)");
    }

    private void drawGrid(Graphics g) {
        g.setColor(new Color(40, 40, 40));
        for (int r = 0; r <= board.getHeight(); r++) {
            g.drawLine(0, r * cellSize, board.getWidth() * cellSize, r * cellSize);
        }
        for (int c = 0; c <= board.getWidth(); c++) {
            g.drawLine(c * cellSize, 0, c * cellSize, board.getHeight() * cellSize);
        }
    }

    private void drawBoard(Graphics g) {
        for (int r = 0; r < board.getHeight(); r++) {
            for (int c = 0; c < board.getWidth(); c++) {
                int val = board.getCell(r, c);
                if (val != 0) {
                    g.setColor(new Color(val, true));
                    g.fillRect(c * cellSize, r * cellSize, cellSize, cellSize);
                    g.setColor(Color.DARK_GRAY);
                    g.drawRect(c * cellSize, r * cellSize, cellSize, cellSize);
                }
            }
        }
    }

    private void drawCurrentPiece(Graphics g) {
        Tetromino t = currentSupplier.get();
        int[][] s = t.getShape();
        g.setColor(t.getColor());
        for (int r = 0; r < s.length; r++) {
            for (int c = 0; c < s[r].length; c++) {
                if (s[r][c] != 0) {
                    int x = (t.getX() + c) * cellSize;
                    int y = (t.getY() + r) * cellSize;
                    g.fillRect(x, y, cellSize, cellSize);
                    g.setColor(Color.DARK_GRAY);
                    g.drawRect(x, y, cellSize, cellSize);
                    g.setColor(t.getColor());
                }
            }
        }
    }

    private void drawOverlay(Graphics g, String text) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setColor(Color.WHITE);
        g2.setFont(getFont().deriveFont(Font.BOLD, 20f));
        FontMetrics fm = g2.getFontMetrics();
        int tw = fm.stringWidth(text);
        g2.drawString(text, (getWidth() - tw) / 2, getHeight() / 2);
        g2.dispose();
    }
}