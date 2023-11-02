package com.example.chefschoice.DAO;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.chefschoice.Model.Ingredient;

public class IngredientDAOImpl implements IngredientDAO{

    private SQLiteDatabase db;

    public IngredientDAOImpl(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public long insertIngredient(Ingredient zutat, long recipeID) {
        ContentValues values = new ContentValues();
        values.put("name", zutat.getName());
        values.put("RezeptId", recipeID);
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
                return new Ingredient(id, zutatenName, 0 ,0 ,null);
            }
            cs.close();
        }
        return null;
    }
}
