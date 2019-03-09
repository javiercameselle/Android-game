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
import static com.example.came.cameselleabreujavier_proyecto.MainActivity.withSound;

public class Scene {
    Context context;
    int idScene;
    int screenWidth, screenHeight;
    Bitmap background;
    Paint pText, pText2;
    Rect rMenu;
    Bitmap bmGoBack;
    SoundPool effects;
    int selection, maxSonidosSimultaneos = 2, vol;
    SharedPreferences preferences;

    public Scene(Context context, int idScene, int screenWidth, int screenHeight) {
        this.context = context;
        this.idScene = idScene;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        preferences = context.getSharedPreferences("aa", Context.MODE_PRIVATE);

        this.bmGoBack = BitmapFactory.decodeResource(context.getResources(), R.drawable.go_back);
        this.bmGoBack = Bitmap.createScaledBitmap(bmGoBack, screenWidth / 8, screenHeight / 7, false);
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);
        background = Bitmap.createScaledBitmap(background, screenWidth, screenHeight, false);

        pText = new Paint();
        pText2 = new Paint();

        pText.setColor(Color.RED);
        pText.setTextAlign(Paint.Align.CENTER);
        pText.setTextSize((int) (screenHeight / 12));
        pText2.setColor(Color.WHITE);
        pText2.setTextAlign(Paint.Align.CENTER);
        pText2.setTextSize(screenHeight / 5);
        pText.setColor(Color.BLUE);


        rMenu = new Rect(screenWidth - screenWidth / 8, 0, screenWidth, screenHeight / 7);
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
            this.effects = spb.build();
        } else {
            this.effects = new SoundPool(maxSonidosSimultaneos, AudioManager.STREAM_MUSIC, 0);
        }
        selection = effects.load(context, R.raw.select_menu_effect, 1);

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
                if (pulsa(rMenu, event) && idScene != 0) {
                    if (withSound) {
                        effects.play(selection, vol, vol, 1, 0, 1);
                    }
                    return 0;
                }
                break;

            case MotionEvent.ACTION_MOVE: // Se mueve alguno de los dedos

                break;
            default:
                Log.i("Otra acción", "Acción no definida: " + accion);

        }
        return idScene;
    }

    public void actualizarFisica() {

    }

    public void dibujar(Canvas c) {
        if (idScene != 0) {
            c.drawBitmap(bmGoBack, rMenu.centerX() - bmGoBack.getWidth() / 2, rMenu.centerY() - bmGoBack.getHeight() / 2, null);
        }
    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getIdScene() {
        return idScene;
    }

    public void setIdScene(int idScene) {
        this.idScene = idScene;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

}
