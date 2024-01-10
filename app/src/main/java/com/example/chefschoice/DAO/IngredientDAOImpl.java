package com.example.chefschoice.DAO;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.chefschoice.Model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientDAOImpl implements IngredientDAO{

    private final SQLiteDatabase db;

    public IngredientDAOImpl(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public long insertIngredient(Ingredient zutat) {
        ContentValues values = new ContentValues();
        values.put("Name", zutat.getName());
        values.put("Menge", zutat.getMenge());
        values.put("Einheit", zutat.getEinheit());
        values.put("RezeptID", zutat.getRecipeId());
        long id = db.insert("Zutaten", null, values);
        zutat.setId(id);
        return id;
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
    public void addIngredients(List<Ingredient> zutaten, long recipeID){
        //IngredientDAOImpl ingredientDAO = new IngredientDAOImpl(db);

        for (Ingredient ingredient : zutaten) {
            //Ingredient neueZutat = new Ingredient(ingredient.getName(), nextID,ingredient.getMenge(), ingredient.getEinheit());
            ingredient.setRecipeId(recipeID);
            insertIngredient(ingredient);
        }
    }

    @Override
    public void updateIngredients(List<Ingredient> zutaten, long recipeID) {

        db.delete("Zutaten", "RezeptID"+"=?",new String[]{String.valueOf(recipeID)});
        addIngredients(zutaten, recipeID);

        /*for (Ingredient i : zutaten) {
            ContentValues values = new ContentValues();
            values.put("Name", i.getName());
            values.put("Menge", i.getMenge());
            values.put("Einheit", i.getEinheit());

            db.update("Zutaten", values, "ID = ?", new String[]{String.valueOf(i.getId())});
        }
        db.close();
        */
    }
}
