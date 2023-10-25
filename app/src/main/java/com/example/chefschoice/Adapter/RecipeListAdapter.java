package com.example.chefschoice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.chefschoice.Model.Recipe;
import com.example.chefschoice.R;

import java.util.ArrayList;

public class RecipeListAdapter extends ArrayAdapter<Recipe> {

    private static Context mContext;
    int mResource;
    public RecipeListAdapter( Context context, int resource, ArrayList<Recipe> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getName();
        byte[] bild = getItem(position).getBild();
        String beschreibung = getItem(position).getBeschreibung();

        Recipe rezept = new Recipe(name,beschreibung, bild);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent,false);

        ImageView bildView = (ImageView) convertView.findViewById(R.id.bild);
        TextView nameView = (TextView)  convertView.findViewById(R.id.text);

        nameView.setText(name);

        return convertView;
    }
}
