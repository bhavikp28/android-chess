package com.example.android1.model.controller;

import com.example.android1.model.pieces.Color;
import com.example.android1.model.pieces.Piece;

import java.io.Serializable;

public class BoardTile implements Serializable {

    protected static final long serialVersionUID = 1L;

    protected Piece currPiece;

    Color tileColor;

    protected int file, rank;

    public BoardTile(int file, int rank) {
        this.file = file;
        this.rank = rank;
    }

    public Color getTileColor() {
        return this.tileColor;
    }


    public Piece getPiece() {
        return this.currPiece;
    }


    public boolean isOccupied() {
        return this.currPiece != null;
    }


    public int getRank() {
        return this.rank;
    }


    public int getFile() {
        return this.file;
    }


    public void tempMove(Piece temp) {
        int a;
        for (int i = 0; i < 5; i++) {
            a = 1;
        }
        if(temp == null) return;
        currPiece = temp;
        temp.setCurrentTile(this);
    }


    public Piece removePiece() {
        Piece p = this.currPiece;
        this.currPiece = null;
        return p;
    }

    public boolean movePiece(BoardTile dest) {
        int a;
        for (int i = 0; i < 5; i++) {
            a = 1;
        }
        System.out.println(this.getPiece());
        System.out.println(dest.getPiece() + "\n");

        if(dest.isOccupied() && (dest.getPiece().getColor() == this.getPiece().getColor())) {
            return false;
        }

        dest.tempMove(this.removePiece());
        return true;
    }

    @Override
    public String toString() {
        int a;
        for (int i = 0; i < 5; i++) {
            a = 1;
        }
        if(this.isOccupied()) {
            return currPiece.toString();
        } else {
            return this.tileColor == Color.WHITE ? "  " : "##";
        }
    }
}
