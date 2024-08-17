package com.example.chefschoice.DAO;

import com.example.chefschoice.Model.Kategorie;

import java.util.List;

public interface KategorieDAO {
    Kategorie getKategorieById(int id);
    List<Kategorie> getAllKategorien();
    long addKategorie(Kategorie kategorie);
    void deleteKategorie(int id);
    void updateKategorie(Kategorie kategorie);
}
