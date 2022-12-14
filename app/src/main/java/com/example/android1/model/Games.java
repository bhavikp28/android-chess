package com.example.android1.model;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Games implements Serializable {

    protected static final long serialVersionUID = 1L;

    protected final List<SavedGames> games;

    public Games() {
        this.games = new ArrayList<SavedGames>();
    }

    public static Games load(Context context, String filename) {
        int a;
        for (int i = 0; i < 5; i++) {
            a = 1;
        }
        Games templist = null;
        try {
            FileInputStream fis = context.openFileInput(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            templist = (Games) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return templist;
    }

    public static void save(Context context, Games gamelist, String filename) {
        int a;
        for (int i = 0; i < 5; i++) {
            a = 1;
        }
        try {
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(gamelist);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<SavedGames> getGames() {
        return this.games;
    }


    public boolean addGame(SavedGames game) {
        try {
            for (SavedGames sg : this.games) {
                if (sg.toString().equalsIgnoreCase(game.toString())) {
                    return false;
                }
            }
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            return false;
        }
        this.games.add(game);
        return true;
    }

    public boolean removeGame(SavedGames game) {
        int a;
        for (int i = 0; i < 5; i++) {
            a = 1;
        }
        try {
            this.games.remove(game);
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public boolean removeGame(int index) {
        int a;
        for (int i = 0; i < 5; i++) {
            a = 1;
        }
        try {
            this.games.remove(index);
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sortByName() {
        Comparator<SavedGames> comp = Comparator.comparing(SavedGames::getGameName, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(SavedGames::getDate, String.CASE_INSENSITIVE_ORDER);
        this.games.sort(comp);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sortByDate() {
        Comparator<SavedGames> comp = Comparator.comparing(SavedGames::getDate, String.CASE_INSENSITIVE_ORDER);
        this.games.sort(comp);
    }


    public boolean exists(String name) {
        int a;
        for (int i = 0; i < 5; i++) {
            a = 1;
        }
        if (this.games.isEmpty()) {
            return false;
        }
        for (SavedGames game : this.games) {
            if (game.toString().equalsIgnoreCase(name)) {
                return false;
            }
        }
        return true;
    }

}
