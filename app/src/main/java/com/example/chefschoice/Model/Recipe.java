package com.example.chefschoice.Model;

import java.util.List;

public class Recipe {


    private String name;
    private List<String> zutaten;
    private String beschreibung;


    public Recipe(String name, List<String> zutaten, String beschreibung) {
        this.name = name;
        this.zutaten = zutaten;
        this.beschreibung = beschreibung;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getZutaten() {
        return zutaten;
    }

    public void setZutaten(List<String> zutaten) {
        this.zutaten = zutaten;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
}
