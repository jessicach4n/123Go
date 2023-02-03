package com.example.apptpfinal_jessicachan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InfoActivity extends AppCompatActivity {
    Button mBtnListeDefis;
    Button mBtnFb;
    Button mBtnInfo;
    Menu mMenuObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        mBtnListeDefis = findViewById(R.id.defis);
        mBtnFb = findViewById(R.id.facebook);
        mBtnInfo = findViewById(R.id.about);

        mMenuObj = Menu.getInstance(this);
        Ecouteur ec = new Ecouteur();

        mBtnListeDefis.setOnClickListener(ec);
        mBtnFb.setOnClickListener(ec);
        mBtnInfo.setOnClickListener(ec);
    }

    private class Ecouteur implements View.OnClickListener {

        @Override
        public void onClick(View source) {
            finish();
            mMenuObj.naviguerMenu(source, mBtnListeDefis, mBtnInfo, mBtnFb, null);
        }
    }
}