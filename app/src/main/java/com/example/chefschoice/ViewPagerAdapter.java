package com.example.chefschoice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

   Context context;
   ArrayList<ViewPagerItem> viewPagerItems;

    public ViewPagerAdapter(Context context, ArrayList<ViewPagerItem> viewPagerItems) {
        this.context = context;
        this.viewPagerItems = viewPagerItems;
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
        ViewPagerItem item = viewPagerItems.get(position);

        holder.imageView.setImageResource(item.imageID);
        holder.rezeptname.setText(item.rezeptName);
    }//ordnet werte den views zu die in dem layout file gebaut wurden basierend auf der position der recycler view

    @Override
    public int getItemCount() {
        return viewPagerItems.size();
    }//die recycler view will wissen wieviel items auf dem bildschirm sind

    public class ViewHolder extends RecyclerView.ViewHolder{
        //holt die views von dem layout file card item
        //
        ImageView imageView;
        TextView rezeptname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.rezeptbild);
            rezeptname = itemView.findViewById(R.id.Rezeptname);

        }
    }
}
