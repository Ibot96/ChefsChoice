package com.example.chefschoice.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.example.chefschoice.DB.DatabaseHelper;
import com.example.chefschoice.Model.Recipe;

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
    public List<Recipe> getAllRecipes() {
        return null;
    }

    @Override
    public long addRecipe(Recipe rezept) {
        ContentValues values = new ContentValues();
        values.put("name", rezept.getName());

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

}
