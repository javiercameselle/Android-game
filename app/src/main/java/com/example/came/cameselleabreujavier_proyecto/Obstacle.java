package com.example.came.cameselleabreujavier_proyecto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

public class Obstacle {

    private Context context;
    private int speed;//Speed movement
    private int posX, posXObstacle;//Position on horizontal axis
    private int posY;//Position on vertical axis
    private int drawTime = 10;
    private int screenWidth;
    private int screenHeigth;
    private Bitmap imgObstacle;//Obstacles image
    private ArrayList<Bitmap> obstacles;//Obstacles images
    private long tiempoActual = System.currentTimeMillis();
    private Paint pObst;//Obstacle image modifier
    private Paint p;//Obstacle rectangle modifier
    public Rect rectObstacle;//Rectangle that encloses obstacle image
    private boolean collisionable = true;//If character can to intersect with life icon

    /**
     * Initialize obstacle properties
     *
     * @param context      Context
     * @param posXObstacle Position X
     * @param posY         Position Y
     * @param speed        Speed
     * @param screenWidth  Screen width
     * @param screenHeight Screen height
     * @param obstacles    Obstacle images array
     */
    public Obstacle(Context context, int posXObstacle, int posY, int speed, int screenWidth, int screenHeight, ArrayList<Bitmap> obstacles) {
        this.context = context;
        this.speed = speed;
        this.posX = (int) (Math.random() * screenWidth + screenWidth) + posXObstacle;
        this.posY = posY;
        this.screenWidth = screenWidth;
        this.screenHeigth = screenHeight;
        this.obstacles = obstacles;
        this.imgObstacle = this.obstacles.get((int) (Math.random() * obstacles.size()));
        this.pObst = new Paint();
        pObst.setAlpha(255);
        p = new Paint();
        p.setColor(Color.GREEN);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5);
    }

    /**
     * Paint obstacle properties
     *
     * @param c Canvas
     */
    public void dibujar(Canvas c) {
        c.drawBitmap(imgObstacle, posX, posY, pObst);
//        c.drawRect(rectObstacle, p);
    }

    /**
     * Manage obstacle properties
     */
    public void mover() {
        rectObstacle = new Rect(posX + imgObstacle.getWidth() / 5, posY + imgObstacle.getHeight() / 5, posX + imgObstacle.getWidth() * 4 / 5, posY + imgObstacle.getHeight());
        if (System.currentTimeMillis() - tiempoActual > drawTime) {
            this.posX += speed;
            tiempoActual = System.currentTimeMillis();
            if (this.posX + imgObstacle.getWidth() < 0) {
                this.imgObstacle = obstacles.get((int) (Math.random() * obstacles.size()));
                posX = (int) (Math.random() * screenWidth * 2 + screenWidth) + posXObstacle;
            }
        }
    }

    /**
     * Returns obstacle speed
     *
     * @return Obstacle speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Set obstacle speed
     *
     * @param speed Obstacle speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Returns position on horizontal axis
     *
     * @return Position X
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Set position on horizontal axis
     *
     * @param posX Position X
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * Returns position on vertical axis
     *
     * @return Position Y
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Set position on vertical axis
     *
     * @param posY Position Y
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     * Returns obstacle image
     *
     * @return Obstacle image
     */
    public Bitmap getImgObstacle() {
        return imgObstacle;
    }

    /**
     * Set obstacle image
     *
     * @param imgObstacle Obstacle image
     */
    public void setImgObstacle(Bitmap imgObstacle) {
        this.imgObstacle = imgObstacle;
    }

    /**
     * Returns collisionable boolean value
     *
     * @return Collisionable value
     */
    public boolean isCollisionable() {
        return collisionable;
    }

    /**
     * Set collisionable boolean value
     *
     * @param collisionable Boolean value
     */
    public void setCollisionable(boolean collisionable) {
        this.collisionable = collisionable;
    }


}
