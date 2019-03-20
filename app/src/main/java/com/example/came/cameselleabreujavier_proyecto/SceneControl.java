package com.example.came.cameselleabreujavier_proyecto;


import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.came.cameselleabreujavier_proyecto.Scenes.Credits;
import com.example.came.cameselleabreujavier_proyecto.Scenes.Game;
import com.example.came.cameselleabreujavier_proyecto.Scenes.MainMenu;
import com.example.came.cameselleabreujavier_proyecto.Scenes.Save;
import com.example.came.cameselleabreujavier_proyecto.Scenes.Help;
import com.example.came.cameselleabreujavier_proyecto.Scenes.Options;
import com.example.came.cameselleabreujavier_proyecto.Scenes.Records;

import static com.example.came.cameselleabreujavier_proyecto.MainActivity.mediaPlayer;
import static com.example.came.cameselleabreujavier_proyecto.MainActivity.withSound;

/**
 * Scenes manager
 */

public class SceneControl extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;      // Draw area manager
    private Context context;                  // Application context

    public static int screenWidth = 1;              // Screen width
    public static int screenHeight = 1;               // Screen height
    private ThreadGame thread;                        // ThreadGame manage onPaint y Move
    public boolean running = false;      // Control del thread
    private Scene actualScene;

    public SceneControl(Context context) {
        super(context);
        this.surfaceHolder = getHolder();       // Get holder
        this.surfaceHolder.addCallback(this);   // Starts where callbacks function are
        this.context = context;                 // Get context
        thread = new ThreadGame();                      // Initialize thread
        setFocusable(true);                     // Allow receive touch event
    }

    /**
     * Control scene manager
     *
     * @param event Press action
     * @return true
     */
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
        screenWidth = width;               // New screen width
        screenHeight = height;               // New screen height
        thread.setSurfaceSize(width, height);   // New screen measures on surface
        actualScene = new MainMenu(context, 0, screenWidth, screenHeight);
        thread.setRunning(true); // Allow to start thread
        if (thread.getState() == Thread.State.NEW)
            thread.start();
        if (thread.getState() == Thread.State.TERMINATED) {
            thread = new ThreadGame();
            thread.start();
        }
    }


    // Implement paint method to manage user interface
    class ThreadGame extends Thread {
        public ThreadGame() {

        }

        @Override
        public void run() {
            while (running) {
                Canvas c = null; //It need to repaint canvas
                try {
                    if (!surfaceHolder.getSurface().isValid())
                        continue; // If canvas is not preapared to paint
                    c = surfaceHolder.lockCanvas(); // Commons resources need syncro
                    synchronized (surfaceHolder) {
                        actualScene.actualizarFisica();  // Components move
                        actualScene.dibujar(c);          // Components paint
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {  // It must release Canvas
                    if (c != null) {
                        surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }

        /**
         * Active/desactive game thread
         *
         * @param flag
         */
        public void setRunning(boolean flag) {
            running = flag;
        }

        /**
         * Give us screen width and height
         *
         * @param width
         * @param height
         */
        public void setSurfaceSize(int width, int height) {
            synchronized (surfaceHolder) {
            }
        }
    }
}

