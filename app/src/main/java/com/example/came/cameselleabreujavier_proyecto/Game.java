package com.example.came.cameselleabreujavier_proyecto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Random;

public class Game extends Escena {

    private ImagenesCapa[] imagenesCapa;
    private Cap c1, c2;
    private Bitmap bitmapAux, bmReflex;
    private ArrayList<Bitmap> bmBackGround, bmFloor, bmClouds, bmObstaculos;
    private Utils u;
    private ArrayList<Cloud> arrayClouds;
    private Obstaculo obstaculo;

    public Game(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);
        u = new Utils(context);

        bmBackGround = new ArrayList<>();
        bitmapAux = BitmapFactory.decodeResource(context.getResources(), R.drawable.fondo);
        bmBackGround.add(Bitmap.createScaledBitmap(bitmapAux, anchoPantalla, altoPantalla, false));
//        bitmapAux = BitmapFactory.decodeResource(context.getResources(), R.drawable.fondo);
//        bmEspejo = u.espejo(bitmapAux, true);
        bmBackGround.add(Bitmap.createScaledBitmap(bitmapAux, anchoPantalla, altoPantalla, false));

        bmFloor = new ArrayList<>();
        bitmapAux = BitmapFactory.decodeResource(context.getResources(), R.drawable.ground);
        bmFloor.add(Bitmap.createScaledBitmap(bitmapAux, anchoPantalla, altoPantalla, false));
//        bitmapAux = BitmapFactory.decodeResource(context.getResources(), R.drawable.ground);
        bmFloor.add(Bitmap.createScaledBitmap(bitmapAux, anchoPantalla, altoPantalla, false));
//
        bmClouds = new ArrayList<>();
        bitmapAux = BitmapFactory.decodeResource(context.getResources(), R.drawable.clouds);
        bmClouds.add(Bitmap.createScaledBitmap(bitmapAux, anchoPantalla / 3, altoPantalla / 5, false));
//        bitmapAux = BitmapFactory.decodeResource(context.getResources(), R.drawable.clouds);
        bmClouds.add(Bitmap.createScaledBitmap(bitmapAux, anchoPantalla / 3, altoPantalla / 5, false));
        bitmapAux = BitmapFactory.decodeResource(context.getResources(), R.drawable.nubes6);
        bmClouds.add(Bitmap.createScaledBitmap(bitmapAux, anchoPantalla / 3, altoPantalla / 5, false));
//        bitmapAux = BitmapFactory.decodeResource(context.getResources(), R.drawable.nubes6);
        bmClouds.add(Bitmap.createScaledBitmap(bitmapAux, anchoPantalla / 3, altoPantalla / 5, false));

        c1 = new Cap(context, anchoPantalla, altoPantalla, bmBackGround);
        c1.setVelocidad(-7);

        c2 = new Cap(context, anchoPantalla, altoPantalla, bmFloor);
        c2.setVelocidad(-10);
        c2.setPosY(altoPantalla / 2);

        bmObstaculos = new ArrayList<>();
        bitmapAux = BitmapFactory.decodeResource(context.getResources(), R.drawable.obstaculo1);
        bmObstaculos.add(Bitmap.createScaledBitmap(bitmapAux, anchoPantalla / 8, altoPantalla / 8, false));
        bitmapAux = BitmapFactory.decodeResource(context.getResources(), R.drawable.obstaculo2);
        bmObstaculos.add(Bitmap.createScaledBitmap(bitmapAux, anchoPantalla / 8, altoPantalla / 8, false));
        bitmapAux = BitmapFactory.decodeResource(context.getResources(), R.drawable.obstaculo3);
        bmObstaculos.add(Bitmap.createScaledBitmap(bitmapAux, anchoPantalla / 8, altoPantalla / 8, false));
        obstaculo = new Obstaculo(context, c2.getPosY() + bmObstaculos.get(0).getHeight(), c2.getVelocidad(), anchoPantalla, altoPantalla, bmObstaculos);

        arrayClouds = new ArrayList<>();
        int fin = (int) (Math.random() * 7 + 1);
        Log.i("nubes", fin + "");
        for (int i = 0; i < fin; i++) {
            arrayClouds.add(new Cloud(context, anchoPantalla, altoPantalla, bmClouds));
        }

    }

    public void actualizarFisica() {
        c1.mover();
        c2.mover();

        for (int i = 0; i < arrayClouds.size(); i++) {
            arrayClouds.get(i).mover();
        }

        obstaculo.mover();
    }

    public void dibujar(Canvas c) {
        try {
            c1.dibujar(c);
            c2.dibujar(c);

            for (int i = 0; i < bmClouds.size(); i++) {
                arrayClouds.get(i).dibujar(c);
                Log.i(arrayClouds.get(i) + "", arrayClouds.get(i).getPosX() + "--" + arrayClouds.get(i).getPosY());
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
