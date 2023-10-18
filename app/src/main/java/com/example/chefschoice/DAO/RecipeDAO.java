package com.example.chefschoice.DAO;

import com.example.chefschoice.Model.Recipe;

import java.util.List;

public interface RecipeDAO {

    Recipe getRecipe(int id);
    List<Recipe> getAllRecipes();
    void addRecipe(Recipe rezept);
    void deleteRecipe(int id);

}
