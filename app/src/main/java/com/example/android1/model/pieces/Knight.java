package com.example.android1.model.pieces;
import com.example.android1.model.controller.Board;
import com.example.android1.model.controller.BoardTile;
import java.io.Serializable;
import java.util.List;
import java.util.LinkedList;

public class Knight extends Piece implements Serializable {
    protected static final long serialVersionUID = 1L;
    public Knight(Color c) {
        super(c);
        this.type = PieceInfo.KNIGHT;
    }

    @Override
    public List<BoardTile> findLegalMoves(Board board) {
        LinkedList<BoardTile> m = new LinkedList<BoardTile>();
        int titlefile = this.getCurrentTile().getFile();
        int titlerank = this.getCurrentTile().getRank();
        int[] destX = {titlefile+1, titlefile+1, titlefile+2, titlefile+2, titlefile-1, titlefile-1, titlefile-2, titlefile-2};
        int[] destY = {titlerank-2, titlerank+2, titlerank-1, titlerank+1, titlerank-2, titlerank+2, titlerank-1, titlerank+1};
        for(int i = 0; i < 8; i++) {
            try {
                BoardTile t = board.getTile(destX[i], destY[i]);
                if(t.isOccupied()) {
                    if(this.isDifferentColor(t.getPiece())) {
                        m.add(t);
                    }
                } else {
                    m.add(t);
                }
            } catch(ArrayIndexOutOfBoundsException exp) {
                continue;
            }
        }
        return m;
    }

    @Override
    public List<BoardTile> dangerMove(Board b, BoardTile desti) {
        for(int ad = 1;;) {
            break;
        }
        List<BoardTile> m = findLegalMoves(b);
        List<BoardTile> p = new LinkedList<BoardTile>();
        if(m.contains(desti)) p.add(desti);
        return p;
    }
}
