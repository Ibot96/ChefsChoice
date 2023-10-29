package com.example.chefschoice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chefschoice.Adapter.ViewPagerAdapter;
import com.example.chefschoice.Model.Recipe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LandingPage extends AppCompatActivity{
    private ArrayList<Recipe> recipes;
    private ViewPager2 viewPager;

    private FloatingActionButton menuFab;
    private FloatingActionButton addFab,createFab, listFab;

    private Boolean areFabsVisible;
    private androidx.appcompat.widget.Toolbar toolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initViewpager();
        initFABMenu();





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

        menuFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
        //todo hier die weiteren onclicklistener für die buttons

        listFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingPage.this,RezeptUebersicht.class);
                startActivity(intent);
            }
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
        recipes = new ArrayList<>();
        for(int i=0;i< images.length;i++){
            Recipe recipe = new Recipe(rezeptname[i],"",null); // todo int -> byte[]
            recipes.add(recipe);
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
        viewPager.setAdapter(new ViewPagerAdapter(this,recipes));
        viewPager.setPageTransformer(transformer);

    }

    //Rufe die generierenActivity auf
    public void genTage(View v) {



    }
    //neu laden des ausgewählten Tages
    public void reloadDay(View v) {

    }

}