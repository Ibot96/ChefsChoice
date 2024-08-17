package com.example.chefschoice.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ChefsChoice.db";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("CREATE TABLE IF NOT EXISTS Rezepte ("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + "Name TEXT,"
                + "Bild TEXT,"
                + "Beschreibung TEXT);");


        db.execSQL("CREATE TABLE IF NOT EXISTS Zutaten ("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "RezeptID INTEGER,"
                + "Name TEXT,"
                + "Menge REAL,"
                + "Einheit TEXT,"  +
                "FOREIGN KEY (RezeptID) REFERENCES Rezepte(ID) ON DELETE CASCADE);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        if (oldV < 2) { // Erhöhen Sie die Version, wenn Sie Änderungen vornehmen
            db.execSQL("CREATE TABLE Kategorien (" +
                    "    ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "    Name TEXT" +
                    ");");
            db.execSQL("ALTER TABLE Rezepte ADD COLUMN Kategorie Integer");

            // Optionale Datenmigration: Alle Rezepte der Kategorie "Sonstiges" zuweisen
            db.execSQL("Insert into Kategorien (name) values ('Sonstiges')");
            db.execSQL("UPDATE Rezepte SET Kategorie = 1");
        }
    }
}
