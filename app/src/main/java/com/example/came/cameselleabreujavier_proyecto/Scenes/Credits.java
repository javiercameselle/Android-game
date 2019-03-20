package com.example.came.cameselleabreujavier_proyecto.Scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.util.Log;
import android.view.MotionEvent;

import com.example.came.cameselleabreujavier_proyecto.R;
import com.example.came.cameselleabreujavier_proyecto.Scene;
import com.example.came.cameselleabreujavier_proyecto.SceneUtils.Utils;

import java.util.ArrayList;

import static com.example.came.cameselleabreujavier_proyecto.MainActivity.faw;

/**
 * Mention to supporters, game providers, etc.
 */

public class Credits extends Scene {

    private Paint pText;//Text modifier
    private Paint pTextNameCategory;//Text modifier
    private Bitmap imgBuildingsShadow;//Buildings shadows image
    private int posX;//Position on horizontal axis
    private String camID;//Camera ID
    private ArrayList<Integer> posY;//Different text position on vertical axis
    private Utils u;//Utils class
    private CameraManager cameraManager;//Allow to use camera
    private boolean flash;//Flash (not) enabled
    private int screenWidth, screenHeight;//Screen measures
    private String[] credits = {"game-icons.net", "noisefuns.com", "iconarchive.com", "opengameart.com", "youtube.com", "BoxCatGames", "SoundBible", "Sara Mendez", "Javier Conde", "Alvaro Cayetano", "Diego Bea", "Pablo Fernandez", "Franco Alessandrini"};//Credits names


    /**
     * Initialize credits properties
     *
     * @param context      Context
     * @param idScene      Scene ID
     * @param screenWidth  Screen width
     * @param screenHeight Screen height
     */
    public Credits(Context context, int idScene, int screenWidth, int screenHeight) {
        super(context, idScene, screenWidth, screenHeight);
        u = new Utils(context);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {
            camID = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(camID, true);
            flash = true;
        } catch (CameraAccessException cae) {
        }

        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.moon_background);
        background = Bitmap.createScaledBitmap(background, screenWidth, screenHeight, false);

        imgBuildingsShadow = BitmapFactory.decodeResource(context.getResources(), R.drawable.darks_buildings2);
        imgBuildingsShadow = Bitmap.createScaledBitmap(imgBuildingsShadow, screenWidth, screenHeight, false);

        this.posX = screenWidth / 2;
        posY = new ArrayList<Integer>();
        posY.add(screenHeight / 2);
        int sep = 0;
        for (int i = 1; i <= 20; i++) {
            if (i == 1 || i == 5 || i == 7 || i == 11 || i == 18) {
                sep = 50;
            } else {
                sep = 0;
            }
            posY.add(posY.get(i - 1) + u.getDpH(170) + sep);
        }

        pText = new Paint();
        pText.setTextSize(u.getDpW(50));
        pText.setColor(Color.rgb(253, 236, 166));
        pText.setFakeBoldText(true);
        pText.setTextAlign(Paint.Align.CENTER);
        pText.setTypeface(faw);
        pTextNameCategory = new Paint();
        pTextNameCategory.setTextSize(u.getDpW(70));
        pTextNameCategory.setColor(Color.WHITE);
        pTextNameCategory.setTextAlign(Paint.Align.CENTER);
        pTextNameCategory.setTypeface(faw);
    }

    /**
     * Credit text movement manager
     */
    public void actualizarFisica() {
        for (int i = 0; i < posY.size(); i++) {
            if (posY.get(i) >= u.getDpH(300)) {
                posY.set(i, posY.get(i) - u.getDpH(2));
            } else {
                posY.set(i, screenHeight * 4);
            }
        }
    }

    /**
     * Paint credits properties on screen
     *
     * @param c Canvas
     */
    public void dibujar(Canvas c) {
        try {
            c.drawBitmap(background, 0, 0, null);
            c.drawBitmap(imgBuildingsShadow, 0, 0, null);
            c.drawText(context.getString(R.string.credits), screenWidth / 2, u.getDpH(200), pText2);

            c.drawText("  " + context.getString(R.string.gameicons), posX, posY.get(1), pTextNameCategory);
            c.drawText(credits[0] + "", posX, posY.get(2), pText);
            c.drawText(credits[1] + "", posX, posY.get(3), pText);
            c.drawText(credits[2] + "", posX, posY.get(4), pText);
            c.drawText("  " + context.getString(R.string.textures), posX, posY.get(5), pTextNameCategory);
            c.drawText(credits[3] + " ", posX, posY.get(6), pText);
            c.drawText("  " + context.getString(R.string.music_sounds), posX, posY.get(7), pTextNameCategory);
            c.drawText(credits[4] + " ", posX, posY.get(8), pText);
            c.drawText(credits[5] + " ", posX, posY.get(9), pText);
            c.drawText(credits[6] + " ", posX, posY.get(10), pText);
            c.drawText("  " + context.getString(R.string.people), posX, posY.get(11), pTextNameCategory);
            c.drawText(credits[7] + " ", posX, posY.get(12), pText);
            c.drawText(credits[8] + " ", posX, posY.get(13), pText);
            c.drawText(credits[9] + " ", posX, posY.get(14), pText);
            c.drawText(credits[10] + " ", posX, posY.get(15), pText);
            c.drawText(credits[11] + " ", posX, posY.get(16), pText);
            c.drawText(credits[12] + " ", posX, posY.get(17), pText);
            c.drawText("  " + context.getString(R.string.author), posX, posY.get(18), pTextNameCategory);
            c.drawText(context.getString(R.string.name), posX, posY.get(19), pTextNameCategory);
            c.drawText("" + context.getString(R.string.social_media), posX, posY.get(20), pText);
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
            case MotionEvent.ACTION_POINTER_UP:  // Al levantar un dedo que no es el último
                if (pulsa(rMenu, event) && flash) {
                    try {
                        cameraManager.setTorchMode(camID, false);
                        flash = false;
                    } catch (CameraAccessException cae) {
                    }
                }
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