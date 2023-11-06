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

import com.example.chefschoice.Model.Ingredient;
import com.example.chefschoice.Model.Recipe;
import com.example.chefschoice.R;

import java.math.BigDecimal;
import java.util.List;

public class IngredientsListAdapter extends ArrayAdapter<Ingredient> {

    private static Context mContext;

    int mRessource;

    public IngredientsListAdapter(@NonNull Context context, int resource, @NonNull List<Ingredient> objects) {
        super(context, resource, objects);
        mContext = context;
        mRessource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

        Ingredient ingredient = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(mRessource, parent,false);

        TextView positionNumber = (TextView) view.findViewById(R.id.PositionIngriedentlist);
        TextView zutatenName = (TextView)  view.findViewById(R.id.ingriedientName);
        TextView menge = (TextView)  view.findViewById(R.id.ingriedientMenge);
        TextView einheit2 = (TextView) view.findViewById(R.id.ingriedienteinheit);

        positionNumber.setText((position+1) + ". ");
        zutatenName.setText(ingredient.getName());
        menge.setText(removeTrailingZeros(ingredient.getMenge()));
        einheit2.setText(ingredient.getEinheit());

        return view;
    }
    public static String removeTrailingZeros(double d) {
        return String.valueOf(d).replaceAll("[0]*$", "").replaceAll(".$", "");
    }
}
