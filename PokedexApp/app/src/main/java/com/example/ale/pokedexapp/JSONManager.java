package com.example.ale.pokedexapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class JSONManager {
    public static final String BASE_URL = "http://pokeapi.co";

    public JSONManager() {

    }

    public Pokemon getNewPokemon(int num) {
        Pokemon pokemon = new Pokemon();
        String url = makePokemonURL(num);
        String json = getJson(url);
        try {
            JSONObject jPokemon = new JSONObject(json);

            JSONArray abilities = jPokemon.getJSONArray("abilities");
            for (int i = 0; i < abilities.length(); i++) {
                pokemon.setAbilities(abilities.getJSONObject(i).getString("name"));
            }

            JSONArray eggGroups = jPokemon.getJSONArray("egg_groups");
            for (int i = 0; i < eggGroups.length(); i++) {
                pokemon.setEggGroups(eggGroups.getJSONObject(i).getString("name"));
            }

            JSONArray types = jPokemon.getJSONArray("types");
            for (int i = 0; i < types.length(); i++) {
                pokemon.setType(types.getJSONObject(i).getString("name"));
            }

            JSONArray descriptionArray = jPokemon.getJSONArray("descriptions");
            String desc = descriptionArray.getJSONObject(0).getString("resource_uri");

            pokemon.setDescription(getPokemonDesciption(desc));

            pokemon.setAttack(jPokemon.getInt("attack"));
            pokemon.setDefense(jPokemon.getInt("defense"));
            pokemon.setHeight(jPokemon.getInt("height"));
            pokemon.setHp(jPokemon.getInt("hp"));
            pokemon.setSpAttack(jPokemon.getInt("sp_atk"));
            pokemon.setSpDefense(jPokemon.getInt("sp_def"));
            pokemon.setSpecie(jPokemon.getString("species"));
            pokemon.setSpeed(jPokemon.getInt("speed"));
            pokemon.setWeight(jPokemon.getInt("weight"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pokemon;
    }

    public String getPokemonDesciption(String d){
        String res = "";
        String url = makeDescriptionUrRL(d);
        String json = getJson(url);
        try {
            JSONObject desc = new JSONObject(json);
            res = desc.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }

    private String makePokemonURL(int num) {
        return BASE_URL + "/api/v1/pokemon/" + num + "/";

    }

    private String makeDescriptionUrRL(String d){
        return BASE_URL + d;
    }

    private String getJson(String mUrl) {
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(mUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = buffer.readLine()) != null) {
                content.append(line + "\n");
            }
            buffer.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
