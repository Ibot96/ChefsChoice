package com.example.chefschoice.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.chefschoice.Model.Recipe;
import com.example.chefschoice.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

   Context context;

   List<Recipe> recipes;


    public ViewPagerAdapter(Context context, List<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_item_viewpager, parent, false);
        return new ViewHolder(view);
    }//gibt dem ganzen den look

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe item = recipes.get(position);
        //todo bildhinzufügen
        holder.rezeptname.setText(item.getName());
    }//ordnet werte den views zu die in dem layout file gebaut wurden basierend auf der position der recycler view

    @Override
    public int getItemCount() {
        return recipes.size();
    }//die recycler view will wissen wieviel items auf dem bildschirm sind

    public class ViewHolder extends RecyclerView.ViewHolder{
        //holt die views von dem layout file card item
        //
        ImageView rezeptbild;
        TextView rezeptname;
        ImageView reload;
        ImageView delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rezeptbild = itemView.findViewById(R.id.rezeptbild);
            rezeptname = itemView.findViewById(R.id.Rezeptname);
            reload = itemView.findViewById(R.id.reloadButton);
            delete = itemView.findViewById(R.id.deleteButton);
        }
    }
}
