package com.example.came.cameselleabreujavier_proyecto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
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

public class MenuPrincipal extends Escena {

    private Paint p;
    private ArrayList<Cloud> arrayClouds;
    private Rect ayuda, opciones, records, juego, creditos;
    private int ancho, ancho2, alto, altoPantalla, anchoPantalla;
    private Bitmap imgBuildingsShadow, imgCloud, imgPlay, imgOptions, imgHelp, imgRecords, imgCréditos, imgCréditos2;
    private ArrayList<Bitmap> fondos, bmClouds;
    private AudioManager audioManager;
    private boolean suena = true;
    private Vibrator vibrator;
    private Cap capa;
    /**/
    private SoundPool efectos;
    private int diferentes_efectos;
    final private int maxSonidosSimultaneos = 10;
    /**/

    public MenuPrincipal(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);

        fondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.dark_background);
        fondo = Bitmap.createScaledBitmap(fondo, anchoPantalla, altoPantalla, false);

        imgBuildingsShadow = BitmapFactory.decodeResource(context.getResources(), R.drawable.darks_buildings);
        imgBuildingsShadow = Bitmap.createScaledBitmap(imgBuildingsShadow, anchoPantalla, altoPantalla, false);
//parallax
//        fondos = new ArrayList<>();
//        fondos.add(imgBuildingsShadow);
//        fondos.add(imgBuildingsShadow);
//        capa = new Cap(context, anchoPantalla, altoPantalla, fondos);
//        capa.setVelocidad(-10);

        bmClouds = new ArrayList<>();
        arrayClouds = new ArrayList<>();
        int fin = (int) (Math.random() * 7 + 1);
        imgCloud = BitmapFactory.decodeResource(context.getResources(), R.drawable.little_cloud);
        bmClouds.add(Bitmap.createScaledBitmap(imgCloud, anchoPantalla / 3, altoPantalla / 7, false));
        bmClouds.add(Bitmap.createScaledBitmap(imgCloud, anchoPantalla / 3, altoPantalla / 7, false));
        imgCloud = BitmapFactory.decodeResource(context.getResources(), R.drawable.dark_cloud);
        bmClouds.add(Bitmap.createScaledBitmap(imgCloud, anchoPantalla / 3, altoPantalla / 5, false));
        bmClouds.add(Bitmap.createScaledBitmap(imgCloud, anchoPantalla / 3, altoPantalla / 5, false));
        imgCloud = BitmapFactory.decodeResource(context.getResources(), R.drawable.clouds_withalpha);
        bmClouds.add(Bitmap.createScaledBitmap(imgCloud, anchoPantalla / 3, altoPantalla / 7, false));
        bmClouds.add(Bitmap.createScaledBitmap(imgCloud, anchoPantalla / 3, altoPantalla / 7, false));
        for (int i = 0; i < fin; i++) {
            arrayClouds.add(new Cloud(context, anchoPantalla, altoPantalla, bmClouds));
        }

        p = new Paint();
        p.setAlpha(50);
        p.setColor(Color.BLUE);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(1);

        alto = altoPantalla / 7;
        ancho = anchoPantalla / 4;
        ancho2 = anchoPantalla / 10;

//            ayuda = new Rect(ancho, alto * 6, ancho * 2, alto * 7);
//            opciones = new Rect(ancho2 * 4, alto * 4, ancho2 * 6, alto * 6);
//            records = new Rect(ancho2 * 7, alto * 4, ancho2 * 9, alto * 6);
//            creditos = new Rect(ancho2, alto * 4, ancho2 * 3, alto * 6);

        juego = new Rect(ancho2 * 3, alto, ancho2 * 7, alto * 3);
        imgPlay = BitmapFactory.decodeResource(context.getResources(), R.drawable.play);
        imgPlay = Bitmap.createScaledBitmap(imgPlay, anchoPantalla / 4, altoPantalla / 4, false);
        ayuda = new Rect(ancho2 * 9, alto, ancho2 * 10, alto * 2);
        imgHelp = BitmapFactory.decodeResource(context.getResources(), R.drawable.help);
        imgHelp = Bitmap.createScaledBitmap(imgHelp, anchoPantalla / 10, altoPantalla / 8, false);
        opciones = new Rect(ancho2 * 4, alto * 4, ancho2 * 6, alto * 6);
        imgOptions = BitmapFactory.decodeResource(context.getResources(), R.drawable.options);
        imgOptions = Bitmap.createScaledBitmap(imgOptions, anchoPantalla / 8, altoPantalla / 6, false);
        records = new Rect(ancho2 * 7, alto * 4, ancho2 * 9, alto * 6);
        imgRecords = BitmapFactory.decodeResource(context.getResources(), R.drawable.trophy_cup);
        imgRecords = Bitmap.createScaledBitmap(imgRecords, anchoPantalla / 8, altoPantalla / 6, false);
        creditos = new Rect(ancho2, alto * 4, ancho2 * 3, alto * 6);
        imgCréditos = BitmapFactory.decodeResource(context.getResources(), R.drawable.team_idea);
        imgCréditos = Bitmap.createScaledBitmap(imgCréditos, anchoPantalla / 8, altoPantalla / 6, false);
        if(withVibration) {
            vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        }
        if(withSound) {
            audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

            int vol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            mediaPlayer = MediaPlayer.create(context, R.raw.musica);
            mediaPlayer.setVolume(50, 50);
            if(!musicStarted) {
                mediaPlayer.start();
                musicStarted=!musicStarted;
            }

            if ((android.os.Build.VERSION.SDK_INT) >= 21) {
                SoundPool.Builder spb = new SoundPool.Builder();
                spb.setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build());
                spb.setMaxStreams(maxSonidosSimultaneos);
                this.efectos = spb.build();
            } else {
                this.efectos = new SoundPool(maxSonidosSimultaneos, AudioManager.STREAM_MUSIC, 0);
            }

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
                if (pulsa(juego, event)) {
                    mediaPlayer.stop();
                    if (withVibration) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            vibrator.vibrate(100);
                        }
                    }
                    return 1;
                } else if (pulsa(ayuda, event)) {
                    return 2;
                } else if (pulsa(records, event)) {
                    return 3;
                } else if (pulsa(opciones, event)) {
                    return 4;
                } else if (pulsa(creditos, event)) {
                    return 5;
                }
                break;


            case MotionEvent.ACTION_MOVE: // Se mueve alguno de los dedos

                break;
            default:
                Log.i("Otra acción", "Acción no definida: " + accion);
        }
        return idEscena;
    }

    public void actualizarFisica() {
//        capa.mover();
        for (Cloud cd : arrayClouds) {
            cd.mover();
        }
    }

    public void dibujar(Canvas c) {
        try {
//            capa.dibujar(c);
            c.drawBitmap(fondo, 0, 0, null);
            c.drawBitmap(imgBuildingsShadow, 0, 0, null);
            for (Cloud cd : arrayClouds) {
                cd.dibujar(c);
            }

//            c.drawRect(juego, p);
            c.drawBitmap(imgPlay, juego.centerX() - imgPlay.getWidth() / 2, juego.centerY() - imgPlay.getHeight() / 2, null);
//            c.drawRect(ayuda,p);
            c.drawBitmap(imgHelp, ayuda.centerX() - imgHelp.getWidth() / 2, ayuda.centerY() - imgHelp.getHeight() / 2, null);
//            c.drawText(context.getString(R.string.play), juego.centerX(), juego.centerY() + alto / 3, pTexto);
//            c.drawRect(creditos, p);
//            c.drawBitmap(imgCréditos2, creditos.centerX() - imgCréditos.getWidth() / 2, creditos.centerY() - imgCréditos.getHeight() / 2, null);
            c.drawBitmap(imgCréditos, creditos.centerX() - imgCréditos.getWidth() / 2, creditos.centerY() - imgCréditos.getHeight() / 2, null);
//            c.drawText(context.getString(R.string.credits), creditos.centerX(), creditos.centerY() + alto / 3, pTexto);
//            c.drawRect(records, p);
            c.drawBitmap(imgRecords, records.centerX() - imgRecords.getWidth() / 2, records.centerY() - imgRecords.getHeight() / 2, null);
//            c.drawText(context.getString(R.string.records), records.centerX(), records.centerY() + alto / 3, pTexto);
//            c.drawRect(opciones, p);
            c.drawBitmap(imgOptions, opciones.centerX() - imgOptions.getWidth() / 2, opciones.centerY() - imgOptions.getHeight() / 2, null);
//            c.drawText(context.getString(R.string.options), opciones.centerX(), opciones.centerY() + alto / 3, pTexto);
        } catch (Exception e) {
            Log.e("ERROR AL DIBUJAR", e.getLocalizedMessage());
        }
    }

}
