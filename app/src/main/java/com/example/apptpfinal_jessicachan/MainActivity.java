package com.example.apptpfinal_jessicachan;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class MainActivity extends AppCompatActivity {
    Button mBtnListeDefis;
    Button mBtnFb;
    Button mBtnInfo;
    Button mScanQR;
    LinearLayout mMenuLayout;
    ActivityResultLauncher<ScanOptions> barcodeLauncher;
    Menu mMenuObj;
    ActivityResultLauncher<Intent> jeulanceur;
    Defi mDefiAnimaux;
    Defi mDefiSaisons;
    ListeDefis mListeDefis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListeDefisInstance.setApplicationContext(getApplicationContext());
        mBtnListeDefis = findViewById(R.id.defis);
        mBtnFb = findViewById(R.id.facebook);
        mBtnInfo = findViewById(R.id.about);
        mScanQR = findViewById(R.id.scan);
        mMenuLayout = findViewById(R.id.menu);
        mMenuObj = Menu.getInstance(this);

        Ecouteur ec = new Ecouteur();

        mBtnListeDefis.setOnClickListener(ec);
        mBtnFb.setOnClickListener(ec);
        mBtnInfo.setOnClickListener(ec);
        mScanQR.setOnClickListener(ec);

        mListeDefis = ListeDefisInstance.getInstance();
        mDefiAnimaux = mListeDefis.getDefi(0);
        mDefiSaisons = mListeDefis.getDefi(1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 666);
        }

        jeulanceur = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new MonCallBack());
        barcodeLauncher = registerForActivityResult(new ScanContract(),
                result -> {
                    if(result.getContents() == null) {
                        Toast.makeText(MainActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
                    } else {
                        if (result.getContents().equals("Les animaux")) {
                            jeulanceur.launch(new Intent(MainActivity.this, JeuAnimauxActivity.class));
                        }
                        else if (result.getContents().equals("Les saisons")) {
                            jeulanceur.launch(new Intent(MainActivity.this, JeuSaisonActivity.class));

                        }
                        else {
                            Toast.makeText(MainActivity.this, "Essayer un autre code QR", Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }

    private class MonCallBack implements ActivityResultCallback<ActivityResult> {

        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == 123) {
                mDefiAnimaux.setEstReussi(result.getData().getBooleanExtra("etat", false));
            }
            else if (result.getResultCode() == 321) {
                mDefiSaisons.setEstReussi(result.getData().getBooleanExtra("etat", false));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[]permissions, int [] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 666)
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_LONG).show();
    }

    private class Ecouteur implements View.OnClickListener {

        @Override
        public void onClick(View source) {
            mMenuObj.naviguerMenu(source, mBtnListeDefis, mBtnInfo, mBtnFb, mScanQR);
        }
    }
}