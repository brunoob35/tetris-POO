package tetris.model.pieces;

import tetris.model.Tetromino;
import java.awt.*;

public class ZPiece extends Tetromino {
    public ZPiece() {
        color = new Color(255, 0, 0);
        shape = new int[][]{
            {1,1,0},
            {0,1,1}
        };
    }
}