package com.example.came.cameselleabreujavier_proyecto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import java.util.ArrayList;

public class Nube {

    private Context context;
    private int velocidad, posX, posY, alfa, tiempoDibujado = 20;
    private long tiempoActual = System.currentTimeMillis();
    private Bitmap bitmapNube;
    private boolean seMueve;
    private int altoPantalla, anchoPantalla;
    private Paint pNube;

    public Nube(Context context, int anchoPantalla, int altoPantalla, Bitmap bitmapNubes) {
        this.context = context;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        this.velocidad = (int) (Math.random() * 13 + 4);
        this.posX = (int) (Math.random() * anchoPantalla + anchoPantalla);
        this.posY = (int) (Math.random() * altoPantalla / 3);
        this.alfa = (int) (Math.random() * 255 + 0);
        this.pNube = new Paint();
        pNube.setAlpha(alfa);
        this.bitmapNube = bitmapNubes;
//        this.bitmapNube = bitmapsNubes.get((int) (Math.random() * bitmapsNubes.size()));
//        this.bitmapNube = Bitmap.createScaledBitmap(this.bitmapNube, anchoPantalla / 3, altoPantalla / 5, false);
    }


    public void dibujar(Canvas c) {
        c.drawBitmap(bitmapNube, posX, posY, pNube);
    }

    public void mover() {

        if (System.currentTimeMillis() - tiempoActual > tiempoDibujado) {
            this.posX += velocidad;
            tiempoActual = System.currentTimeMillis();
            if (this.posX + bitmapNube.getWidth() < 0) {
                posY = (int) (Math.random() * altoPantalla / 3);
                posX = (int) (Math.random() * anchoPantalla * 3 + anchoPantalla);
            }
        }


    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getAlfa() {
        return alfa;
    }

    public void setAlfa(int alfa) {
        this.alfa = alfa;
    }

    public int getTiempoDibujado() {
        return tiempoDibujado;
    }

    public void setTiempoDibujado(int tiempoDibujado) {
        this.tiempoDibujado = tiempoDibujado;
    }

    public long getTiempoActual() {
        return tiempoActual;
    }

    public void setTiempoActual(long tiempoActual) {
        this.tiempoActual = tiempoActual;
    }

    public Bitmap getBitmapNube() {
        return bitmapNube;
    }

    public void setBitmapNube(Bitmap bitmapNube) {
        this.bitmapNube = bitmapNube;
    }

    public boolean isSeMueve() {
        return seMueve;
    }

    public void setSeMueve(boolean seMueve) {
        this.seMueve = seMueve;
    }

    public int getAltoPantalla() {
        return altoPantalla;
    }

    public void setAltoPantalla(int altoPantalla) {
        this.altoPantalla = altoPantalla;
    }

    public int getAnchoPantalla() {
        return anchoPantalla;
    }

    public void setAnchoPantalla(int anchoPantalla) {
        this.anchoPantalla = anchoPantalla;
    }
}
