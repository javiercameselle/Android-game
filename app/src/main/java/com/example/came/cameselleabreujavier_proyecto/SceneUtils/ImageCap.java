package com.example.came.cameselleabreujavier_proyecto.SceneUtils;

import android.graphics.Bitmap;
import android.graphics.PointF;

/**
 * Caps images manage
 */

public class ImageCap {
    public PointF position; //Image position
    public Bitmap image;    //Image to show
    public int direction;   //Move to left(-1) or right(1)

    /**
     * Initialize cap images
     *
     * @param image Image
     * @param x     Position X
     * @param y     Position Y
     * @param dir   Direction: positive(1) or negative(-1)
     */
    public ImageCap(Bitmap image, float x, float y, int dir) {
        this.image = image;
        this.position = new PointF(x, y);
        this.direction = dir;
    }

    /**
     * Initialize cap images
     *
     * @param posY        Position Y
     * @param image       Image
     * @param screenWidth Screen width
     * @param dir         Direction: positive(1) or negative(-1)
     */
    public ImageCap(int posY, Bitmap image, int screenWidth, int dir) {
        this(image, screenWidth - image.getWidth(), posY, dir);
    }

//    public void mover(int velocidad) {
//        position.x += velocidad * direction;
//    }

}
