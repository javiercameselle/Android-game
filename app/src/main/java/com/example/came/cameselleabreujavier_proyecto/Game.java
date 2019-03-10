package com.example.came.cameselleabreujavier_proyecto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import static com.example.came.cameselleabreujavier_proyecto.MainActivity.mediaPlayer;
import static com.example.came.cameselleabreujavier_proyecto.MainActivity.musicStarted;
import static com.example.came.cameselleabreujavier_proyecto.MainActivity.withSound;
import static com.example.came.cameselleabreujavier_proyecto.MainActivity.withVibration;

public class Game extends Scene {

    private Cap backgroundCap, floorCap, buildingsCap;
    private Bitmap bitmapAux, imgFloor, imgCloud, imgBuildings, imgFondo, imgObstacle, imgGameOver, imgPause, imgPlay;
    private ArrayList<Bitmap> bmBackGround, bmFloor, bmClouds, bmObstaculos, bmBuildings;
    private Utils u;
    private Obstacle obstaculo, obstaculo_;
    private ArrayList<Cloud> arrayClouds;
    private Character p;
    private Live l;
    private Bitmap[] bmDead, bmJump, bmRun, bmColision;
    private long difTime;
    private boolean endRun;
    static boolean pause;
    private static boolean endGame;
    private Vibrator vibrator;
    private SoundPool efectos;
    private int hitEffect, jumpEffect, gameoverEffect, liveUp;
    final private int maxSonidosSimultaneos = 5;
    private Rect rectanguloGameOver, rectPausa, rectPlay;
    private Paint paint;


    public Game(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);
        u = new Utils(context);
        this.endRun = false;
        this.endGame = false;
        this.pause = false;

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        difTime = System.currentTimeMillis();
        imgGameOver = BitmapFactory.decodeResource(context.getResources(), R.drawable.game_over);
        imgGameOver = Bitmap.createScaledBitmap(imgGameOver, anchoPantalla / 2, anchoPantalla / 2, false);
        rectanguloGameOver = new Rect(anchoPantalla / 2 - imgGameOver.getWidth() / 2, altoPantalla / 2 - imgGameOver.getHeight() / 2, anchoPantalla / 2 + imgGameOver.getWidth() / 2, altoPantalla / 2 + imgGameOver.getHeight() / 2);
        rectPausa = new Rect(0, 0, anchoPantalla / 8, altoPantalla / 7);
        imgPause = BitmapFactory.decodeResource(context.getResources(), R.drawable.pause_button);
        imgPause = Bitmap.createScaledBitmap(imgPause, anchoPantalla / 8, altoPantalla / 7, false);
        rectPlay = new Rect(anchoPantalla * 3 / 8, altoPantalla * 3 / 7, anchoPantalla * 5 / 8, altoPantalla * 5 / 7);
        imgPlay = BitmapFactory.decodeResource(context.getResources(), R.drawable.play_button);
        imgPlay = Bitmap.createScaledBitmap(imgPlay, anchoPantalla * 3 / 8, altoPantalla * 3 / 7, false);
        //background
//        bmBackGround = new ArrayList<>();
        imgFondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.game_dark_background);
        imgFondo = Bitmap.createScaledBitmap(imgFondo, anchoPantalla, altoPantalla, false);
//        bmBackGround.add(imgFondo);
//        imgReflex = u.espejo(imgFondo, true);
//        bmBackGround.add(Bitmap.createScaledBitmap(imgFondo, screenWidth, screenHeight, false));
//        backgroundCap = new Cap(context, screenWidth, screenHeight, bmBackGround);
//        backgroundCap.setVelocidad(-4);

        //buildings
        bmBuildings = new ArrayList<>();
        imgBuildings = BitmapFactory.decodeResource(context.getResources(), R.drawable.buildings);
        imgBuildings = Bitmap.createScaledBitmap(imgBuildings, anchoPantalla, altoPantalla, false);
        bmBuildings.add(imgBuildings);
        bmBuildings.add(imgBuildings);
        buildingsCap = new Cap(context, anchoPantalla, altoPantalla, bmBuildings);
        buildingsCap.setVelocidad(-u.getDpW(6));

        //suelo
        bmFloor = new ArrayList<>();
        imgFloor = BitmapFactory.decodeResource(context.getResources(), R.drawable.suelo);
        bmFloor.add(Bitmap.createScaledBitmap(imgFloor, anchoPantalla, altoPantalla / 6, false));
        bmFloor.add(Bitmap.createScaledBitmap(imgFloor, anchoPantalla, altoPantalla / 6, false));
        floorCap = new Cap(context, anchoPantalla, altoPantalla, bmFloor);
        floorCap.setVelocidad(-u.getDpW(16));
        floorCap.setPosY(altoPantalla * 7 / 8);

        //nubes
        bmClouds = new ArrayList<>();
        arrayClouds = new ArrayList<>();
        int fin = (int) (Math.random() * 7 + 2);
        imgCloud = BitmapFactory.decodeResource(context.getResources(), R.drawable.little_cloud);
        bmClouds.add(Bitmap.createScaledBitmap(imgCloud, anchoPantalla / 3, altoPantalla / 7, false));
        bmClouds.add(Bitmap.createScaledBitmap(imgCloud, anchoPantalla / 3, altoPantalla / 7, false));
        imgCloud = BitmapFactory.decodeResource(context.getResources(), R.drawable.dark_cloud);
        bmClouds.add(Bitmap.createScaledBitmap(imgCloud, anchoPantalla / 3, altoPantalla / 5, false));
        bmClouds.add(Bitmap.createScaledBitmap(imgCloud, anchoPantalla / 3, altoPantalla / 5, false));
        imgCloud = BitmapFactory.decodeResource(context.getResources(), R.drawable.clouds_withalpha);
        bmClouds.add(Bitmap.createScaledBitmap(imgCloud, anchoPantalla / 3, altoPantalla / 7, false));
        bmClouds.add(Bitmap.createScaledBitmap(imgCloud, anchoPantalla / 3, altoPantalla / 7, false));
        for (int i = 0; i < fin; i++) {
            arrayClouds.add(new Cloud(context, anchoPantalla, altoPantalla, bmClouds));
        }

        //obstaculo
        bmObstaculos = new ArrayList<>();
        imgObstacle = BitmapFactory.decodeResource(context.getResources(), R.drawable.obstaculo1);
        bmObstaculos.add(Bitmap.createScaledBitmap(imgObstacle, anchoPantalla / 10, altoPantalla / 8, false));
        imgObstacle = BitmapFactory.decodeResource(context.getResources(), R.drawable.obstaculo2);
        bmObstaculos.add(Bitmap.createScaledBitmap(imgObstacle, anchoPantalla / 10, altoPantalla / 8, false));
        imgObstacle = BitmapFactory.decodeResource(context.getResources(), R.drawable.obstaculo3);
        bmObstaculos.add(Bitmap.createScaledBitmap(imgObstacle, anchoPantalla / 10, altoPantalla / 8, false));
        obstaculo = new Obstacle(context, 0, floorCap.getPosY() - bmObstaculos.get(0).getHeight(), floorCap.getVelocidad(), anchoPantalla, altoPantalla, bmObstaculos);

        l = new Live(context, anchoPantalla, altoPantalla);

        bmDead = u.getFrames(8, "dead", "pDead", anchoPantalla / 10);
        bmJump = u.getFrames(5, "jump", "pJump", anchoPantalla / 10);
        bmRun = u.getFrames(5, "run", "pRun", anchoPantalla / 10);
        bmColision = u.getFrames(2, "collision", "pCollision", 200);
        p = new Character(context, bmRun, bmJump, bmColision, bmDead, anchoPantalla, altoPantalla, 0, anchoPantalla / 3, floorCap.getPosY() - bmRun[0].getHeight());

        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        if ((android.os.Build.VERSION.SDK_INT) >= 21) {
            SoundPool.Builder spb = new SoundPool.Builder();
            spb.setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build());
            spb.setMaxStreams(maxSonidosSimultaneos);
            this.efectos = spb.build();
        } else {
            this.efectos = new SoundPool(maxSonidosSimultaneos, AudioManager.STREAM_MUSIC, 0);
        }

//        jumpEffect = efectos.load(context, R.raw.suuu, 1);
//        hitEffect = efectos.load(context, R.raw.hit, 1);
//        liveUp = efectos.load(context, R.raw.vida_mas, 1);

        if (withSound) {
//            if(mediaPlayer==null){
            mediaPlayer = MediaPlayer.create(context, R.raw.run_music);
            mediaPlayer.setVolume(vol, vol);
//            }
            if (!musicStarted && !mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                musicStarted = true;
            }
        }

    }

    public void actualizarFisica() {
        if (!pause) {
            if (!endGame) {
                //        backgroundCap.mover();
                buildingsCap.mover();
                floorCap.mover();
                for (Cloud cd : arrayClouds) {
                    cd.mover();
                }
                obstaculo.mover();
//            p.mover();
                if (p.isJumping()) {
                    p.jump();
                }
                if (p.isDead() && p.getPosY() == p.getInitialPosY()) {
                    endGame = true;
                    mediaPlayer.stop();
                }
                p.mover();
                l.mover();

                if (p.rectPersonaje.intersect(obstaculo.rectObstacle) && obstaculo.isCollisionable()) {
                    obstaculo.setCollisionable(false);
                    p.setBroke(true);
                    Log.i("xxxxObs", p.getLives() + "");
                    p.setLives(p.getLives() - 1);
                    Log.i("xxxxObs", p.getLives() + "");

//                    efectos.play(hitEffect, vol, vol, 1, 0, 1);
                    if (p.getLives() == 0) {
                        endRun = true;
                        p.setDead(true);
                        p.setBroke(false);
                        if (withVibration) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {
                                vibrator.vibrate(500);
                            }
                        }
                    }
                }

                if (p.getPosX() > obstaculo.getPosX() + obstaculo.getImgObstacle().getWidth()) {
                    obstaculo.setCollisionable(true);
                    p.setBroke(false);
                }
                if (p.rectPersonaje.intersect(l.getRectLive()) && l.isCollisionable()) {
                    l.setCatched(true);
                    l.setCollisionable(false);
                    if (p.getLives() != 3) {
                        p.setLives(p.getLives() + 1);
//                        if (withSound)
//                            efectos.play(liveUp, vol, vol, 1, 0, 1);
                    }
                    Log.i("xxxxLiv", p.getLives() + "");
                }
                if (p.getPosX() > l.getPosX() + l.getBitmapLive().getWidth()) {
                    l.setCollisionable(true);
                }
            } else {
                p.setDead(true);
            }
        }
    }

    public void dibujar(Canvas c) {
        try {
            c.drawBitmap(imgFondo, 0, 0, null);
//            backgroundCap.dibujar(c);
            for (Cloud cd : arrayClouds) {
                cd.dibujar(c);
            }
            buildingsCap.dibujar(c);
            floorCap.dibujar(c);
            obstaculo.dibujar(c);
            l.dibujar(c);
            p.dibujar(c);

            if (System.currentTimeMillis() - difTime > 15000 && !pause) {
                buildingsCap.setVelocidad(buildingsCap.getVelocidad() - u.getDpW(2));
                floorCap.setVelocidad(floorCap.getVelocidad() - u.getDpW(2));
                for (Cloud cd : arrayClouds) {
                    cd.setMinRandom(+2);
                    cd.setMaxRandom(+2);
                }
                obstaculo.setSpeed(floorCap.getVelocidad());
                p.setFrameTime(p.getFrameTime() - u.getDpW(2));
                difTime = System.currentTimeMillis();
            }
            if (endRun) {
                c.drawBitmap(imgGameOver, screenWidth / 2 - imgGameOver.getWidth() / 2, screenHeight / 2 - imgGameOver.getHeight() / 2, null);
                //p.setDead(true);
                if (!p.isJumping() && p.isDead()) {
                    p.setJumping(true);
                    p.setIndex(0);
                    p.setPulsacionTime();
                }
            }
            if (!pause)
                c.drawBitmap(imgPause, rectPausa.centerX() - imgPause.getWidth() / 2, rectPausa.centerY() - imgPause.getHeight() / 2, null);
            if (pause) {
                c.drawColor(Color.argb(50, 255, 255, 204));
                c.drawBitmap(imgPlay, rectPlay.centerX() - imgPlay.getWidth() / 2, rectPlay.centerY() - imgPlay.getHeight() / 2, null);
            }
            super.dibujar(c);
        } catch (Exception e) {
            Log.i("ERROR AL DIBUJAR", e.getLocalizedMessage());
        }

    }

    public boolean pulsa(Rect boton, MotionEvent event) {
        if (boton.contains((int) event.getX(), (int) event.getY())) {
            return true;
        } else return false;
    }

    public int onTouchEvent(MotionEvent event) {
        int pointerIndex = event.getActionIndex();        //Obtenemos el índice de la acción
        int pointerID = event.getPointerId(pointerIndex); //Obtenemos el Id del pointer asociado a la acción
        int accion = event.getActionMasked();             //Obtenemos el tipo de pulsación
        switch (accion) {
            case MotionEvent.ACTION_DOWN:           // Primer dedo toca
            case MotionEvent.ACTION_POINTER_DOWN:  // Segundo y siguientes tocan
                break;

            case MotionEvent.ACTION_UP:// Al levantar el último dedo
                //go back
                if (pulsa(rMenu, event)) {
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                        musicStarted = false;
                    }
                } else if (pulsa(rectPausa, event) && pause == false) {
                    this.pause = true;
                } else if (pulsa(rectPlay, event) && pause == true) {
                    this.pause = false;
                }

                //jump
                else if (!p.isJumping() && !p.isDead() && !pause) {
                    p.setJumping(true);
                    p.setIndex(0);
                    p.setPulsacionTime();
//                    if (withSound) {
//                        efectos.play(jumpEffect, vol, vol, 1, 0, 1);
//                    }
                }
                if (pulsa(rectanguloGameOver, event) && endGame && endRun) {
                    return 6;
                }

            case MotionEvent.ACTION_POINTER_UP:  // Al levantar un dedo que no es el último
                break;


            case MotionEvent.ACTION_MOVE: // Se mueve alguno de los dedos

                break;
            default:
                Log.i("Otra acción", "Acción no definida: " + accion);
        }

        int idPadre = super.onTouchEvent(event);
        if (idPadre != idScene) {
            return idPadre;
        }
        return idScene;
    }
}



