package com.example.chefschoice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;
import android.Manifest;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.example.chefschoice.Adapter.ViewPagerAdapter;
import com.example.chefschoice.DB.DatabaseHelper;
import com.example.chefschoice.Model.Recipe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class LandingPage extends AppCompatActivity{
    private ArrayList<Recipe> recipesWeek;
    private ViewPager2 viewPager;

    private FloatingActionButton menuFab;
    private FloatingActionButton addFab,createFab, listFab;

    private Boolean areFabsVisible;
    private androidx.appcompat.widget.Toolbar toolbar;

    private DatabaseHelper databaseHelper;

    private List<Integer> currentWeekIDs;

    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        currentWeekIDs = new ArrayList<>();
        preferences = getSharedPreferences("Current_Recipes", Context.MODE_PRIVATE);
        initViewpager();
        initFABMenu();

        databaseHelper = new DatabaseHelper(this);
        askPermission();
    }

    private void initFABMenu(){
        addFab = (FloatingActionButton) findViewById(R.id.menu_add);
        createFab = (FloatingActionButton) findViewById(R.id.menu_create);
        listFab = (FloatingActionButton) findViewById(R.id.menu_list);
        menuFab = (FloatingActionButton) findViewById(R.id.menu);

        //Hide buttons
        addFab.setVisibility(View.GONE);
        createFab.setVisibility(View.GONE);
        listFab.setVisibility(View.GONE);
        areFabsVisible = false;

        menuFab.setOnClickListener(v -> {
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
        });

        listFab.setOnClickListener(v -> {
            startActivity(new Intent(LandingPage.this,RezeptUebersicht.class));
        });
        addFab.setOnClickListener(v -> {
            startActivity(new Intent(LandingPage.this, RezeptEingabe.class));
        });
        createFab.setOnClickListener(v -> {

        });
    }
    private void initViewpager(){
        //todo bilder müssen auf bestimmte größe angepasst werden
        viewPager = findViewById(R.id.ViewPager);
        int[] images = {
                R.drawable.saumagen,
                R.drawable.kartoffelsalat,
                R.drawable.schnitzel,
                R.drawable.steak
        };

        String[] rezeptname = {
                "Saumagen",
                "Kartoffelsalat",
                "Schnitzel",
                "Steak"
        };
        recipesWeek = new ArrayList<>();
        for(int i=0;i< images.length;i++){
            Recipe recipe = new Recipe(rezeptname[i],"",null); // todo int -> byte[]
            recipesWeek.add(recipe);
        }


        viewPager.setClipToPadding(false);
        viewPager.setPadding(100,0,120,0);
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(2);
        viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);


        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r= 1- Math.abs(position);
                page.setScaleY(0.85f + r * 0.14f);
            }
        });
        viewPager.setAdapter(new ViewPagerAdapter(this,recipesWeek));
        viewPager.setPageTransformer(transformer);

    }

    //Rufe die generierenActivity auf
    public void genTage(View v) {



    }
    //neu laden des ausgewählten Tages
    public void reloadDay(View v) {

    }

    private void askPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 123);
        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_DENIED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 124);
        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
            Log.e("ddd", "Permission");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 125);
        }
    }

}