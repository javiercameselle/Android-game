package com.example.came.cameselleabreujavier_proyecto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Timer;

import static com.example.came.cameselleabreujavier_proyecto.Game.pause;

public class Character {

    private Context context;
    private Bitmap bmAux;
    private Bitmap[] bmLives, bmRun, bmJump, bmCollision, bmDead;
    private int cont = 0, lives, posX, posY, initialPosY, speed, drawTime = 50, frameTime = 100, jumpTime = 200, index = 0, screenWidth, screenHeight;
    static int metres;
    private long actualTime = System.currentTimeMillis(), pulsacionTime, metresTime = System.currentTimeMillis(), deadTime;
    private boolean jumping = false, dead = false, broke = false;
    public Rect rectPersonaje;
    private Paint p, pText;
    private Utils u;

    public Character(Context context, Bitmap[] bmRun, Bitmap[] bmJump, Bitmap[] bmCollision, Bitmap[] bmDead, int anchoPantalla, int altoPantalla, int speed, int posX, int posY) {
        this.context = context;
        u = new Utils(context);
        this.lives = 3;
        this.setMetres(0);
        this.screenWidth = anchoPantalla;
        this.screenHeight = altoPantalla;
        this.bmRun = bmRun;
        this.bmJump = bmJump;
//        this.bmCollision = bmCollision;
        this.bmDead = bmDead;
        this.posX = posX;
        this.posY = posY;
        this.initialPosY = posY;
        bmLives = u.getFrames(4, "lives", "lives", u.getDpH(300));
        p = new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5);
        pText = new Paint();
        pText.setTextSize(u.getDpW(100));
        pText.setColor(Color.BLACK);
    }

    public void jump() {
        if (System.currentTimeMillis() < (pulsacionTime + 700)) {
            posY -= u.getDpH(10);
            cont++;
        } else {
            posY += u.getDpH(10);
        }
        if (initialPosY == posY) {
            jumping = false;
            index = 0;
        }
    }

    public void mover() {
        if (System.currentTimeMillis() - actualTime > frameTime) {
            if (jumping && !dead) {
                rectPersonaje = new Rect(posX + bmJump[index].getWidth() / 4, posY,
                        posX + bmJump[index].getWidth() * 2 / 3, posY + bmJump[index].getHeight());
                if (index < bmJump.length - 1)
                    index++;
            } else if (jumping && dead) {
                if (index < bmDead.length - 1)
                    index++;
            } else {
                rectPersonaje = new Rect(posX + bmRun[index].getWidth() / 4, posY,
                        posX + bmRun[index].getWidth() * 2 / 3, posY + bmRun[index].getHeight());
                index++;
                if (index == bmRun.length) index = 0;
            }
            actualTime = System.currentTimeMillis();
        }
    }

    public void dibujar(Canvas c) {
        if (!dead && System.currentTimeMillis() - metresTime > 500 && !pause) {
            setMetres(getMetres() + 1);
            metresTime = System.currentTimeMillis();
        }
        c.drawBitmap(bmLives[lives], u.getDpW(300), u.getDpH(50), null);
        c.drawText(context.getString(R.string.distance) + ": " + metres + " m", u.getDpW(screenWidth) * 2 / 5, u.getDpH(100), pText);
        if (isBroke()) {
            p.setAlpha((int) (Math.random() * 255 + 100));
        } else {
            p.setAlpha(255);
        }
        if (jumping && !dead) {
            c.drawBitmap(bmJump[index], posX, posY, p);
//            c.drawRect(rectPersonaje, p);
        } else if (jumping && dead) {
            c.drawBitmap(bmDead[index], posX, posY, p);
        } else {
            c.drawBitmap(bmRun[index], posX, posY, p);
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

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public long getDeadTime() {
        return deadTime;
    }

    public void setDeadTime() {
        this.deadTime = System.currentTimeMillis();
    }

    public int getInitialPosY() {
        return initialPosY;
    }

    public void setInitialPosY(int initialPosY) {
        this.initialPosY = initialPosY;
    }
}
