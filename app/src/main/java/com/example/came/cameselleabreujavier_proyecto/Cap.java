package com.example.came.cameselleabreujavier_proyecto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;

import java.util.ArrayList;

/**
 * Set cap properties
 */

public class Cap {

    private ArrayList<ImageCap> imgCaps = new ArrayList<>(); //Images to show in this cap
    private int screenWidth, screenHeight; //Screen width and height
    private int speed = 0; //Pixels added o substracted on cap movement
    private int posY = 0;  //Cap position on vertical axis

    /**
     * Initialize cap properties
     *
     * @param context      Context
     * @param screenWidth  Screen width
     * @param screenHeight Screen heeight
     * @param bitmapsCapa  Cap image
     */
    public Cap(Context context, int screenWidth, int screenHeight, ArrayList<Bitmap> bitmapsCapa) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        if (bitmapsCapa.size() != 0) {
            for (int i = 0; i < bitmapsCapa.size(); i++) {
                imgCaps.add(new ImageCap(posY, bitmapsCapa.get(i), screenWidth, -1));
            }

            imgCaps.get(0).position = new PointF(0, posY);
            for (int i = 1; i < imgCaps.size(); i++) {
                imgCaps.get(i).position = new PointF(imgCaps.get(i - 1).position.x + imgCaps.get(i).image.getWidth(), posY);
            }
        }
    }

    /**
     * Return cap speed
     *
     * @return Cap speed
     */
    public int getSpeed() {
        return speed;
    }


    /**
     * Set cap speed
     *
     * @param speed Cap speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }


    /**
     * Return cap position on vertical axis
     *
     * @return Cap position on vertical axis
     */
    public int getPosY() {
        return (int) imgCaps.get(0).position.y;

    }


    /**
     * Set cap position on vertical axis
     *
     * @param posY Position on vertical axis
     */
    public void setPosY(int posY) {
        for (int i = 0; i < imgCaps.size(); i++) {
            imgCaps.get(i).position.y = posY;
        }
    }

    /**
     * Cap movement manager
     */
    public void mover() {
        if (speed < 0) {
            for (ImageCap f : imgCaps) {
                f.position.x += speed;
            }
            for (int i = 0; i < imgCaps.size(); i++) {
                ImageCap ff = imgCaps.get(i);
                if (ff.position.x + ff.image.getWidth() < 0) {
                    ff.position.x = imgCaps.get(imgCaps.size() - 1).position.x + imgCaps.get(imgCaps.size() - 1).image.getWidth();
                    imgCaps.remove(ff);
                    imgCaps.add(ff);
                }
            }
        }

    }

    /**
     * Paint cap on screen
     *
     * @param c Canvas
     */
    public void dibujar(Canvas c) {
        for (ImageCap f : imgCaps) {
            c.drawBitmap(f.image, f.position.x, f.position.y, null);
        }
    }

}
