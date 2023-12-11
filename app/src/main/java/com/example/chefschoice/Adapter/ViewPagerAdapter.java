package com.example.chefschoice.Adapter;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chefschoice.Detailansicht;
import com.example.chefschoice.LandingPage;
import com.example.chefschoice.Model.Recipe;
import com.example.chefschoice.R;
import com.example.chefschoice.RezeptUebersicht;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

   Context context;

   List<Recipe> recipes;

   List<Recipe> allrecipes;


    public ViewPagerAdapter(Context context, List<Recipe> recipes, List<Recipe> allrecipes) {
        this.context = context;
        this.recipes = recipes;
        this.allrecipes = allrecipes;
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
        if (item.getBild()!=null){
            Uri uri = Uri.parse(item.getBild());
            holder.rezeptbild.setImageURI(uri);
        }

        holder.rezeptname.setText(item.getName());
        holder.rezeptbild.setOnClickListener(v -> {
            Intent intent = new Intent(context, Detailansicht.class);
            intent.putExtra("selctedRecipe", item.getId());
            context.startActivity(intent);
        });

        holder.reload.setOnClickListener(v -> {
            Random random = new Random();
            List<Recipe> tmp = new ArrayList<>(allrecipes);
            tmp.removeAll(recipes);
            if (tmp.size()>0){
                int index = random.nextInt(tmp.size());
                recipes.set(position,tmp.get(index));
                recipes.remove(item);
                notifyDataSetChanged();
            }else {
                Toast toast = new Toast(v.getContext());
                toast.setText("Kein weiteres Rezept verfügbar");
                toast.show();
            }
        });
        holder.delete.setOnClickListener(v -> {
            recipes.remove(item);
            notifyDataSetChanged();
        });
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
