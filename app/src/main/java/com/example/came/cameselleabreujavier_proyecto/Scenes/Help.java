package com.example.came.cameselleabreujavier_proyecto.Scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import com.example.came.cameselleabreujavier_proyecto.R;
import com.example.came.cameselleabreujavier_proyecto.Scene;
import com.example.came.cameselleabreujavier_proyecto.SceneUtils.Utils;

import java.util.Locale;

import static com.example.came.cameselleabreujavier_proyecto.MainActivity.faw;

/**
 * Game screenshots are shown to help player
 */

public class Help extends Scene {

    private Bitmap imgMenu;//Main menu screenshot
    private Bitmap imgGame;//Game screenshot
    private Bitmap imgOptions;//Options screenshot
    private Bitmap imgRecords;//Records screenshot
    private Bitmap imgSave;//Save screenshot
    private Bitmap imgNext;//Next image icon
    private Bitmap imgPrevious;//Previous image icon
    private String language;//System/game language
    private int width;//Divided screen width
    private int height;//Divided screen height
    private int screenWidth;//Screen width
    private int screenHeight;//Screen height
    private int index = 0;//Number to control positions
    private int[] posY, posX;//Texts positions
    private Utils u;//Utils class
    private Paint pText, pRectangle;//Rectangles and texts modifiers
    private Bitmap[] helpImages;//Screenshots array
    private Rect next, previous, text;//Buttons rectangles
    private String[] texts;//Texts array

    /**
     * @param context      Context
     * @param idScene      Scene ID
     * @param screenWidth  Screen width
     * @param screenHeight Screen height
     */
    public Help(Context context, int idScene, int screenWidth, int screenHeight) {
        super(context, idScene, screenWidth, screenHeight);
        u = new Utils(context);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        width = screenWidth / 10;
        height = screenHeight / 12;
        index = 0;
        pText = new Paint();
        pText.setTextAlign(Paint.Align.CENTER);
        pText.setColor(Color.BLACK);
        pText.setTextSize(u.getDpW(75));
        pText.setFakeBoldText(true);
        pText.setTypeface(faw);
        pRectangle = new Paint();
        pRectangle.setColor(Color.rgb(253, 236, 166));

        next = new Rect(width * 9, height * 5, width * 10, height * 7);
        previous = new Rect(0, height * 5, width, height * 7);
        text = new Rect(width, height * 9, width * 9, height * 11);

        imgNext = BitmapFactory.decodeResource(context.getResources(), R.drawable.next);
        imgNext = Bitmap.createScaledBitmap(imgNext, width, height * 2, false);

        imgPrevious = BitmapFactory.decodeResource(context.getResources(), R.drawable.previous);
        imgPrevious = Bitmap.createScaledBitmap(imgPrevious, width, height * 2, false);

        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.dark_background);
        background = Bitmap.createScaledBitmap(background, screenWidth, screenHeight, false);

        language = Locale.getDefault().getDisplayLanguage();
        if (language.equals("español")) {
            imgMenu = BitmapFactory.decodeResource(context.getResources(), R.drawable.menu_es);
            imgMenu = Bitmap.createScaledBitmap(imgMenu, width * 6, height * 7, false);
            imgSave = BitmapFactory.decodeResource(context.getResources(), R.drawable.save_es);
            imgSave = Bitmap.createScaledBitmap(imgSave, width * 6, height * 7, false);
            imgRecords = BitmapFactory.decodeResource(context.getResources(), R.drawable.records_es);
            imgRecords = Bitmap.createScaledBitmap(imgRecords, width * 6, height * 7, false);
            imgOptions = BitmapFactory.decodeResource(context.getResources(), R.drawable.options_es);
            imgOptions = Bitmap.createScaledBitmap(imgOptions, width * 6, height * 7, false);
            imgGame = BitmapFactory.decodeResource(context.getResources(), R.drawable.game_es);
            imgGame = Bitmap.createScaledBitmap(imgGame, width * 6, height * 7, false);
        } else {
            imgMenu = BitmapFactory.decodeResource(context.getResources(), R.drawable.menu_en);
            imgMenu = Bitmap.createScaledBitmap(imgMenu, width * 6, height * 7, false);
            imgSave = BitmapFactory.decodeResource(context.getResources(), R.drawable.save_en);
            imgSave = Bitmap.createScaledBitmap(imgSave, width * 6, height * 7, false);
            imgRecords = BitmapFactory.decodeResource(context.getResources(), R.drawable.records_en);
            imgRecords = Bitmap.createScaledBitmap(imgRecords, width * 6, height * 7, false);
            imgOptions = BitmapFactory.decodeResource(context.getResources(), R.drawable.options_en);
            imgOptions = Bitmap.createScaledBitmap(imgOptions, width * 6, height * 7, false);
            imgGame = BitmapFactory.decodeResource(context.getResources(), R.drawable.game_en);
            imgGame = Bitmap.createScaledBitmap(imgGame, width * 6, height * 7, false);
        }
        texts = new String[5];
        texts[0] = context.getString(R.string.menu_help);
        texts[1] = context.getString(R.string.options_help);
        texts[2] = context.getString(R.string.game_help);
        texts[3] = context.getString(R.string.save_help);
        texts[4] = context.getString(R.string.records_help);

        helpImages = new Bitmap[5];
        helpImages[0] = imgMenu;
        helpImages[1] = imgOptions;
        helpImages[2] = imgGame;
        helpImages[3] = imgSave;
        helpImages[4] = imgRecords;

    }

    public void actualizarFisica() {

    }

    /**
     * Paint images and text to explain differents game screens
     *
     * @param c Canvas
     */
    public void dibujar(Canvas c) {
        try {
            c.drawBitmap(background, 0, 0, null);
            super.dibujar(c);

            c.drawBitmap(imgPrevious, previous.centerX() - imgPrevious.getWidth() / 2, previous.centerY() - imgPrevious.getHeight() / 2, null);
            c.drawBitmap(imgNext, next.centerX() - imgNext.getWidth() / 2, next.centerY() - imgNext.getHeight() / 2, null);
            c.drawBitmap(helpImages[index], width * 2, height, null);
            c.drawRect(text, pRectangle);
            c.drawText(texts[index], text.centerX(), text.centerY() + u.getDpH((int) (pText.getTextSize() / 2)), pText);


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
                if (/*pulsa(new Rect(0, 0, screenWidth / 2, screenHeight), event) ||*/  pulsa(previous, event)) {
                    if (index > 0) {
                        index--;
                    } else {
                        index = helpImages.length - 1;
                    }
                } else if (/*pulsa(new Rect(screenWidth / 2, 0, screenWidth, screenHeight), event) ||*/ pulsa(next, event)) {
                    if (index < helpImages.length - 1) {
                        index++;
                    } else {
                        index = 0;
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
