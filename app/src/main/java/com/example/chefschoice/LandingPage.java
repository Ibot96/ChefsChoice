package com.example.chefschoice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LandingPage extends AppCompatActivity{
    ArrayList<ViewPagerItem> viewPagerItems;
    ViewPager2 viewPager;

    FloatingActionButton menuFab;
    FloatingActionButton addFab,createFab, listFab;

    Boolean areFabsVisible;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);
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
    }
    private void initViewpager(){
        viewPager = findViewById(R.id.ViewPager);
        int[] images = {
                R.drawable.saumagen,
                R.drawable.kartoffelsalat,
                R.drawable.schnitzel,
                R.drawable.steak
        };
        String[] rezeptname = {
                "saumagen",
                "kartoffelsalat",
                "schnitzel",
                "steak"
        };
        viewPagerItems = new ArrayList<>();
        for(int i=0;i< images.length;i++){
            ViewPagerItem viewPagerItem = new ViewPagerItem(images[i],rezeptname[i], null );
            viewPagerItems.add(viewPagerItem);
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
        viewPager.setAdapter(new ViewPagerAdapter(this,viewPagerItems));
        viewPager.setPageTransformer(transformer);

    }

    //Rufe die generierenActivity auf
    public void genTage(View v) {

    }
    //neu laden des ausgewählten Tages
    public void reloadDay(View v) {

    }

}