package com.example.came.cameselleabreujavier_proyecto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

public class Cloud {

    private Context context;
    private int velocidad, posX, posY, alfa, tiempoDibujado = 20;
    private long tiempoActual = System.currentTimeMillis();
    private Bitmap imgCloud;
    private ArrayList<Bitmap> bitmapNubes;
    private int altoPantalla, anchoPantalla, minRandom=6, maxRandom=9;
    private Paint pNube;
    private Utils u;

    public Cloud(Context context, int anchoPantalla, int altoPantalla, ArrayList<Bitmap> bitmapNubes) {
        u=new Utils(context);
        this.context = context;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        this.velocidad = (int) (Math.random() * maxRandom + minRandom);
        this.posX = (int) (Math.random() * anchoPantalla + anchoPantalla);
        this.posY = (int) (Math.random() * altoPantalla / 4);
        this.alfa = (int) (Math.random() * 255 + 175);
        this.pNube = new Paint();
        this.pNube.setAlpha(this.alfa);
        this.bitmapNubes = bitmapNubes;
        this.imgCloud = bitmapNubes.get((int) (Math.random() * bitmapNubes.size()));
    }


    public void dibujar(Canvas c) {
        c.drawBitmap(imgCloud, posX, posY, pNube);
    }

    public void mover() {

        if (System.currentTimeMillis() - tiempoActual > tiempoDibujado) {
            this.posX += u.getDpW(velocidad) * -1;
            tiempoActual = System.currentTimeMillis();
            if (this.posX + imgCloud.getWidth() < 0) {
                this.velocidad = (int) (Math.random() * maxRandom + minRandom);
                this.imgCloud = bitmapNubes.get((int) (Math.random() * bitmapNubes.size()));
                posY = (int) (Math.random() * altoPantalla / 4);
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

    public Bitmap getImgCloud() {
        return imgCloud;
    }

    public void setImgCloud(Bitmap imgCloud) {
        this.imgCloud = imgCloud;
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

    public int getMinRandom() {
        return minRandom;
    }

    public void setMinRandom(int minRandom) {
        this.minRandom = minRandom;
    }

    public int getMaxRandom() {
        return maxRandom;
    }

    public void setMaxRandom(int maxRandom) {
        this.maxRandom = maxRandom;
    }
}
