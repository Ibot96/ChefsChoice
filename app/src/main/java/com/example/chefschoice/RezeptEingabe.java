package com.example.chefschoice;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.chefschoice.Adapter.IngredientsListAdapter;
import com.example.chefschoice.DAO.IngredientDAOImpl;
import com.example.chefschoice.DAO.RecipeDAOImpl;
import com.example.chefschoice.DB.DatabaseHelper;
import com.example.chefschoice.Model.Ingredient;
import com.example.chefschoice.Model.Recipe;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class RezeptEingabe extends AppCompatActivity {

    private AutoCompleteTextView dropdown;
    private androidx.appcompat.widget.Toolbar toolbar;

    private TextInputEditText inputRezeptname, inputZutatenName, inputZutatenMenge, inputBeschreibung;

    private List<Ingredient> ingredientList;

    private Button add, speichern, abbrechen;
    private RecipeDAOImpl recipeDAO;
    private IngredientDAOImpl ingredientDAO;
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private ListView liste;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rezept_eingabe);

        // db initialisieren
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        recipeDAO = new RecipeDAOImpl(db);
        ingredientDAO = new IngredientDAOImpl(db);

        //initialisieren der toolbar
        toolbar = (Toolbar) findViewById(R.id.rezepteingabetoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //initialisieren aller inputs und des Dropdown
        initInputs();
        fillDropdown();


    }

    private void initInputs() {
        inputRezeptname = findViewById(R.id.inputRezeptname);
        inputZutatenName = findViewById(R.id.inputZutatenName);
        inputZutatenMenge = findViewById(R.id.inputZutatenMenge);
        inputBeschreibung = findViewById(R.id.inputBeschreibung);
        dropdown = findViewById(R.id.inputZutatenEinheit);
        add = findViewById(R.id.addbutton);
        speichern = findViewById(R.id.rezeptspeichern);
        abbrechen = findViewById(R.id.rezeptabbrechen);
        liste = findViewById(R.id.Zutatenliste);
        ingredientList = new ArrayList<Ingredient>();



        //hinzufügen der Zutaten in die Liste des Rezeptes
        add.setOnClickListener(v -> {

            if(inputZutatenName.getText().length()!=0) {
                String name = inputZutatenName.getText().toString();
                String mengeText = inputZutatenMenge.getText().toString();
                Double menge = Double.valueOf(mengeText);
                String einheit = dropdown.getText().toString();
                Ingredient ingredient = new Ingredient(name, menge, einheit);
                ingredientList.add(ingredient);

                //anzeigen in der Liste
                IngredientsListAdapter ingredientsListAdapter = new IngredientsListAdapter(this, R.layout.ingredients_list_item, ingredientList);
                liste.setAdapter(ingredientsListAdapter);

                //ausgeben der Liste
               /* for (Ingredient ingredientfor: ingredientList
                     ) {
                    Log.d("chefchoice2", ingredientfor.toString());
                }*/


                //eingabefelder leeren
                inputZutatenName.setText(null);
                inputZutatenMenge.setText(null);
                dropdown.setText(null);
                //hide keyboard nach add button on click
                View view = getCurrentFocus();
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }else{
                Log.d("chefchoice2", "no INPUT");
            }

        });
        speichern.setOnClickListener(v -> {
            //erstellen Rezept und in Datenbank eintragen
            String name = inputRezeptname.getText().toString();
            String beschreibung = inputBeschreibung.getText().toString();
            //todo bild
            Recipe recipe = new Recipe(name,beschreibung,null);
            long numb = recipeDAO.addRecipe(recipe);
            ingredientDAO.addIngredients(ingredientList,numb);
            Log.d("chefchoice2", String.valueOf(numb));
            //Intent auf die Rezeptliste
            startActivity(new Intent(RezeptEingabe.this,LandingPage.class));
        });
        abbrechen.setOnClickListener(v -> {
            //intent auf die Hauptseite
            startActivity(new Intent(RezeptEingabe.this,LandingPage.class));
        });
    }



    void fillDropdown(){
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_list_item, getResources().getStringArray(R.array.einheiten));
        dropdown.setAdapter(spinnerAdapter);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), LandingPage.class);
        startActivityForResult(myIntent, 0);
        return true;
    }



}
