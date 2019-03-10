package com.example.came.cameselleabreujavier_proyecto;

import android.content.Context;
import android.content.SharedPreferences;
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

import static com.example.came.cameselleabreujavier_proyecto.MainActivity.faw;
import static com.example.came.cameselleabreujavier_proyecto.MainActivity.mediaPlayer;
import static com.example.came.cameselleabreujavier_proyecto.MainActivity.musicStarted;
import static com.example.came.cameselleabreujavier_proyecto.MainActivity.withSound;
import static com.example.came.cameselleabreujavier_proyecto.MainActivity.withVibration;

public class Options extends Scene {

    private Bitmap imgBuildingsShadow;//Buildings image
    private Bitmap imgSoundOn;//Sound on icon
    private Bitmap imgSoundOff;//Sound off icon
    private Bitmap imgVibrationOn;//Vibration on icon
    private Bitmap imgVibrationOff;//Vibration off icon
    private Rect rSonido, rVibration;//Buttons rectangles to set sound on/off and vibration on/off
    private Paint pText, pBoton;//Buttons mpodifiers
    private Utils u;//Utils class
    private int widthAux, heightAux;//Divided screen measures
    private Vibrator vibrator;//Allow to vibrate
    private SharedPreferences.Editor editor = preferences.edit();//Allow to modify shared preferences, that save sounds and vibration options

    /**
     * Initialize options screen components
     *
     * @param context      Context
     * @param idScene      Scene ID
     * @param screenWidth  Screen width
     * @param screenHeight Screen height
     */
    public Options(Context context, int idScene, int screenWidth, int screenHeight) {
        super(context, idScene, screenWidth, screenHeight);
        u = new Utils(context);
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);
        background = Bitmap.createScaledBitmap(background, screenWidth, screenHeight, false);

        imgBuildingsShadow = BitmapFactory.decodeResource(context.getResources(), R.drawable.darks_buildings2);
        imgBuildingsShadow = Bitmap.createScaledBitmap(imgBuildingsShadow, screenWidth, screenHeight, false);

        widthAux = screenWidth / 9;
        heightAux = screenHeight / 9;

        imgSoundOn = BitmapFactory.decodeResource(context.getResources(), R.drawable.sound_on);
        imgSoundOn = Bitmap.createScaledBitmap(imgSoundOn, widthAux * 2, heightAux * 2, false);

        imgSoundOff = BitmapFactory.decodeResource(context.getResources(), R.drawable.sound_off);
        imgSoundOff = Bitmap.createScaledBitmap(imgSoundOff, widthAux * 2, heightAux * 2, false);

        imgVibrationOn = BitmapFactory.decodeResource(context.getResources(), R.drawable.vibration_on);
        imgVibrationOn = Bitmap.createScaledBitmap(imgVibrationOn, widthAux * 2, heightAux * 2, false);

        imgVibrationOff = BitmapFactory.decodeResource(context.getResources(), R.drawable.vibration_off);
        imgVibrationOff = Bitmap.createScaledBitmap(imgVibrationOff, widthAux * 2, heightAux * 2, false);

        pText = new Paint();
        pText.setColor(Color.WHITE);
        pText.setTextSize(u.getDpW(200));
        pText.setTypeface(faw);

        pBoton = new Paint();
//        pBoton.setAlpha(50);
        pBoton.setColor(Color.RED);
        pBoton.setStyle(Paint.Style.STROKE);
        pBoton.setStrokeWidth(5);

        rSonido = new Rect(widthAux * 5, heightAux + u.getDpH(50), widthAux * 7, heightAux * 3);
        rVibration = new Rect(widthAux * 5, heightAux * 4 + u.getDpH(50), widthAux * 7, heightAux * 6);

        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void actualizarFisica() {

    }

    /**
     * Paint options on screen
     *
     * @param c Canvas
     */
    public void dibujar(Canvas c) {
        try {
            c.drawBitmap(background, 0, 0, null);
            c.drawBitmap(imgBuildingsShadow, 0, 0, null);
            c.drawText(context.getString(R.string.sound), widthAux * 2, heightAux * 3, pText);
//            c.drawRect(rSonido, pBoton);
            if (withSound) {
                c.drawBitmap(imgSoundOn, rSonido.centerX() - imgSoundOn.getWidth() / 2, rSonido.centerY() - imgSoundOn.getHeight() / 2, null);
            } else {
                c.drawBitmap(imgSoundOff, rSonido.centerX() - imgSoundOff.getWidth() / 2, rSonido.centerY() - imgSoundOff.getHeight() / 2, null);
            }
            c.drawText(context.getString(R.string.vibration), widthAux, heightAux * 6, pText);
//            c.drawRect(rVibration, pBoton);

            if (withVibration) {
                c.drawBitmap(imgVibrationOn, rVibration.centerX() - imgVibrationOn.getWidth() / 2, rVibration.centerY() - imgVibrationOn.getHeight() / 2, null);
            } else {
                c.drawBitmap(imgVibrationOff, rVibration.centerX() - imgVibrationOff.getWidth() / 2, rVibration.centerY() - imgVibrationOff.getHeight() / 2, null);
            }
            super.dibujar(c);
        } catch (Exception e) {
            Log.i("ERROR AL DIBUJAR", e.getLocalizedMessage());
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
            case MotionEvent.ACTION_DOWN:           // Primer dedo toca
            case MotionEvent.ACTION_POINTER_DOWN:  // Segundo y siguientes tocan
                break;

            case MotionEvent.ACTION_UP:                     // Al levantar el último dedo
                if (pulsa(rSonido, event)) {
                    if (!withSound) {
                        effects.play(selection, vol, vol, 1, 0, 1);

                        if (mediaPlayer == null) {
                            mediaPlayer = MediaPlayer.create(context, R.raw.music_menu);
                            mediaPlayer.setVolume(vol, vol);
                        }
                        if (!mediaPlayer.isPlaying()) {
                            mediaPlayer.start();
                        }
                        musicStarted = true;
                        withSound = true;
                    } else {
                        mediaPlayer.pause();
                        withSound = false;
                    }
                    Log.i("xxxxSOUNDmenu", withSound + "");
                    editor.putBoolean("sound", withSound);
                    editor.commit();

                } else if (pulsa(rVibration, event)) {
                    if (withSound) {
                        effects.play(selection, vol, vol, 1, 0, 1);
                    }
                    withVibration = !withVibration;
                    if (withVibration) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            vibrator.vibrate(200);
                        }
                    }
                    editor.putBoolean("vibration", withVibration);
                    editor.commit();
                }

            case MotionEvent.ACTION_POINTER_UP:  // Al levantar un dedo que no es el último
                break;


            case MotionEvent.ACTION_MOVE: // Se mueve alguno de los dedos

                break;
            default:
                Log.i("Otra acción", "Acción no definida: " + action);
        }

        int idFather = super.onTouchEvent(event);
        if (idFather != idScene) {
            return idFather;
        }
        return idScene;
    }

}