package com.example.came.cameselleabreujavier_proyecto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

public class Obstaculo {

    private Context context;
    private int speed, posX, posY, drawTime, screenWidth, screenHeigth;
    private Bitmap imgObstacle;
    private ArrayList<Bitmap> obstacles;
    private long tiempoActual = System.currentTimeMillis();

    public Obstaculo(Context context, int posY, int speed, int screenWidth, int screenHeigth, ArrayList<Bitmap> obstacles) {
        this.context = context;
        this.speed = -10;
        this.posX = (int) (Math.random() * screenWidth + screenWidth);
        this.posY = posY;
        this.drawTime = drawTime;
        this.screenWidth = screenWidth;
        this.screenHeigth = screenHeigth;
        this.obstacles = obstacles;
        this.imgObstacle = this.obstacles.get((int) (Math.random() * obstacles.size()));
    }

    public void dibujar(Canvas c) {
        c.drawBitmap(imgObstacle, posX, posY, null);
    }
    public void mover(){
        if (System.currentTimeMillis() - tiempoActual > drawTime) {
            this.posX -= speed;
            tiempoActual = System.currentTimeMillis();
            if (this.posX + imgObstacle.getWidth() < 0) {
                this.imgObstacle = obstacles.get((int) (Math.random() * obstacles.size()));
                posX = (int) (Math.random() * screenWidth * 3 + screenWidth);
            }
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getDrawTime() {
        return drawTime;
    }

    public void setDrawTime(int drawTime) {
        this.drawTime = drawTime;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeigth() {
        return screenHeigth;
    }

    public void setScreenHeigth(int screenHeigth) {
        this.screenHeigth = screenHeigth;
    }

    public Bitmap getImgObstacle() {
        return imgObstacle;
    }

    public void setImgObstacle(Bitmap imgObstacle) {
        this.imgObstacle = imgObstacle;
    }

    public ArrayList<Bitmap> getObstacles() {
        return obstacles;
    }

    public void setObstacles(ArrayList<Bitmap> obstacles) {
        this.obstacles = obstacles;
    }

    public long getTiempoActual() {
        return tiempoActual;
    }

    public void setTiempoActual(long tiempoActual) {
        this.tiempoActual = tiempoActual;
    }
}
