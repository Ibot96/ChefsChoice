package com.example.chefschoice.Model;

import java.util.List;

public class Recipe {


    private String name;

    private String beschreibung;

    private String bild;


    public Recipe(String name, String beschreibung, String bild) {
        this.name = name;
        this.bild = bild;
        this.beschreibung = beschreibung;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBild() {
        return bild;
    }

    public void setBild(String bild) {
        this.bild = bild;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
}
