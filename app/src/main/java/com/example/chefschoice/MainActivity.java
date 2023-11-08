package com.example.chefschoice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextRecipeName;
    private EditText editTextIngredients;
    private EditText editTextDescription;
    private Button buttonSaveRecipe;

    private Button buttonShow;
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
        buttonShow = findViewById(R.id.buttonshow);
        RecipeDAOImpl rezeptDAO = new RecipeDAOImpl(db);



        buttonSaveRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipeName = editTextRecipeName.getText().toString();
                String ingredients = editTextIngredients.getText().toString();
                String description = editTextDescription.getText().toString();

                String[] ingredientsArray =     ingredients.split(",");
                long nextId = rezeptDAO.getNextRecipeId();

                //addIngredients(ingredientsArray, db, nextId);
                Recipe rezept = new Recipe(recipeName,description, null);

                rezeptDAO.addRecipe(rezept);
            }
        });

        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RezeptUebersicht.class);
                startActivity(intent);
            }
        });
    }




}
