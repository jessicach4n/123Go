package com.example.apptpfinal_jessicachan;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Path;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

public class JeuAnimaux extends Defi {
    private final int NIV_FACILE = 10;
    private final int NIV_MOYEN = 15;
    private final int NIV_DIFFICILE = 20;

    private final Animal[] type = new Animal[3];
    private Vector<Animal> animaux = new Vector<>();
    private int limiteAnimaux = NIV_FACILE;
    private String animalACompter = "";
    private int compteJoueur = 0;

    public JeuAnimaux() {
        type[0] = new Cochon();
        type[1] = new Canard();
        type[2] = new Vache();
    }

    // Générer un certain nombre de chaque type d'animaux selon la limite
    public Vector<Animal> genererAnimaux() {
        for (Animal animal : type) {
            Random r = new Random();
            for (int i = 0; i < r.nextInt(limiteAnimaux) + 4; i++) {
                animaux.add(animal);
            }
        }
        Collections.shuffle(animaux);

        return animaux;
    }

    // Aléatoirement choisir l'animal à compter durant une partie
    public String animalACompter() {
        animalACompter = type[new Random().nextInt(3)].getNom();
        return animalACompter;
    }

    // Set la réponse du joueur
    public void setCompteJoueur(int compteJoueur) {
        this.compteJoueur = compteJoueur;
    }

    // Obtenir le nombre de chaque animal
    public HashMap<String, Integer> nbrDeChaqueAnimal() {
        HashMap<String, Integer> hashMap = new HashMap<>();
        int nbrcochon = 0;
        int nbrcanard = 0;
        int nbrvache = 0;
        for (Animal animal : animaux) {
            if (animal instanceof Cochon) {
                nbrcochon++;
            } else if (animal instanceof Canard) {
                nbrcanard++;
            } else if (animal instanceof Vache) {
                nbrvache++;
            }
        }
        hashMap.put("cochon", nbrcochon);
        hashMap.put("canard", nbrcanard);
        hashMap.put("vache", nbrvache);

        return hashMap;
    }

    // Vérifier si partie gagnée si joueur à le bon compte
    @Override
    public boolean partieGagnee() {
        HashMap<String, Integer> hashMap = nbrDeChaqueAnimal();
        if (compteJoueur == hashMap.get(animalACompter)) {
            setEstReussi(true);
            return true;
        }
        return false;
    }

    @Override
    public void reinitialiserJeu() {
        animaux.removeAllElements();
    }

    // Chercher le bon icon pour le dialog box
    public int getIcon(String animal) {
        switch (animal) {
            case "cochon":
                return R.drawable.ic_cochon;
            case "canard":
                return R.drawable.ic_canard;
            case "vache":
                return R.drawable.ic_vache;
            default:
                break;
        }
        return 0;
    }

    public void setLimiteAnimaux(int limiteAnimaux) {
        this.limiteAnimaux = limiteAnimaux;
    }

    public int getNIV_FACILE() {
        return NIV_FACILE;
    }

    public int getNIV_MOYEN() {
        return NIV_MOYEN;
    }

    public int getNIV_DIFFICILE() {
        return NIV_DIFFICILE;
    }
}
