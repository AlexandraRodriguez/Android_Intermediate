package com.example.ale.pokedexapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;


public class PokemonDetailsActivity extends Activity {
    private TabHost tabHost;
    private Pokemon pokemon;

    private ImageView pokemonIcon;
    private TextView txtNumber;
    private TextView txtName;
    private TextView txtType;
    private TextView txtDescription;
    private TextView txtSpecie;
    private TextView txtAbilities;
    private TextView txtEggGroups;

    private TextView txtWeight;
    private TextView txtHeight;
    private TextView txtHP;
    private TextView txtAttack;
    private TextView txtDefense;
    private TextView txtSpeed;
    private TextView txtSpAttack;
    private TextView txtSpDefense;


    int number;
    String name;
    Bitmap image;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.pokemon_details);

        tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();
        TabHost.TabSpec spec = tabHost.newTabSpec("Tab1").setIndicator("Pokemon").setContent(R.id.tab1);
        tabHost.addTab(spec);
        spec = tabHost.newTabSpec("Tab2").setIndicator("Stats").setContent(R.id.tab2);
        tabHost.addTab(spec);
        tabHost.setCurrentTab(0);

        loadReferences();

        number = getIntent().getExtras().getInt("pNumber");
        name = getIntent().getExtras().getString("pName");
        image = (Bitmap) getIntent().getExtras().get("pImage");

        Log.e("details", "pokemon number from intent: " + number);
        pokemon = new Pokemon();
        getPokemon(number);
        //setDetails();
    }

    private void loadReferences() {
        pokemonIcon = (ImageView) findViewById(R.id.pokemonIcon);
        txtNumber = (TextView) findViewById(R.id.txtNumber);
        txtName = (TextView) findViewById(R.id.txtName);
        txtType = (TextView) findViewById(R.id.txtType);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        txtSpecie = (TextView) findViewById(R.id.txtSpecie);
        txtAbilities = (TextView) findViewById(R.id.txtAbilities);
        txtEggGroups = (TextView) findViewById(R.id.txtEggGroups);

        txtWeight = (TextView) findViewById(R.id.txtWeight);
        txtHeight = (TextView) findViewById(R.id.txtHeight);
        txtHP = (TextView) findViewById(R.id.txtHP);
        txtAttack = (TextView) findViewById(R.id.txtAttack);
        txtDefense = (TextView) findViewById(R.id.txtDefense);
        txtSpeed = (TextView) findViewById(R.id.txtSpeed);
        txtSpAttack = (TextView) findViewById(R.id.txtSpAttack);
        txtSpDefense = (TextView) findViewById(R.id.txtSpDefense);
    }


    public void getPokemon(int num) {
        new PokemonGetter().execute(num);
    }


    public void savePokemon(View view){
        pokemon.save();
    }


    private class PokemonGetter extends AsyncTask<Integer, Void, Pokemon> {

        @Override
        protected Pokemon doInBackground(Integer... params) {
            JSONManager jsonManager = new JSONManager();
            Pokemon pokemon = jsonManager.getNewPokemon(params[0]);
            return pokemon;
        }

        @Override
        protected void onPostExecute(Pokemon p) {
            pokemon = p;
            Log.e("JSON", "onPostExecute, name:" + pokemon.getName() + ", number:" + pokemon.getNumber() + ", type:" + pokemon.getType());
            txtName.setText(name);
            txtNumber.setText("N° " + number);
            pokemonIcon.setImageBitmap(image);
            txtType.setText("Type: " + pokemon.getType());
            txtDescription.setText(pokemon.getDescription());
            txtSpecie.setText("Specie: " + pokemon.getSpecie());
            txtAbilities.setText("Abilities: " + pokemon.getAbilities());
            txtEggGroups.setText("Egg Groups: " + pokemon.getEggGroups());

            txtWeight.setText("Weight: "+pokemon.getWeight());
            txtHeight.setText("Height: "+pokemon.getHeight());
            txtHP.setText("HP: "+pokemon.getHp());
            txtAttack.setText("Attack: "+pokemon.getAttack());
            txtDefense.setText("Defense: "+pokemon.getDefense());
            txtSpeed.setText("Speed: "+pokemon.getSpeed());
            txtSpAttack.setText("sp_attack: "+pokemon.getSpAttack());
            txtSpDefense.setText("sp_defense: "+pokemon.getSpDefense());
        }
    }
}
