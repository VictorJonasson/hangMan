package com.example.hangman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    MediaPlayer sound_buttonClick_mp;
    MediaPlayer sound_music_mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sound_buttonClick_mp = MediaPlayer.create(this, R.raw.button);
        sound_music_mp = MediaPlayer.create(this, R.raw.intro);
        sound_music_mp.setLooping(true);
        sound_music_mp.start();

    }

    public void aboutMe(View view) {
        Intent intent = new Intent(this, aboutMe.class);
        startActivity(intent);
        sound_buttonClick_mp.start();
        sound_music_mp.start();
        Log.d("victor", "aboutMe button pressed");

    }

    public void playGame(View view) {
        Intent intent = new Intent(this, playGame.class);
        startActivity(intent);
        sound_buttonClick_mp.start();
        sound_music_mp.start();
        Log.d("victor", "play game button pressed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //sound_music_mp.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // sound_music_mp.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        sound_music_mp.stop();
    }
}