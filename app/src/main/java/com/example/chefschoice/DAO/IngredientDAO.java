package com.example.chefschoice.DAO;

import com.example.chefschoice.Model.Ingredient;

public interface IngredientDAO {

    public long insertIngredient(Ingredient zutat, long recipeID);

    public Ingredient getIngredientByName(String name);
}
