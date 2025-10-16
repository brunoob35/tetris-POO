package tetris.controller;

import tetris.view.GamePanel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputHandler extends KeyAdapter {

    private final GamePanel panel;
    private final GameEngine engine;

    public InputHandler(GamePanel panel, GameEngine engine) {
        this.panel = panel;
        this.engine = engine;
        panel.addKeyListener(this);
        panel.setFocusable(true);
        panel.requestFocusInWindow();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> engine.moveLeft();
            case KeyEvent.VK_RIGHT -> engine.moveRight();
            case KeyEvent.VK_DOWN -> engine.softDrop();
            case KeyEvent.VK_UP -> engine.rotate();
            case KeyEvent.VK_SPACE -> engine.hardDrop();
            case KeyEvent.VK_P -> engine.togglePause();
            case KeyEvent.VK_R -> engine.restart();
        }
    }
}