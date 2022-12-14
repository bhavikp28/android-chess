package com.example.android1.model.controller;

import com.example.android1.model.pieces.Bishop;
import com.example.android1.model.pieces.Color;
import com.example.android1.model.pieces.King;
import com.example.android1.model.pieces.Knight;
import com.example.android1.model.pieces.Pawn;
import com.example.android1.model.pieces.Piece;
import com.example.android1.model.pieces.PieceInfo;
import com.example.android1.model.pieces.Queen;
import com.example.android1.model.pieces.Rook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board implements Serializable {
    protected static final long serialVersionUID = 1L;


    public BoardTile[][] board;

    public Board() {
        this.board = new BoardTile[8][8];
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                this.board[file][rank] = new BoardTile(file, rank);
                if (file % 2 == 0) {
                    this.board[file][rank].tileColor = rank % 2 == 0 ? Color.BLACK : Color.WHITE;
                } else {
                    this.board[file][rank].tileColor = rank % 2 == 0 ? Color.WHITE : Color.BLACK;
                }
            }
        }
    }

    public BoardTile[][] getBoard() {
        return this.board;
    }

    public BoardTile getTile(int file, int rank) {
        return this.board[file][rank];
    }

    public void initialize() {
        for (int i = 0; i < 8; i++) {
            this.board[i][1].tempMove(new Pawn(Color.WHITE));
            this.board[i][6].tempMove(new Pawn(Color.BLACK));
        }

        this.board[0][0].tempMove(new Rook(Color.WHITE));
        this.board[1][0].tempMove(new Knight(Color.WHITE));
        this.board[2][0].tempMove(new Bishop(Color.WHITE));
        this.board[3][0].tempMove(new Queen(Color.WHITE));
        this.board[4][0].tempMove(new King(Color.WHITE));
        this.board[5][0].tempMove(new Bishop(Color.WHITE));
        this.board[6][0].tempMove(new Knight(Color.WHITE));
        this.board[7][0].tempMove(new Rook(Color.WHITE));

        this.board[0][7].tempMove(new Rook(Color.BLACK));
        this.board[1][7].tempMove(new Knight(Color.BLACK));
        this.board[2][7].tempMove(new Bishop(Color.BLACK));
        this.board[3][7].tempMove(new Queen(Color.BLACK));
        this.board[4][7].tempMove(new King(Color.BLACK));
        this.board[5][7].tempMove(new Bishop(Color.BLACK));
        this.board[6][7].tempMove(new Knight(Color.BLACK));
        this.board[7][7].tempMove(new Rook(Color.BLACK));
    }

    public void print() {
        for (int rank = 7; rank >= 0; rank--) {
            for (int file = 0; file < 8; file++) {
                System.out.print(board[file][rank] + " ");
            }
            System.out.println(rank + 1);
        }
        System.out.println(" a  b  c  d  e  f  g  h\n");

    }

    public boolean move(BoardTile start, BoardTile end) {
        if (!start.isOccupied()) {
            return false;
        }

        Piece temp = start.getPiece();
        if (temp.findLegalMoves(this).contains(end)) {
            if (temp.getType() == PieceInfo.KING) {
                return moveKing(start, end);
            }

            if (temp.getType() == PieceInfo.PAWN) {
                return movePawn(start, end);
            }

            return capture(start, end);
        }
        return false;
    }

    protected boolean movePawn(BoardTile start, BoardTile end) {
        if (Math.abs(end.getRank() - start.getRank()) == 2) {
            end.tempMove(start.removePiece());
            if (isCheck(end.getPiece().getColor())) {
                start.tempMove(end.removePiece());
                return false;
            }
            end.getPiece().firstMove = true;
            setEnPassate();
            end.getPiece().enPassante = true;
            return true;
        }
        if (Math.abs(end.getFile() - start.getFile()) == 1) {
            if (end.getFile() - start.getFile() > 0) {
                //passant to the right
                BoardTile passant = this.getTile(start.getFile() + 1, start.getRank());
                if (passant.isOccupied() && passant.getPiece().isEnPassante()) {
                    Piece hold = passant.removePiece();
                    end.tempMove(start.removePiece());
                    if (isCheck(end.getPiece().getColor())) {
                        start.tempMove(end.removePiece());
                        passant.tempMove(hold);
                        return false;
                    }
                    end.getPiece().firstMove = true;
                    setEnPassate();
                    return true;
                }
            }
            if (end.getFile() - start.getFile() < 0) {
                //passant to the left
                BoardTile passant = this.getTile(start.getFile() - 1, start.getRank());
                if (passant.isOccupied() && passant.getPiece().isEnPassante()) {
                    Piece hold = passant.removePiece();
                    end.tempMove(start.removePiece());
                    if (isCheck(end.getPiece().getColor())) {
                        start.tempMove(end.removePiece());
                        passant.tempMove(hold);
                        return false;
                    }
                    end.getPiece().firstMove = true;
                    setEnPassate();
                    return true;
                }
            }
        }
        return capture(start, end);
    }

    protected boolean moveKing(BoardTile start, BoardTile end) {
        int a;
        for (int i = 0; i < 5; i++) {
            a = 1;
        }
        Piece king = start.getPiece();
        Color opp_color = king.getColor() == Color.WHITE ? Color.BLACK : Color.WHITE;
        if (!king.firstMove && Math.abs(start.getFile() - end.getFile()) == 2 && start.getRank() == end.getRank()) {
            if (isCheck(king.getColor())) return false;
            if (end.getFile() - start.getFile() > 0) {
                Piece rook = this.board[7][start.getRank()].getPiece();
                if (rook.firstMove) return false;
                for (int file = 5; file < 7; file++) {
                    if (this.board[file][start.getRank()].isOccupied())
                        return false;
                    if (isUnderAttack(this.board[file][start.getRank()], opp_color))
                        return false;
                }

                king.firstMove = true;
                rook.firstMove = true;
                end.tempMove(start.removePiece());
                this.board[5][start.getRank()].tempMove(rook.getCurrentTile().removePiece());
                setEnPassate();
                return true;
            } else {
                Piece rook = this.board[0][start.getRank()].getPiece();
                if (rook.firstMove || isUnderAttack(rook.getCurrentTile(), opp_color)) return false;
                for (int file = 1; file < 4; file++) {
                    if (this.board[file][start.getRank()].isOccupied()) return false;
                    if (isUnderAttack(this.board[file][start.getRank()], opp_color)) return false;
                }

                king.firstMove = true;
                rook.firstMove = true;
                end.tempMove(start.removePiece());
                this.board[3][start.getRank()].tempMove(rook.getCurrentTile().removePiece());
                setEnPassate();
                return true;
            }
        }

        return capture(start, end);
    }

    protected boolean isUnderAttack(BoardTile boardTile, Color opp_color) {
        int a;
        for (int i = 0; i < 5; i++) {
            a = 1;
        }
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                BoardTile dangerPiece = this.getTile(x, y);
                if (dangerPiece.isOccupied() && dangerPiece.getPiece().getColor() == opp_color) {
                    List<BoardTile> dangerMove = dangerPiece.getPiece().dangerMove(this, boardTile);
                    if (dangerMove.isEmpty()) {
                        continue;
                    } else {
                        if (dangerMove.contains(boardTile)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    protected boolean capture(BoardTile start, BoardTile end) {
        Piece keep = end.removePiece();
        end.tempMove(start.removePiece());
        if (isCheck(end.getPiece().getColor())) {
            start.tempMove(end.removePiece());
            end.tempMove(keep);
            return false;
        }
        end.getPiece().firstMove = true;
        setEnPassate();
        return true;
    }

    public BoardTile getKing(Color color) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                BoardTile king_pos = this.board[x][y];
                if (king_pos.isOccupied()) {
                    if (king_pos.getPiece().getType() == PieceInfo.KING && king_pos.getPiece().getColor() == color)
                        return king_pos;
                }
            }
        }
        return null;
    }

    protected boolean isCheck(Color color) {
        int a;
        for (int i = 0; i < 5; i++) {
            a = 1;
        }
        BoardTile king = getKing(color);
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                BoardTile attacker = this.getTile(file, rank);
                if (attacker.isOccupied() && attacker.getPiece().isDifferentColor(king.getPiece())) {
                    if (attacker.getPiece().dangerMove(this, king).isEmpty()) {
                        continue;
                    } else {
                        if (attacker.getPiece().dangerMove(this, king).contains(king))
                            return true;
                    }
                }
            }
        }
        return false;
    }

    public BoardTile randomMove(Color color) {
        List<BoardTile> sourceTiles = new ArrayList<BoardTile>();
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                BoardTile tile = this.board[file][rank];
                if (tile.isOccupied() && tile.getPiece().getColor().equals(color) && tile.getPiece().findLegalMoves(this).size() > 0)
                    sourceTiles.add(tile);
            }
        }

        Collections.shuffle(sourceTiles);
        for (int i = 0; i < sourceTiles.size(); i++) {
            List<BoardTile> destTiles = sourceTiles.get(i).getPiece().findLegalMoves(this);
            Collections.shuffle(destTiles);
            for (int j = 0; j < destTiles.size(); j++) {
                if (move(sourceTiles.get(i), destTiles.get(j))) return destTiles.get(j);
            }
        }
        return null;
    }

    public void setEnPassate() {
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                BoardTile tile = this.getTile(file, rank);
                if (tile.isOccupied()) tile.getPiece().enPassante = false;
            }
        }
    }

    public Board saving() throws IOException, ClassNotFoundException {
        int a;
        for (int i = 0; i < 5; i++) {
            a = 1;
        }
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try
        {
            ByteArrayOutputStream bos =
                    new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            oos.flush();
            ByteArrayInputStream bin =
                    new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bin);
            return (Board)ois.readObject();
        }
        catch(Exception e)
        {

            throw(e);
        }
        finally
        {
            oos.close();
            ois.close();
        }
    }

    public boolean isCheckMate(Color opponent) {
        int a;
        for (int i = 0; i < 5; i++) {
            a = 1;
        }
        for(int file = 0; file < 8; file++) {
            for(int rank = 0; rank < 8; rank++) {
                BoardTile start = getTile(file, rank);
                if(start.isOccupied() && start.getPiece().getColor() == opponent) {
                    List<BoardTile> moves = start.getPiece().findLegalMoves(this);
                    for(BoardTile end : moves) {
                        Piece hold = end.removePiece();
                        end.tempMove(start.removePiece());
                        if(!isCheck(opponent)) {
                            start.tempMove(end.removePiece());
                            end.tempMove(hold);
                            return false;
                        }
                        start.tempMove(end.removePiece());
                        end.tempMove(hold);
                    }
                }
            }
        }
        return true;
    }
}
