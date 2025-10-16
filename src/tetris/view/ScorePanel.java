package tetris.view;

import tetris.model.Tetromino;

import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

public class ScorePanel extends JPanel {

    private final Supplier<Integer> score;
    private final Supplier<Integer> level;
    private final Supplier<Integer> lines;
    private final Supplier<Tetromino> nextSupplier;
    private final int cellSize;

    public ScorePanel(Supplier<Integer> score, Supplier<Integer> level,
                      Supplier<Integer> lines, Supplier<Tetromino> nextSupplier, int cellSize) {
        this.score = score;
        this.level = level;
        this.lines = lines;
        this.nextSupplier = nextSupplier;
        this.cellSize = cellSize;
        setPreferredSize(new Dimension(8 * cellSize, 20 * cellSize));
        setBackground(new Color(20, 20, 20));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.setFont(getFont().deriveFont(Font.BOLD, 14f));
        int y = 30;
        g.drawString("Pontuação: " + score.get(), 10, y); y += 24;
        g.drawString("Nível: " + level.get(), 10, y); y += 24;
        g.drawString("Linhas: " + lines.get(), 10, y); y += 30;

        g.drawString("Próxima:", 10, y); y += 10;

        // draw next piece preview centered
        Tetromino next = nextSupplier.get();
        int[][] s = next.getShape();
        int pw = s[0].length * cellSize / 2;
        int ph = s.length * cellSize / 2;
        int px = 10;
        int py = y + 10;

        g.setColor(next.getColor());
        for (int r = 0; r < s.length; r++) {
            for (int c = 0; c < s[r].length; c++) {
                if (s[r][c] != 0) {
                    int x = px + c * (cellSize / 2);
                    int yy = py + r * (cellSize / 2);
                    g.fillRect(x, yy, cellSize / 2, cellSize / 2);
                    g.setColor(Color.DARK_GRAY);
                    g.drawRect(x, yy, cellSize / 2, cellSize / 2);
                    g.setColor(next.getColor());
                }
            }
        }

        // controls help
        y = getHeight() - 120;
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("Controles:", 10, y); y += 20;
        g.drawString("← → mover", 10, y); y += 18;
        g.drawString("↑ rotacionar", 10, y); y += 18;
        g.drawString("↓ cair suave", 10, y); y += 18;
        g.drawString("Espaço: queda rápida", 10, y); y += 18;
        g.drawString("P: Pausa | R: Reiniciar", 10, y);
    }
}