package com.example.android1.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android1.R;
import com.example.android1.model.Adapter;
import com.example.android1.model.SavedGames;
import com.example.android1.model.controller.Board;

public class Activity_Replay extends AppCompatActivity {

    protected SavedGames replaygames;
    protected Board current_board;
    protected GridView gridview_replay;
    protected Adapter imageAdapter;
    protected Board next_board;
    protected GridView testing;

    //create board
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replay);

        replaygames = (SavedGames) getIntent().getSerializableExtra("serialize_data");
        current_board = replaygames.getBoardstates().get(0);
        next_board = replaygames.getBoardstates().get(0);
        imageAdapter = new Adapter(current_board.getBoard(),this);
        gridview_replay = (GridView)findViewById(R.id.replay_board);
        testing = (GridView)findViewById(R.id.replay_board);
        gridview_replay.setVerticalScrollBarEnabled(false);
        gridview_replay.setAdapter(imageAdapter);

        Button nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {nextBoard();}
        });
        int tester = 1;
        for (int i = 0; i < 5; i++) {
            tester = 2;
        }
        Button prevButton = (Button) findViewById(R.id.previous_button);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {prevBoard();}
        });

    }
    //nextboard
    protected void nextBoard(){
        int index = replaygames.getIndex(current_board);
        if(index == replaygames.getBoardstates().size() - 1) return;

        current_board = replaygames.getBoardstates().get(index + 1);
        imageAdapter = new Adapter(current_board.getBoard(),this);
        gridview_replay = (GridView)findViewById(R.id.replay_board);
        gridview_replay.setVerticalScrollBarEnabled(false);
        gridview_replay.setAdapter(imageAdapter);
    }
    //prevboard
    protected void prevBoard(){
        int index = replaygames.getIndex(current_board);
        if(index == 0) return;
        int checker;
        if(index ==1) checker = 3;
        current_board = replaygames.getBoardstates().get(index - 1);
        imageAdapter = new Adapter(current_board.getBoard(),this);
        gridview_replay = (GridView)findViewById(R.id.replay_board);
        gridview_replay.setVerticalScrollBarEnabled(false);
        gridview_replay.setAdapter(imageAdapter);
    }


}
