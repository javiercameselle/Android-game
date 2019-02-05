package com.example.came.cameselleabreujavier_proyecto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;

import java.util.ArrayList;

public class Capa {

    private ArrayList<Fondo> fondos = new ArrayList<>();
    private Bitmap bitmapFondo1, bitmapFondo2, bitmapFondo3;
    private int anchoPantalla, altoPantalla;
    private int velocidad = 0;
    private int posY = 0;

    public Capa(Context context, int anchoPantalla, int altoPantalla, ArrayList<Bitmap> bitmapsCapa) {
        if (bitmapsCapa.size() != 0) {
            for (int i = 0; i < bitmapsCapa.size(); i++) {
                fondos.add(new Fondo(posY, bitmapsCapa.get(i), anchoPantalla, -1));
            }

            fondos.get(0).posicion = new PointF(0, posY);
            for (int i = 1; i < fondos.size(); i++) {
                fondos.get(i).posicion = new PointF(fondos.get(i - 1).posicion.x + fondos.get(i).imagen.getWidth(), posY);

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
        for (int i = 0; i < fondos.size(); i++) {
            fondos.get(i).posicion.y = posY;

        }
    }

    public void mover() {
        if (velocidad < 0) {
            for (Fondo f : fondos) {
                f.posicion.x += velocidad;
            }
            for (int i = 0; i < fondos.size(); i++) {
                Fondo ff = fondos.get(i);
                if (ff.posicion.x + ff.imagen.getWidth() < 0) {
                    ff.posicion.x = fondos.get(fondos.size() - 1).posicion.x + fondos.get(fondos.size() - 1).imagen.getWidth();
                    fondos.remove(ff);
                    fondos.add(ff);
                }
            }
        }

    }

    public void dibujar(Canvas c) {
        for (Fondo f : fondos) {
            Log.i("TAG", f.posicion.y + "");
            c.drawBitmap(f.imagen, f.posicion.x, f.posicion.y, null);
        }
    }

}
