package com.example.came.cameselleabreujavier_proyecto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Locale;

public class Help extends Scene {

    private Bitmap imgBuildingsShadow, imgMenu, imgGame, imgOptions, imgRecords, imgSave, imgNext, imgPrevious;
    private String language, menu, game, records, save, options;
    private int width, height, screenWidth, screenHeight, index = 0;
    private int[] posY, posX;
    private Utils u;
    private Paint pText, pRectangle;
    private Bitmap[] imgs;
    private Rect next, previous, text;
    private String[] texts;

    public Help(Context context, int idEscena, int screenWidth, int screenHeight) {
        super(context, idEscena, screenWidth, screenHeight);
        u = new Utils(context);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        width = screenWidth / 10;
        height = screenHeight / 12;
        index = 0;
        pText = new Paint();
        pText.setTextAlign(Paint.Align.CENTER);
        pText.setColor(Color.BLACK);
        pText.setTextSize(u.getDpW(25));
        pText.setFakeBoldText(true);
        pRectangle = new Paint();
        pRectangle.setColor(Color.rgb(253, 236, 166));

        next = new Rect(width * 7, height * 5, width * 8, height * 7);
        previous = new Rect(0, height * 5, width, height * 7);
        text = new Rect(width, height * 9, width * 9, height * 11);

        imgNext = BitmapFactory.decodeResource(context.getResources(), R.drawable.next);
        imgNext = Bitmap.createScaledBitmap(imgNext, width, height * 2, false);

        imgNext = BitmapFactory.decodeResource(context.getResources(), R.drawable.previous);
        imgNext = Bitmap.createScaledBitmap(imgNext, width, height * 2, false);

        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.dark_background);
        background = Bitmap.createScaledBitmap(background, screenWidth, screenHeight, false);

//        imgBuildingsShadow = BitmapFactory.decodeResource(context.getResources(), R.drawable.darks_buildings2);
//        imgBuildingsShadow = Bitmap.createScaledBitmap(imgBuildingsShadow, screenWidth, screenHeight, false);


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

        imgs = new Bitmap[5];
        imgs[0] = imgMenu;
        imgs[1] = imgOptions;
        imgs[2] = imgGame;
        imgs[3] = imgSave;
        imgs[4] = imgRecords;

//
//        posY = new int[5];
//        posX = new int[5];
//        for (int i = 0; i < posX.length; i++) {
//            if (i % 2 == 0) {
//                posX[i] = width;
//            } else {
//                posX[i] = width * 3;
//            }
//        }
//        posY[0] = height;
//        for (int i = 1; i < posY.length; i++) {
//            posY[i] = posY[i - 1] + u.getDpH(200);
//        }

    }

    public void actualizarFisica() {
    }

    public void dibujar(Canvas c) {
        try {
            c.drawBitmap(background, 0, 0, null);
//            c.drawBitmap(imgBuildingsShadow, 0, 0, null);
            super.dibujar(c);

//            c.drawBitmap(imgPrevious, previous.centerX() - imgPrevious.getWidth() / 2, previous.centerY() - imgPrevious.getHeight() / 2, null);
//            c.drawBitmap(imgNext, next.centerX() - imgNext.getWidth() / 2, next.centerY() - imgNext.getHeight() / 2, null);
            c.drawBitmap(imgs[index], width * 2, height, null);
            c.drawRect(text, pRectangle);
            c.drawText(texts[index], text.centerX(), text.centerY() + u.getDpH((int) (pText.getTextSize() / 2)), pText);


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
            case MotionEvent.ACTION_POINTER_UP:  // Al levantar un dedo que no es el último
//                if (pulsa(next, event)) {
//
//                } else if (pulsa(previous, event)) {
//
//                }
                if (pulsa(new Rect(0, 0, screenWidth / 2, screenHeight), event)) {
                    if (index > 0) {
                        index--;
                    } else {
                        index = imgs.length - 1;
                    }
                } else if (pulsa(new Rect(screenWidth / 2, 0, screenWidth, screenHeight), event)) {
                    if (index < imgs.length - 1) {
                        index++;
                    } else {
                        index = 0;
                    }
                }
                break;

            case MotionEvent.ACTION_MOVE: // Se mueve alguno de los dedos

                break;
            default:
                Log.i("Otra acción", "Acción no definida: " + accion);
        }

        int idPadre = super.onTouchEvent(event);
        if (idPadre != idScene) {
            return idPadre;
        }
        return idScene;
    }

}
