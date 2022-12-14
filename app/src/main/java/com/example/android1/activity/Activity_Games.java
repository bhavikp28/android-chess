package com.example.android1.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.ToggleButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android1.R;
import com.example.android1.model.Games;
import com.example.android1.model.SavedGames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity_Games extends AppCompatActivity {

    protected Games games;
    protected List<SavedGames> savedGames;
    protected String filename = "chess.dat";
    protected ArrayList<Map<String,Object>> gameInfoList;
    protected ListView listView;
    protected SimpleAdapter adapter;
    protected int index;
// create board
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
        int a = 1;
        for (int i = 0; i < 5; i++) {
            a=3;
        }

        games = Games.load(this,filename);
        if (games == null) games = new Games();
        games.sortByName();

        populateListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //when they click find position
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;
                System.out.println("index is: " + index);


            }
        });
//button to play gmae
        Button loadButton = (Button) findViewById(R.id.load_game_button);
        loadButton.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(games.getGames().size() > 0) {
                    SavedGames game = games.getGames().get(index);
                    if (game!= null) {
                        //load selected game into the replay activity
                        Intent intent = new Intent(Activity_Games.this,
                                Activity_Replay.class);
                        intent.putExtra("serialize_data",game);
                        startActivity(intent);
                    }
                }
            }
        });
//delete game recording
        Button delButton = (Button) findViewById(R.id.delete_game_button);
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(games.getGames().size() > 0) {
                    games.removeGame(index);
                    Games.save(Activity_Games.this, games, filename);
                    populateListView();
                    int a = 1;
                    for (int i = 0; i < 5; i++) {
                        a=3;
                    }
                }
            }
        });

        ToggleButton sortToggle = (ToggleButton) findViewById(R.id.toggleSort);
        sortToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    games.sortByDate();
                    populateListView();
                } else {
                    games.sortByName();
                    populateListView();
                }
            }
        });

    }
//sort the view
    protected void populateListView(){
        savedGames = games.getGames();
        gameInfoList = new ArrayList<Map<String,Object>>();
        for(int i = 0; i < savedGames.size(); i++) {
            Map<String,Object> gameInfoMap = new HashMap<String,Object>();
            gameInfoMap.put("title", savedGames.get(i).getGameName());
            gameInfoMap.put("date", savedGames.get(i).getDate());
            gameInfoList.add(gameInfoMap);
        }
        int a = 1;
        for (int i = 0; i < 5; i++) {
            a=3;
        }
        adapter = new SimpleAdapter(this, gameInfoList, android.R.layout.simple_list_item_2,
                new String[]{"title", "date"}, new int[]{android.R.id.text1,android.R.id.text2});

        listView = (ListView) findViewById(R.id.saved_games_list);
        listView.setAdapter(adapter);

    }

}
