package com.example.chefschoice.DAO;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.chefschoice.Model.Ingredient;

import java.util.List;

public class IngredientDAOImpl implements IngredientDAO{

    private SQLiteDatabase db;

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
    public Ingredient getIngredientByName(String name) {
        Cursor cs = db.query("Zutaten", null, "name=?", new String[]{name}, null, null, null);
        if(cs != null){
            if(cs.moveToFirst()){
                long id = cs.getLong(cs.getColumnIndexOrThrow("ID"));
                String zutatenName = cs.getString((cs.getColumnIndexOrThrow("Name")));
                return new Ingredient(zutatenName, 0,null);
            }
            cs.close();
        }
        return null;
    }
    public void addIngredients(List<Ingredient> zutaten, long nextID){
        //IngredientDAOImpl ingredientDAO = new IngredientDAOImpl(db);

        for (Ingredient ingredient : zutaten) {
            //Ingredient neueZutat = new Ingredient(ingredient.getName(), nextID,ingredient.getMenge(), ingredient.getEinheit());
            ingredient.setRecipeId(nextID);
            insertIngredient(ingredient);
        }
    }
}
