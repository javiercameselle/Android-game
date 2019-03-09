package com.example.came.cameselleabreujavier_proyecto;


import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import static com.example.came.cameselleabreujavier_proyecto.MainActivity.mediaPlayer;
import static com.example.came.cameselleabreujavier_proyecto.MainActivity.withSound;

public class SceneControl extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;      // Interfaz abstracta para manejar la superficie de dibujado
    private Context context;                  // Contexto de la aplicación

    static int screenWidth = 1;              // Ancho de la pantalla, su valor se actualiza en el método surfaceChanged
    static int screenHeight = 1;               // Alto de la pantalla, su valor se actualiza en el método surfaceChanged
    private ThreadGame thread;                        // ThreadGame encargado de dibujar y actualizar la física
    public boolean running = false;      // Control del thread
    private Scene actualScene;

    public SceneControl(Context context) {
        super(context);
        this.surfaceHolder = getHolder();       // Se obtiene el holder
        this.surfaceHolder.addCallback(this);   // Se indica donde van las funciones callback
        this.context = context;                 // Obtenemos el contexto
        thread = new ThreadGame();                      // Inicializamos el thread
        setFocusable(true);                     // Aseguramos que reciba eventos de toque
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        synchronized (surfaceHolder) {
            int newScene = actualScene.onTouchEvent(event);
            if (newScene != actualScene.idScene) {
                switch (newScene) {
                    case 0:
                        actualScene = new MainMenu(context, 0, screenWidth, screenHeight);
                        break;
                    case 1:
                        actualScene = new Game(context, 1, screenWidth, screenHeight);
                        break;
                    case 2:
                        actualScene = new Help(context, 2, screenWidth, screenHeight);
                        break;
                    case 3:
                        actualScene = new Records(context, 3, screenWidth, screenHeight);
                        break;
                    case 4:
                        actualScene = new Options(context, 4, screenWidth, screenHeight);
                        break;
                    case 5:
                        actualScene = new Credits(context, 5, screenWidth, screenHeight);
                        break;
                    case 6:
                        actualScene = new Save(context, 6, screenWidth, screenHeight);
                        break;
                }
            }
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        thread.setRunning(false);// Se para el thread
        if (withSound)
            mediaPlayer.stop();
        try {
            thread.join();   // Se espera a que finalize
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        screenWidth = width;               // se establece el nuevo ancho de pantalla
        screenHeight = height;               // se establece el nuevo alto de pantalla
        thread.setSurfaceSize(width, height);   // se establece el nuevo ancho y alto de pantalla en el thread
        actualScene = new MainMenu(context, 0, screenWidth, screenHeight);
        thread.setRunning(true); // Se le indica al thread que puede arrancar
        if (thread.getState() == Thread.State.NEW)
            thread.start(); // si el thread no ha sido creado se crea;
        if (thread.getState() == Thread.State.TERMINATED) {      // si el thread ha sido finalizado se crea de nuevo;
            thread = new ThreadGame();
            thread.start(); // se arranca el thread
        }
    }


    // Clase ThreadGame en la cual implementamos el método de dibujo (y física) para que se haga en paralelo con la gestión de la interfaz de usuario
    class ThreadGame extends Thread {
        public ThreadGame() {

        }

        @Override
        public void run() {
            while (running) {
                Canvas c = null; //Necesario repintar _todo el lienzo
                try {
                    if (!surfaceHolder.getSurface().isValid())
                        continue; // si la superficie no está preparada repetimos
                    c = surfaceHolder.lockCanvas(); // Obtenemos el lienzo.  La sincronización es necesaria por ser recurso común
                    synchronized (surfaceHolder) {
                        actualScene.actualizarFisica();  // Movimiento de los elementos
                        actualScene.dibujar(c);              // Dibujamos los elementos
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {  // Haya o no excepción, hay que liberar el lienzo
                    if (c != null) {
                        surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }

        // Activa o desactiva el funcionamiento del thread
        public void setRunning(boolean flag) {
            running = flag;
        }

        // Función es llamada si cambia el tamaño de la pantall o la orientación
        public void setSurfaceSize(int width, int height) {
            synchronized (surfaceHolder) {  // Se recomienda realizarlo de forma atómica

            }
        }
    }
}

