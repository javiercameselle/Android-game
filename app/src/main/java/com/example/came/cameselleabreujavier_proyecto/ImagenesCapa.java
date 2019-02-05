package com.example.came.cameselleabreujavier_proyecto;

import android.graphics.Bitmap;
import android.graphics.PointF;

public class ImagenesCapa {
    public PointF posicion;
    public Bitmap imagen;
    public int direccion;

    public ImagenesCapa(Bitmap imagen, float x, float y, int direccion) {
        this.imagen = imagen;
        this.posicion = new PointF(x, y);
        this.direccion = direccion;
    }

    public ImagenesCapa(int posY, Bitmap imagen, int anchoPantalla, int dir) {
        this(imagen, anchoPantalla - imagen.getWidth(), posY, dir);
    }

//    public void mover(int velocidad) {
//        posicion.x += velocidad * direccion;
//    }

}
