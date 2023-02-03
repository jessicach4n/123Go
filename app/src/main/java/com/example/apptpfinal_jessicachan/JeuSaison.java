package com.example.apptpfinal_jessicachan;

import android.content.Context;
import android.view.View;
import android.view.ViewParent;
import android.widget.LinearLayout;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Vector;

public class JeuSaison extends Defi {
    private boolean mReponseEte = false;
    private boolean mReponseAutomne = false;
    private boolean mReponseHiver = false;
    private boolean mReponsePrintemps = false;
    private Vector<Long> scores;

    public JeuSaison() {
        scores = new Vector();
    }

    @Override
    public void reinitialiserJeu() {
        mReponseEte = false;
        mReponseAutomne = false;
        mReponseHiver = false;
        mReponsePrintemps = false;
    }


    public void setReponseEte(ViewParent reponseEte, LinearLayout image) {
        // Vérifier si le mot est dans le bon linear layout
        if (reponseEte == image) {
            mReponseEte = true;
        }
    }

    public void setReponseAutomne(ViewParent reponseEte, LinearLayout image) {
        // Vérifier si le mot est dans le bon linear layout
        if (reponseEte == image) {
            mReponseAutomne = true;
        }
    }

    public void setReponseHiver(ViewParent reponseEte, LinearLayout image) {
        // Vérifier si le mot est dans le bon linear layout
        if (reponseEte == image) {
            mReponseHiver = true;
        }
    }

    public void setReponsePrintemps(ViewParent reponseEte, LinearLayout image) {
        // Vérifier si le mot est dans le bon linear layout
        if (reponseEte == image) {
            mReponsePrintemps = true;
        }
    }

    // Partie gagné si toutes les réponses sont bonnes
    @Override
    public boolean partieGagnee() {
        setEstReussi(true);
        return mReponseEte && mReponseAutomne && mReponseHiver && mReponsePrintemps;
    }

    // score du joueur selon le temps (meilleur temps en secondes)
    public long getScore() {
        if (!scores.isEmpty()) {
            long tmp = scores.get(0);
            for (long i : scores) {
                if (i < tmp) {
                    tmp = i;
                }
            }
            return tmp/1000;
        }
        else {
            return 0;
        }
    }

    public void setScore(long score) {
        scores.add(score);
    }

}
