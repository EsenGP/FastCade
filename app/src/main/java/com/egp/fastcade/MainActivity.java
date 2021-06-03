package com.egp.fastcade;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    public SQLiteDatabase database;


            //Create var's

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch sw;
    ImageView attent, algebr, imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



                    //Initialize Switch and ImageView's
        sw = findViewById(R.id.switchOnOff);
        attent = findViewById(R.id.atten);
        algebr = findViewById(R.id.algebr);
        imageView = findViewById(R.id.imageView);

            // Click on Attention
    attent.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, Attention.class);
        startActivity(intent);
        }
    });
                // Click on Algebra
    algebr.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, Algebra.class);
        startActivity(intent);
        }
    });



                // Click on Switch
    sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (sw.isChecked()) {
                StaticValues.timer = true;
                imageView.setImageResource(R.drawable.timer_red_without);
            } else {
                StaticValues.timer = false;
                imageView.setImageResource(R.drawable.timer_ciane_without);
            }
        }
    });

    }
}