package tetris.model.pieces;

import tetris.model.Tetromino;
import java.awt.*;

public class OPiece extends Tetromino {
    public OPiece() {
        color = new Color(255, 255, 0);
        shape = new int[][]{
            {1,1},
            {1,1}
        };
    }

    @Override
    public int[][] peekRotate() {
        // O-piece rotation is a no-op
        return shape;
    }

    @Override
    public void applyRotate() { /* no-op */ }
}