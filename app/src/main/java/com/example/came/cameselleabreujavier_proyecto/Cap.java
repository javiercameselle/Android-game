package com.example.came.cameselleabreujavier_proyecto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;

import java.util.ArrayList;

public class Cap {

    private ArrayList<ImagesCap> imgCaps = new ArrayList<>();
//    private int screenWidth, screenHeight;
    private int velocidad = 0;
    private int posY = 0;

    public Cap(Context context, int anchoPantalla, int altoPantalla, ArrayList<Bitmap> bitmapsCapa) {
        if (bitmapsCapa.size() != 0) {
            for (int i = 0; i < bitmapsCapa.size(); i++) {
                imgCaps.add(new ImagesCap(posY, bitmapsCapa.get(i), anchoPantalla, -1));
            }

            imgCaps.get(0).posicion = new PointF(0, posY);
            for (int i = 1; i < imgCaps.size(); i++) {
                imgCaps.get(i).posicion = new PointF(imgCaps.get(i - 1).posicion.x + imgCaps.get(i).imagen.getWidth(), posY);
            }
        }
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getPosY() {
        return (int)imgCaps.get(0).posicion.y;

    }

    public void setPosY(int posY) {
        for (int i = 0; i < imgCaps.size(); i++) {
            imgCaps.get(i).posicion.y = posY;
        }
    }

    public void mover() {
        if (velocidad < 0) {
            for (ImagesCap f : imgCaps) {
                f.posicion.x += velocidad;
            }
            for (int i = 0; i < imgCaps.size(); i++) {
                ImagesCap ff = imgCaps.get(i);
                if (ff.posicion.x + ff.imagen.getWidth() < 0) {
                    ff.posicion.x = imgCaps.get(imgCaps.size() - 1).posicion.x + imgCaps.get(imgCaps.size() - 1).imagen.getWidth();
                    imgCaps.remove(ff);
                    imgCaps.add(ff);
                }
            }
        }

    }

    public void dibujar(Canvas c) {
        for (ImagesCap f : imgCaps) {
            c.drawBitmap(f.imagen, f.posicion.x, f.posicion.y, null);
        }
    }

}
