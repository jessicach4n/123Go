package com.example.apptpfinal_jessicachan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class JeuSaisonActivity extends AppCompatActivity {
    LinearLayout mTags;
    LinearLayout mImgEte;
    LinearLayout mImgAutomne;
    LinearLayout mImgHiver;
    LinearLayout mImgPrintemps;
    TextView mTagEte;
    TextView mTagAutomne;
    TextView mTagPrintemps;
    TextView mTagHiver;
    Context mContext;
    JeuSaison mJeuSaison;
    Chronometer mChronometer;
    TextView mScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu_saison);

        mContext = this;
        mTags = findViewById(R.id.tags);
        mImgEte = findViewById(R.id.ete);
        mImgAutomne = findViewById(R.id.automne);
        mImgHiver = findViewById(R.id.hiver);
        mImgPrintemps = findViewById(R.id.printemps);

        mTagEte = findViewById(R.id.tagEte);
        mTagAutomne = findViewById(R.id.tagAutomne);
        mTagHiver = findViewById(R.id.tagHiver);
        mTagPrintemps = findViewById(R.id.tagPrintemps);

        mScore = findViewById(R.id.bestScore);

        mJeuSaison = (JeuSaison) ListeDefisInstance.getInstance().getDefi(1);
        mJeuSaison.reinitialiserJeu();
        mScore.setText("Meilleur temps : " + mJeuSaison.getScore() + "s");

        Ecouteur ec = new Ecouteur();

        mImgEte.setOnDragListener(ec);
        mImgAutomne.setOnDragListener(ec);
        mImgHiver.setOnDragListener(ec);
        mImgPrintemps.setOnDragListener(ec);

        for (int i = 0; i < mTags.getChildCount(); i++) {
            LinearLayout enfant = (LinearLayout) mTags.getChildAt(i);
            for (int j = 0; j < enfant.getChildCount(); j++) {
                View texte = enfant.getChildAt(j);
                texte.setOnTouchListener(ec);
            }
        }

        new AlertDialog.Builder(this)
                .setTitle("Les saisons : Connais-tu tes saisons?")
                .setMessage("Aide-moi à associer chaque saison avec sa bonne image!")
                .setNegativeButton("Annuler", (dialog, which) -> {
                    finish();
                })
                .setPositiveButton("Jouer", (dialog, which) -> {
                    mChronometer = findViewById(R.id.chronometer);
                    mChronometer.setBase(SystemClock.elapsedRealtime());
                    mChronometer.start();
                })
                .show();
    }

    public class Ecouteur implements View.OnTouchListener, View.OnDragListener {

        @Override
        public boolean onTouch(View source, MotionEvent event) {
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(source);
            int sdkVersion = Build.VERSION.SDK_INT;
            if (sdkVersion <= 24)
                source.startDrag(null, shadowBuilder, source, 0);
            else
                source.startDragAndDrop(null, shadowBuilder, source, 0);
            source.setVisibility(View.INVISIBLE);
            return true;
        }

        @Override
        public boolean onDrag(View source, DragEvent event) {
            View texte = (View) event.getLocalState();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    texte.setVisibility(View.VISIBLE);
                    for (int i = 0; i < mTags.getChildCount(); i++) {
                        LinearLayout enfant = (LinearLayout) mTags.getChildAt(i);
                        if (enfant.getChildCount() == 0) {
                            mJeuSaison.setReponseEte(mTagEte.getParent(), mImgEte);
                            mJeuSaison.setReponseAutomne(mTagAutomne.getParent(), mImgAutomne);
                            mJeuSaison.setReponseHiver(mTagHiver.getParent(), mImgHiver);
                            mJeuSaison.setReponsePrintemps(mTagPrintemps.getParent(), mImgPrintemps);
                            if (mJeuSaison.partieGagnee()) {
                                mJeuSaison.setScore(SystemClock.elapsedRealtime() - mChronometer.getBase());
                                showCustomDialog();
                            }
                        }
                    }
                    break;
                case DragEvent.ACTION_DROP:
                    LinearLayout parent = (LinearLayout) texte.getParent();
                    LinearLayout image = (LinearLayout) source;
                    parent.removeView(texte);
                    image.addView(texte);
                    break;
                default:
                    break;
            }
            return true;
        }
    }

    public void showCustomDialog() {
        Dialog dialog = new Dialog(this, R.style.DialogStyle);
        dialog.setContentView(R.layout.custom_bienjoue_dialog);
        Button btnOk = dialog.findViewById(R.id.btnTerminer);
        TextView description = dialog.findViewById(R.id.description);
        description.setText("Assis-toi au sol et tente de toucher à tes peids en gardant tes jambes droites!");
        ImageView image = dialog.findViewById(R.id.touchepied);
        image.setImageResource(R.drawable.touchepied);
        btnOk.setOnClickListener(source -> {
            Intent retour = new Intent();
            retour.putExtra("etat", true);
            setResult(321, retour);
            finish();
            dialog.dismiss();
        });
        dialog.show();
    }
}