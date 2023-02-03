package com.example.apptpfinal_jessicachan;

import android.content.Context;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class ListeDefisInstance {
    private static ListeDefis instance;
    private static Context mApplicationContext;

    private ListeDefisInstance() {
    }

    // Récupère ou crée une instance de la classe ListeDefis
    public static ListeDefis getInstance() {
        if (instance == null) {
            try {
                instance = recupererDefis();
            } catch (Exception e) {
                instance = new ListeDefis();
                Defi defiAnimaux = new JeuAnimaux();
                Defi defiSaisons = new JeuSaison();
                instance.ajouterDefi(defiAnimaux);
                instance.ajouterDefi(defiSaisons);
            }
        }
        return instance;
    }

    public static void setInstance(ListeDefis instance) {
        ListeDefisInstance.instance = instance;
    }

    public static void setApplicationContext(Context applicationContext) {
        mApplicationContext = applicationContext;
    }

    public static ListeDefis recupererDefis() throws Exception {
        ObjectInputStream ois = null;
        ListeDefis resultat = null;
        FileInputStream fis = mApplicationContext.openFileInput("fichier.ser");
        ois = new ObjectInputStream(fis);
        resultat = (ListeDefis) ois.readObject();
        ois.close();
        return resultat;
    }
}
