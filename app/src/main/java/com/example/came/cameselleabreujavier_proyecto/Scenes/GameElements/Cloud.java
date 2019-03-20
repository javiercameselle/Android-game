package com.example.came.cameselleabreujavier_proyecto.Scenes.GameElements;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.came.cameselleabreujavier_proyecto.SceneUtils.Utils;

import java.util.ArrayList;

/**
 * Game clouds
 */
public class Cloud {

    private Context context;
    private int speed; //Pixels added o substracted on cap movement
    private int posX;//Cloud position on horizontal axis
    private int posY;//Cloud position on vertical axis
    private int alpha;//Allow to look image with more or less transparency
    private int drawTime = 20;//Draw time control
    private long actualTime = System.currentTimeMillis();
    private Bitmap imgCloud;//Cloud image
    private ArrayList<Bitmap> bitmapClouds;//Differents clouds images
    private int screenHeight;//Screen vertical measure
    private int screenWidth;//Screen horizontal measure
    private int minRandom = 8;//Minimum random number to manage cloud speed
    private int maxRandom = 10;//Maximum random number to manage cloud speed
    private Paint pCloud;//Cloud modifier
    private Utils u;//Utils class
    private boolean background;//Allow to look clouds under/over buildings shadows

    /**
     * Initialize cloud properties
     *
     * @param context      Context
     * @param screenWidth  Screen width
     * @param screenHeight Screen height
     * @param bitmapClouds Array of clouds images
     */
    public Cloud(Context context, int screenWidth, int screenHeight, ArrayList<Bitmap> bitmapClouds) {
        u = new Utils(context);
        this.context = context;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.speed = (int) (Math.random() * maxRandom + minRandom);
        this.posX = (int) (Math.random() * screenWidth * 3 + screenWidth);
        this.posY = (int) (Math.random() * screenHeight / 4);
        this.alpha = (int) (Math.random() * 255 + 175);
        this.pCloud = new Paint();
        this.pCloud.setAlpha(this.alpha);
        this.bitmapClouds = bitmapClouds;
        this.imgCloud = bitmapClouds.get((int) (Math.random() * bitmapClouds.size()));
    }

    /**
     * Paint cloud image on screen
     *
     * @param c Canvas
     */
    public void dibujar(Canvas c) {
        c.drawBitmap(imgCloud, posX, posY, pCloud);
    }

    /**
     * Cloud movement manager
     */
    public void mover() {

        if (System.currentTimeMillis() - actualTime > drawTime) {
            this.posX += u.getDpW(speed) * -1;
            actualTime = System.currentTimeMillis();
            if (this.posX + imgCloud.getWidth() < 0) {
                if (Math.random() * 6000 > 3003) {
                    this.background = true;
                } else {
                    this.background = false;
                }
                this.speed = u.getDpW((int) (Math.random() * maxRandom + minRandom));
                this.imgCloud = bitmapClouds.get((int) (Math.random() * bitmapClouds.size()));
                posY = (int) (Math.random() * screenHeight / 4);
                posX = (int) (Math.random() * screenWidth * 3 + screenWidth);
            }
        }


    }

    /**
     * Returns cloud speed
     *
     * @return Cloud speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Set cloud speed
     *
     * @param speed Speed cloud
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Returns cloud position on horizontal axis
     *
     * @return Position X
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Set cloud position on horizontal axis
     *
     * @param posX Position X
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * Returns cloud position on vertical axis
     *
     * @return Position Y
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Set cloud position on vertical axis
     *
     * @param posY Position Y value
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     * Returns cloud alpha
     *
     * @return Alpha cloud
     */
    public int getAlpha() {
        return alpha;
    }

    /**
     * Set alpha value
     *
     * @param alpha Cloud alplha value
     */
    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    /**
     * Returns actual time
     *
     * @return Acutal time
     */
    public long getActualTime() {
        return actualTime;
    }

    /**
     * Set actual time
     */
    public void setActualTime() {
        this.actualTime = System.currentTimeMillis();
    }

    /**
     * Returns cloud image
     *
     * @return Cloud image
     */
    public Bitmap getImgCloud() {
        return imgCloud;
    }

    /**
     * Set cloud image
     *
     * @param imgCloud Cloud image
     */
    public void setImgCloud(Bitmap imgCloud) {
        this.imgCloud = imgCloud;
    }

    /**
     * Returns minimum
     *
     * @return Minimum
     */
    public int getMinRandom() {
        return minRandom;
    }

    /**
     * Set minimum
     *
     * @param minRandom Minimum random value
     */
    public void setMinRandom(int minRandom) {
        this.minRandom = minRandom;
    }

    /**
     * Returns maximum
     *
     * @return Maximum
     */
    public int getMaxRandom() {
        return maxRandom;
    }

    /**
     * Set maximum
     *
     * @param maxRandom Maximum random value
     */
    public void setMaxRandom(int maxRandom) {
        this.maxRandom = maxRandom;
    }

    /**
     * Returns background boolean value
     *
     * @return True-clouds over background,False-clouds under background
     */
    public boolean isBackground() {
        return background;
    }

    /**
     * Set background boolean value
     *
     * @param background Boolean value
     */
    public void setBackground(boolean background) {
        this.background = background;
    }
}

