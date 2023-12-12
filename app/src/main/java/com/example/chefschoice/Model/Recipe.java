package com.example.chefschoice.Model;

import java.util.Objects;

public class Recipe {

   private int id;
    private String name;

    private String beschreibung;

    private String bild;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Recipe(String name, String beschreibung, String bild) {
        this.name = name;
        this.bild = bild;
        this.beschreibung = beschreibung;
    }

    public Recipe(int id, String name, String beschreibung, String bild){
        this.id = id;
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
