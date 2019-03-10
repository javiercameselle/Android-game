package com.example.came.cameselleabreujavier_proyecto;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    protected static MediaPlayer mediaPlayer;//Music player
    protected static AudioManager audioManager;//Sound effects and volume manager
    protected static boolean withSound, withVibration, musicStarted;
    private SceneControl p;//Scene control class
    protected static Typeface faw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        faw = Typeface.createFromAsset(this.getAssets(), "fonts/kr1.ttf");
        View decorView = getWindow().getDecorView();
        int opciones = View.SYSTEM_UI_FLAG_FULLSCREEN        // pone la pantalla en modo pantalla completa ocultando elementos no criticos como la barra de estado.
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION  // oculta la barra de navegación
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(opciones);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        withSound = preferences.getBoolean("sound", false);
        withVibration = preferences.getBoolean("vibration", false);
        mediaPlayer = MediaPlayer.create(this, R.raw.music_menu);

        p = new SceneControl(this);
        p.setKeepScreenOn(true);

        setContentView(p);
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        int opciones = View.SYSTEM_UI_FLAG_FULLSCREEN        // pone la pantalla en modo pantalla completa ocultando elementos no criticos como la barra de estado.
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION  // oculta la barra de navegación
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(opciones);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);


        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        withSound = preferences.getBoolean("sound", false);
        withVibration = preferences.getBoolean("vibration", false);
        /*
         * If sound is enable, this boolean allow to play music
         */
        if (withSound)
            mediaPlayer.start();
        if (p == null) {
            p = new SceneControl(this);
        } else {
            p.running = true;
        }
    }

    @Override
    protected void onPause() {
        if (withSound)
            mediaPlayer.pause();
        p.running = false;
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        mediaPlayer.stop();
        System.exit(0);
    }
}


