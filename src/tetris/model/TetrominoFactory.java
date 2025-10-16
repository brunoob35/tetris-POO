package tetris.model;

import tetris.model.pieces.*;

import java.util.Random;

public class TetrominoFactory {
    private static final Random RND = new Random();

    public static Tetromino randomPiece() {
        int i = RND.nextInt(7);
        Tetromino t = switch (i) {
            case 0 -> new IPiece();
            case 1 -> new OPiece();
            case 2 -> new TPiece();
            case 3 -> new SPiece();
            case 4 -> new ZPiece();
            case 5 -> new JPiece();
            default -> new LPiece();
        };
        // default spawn near top; actual x is adjusted by GameEngine
        t.spawn(3, 0);
        return t;
    }
}