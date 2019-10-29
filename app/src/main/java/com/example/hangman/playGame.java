package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.String.valueOf;

public class playGame extends AppCompatActivity {
    int imageCounter = 1;
    int lifeCounter = 10;
    int succesfullTries = 0;
    StringBuilder builder;
    String hiddenWord = "";
    String randomGeneratedWord;
    TextView tv_rightAnswer;
    TextView tv_triesLeft;
    TextView tv_failedGuessArray;
    ImageView iv_Gallow;
    ArrayList<String> failedGuessArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        iv_Gallow = findViewById(R.id.iv_gallowView);
        //sätt startbild
        iv_Gallow.setImageResource(R.drawable.gallow1);
        tv_rightAnswer = findViewById(R.id.tv_rightAnswer);
        randomGeneratedWord = generateRandomWord();
        String hiddenword = hideWord(randomGeneratedWord);
        tv_rightAnswer.setText(hiddenword);
    }

    //räkna upp bild, räkna bort liv.
    public void gallowIncrement() {
        //counter för bildID
        imageCounter++;
        //omgångsräknare för försök kvar
        lifeCounter--;
        //hämta id från bildkälla
        int id = getResources().getIdentifier("gallow" + imageCounter, "drawable", getPackageName());
        //sätt bildkälla efter id
        iv_Gallow.setImageResource(id);

        //avsluta spel, Toasta och återgå till main
        if (lifeCounter == 0) {
            Toast.makeText(getApplicationContext(), "You just killed a man \n try again?", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    //randomgenerator för ord
    public String generateRandomWord() {
        //array med ord
        String[] answerArray = new String[]{"kotlin", "java", "csharp", "go"};
        Random rnd = new Random();
        //generera random nummer med lägd på array som index
        int randomNumber = rnd.nextInt(answerArray.length);
        String randomWord = null;

        for (int i = 0; i < answerArray.length; i++) {
            randomWord = answerArray[randomNumber];
        }
        return randomWord;
    }

    //tar emot random ord
    public String hideWord(String randomGeneratedWord) {
        hiddenWord = "";
        /*
        loopar genom randomgenererade ordet , sparar undan det i hidden word,
        och ersätter strängens i med "-"
        */
        for (int i = 0; i < randomGeneratedWord.length(); i++) {
            hiddenWord += "-";
        }
        builder = new StringBuilder(hiddenWord);
        return hiddenWord;
    }

    //återställer det gämda ordet, tar användarens gissning och det random genererade ordet som argument
    public void uncoverCharsInHiddenWord(String userguess, String rndWord) {
        EditText et_charguess = findViewById(R.id.et_charGuess);
        tv_triesLeft = findViewById(R.id.tv_triesLeft);
        tv_failedGuessArray = findViewById(R.id.tv_failedGuess);

        //loopar random-ord
        for (int i = 0; i < rndWord.length(); i++) {
            //ställer gissningen mot random-ordet och ersätter "-" med bokstav i min "builder" som huserar de gömda ordet i form av "_"
            if (rndWord.substring(i, i + 1).equals(userguess)) {
                builder.replace(i, i + 1, userguess);
                et_charguess.setText("");
                //räknar upp lyckade antal gissningar
                succesfullTries++;
            }
            //sätter min textview till "-"
            tv_rightAnswer.setText(builder);
        }

        //ställer mina lyckade gissningar mot längden på mitt ord.
        //Om detta stämmer, byt activity.
        if (succesfullTries == builder.length()) {
            Intent intent = new Intent(playGame.this, gameWinner.class);
            startActivity(intent);
            Log.d("victor", "Inne i Win activity");
        }

        // stämmer inte gissning stämmer
        if (!rndWord.contains(userguess)) {
            et_charguess.setText("");
            //anropa uppräkning av bild etc.
            gallowIncrement();
            //visa antal gånger kvar
            tv_triesLeft.setText(MessageFormat.format("{0}{1}", getString(R.string.triesLeftCounter), lifeCounter));

            //kontrollera om gissningar använts hittils
            if (failedGuessArray.contains(userguess)) {
                Toast.makeText(this, "Try something new for a change", Toast.LENGTH_LONG).show();
            //addera misslyckade gissningar till array
            } else {
                failedGuessArray.add(userguess);
                String testFail = valueOf(failedGuessArray);
                tv_failedGuessArray.setText(testFail);
            }
        }
    }
    //ta emot användaren gissning från edit text
    public void buttonTap(View view) {
        EditText inputChar = findViewById(R.id.et_charGuess);
        String guess = inputChar.getText().toString();
        Log.d("victor", "Gissning från knapptryck = " + guess);
        uncoverCharsInHiddenWord(guess, randomGeneratedWord);
    }
}