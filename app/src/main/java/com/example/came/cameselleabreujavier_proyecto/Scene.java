package com.example.came.cameselleabreujavier_proyecto;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;

import static com.example.came.cameselleabreujavier_proyecto.MainActivity.audioManager;
import static com.example.came.cameselleabreujavier_proyecto.MainActivity.mediaPlayer;
import static com.example.came.cameselleabreujavier_proyecto.MainActivity.withSound;

public class Scene {
    Context context;
    int idEscena;
    int anchoPantalla, altoPantalla;
    Bitmap fondo;
    Paint pTexto, pTexto2, pBoton, pBoton2;
    Rect rMenu;
    Bitmap bmGoBack;
    SoundPool efectos;
    int selection, maxSonidosSimultaneos = 2, vol;
    SharedPreferences preferences;

    public Scene(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        this.context = context;
        this.idEscena = idEscena;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        preferences = context.getSharedPreferences("aa", Context.MODE_PRIVATE);

        this.bmGoBack = BitmapFactory.decodeResource(context.getResources(), R.drawable.go_back);
        this.bmGoBack = Bitmap.createScaledBitmap(bmGoBack, anchoPantalla / 8, altoPantalla / 7, false);
        fondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);
        fondo = Bitmap.createScaledBitmap(fondo, anchoPantalla, altoPantalla, false);

        pTexto = new Paint();
        pTexto2 = new Paint();

        pTexto.setColor(Color.RED);
        pTexto.setTextAlign(Paint.Align.CENTER);
        pTexto.setTextSize((int) (altoPantalla / 12));
        pTexto2.setColor(Color.GREEN);
        pTexto2.setTextAlign(Paint.Align.CENTER);
        pTexto2.setTextSize(altoPantalla / 5);
        pTexto.setColor(Color.BLUE);
        pBoton = new Paint();
        pBoton.setColor(Color.LTGRAY);
        pBoton2 = new Paint();
        pBoton2.setColor(Color.LTGRAY);


        rMenu = new Rect(anchoPantalla - anchoPantalla / 8, 0, anchoPantalla, altoPantalla / 7);
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        vol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        if (vol == 0) {
            vol = 1;
            withSound = false;
        }

        if ((android.os.Build.VERSION.SDK_INT) >= 21) {
            SoundPool.Builder spb = new SoundPool.Builder();
            spb.setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build());
            spb.setMaxStreams(maxSonidosSimultaneos);
            this.efectos = spb.build();
        } else {
            this.efectos = new SoundPool(maxSonidosSimultaneos, AudioManager.STREAM_MUSIC, 0);
        }
        selection = efectos.load(context, R.raw.select_menu_effect, 1);

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

            case MotionEvent.ACTION_UP:// Al levantar el último dedo
            case MotionEvent.ACTION_POINTER_UP:  // Al levantar un dedo que no es el último
                if (pulsa(rMenu, event) && idEscena != 0) {
                    if (withSound) {
                        efectos.play(selection, vol, vol, 1, 0, 1);
                    }
                    return 0;
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

    }

    public void dibujar(Canvas c) {
        if (idEscena != 0) {
//            c.drawRect(rMenu, pBoton);
            c.drawBitmap(bmGoBack, rMenu.centerX() - bmGoBack.getWidth() / 2, rMenu.centerY() - bmGoBack.getHeight() / 2, null);
        }
    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getIdEscena() {
        return idEscena;
    }

    public void setIdEscena(int idEscena) {
        this.idEscena = idEscena;
    }

    public int getAnchoPantalla() {
        return anchoPantalla;
    }

    public void setAnchoPantalla(int anchoPantalla) {
        this.anchoPantalla = anchoPantalla;
    }

    public int getAltoPantalla() {
        return altoPantalla;
    }

    public void setAltoPantalla(int altoPantalla) {
        this.altoPantalla = altoPantalla;
    }

}
