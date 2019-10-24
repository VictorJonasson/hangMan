package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class playGame extends AppCompatActivity {
    StringBuilder builder;
    TextView tv_rightAnswer;
    int counter = 2;
    String randomGeneratedWord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        ImageView ivStart = findViewById(R.id.gallowView);
        ivStart.setImageResource(R.drawable.gallow1);
        tv_rightAnswer = findViewById(R.id.right_answer);

        randomGeneratedWord = generateRandomWord();
        Log.d("Victor", "Random ord genererat: " + randomGeneratedWord);

        String hiddenword = hideWord(randomGeneratedWord);
        tv_rightAnswer.setText(hiddenword);
        Log.d("victor", "test" + hiddenword);


    }

    public void gallowIncrementButtonClick() {

        ImageView iv = findViewById(R.id.gallowView);
        //hämta id från bildkälla
        int id = getResources().getIdentifier("gallow" + counter, "drawable", getPackageName());
        //sätt bildkälla efter id
        iv.setImageResource(id);
        //omgångsräknare
        counter++;
        //generera random ord
        // generateRandomWord();


        //antal omgångar, och avslut.
        if (counter == 12) {
            Toast.makeText(getApplicationContext(), "You just killed a man \n try again?", Toast.LENGTH_LONG).show();
            counter = 0;
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public String generateRandomWord() {
        //array med ord
        String[] answerArray = new String[]{"kotlin","java","csharp","go"};
        TextView tv_random_answer = findViewById(R.id.right_answer);
        Random rnd = new Random();
        //generera randomsiffra med antal ord i array som slutsiffra
        int randomNumber = rnd.nextInt(answerArray.length);
        String randomWord = null;

        for (int i = 0; i < answerArray.length; i++) {
            randomWord = answerArray[randomNumber];
        }
        return randomWord;
    }

    public String hideWord(String randomGeneratedWord) {
        Log.d("Victor", "inne i hide word");
        String wordToGuess = randomGeneratedWord;
        String hiddenWord = "";

        for (int i = 0; i < wordToGuess.length(); i++) {
            hiddenWord += "-";
        }
        builder = new StringBuilder(hiddenWord);
        return hiddenWord;
    }

    public void uncoverCharsInHiddenWord(String userguess, String rndWord) {
        EditText et = findViewById(R.id.charGuess);
        builder.toString();
        for (int i = 0; i < rndWord.length(); i++) {
            if (rndWord.substring(i, i + 1).equals(userguess)) {
                builder.replace(i, i + 1, userguess);
                et.setText("");
            }
            Log.d("Victor", "inne i Uncover : " + builder.toString());
            tv_rightAnswer.setText(builder);
        }

        if (!rndWord.contains(userguess)) {
            et.setText("");
            gallowIncrementButtonClick();
        }
    }

    public void buttonTap(View view) {
        EditText inputChar = findViewById(R.id.charGuess);

        String guess = inputChar.getText().toString();

        System.out.println(guess);

        uncoverCharsInHiddenWord(guess, randomGeneratedWord);
    }

}