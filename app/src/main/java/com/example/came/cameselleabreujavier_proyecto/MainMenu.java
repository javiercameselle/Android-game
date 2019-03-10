package com.example.came.cameselleabreujavier_proyecto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import static com.example.came.cameselleabreujavier_proyecto.MainActivity.mediaPlayer;
import static com.example.came.cameselleabreujavier_proyecto.MainActivity.musicStarted;
import static com.example.came.cameselleabreujavier_proyecto.MainActivity.withSound;
import static com.example.came.cameselleabreujavier_proyecto.MainActivity.withVibration;

/**
 * Main menu shows different options of the game to choose
 */

public class MainMenu extends Scene {

    private Paint p;//Buttons modifiers
    private ArrayList<Cloud> arrayClouds;//Clouds array
    private Rect help;//Button help
    private Rect options;//Button options
    private Rect records;//Button recorsds
    private Rect game;//Button play game
    private Rect credits;//Button credits
    private Rect exit;//Button exit
    private int width;//Divided screen width measure
    private int width2;//Other divided screen width measure
    private int height;//Divided screen height measure
    private int screenWidth;//Screen width
    private int screenHeight;//Screen height
    private int end;//Number of clouds in main menu
    private Bitmap imgBuildingsShadow;//Buildings image
    private Bitmap imgCloud;//Cloud image
    private Bitmap imgPlay;//Play game icon
    private Bitmap imgOptions;//Options icon
    private Bitmap imgHelp;//Help icon
    private Bitmap imgRecords;//Records icon
    private Bitmap imgCredits;//Credits icon
    private Bitmap imgExit;//Exit icon
    private ArrayList<Bitmap> bmClouds;//Cloud images array
    private Vibrator vibrator;//Allow to vibrate
    /**/
    final private int maxSimultaneousSounds = 10;
    /**/

    /**
     * Iniatilize main menu components and properties
     *
     * @param context      Context
     * @param idScene      Scene ID
     * @param screenWidth  Screen width
     * @param screenHeight Screen height
     */
    public MainMenu(Context context, int idScene, int screenWidth, int screenHeight) {
        super(context, idScene, screenWidth, screenHeight);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.moon_background);
        background = Bitmap.createScaledBitmap(background, screenWidth, screenHeight, false);

        imgBuildingsShadow = BitmapFactory.decodeResource(context.getResources(), R.drawable.darks_buildings);
        imgBuildingsShadow = Bitmap.createScaledBitmap(imgBuildingsShadow, screenWidth, screenHeight, false);

        bmClouds = new ArrayList<>();
        arrayClouds = new ArrayList<>();
        end = (int) (Math.random() * 10 + 2);
        imgCloud = BitmapFactory.decodeResource(context.getResources(), R.drawable.little_cloud);
        bmClouds.add(Bitmap.createScaledBitmap(imgCloud, screenWidth / 3, screenHeight / 7, false));
        bmClouds.add(Bitmap.createScaledBitmap(imgCloud, screenWidth / 3, screenHeight / 7, false));
        imgCloud = BitmapFactory.decodeResource(context.getResources(), R.drawable.dark_cloud);
        bmClouds.add(Bitmap.createScaledBitmap(imgCloud, screenWidth / 3, screenHeight / 5, false));
        bmClouds.add(Bitmap.createScaledBitmap(imgCloud, screenWidth / 3, screenHeight / 5, false));
        imgCloud = BitmapFactory.decodeResource(context.getResources(), R.drawable.clouds_withalpha);
        bmClouds.add(Bitmap.createScaledBitmap(imgCloud, screenWidth / 3, screenHeight / 7, false));
        bmClouds.add(Bitmap.createScaledBitmap(imgCloud, screenWidth / 3, screenHeight / 7, false));
        for (int i = 0; i < end; i++) {
            arrayClouds.add(new Cloud(context, screenWidth, screenHeight, bmClouds));
        }

        p = new Paint();
        p.setAlpha(50);
        p.setColor(Color.BLUE);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(1);

        height = screenHeight / 7;
        width = screenWidth / 4;
        width2 = screenWidth / 10;

//            help = new Rect(width, height * 6, width * 2, height * 7);
//            options = new Rect(width2 * 4, height * 4, width2 * 6, height * 6);
//            records = new Rect(width2 * 7, height * 4, width2 * 9, height * 6);
//            credits = new Rect(width2, height * 4, width2 * 3, height * 6);

        game = new Rect(width2 * 3, height, width2 * 7, height * 3);
        imgPlay = BitmapFactory.decodeResource(context.getResources(), R.drawable.play);
        imgPlay = Bitmap.createScaledBitmap(imgPlay, screenWidth / 4, screenHeight / 4, false);
        help = new Rect(width2 * 9, height, width2 * 10, height * 2);
        imgHelp = BitmapFactory.decodeResource(context.getResources(), R.drawable.help);
        imgHelp = Bitmap.createScaledBitmap(imgHelp, screenWidth / 10, screenHeight / 8, false);
        options = new Rect(width2 * 4, height * 4, width2 * 6, height * 6);
        imgOptions = BitmapFactory.decodeResource(context.getResources(), R.drawable.options);
        imgOptions = Bitmap.createScaledBitmap(imgOptions, screenWidth / 8, screenHeight / 6, false);
        records = new Rect(width2 * 7, height * 4, width2 * 9, height * 6);
        imgRecords = BitmapFactory.decodeResource(context.getResources(), R.drawable.trophy_cup);
        imgRecords = Bitmap.createScaledBitmap(imgRecords, screenWidth / 8, screenHeight / 6, false);
        credits = new Rect(width2, height * 4, width2 * 3, height * 6);
        imgCredits = BitmapFactory.decodeResource(context.getResources(), R.drawable.team_idea);
        imgCredits = Bitmap.createScaledBitmap(imgCredits, screenWidth / 8, screenHeight / 6, false);
        exit = new Rect(0, height, width2, height * 2);
        imgExit = BitmapFactory.decodeResource(context.getResources(), R.drawable.exit);
        imgExit = Bitmap.createScaledBitmap(imgExit, screenWidth / 10, screenHeight / 8, false);

        //Options
        {
            withSound = this.preferences.getBoolean("sound", false);
            withVibration = this.preferences.getBoolean("vibration", false);
        }

        if (withVibration) {
            vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        }

        if (withSound) {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(context, R.raw.music_menu);
                mediaPlayer.setVolume(vol, vol);
            }
            if (!musicStarted && !mediaPlayer.isPlaying()) {
                mediaPlayer = MediaPlayer.create(context, R.raw.music_menu);
                mediaPlayer.start();
                musicStarted = true;
            }
        }
    }

    /**
     * Pressed button checker
     *
     * @param button Button rectangle
     * @param event  Action event
     * @return Button pressed
     */
    public boolean pulsa(Rect button, MotionEvent event) {
        if (button.contains((int) event.getX(), (int) event.getY())) {
            return true;
        } else return false;
    }

    /**
     * Screen touch control
     *
     * @param event Press action
     * @return Scene ID to change scene
     */
    public int onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();             //Obtenemos el tipo de pulsación
        switch (action) {
            case MotionEvent.ACTION_DOWN:// Primer dedo 0
                try {
//
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case MotionEvent.ACTION_POINTER_DOWN:  // Segundo y siguientes tocan
                break;

            case MotionEvent.ACTION_UP:// Al levantar el último dedo
            case MotionEvent.ACTION_POINTER_UP:  // Al levantar un dedo que no es el último
                if (pulsa(game, event)) {
                    if (withSound) {
                        effects.play(selection, vol, vol, 1, 0, 1);
                        mediaPlayer.stop();
                        musicStarted = false;
                    }
                    if (withVibration) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            vibrator.vibrate(100);
                        }
                    }
                    return 1;
                } else if (pulsa(help, event)) {
                    if (withSound)
                        effects.play(selection, vol, vol, 1, 0, 1);
                    return 2;
                } else if (pulsa(records, event)) {
                    if (withSound)
                        effects.play(selection, vol, vol, 1, 0, 1);
                    return 3;
                } else if (pulsa(options, event)) {
                    if (withSound)
                        effects.play(selection, vol, vol, 1, 0, 1);
                    return 4;
                } else if (pulsa(credits, event)) {
                    if (withSound)
                        effects.play(selection, vol, vol, 1, 0, 1);
                    return 5;
                } else if (pulsa(exit, event)) {
                    if (withSound) {
                        effects.play(selection, vol, vol, 1, 0, 1);
                    }
                    System.exit(0);
                }
                break;


            case MotionEvent.ACTION_MOVE: // Se mueve alguno de los dedos

                break;
            default:
                Log.i("Otra acción", "Acción no definida: " + action);
        }
        return idScene;
    }

    /**
     * Cloud movement manager
     */
    public void actualizarFisica() {
//        capa.mover();
        for (Cloud cd : arrayClouds) {
            cd.mover();
        }
    }

    /**
     * Paint main menu components on screen
     *
     * @param c Canvas
     */
    public void dibujar(Canvas c) {
        try {
            c.drawBitmap(background, 0, 0, null);
            if (arrayClouds.get(0).isBackground()) {
                for (Cloud cd : arrayClouds) {
                    cd.dibujar(c);
                }
                c.drawBitmap(imgBuildingsShadow, 0, 0, null);
            } else {
                c.drawBitmap(imgBuildingsShadow, 0, 0, null);
                for (Cloud cd : arrayClouds) {
                    cd.dibujar(c);
                }
            }
//            c.drawRect(game, p);
            c.drawBitmap(imgPlay, game.centerX() - imgPlay.getWidth() / 2, game.centerY() - imgPlay.getHeight() / 2, null);
//            c.drawRect(help,p);
            c.drawBitmap(imgHelp, help.centerX() - imgHelp.getWidth() / 2, help.centerY() - imgHelp.getHeight() / 2, null);
//            c.drawText(context.getString(R.string.play), game.centerX(), game.centerY() + height / 3, pText);
//            c.drawRect(credits, p);
            c.drawBitmap(imgCredits, credits.centerX() - imgCredits.getWidth() / 2, credits.centerY() - imgCredits.getHeight() / 2, null);
//            c.drawText(context.getString(R.string.credits), credits.centerX(), credits.centerY() + height / 3, pText);
//            c.drawRect(records, p);
            c.drawBitmap(imgRecords, records.centerX() - imgRecords.getWidth() / 2, records.centerY() - imgRecords.getHeight() / 2, null);
//            c.drawText(context.getString(R.string.records), records.centerX(), records.centerY() + height / 3, pText);
//            c.drawRect(options, p);
            c.drawBitmap(imgOptions, options.centerX() - imgOptions.getWidth() / 2, options.centerY() - imgOptions.getHeight() / 2, null);
//            c.drawText(context.getString(R.string.options), options.centerX(), options.centerY() + height / 3, pText);
            c.drawBitmap(imgExit, exit.centerX() - imgExit.getWidth() / 2, exit.centerY() - imgExit.getHeight() / 2, null);
        } catch (Exception e) {
            Log.e("ERROR AL DIBUJAR", e.getLocalizedMessage());
        }
    }

}
