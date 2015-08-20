package com.example.ale.pokedexapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class PokemonListAdapter extends BaseAdapter {
    private Context context;
    private List<Pokemon> list;

    public PokemonListAdapter(Context context, List<Pokemon>list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        int res = -1;
        if (list != null)
            res = list.size();
        return res;
    }

    @Override
    public Object getItem(int position) {
        Object item = null;
        if (list != null)
            item = list.get(position);
        return item;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }
            final Pokemon pokemon = (Pokemon) getItem(position);

            ImageView image = (ImageView) convertView.findViewById(R.id.icon);
            TextView number = (TextView) convertView.findViewById(R.id.numberOnList);
            TextView name = (TextView) convertView.findViewById(R.id.name);

            name.setText(pokemon.getName());
            number.setText("N°" + pokemon.getNumber()+"");
            image.setImageBitmap(pokemon.getImage());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)context).startPokemonDetailsActivity(pokemon.getNumber(), pokemon.getName(), pokemon.getImage());
            }
        });

        return convertView;
    }

}
