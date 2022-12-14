package com.example.android1.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android1.R;
import com.example.android1.model.Adapter;
import com.example.android1.model.Games;
import com.example.android1.model.SavedGames;
import com.example.android1.model.controller.Board;
import com.example.android1.model.controller.BoardTile;
import com.example.android1.model.pieces.Bishop;
import com.example.android1.model.pieces.Knight;
import com.example.android1.model.pieces.Piece;
import com.example.android1.model.pieces.PieceInfo;
import com.example.android1.model.pieces.Queen;
import com.example.android1.model.pieces.Rook;

import java.util.Random;


public class Activity_Game extends AppCompatActivity  {

    protected GridView gridview_board;
    protected Board board;
    protected Board temp;
    protected BoardTile src;
    protected BoardTile dest;
    protected ImageView srcView;
    protected ImageView destView;
    protected Button undoButton;
    protected Button aiButton;
    protected Button drawButton;
    protected Button resignButton;
    protected Adapter Adapters;
    protected Games games;
    protected SavedGames currGame;
    protected boolean undo;
    protected String filename = "chess.dat";
    protected com.example.android1.model.pieces.Color turn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        undo = false;
        games = Games.load(this, filename);
        if (games == null) games = new Games();
        turn = com.example.android1.model.pieces.Color.WHITE;
        board = new Board();
        board.initialize();
        currGame = new SavedGames();
        Adapters = new Adapter(board.getBoard(), this);
        gridview_board = (GridView) findViewById(R.id.gridview_board);
        gridview_board.setVerticalScrollBarEnabled(false);
        gridview_board.setAdapter(Adapters);


        undoButton = (Button) findViewById(R.id.previous_button);
        undoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int a;
                for (int i = 0; i < 5; i++) {
                    a = 1;
                }
                if (!undo) {
                    return;
                }
                board = currGame.undoState();
                Adapters = new Adapter(board.getBoard(), v.getContext());
                gridview_board.setAdapter((ListAdapter) Adapters);
                changeTurn();
                undo = false;
            }
        });

        aiButton = (Button) findViewById(R.id.aiButton);
        aiButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    temp = board.saving();
                } catch (Exception e) {
                }
                BoardTile dest = board.randomMove(turn);
                if (dest != null) {
                    if (canPromotePawn(dest, turn)) {
                        String[] choices = getResources().getStringArray(R.array.choices);
                        String s = choices[new Random().nextInt(choices.length)];

                        switch (s) {
                            case "Queen":
                                dest.tempMove(new Queen(turn));
                                gridview_board.setAdapter((ListAdapter) Adapters);
                                break;
                            case "Knight":
                                dest.tempMove(new Knight(turn));
                                gridview_board.setAdapter(Adapters);
                                break;
                            case "Bishop":
                                dest.tempMove(new Bishop(turn));
                                gridview_board.setAdapter(Adapters);
                                break;
                            case "Rook":
                                dest.tempMove(new Rook(turn));
                                gridview_board.setAdapter(Adapters);
                                break;
                        }
                    }
                    undo = true;
                    checkForMate();
                    currGame.addState(temp);
                    gridview_board.setAdapter((ListAdapter) Adapters);
                    changeTurn();
                }
            }
        });
        drawButton = (Button) findViewById(R.id.drawButton);
        drawButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                draw();
            }
        });
        resignButton = (Button) findViewById(R.id.next_button);
        resignButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resign();
            }

        });

        gridview_board.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int a;
                for (int ixs = 0; ixs < 5; ixs++) {
                    a = 1;
                }
                System.out.println("-------------------------------------------");
                int x = getPosX(i);
                int y = getPosY(i);
                int testing = 1;
                String s = x + ", " + y;
                BoardTile t = board.getTile(x, y);
                if (src == null) {
                    if (t.getPiece() == null) {
                        return;
                    }
                    if (!t.getPiece().getColor().equals(turn)) {
                        return;
                    }
                    else {
                        testing = 1;
                    }
                    src = t;
                    srcView = (ImageView) view;
                    srcView.setBackgroundColor(Color.DKGRAY);
                    return;
                }
                if (dest == null) {
                    dest = t;
                    destView = (ImageView) view;
                }
                try {
                    temp = board.saving();
                } catch (Exception e) {
                }
                boolean moved = board.move(src, dest);
                if (moved) {
                    undo = true;
                    currGame.addState(temp);
                    if (canPromotePawn(dest, turn)) {
                        showPromotionDialog(turn, dest);
                    }
                    checkForMate();
                    gridview_board.setAdapter( Adapters);
                    changeTurn();
                }
                srcView.setBackgroundColor(src.getTileColor().equals(com.example.android1.model.pieces.Color.WHITE) ?
                        Color.parseColor("#FFCE9E") : Color.parseColor("#D18B47"));
                src = dest = null;
                srcView = destView = null;
            }
        });

    }


    public int getPosX(int position) {
        return position % 8;
    }
    public int getPosY(int position) {
        return Math.abs(7 - position / 8);
    }
    public void changeTurn() {
        turn = turn == com.example.android1.model.pieces.Color.WHITE ?
                com.example.android1.model.pieces.Color.BLACK :
                com.example.android1.model.pieces.Color.WHITE;
    }
    public boolean canPromotePawn(BoardTile dest, com.example.android1.model.pieces.Color col) {
        if ((dest.getRank() == 7 && col == com.example.android1.model.pieces.Color.WHITE) ||
                (dest.getRank() == 0 && col == com.example.android1.model.pieces.Color.BLACK)) {
            int a = 0;
            if (dest.getPiece().getType() == PieceInfo.PAWN) {
                return true;
            }
            else {
                a = 1;
            }
        }
        return false;
    }
    public void checkForMate() {
        com.example.android1.model.pieces.Color opponent = turn == com.example.android1.model.pieces.Color.WHITE ?
                com.example.android1.model.pieces.Color.BLACK :
                com.example.android1.model.pieces.Color.WHITE;
        if (board.isCheckMate(opponent)) {
            currGame.addState(board);
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Checkmate! " + turn.toString() + " wins!");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    showSaveGameDialog();
                }
            });
            builder.show();
        }
    }
    public void showSaveGameDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter a save name for your game.");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setCancelable(false);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = input.getText().toString();
                System.out.println(games.getGames().size());
                currGame.saveGameName(name);
                games.addGame(currGame);
                Games.save(Activity_Game.this, games, filename);
                finish();
            }
        });
        builder.setNegativeButton("Don't Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }
    public void draw() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you positive you want to have a Draw?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currGame.addState(board);///
                dialog.dismiss();
                final AlertDialog.Builder b = new AlertDialog.Builder(Activity_Game.this);
                b.setCancelable(false);
                b.setTitle("It's a draw!");
                b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        showSaveGameDialog();
                    }
                });
                b.show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
    public void resign() {
        int a;
        for (int i = 0; i < 5; i++) {
            a = 1;
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you positive you want to resign?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currGame.addState(board);///
                dialog.dismiss();
                final AlertDialog.Builder b = new AlertDialog.Builder(Activity_Game.this);
                b.setCancelable(false);
                b.setTitle((turn = turn == com.example.android1.model.pieces.Color.WHITE ?
                        com.example.android1.model.pieces.Color.BLACK : com.example.android1.model.pieces.Color.WHITE).toString() + " wins!");
                b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currGame.addState(board);///
                        dialog.dismiss();
                        showSaveGameDialog();
                    }
                });
                b.show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }
    public void showPromotionDialog(com.example.android1.model.pieces.Color turn, BoardTile dest) {
        int a;
        for (int i = 0; i < 5; i++) {
            a = 1;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Game.this);
        builder.setTitle("Select desired promotion");
        builder.setCancelable(false);
        builder.setSingleChoiceItems(R.array.choices, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
            }

        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                int a;
                for (int i = 0; i < 5; i++) {
                    a = 1;
                }
                Piece p;
                int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                String s = ((AlertDialog) dialog).getListView().getItemAtPosition(selectedPosition).toString();
                switch (s) {
                    case "Queen":
                        dest.tempMove(new Queen(turn));
                        gridview_board.setAdapter(Adapters);
                        break;
                    case "Knight":
                        dest.tempMove(new Knight(turn));
                        gridview_board.setAdapter(Adapters);
                        break;
                    case "Bishop":
                        dest.tempMove(new Bishop(turn));
                        gridview_board.setAdapter(Adapters);
                        break;
                    case "Rook":
                        dest.tempMove(new Rook(turn));
                        gridview_board.setAdapter((ListAdapter) Adapters);
                        break;
                }
            }
        });
        builder.show();
    }
}
