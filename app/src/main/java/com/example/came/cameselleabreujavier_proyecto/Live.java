package com.example.came.cameselleabreujavier_proyecto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;

public class Live {
    private Bitmap bitmapLive;
    private int posX, posY, screenWidth, screenHeight, speed, max, min;
    private boolean catched, collisionable = true;
    private long actualTime = System.currentTimeMillis();
    private Rect rectLive;
    private Paint p;
    private Utils u;
    private Random r;

    public Live(Context context, int screenWidth, int screenHeight) {
        u = new Utils(context);
        r = new Random();
        this.bitmapLive = BitmapFactory.decodeResource(context.getResources(), R.drawable.live);
        this.bitmapLive = Bitmap.createScaledBitmap(bitmapLive, screenWidth / 15, screenHeight / 15, false);
        max = screenHeight * 7 / 10;
        min = screenHeight * 5 / 10;
//        this.posX = (int) (Math.random() * screenWidth * 15 + screenWidth * 2);
        this.posX = screenWidth;
        this.posY = (int)(Math.random());
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.catched = false;
        this.speed = (int) (Math.random() * 16 + 20);
        p = new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5);
    }

    public void mover() {

        Log.i("pos", posY + "");
        Log.i("posPantalla", screenWidth + "-" + screenHeight);
        rectLive = new Rect(posX, posY, posX + bitmapLive.getWidth(), posY + bitmapLive.getHeight());
        this.posX -= speed;
        if (posX + bitmapLive.getWidth() < 0) {
            this.speed = (int) (Math.random() * 14 + 18);
//            this.posX = (int) (Math.random() * screenWidth * 15 + screenWidth * 5);
            this.posX = screenWidth * 2;
            this.posY = (int)(Math.random()*1080+700);
            catched = false;
        }
        if (posX > 2000) setCollisionable(true);
    }

    public void dibujar(Canvas c) {
        if (!isCatched())
            c.drawBitmap(bitmapLive, posX, posY, null);
//            c.drawRect(rectLive, p);
    }

    public Bitmap getBitmapLive() {
        return bitmapLive;
    }

    public void setBitmapLive(Bitmap bitmapLive) {
        this.bitmapLive = bitmapLive;
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

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public boolean isCatched() {
        return catched;
    }

    public void setCatched(boolean catched) {
        this.catched = catched;
    }

    public Rect getRectLive() {
        return rectLive;
    }

    public void setRectLive(Rect rectLive) {
        this.rectLive = rectLive;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public boolean isCollisionable() {
        return collisionable;
    }

    public void setCollisionable(boolean collisionable) {
        this.collisionable = collisionable;
    }
}
