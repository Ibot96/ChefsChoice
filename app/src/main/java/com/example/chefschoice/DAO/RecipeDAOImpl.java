package com.example.chefschoice.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.example.chefschoice.DB.DatabaseHelper;
import com.example.chefschoice.Model.Recipe;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class RecipeDAOImpl implements RecipeDAO{
    private SQLiteDatabase db;

    public RecipeDAOImpl(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public Recipe getRecipe(int id) {
        return null;
    }

    @Override
    public ArrayList<Recipe> getAllRecipes() {
        Cursor cursor = db.rawQuery("SELECT * FROM Rezepte", null);
        ArrayList<Recipe> rezepte = new ArrayList<>();
        if(cursor != null){

            while(cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
                String beschreibung = cursor.getString(cursor.getColumnIndexOrThrow("Beschreibung"));
                String bild = cursor.getString(cursor.getColumnIndexOrThrow("Bild"));

                Recipe aktRezept = new Recipe(name,beschreibung,bild);

                rezepte.add(aktRezept);
            }
        }
        return rezepte;
    }

    @Override
    public long addRecipe(Recipe rezept) {
        ContentValues values = new ContentValues();
        values.put("name", rezept.getName());
        //byte[] image = getImageAsByteArray("C:\\Users\\JanLudwig\\OneDrive\\Desktop\\Pfannkuchen.png");
        //values.put("bild", image);
        values.put("beschreibung", rezept.getBeschreibung());

        long newRow = db.insert("Rezepte", null, values);
        return newRow;
    }

    @Override
    public void deleteRecipe(int id) {

    }

    @Override
    public long getNextRecipeId() {
        Cursor cursor = db.rawQuery("SELECT MAX(id) +1 FROM Rezepte", null);

        long nextId = 1;

        if(cursor != null){
            if(cursor.moveToFirst()){
                nextId = cursor.getLong(0);
            }
            cursor.close();
        }
        return nextId;
    }

    public byte[] getImageAsByteArray(String path){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Bitmap bitmap = BitmapFactory.decodeFile(path);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] image = stream.toByteArray();
        return image;
    }

}
