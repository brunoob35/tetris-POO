package tetris.controller;

import tetris.model.Board;
import tetris.model.Tetromino;
import tetris.model.TetrominoFactory;
import tetris.view.GamePanel;
import tetris.view.ScorePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameEngine {

    private JFrame frame;
    private GamePanel gamePanel;
    private ScorePanel scorePanel;
    private Board board;

    private Tetromino currentPiece;
    private Tetromino nextPiece;

    private Timer timer;
    private boolean running = false;
    private boolean paused = false;

    private int score = 0;
    private int lines = 0;
    private int level = 1;

    public static final int CELL = 30; // pixel size

    public void start() {
        board = new Board(10, 20);
        currentPiece = TetrominoFactory.randomPiece();
        nextPiece = TetrominoFactory.randomPiece();

        frame = new JFrame("Tetris - v1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        gamePanel = new GamePanel(board, () -> currentPiece, CELL);
        scorePanel = new ScorePanel(() -> score, () -> level, () -> lines, () -> nextPiece, CELL);

        frame.add(gamePanel, BorderLayout.CENTER);
        frame.add(scorePanel, BorderLayout.EAST);

        new InputHandler(gamePanel, this);

        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        int delay = levelToDelay(level);
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!running || paused) return;
                tick();
            }
        });

        running = true;
        timer.start();
    }

    private int levelToDelay(int lvl) {
        if (lvl >= 7) return 400;
        if (lvl >= 4) return 700;
        return 1000;
    }

    public void togglePause() {
        paused = !paused;
        gamePanel.setPaused(paused);
        gamePanel.repaint();
    }

    public void restart() {
        score = 0;
        lines = 0;
        level = 1;
        board.clearAll();
        currentPiece = TetrominoFactory.randomPiece();
        nextPiece = TetrominoFactory.randomPiece();
        paused = false;
        gamePanel.setPaused(false);
        timer.setDelay(levelToDelay(level));
        gamePanel.repaint();
        scorePanel.repaint();
    }

    public void softDrop() {
        if (!running || paused) return;
        moveDown();
    }

    public void hardDrop() {
        if (!running || paused) return;
        while (board.isValidMove(currentPiece.getShape(), currentPiece.getX(), currentPiece.getY() + 1)) {
            currentPiece.move(0, 1);
        }
        lockPiece();
    }

    public void moveLeft() {
        if (!running || paused) return;
        if (board.isValidMove(currentPiece.getShape(), currentPiece.getX() - 1, currentPiece.getY())) {
            currentPiece.move(-1, 0);
            gamePanel.repaint();
        }
    }

    public void moveRight() {
        if (!running || paused) return;
        if (board.isValidMove(currentPiece.getShape(), currentPiece.getX() + 1, currentPiece.getY())) {
            currentPiece.move(1, 0);
            gamePanel.repaint();
        }
    }

    public void rotate() {
        if (!running || paused) return;
        int[][] rotated = currentPiece.peekRotate();
        int x = currentPiece.getX();
        int y = currentPiece.getY();
        // Simple wall-kick attempts
        int[] kicks = new int[]{0, -1, 1, -2, 2};
        for (int dx : kicks) {
            if (board.isValidMove(rotated, x + dx, y)) {
                currentPiece.applyRotate();
                currentPiece.setX(x + dx);
                gamePanel.repaint();
                return;
            }
        }
        // if none valid, ignore rotation
    }

    private void tick() {
        moveDown();
    }

    private void moveDown() {
        if (board.isValidMove(currentPiece.getShape(), currentPiece.getX(), currentPiece.getY() + 1)) {
            currentPiece.move(0, 1);
        } else {
            lockPiece();
        }
        gamePanel.repaint();
    }

    private void lockPiece() {
        board.merge(currentPiece);
        int cleared = board.clearLines();
        if (cleared > 0) {
            lines += cleared;
            score += switch (cleared) {
                case 1 -> 40;
                case 2 -> 100;
                case 3 -> 300;
                case 4 -> 1200;
                default -> 0;
            } * level;
            level = Math.max(1, 1 + lines / 10);
            timer.setDelay(levelToDelay(level));
        }

        // spawn next
        currentPiece = nextPiece;
        nextPiece = TetrominoFactory.randomPiece();
        currentPiece.spawn(board.getWidth() / 2 - 2, 0);

        // game over check: if spawn position invalid
        if (!board.isValidMove(currentPiece.getShape(), currentPiece.getX(), currentPiece.getY())) {
            running = false;
            paused = false;
            gamePanel.setGameOver(true);
            gamePanel.repaint();
            timer.stop();
        }
        scorePanel.repaint();
    }

    public Board getBoard() {
        return board;
    }

    public Tetromino getCurrentPiece() {
        return currentPiece;
    }
}