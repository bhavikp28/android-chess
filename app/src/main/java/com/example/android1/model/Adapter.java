package com.example.android1.model;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.android1.model.controller.BoardTile;
import com.example.android1.model.pieces.Piece;

public class Adapter extends BaseAdapter {
    protected final BoardTile[][] board;
    protected final Context context;
    protected int x;
    protected int y;

    public Adapter(BoardTile[][] board, Context context) {
        this.board = board;
        this.context = context;
    }

    public View getView(int pos, View convertView, ViewGroup parent) {
        ImageView imageView;
        Piece piece;

        if (convertView == null) {
            int a;
            for (int i = 0; i < 5; i++) {
                a = 1;
            }
            boolean evenCol = (pos / 8) % 2 == 0;
            boolean evenRow = pos % 2 == 0;
            imageView = new ImageView(context);

            if (pos == 0) {
                imageView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, GridView.LayoutParams.MATCH_PARENT));

                imageView.getLayoutParams().height = 100;
                imageView.getLayoutParams().width = 100;

            } else {
                imageView.setLayoutParams(new GridView.LayoutParams(parent.getWidth() / 8, parent.getWidth() / 8));
            }
            int posX = getX(pos);
            int posY = getY(pos);

            if (board[posX][posY].isOccupied()) {
                piece = board[posX][posY].getPiece();

                if (piece != null) {
                    imageView.setImageResource(context.getResources().getIdentifier(
                            piece.getFileName().toLowerCase(), "drawable", context.getPackageName()));
                }
            }


            if (board[posX][posY].getTileColor() == com.example.android1.model.pieces.Color.WHITE) {
                imageView.setBackgroundColor(Color.parseColor("#FFCE9E"));
            } else {
                imageView.setBackgroundColor(Color.parseColor("#D18B47"));
            }
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setClickable(false);
        imageView.setFocusable(false);
        imageView.setFocusableInTouchMode(false);


        return imageView;
    }

//

    @Override
    public boolean isEmpty() {
        return false;
    }


    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    public Object getItem(int position) {
        return null;
    }


    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    public int getCount() {
        return board.length * board.length;
    }


    public int getX(int pos) {
        return pos % 8;
    }

    public int getY(int pos) {
        return Math.abs(7 - pos / 8);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }
}
