package com.example.android1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android1.R;

public class Activity_Main extends AppCompatActivity {
    // Declare two Button objects
    protected Button loadButton;
    protected Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize the Button objects

        loadButton = (Button) findViewById(R.id.LoadBt);
        playButton = (Button) findViewById(R.id.startBt);
        // Set up an event listener for the playButton

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
            // When the playButton is clicked, call the startGame() method

        });

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadGame();
            }
        });
    }
//define start game method
    protected void startGame() {
        Intent myIntent = new Intent(this, Activity_Game.class);
        startActivity(myIntent);
    }
// define load game method
    protected void loadGame() {
        Intent myIntent = new Intent(this, Activity_Games.class);
        startActivity(myIntent);
    }
}
