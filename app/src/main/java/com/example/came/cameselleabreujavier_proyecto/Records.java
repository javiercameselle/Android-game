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
    private ArrayList<String> nombres;
    private ArrayList<Integer> distancias;
    private int[] posY;
    private Utils u;
    private Paint pTexto, pRect1, pRect2, pRect3;
    private Rect rectFirst, rectSecond, rectThird;

    public Records(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);
        values = new HashMap<>();
        nombres = new ArrayList<>();
        distancias = new ArrayList<>();
        u = new Utils(context);

        rectFirst = new Rect(anchoPantalla / 3, altoPantalla / 3, anchoPantalla * 2 / 3, altoPantalla);
        rectSecond = new Rect(0, altoPantalla / 2, anchoPantalla / 3, altoPantalla);
        rectThird = new Rect(anchoPantalla * 2 / 3, altoPantalla * 2 / 3, anchoPantalla, altoPantalla);

        this.pTexto = new Paint();
        this.pTexto.setColor(Color.BLACK);
        this.pTexto.setTextAlign(Paint.Align.CENTER);
        this.pTexto.setTextSize(u.getDpW(70));

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

        fondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.moon_background);
        fondo = Bitmap.createScaledBitmap(fondo, anchoPantalla, altoPantalla, false);

        imgBuildingsShadow = BitmapFactory.decodeResource(context.getResources(), R.drawable.darks_buildings2);
        imgBuildingsShadow = Bitmap.createScaledBitmap(imgBuildingsShadow, anchoPantalla, altoPantalla, false);

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
                nombres.add(name);
//                Log.i("xxxName", name);
                distance = c.getInt(1);
                distancias.add(distance);
//                Log.i("xxxDistance", distance + "");
                values.put(name, distance);
            } while (c.moveToNext());
//            posY = new int[numRaws];
        }
        if (database != null) {
            database.close();
        }
    }

    public void actualizarFisica() {

    }

    public void dibujar(Canvas c) {
        try {
            c.drawBitmap(fondo, 0, 0, null);
            c.drawBitmap(imgBuildingsShadow, 0, 0, null);
            c.drawRect(rectFirst, pRect1);
            c.drawRect(rectSecond, pRect2);
            c.drawRect(rectThird, pRect3);
//            Iterator<Map.Entry<String, Integer>> it = values.entrySet().iterator();
//            for (int i = 0; i <= values.size(); i++) {
            for (int i = 0; i <= nombres.size(); i++) {
//                Map.Entry<String, Integer> e = it.next();
                if (i == 0) {
//                    this.pTexto.setColor(Color.rgb(245, 208, 111));
                    c.drawBitmap(imgMedal, rectFirst.centerX() - imgMedal.getWidth() / 2, rectFirst.centerY() - imgMedal.getHeight() / 2, null);
//                    c.drawText(e.getKey() + " - " + e.getValue(), rectFirst.centerX(), rectFirst.top + u.getDpH(100), this.pTexto);
                    c.drawText(nombres.get(i) + " - " + distancias.get(i) + "m.", rectFirst.centerX(), rectFirst.top + u.getDpH(100), this.pTexto);
                }
                if (i == 1) {
//                    this.pTexto.setColor(Color.rgb(192, 192, 192));
                    c.drawBitmap(imgMedal, rectSecond.centerX() - imgMedal.getWidth() / 2, rectSecond.centerY() - imgMedal.getHeight() / 2, null);
//                    c.drawText(e.getKey() + " - " + e.getValue(), rectSecond.centerX(), rectSecond.top + u.getDpH(100), this.pTexto);
                    c.drawText(nombres.get(i) + " - " + distancias.get(i) + "m.", rectSecond.centerX(), rectSecond.top + u.getDpH(100), this.pTexto);
                }
                if (i == 2) {
//                    this.pTexto.setColor(Color.rgb(205, 127, 50));
                    c.drawBitmap(imgMedal, rectThird.centerX() - imgMedal.getWidth() / 2, rectThird.centerY() - imgMedal.getHeight() / 2, null);
//                    c.drawText(e.getKey() + " - " + e.getValue(), rectThird.centerX(), rectThird.top + u.getDpH(100), this.pTexto);
                    c.drawText(nombres.get(i) + " - " + distancias.get(i) + "m.", rectThird.centerX(), rectThird.top + u.getDpH(100), this.pTexto);
                }
//
//                c.drawText( e.getKey() + " - " + e.getValue(), u.getDpW(200), u.getDpH((i + 1) * 150), this.pTexto);
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