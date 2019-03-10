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
import static com.example.came.cameselleabreujavier_proyecto.MainActivity.faw;
import static com.example.came.cameselleabreujavier_proyecto.MainActivity.withSound;

/**
 * Scene commons properties
 */

public class Scene {


    Context context;
    int idScene;//Scene ID
    int screenWidth, screenHeight;
    Bitmap background;//Background image
    Paint pText, pText2;//Text scene modifier
    Rect rMenu;//Button area to go back
    Bitmap bmGoBack;//Button image to go back
    SoundPool effects;//Sound effects
    int selection, maxSimultaneousSounds = 2, vol;
    SharedPreferences preferences;//Options prefences

    /**
     * Initialize Scene components
     *
     * @param context      Context
     * @param idScene      Scene ID
     * @param screenWidth  Screen width
     * @param screenHeight Screen height
     */
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
        pText.setTypeface(faw);
        pText.setTextSize((int) (screenHeight / 12));
        pText2.setColor(Color.WHITE);
        pText2.setTextAlign(Paint.Align.CENTER);
        pText2.setTextSize(screenHeight / 5);
        pText2.setTypeface(faw);


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
            spb.setMaxStreams(maxSimultaneousSounds);
            this.effects = spb.build();
        } else {
            this.effects = new SoundPool(maxSimultaneousSounds, AudioManager.STREAM_MUSIC, 0);
        }
        selection = effects.load(context, R.raw.select_menu_effect, 1);

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
                Log.i("Otra acción", "Acción no definida: " + action);

        }
        return idScene;
    }

    public void actualizarFisica() {

    }

    /**
     * Paint common scene components
     *
     * @param c Canvas
     */
    public void dibujar(Canvas c) {
        if (idScene != 0) {
            c.drawBitmap(bmGoBack, rMenu.centerX() - bmGoBack.getWidth() / 2, rMenu.centerY() - bmGoBack.getHeight() / 2, null);
        }
    }

}
