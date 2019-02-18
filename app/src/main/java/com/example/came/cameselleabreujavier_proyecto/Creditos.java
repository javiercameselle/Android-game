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

public class Creditos extends Escena {

    private Paint pTexto;
    private Bitmap imgBuildingsShadow;
    private int posX;
    private int[] posY;
    private Utils u;

    public Creditos(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);
        u = new Utils(context);
        fondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.dark_background);
        fondo = Bitmap.createScaledBitmap(fondo, anchoPantalla, altoPantalla, false);

        imgBuildingsShadow = BitmapFactory.decodeResource(context.getResources(), R.drawable.darks_buildings);
        imgBuildingsShadow = Bitmap.createScaledBitmap(imgBuildingsShadow, anchoPantalla, altoPantalla, false);

        this.posX=anchoPantalla/2;
        posY=new int[6];
        posY[0]=altoPantalla/2;
        for(int i=1;i<posY.length;i++){
            posY[i]=posY[i-1]+u.getDpH(200);
        }

        pTexto = new Paint();
        pTexto.setTextSize(u.getDpW(50));
        pTexto.setColor(Color.WHITE);
    }

    public void actualizarFisica() {
        for(int i=0;i<posY.length;i++){
            posY[i]-=u.getDpH(2);
        }
    }

    public void dibujar(Canvas c) {
        try {
            c.drawBitmap(fondo, 0, 0, null);
            c.drawBitmap(imgBuildingsShadow, 0, 0, null);
            c.drawText(context.getString(R.string.thanks), u.getDpW(100), posY[0], pTexto);
            c.drawText(context.getString(R.string.gameicons),u.getDpW(100),posY[1],pTexto);
            c.drawText(context.getString(R.string.opengameart),u.getDpW(100),posY[2],pTexto);
            c.drawText(context.getString(R.string.teacher),u.getDpW(100),posY[3],pTexto);
            c.drawText(context.getString(R.string.people),u.getDpW(100),posY[4],pTexto);
            c.drawText("Javier Cameselle",u.getDpW(100),posY[5],pTexto);
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