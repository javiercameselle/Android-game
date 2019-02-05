package com.example.came.cameselleabreujavier_proyecto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.Log;

import java.util.ArrayList;

public class Capa {

    private ArrayList<ImagenesCapa> imagenesCapas = new ArrayList<>();
    private Bitmap bitmapFondo1, bitmapFondo2, bitmapFondo3;
    private int anchoPantalla, altoPantalla;
    private int velocidad = 0;
    private int posY = 0;

    public Capa(Context context, int anchoPantalla, int altoPantalla, ArrayList<Bitmap> bitmapsCapa) {
        if (bitmapsCapa.size() != 0) {
            for (int i = 0; i < bitmapsCapa.size(); i++) {
                imagenesCapas.add(new ImagenesCapa(posY, bitmapsCapa.get(i), anchoPantalla, -1));
            }

            imagenesCapas.get(0).posicion = new PointF(0, posY);
            for (int i = 1; i < imagenesCapas.size(); i++) {
                imagenesCapas.get(i).posicion = new PointF(imagenesCapas.get(i - 1).posicion.x + imagenesCapas.get(i).imagen.getWidth(), posY);
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
        return posY;
    }

    public void setPosY(int posY) {
        for (int i = 0; i < imagenesCapas.size(); i++) {
            imagenesCapas.get(i).posicion.y = posY;

        }
    }

    public void mover() {
        if (velocidad < 0) {
            for (ImagenesCapa f : imagenesCapas) {
                f.posicion.x += velocidad;
            }
            for (int i = 0; i < imagenesCapas.size(); i++) {
                ImagenesCapa ff = imagenesCapas.get(i);
                if (ff.posicion.x + ff.imagen.getWidth() < 0) {
                    ff.posicion.x = imagenesCapas.get(imagenesCapas.size() - 1).posicion.x + imagenesCapas.get(imagenesCapas.size() - 1).imagen.getWidth();
                    imagenesCapas.remove(ff);
                    imagenesCapas.add(ff);
                }
            }
        }

    }

    public void dibujar(Canvas c) {
        for (ImagenesCapa f : imagenesCapas) {
            Log.i("TAG", f.posicion.y + "");
            c.drawBitmap(f.imagen, f.posicion.x, f.posicion.y, null);
        }
    }

}
