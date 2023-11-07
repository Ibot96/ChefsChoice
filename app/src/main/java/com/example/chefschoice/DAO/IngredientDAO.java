package com.example.chefschoice.DAO;

import com.example.chefschoice.Model.Ingredient;

import java.util.List;

public interface IngredientDAO {

    public long insertIngredient(Ingredient zutat);

    public List<Ingredient> getIngrediantByRecipeId(long recipeId);

    public List<Ingredient> getIngrediantsById(int id);

    public Ingredient getIngredientByName(String name);
}
