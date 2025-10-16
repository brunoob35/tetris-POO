package tetris.model.pieces;

import tetris.model.Tetromino;
import java.awt.*;

public class TPiece extends Tetromino {
    public TPiece() {
        color = new Color(255, 0, 255);
        shape = new int[][]{
            {0,1,0},
            {1,1,1}
        };
    }
}