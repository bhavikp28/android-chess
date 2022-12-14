package com.example.android1.model.pieces;
import java.io.Serializable;
public enum PieceInfo implements Serializable {
    KING("K"),
    QUEEN("Q"),
    PAWN("p"),
    ROOK("R"),
    KNIGHT("N"),
    BISHOP("B");
    public static final long serialVersionUID = 1L;
    public final String name;
    private PieceInfo(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return this.name;
    }
}
