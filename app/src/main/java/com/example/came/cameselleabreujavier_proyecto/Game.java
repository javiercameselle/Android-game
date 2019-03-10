package com.example.came.cameselleabreujavier_proyecto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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

/**
 * Game and it components
 */

public class Game extends Scene {

    //    private Cap backgroundCap;
    private Cap floorCap;//Floor cap
    private Cap buildingsCap;//Buildings cap
    private Bitmap imgFloor;//Floor image
    private Bitmap imgCloud;//Cloud image
    private Bitmap imgBuildings;//Buildings image
    private Bitmap imgFondo;//Background image
    private Bitmap imgObstacle;//Obstacle image
    private Bitmap imgGameOver;//End of the game image
    private Bitmap imgPause;//Pause icon
    private Bitmap imgPlay;//Play button
    //    private ArrayList<Bitmap> bmBackGround;
    private ArrayList<Bitmap> bmFloor;//Floor cap images
    private ArrayList<Bitmap> bmClouds;//Clouds images
    private ArrayList<Bitmap> bmObstacles;//Obstacles images
    private ArrayList<Bitmap> bmBuildings;//Buildings images
    private Utils u;//Utils class
    private Obstacle obstaculo;//Game obstacle
    private ArrayList<Cloud> arrayClouds;//Game clouds
    private Character p;//Game main character
    private Life l;//Game lives to catch
    private Bitmap[] bmDeath;//Death animations
    private Bitmap[] bmJump;//Jumping animations
    private Bitmap[] bmRun;//Running animations
    private long difTime;//Time diference to increase game speed
    private boolean endRun;//End of character running
    static boolean pause;//Pause controler
    private static boolean endGame;//End of the game
    private Vibrator vibrator;//Vibrator manager
    private SoundPool efectos;//Sound effects manager
    //    private int hitEffect;//Hit sound effect against obstacle
//    private int jumpEffect;//Jumping sound effect
//    private int gameoverEffect;//End of game sound effect
//    private int liveUp;//Increase lives sound effect
    final private int maxSonidosSimultaneos = 5;//Maximum number of simultaneous sounds
    private Rect rectanguloGameOver;//Game over area to touch it
    private Rect rectPausa;//Pausa button area
    private Rect rectPlay;//Play area button
//    private Paint paint;//Rectangles modifiers

    /**
     * Initialize character properties
     *
     * @param context      Context
     * @param idScene      Scene ID
     * @param screenWidth  Screen width
     * @param screenHeight Screen height
     */
    public Game(Context context, int idScene, int screenWidth, int screenHeight) {
        super(context, idScene, screenWidth, screenHeight);
        u = new Utils(context);
        this.endRun = false;
        this.endGame = false;
        this.pause = false;

//        paint = new Paint();
//        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(5);

        difTime = System.currentTimeMillis();
        imgGameOver = BitmapFactory.decodeResource(context.getResources(), R.drawable.game_over);
        imgGameOver = Bitmap.createScaledBitmap(imgGameOver, screenWidth / 2, screenWidth / 2, false);
        rectanguloGameOver = new Rect(screenWidth / 2 - imgGameOver.getWidth() / 2, screenHeight / 2 - imgGameOver.getHeight() / 2, screenWidth / 2 + imgGameOver.getWidth() / 2, screenHeight / 2 + imgGameOver.getHeight() / 2);
        rectPausa = new Rect(0, 0, screenWidth / 8, screenHeight / 7);
        imgPause = BitmapFactory.decodeResource(context.getResources(), R.drawable.pause_button);
        imgPause = Bitmap.createScaledBitmap(imgPause, screenWidth / 8, screenHeight / 7, false);
        rectPlay = new Rect(screenWidth * 3 / 8, screenHeight * 3 / 7, screenWidth * 5 / 8, screenHeight * 5 / 7);
        imgPlay = BitmapFactory.decodeResource(context.getResources(), R.drawable.play_button);
        imgPlay = Bitmap.createScaledBitmap(imgPlay, screenWidth * 3 / 8, screenHeight * 3 / 7, false);
        //background
//        bmBackGround = new ArrayList<>();
        imgFondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.game_dark_background);
        imgFondo = Bitmap.createScaledBitmap(imgFondo, screenWidth, screenHeight, false);
//        bmBackGround.add(imgFondo);
//        imgReflex = u.espejo(imgFondo, true);
//        bmBackGround.add(Bitmap.createScaledBitmap(imgFondo, screenWidth, screenHeight, false));
//        backgroundCap = new Cap(context, screenWidth, screenHeight, bmBackGround);
//        backgroundCap.setSpeed(-4);

        //buildings
        bmBuildings = new ArrayList<>();
        imgBuildings = BitmapFactory.decodeResource(context.getResources(), R.drawable.buildings);
        imgBuildings = Bitmap.createScaledBitmap(imgBuildings, screenWidth, screenHeight, false);
        bmBuildings.add(imgBuildings);
        bmBuildings.add(imgBuildings);
        buildingsCap = new Cap(context, screenWidth, screenHeight, bmBuildings);
        buildingsCap.setSpeed(-u.getDpW(6));

        //suelo
        bmFloor = new ArrayList<>();
        imgFloor = BitmapFactory.decodeResource(context.getResources(), R.drawable.suelo);
        bmFloor.add(Bitmap.createScaledBitmap(imgFloor, screenWidth, screenHeight / 6, false));
        bmFloor.add(Bitmap.createScaledBitmap(imgFloor, screenWidth, screenHeight / 6, false));
        floorCap = new Cap(context, screenWidth, screenHeight, bmFloor);
        floorCap.setSpeed(-u.getDpW(16));
        floorCap.setPosY(screenHeight * 7 / 8);

        //nubes
        bmClouds = new ArrayList<>();
        arrayClouds = new ArrayList<>();
        int end = (int) (Math.random() * 7 + 2);
        imgCloud = BitmapFactory.decodeResource(context.getResources(), R.drawable.little_cloud);
        bmClouds.add(Bitmap.createScaledBitmap(imgCloud, screenWidth / 3, screenHeight / 7, false));
        bmClouds.add(Bitmap.createScaledBitmap(imgCloud, screenWidth / 3, screenHeight / 7, false));
        imgCloud = BitmapFactory.decodeResource(context.getResources(), R.drawable.dark_cloud);
        bmClouds.add(Bitmap.createScaledBitmap(imgCloud, screenWidth / 3, screenHeight / 5, false));
        bmClouds.add(Bitmap.createScaledBitmap(imgCloud, screenWidth / 3, screenHeight / 5, false));
        imgCloud = BitmapFactory.decodeResource(context.getResources(), R.drawable.clouds_withalpha);
        bmClouds.add(Bitmap.createScaledBitmap(imgCloud, screenWidth / 3, screenHeight / 7, false));
        bmClouds.add(Bitmap.createScaledBitmap(imgCloud, screenWidth / 3, screenHeight / 7, false));
        for (int i = 0; i < end; i++) {
            arrayClouds.add(new Cloud(context, screenWidth, screenHeight, bmClouds));
        }

        //obstaculo
        bmObstacles = new ArrayList<>();
        imgObstacle = BitmapFactory.decodeResource(context.getResources(), R.drawable.obstaculo1);
        bmObstacles.add(Bitmap.createScaledBitmap(imgObstacle, screenWidth / 10, screenHeight / 8, false));
        imgObstacle = BitmapFactory.decodeResource(context.getResources(), R.drawable.obstaculo2);
        bmObstacles.add(Bitmap.createScaledBitmap(imgObstacle, screenWidth / 10, screenHeight / 8, false));
        imgObstacle = BitmapFactory.decodeResource(context.getResources(), R.drawable.obstaculo3);
        bmObstacles.add(Bitmap.createScaledBitmap(imgObstacle, screenWidth / 10, screenHeight / 8, false));
        obstaculo = new Obstacle(context, 0, floorCap.getPosY() - bmObstacles.get(0).getHeight(), floorCap.getSpeed(), screenWidth, screenHeight, bmObstacles);

        l = new Life(context, screenWidth, screenHeight);

        bmDeath = u.getFrames(8, "dead", "pDead", screenWidth / 10);
        bmJump = u.getFrames(5, "jump", "pJump", screenWidth / 10);
        bmRun = u.getFrames(5, "run", "pRun", screenWidth / 10);
        p = new Character(context, bmRun, bmJump, bmDeath, screenWidth, screenHeight, 0, screenWidth / 3, floorCap.getPosY() - bmRun[0].getHeight());

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
//        lifeUp = efectos.load(context, R.raw.vida_mas, 1);

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

    /**
     * All game components movements manager
     */
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
                if (p.isDeath() && p.getPosY() == p.getInitialPosY()) {
                    endGame = true;
                    mediaPlayer.stop();
                }
                p.mover();
                l.mover();

                if (p.rectPersonaje.intersect(obstaculo.rectObstacle) && obstaculo.isCollisionable()) {
                    obstaculo.setCollisionable(false);
                    p.setBroke(true);
                    p.setLifes(p.getLifes() - 1);

//                    efectos.play(hitEffect, vol, vol, 1, 0, 1);
                    if (p.getLifes() == 0) {
                        endRun = true;
                        p.setDeath(true);
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
                if (p.rectPersonaje.intersect(l.getRectLife()) && l.isCollisionable()) {
                    l.setCatched(true);
                    l.setCollisionable(false);
                    if (p.getLifes() != 3) {
                        p.setLifes(p.getLifes() + 1);
//                        if (withSound)
//                            efectos.play(lifeUp, vol, vol, 1, 0, 1);
                    }
                }
                if (p.getPosX() > l.getPosX() + l.getBitmapLife().getWidth()) {
                    l.setCollisionable(true);
                }
            } else {//Death
                p.setDeath(true);
            }
        }
    }

    /**
     * Paint game screen
     *
     * @param c Canvas
     */
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
                buildingsCap.setSpeed(buildingsCap.getSpeed() - u.getDpW(2));
                floorCap.setSpeed(floorCap.getSpeed() - u.getDpW(2));
                for (Cloud cd : arrayClouds) {
                    cd.setMinRandom(+2);
                    cd.setMaxRandom(+2);
                }
                obstaculo.setSpeed(floorCap.getSpeed());
                p.setFrameTime(p.getFrameTime() - u.getDpW(2));
                difTime = System.currentTimeMillis();
            }
            if (endRun) {
                c.drawBitmap(imgGameOver, screenWidth / 2 - imgGameOver.getWidth() / 2, screenHeight / 2 - imgGameOver.getHeight() / 2, null);
                //p.setDeath(true);
                if (!p.isJumping() && p.isDeath()) {
                    p.setJumping(true);
                    p.setIndex(0);
                    p.setPressTime();
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

    /**
     * Pressed button checker
     *
     * @param button Button rectangle
     * @param event  Action event
     * @return Button pressed
     */
    public boolean pulsa(Rect button, MotionEvent event) {
        if (button.contains((int) event.getX(), (int) event.getY())) {
            return true;
        } else return false;
    }

    /**
     * Screen touch control
     *
     * @param event Press action
     * @return Scene ID to change scene
     */
    public int onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();             //Obtenemos el tipo de pulsación
        switch (action) {
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
                else if (!p.isJumping() && !p.isDeath() && !pause) {
                    p.setJumping(true);
                    p.setIndex(0);
                    p.setPressTime();
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
                Log.i("Otra acción", "Acción no definida: " + action);
        }

        int idFather = super.onTouchEvent(event);
        if (idFather != idScene) {
            return idFather;
        }
        return idScene;
    }
}



