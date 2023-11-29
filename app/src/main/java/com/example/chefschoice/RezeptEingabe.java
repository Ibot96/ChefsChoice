package com.example.chefschoice;

import static com.example.chefschoice.Adapter.IngredientsListAdapter.removeTrailingZeros;

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

import android.widget.LinearLayout;
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
    private IngredientsListAdapter ingredientsListAdapter;

    private Recipe editRecipe;

    private List<Ingredient> editZutatenListe;

    private boolean editFlag = false;

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

        // Bearbeiten eines bereits vorhandenen rezeptes
        Intent intent = getIntent();
        if (intent.hasExtra("id")){
            editFlag = true;
            int id = intent.getIntExtra("id",0);
            editRecipe = recipeDAO.getRecipeById(id);
            //eintragen der Daten
            inputRezeptname.setText(editRecipe.getName());
            inputBeschreibung.setText(editRecipe.getBeschreibung());
            //zuatenliste
            editZutatenListe = ingredientDAO.getIngrediantByRecipeId(id);
            showIngredientList(editZutatenListe);
        }


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
                //hide keyboard nach add button on click
                View view = getCurrentFocus();
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (manager.isActive(view)) {
                    manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                String name = inputZutatenName.getText().toString();
                String mengeText = inputZutatenMenge.getText().toString();
                Double menge = Double.valueOf(mengeText);
                String einheit = dropdown.getText().toString();
                Ingredient ingredient = new Ingredient(name, menge, einheit);
                if (editFlag){
                    editZutatenListe.add(ingredient);
                    showIngredientList(editZutatenListe);
                }else {
                    ingredientList.add(ingredient);
                    showIngredientList(ingredientList);
                }


                //eingabefelder leeren
                inputZutatenName.setText(null);
                inputZutatenMenge.setText(null);
                dropdown.setText(null);
                //schließt die eingabefenster
                findViewById(R.id.linearLayoutZutateneingabe).clearFocus();
            }else{
                Log.d("chefchoice2", "no INPUT");
            }
        });
        speichern.setOnClickListener(v -> {


                //erstellen Rezept und in Datenbank eintragen
                String name = inputRezeptname.getText().toString();
                String beschreibung = inputBeschreibung.getText().toString();
                //todo bild

                if (editFlag){
                    //todo update funktion der db auf die id
                }else {
                    //speichern in db
                    Recipe recipe = new Recipe(name,beschreibung,null);
                    long numb = recipeDAO.addRecipe(recipe);
                    ingredientDAO.addIngredients(ingredientList,numb);
                }
                //Log.d("chefchoice2", String.valueOf(numb));

                //Intent auf die Rezeptliste
                startActivity(new Intent(RezeptEingabe.this,RezeptUebersicht.class));


        });
        abbrechen.setOnClickListener(v -> {
            finish();
        });

        liste.setOnItemClickListener((parent, view, position, id) -> {
                Ingredient i = ingredientsListAdapter.getItem(position);
                String name = ingredientsListAdapter.getItem(position).getName();
                String menge = String.valueOf(ingredientsListAdapter.getItem(position).getMenge());
                String einheit = ingredientsListAdapter.getItem(position).getEinheit();

                inputZutatenName.setText(i.getName());
                inputZutatenMenge.setText(removeTrailingZeros(i.getMenge()));
                dropdown.setText(i.getEinheit());
                dropdown.dismissDropDown();
                fillDropdown();
                if (editFlag){
                    editZutatenListe.remove(i);
                }else {
                    ingredientList.remove(i);
                }
                ingredientsListAdapter.notifyDataSetChanged();
                //Log.d("chefchoice2", name + ", " + menge + " " + einheit);

        });
    }

    private void showIngredientList(List<Ingredient> list) {
        //anzeigen in der Liste
        ingredientsListAdapter = new IngredientsListAdapter(this, R.layout.ingredients_list_item, list);
        liste.setAdapter(ingredientsListAdapter);
    }


    void fillDropdown(){
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_list_item, getResources().getStringArray(R.array.einheiten));
        dropdown.setAdapter(spinnerAdapter);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

    private String removeUnwantedPrefix(String uriString) {
        // Der unerwünschte Präfix
        String unwantedPrefix = "/-1/1/content:/";

        // Überprüfe, ob der Präfix im Uri-String vorhanden ist
        if (uriString.startsWith(unwantedPrefix)) {
            // Entferne den Präfix
            return uriString.substring(unwantedPrefix.length());
        }
        //Test
        // Wenn der Präfix nicht gefunden wurde, gib den ursprünglichen String zurück
        return uriString;
    }




}
