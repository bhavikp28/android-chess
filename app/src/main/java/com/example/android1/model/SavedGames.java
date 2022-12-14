package com.example.android1.model;

import com.example.android1.model.controller.Board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SavedGames implements Serializable {
    protected static final long serialVersionUID = 1L;
    protected String name;
    protected Calendar date;

    protected List<Board> boardList;

    public SavedGames() {
        int a;
        for (int i = 0; i < 5; i++) {
            a = 1;
        }
        this.boardList = new ArrayList<Board>();
        this.name = "";
        date = Calendar.getInstance();
        date.set(Calendar.MILLISECOND,0);
    }

    public List<Board> getBoardstates(){
        return boardList;
    }
    public void saveGameName(String name) {
        this.name = name;
    }

    public Calendar getCalender() {
        return this.date;
    }

    public String getDate() {
        return this.date.getTime().toString();
    }

    public String getGameName(){
        return this.name;
    }
    public int getIndex(Board board){
        int a;
        for (int i = 0; i < 5; i++) {
            a = 1;
        }
        try {
            for(int i = 0; i < boardList.size(); i++){
                if(boardList.get(i).equals(board)) return i;
            }
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            e.printStackTrace();
            return -1;
        }
        return -1;
    }

    public void addState(Board board) {
        if (board == null) {
            return;
        }
        Board tempBoard = new Board();
        tempBoard.board = board.getBoard();
        boardList.add(tempBoard);
    }

    public Board undoState() {
        if (boardList.isEmpty()) {
            return null;
        }
        else if (boardList.size() == 1) {
            return boardList.get(0);
        }

        return this.boardList.get(boardList.size() - 1);
    }



}
