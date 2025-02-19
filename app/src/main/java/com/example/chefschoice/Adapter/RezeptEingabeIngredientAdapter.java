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

public class RezeptEingabeIngredientAdapter extends ArrayAdapter<Ingredient> {

    private static Context mContext;

    int mRessource;

    public RezeptEingabeIngredientAdapter(@NonNull Context context, int resource, @NonNull List<Ingredient> objects) {
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

        TextView positionNumber = view.findViewById(R.id.PositionIngriedentlist);
        TextView zutatenName = view.findViewById(R.id.ingriedientName);
        TextView menge = view.findViewById(R.id.ingriedientMenge);
        TextView einheit2 = view.findViewById(R.id.ingriedienteinheit);

        positionNumber.setText((position+1) + ". ");
        zutatenName.setText(ingredient.getName());

        if (sindNurNullenHintermKomma(ingredient.getMenge())){
            menge.setText(String.valueOf((int) ingredient.getMenge()));
        }else{
            menge.setText(String.valueOf(ingredient.getMenge()));
        }
        einheit2.setText(ingredient.getEinheit());

        return view;
    }

    public static boolean sindNurNullenHintermKomma(double zahl) {
        //wenn keine stellen hinter dem komma sind wird true ausgegeben
        String[] teile = Double.toString(zahl).split("\\.");
        if (teile.length > 1) {
            String dezimalteil = teile[1];
            return dezimalteil.matches("0*");
        }
        return true;
    }
}
