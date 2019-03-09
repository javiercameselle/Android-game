package com.example.came.cameselleabreujavier_proyecto;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class Records extends Scene {

    private Bitmap imgBuildingsShadow, imgGold, imgSilver, imgBronze, imgMedal;
    private HashMap<String, Integer> values;
    private ArrayList<String> names;
    private ArrayList<Integer> distances;
    private int screenWidth, screenHeight;
    private Utils u;
    private Paint pText, pRect1, pRect2, pRect3;
    private Rect rectFirst, rectSecond, rectThird;

    public Records(Context context, int idScene, int screenWidth, int screenHeight) {
        super(context, idScene, screenWidth, screenHeight);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        values = new HashMap<>();
        names = new ArrayList<>();
        distances = new ArrayList<>();
        u = new Utils(context);

        rectFirst = new Rect(screenWidth / 3, screenHeight / 3, screenWidth * 2 / 3, screenHeight);
        rectSecond = new Rect(0, screenHeight / 2, screenWidth / 3, screenHeight);
        rectThird = new Rect(screenWidth * 2 / 3, screenHeight * 2 / 3, screenWidth, screenHeight);

        this.pText = new Paint();
        this.pText.setColor(Color.BLACK);
        this.pText.setTextAlign(Paint.Align.CENTER);
        this.pText.setTextSize(u.getDpW(70));

        this.pRect1 = new Paint();
        this.pRect1.setColor(Color.rgb(245, 208, 111));
        this.pRect1.setStyle(Paint.Style.FILL_AND_STROKE);

        this.pRect2 = new Paint();
        this.pRect2.setColor(Color.rgb(192, 192, 192));
        this.pRect2.setStyle(Paint.Style.FILL_AND_STROKE);

        this.pRect3 = new Paint();
        this.pRect3.setColor(Color.rgb(205, 127, 50));
        this.pRect3.setStyle(Paint.Style.FILL_AND_STROKE);

//        imgGold = BitmapFactory.decodeResource(context.getResources(), R.drawable.gold);
//        imgGold = Bitmap.createScaledBitmap(imgGold, u.getDpW(100), u.getDpH(100), false);
//        imgSilver = BitmapFactory.decodeResource(context.getResources(), R.drawable.silver);
//        imgSilver = Bitmap.createScaledBitmap(imgSilver, u.getDpW(100), u.getDpH(100), false);
//        imgBronze = BitmapFactory.decodeResource(context.getResources(), R.drawable.bronze);
//        imgBronze = Bitmap.createScaledBitmap(imgBronze, u.getDpW(100), u.getDpH(100), false);
        imgMedal = BitmapFactory.decodeResource(context.getResources(), R.drawable.medal);
        imgMedal = Bitmap.createScaledBitmap(imgMedal, u.getDpW(100), u.getDpH(100), false);

        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.moon_background);
        background = Bitmap.createScaledBitmap(background, screenWidth, screenHeight, false);

        imgBuildingsShadow = BitmapFactory.decodeResource(context.getResources(), R.drawable.darks_buildings2);
        imgBuildingsShadow = Bitmap.createScaledBitmap(imgBuildingsShadow, screenWidth, screenHeight, false);

        DataBase db = new DataBase(context, "records", null, 1);
        SQLiteDatabase database = db.getWritableDatabase();
        String query = "select * from records order by distance desc";
        Cursor c = database.rawQuery(query, null);
//        Cursor c = database.query("records", null, null, null, null, null, "distance desc", "10");
        if (c.moveToFirst()) {
            String name;
            int distance;
            int numRaws = c.getCount();
            do {
                name = c.getString(0);
                names.add(name);
//                Log.i("xxxName", name);
                distance = c.getInt(1);
                distances.add(distance);
//                Log.i("xxxDistance", distance + "");
                values.put(name, distance);
            } while (c.moveToNext());
        }
        if (database != null) {
            database.close();
        }
    }

    public void actualizarFisica() {

    }

    public void dibujar(Canvas c) {
        try {
            c.drawBitmap(background, 0, 0, null);
            c.drawBitmap(imgBuildingsShadow, 0, 0, null);
            super.dibujar(c);
            c.drawText(context.getString(R.string.records), screenWidth / 2, u.getDpH(200), pText2);
            c.drawRect(rectFirst, pRect1);
            c.drawRect(rectSecond, pRect2);
            c.drawRect(rectThird, pRect3);
            for (int i = 0; i < names.size(); i++) {
                if (i == 0) {
//                    this.pText.setColor(Color.rgb(245, 208, 111));
                    c.drawBitmap(imgMedal, rectFirst.centerX() - imgMedal.getWidth() / 2, rectFirst.centerY() - imgMedal.getHeight() / 2, null);
                    c.drawText(names.get(i) + " - " + distances.get(i) + "m.", rectFirst.centerX(), rectFirst.top + u.getDpH(100), this.pText);
                }
                if (i == 1) {
//                    this.pText.setColor(Color.rgb(192, 192, 192));
                    c.drawBitmap(imgMedal, rectSecond.centerX() - imgMedal.getWidth() / 2, rectSecond.centerY() - imgMedal.getHeight() / 2, null);
                    c.drawText(names.get(i) + " - " + distances.get(i) + "m.", rectSecond.centerX(), rectSecond.top + u.getDpH(100), this.pText);
                }
                if (i == 2) {
//                    this.pText.setColor(Color.rgb(205, 127, 50));
                    c.drawBitmap(imgMedal, rectThird.centerX() - imgMedal.getWidth() / 2, rectThird.centerY() - imgMedal.getHeight() / 2, null);
                    c.drawText(names.get(i) + " - " + distances.get(i) + "m.", rectThird.centerX(), rectThird.top + u.getDpH(100), this.pText);
                }
//
//                c.drawText( e.getKey() + " - " + e.getValue(), u.getDpW(200), u.getDpH((i + 1) * 150), this.pText);
            }


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