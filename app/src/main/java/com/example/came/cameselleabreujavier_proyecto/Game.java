package com.example.came.cameselleabreujavier_proyecto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

public class Game extends Escena {

    private Cap backgroundCap, floorCap, buildingsCap;
    private Bitmap bitmapAux, imgFloor, imgCloud, imgBuildings, imgFondo, imgObstacle, imgReflex;
    private ArrayList<Bitmap> bmBackGround, bmFloor, bmClouds, bmObstaculos, bmBuildings;
    private Utils u;
    private Obstaculo obstaculo;
    private ArrayList<Cloud> arrayClouds;
    private Personaje p;
    private Bitmap[] bmDead, bmJump, bmRun, bmColision;

    public Game(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);
        u = new Utils(context);

        //fondo
//        bmBackGround = new ArrayList<>();
        imgFondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.game_dark_background);
        imgFondo = Bitmap.createScaledBitmap(imgFondo, anchoPantalla, altoPantalla, false);
//        bmBackGround.add(imgFondo);
//        imgReflex = u.espejo(imgFondo, true);
//        bmBackGround.add(Bitmap.createScaledBitmap(imgFondo, anchoPantalla, altoPantalla, false));
//        backgroundCap = new Cap(context, anchoPantalla, altoPantalla, bmBackGround);
//        backgroundCap.setVelocidad(-4);

        //buildings
        bmBuildings = new ArrayList<>();
        imgBuildings = BitmapFactory.decodeResource(context.getResources(), R.drawable.buildings);
        imgBuildings = Bitmap.createScaledBitmap(imgBuildings, anchoPantalla, altoPantalla, false);
        bmBuildings.add(imgBuildings);
        bmBuildings.add(imgBuildings);
        buildingsCap = new Cap(context, anchoPantalla, altoPantalla, bmBuildings);
        buildingsCap.setVelocidad(-6);

        //suelo
        bmFloor = new ArrayList<>();
        imgFloor = BitmapFactory.decodeResource(context.getResources(), R.drawable.suelo);
        bmFloor.add(Bitmap.createScaledBitmap(imgFloor, anchoPantalla, altoPantalla / 6, false));
        bmFloor.add(Bitmap.createScaledBitmap(imgFloor, anchoPantalla, altoPantalla / 6, false));
        floorCap = new Cap(context, anchoPantalla, altoPantalla, bmFloor);
        floorCap.setVelocidad(-16);
        floorCap.setPosY(altoPantalla * 7 / 8);

        //nubes
        bmClouds = new ArrayList<>();
        arrayClouds = new ArrayList<>();
        int fin = (int) (Math.random() * 7 + 2);
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

        //obstaculo
        bmObstaculos = new ArrayList<>();
        imgObstacle = BitmapFactory.decodeResource(context.getResources(), R.drawable.obstaculo1);
        bmObstaculos.add(Bitmap.createScaledBitmap(imgObstacle, anchoPantalla / 10, altoPantalla / 8, false));
        imgObstacle = BitmapFactory.decodeResource(context.getResources(), R.drawable.obstaculo2);
        bmObstaculos.add(Bitmap.createScaledBitmap(imgObstacle, anchoPantalla / 10, altoPantalla / 8, false));
        imgObstacle = BitmapFactory.decodeResource(context.getResources(), R.drawable.obstaculo3);
        bmObstaculos.add(Bitmap.createScaledBitmap(imgObstacle, anchoPantalla / 10, altoPantalla / 8, false));
        obstaculo = new Obstaculo(context, floorCap.getPosY() - bmObstaculos.get(0).getHeight(), floorCap.getVelocidad(), anchoPantalla, altoPantalla, bmObstaculos);


        bmDead = u.getFrames(8, "dead", "pDead", 200);
        bmJump = u.getFrames(5, "jump", "pJump", 200);
        bmRun = u.getFrames(5, "run", "pRun", 200);
        bmColision = u.getFrames(2, "collision", "pCollision", 200);
        p = new Personaje(bmRun, bmJump, bmColision, bmDead, anchoPantalla, altoPantalla, 0, anchoPantalla / 3, floorCap.getPosY() - bmRun[0].getHeight());
    }

    public void actualizarFisica() {
//        backgroundCap.mover();
        buildingsCap.mover();
        floorCap.mover();
        for (Cloud cd : arrayClouds) {
            cd.mover();
        }
//        p.mover();
        p.cambioFrame();
        obstaculo.mover();
    }

    public void dibujar(Canvas c) {
        try {
            c.drawBitmap(imgFondo, 0, 0, null);
//            backgroundCap.dibujar(c);
            for (Cloud cd : arrayClouds) {
                cd.dibujar(c);
            }
            buildingsCap.dibujar(c);
            floorCap.dibujar(c);
            p.dibujar(c);
            obstaculo.dibujar(c);
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
