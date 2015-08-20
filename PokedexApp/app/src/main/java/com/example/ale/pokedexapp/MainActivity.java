package com.example.ale.pokedexapp;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ListActivity {
    private PokemonListAdapter adapter;
    private List<Pokemon> list;
    private PokemonDAO dao;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main);

        dao = new PokemonDAO();
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        list = new ArrayList<Pokemon>();
        initializeList();
        Log.e("tagSize", "list size after initialize method: " + list.size());
        adapter = new PokemonListAdapter(this, list);
        this.setListAdapter(adapter);
    }

    private void initializeList() {
        dao.getListFromParse(new PCallback() {
            @Override
            public void getPokemonList(List<Pokemon> pokemonList) {
                for (Pokemon p : pokemonList) {
                    Log.e("tagName", p.getName());
                    list.add(p);
                    Log.e("tagSize", "" + list.size());
                }
                Log.e("tagSize", "res size after for loop: " + list.size());
                setAdapter();
            }
        });
    }

    private void setAdapter() {
        adapter = new PokemonListAdapter(this, list);
        this.setListAdapter(adapter);
    }

    public void startPokemonDetailsActivity(int num, String name, Bitmap image) {
        Intent intent = new Intent(this, PokemonDetailsActivity.class);
        intent.putExtra("pNumber", num);
        intent.putExtra("pName", name);
        intent.putExtra("pImage", image);
        startActivity(intent);
    }
}
