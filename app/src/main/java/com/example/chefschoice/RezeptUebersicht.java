package com.example.chefschoice;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.chefschoice.Adapter.OnButtonClickListener;
import com.example.chefschoice.Adapter.RecipeListAdapter;
import com.example.chefschoice.DAO.RecipeDAOImpl;
import com.example.chefschoice.DB.DatabaseHelper;
import com.example.chefschoice.Model.Recipe;

import java.util.ArrayList;

public class RezeptUebersicht extends AppCompatActivity implements OnButtonClickListener {

    private ListView liste;
    private RecipeDAOImpl recipeDAO;

    private ArrayList<Recipe> rezepte;
    private androidx.appcompat.widget.Toolbar toolbar;
    private ImageButton zuRezepteingabe;
    private RecipeListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rezept_uebersicht);
        toolbar = (Toolbar) findViewById(R.id.toolbarUebersicht);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        zuRezepteingabe= toolbar.findViewById(R.id.zuRezepteingabe);
        askPermission();
        zuRezepteingabe.setOnClickListener(view -> {
            startActivity(new Intent(RezeptUebersicht.this, RezeptEingabe.class));
        });

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        recipeDAO = new RecipeDAOImpl(db);
        rezepte = recipeDAO.getAllRecipes();

        liste = findViewById(R.id.liste);



        adapter = new RecipeListAdapter(this, R.layout.recipelistadapter, rezepte);
        adapter.setOnButtonClickListener(this);
        liste.setAdapter(adapter);

        liste.setOnItemClickListener((parent, view, position, id) -> {
            Recipe seletedRecipe = (Recipe) liste.getItemAtPosition(position);
            int recipeId = seletedRecipe.getId();

            Intent intent = new Intent(RezeptUebersicht.this, Detailansicht.class);
            intent.putExtra("selctedRecipe", recipeId);
            startActivity(intent);
        });

    }
    public boolean onOptionsItemSelected(MenuItem item){
        startActivity(new Intent(RezeptUebersicht.this, LandingPage.class));
        return true;
    }

    @Override
    public void onEditButtonClick(int itemId) {
        Intent intent = new Intent(RezeptUebersicht.this, RezeptEingabe.class);
        intent.putExtra("id", itemId);
        startActivity(intent);
    }

    @Override
    public void onDeleteButtonClick(int itemId) {
        recipeDAO.deleteRecipe(itemId);
    }

    private void askPermission(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, 123);
        }

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_DENIED){

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_MEDIA_IMAGES}, 124);
        }

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
            Log.e("ddd", "Permission");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 125);
        }
    }

}

