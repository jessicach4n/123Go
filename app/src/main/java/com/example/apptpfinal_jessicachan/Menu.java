package com.example.apptpfinal_jessicachan;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import com.journeyapps.barcodescanner.ScanOptions;

// Menu du bas se retrouvant sur chaque activité sauf les jeux
public class Menu {
    private static Menu instance;
    private MainActivity mMainActivity;
    private Context mContext;

    private Menu(Context context) {
        this.mContext = context;
        mMainActivity = (MainActivity) context;
    }

    public static Menu getInstance(Context context) {
        if (instance == null) {
            instance = new Menu(context);
        }
        return instance;
    }

    public void naviguerMenu(View source, Button btnListeDefis, Button btnInfo, Button btnFb, Button scanQR) {
        Intent i = null;
        if (source == btnListeDefis) {
            i = new Intent(mContext, ListeDefisActivity.class);
            mMainActivity.startActivity(i, ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, mMainActivity.mMenuLayout, "menu").toBundle());
        }
        else if (source == btnInfo){
            i = new Intent(mContext, InfoActivity.class);
            mMainActivity.startActivity(i, ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, mMainActivity.mMenuLayout, "menu").toBundle());
        }
        else if (source == btnFb){
            i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Initiative123GoRDP"));
            mMainActivity.startActivity(i);
        }
        else if (source == scanQR) {
//            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            ScanOptions options = new ScanOptions();
            options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
            options.setPrompt("Centrer le code QR pour le scanner");
            mMainActivity.barcodeLauncher.launch(options);
        }
    }

}
