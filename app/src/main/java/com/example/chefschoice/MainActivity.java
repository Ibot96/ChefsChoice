package com.example.chefschoice;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chefschoice.DAO.IngredientDAO;
import com.example.chefschoice.DAO.IngredientDAOImpl;
import com.example.chefschoice.DAO.RecipeDAO;
import com.example.chefschoice.DAO.RecipeDAOImpl;
import com.example.chefschoice.DB.DatabaseHelper;
import com.example.chefschoice.Model.Ingredient;
import com.example.chefschoice.Model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextRecipeName;
    private EditText editTextIngredients;
    private EditText editTextDescription;
    private Button buttonSaveRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        editTextRecipeName = findViewById(R.id.editTextRecipeName);
        editTextIngredients = findViewById(R.id.editTextIngredients);
        editTextDescription = findViewById(R.id.editTextDescription);
        buttonSaveRecipe = findViewById(R.id.buttonSaveRecipe);

        RecipeDAOImpl rezeptDAO = new RecipeDAOImpl(db);

        buttonSaveRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipeName = editTextRecipeName.getText().toString();
                String ingredients = editTextIngredients.getText().toString();
                String description = editTextDescription.getText().toString();

                String[] ingredientsArray =     ingredients.split(",");
                List<Long> ingredientsList = getIngredientList(ingredientsArray, db);
                Recipe rezept = new Recipe(recipeName,ingredientsList,description);

                rezeptDAO.addRecipe(rezept);
            }
        });
    }

    public List<Long> getIngredientList(String[] zutaten, SQLiteDatabase db){
        IngredientDAOImpl ingredientDAO = new IngredientDAOImpl(db);
        List<Long> ingredientsList = new ArrayList<>();
        for (String ingredient : zutaten) {
            Ingredient aktZutat = ingredientDAO.getIngredientByName(ingredient.toUpperCase());

                Ingredient neueZutat = new Ingredient(0, ingredient, 0, 0, null);
                long id = ingredientDAO.insertIngredient(neueZutat);

                ingredientsList.add(id);

        }
        return ingredientsList;
    }
}