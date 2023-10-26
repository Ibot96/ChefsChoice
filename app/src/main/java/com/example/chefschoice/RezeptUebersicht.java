package com.example.chefschoice;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chefschoice.Adapter.RecipeListAdapter;
import com.example.chefschoice.DAO.RecipeDAO;
import com.example.chefschoice.DAO.RecipeDAOImpl;
import com.example.chefschoice.DB.DatabaseHelper;
import com.example.chefschoice.Model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RezeptUebersicht extends AppCompatActivity {

    private ListView liste;
    private RecipeDAOImpl recipeDAO;

    private ArrayList<Recipe> rezepte;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rezept_uebersicht);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        recipeDAO = new RecipeDAOImpl(db);
        rezepte = recipeDAO.getAllRecipes();

        liste = findViewById(R.id.liste);



        RecipeListAdapter adapter = new RecipeListAdapter(this, R.layout.list_adapter, rezepte);

        liste.setAdapter(adapter);
    }
}
