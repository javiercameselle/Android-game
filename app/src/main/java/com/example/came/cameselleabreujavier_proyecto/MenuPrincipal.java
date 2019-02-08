package com.example.came.cameselleabreujavier_proyecto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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

public class MenuPrincipal extends Escena {

    private ArrayList<Cloud> arrayClouds;
    private Rect ayuda, opciones, records, juego;
    private int ancho, ancho2, alto, altoPantalla, anchoPantalla;
    private Bitmap imgBuildingsShadow, imgFondo, imgCloud;
    private ArrayList<Bitmap> fondos, bmClouds;
    private AudioManager audioManager;
    private MediaPlayer mediaPlayer;
    private boolean suena = true;
    private Vibrator vibrator;
    private Cap capa;
    /**/
    private SoundPool efectos;
    private int sonidoWoosh, sonidoPajaro, sonidoExplosion;
    final private int maxSonidosSimultaneos = 10;
    /**/

    public MenuPrincipal(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);

        imgFondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.dark_background);
        imgFondo = Bitmap.createScaledBitmap(imgFondo, anchoPantalla, altoPantalla, false);

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
        imgCloud = BitmapFactory.decodeResource(context.getResources(), R.drawable.alphas_clouds);
        bmClouds.add(Bitmap.createScaledBitmap(imgCloud, anchoPantalla / 3, altoPantalla / 7, false));
        bmClouds.add(Bitmap.createScaledBitmap(imgCloud, anchoPantalla / 3, altoPantalla / 7, false));
        for (int i = 0; i < fin; i++) {
            arrayClouds.add(new Cloud(context, anchoPantalla, altoPantalla, bmClouds));
        }


        alto = altoPantalla / 7;
        ancho = anchoPantalla / 4;
        ancho2 = anchoPantalla / 10;
        juego = new Rect(ancho, alto, ancho * 3, alto * 3);
        ayuda = new Rect(ancho2, alto * 4, ancho2 * 3, alto * 6);
        opciones = new Rect(ancho2 * 4, alto * 4, ancho2 * 6, alto * 6);
        records = new Rect(ancho2 * 7, alto * 4, ancho2 * 9, alto * 6);

        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int vol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        if ((android.os.Build.VERSION.SDK_INT) >= 21) {
            SoundPool.Builder spb = new SoundPool.Builder();
            spb.setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build());
            spb.setMaxStreams(maxSonidosSimultaneos);
            this.efectos = spb.build();
        } else {
            this.efectos = new SoundPool(maxSonidosSimultaneos, AudioManager.STREAM_MUSIC, 0);
        }
        sonidoWoosh = efectos.load(context, R.raw.woosh, 1);
        sonidoExplosion = efectos.load(context, R.raw.explosion, 1);
        sonidoPajaro = efectos.load(context, R.raw.pajaro, 1);
        mediaPlayer = MediaPlayer.create(context, R.raw.musica);
        mediaPlayer.setVolume(vol * 5, vol * 5);
//        mediaPlayer.start();//Hay que colocar un stop al finalizar la app
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
//                    if (!suena)
//                        mediaPlayer.prepare();
//                    mediaPlayer.start();
//                    mediaPlayer.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case MotionEvent.ACTION_POINTER_DOWN:  // Segundo y siguientes tocan
                break;

            case MotionEvent.ACTION_UP:// Al levantar el último dedo
//                efectos.play(sonidoPajaro, 8, 8, 1, 0, 1);
//                suena = false;
            case MotionEvent.ACTION_POINTER_UP:  // Al levantar un dedo que no es el último
                if (pulsa(juego, event)) {
                    mediaPlayer.stop();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        vibrator.vibrate(500);
                    }
                    return 1;
                } else if (pulsa(ayuda, event)) {
                    mediaPlayer.stop();
                    return 2;
                } else if (pulsa(records, event)) {
                    mediaPlayer.stop();
                    return 3;
                } else if (pulsa(opciones, event)) {
                    mediaPlayer.stop();
                    return 4;
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
            c.drawBitmap(imgFondo, 0, 0, null);
            c.drawBitmap(imgBuildingsShadow, 0, 0, null);
            for (Cloud cd : arrayClouds) {
                cd.dibujar(c);
            }

            c.drawRect(juego, pBoton);
            c.drawText(context.getString(R.string.play), juego.centerX(), juego.centerY() + alto / 3, pTexto);
            c.drawRect(ayuda, pBoton2);
            c.drawText(context.getString(R.string.help), ayuda.centerX(), ayuda.centerY() + alto / 3, pTexto);
            c.drawRect(records, pBoton2);
            c.drawText(context.getString(R.string.records), records.centerX(), records.centerY() + alto / 3, pTexto);
            c.drawRect(opciones, pBoton2);
            c.drawText(context.getString(R.string.options), opciones.centerX(), opciones.centerY() + alto / 3, pTexto);
//            c.drawText("MenuPrincipal" + this.idEscena, anchoPantalla / 2, altoPantalla / 5, pTexto);
//            c.drawText("MenuPrincipal" + this.idEscena, anchoPantalla / 2, altoPantalla / 5 + 10, pTexto2);
        } catch (Exception e) {
            Log.e("ERROR AL DIBUJAR", e.getLocalizedMessage());
        }
    }

}
