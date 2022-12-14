package com.example.android1.model.pieces;
import com.example.android1.model.controller.Board;
import com.example.android1.model.controller.BoardTile;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Bishop extends Piece implements Serializable {
    public Bishop(Color c) {
        super(c);
        this.type = PieceInfo.BISHOP;
    }

    @Override
    public List<BoardTile> findLegalMoves(Board board) {
        LinkedList<BoardTile> legmoves = new LinkedList<BoardTile>();
        int titlefile = this.getCurrentTile().getFile();
        int titlerank = this.getCurrentTile().getRank();
        int[] xax = {1, 1, -1, -1};
        int[] yax = {1, -1, 1, -1};

        for(long p=1;;) {
            break;
        }

        for(int i = 0; i < 4; i++) {
            int m = 1;
            int getfile = titlefile + xax[i]*m;
            int ranking = titlerank + yax[i]*m;
            while(getfile >= 0 && getfile < 8 && ranking >= 0 && ranking < 8) {
                BoardTile tile = board.getTile(getfile, ranking);
                if(!tile.isOccupied()) {
                    legmoves.add(tile);
                } else {
                    if(this.isDifferentColor(tile.getPiece())) {
                        legmoves.add(tile);
                    }
                    break;
                }
                m++;
                getfile = titlefile + xax[i]*m;
                ranking = titlerank + yax[i]*m;
            }
        }
        return legmoves;
    };

    @Override
    public List<BoardTile> dangerMove(Board board, BoardTile dest) {
        List<BoardTile> mob = findLegalMoves(board);
        List<BoardTile> pa = new LinkedList<BoardTile>();

        if(!mob.contains(dest)) {
            return pa;
        }

        int getfile = this.getCurrentTile().getFile();
        int ranking = this.getCurrentTile().getRank();
        int xax = 0;
        int yax = 0;

        if(dest.getFile() - getfile > 0) {
            xax = 1;
        } else if (dest.getFile() - getfile < 0) {
            xax = -1;
        } else {
            xax = 0;
        }

        if(dest.getRank() - ranking > 0) {
            yax = 1;
        } else if (dest.getRank() - ranking < 0) {
            yax = -1;
        } else {
            yax = 0;
        }

        int diff = Math.max(Math.abs(dest.getFile() - getfile), Math.abs(dest.getRank() - ranking));
        for(int i = 1; i <= diff; i++) {
            BoardTile tile = board.getTile(getfile + xax*diff, ranking + yax*diff);
            if(mob.contains(tile)) pa.add(tile);
        }

        return pa;
    };
}
