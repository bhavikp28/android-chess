package com.example.android1.model.pieces;
import java.io.Serializable;
public enum Color implements Serializable {
    WHITE("White"), BLACK("Black");
    protected final String col;
    Color(String col) {
        this.col = col;
    }
    @Override
    public String toString() {
        return this.col;
    }
}
