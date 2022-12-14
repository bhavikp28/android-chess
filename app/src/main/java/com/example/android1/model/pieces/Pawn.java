package com.example.android1.model.pieces;
import com.example.android1.model.controller.Board;
import com.example.android1.model.controller.BoardTile;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Pawn extends Piece implements Serializable {
    public static final long serialVersionUID = 1L;
    public Pawn(Color c) {
        super(c);
        this.type = PieceInfo.PAWN;
    }

    @Override
    public List<BoardTile> findLegalMoves(Board bo) {
        BoardTile[][] b = bo.getBoard();
        List<BoardTile> moves = new LinkedList<BoardTile>();
        int titlefile = this.getCurrentTile().getFile();
        int titlerank = this.getCurrentTile().getRank();
        if(this.getColor() == Color.WHITE) {
            if(titlerank < 7) {
                if(!b[titlefile][titlerank+1].isOccupied()) {
                    moves.add(b[titlefile][titlerank+1]);
                    if(!this.firstMove && !b[titlefile][titlerank+2].isOccupied()) {
                        moves.add(b[titlefile][titlerank+2]);
                    }
                }
                if(titlefile > 0 && b[titlefile-1][titlerank+1].isOccupied() && this.isDifferentColor(b[titlefile-1][titlerank+1].getPiece())) {
                    moves.add(b[titlefile-1][titlerank+1]);
                }
                if(titlefile < 7 && b[titlefile+1][titlerank+1].isOccupied() && this.isDifferentColor(b[titlefile+1][titlerank+1].getPiece())) {
                    moves.add(b[titlefile+1][titlerank+1]);
                }
            }
            if(titlerank == 4) {
                if(titlefile > 0 && !b[titlefile-1][titlerank+1].isOccupied() && b[titlefile-1][titlerank].isOccupied()
                        && this.isDifferentColor(b[titlefile-1][titlerank].getPiece()) && b[titlefile-1][titlerank].getPiece().isEnPassante()) {
                    moves.add(b[titlefile-1][titlerank+1]);
                }
                if(titlefile < 7 && !b[titlefile+1][titlerank+1].isOccupied() && b[titlefile+1][titlerank].isOccupied()
                        && this.isDifferentColor(b[titlefile+1][titlerank].getPiece()) && b[titlefile+1][titlerank].getPiece().isEnPassante()) {
                    moves.add(b[titlefile+1][titlerank+1]);
                }
            }
        } else {
            if(titlerank > 0) {
                if(!b[titlefile][titlerank-1].isOccupied()) {
                    moves.add(b[titlefile][titlerank-1]);
                    if(!this.firstMove && !b[titlefile][titlerank-2].isOccupied()) {
                        moves.add(b[titlefile][titlerank-2]);
                    }
                }
                if(titlefile > 0 && b[titlefile-1][titlerank-1].isOccupied() && this.isDifferentColor(b[titlefile-1][titlerank-1].getPiece())) {
                    moves.add(b[titlefile-1][titlerank-1]);
                }
                if(titlefile < 7 && b[titlefile+1][titlerank-1].isOccupied() && this.isDifferentColor(b[titlefile+1][titlerank-1].getPiece())) {
                    moves.add(b[titlefile+1][titlerank-1]);
                }
            }
            if(titlerank == 3) {
                if(titlefile > 0 && !b[titlefile-1][titlerank-1].isOccupied() && b[titlefile-1][titlerank].isOccupied()
                        && this.isDifferentColor(b[titlefile-1][titlerank].getPiece()) && b[titlefile-1][titlerank].getPiece().isEnPassante()) {
                    moves.add(b[titlefile-1][titlerank-1]);
                }
                if(titlefile < 7 && !b[titlefile+1][titlerank-1].isOccupied() && b[titlefile+1][titlerank].isOccupied()
                        && this.isDifferentColor(b[titlefile+1][titlerank].getPiece()) && b[titlefile+1][titlerank].getPiece().isEnPassante()) {
                    moves.add(b[titlefile+1][titlerank-1]);
                }
            }
        }
        return moves;
    };

    @Override
    public List<BoardTile> dangerMove(Board bo, BoardTile dest) {
        List<BoardTile> moves = findLegalMoves(bo);
        List<BoardTile> path = new LinkedList<BoardTile>();
        if(moves.contains(dest)) path.add(dest);
        return path;
    };
}
