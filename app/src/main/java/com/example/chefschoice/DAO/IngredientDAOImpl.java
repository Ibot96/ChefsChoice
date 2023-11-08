package com.example.chefschoice.DAO;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.chefschoice.Model.Ingredient;
import com.example.chefschoice.Model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class IngredientDAOImpl implements IngredientDAO{

    private SQLiteDatabase db;

    public IngredientDAOImpl(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public long insertIngredient(Ingredient zutat) {
        ContentValues values = new ContentValues();
        values.put("name", zutat.getName());
        values.put("RezeptId", zutat.getRecipeId());
        long id = db.insert("Zutaten", null, values);
        zutat.setId(id);
        return id;
    }

    @Override
    public Ingredient getIngredientByName(String name) {
        Cursor cs = db.query("Zutaten", null, "name=?", new String[]{name}, null, null, null);
        if(cs != null){
            if(cs.moveToFirst()){

                String zutatenName = cs.getString((cs.getColumnIndexOrThrow("Name")));
                return new Ingredient( zutatenName, 0 ,0 ,null);
            }
            cs.close();
        }
        return null;
    }

    @Override
    public List<Ingredient> getIngrediantsById(int id) {
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.rawQuery("SELECT * FROM Zutaten", selectionArgs);
        List<Ingredient> zutaten = new ArrayList<>();
        if(cursor != null){

            while(cursor.moveToNext()){

                String name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
                Double menge = cursor.getDouble(cursor.getColumnIndexOrThrow("Menge"));
                String einheit = cursor.getString(cursor.getColumnIndexOrThrow("Einheit"));

                Ingredient aktZutat = new Ingredient(name,menge, einheit);

                zutaten.add(aktZutat);
            }
        }
        return zutaten;
    }


    @Override
    public List<Ingredient> getIngrediantByRecipeId(long recipeId) {
        List<Ingredient> zutaten = new ArrayList<>();
        Cursor cs = db.query("Zutaten", null, "RezeptID=?", new String[]{String.valueOf(recipeId)}, null, null, null);
        if(cs != null){

            while(cs.moveToNext()){
                String name = cs.getString(cs.getColumnIndexOrThrow("Name"));
                Double menge = cs.getDouble(cs.getColumnIndexOrThrow("Menge"));
                String einheit = cs.getString(cs.getColumnIndexOrThrow("Einheit"));

                Ingredient zutat = new Ingredient(name,menge,einheit);
                zutaten.add(zutat);

            }
            cs.close();
        }
        return zutaten;
    }
}
