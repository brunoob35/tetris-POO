package tetris.model.pieces;

import tetris.model.Tetromino;
import java.awt.*;

public class JPiece extends Tetromino {
    public JPiece() {
        color = new Color(0, 0, 255);
        shape = new int[][]{
            {1,0,0},
            {1,1,1}
        };
    }
}