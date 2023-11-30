package com.example.kopapirollo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;


public class MainActivity extends AppCompatActivity {


    private int draws = 0;
    private int playerLives = 3;
    private int computersLives = 3;

    private int playerScore = 0;
    private int computerScore = 0;
    int playerChoice;
    int computerChoice;
    private Random random;


    private ImageView playerChoiceImageView;
    private ImageView computerChoiceImageView;
    private TextView resultsTextView;
    private LinearLayout livesLayoutPlayer;
    private LinearLayout livesLayoutComputer;
    private TextView textViewDrawsVariable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void onButtonClick(View view) {
        int resourceId;
            if (view.getId() == R.id.buttonScissors) {
                resourceId = R.drawable.s;
                playerChoiceImageView.setImageResource(resourceId);
                playerChoice = 2;
            } else if (view.getId() == R.id.buttonPaper) {
                resourceId = R.drawable.paper;
                playerChoiceImageView.setImageResource(resourceId);
                playerChoice = 1;
            } else if (view.getId() == R.id.buttonRock) {
                resourceId = R.drawable.rock;
                playerChoiceImageView.setImageResource(resourceId);
                playerChoice = 0;
            }
        Game();

    }

    public void Game(){

        if (playerLives > 0 && computersLives > 0) {
            computerChoice = random.nextInt(3); // 0: kő, 1: papír, 2: olló
            setComputerChoiceImage(computerChoice);
            if(computerChoice==playerChoice){
                draws++;
                textViewDrawsVariable.setText(String.valueOf(draws));
            }  else if ((computerChoice == 0 && playerChoice == 1) ||
                    (computerChoice == 1 && playerChoice == 2) ||
                    (computerChoice == 2 && playerChoice == 0)) {
                // A játékos nyer, csökkentjük a gép életeit
                computersLives--;
                playerScore++;
                // Frissítjük a szív képeket a gép életeinél
                updateHeartImagesComputer(playerLives,computersLives,livesLayoutComputer);
                //Eredmény kiírása
                resultsTextView.setText("Eredmény: Ember: " + String.valueOf(playerScore)  + " Gép: " + String.valueOf(computerScore));

            } else {
                // A gép nyer, csökkentjük a játékos életeit
                playerLives--;
                computerScore++;
                // Frissítjük a szív képeket a játékos életeinél
                updateHeartImagesPlayer(playerLives,computersLives,livesLayoutPlayer);
                //Eredmény kiírása
                resultsTextView.setText("Eredmény: Ember: " + String.valueOf(playerScore)  + " Gép: " + String.valueOf(computerScore));
            }
        }

        // Ellenőrizzük, hogy nyert e valaki
        if (playerLives == 0 || computersLives == 0 ) {
                showEndGameDialog();
        }
    }

    private void setComputerChoiceImage(int choice) {
        int resourceId;
        if (choice == 0) {
            resourceId = R.drawable.rock;
        } else if (choice == 1) {
            resourceId = R.drawable.paper;
        } else {
            resourceId = R.drawable.s;
        }
        computerChoiceImageView.setImageResource(resourceId);
    }

    private void showEndGameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(WinnerCheck())
                .setMessage("Szeretnél új játékot kezdeni?")
                .setPositiveButton("Igen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setDefaultValues();
                        // Értesítés bezárása
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Nem", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Játék vége, kilépés az alkalmazásból
                        finish();
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void updateHeartImagesPlayer(int playerLives, int computersLives, LinearLayout livesLayoutPlayer) {
        // Pontszám alapján frissítjük a szív képeket
        if (playerLives == 3 && computersLives== 3) {
            // Döntetlen esetén nem változik meg a szív képe
        } else if (playerLives == 2) {
            // Csak a játékos pontszáma 2, egy szív változik meg
            ((ImageView) livesLayoutPlayer.getChildAt(1)).setImageResource(R.drawable.heart1);
            ((ImageView) livesLayoutPlayer.getChildAt(2)).setImageResource(R.drawable.heart2);
            ((ImageView) livesLayoutPlayer.getChildAt(3)).setImageResource(R.drawable.heart2);

        } else if (playerLives == 1) {
            // Csak a játékos pontszáma 1, két szív változik meg
            ((ImageView) livesLayoutPlayer.getChildAt(1)).setImageResource(R.drawable.heart1);
            ((ImageView) livesLayoutPlayer.getChildAt(2)).setImageResource(R.drawable.heart1);
            ((ImageView) livesLayoutPlayer.getChildAt(3)).setImageResource(R.drawable.heart2);
        } else if (playerLives == 0) {
            // A játékosnak nincs több élete, mindhárom szív megváltozik
            ((ImageView) livesLayoutPlayer.getChildAt(1)).setImageResource(R.drawable.heart1);
            ((ImageView) livesLayoutPlayer.getChildAt(2)).setImageResource(R.drawable.heart1);
            ((ImageView) livesLayoutPlayer.getChildAt(3)).setImageResource(R.drawable.heart1);
        }

    }

    public void updateHeartImagesComputer(int computersLives, int playerLives, LinearLayout livesLayoutComputer){
        // Pontszám alapján frissítjük a szív képeket
        if (playerLives == 3 && computersLives== 3) {
            // Döntetlen esetén nem változik meg a szív képe
        } else if (computersLives == 2) {
            // Csak a computer pontszáma 2, egy szív változik meg
            ((ImageView) livesLayoutComputer.getChildAt(0)).setImageResource(R.drawable.heart1);
            ((ImageView) livesLayoutComputer.getChildAt(1)).setImageResource(R.drawable.heart2);
            ((ImageView) livesLayoutComputer.getChildAt(2)).setImageResource(R.drawable.heart2);

        } else if (computersLives == 1) {
            // Csak a computer pontszáma 1, két szív változik meg
            ((ImageView) livesLayoutComputer.getChildAt(0)).setImageResource(R.drawable.heart1);
            ((ImageView) livesLayoutComputer.getChildAt(1)).setImageResource(R.drawable.heart1);
            ((ImageView) livesLayoutComputer.getChildAt(2)).setImageResource(R.drawable.heart2);
        } else if (computersLives == 0) {
            // A computernek nincs több élete, mindhárom szív megváltozik
            ((ImageView) livesLayoutComputer.getChildAt(0)).setImageResource(R.drawable.heart1);
            ((ImageView) livesLayoutComputer.getChildAt(1)).setImageResource(R.drawable.heart1);
            ((ImageView) livesLayoutComputer.getChildAt(2)).setImageResource(R.drawable.heart1);
        }
    }

    public void setDefaultValues(){
        // Új játék kezdése, változók inicializálása
        playerLives = 3;
        computersLives = 3;
        computerScore = 0;
        playerScore = 0;
        draws=0;
        textViewDrawsVariable.setText(String.valueOf(draws));
        resultsTextView.setText("Eredmény: Ember: " + String.valueOf(playerScore)  + " Gép: " + String.valueOf(computerScore));

        // Új játék kezdése esetén frissíti az életek és választások képeit
        // A játékos
        ((ImageView) livesLayoutPlayer.getChildAt(1)).setImageResource(R.drawable.heart2);
        ((ImageView) livesLayoutPlayer.getChildAt(2)).setImageResource(R.drawable.heart2);
        ((ImageView) livesLayoutPlayer.getChildAt(3)).setImageResource(R.drawable.heart2);
        // A computernek
        ((ImageView) livesLayoutComputer.getChildAt(0)).setImageResource(R.drawable.heart2);
        ((ImageView) livesLayoutComputer.getChildAt(1)).setImageResource(R.drawable.heart2);
        ((ImageView) livesLayoutComputer.getChildAt(2)).setImageResource(R.drawable.heart2);


        playerChoiceImageView.setImageResource(R.drawable.rock);
        computerChoiceImageView.setImageResource(R.drawable.rock);
    }

    public String WinnerCheck(){
        String result;
        if(playerScore> computerScore){
            result="Győzelem";
        }
        else{
            result = "Vereség";
        }
        return result;
    }

    public void init(){
        playerChoiceImageView = findViewById(R.id.imageViewPlayerChoice);
        computerChoiceImageView = findViewById(R.id.imageViewComputerChoice);
        resultsTextView = findViewById(R.id.textViewResults);
        livesLayoutPlayer = findViewById(R.id.livesLayoutPlayer);
        livesLayoutComputer = findViewById(R.id.livesLayoutComputer);
        textViewDrawsVariable = findViewById(R.id.textViewDrawsVariable);
        random = new Random();

    }
}