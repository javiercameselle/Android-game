package com.example.came.cameselleabreujavier_proyecto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Personaje {

    private Context context;
    private Bitmap bmAux;
    private Bitmap[] bmLives, bmRun, bmJump, bmCollision, bmDead;
    private int lives, posX, posY, initialPosY, speed, drawTime = 50, frameTime = 100, jumpTime = 200, index = 0, screenWidth, screenHeight, metres;
    private long actualTime = System.currentTimeMillis(), pulsacionTime;
    private boolean jumping = false, dead = false, collision = false;
    public Rect rectPersonaje;
    private Paint p, pText;
    private Utils u;
    private boolean broke = false;

    public Personaje(Context context, Bitmap[] bmRun, Bitmap[] bmJump, Bitmap[] bmCollision, Bitmap[] bmDead, int anchoPantalla, int altoPantalla, int speed, int posX, int posY) {
        this.context = context;
        u = new Utils(context);
        this.lives = 3;
        this.screenWidth = anchoPantalla;
        this.screenHeight = altoPantalla;
        this.bmRun = bmRun;
        this.bmJump = bmJump;
        this.bmCollision = bmCollision;
        this.bmDead = bmDead;
        this.posX = posX;
        this.posY = posY;
        this.initialPosY = posY;
        bmLives = u.getFrames(4, "lives", "lives", u.getDpH(300));
        p = new Paint();
        pText = new Paint();
        pText.setTextSize(u.getDpW(100));
        pText.setColor(Color.WHITE);
    }

    public void mover() {
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
    }

    public void cambioFrame() {
        if (System.currentTimeMillis() - actualTime > frameTime) {
            if (jumping) {
                rectPersonaje = new Rect(posX + bmJump[index].getWidth() / 4, posY + bmJump[index].getHeight() / 2,
                        posX + bmJump[index].getWidth() * 2 / 3, posY + bmJump[index].getHeight());
                if (index < bmJump.length - 1)
                    index++;
            } else {
                rectPersonaje = new Rect(posX + bmRun[index].getWidth() / 4, posY + bmRun[index].getHeight() / 2,
                        posX + bmRun[index].getWidth() * 2 / 3, posY + bmRun[index].getHeight());
                index++;
                if (index == bmRun.length) index = 0;
            }
            actualTime = System.currentTimeMillis();
        }
    }

    public void dibujar(Canvas c) {
        setMetres(getMetres()+1);
        c.drawBitmap(bmLives[lives], u.getDpW(20), u.getDpH(10), null);
        c.drawText(context.getString(R.string.distance) + ": " + metres + " m", u.getDpW(screenWidth) / 3, u.getDpH(100), pText);
        if (isBroke()) {
            p.setAlpha((int) (Math.random() * 255 + 100));
        } else {
            p.setAlpha(255);
        }
        if (!jumping) {
            c.drawBitmap(bmRun[index], posX, posY, p);
//            c.drawRect(rectPersonaje, p);
        } else if (jumping) {
            c.drawBitmap(bmJump[index], posX, posY, p);
//            c.drawRect(rectPersonaje, p);
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

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public boolean isBroke() {
        return broke;
    }

    public void setBroke(boolean broke) {
        this.broke = broke;
    }

    public int getMetres() {
        return metres;
    }

    public void setMetres(int metres) {
        this.metres = metres;
    }
}
