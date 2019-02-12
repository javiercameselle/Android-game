package com.example.came.cameselleabreujavier_proyecto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class Personaje {
    private Bitmap[] bmRun, bmJump, bmCollision, bmDead;
    private int posX, posY, initialPosY, speed, drawTime = 50, frameTime = 100, jumpTime = 200, index = 0, screenWidth, screenHeight;
    private long actualTime = System.currentTimeMillis(), pulsacionTime;
    private boolean jumping = false, dead = false, collision = false;
    private Rect r;
    private Paint p;
    Utils u;

    public Personaje(Context context, Bitmap[] bmRun, Bitmap[] bmJump, Bitmap[] bmCollision, Bitmap[] bmDead, int anchoPantalla, int altoPantalla, int speed, int posX, int posY) {
        u = new Utils(context);
        this.screenWidth = anchoPantalla;
        this.screenHeight = altoPantalla;
        this.bmRun = bmRun;
        this.bmJump = bmJump;
        this.bmCollision = bmCollision;
        this.bmDead = bmDead;
        this.posX = posX;
        this.posY = posY;
        this.initialPosY = posY;
        p = new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5);
    }

    public void mover() {
//        if (System.currentTimeMillis() - actualTime > drawTime) {
//            actualTime = System.currentTimeMillis();
//        }
    }

    public void saltar() {
            if (System.currentTimeMillis() < (pulsacionTime + 700)) {
                posY -= u.getDpH(10);
            } else {
                posY += u.getDpH(10);
            }
            if (initialPosY == posY) {
                jumping = !jumping;
                index = 0;
            }
//            Log.i("PosY", posY + "");
    }

    public void cambioFrame() {
        if (System.currentTimeMillis() - actualTime > frameTime) {
            if (jumping) {
                if (index < bmJump.length - 1)
                    index++;
            } else {
                index++;
                if (index == bmRun.length) index = 0;
            }
            actualTime = System.currentTimeMillis();
        }
    }

    public void dibujar(Canvas c) {
        if (!jumping) {
            c.drawBitmap(bmRun[index], posX, posY, null);
            c.drawRect(new Rect(posX + bmRun[index].getWidth() / 4, posY + bmRun[index].getHeight() / 2,
                            posX + bmRun[index].getWidth() * 2 / 3, posY + bmRun[index].getHeight())
                    , p);
        } else if (jumping) {
            c.drawBitmap(bmJump[index], posX, posY, null);
            c.drawRect(new Rect(posX + bmJump[index].getWidth() / 4, posY + bmJump[index].getHeight() / 2,
                            posX + bmJump[index].getWidth() * 2 / 3, posY + bmJump[index].getHeight())
                    , p);
        }
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

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public long getPulsacionTime() {
        return pulsacionTime;
    }

    public void setPulsacionTime() {
        this.pulsacionTime = System.currentTimeMillis();
    }

    public int getFrameTime() {
        return frameTime;
    }

    public void setFrameTime(int frameTime) {
        this.frameTime = frameTime;
    }
}
