package com.example.boguhnindustries.reactiontesttest;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Hauptmenue extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hauptmenue);


    }

    public void startGame(View view){
        Toast.makeText(this, "Spiel startet wenn sie das rote Quadrat berühren!", Toast.LENGTH_SHORT).show();


       Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



    public void options(View view){


        Intent intent = new Intent(this,Options.class);
        startActivity(intent);
    }
}
