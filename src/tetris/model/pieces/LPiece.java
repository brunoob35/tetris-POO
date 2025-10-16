package tetris.model.pieces;

import tetris.model.Tetromino;
import java.awt.*;

public class LPiece extends Tetromino {
    public LPiece() {
        color = new Color(255, 165, 0);
        shape = new int[][]{
            {0,0,1},
            {1,1,1}
        };
    }
}