package com.example.chefschoice.Adapter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.chefschoice.Model.Recipe;
import com.example.chefschoice.R;

import java.io.File;
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
        String bild = getItem(position).getBild();
        String beschreibung = getItem(position).getBeschreibung();

        Recipe rezept = new Recipe(name,beschreibung, bild);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent,false);


        ImageView imageView =  (ImageView) convertView.findViewById(R.id.bild);
        String imagePath = "/storage/emulated/0/Pictures/IMG_20231102_072525.jpg";



        File imgFile = new File(imagePath);

        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

           if (myBitmap != null){
               imageView.setImageBitmap(myBitmap);
               Log.e("ddd", "gefunden");
           }

        } else {
            Log.e("ddd", "File Not Found "+ imgFile);
        }



        TextView nameView = (TextView)  convertView.findViewById(R.id.text);

        nameView.setText(name);

        return convertView;
    }
}
