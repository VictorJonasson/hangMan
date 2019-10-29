package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MediaPlayer sound_buttonClick_mp;
    MediaPlayer sound_music_mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sound_buttonClick_mp = MediaPlayer.create(this, R.raw.button);
        sound_music_mp = MediaPlayer.create(this, R.raw.intro);
    }

    public void aboutMe(View view) {
        Intent intent = new Intent(this, aboutMe.class);
        startActivity(intent);
        //sound_buttonClick_mp.start();
    }

    public void playGame(View view) {
        Intent intent = new Intent(this, playGame.class);
        startActivity(intent);
        //sound_buttonClick_mp.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //sound_music_mp.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //sound_music_mp.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //sound_music_mp.stop();
    }

    public void startMedia(View view) {
        Toast.makeText(getApplicationContext(), R.string.sorry_william, Toast.LENGTH_LONG).show();
        sound_music_mp.start();
        sound_music_mp.setLooping(true);
    }
}