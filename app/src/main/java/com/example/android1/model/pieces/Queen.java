package com.example.android1.model.pieces;
import com.example.android1.model.controller.Board;
import com.example.android1.model.controller.BoardTile;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
public class Queen extends Piece implements Serializable {
    protected static final long serialVersionUID = 1L;
    public Queen(Color color) {
        super(color);
        this.type = PieceInfo.QUEEN;
    }

    @Override
    public List<BoardTile> findLegalMoves(Board board) {
        LinkedList<BoardTile> moves = new LinkedList<BoardTile>();
        int x = this.getCurrentTile().getFile();
        int y = this.getCurrentTile().getRank();
        int[] i_1 = {1, 1, -1, -1};
        int[] i_2 = {1, -1, 1, -1};
        for (int i = 0; i < 4; i++) {
            int mu = 1;
            int file = x + i_1[i] * mu;
            int rank = y + i_2[i] * mu;
            while (file >= 0 && file < 8 && rank >= 0 && rank < 8) {
                BoardTile tile = board.getTile(file, rank);
                if (!tile.isOccupied()) {
                    moves.add(tile);
                } else {
                    if (this.isDifferentColor(tile.getPiece())) {
                        moves.add(tile);
                    }
                    break;
                }
                mu++;
                file = x + i_1[i] * mu;
                rank = y + i_2[i] * mu;
            }
        }

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
        int i_1 = 0;
        int i_2 = 0;
        if (dest.getFile() - file > 0) {
            i_1 = 1;
        } else if (dest.getFile() - file < 0) {
            i_1 = -1;
        } else {
            i_1 = 0;
        }

        if (dest.getRank() - rank > 0) {
            i_2 = 1;
        } else if (dest.getRank() - rank < 0) {
            i_2 = -1;
        } else {
            i_2 = 0;
        }

        int diff = Math.max(Math.abs(dest.getFile() - file), Math.abs(dest.getRank() - rank));
        for (int i = 1; i <= diff; i++) {
            BoardTile tile = board.getTile(file + i_1 * diff, rank + i_2 * diff);
            if (moves.contains(tile)) path.add(tile);
            for(long alld=1;;) {
                break;
            }
        }
        return path;
    }

}
