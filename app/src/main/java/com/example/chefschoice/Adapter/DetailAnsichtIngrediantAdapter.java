package com.example.chefschoice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.chefschoice.Model.Ingredient;
import com.example.chefschoice.R;

import java.util.List;

public class DetailAnsichtIngrediantAdapter extends ArrayAdapter<Ingredient> {


    public DetailAnsichtIngrediantAdapter(@NonNull Context context, List<Ingredient> zutatenliste) {
        super(context, 0, zutatenliste);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Ingredient zutat = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.detail_ingredient_view, parent, false);
        }

        TextView zutatenName = convertView.findViewById(R.id.zutatenName);
        TextView zutatenMenge = convertView.findViewById(R.id.zutatenMenge);
        TextView zutatenEinheit = convertView.findViewById(R.id.zutatenEinheit);

        zutatenName.setText(zutat.getName());
        zutatenMenge.setText(String.valueOf(zutat.getMenge()));
        zutatenEinheit.setText(zutat.getEinheit());

        return convertView;
    }
}
