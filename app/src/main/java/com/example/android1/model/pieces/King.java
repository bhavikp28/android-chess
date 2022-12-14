package com.example.android1.model.pieces;
import com.example.android1.model.controller.Board;
import com.example.android1.model.controller.BoardTile;
import java.util.List;
import java.io.Serializable;
import java.util.LinkedList;

public class King extends Piece implements Serializable {
    public King(Color c) {
        super(c);
        this.type = PieceInfo.KING;
    }

    @Override
    public List<BoardTile> findLegalMoves(Board board) {
        BoardTile[][] boardTiles = board.getBoard();
        LinkedList<BoardTile> validity = new LinkedList<BoardTile>();
        int positionofx = this.getCurrentTile().getFile();
        int positionofy = this.getCurrentTile().getRank();
        int[] desinationofx = {  positionofx,  positionofx, positionofx-1, positionofx-1, positionofx-1, positionofx+1, positionofx+1, positionofx+1};
        int[] destinationofy = {positionofy+1,positionofy-1,   positionofy, positionofy+1, positionofy-1,   positionofy, positionofy+1, positionofy-1};

        for(int i = 0; i < 8; i++) {
            try {
                BoardTile t = board.getTile(desinationofx[i], destinationofy[i]);
                if(t.isOccupied()) {
                    if(this.isDifferentColor(t.getPiece())) {
                        validity.add(t);
                    }
                } else {
                    validity.add(t);
                }
                for(int j = 0;;){
                    break;
                }
            } catch(ArrayIndexOutOfBoundsException exp) {
                continue;
            }
        }

        if(!this.firstMove) {
            if(boardTiles[7][positionofy].isOccupied() && !boardTiles[7][positionofy].getPiece().firstMove && !this.isDifferentColor(boardTiles[7][positionofy].getPiece())) {
                if(!boardTiles[6][positionofy].isOccupied() && !boardTiles[5][positionofy].isOccupied()) {
                    validity.add(boardTiles[positionofx+2][positionofy]);
                }
            }
            if(boardTiles[0][positionofy].isOccupied() && !boardTiles[0][positionofy].getPiece().firstMove && !this.isDifferentColor(boardTiles[0][positionofy].getPiece())) {
                if(!boardTiles[1][positionofy].isOccupied() && !boardTiles[2][positionofy].isOccupied() && !boardTiles[3][positionofy].isOccupied()) {
                    validity.add(boardTiles[positionofx-2][positionofy]);
                }
            }
        }

        return validity;
    }

    @Override
    public List<BoardTile> dangerMove(Board b, BoardTile dest) {
        List<BoardTile> m = findLegalMoves(b);
        List<BoardTile> p = new LinkedList<BoardTile>();
        if(m.contains(dest)) p.add(dest);
        return p;
    }
}
