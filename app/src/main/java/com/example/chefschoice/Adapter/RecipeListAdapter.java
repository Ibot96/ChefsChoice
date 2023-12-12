package com.example.chefschoice.Adapter;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.chefschoice.Detailansicht;
import com.example.chefschoice.Model.Recipe;
import com.example.chefschoice.R;
import com.example.chefschoice.RezeptEingabe;

import java.io.File;
import java.util.ArrayList;

public class RecipeListAdapter extends ArrayAdapter<Recipe> {

    private static Context mContext;
    int mResource;
    private OnButtonClickListener onButtonClickListener;

    public void setOnButtonClickListener(OnButtonClickListener listener){
        this.onButtonClickListener = listener;
    }
    public RecipeListAdapter( Context context, int resource, ArrayList<Recipe> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getName();
        String bild = getItem(position).getBild();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent,false);

        ImageButton deleteButton = convertView.findViewById(R.id.deleteRecipe);
        ImageButton editButton = convertView.findViewById(R.id.editRecipe);

        editButton.setOnClickListener(v -> {
            if (onButtonClickListener != null){
                onButtonClickListener.onEditButtonClick(getItem(position).getId());
            }
        });
        deleteButton.setOnClickListener(v -> {
            if (onButtonClickListener != null){
                onButtonClickListener.onDeleteButtonClick(getItem(position).getId());
                remove(getItem(position));
                notifyDataSetChanged();
            }
        });


        ImageView imageView =  convertView.findViewById(R.id.bild);


       if (bild != null){
           imageView.setImageURI(Uri.parse(bild));
       }else {
            Log.e("Err", "File Not Found");
        }

        TextView nameView = (TextView)  convertView.findViewById(R.id.text);

        nameView.setText(name);

        return convertView;
    }

    public Uri getBitmapForView(String path){
        String imagePath = path;
        Uri uri = null;


        File imgFile = new File(imagePath);

        if (imgFile.exists()) {
          uri = Uri.fromFile(imgFile);
        }
            return  uri;
        }
}
