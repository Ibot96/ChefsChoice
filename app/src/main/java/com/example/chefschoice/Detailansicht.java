package com.example.chefschoice;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.chefschoice.Adapter.DetailAnsichtIngrediantAdapter;
import com.example.chefschoice.DAO.IngredientDAOImpl;
import com.example.chefschoice.DAO.RecipeDAOImpl;
import com.example.chefschoice.DB.DatabaseHelper;
import com.example.chefschoice.Model.Ingredient;
import com.example.chefschoice.Model.Recipe;

import java.io.File;
import java.util.List;

public class Detailansicht extends AppCompatActivity {


    private androidx.appcompat.widget.Toolbar toolbar;

    private TextView txtName;

    private ImageView imView;
    private Recipe aktRezept;
    private List<Ingredient> zutatenListe;

    private ImageButton editButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_page);
        askPermission();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        RecipeDAOImpl recipeDAO = new RecipeDAOImpl(db);
        IngredientDAOImpl ingredientDAO = new IngredientDAOImpl(db);

        txtName = findViewById(R.id.txtRezeptname);
        imView = findViewById(R.id.imViewGericht);

        toolbar = (Toolbar) findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        int id = getIntent().getIntExtra("selctedRecipe", 0);
        aktRezept = recipeDAO.getRecipeById(id);

        String name = aktRezept.getName();
        String imgPath = aktRezept.getBild();
        long rezeptId = aktRezept.getId();
        //Log.e("Pfad", imgPath);
        txtName.setText(name);

        try {
            imView.setImageURI(Uri.parse(imgPath));

        } catch (Exception e){
            Log.d("chefchoice2", e.toString());
        }

        ListView liste = findViewById(R.id.detailListe);

        zutatenListe = ingredientDAO.getIngrediantByRecipeId(id);

        DetailAnsichtIngrediantAdapter adapter = new DetailAnsichtIngrediantAdapter(this, zutatenListe);

        liste.setAdapter(adapter);

        TextView beschreibung = findViewById(R.id.rezeptBeschriebung);

        String beschreibungsText = aktRezept.getBeschreibung();
        Log.d("text", beschreibungsText);

        beschreibung.setText(beschreibungsText);
        editButton = findViewById(R.id.editRecipedetail);
        editButton.setOnClickListener(v -> {
            Intent i = new Intent(Detailansicht.this,RezeptEingabe.class);
            i.putExtra("id",aktRezept.getId());
            startActivity(i);
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

    public Bitmap getImageForView(String path){
        String imagePath = path;
        Bitmap imgBitmap;


        File imgFile = new File(imagePath);

        if (imgFile.exists()) {
            imgBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        } else {
            imgBitmap = null;
        }
        return  imgBitmap;
    }
    private void askPermission(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            Log.e("ddd", "Permission");
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, 123);
        }

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_DENIED){
            Log.e("ddd", "Permission");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 123);
        }
    }


}
