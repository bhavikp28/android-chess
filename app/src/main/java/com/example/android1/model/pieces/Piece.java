package com.example.android1.model.pieces;
import com.example.android1.model.controller.Board;
import com.example.android1.model.controller.BoardTile;
import java.io.Serializable;
import java.util.List;

public abstract class Piece implements Serializable {
    protected static final long serialVersionUID = 1L;
    public boolean enPassante;
    public Color color;
    protected BoardTile currentTile;
    protected PieceInfo type;
    public boolean firstMove;
    public Piece(Color color) {
        this.color = color;
        this.firstMove = false;
        this.enPassante = false;
    }

    public void setCurrentTile(BoardTile tile) {
        currentTile = tile;
    }
    public BoardTile getCurrentTile() {
        return this.currentTile;
    }
    public boolean isDifferentColor(Piece piece) {
        return this.getColor() == piece.getColor() ? false : true;
    }
    public Color getColor() {
        return this.color;
    }
    public boolean isEnPassante() {
        return enPassante;
    }
    @Override
    public String toString() {
        String color = this.color == Color.WHITE ? "w" : "b";
        return color + this.type.toString();
    }
    public PieceInfo getType() {
        return this.type;
    }
    public String getFileName() {
        return type.name() + "_" + color.toString();
    }
    public abstract List<BoardTile> findLegalMoves(Board board);
    public abstract List<BoardTile> dangerMove(Board board, BoardTile dest);
}
