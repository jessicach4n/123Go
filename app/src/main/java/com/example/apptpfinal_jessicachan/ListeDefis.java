package com.example.apptpfinal_jessicachan;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

public class ListeDefis implements Serializable {
    private Vector<Defi> mDefis;

    public ListeDefis() {
        mDefis = new Vector<>();
    }

    public Defi getDefi(int index) {
        return mDefis.get(index);
    }

    public void ajouterDefi(Defi defi) {
        Boolean dejaAchete = false;
        if (mDefis.isEmpty()) {
            mDefis.add(defi);
        }
        else {
            for (int i = 0; i < mDefis.size(); i++) {
                if (mDefis.get(i) == defi) {
                    dejaAchete = true;
                }
            }
            if (!dejaAchete) {
                mDefis.add(defi);
            }
        }
    }

    @Override
    public String toString() {
        return "ListeDefis{" +
                "mDefis=" + mDefis +
                '}';
    }


}
