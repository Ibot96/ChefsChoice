package com.example.chefschoice.DAO;

import com.example.chefschoice.Model.Ingredient;

import java.util.List;

public interface IngredientDAO {

    long insertIngredient(Ingredient zutat);

    List<Ingredient> getIngrediantByRecipeId(long recipeId);
    void addIngredients(List<Ingredient> zutaten, long recipeID);

    void updateIngredients(List<Ingredient> zutaten, long recipeID);
}
