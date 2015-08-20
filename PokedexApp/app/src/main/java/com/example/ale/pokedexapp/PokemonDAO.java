package com.example.ale.pokedexapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class PokemonDAO {


    public void getListFromParse(final PCallback callback) {
        Log.e("tag", "inside getListFromParse");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Pokemon");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> pokemonList, ParseException e) {

                Log.e("tag", "inside parse, pokemonList size: " + pokemonList.size());

                List<Pokemon> pokemons = new ArrayList<Pokemon>();

                    for (ParseObject p : pokemonList) {
                        final Pokemon pokemon = new Pokemon();
                        ParseObject pObject = p;
                        pokemon.setName(pObject.getString("name"));
                        pokemon.setNumber(pObject.getInt("number"));
                        ParseFile imageFile = (ParseFile)pObject.get("image");
                        imageFile.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] bytes, ParseException e) {
                                if(e == null) {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                    pokemon.setImage(bitmap);
                                }
                            }
                        });
                        pokemons.add(pokemon);
                    }

                callback.getPokemonList(pokemons);

            }
        });

    }



}
