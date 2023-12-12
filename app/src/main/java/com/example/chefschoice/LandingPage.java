package com.example.chefschoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;
import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import java.util.Map;
import java.util.Random;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import com.example.chefschoice.Adapter.ViewPagerAdapter;
import com.example.chefschoice.DAO.RecipeDAOImpl;
import com.example.chefschoice.DB.DatabaseHelper;
import com.example.chefschoice.Model.Recipe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;

public class LandingPage extends AppCompatActivity{
    private List<Recipe> recipesWeek;
    private List<Recipe> allRecipes;
    private SharedPreferences preferences;
    private List<Integer> currentWeekIDs;
    private ViewPager2 viewPager;
    private FloatingActionButton addFab,createFab, listFab;
    private Boolean areFabsVisible;
    private androidx.appcompat.widget.Toolbar toolbar;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private RecipeDAOImpl recipeDAO;
    private Dialog dialog;
    private TextInputEditText inputDays;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        askPermission();

        //database initialisieren und alle rezepte holen
        databaseHelper = new DatabaseHelper(this);
        db = databaseHelper.getWritableDatabase();
        recipeDAO = new RecipeDAOImpl(db);
        allRecipes = recipeDAO.getAllRecipes();

        //initialisierung von allen objekten (viewpager, Bedienmenu, Popupdialog)
        initViewpager();
        initFABMenu();
        initDialog();

        //sharedpreferences abfragen
        preferences = getSharedPreferences("Current_Recipes", Context.MODE_PRIVATE);
        Map<String, ?> allPrefs = preferences.getAll();


        if (!allPrefs.isEmpty()){
            for (Map.Entry<String, ?> entry : allPrefs.entrySet()) {
                if (entry.getValue() instanceof Integer) {
                    Recipe recipe = recipeDAO.getRecipeById((Integer) entry.getValue());
                    if (recipe!=null){
                        currentWeekIDs.add((Integer) entry.getValue());
                        recipesWeek.add(recipe);
                    }
                }
            }
            showViewpager();
        }

    }



    private void initDialog() {
        dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.genpopup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button genButton = dialog.findViewById(R.id.generierenPopUp);
        Button cancelButton = dialog.findViewById(R.id.abbrechenPopUp);
        inputDays = dialog.findViewById(R.id.inputTage);

        genButton.setOnClickListener(v -> {
            genRecipes();
            if (!inputDays.getText().toString().equals("")
                    &&!(Integer.valueOf(inputDays.getText().toString())>recipesWeek.size())
                    &&!(Integer.valueOf(inputDays.getText().toString())==0)){
                dialog.dismiss();
            }
            if (viewPager!=null&&!dialog.isShowing()){
                viewPager.setVisibility(View.VISIBLE);
            }
            showViewpager();

        });
        cancelButton.setOnClickListener(v -> {
            dialog.dismiss();
            if (viewPager!=null){
                viewPager.setVisibility(View.VISIBLE);
            }
        });
    }
    private void initFABMenu(){
        addFab = (FloatingActionButton) findViewById(R.id.menu_add);
        createFab = (FloatingActionButton) findViewById(R.id.menu_create);
        listFab = (FloatingActionButton) findViewById(R.id.menu_list);
        FloatingActionButton menuFab = (FloatingActionButton) findViewById(R.id.menu);

        //Hide buttons
        addFab.setVisibility(View.GONE);
        createFab.setVisibility(View.GONE);
        listFab.setVisibility(View.GONE);
        areFabsVisible = false;

        menuFab.setOnClickListener(v -> toggleFABMenu());
        listFab.setOnClickListener(v -> startActivity(new Intent(LandingPage.this,RezeptUebersicht.class)));
        addFab.setOnClickListener(v -> startActivity(new Intent(LandingPage.this, RezeptEingabe.class)));
        createFab.setOnClickListener(v -> {
            if (currentWeekIDs!=null){
                if (currentWeekIDs.size()!=0){
                    viewPager.setVisibility(View.INVISIBLE);
                }
            }
            inputDays.setText("");
            dialog.show();
            toggleFABMenu();
        });

    }

    private void toggleFABMenu() {
        if(!areFabsVisible){
            //show
            addFab.show();
            createFab.show();
            listFab.show();

            areFabsVisible = true;
        }else{
            //hide
            addFab.hide();
            createFab.hide();
            listFab.hide();

            areFabsVisible = false;
        }
    }

    private void initViewpager(){
        currentWeekIDs = new ArrayList<>();
        recipesWeek = new ArrayList<>();
        viewPager = new ViewPager2(this);
        viewPager = findViewById(R.id.ViewPager);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(100,0,120,0);
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(1);
        viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

    }
    private void genRecipes() {
        String daysString = inputDays.getText().toString();

        if (!daysString.equals("")){
            int days = Integer.parseInt(daysString);
            currentWeekIDs = getRandomElements(allRecipes, days);
        }
        updateSharedPreferences();
    }

    public void updateSharedPreferences(){

        // SharedPreferences löschen
        SharedPreferences.Editor myEditor = preferences.edit();
        myEditor.clear();
        myEditor.apply();

        if(currentWeekIDs!=null){
            for (int i=0;i<currentWeekIDs.size();i++){
                myEditor.putInt("ID"+i,currentWeekIDs.get(i));
            }
        }
        myEditor.apply();
    }
    private List<Integer> getRandomElements(List<Recipe> recipes, int days ){
        List<Recipe> tmpRecipes = new ArrayList<>(recipes);
        List<Integer> tmpIDList = new ArrayList<>();
        if (days == 0){
            Toast toast = new Toast(this);
            toast.setText("Mindestens 1 eingeben");
            toast.show();
            return null;
        }
        if (days > recipes.size()) {
            Toast toast = new Toast(this);
            toast.setText("Nicht genügend Rezepte");
            toast.show();
            return null;
        }

        Random random = new Random();
        recipesWeek.clear();
        for (int i = 0; i < days; i++) {
            int randomIndex = random.nextInt(tmpRecipes.size());
            recipesWeek.add(tmpRecipes.get(randomIndex));
            tmpIDList.add(tmpRecipes.get(randomIndex).getId());
            tmpRecipes.remove(randomIndex);
        }

        return tmpIDList;
    }
    private void showViewpager() {
        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        transformer.addTransformer((page, position) -> {
            float r= 1- Math.abs(position);
            page.setScaleY(0.85f + r * 0.14f);
        });
        for (Recipe r: recipesWeek) {
            Log.d("viewpager", r.getName());
        }
        viewPager.setAdapter(new ViewPagerAdapter(this,recipesWeek, allRecipes));
        viewPager.setPageTransformer(transformer);
    }

    private void askPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 123);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 124);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 125);
        }
    }

}