package com.example.android1.model.pieces;
import com.example.android1.model.controller.Board;
import com.example.android1.model.controller.BoardTile;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
public class Rook extends Piece implements Serializable {
    protected static final long serialVersionUID = 1L;
    public Rook(Color color) {
        super(color);
        this.type = PieceInfo.ROOK;
    }

    @Override
    public List<BoardTile> findLegalMoves(Board board) {
        LinkedList<BoardTile> moves = new LinkedList<BoardTile>();
        int x = this.getCurrentTile().getFile();
        int y = this.getCurrentTile().getRank();
        if (y < 7) {
            int j = y + 1;
            while (j < 8) {
                BoardTile tile = board.getTile(x, j);
                if (!tile.isOccupied()) {
                    moves.add(tile);
                    j++;
                } else {
                    if (this.isDifferentColor(tile.getPiece())) {
                        moves.add(tile);
                    }
                    break;
                }
            }
        }
        if (y > 0) {
            int j = y - 1;
            while (j >= 0) {
                BoardTile tile = board.getTile(x, j);
                if (!tile.isOccupied()) {
                    moves.add(tile);
                    j--;
                } else {
                    if (this.isDifferentColor(tile.getPiece())) {
                        moves.add(tile);
                    }
                    break;
                }
            }
        }
        if (x > 0) {
            int i = x - 1;
            while (i >= 0) {
                BoardTile tile = board.getTile(i, y);
                if (!tile.isOccupied()) {
                    moves.add(tile);
                    i--;
                } else {
                    if (this.isDifferentColor(tile.getPiece())) {
                        moves.add(tile);
                    }
                    break;
                }
            }
        }
        if (x < 7) {
            int i = x + 1;
            while (i < 8) {
                BoardTile tile = board.getTile(i, y);
                if (!tile.isOccupied()) {
                    moves.add(tile);
                    i++;
                } else {
                    if (this.isDifferentColor(tile.getPiece())) {
                        moves.add(tile);
                    }
                    break;
                }
            }
        }
        return moves;
    }

    @Override
    public List<BoardTile> dangerMove(Board board, BoardTile dest) {
        List<BoardTile> moves = findLegalMoves(board);
        List<BoardTile> path = new LinkedList<BoardTile>();
        if (!moves.contains(dest)) return path;
        int file = this.getCurrentTile().getFile();
        int rank = this.getCurrentTile().getRank();
        int xInc = 0;
        int yInc = 0;
        if (dest.getFile() - file > 0) {
            xInc = 1;
        } else if (dest.getFile() - file < 0) {
            xInc = -1;
        } else {
            xInc = 0;
        }
        if (dest.getRank() - rank > 0) {
            yInc = 1;
        } else if (dest.getRank() - rank < 0) {
            yInc = -1;
        } else {
            yInc = 0;
        }
        int diff = Math.max(Math.abs(dest.getFile() - file), Math.abs(dest.getRank() - rank));
        for (int i = 1; i <= diff; i++) {
            BoardTile tile = board.getTile(file + xInc * diff, rank + yInc * diff);
            if (moves.contains(tile)) path.add(tile);
        }
        return path;
    }
}

