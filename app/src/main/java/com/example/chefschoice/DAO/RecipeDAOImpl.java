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
    private final SQLiteDatabase db;

    public RecipeDAOImpl(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public Recipe getRecipeById(int id) {
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.rawQuery("SELECT * FROM Rezepte WHERE id= ?",selectionArgs);
        if(cursor != null){
            if(cursor.moveToFirst()){
                int recipeId = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
                String beschreibung = cursor.getString(cursor.getColumnIndexOrThrow("Beschreibung"));
                String bild = cursor.getString(cursor.getColumnIndexOrThrow("Bild"));

                Recipe aktRezept = new Recipe(recipeId,name,beschreibung,bild,"Sonstiges");
                cursor.close();
                return aktRezept;
            }
        }
        cursor.close();
        return null;
    }

    @Override
    public ArrayList<Recipe> getAllRecipes() {

        Cursor cursor = db.rawQuery("SELECT * FROM Rezepte", null);
        ArrayList<Recipe> rezepte = new ArrayList<>();
        if(cursor != null){

            while(cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
                String beschreibung = cursor.getString(cursor.getColumnIndexOrThrow("Beschreibung"));
                String bild = cursor.getString(cursor.getColumnIndexOrThrow("Bild"));

                Recipe aktRezept = new Recipe(id,name,beschreibung,bild, "Sonstiges");

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
        values.put("bild", rezept.getBild());
        values.put("beschreibung", rezept.getBeschreibung());

        long newRow = db.insert("Rezepte", null, values);
        return newRow;
    }

    @Override
    public void deleteRecipe(int id) {
        db.delete("Rezepte","ID"+"=?",new String[]{String.valueOf(id)});
        db.delete("Zutaten", "RezeptID"+"=?",new String[]{String.valueOf(id)});
    }

    @Override
    public void updateRecipe(Recipe recipe) {
        ContentValues values = new ContentValues();

        values.put("Name", recipe.getName());
        values.put("Beschreibung", recipe.getBeschreibung());
        values.put("Bild", recipe.getBild());

        db.update("Rezepte", values, "ID = ?", new String[]{String.valueOf(recipe.getId())});
        /*db.close();*/
    }
}
