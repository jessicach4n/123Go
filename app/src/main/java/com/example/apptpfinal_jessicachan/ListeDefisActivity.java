package com.example.apptpfinal_jessicachan;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ListeDefisActivity extends AppCompatActivity {

    ListeDefis mListeDefis;
    Button mBtnListeDefis;
    Button mBtnFb;
    Button mBtnInfo;
    Menu mMenuObj;
    LinearLayout mDefiUn;
    LinearLayout mDefiDeux;
    ActivityResultLauncher<Intent> lanceur;
    TextView mStatusDefiUn;
    TextView mStatusDefiDeux;
    Defi mDefiAnimaux;
    Defi mDefiSaisons;
    boolean dialogDejaAffichee = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_defis);

        mBtnListeDefis = findViewById(R.id.defis);
        mBtnFb = findViewById(R.id.facebook);
        mBtnInfo = findViewById(R.id.about);
        mDefiUn = findViewById(R.id.btnDefi1);
        mDefiDeux = findViewById(R.id.btnDefi2);
        mStatusDefiUn = findViewById(R.id.status_defi1);
        mStatusDefiDeux = findViewById(R.id.status_defi2);

        mMenuObj = Menu.getInstance(this);
        Ecouteur ec = new Ecouteur();

        mBtnListeDefis.setOnClickListener(ec);
        mBtnFb.setOnClickListener(ec);
        mBtnInfo.setOnClickListener(ec);
        mDefiUn.setOnClickListener(ec);
        mDefiDeux.setOnClickListener(ec);

        lanceur = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new MonCallBack());

        mListeDefis = ListeDefisInstance.getInstance();
        mDefiAnimaux = mListeDefis.getDefi(0);
        mDefiSaisons = mListeDefis.getDefi(1);

        mStatusDefiUn.setText(mDefiAnimaux.getEtat());
        mStatusDefiDeux.setText(mDefiSaisons.getEtat());
    }

    private class MonCallBack implements ActivityResultCallback<ActivityResult> {

        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == 123) {
                mDefiAnimaux.setEstReussi(result.getData().getBooleanExtra("etat", false));
                mStatusDefiUn.setText(mDefiAnimaux.getEtat());
            }
            else if (result.getResultCode() == 321) {
                mDefiSaisons.setEstReussi(result.getData().getBooleanExtra("etat", false));
                mStatusDefiDeux.setText(mDefiSaisons.getEtat());
            }
            if (mDefiAnimaux.isReussi() && mDefiSaisons.isReussi()) {
                showCustomDialog();
            }
        }
    }

    private class Ecouteur implements View.OnClickListener {

        @Override
        public void onClick(View source) {
            if (source == mDefiUn) {
                lanceur.launch(new Intent(ListeDefisActivity.this, JeuAnimauxActivity.class));
            }
            else if (source == mDefiDeux) {
                lanceur.launch(new Intent(ListeDefisActivity.this, JeuSaisonActivity.class));
            }
            else {
                finish();
                mMenuObj.naviguerMenu(source, mBtnListeDefis, mBtnInfo, mBtnFb, null);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        serialiserDefis();
    }

    public void serialiserDefis() {
        ObjectOutputStream oos = null;
        try {
            FileOutputStream fos = this.openFileOutput("fichier.ser", Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(mListeDefis);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public ListeDefis recupererDefis() throws Exception {
//        ObjectInputStream ois = null;
//        ListeDefis resultat = null;
//        FileInputStream fis = this.openFileInput("fichier.ser");
//        ois = new ObjectInputStream(fis);
//        resultat = (ListeDefis) ois.readObject();
//        ois.close();
//        return resultat;
//    }

    public void showCustomDialog() {
        Dialog dialog = new Dialog(this, R.style.DialogStyle);
        dialog.setContentView(R.layout.bravo_dialog);
        Button btnFermer = dialog.findViewById(R.id.fermerBravo);
        btnFermer.setOnClickListener(source -> dialog.dismiss());
        if (!dialogDejaAffichee) {
            dialog.show();
            dialogDejaAffichee = true;
        }
    }

}