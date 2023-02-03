package com.example.apptpfinal_jessicachan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.Vector;

public class JeuAnimauxActivity extends AppCompatActivity {
    Button mBtnMoins;
    Button mBtnPlus;
    TextView mCompteur;
    TextView mAnimalACompter;
    JeuAnimaux mJeuAnimaux;
    ObjectAnimator mAnimator;
    AnimatorSet mAnimatorSet;
    Vector<Animal> mAnimaux;
    Vector<ImageView> mImageViews;
    ConstraintLayout mZone;
    ObjectAnimator[] mTab;
    TextView mTimer;
    MonThread mThread;
    Handler mHandler;

    Path mPath;

    int compteAnimaux = 0;
    int i = 0;
    int temps = 15;
    String animalACompter;
    int idIconAnimal;
    int dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu_animaux);

        mJeuAnimaux = (JeuAnimaux) ListeDefisInstance.getInstance().getDefi(0);
        mJeuAnimaux.reinitialiserJeu();
        mImageViews = new Vector<>();

        mBtnMoins = findViewById(R.id.moins);
        mBtnPlus = findViewById(R.id.plus);
        mCompteur = findViewById(R.id.compteur);
        mZone = findViewById(R.id.zoneJeuAnimaux);
        mTimer = findViewById(R.id.timer);
        mAnimalACompter = findViewById(R.id.animalACompter);

        animalACompter = mJeuAnimaux.animalACompter();
        mAnimalACompter.setText(animalACompter);
        idIconAnimal = mJeuAnimaux.getIcon(animalACompter);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        dp = (dm.widthPixels / (Resources.getSystem().getDisplayMetrics().densityDpi/160)) + 300;

        Ecouteur ec = new Ecouteur();

        mBtnMoins.setOnClickListener(ec);
        mBtnPlus.setOnClickListener(ec);
        mCompteur.setOnClickListener(ec);

        new AlertDialog.Builder(this)
                .setTitle("Les animaux : Compte le nombre de " + animalACompter + "!")
                .setMessage("Sers-toi des boutons ' - ' et ' + ' pour y arriver!")
                .setIcon(idIconAnimal)
                .setNegativeButton("Annuler", (dialog, which) -> {
                    finish();
                })
                .setPositiveButton("Jouer", (dialog, which) -> {
                showDialogNiveaux();
        })
        .show();

    }

    private class MonThread implements Runnable{

        @Override
        public void run() {
            // Écoulement du temps restant au jeu
            if (temps > 0) {
                temps--;
            }
            else {
                temps = 0;
                gameOver(mJeuAnimaux.partieGagnee());
                return;
            }
            mTimer.setText(temps + " s");
            mHandler.postDelayed(mThread, 1000);
        }
    }

    public void gameOver (Boolean isWinner) {
        if (isWinner) {
            showCustomDialog();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Oops !")
                    .setMessage("Ce n'est pas la bonne réponse!")
                    .setNegativeButton("Abandonner", (dialog, which) -> {
                        Intent retour = new Intent();
                        retour.putExtra("etat", false);
                        setResult(123, retour);
                        finish();
                    })
                    .setPositiveButton("Réessayer", (dialog, which) -> {
                        Intent i = new Intent(JeuAnimauxActivity.this, JeuAnimauxActivity.class);
                        startActivity(i);
                        finish();
                    })
                    .show();
        }
    }

    private class Ecouteur implements View.OnClickListener {

        @Override
        public void onClick(View source) {
            if (source == mBtnMoins) {
                if (compteAnimaux > 0) {
                    compteAnimaux -= 1;
                }
            }
            else {
                compteAnimaux += 1;
            }
            mJeuAnimaux.setCompteJoueur(compteAnimaux);
            mCompteur.setText(String.valueOf(compteAnimaux));
        }
    }

    public void showCustomDialog() {
        Dialog dialog = new Dialog(this, R.style.DialogStyle);
        dialog.setContentView(R.layout.custom_bienjoue_dialog);
        Button btnOk = dialog.findViewById(R.id.btnTerminer);
        TextView description = dialog.findViewById(R.id.description);
        description.setText("Montre ta force en faisant 10 pushups !");
        ImageView image = dialog.findViewById(R.id.touchepied);
        image.setImageResource(R.drawable.pushup);
        btnOk.setOnClickListener(source -> {
            Intent retour = new Intent();
            retour.putExtra("etat", true);
            setResult(123, retour);
            finish();
            dialog.dismiss();
        });
        dialog.show();
    }

    public void showDialogNiveaux() {
        Dialog dialog = new Dialog(this, R.style.DialogStyle);
        dialog.setContentView(R.layout.custom_niveaux_animaux);
        Button facile = dialog.findViewById(R.id.btnFacile);
        Button moyen = dialog.findViewById(R.id.btnMoyen);
        Button difficile = dialog.findViewById(R.id.btnDifficile);

        facile.setOnClickListener(source -> {
            creerJeu();
            dialog.dismiss();
        });
        moyen.setOnClickListener(source -> {
            mJeuAnimaux.setLimiteAnimaux(mJeuAnimaux.getNIV_MOYEN());
            creerJeu();
            dialog.dismiss();
        });
        difficile.setOnClickListener(source -> {
            mJeuAnimaux.setLimiteAnimaux(mJeuAnimaux.getNIV_DIFFICILE());
            creerJeu();
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mHandler != null) {
            mHandler.removeCallbacks(mThread);
        }
    }

    // Appelée une fois le niveau est choisi
    public void creerJeu() {
        mAnimaux = mJeuAnimaux.genererAnimaux();
        for (Animal animal : mAnimaux) {
            Random r = new Random();
            ImageView image = new ImageView(this);
            image.setImageResource(animal.getImage());
            image.setY(-210);
            image.setX(r.nextInt(dp));
            mZone.addView(image);
            mImageViews.add(image);
        }

        mThread = new MonThread();
        mHandler = new Handler();
        mHandler.post(mThread);

        mTab = new ObjectAnimator[mImageViews.size()];

        for (ImageView img : mImageViews) {
            mPath = new Path();
            mPath.moveTo(img.getX(), img.getY());
            Random r = new Random();
            mPath.lineTo(r.nextInt(dp), 2000);
            mAnimator = ObjectAnimator.ofFloat(img, View.X, View.Y, mPath);
            mAnimator.setDuration(r.nextInt(15000) + 5000);
            mAnimator.setInterpolator(new AccelerateInterpolator());
            mTab[i] = mAnimator;
            mTab[i].start();
            i++;
        }
        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playSequentially(mTab);
        mAnimatorSet.setDuration(3000);
    }
}