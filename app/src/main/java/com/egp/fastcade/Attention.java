package com.egp.fastcade;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Attention extends AppCompatActivity {

    TextView beScore;
    TextView scoreT;
    int score = 0;
    int n;
    Button[] buttons = new Button[40];
    int difficulty = 3;
    Random rand = new Random();
    Game game;
    boolean yes = false;
    StaticValues staticValues;
    int bestScore;

    AlertDialog.Builder builder;
    AlertDialog alertDialog;

    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;



    void invis() {
        for (int i = 0; i < 40; i++) {
            buttons[i].setBackground(getDrawable(R.drawable.button));
            buttons[i].setVisibility(View.INVISIBLE);
        }
    }

    View.OnClickListener loose = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            game.endGame();
        }
    };

    View.OnClickListener win = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            score++;
            if (difficulty < 25) difficulty++;
            buttons[n].setOnClickListener(loose);
            game.run();
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention);

        dbHelper = new DBHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        bestScore = dbHelper.getValue(sqLiteDatabase,StaticValues.name_attention);



        game = new Game();

        builder = new AlertDialog.Builder(Attention.this);



        /* Initialize buttons */
        {
            buttons[0] = findViewById(R.id.button1);
            buttons[1] = findViewById(R.id.button2);
            buttons[2] = findViewById(R.id.button3);
            buttons[3] = findViewById(R.id.button4);
            buttons[4] = findViewById(R.id.button5);
            buttons[5] = findViewById(R.id.button6);
            buttons[6] = findViewById(R.id.button7);
            buttons[7] = findViewById(R.id.button8);
            buttons[8] = findViewById(R.id.button9);
            buttons[9] = findViewById(R.id.button10);
            buttons[10] = findViewById(R.id.button11);
            buttons[11] = findViewById(R.id.button12);
            buttons[12] = findViewById(R.id.button13);
            buttons[13] = findViewById(R.id.button14);
            buttons[14] = findViewById(R.id.button15);
            buttons[15] = findViewById(R.id.button16);
            buttons[16] = findViewById(R.id.button17);
            buttons[17] = findViewById(R.id.button18);
            buttons[18] = findViewById(R.id.button19);
            buttons[19] = findViewById(R.id.button20);
            buttons[20] = findViewById(R.id.button21);
            buttons[21] = findViewById(R.id.button22);
            buttons[22] = findViewById(R.id.button23);
            buttons[23] = findViewById(R.id.button24);
            buttons[24] = findViewById(R.id.button25);
            buttons[25] = findViewById(R.id.button26);
            buttons[26] = findViewById(R.id.button27);
            buttons[27] = findViewById(R.id.button28);
            buttons[28] = findViewById(R.id.button29);
            buttons[29] = findViewById(R.id.button30);
            buttons[30] = findViewById(R.id.button31);
            buttons[31] = findViewById(R.id.button32);
            buttons[32] = findViewById(R.id.button33);
            buttons[33] = findViewById(R.id.button34);
            buttons[34] = findViewById(R.id.button35);
            buttons[35] = findViewById(R.id.button36);
            buttons[36] = findViewById(R.id.button37);
            buttons[37] = findViewById(R.id.button38);
            buttons[38] = findViewById(R.id.button39);
            buttons[39] = findViewById(R.id.button40);
        }
        scoreT = findViewById(R.id.score);
        beScore = findViewById(R.id.bestScore);

        for (int i = 0; i < 40; i++) {
            buttons[i].setOnClickListener(loose);
        }

        game.run();
    }

    class Time extends Thread {
        @Override
        public void run() {

            int i = score + 1;
            try {
                Thread.sleep(12000 / difficulty);

            } catch (InterruptedException e) {
            }
            if (score<i) {
                Attention.this.runOnUiThread(new Runnable() {
                    public void run() {
                        game.endGame();
                    }
                });
            }

        }
    }

    class Game {
        public void run(){
            scoreT.setText(Integer.toString(score));
            beScore.setText(Integer.toString(bestScore));
            invis();
            int[] bt = new int[difficulty];

            for (int j = 0; j < difficulty; j++) {


                bt[j] = rand.nextInt(40);
                for (int k = 0; k < j; k++) {
                    if (bt[j] == bt[k]) {
                        j--;
                    }
                }
            }
            for (int j = 0; j < bt.length; j++) {
                buttons[bt[j]].setVisibility(View.VISIBLE);
            }
            n = bt[rand.nextInt(difficulty)];
            buttons[n].setBackground(getDrawable(R.drawable.button_white));
            buttons[n].setOnClickListener(win);
            if (StaticValues.timer) {new Time().start();}
        }

        void endGame() {
            if (score> bestScore) {
                bestScore = score;
                dbHelper.updateValue(sqLiteDatabase, StaticValues.name_attention, bestScore);
            }


            invis();

            builder.setTitle("Game Over");
            builder.setMessage("Your score: " + score + "\nPlay again?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    yes = false;

                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Attention.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            alertDialog = builder.create();
            if (!yes) {
                alertDialog.show();
                yes = true;
             }
            score = 0;
            difficulty = 3;
            game.run();
        }


    }


}