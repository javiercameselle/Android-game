package com.example.came.cameselleabreujavier_proyecto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Life {
    private Bitmap bitmapLife;//Heart icon
    private int posX;//Position on horizontal axis
    private int posY;//Position on vertical axis
    private int screenWidth;//Screen width
    private int screenHeight;//Screen height
    private int speed;//Pixels added or substracted on movement
    private boolean catched = false;//Enabled if character intersect with life icon
    private boolean collisionable = true;//If character can to intersect with life icon
    private long actualTime = System.currentTimeMillis();
    private Rect rectLife;//Rectangle that encloses life image
    private Paint p;//Life rectangle modifier
    private Utils u;//Utils class

    /**
     * Initialize live properties
     *
     * @param context      Context
     * @param screenWidth  Screen width
     * @param screenHeight Screen height
     */
    public Life(Context context, int screenWidth, int screenHeight) {
        u = new Utils(context);
        this.screenWidth = screenWidth;//1920
        this.screenHeight = screenHeight;//1080
        this.bitmapLife = BitmapFactory.decodeResource(context.getResources(), R.drawable.live);
        this.bitmapLife = Bitmap.createScaledBitmap(bitmapLife, screenWidth / 15, screenHeight / 15, false);
        this.posX = (int) (Math.random() * screenWidth * 15 + screenWidth * 2);
        this.posY = (int) (Math.random() * screenHeight * 8 / 10);
        while (this.posY < screenHeight * 6 / 10) {
            this.posY = (int) (Math.random() * screenHeight * 8 / 10);
        }
        this.catched = false;
        this.speed = (int) (Math.random() * 16 + 20);
        p = new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5);
    }

    /**
     * Life properties control
     */
    public void mover() {
//        Log.i("pos", posY + "");
        rectLife = new Rect(posX, posY, posX + bitmapLife.getWidth(), posY + bitmapLife.getHeight());
        this.posX -= speed;
        if (posX + bitmapLife.getWidth() < 0) {
            this.speed = (int) (Math.random() * 14 + 18);
            this.posX = (int) (Math.random() * screenWidth * 15 + screenWidth * 5);
            this.posY = (int) (Math.random() * screenHeight * 8 / 10);
            while (this.posY < screenHeight * 6 / 10) {
                this.posY = (int) (Math.random() * screenHeight * 8 / 10);
            }
            catched = false;
        }
        if (posX > 2000) setCollisionable(true);
    }

    /**
     * Paint life on screen
     *
     * @param c Canvas
     */
    public void dibujar(Canvas c) {
        if (!isCatched())
            c.drawBitmap(bitmapLife, posX, posY, null);
//            c.drawRect(rectLife, p);
    }

    /**
     * Returns life image
     *
     * @return Life image
     */
    public Bitmap getBitmapLife() {
        return bitmapLife;
    }

    /**
     * Set live image
     *
     * @param bitmapLife Life image
     */
    public void setBitmapLife(Bitmap bitmapLife) {
        this.bitmapLife = bitmapLife;
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
     * Returns catched boolean value
     *
     * @return Catched value
     */
    public boolean isCatched() {
        return catched;
    }

    /**
     * Set catched boolean value
     *
     * @param catched Boolean value
     */
    public void setCatched(boolean catched) {
        this.catched = catched;
    }

    /**
     * Returns life rectangle
     *
     * @return Life rectangle
     */
    public Rect getRectLife() {
        return rectLife;
    }

    /**
     * Set life rectangle
     *
     * @param rectLife Life rectangle
     */
    public void setRectLife(Rect rectLife) {
        this.rectLife = rectLife;
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
