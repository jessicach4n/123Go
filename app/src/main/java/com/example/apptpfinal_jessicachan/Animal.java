package com.example.apptpfinal_jessicachan;

import java.io.Serializable;

// Classe parent de tous les animaux (Canard, Cochon, Vache) du jeu "JeuAnimaux"
public abstract class Animal implements Serializable {
    private String nom;
    private int image;

    public Animal(String nom, int image) {
        this.nom = nom;
        this.image = image;
    }

    public String getNom() {
        return nom;
    }

    public int getImage() {
        return image;
    }

}
