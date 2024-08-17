package com.example.chefschoice.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.chefschoice.Model.Kategorie;
import com.example.chefschoice.Model.Recipe;

import java.util.ArrayList;

import java.util.List;

public class KategorieDAOImpl implements KategorieDAO{

    private final SQLiteDatabase db;

    public KategorieDAOImpl(SQLiteDatabase db) {
        this.db = db;
    }


    @Override
    public Kategorie getKategorieById(int id) {
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.rawQuery("SELECT * FROM Kategorien WHERE id= ?",selectionArgs);
        if(cursor != null){
            if(cursor.moveToFirst()){
                int recipeId = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));


                Kategorie aktKategorie = new Kategorie(recipeId,name);
                cursor.close();
                return aktKategorie;
            }
        }
        cursor.close();
        return null;
    }

    @Override
    public List<Kategorie> getAllKategorien() {
        Cursor cursor = db.rawQuery("SELECT * FROM Kategorien", null);
        ArrayList<Kategorie> kategorien = new ArrayList<>();

        if(cursor != null){
            while(cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
                Kategorie kategorie = new Kategorie(id, name);

                kategorien.add(kategorie);
            }
            cursor.close();
        }
        return kategorien;
    }

    @Override
    public long addKategorie(Kategorie kategorie) {
        ContentValues values = new ContentValues();
        values.put("Name", kategorie.getName());

        return db.insert("Kategorien", null, values);
    }

    @Override
    public void deleteKategorie(int id) {
        db.delete("Kategorien","ID"+"=?",new String[]{String.valueOf(id)});
    }

    @Override
    public void updateKategorie(Kategorie kategorie) {
        ContentValues values = new ContentValues();
        values.put("Name", kategorie.getName());


        db.update("Kategorien", values, "ID = ?", new String[]{String.valueOf(kategorie.getId())});
    }
}
