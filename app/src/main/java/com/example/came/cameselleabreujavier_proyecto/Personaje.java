package com.example.came.cameselleabreujavier_proyecto;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class Personaje {
    private Bitmap[] run, jump, collision, dead;
    private int posX, posY, speed, drawTime = 50, frameTime = 100, index = 0, screenWidth, screenHeight;
    private long actualTime = System.currentTimeMillis();

    public Personaje(Bitmap[] run, Bitmap[] jump, Bitmap[] collision, Bitmap[] dead, int anchoPantalla, int altoPantalla, int speed, int posX, int posY) {
        this.screenWidth = anchoPantalla;
        this.screenHeight = altoPantalla;
        this.run = run;
        this.jump = jump;
        this.collision = collision;
        this.dead = dead;
        this.posX = posX;
        this.posY = posY;
    }

    public void mover() {
//        if (System.currentTimeMillis() - actualTime > drawTime) {
//            this.posX += this.speed;
//            if (this.posX > screenWidth) {
//                //LEVEL++
//            }
//            actualTime = System.currentTimeMillis();
//        }
    }

    public void cambioFrame() {
        if (System.currentTimeMillis() - actualTime > frameTime) {
            index++;
            Log.i("INDICE: ", index + "");
            if (index >= run.length) index = 0;
            actualTime = System.currentTimeMillis();
        }
    }

    public void dibujar(Canvas c) {
        c.drawBitmap(run[index], posX, posY, null);
        Log.i("Se dibuja", run[index] + "");
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
