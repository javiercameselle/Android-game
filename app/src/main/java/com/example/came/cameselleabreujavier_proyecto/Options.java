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

import static com.example.came.cameselleabreujavier_proyecto.MainActivity.mediaPlayer;
import static com.example.came.cameselleabreujavier_proyecto.MainActivity.musicStarted;
import static com.example.came.cameselleabreujavier_proyecto.MainActivity.withSound;
import static com.example.came.cameselleabreujavier_proyecto.MainActivity.withVibration;

public class Options extends Scene {

    private Bitmap imgBuildingsShadow, imgSoundOn, imgSoundOff, imgVibrationOn, imgVibrationOff;
    private Rect rSonido, rVibration;
    private Paint pText, pBoton;
    Utils u;
    private int widthAux, heightAux;
    private Vibrator vibrator;
    private SharedPreferences.Editor editor = preferences.edit();

    public Options(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);
        u = new Utils(context);
        fondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.moon_background);
        fondo = Bitmap.createScaledBitmap(fondo, anchoPantalla, altoPantalla, false);

        imgBuildingsShadow = BitmapFactory.decodeResource(context.getResources(), R.drawable.darks_buildings2);
        imgBuildingsShadow = Bitmap.createScaledBitmap(imgBuildingsShadow, anchoPantalla, altoPantalla, false);

        widthAux = anchoPantalla / 9;
        heightAux = altoPantalla / 9;

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

    public void dibujar(Canvas c) {
        try {
            c.drawBitmap(fondo, 0, 0, null);
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

    public boolean pulsa(Rect boton, MotionEvent event) {
        if (boton.contains((int) event.getX(), (int) event.getY())) {
            return true;
        } else return false;
    }

    public int onTouchEvent(MotionEvent event) {
        int pointerIndex = event.getActionIndex();        //Obtenemos el índice de la acción
        int pointerID = event.getPointerId(pointerIndex); //Obtenemos el Id del pointer asociado a la acción
        int accion = event.getActionMasked();             //Obtenemos el tipo de pulsación
        switch (accion) {
            case MotionEvent.ACTION_DOWN:           // Primer dedo toca
            case MotionEvent.ACTION_POINTER_DOWN:  // Segundo y siguientes tocan
                break;

            case MotionEvent.ACTION_UP:                     // Al levantar el último dedo
                if (pulsa(rSonido, event)) {
                    if (!withSound) {
                        efectos.play(selection, vol, vol, 1, 0, 1);

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
                    Log.i("xxxxSOUNDmenu", withSound+"");
                    editor.putBoolean("sound", withSound);
                    editor.commit();

                } else if (pulsa(rVibration, event)) {
                    if (withSound) {
                        efectos.play(selection, vol, vol, 1, 0, 1);
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
                Log.i("Otra acción", "Acción no definida: " + accion);
        }

        int idPadre = super.onTouchEvent(event);
        if (idPadre != idEscena) {
            return idPadre;
        }
        return idEscena;
    }

}