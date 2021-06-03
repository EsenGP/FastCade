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

public class Algebra extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;

    int score;
    int bestScore;

    TextView sc;
    TextView bestSc;

    int difficulty = 1;
    int right;

    Button b[] = new Button[10];
    TextView answer;
    TextView question;

    AlertDialog.Builder builder;
    AlertDialog alertDialog;

    boolean yes = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algebra);


        dbHelper = new DBHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();

        bestScore = dbHelper.getValue(sqLiteDatabase, StaticValues.name_math);

        question = findViewById(R.id.quest);
        answer = findViewById(R.id.answer);

        sc = findViewById(R.id.scoreM);
        bestSc = findViewById(R.id.bestScoreM);

        Button back = findViewById(R.id.back);
        Button ok = findViewById(R.id.ok);



        b[0] = findViewById(R.id.b0);
        b[1] = findViewById(R.id.b1);
        b[2] = findViewById(R.id.b2);
        b[3] = findViewById(R.id.b3);
        b[4] = findViewById(R.id.b4);
        b[5] = findViewById(R.id.b5);
        b[6] = findViewById(R.id.b6);
        b[7] = findViewById(R.id.b7);
        b[8] = findViewById(R.id.b8);
        b[9] = findViewById(R.id.b9);

        for (int i = 0; i < 10; i++) {
            onClickAdd(b[i]);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answer.getText().equals("")){
                    answer.setText(answer.getText().toString().substring(0,answer.getText().toString().length()-1));
                }
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proverka();
            }
        });

        builder = new AlertDialog.Builder(Algebra.this);

        Game();

    }

    void onClickAdd(final Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer.setText(answer.getText().toString() + button.getText().toString());
            }
        });
    }

    void Game(){
        answer.setText("");
        sc.setText(Integer.toString(score));
        bestSc.setText(Integer.toString(bestScore));
        Random rand = new Random();
        int firstValue = rand.nextInt(100*difficulty);
        int secondValue = rand.nextInt(10 * difficulty);
      String text = "";
        switch (rand.nextInt(5)){
            case 0: text = "+";
            right =  firstValue + secondValue;
            break;
            case 1: text = "-";
            right =  firstValue - secondValue;
            break;
            case 2: text = "*";
            right =  firstValue * secondValue;
            break;
            case 3: text = "/";
            right =  firstValue / secondValue;
            break;
            case 4: text = "^";
            firstValue = rand.nextInt(2*difficulty);
            secondValue = rand.nextInt(2*difficulty);
            right = (int) Math.pow(firstValue, secondValue);
            break;
            default: right = 0;
            break;
        }
        question.setText(firstValue + text + secondValue);
        if (StaticValues.timer) {new Time().start();}

    }
    void proverka(){

        if(answer.getText().toString().equals(Integer.toString(right))){
            win();
        }
        else {
            loose();
        }
    }

    void win(){
        score++;
        if (difficulty <15)
        difficulty++;


        Game();
    }
    void loose(){
        if(score>bestScore){
            bestScore = score;
            dbHelper.updateValue(sqLiteDatabase, StaticValues.name_math, bestScore);
        }
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
                Intent intent = new Intent(Algebra.this, MainActivity.class);
                startActivity(intent);
            }
        });
        alertDialog = builder.create();
        if (!yes) {
            alertDialog.show();
            yes = true;
        }
        score = 0;
        difficulty = 1;
        Game();

    }
    class Time extends Thread {
        @Override
        public void run() {

            int i = score + 1;
            try {
                Thread.sleep(60000 / (difficulty / 5 > 0 ? difficulty / 5 : 1 ));

            } catch (InterruptedException e) {
            }
            if (score<i) {
                Algebra.this.runOnUiThread(new Runnable() {
                    public void run() {
                        loose();
                    }
                });
            }

        }
    }
}