package tetris.model.pieces;

import tetris.model.Tetromino;
import java.awt.*;

public class IPiece extends Tetromino {
    public IPiece() {
        color = new Color(0, 255, 255);
        shape = new int[][]{
            {1,1,1,1}
        };
    }
}