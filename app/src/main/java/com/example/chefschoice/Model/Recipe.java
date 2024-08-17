package com.example.chefschoice.Model;

import java.util.Objects;

public class Recipe {

   private int id;
    private String name;

    private String beschreibung;

    private String bild;
    private String kategorie;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Recipe(String name, String beschreibung, String bild, String kategorie) {
        this.name = name;
        this.bild = bild;
        this.beschreibung = beschreibung;
        this.kategorie = kategorie;
    }

    public Recipe(int id, String name, String beschreibung, String bild, String kategorie){
        this.id = id;
        this.name = name;
        this.bild = bild;
        this.beschreibung = beschreibung;
        this.kategorie = kategorie;
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
    public String getKategorie() {
        return kategorie;
    }
    public void setKategorie(String kategorie) {
        this.kategorie = kategorie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return id == recipe.id && Objects.equals(name, recipe.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", beschreibung='" + beschreibung + '\'' +
                ", bild='" + bild + '\'' +
                '}';
    }
}
