package com.example.chefschoice;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import com.example.chefschoice.Adapter.RezeptEingabeIngredientAdapter;
import com.example.chefschoice.DAO.IngredientDAOImpl;
import com.example.chefschoice.DAO.RecipeDAOImpl;
import com.example.chefschoice.DB.DatabaseHelper;
import com.example.chefschoice.Model.Ingredient;
import com.example.chefschoice.Model.Recipe;
import com.google.android.material.textfield.TextInputEditText;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RezeptEingabe extends AppCompatActivity {

    private AutoCompleteTextView dropdown;
    private androidx.appcompat.widget.Toolbar toolbar;
    private TextInputEditText inputRezeptname, inputZutatenName, inputZutatenMenge, inputBeschreibung;
    private ImageView inputBild;
    private List<Ingredient> ingredientList;
    private String imagePath;
    private Button add, speichern, abbrechen;
    private RecipeDAOImpl recipeDAO;
    private IngredientDAOImpl ingredientDAO;
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private ListView liste;
    private RezeptEingabeIngredientAdapter rezeptEingabeIngredientAdapter;
    private Recipe editRecipe;

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
        imagePath = null;
        //initialisieren der toolbar
        toolbar = findViewById(R.id.rezepteingabetoolbar);
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
            if (editRecipe.getBild()!=null){
                inputBild.setImageURI(Uri.parse(editRecipe.getBild()));
                imagePath = String.valueOf(Uri.parse(editRecipe.getBild()));
            }
            //zutatenliste
            ingredientList = ingredientDAO.getIngrediantByRecipeId(id);
            showIngredientList(ingredientList);
        }
        if (savedInstanceState != null){
            onRestoreInstanceState(savedInstanceState);
            showIngredientList(ingredientList);
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2 && data != null){
            Uri selectedImage = data.getData();
            Log.d("UriTest", String.valueOf(selectedImage));
            File photFile;
            Uri photoUri;
            try {
                photFile = getImageFile();

                photFile.setReadable(true);
                photFile.setWritable(true);
                photoUri = FileProvider.getUriForFile(RezeptEingabe.this, "com.example.chefschoice.fileprovider", photFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            copyImage(this,selectedImage,photoUri);
            inputBild.setImageURI(selectedImage);
            imagePath = photoUri.toString();

        } else if (requestCode == 1) {

            inputBild.setImageURI(Uri.parse(imagePath));
        }



    }

    private File getImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    private void initInputs() {
        inputRezeptname = findViewById(R.id.inputRezeptname);
        inputBild = findViewById(R.id.imageButton);
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

                //hide keyboard nach add button on click
                View view = getCurrentFocus();
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (manager.isActive(view)) {
                    manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                String name = inputZutatenName.getText().toString();
                String mengeText = inputZutatenMenge.getText().toString();
                String einheit = dropdown.getText().toString();

                if (!(name.equals("") || mengeText.equals("") || einheit.equals(""))){
                    double menge = Double.parseDouble(mengeText);

                    Ingredient ingredient = new Ingredient(name, menge, einheit);
                    ingredientList.add(ingredient);
                    showIngredientList(ingredientList);
                }else {
                    Toast toast = new Toast(this);
                    toast.setText("Alles ausfüllen bitte");
                    toast.show();
                }
                //eingabefelder leeren
                inputZutatenName.setText(null);
                inputZutatenMenge.setText(null);
                dropdown.setText(null);
                //schließt die eingabefenster
                findViewById(R.id.linearLayoutZutateneingabe).clearFocus();
        });
        speichern.setOnClickListener(v -> {
            //erstellen Rezept und in Datenbank eintragen
            String name = inputRezeptname.getText().toString();
            String beschreibung = inputBeschreibung.getText().toString();
            String bild = imagePath;

                if (editFlag){
                    editRecipe.setBeschreibung(beschreibung);
                    editRecipe.setBild(imagePath);
                    editRecipe.setName(name);
                    ingredientDAO.updateIngredients(ingredientList, editRecipe.getId());
                    recipeDAO.updateRecipe(editRecipe);
                }else {
                    //speichern in db
                    Recipe recipe = new Recipe(name,beschreibung,bild);
                    long recipeID = recipeDAO.addRecipe(recipe);
                    ingredientDAO.addIngredients(ingredientList,recipeID);
                }
                //Intent auf die Rezeptliste
                startActivity(new Intent(RezeptEingabe.this,RezeptUebersicht.class));


        });
        abbrechen.setOnClickListener(v -> {
            finish();
        });

        liste.setOnItemClickListener((parent, view, position, id) -> {
                Ingredient i = rezeptEingabeIngredientAdapter.getItem(position);
                inputZutatenName.setText(i.getName());
                inputZutatenMenge.setText(String.valueOf(i.getMenge()));
                dropdown.setText(i.getEinheit());
                dropdown.dismissDropDown();
                fillDropdown();
                ingredientList.remove(i);
                rezeptEingabeIngredientAdapter.notifyDataSetChanged();
        });
        inputBild.setOnClickListener(v -> initDialog());
    }

    private void initDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.input_bild_dialog);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageButton gallery = dialog.findViewById(R.id.intentGallery);
        ImageButton cam = dialog.findViewById(R.id.intentCamera);

        gallery.setOnClickListener(v -> {
            Toast toast = new Toast(this);
            toast.setText("Galerie");
            toast.show();

            Intent fotoAuswaehlenIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(fotoAuswaehlenIntent,2);
            dialog.dismiss();
        });

        cam.setOnClickListener(v -> {
            Toast toast = new Toast(this);
            toast.setText("Kamera");
            toast.show();
            Intent fotoMachenIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File photFile;
            Uri photoUri;
            try {
                photFile = getImageFile();
                photFile.setReadable(true);
                photFile.setWritable(true);
                photoUri = FileProvider.getUriForFile(RezeptEingabe.this, "com.example.chefschoice.fileprovider", photFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            imagePath = photoUri.toString();
            Log.d("catch", imagePath);
            fotoMachenIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(fotoMachenIntent,1);
            dialog.dismiss();
        });
        dialog.show();
    }

    //anzeigen der übergebenen Liste
    private void showIngredientList(List<Ingredient> list) {
        //anzeigen in der Liste
        rezeptEingabeIngredientAdapter = new RezeptEingabeIngredientAdapter(this, R.layout.recipe_ingredient_input_list, list);
        liste.setAdapter(rezeptEingabeIngredientAdapter);
    }

    //fühlt die Einheitenliste
    void fillDropdown(){
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_list_item, getResources().getStringArray(R.array.einheiten));
        dropdown.setAdapter(spinnerAdapter);
    }

    //zurückbutton der Toolbar geht immer zur zuletzt geöffneten seite
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

    public static void copyImage(Context context, Uri sourceUri, Uri destinationUri) {
        try {
            // Öffne InputStream für die Quelldatei
            InputStream inputStream = context.getContentResolver().openInputStream(sourceUri);

            // Öffne OutputStream für die Zieldatei
            OutputStream outputStream = context.getContentResolver().openOutputStream(destinationUri);

            // Kopiere die Daten von InputStream zu OutputStream
            copyStream(inputStream, outputStream);

            // Schließe die Streams
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyStream(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

}
