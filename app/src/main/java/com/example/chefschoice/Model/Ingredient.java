package com.example.chefschoice.Model;

public class Ingredient {
    private long id;
    private String name;

    private long recipeId;

    private double menge;

    private String einheit;

    public Ingredient(long id, String name, long recipeId, double menge, String einheit) {
        this.id = id;
        this.name = name;
        this.recipeId = recipeId;
        this.menge = menge;
        this.einheit = einheit;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }

    public double getMenge() {
        return menge;
    }

    public void setMenge(double menge) {
        this.menge = menge;
    }

    public String getEinheit() {
        return einheit;
    }

    public void setEinheit(String einheit) {
        this.einheit = einheit;
    }
}
