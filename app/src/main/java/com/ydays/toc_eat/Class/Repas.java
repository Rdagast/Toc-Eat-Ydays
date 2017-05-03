package com.ydays.toc_eat.Class;

/**
 * Created by benjaminthomas on 03/05/2017.
 */

public class Repas {
    private Integer id;
    private String title;
    private String description;
    private String ingredient;
    private Integer participation;


    public Repas(Integer id, String title, String description, String ingredient, Integer participation) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.ingredient = ingredient;
        this.participation = participation;
    }

    public Repas(int id, String title, String description, String ingredient, String prix) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.ingredient = ingredient;
        this.participation = Integer.parseInt(prix);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public Integer getParticipation() {
        return participation;
    }

    public void setParticipation(Integer participation) {
        this.participation = participation;
    }
}
