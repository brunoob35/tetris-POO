package tetris.model.pieces;

import tetris.model.Tetromino;
import java.awt.*;

public class SPiece extends Tetromino {
    public SPiece() {
        color = new Color(0, 255, 0);
        shape = new int[][]{
            {0,1,1},
            {1,1,0}
        };
    }
}