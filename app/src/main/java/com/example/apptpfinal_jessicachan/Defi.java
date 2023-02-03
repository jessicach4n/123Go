package com.example.apptpfinal_jessicachan;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

public abstract class Defi implements Serializable {
    private boolean mEstReussi;

    public Defi() {
        mEstReussi = false;
    }

    public void setEstReussi(boolean estReussi) {
        if (!mEstReussi) {
            mEstReussi = estReussi;
        }
    }

    public abstract boolean partieGagnee();

    public abstract void reinitialiserJeu();

    public boolean isReussi() {
        return mEstReussi;
    }

    public String getEtat() {
        if (mEstReussi) {
            return "Complété";
        }
        return "À faire";
    }
}
