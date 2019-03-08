package com.example.came.cameselleabreujavier_proyecto;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.util.Log;
import android.view.MotionEvent;

import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.List;

import javax.security.auth.callback.Callback;

public class Credits extends Scene {

    private Paint pTexto;
    private Bitmap imgBuildingsShadow, imgIconito;
    private int posX;
    private String camID;
    private int[] posY;
    private Utils u;
    private CameraManager cameraManager;
    private boolean flash;

    public Credits(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);
        u = new Utils(context);
        cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {
            camID = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(camID, true);
            flash = true;
        } catch (CameraAccessException cae) {
        }

        fondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.moon_background);
        fondo = Bitmap.createScaledBitmap(fondo, anchoPantalla, altoPantalla, false);

        imgBuildingsShadow = BitmapFactory.decodeResource(context.getResources(), R.drawable.darks_buildings2);
        imgBuildingsShadow = Bitmap.createScaledBitmap(imgBuildingsShadow, anchoPantalla, altoPantalla, false);

        imgIconito = BitmapFactory.decodeResource(context.getResources(), R.drawable.running1);
        imgIconito = Bitmap.createScaledBitmap(imgIconito, anchoPantalla / 3, altoPantalla / 3, false);

        this.posX = anchoPantalla / 2;
        posY = new int[6];
        posY[0] = altoPantalla / 2;
        for (int i = 1; i < posY.length; i++) {
            posY[i] = posY[i - 1] + u.getDpH(200);
        }

        pTexto = new Paint();
        pTexto.setTextSize(u.getDpW(50));
        pTexto.setColor(Color.WHITE);
    }

    public void actualizarFisica() {
        for (int i = 0; i < posY.length; i++) {
            if (posY[i] > 0) {
                posY[i] -= u.getDpH(2);
            } else {
                posY[i] = altoPantalla * 3 / 2;
            }
        }
    }

    public void dibujar(Canvas c) {
        try {
            c.drawBitmap(fondo, 0, 0, null);
            c.drawBitmap(imgBuildingsShadow, 0, 0, null);
            c.drawBitmap(imgIconito, anchoPantalla / 3, altoPantalla * 3 / 5, null);
            c.drawText(context.getString(R.string.thanks), u.getDpW(100), posY[0], pTexto);
            c.drawText("  " + context.getString(R.string.gameicons), u.getDpW(200), posY[1], pTexto);
            c.drawText("  " + context.getString(R.string.opengameart), u.getDpW(200), posY[2], pTexto);
            c.drawText("  " + context.getString(R.string.teacher), u.getDpW(200), posY[3], pTexto);
            c.drawText("  " + context.getString(R.string.people), u.getDpW(200), posY[4], pTexto);
            c.drawText(context.getString(R.string.name), anchoPantalla - u.getDpW(500), posY[5] + u.getDpH(30), pTexto);
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
                Log.i("Otra acción", "Acción no definida: " + accion);
        }

        int idPadre = super.onTouchEvent(event);
        if (idPadre != idEscena) {
            return idPadre;
        }
        return idEscena;
    }
}