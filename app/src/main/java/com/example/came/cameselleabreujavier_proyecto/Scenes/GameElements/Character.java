package com.example.came.cameselleabreujavier_proyecto.Scenes.GameElements;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.came.cameselleabreujavier_proyecto.R;
import com.example.came.cameselleabreujavier_proyecto.SceneUtils.Utils;

import static com.example.came.cameselleabreujavier_proyecto.Scenes.Game.pause;
import static com.example.came.cameselleabreujavier_proyecto.MainActivity.faw;

/**
 * Game character
 */

public class Character {

    private Bitmap bitmapBullets;
    private Context context;
    private Bitmap[] bmLifes;//Lifes images array
    private Bitmap[] bmRun;//Running animation
    private Bitmap[] bmJump;//Jumping animation
    private Bitmap[] bmDeath;//Death animation
    private int lifes;//Character lifes
    private int posX, posXball;//Character position on horizontal axis
    private int posY, posYball;//Character position on vertical axis
    private int initialPosY;//Character initial position to compare with position during the jump
    private int speed, added;//Movement character speed
    private int drawTime = 50;
    private int frameTime = 100;//Change animation time
    private int jumpTime = 200;
    private int index = 0;//Animation control
    private int screenWidth;//Screen horizontal measure
    private int screenHeight;//Screen vertical measure
    public static int metres;//Running distance
    private long actualTime = System.currentTimeMillis();
    private long pressJumpTime;//Time control to character jump
    private long metresTime = System.currentTimeMillis();//Time control of meters increment
    private long deathTime;//Time when character death
    private boolean jumping = false;//Jumping control
    private boolean death = false;//Death control
    private boolean broke = false;//Hit control
    public Rect rectPersonaje;//Character rectangle
    public Rect rectShootball;//Character rectangle
    private Paint p, pText;//Text and retangle modifiers
    private Utils u;//Utils class
    private int bullets;
    private boolean shooting = false;
    private Bitmap imgShootball;
    private long pressShootTime;

    /**
     * Initialize character properties
     *
     * @param context      Context
     * @param bmRun        Running image
     * @param bmJump       Jumping image
     * @param bmDeath      Death image
     * @param screenWidth  Screen width
     * @param screenHeight Screen Height
     * @param speed        Character speed
     * @param posX         Position X
     * @param posY         position Y
     */
    public Character(Context context, Bitmap[] bmRun, Bitmap[] bmJump, Bitmap[] bmDeath, int screenWidth, int screenHeight, int speed, int posX, int posY) {
        this.context = context;
        u = new Utils(context);
        this.lifes = 3;
        this.bullets = 5;
        this.setMetres(0);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.bmRun = bmRun;
        this.bmJump = bmJump;
        this.bmDeath = bmDeath;
        this.posX = posX;
        this.posY = posY;
        this.posYball = posY + bmRun[0].getHeight() / 2;
        this.posXball = posX + bmRun[0].getWidth() / 2;
        this.initialPosY = posY;
        bmLifes = u.getFrames(4, "lives", "lives", u.getDpH(300));
        this.bitmapBullets = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullets);
        this.bitmapBullets = Bitmap.createScaledBitmap(bitmapBullets, screenWidth / 15, screenHeight / 15, false);
        imgShootball = BitmapFactory.decodeResource(context.getResources(), R.drawable.shootball);
        imgShootball = Bitmap.createScaledBitmap(imgShootball, screenWidth / 20, screenHeight / 20, false);
        p = new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5);
        pText = new Paint();
        pText.setTextSize(u.getDpW(100));
        pText.setColor(Color.BLACK);
        pText.setTypeface(faw);
    }

    /**
     * Manage the movement on character´s jump
     */
    public void jump() {
        if (System.currentTimeMillis() < (pressJumpTime + 700)) {
            posY -= u.getDpH(10);
        } else {
            posY += u.getDpH(10);
        }
        if (initialPosY == posY) {
            jumping = false;
            index = 0;
        }
    }

    /**
     * Manage all characters movements
     */
    public void mover() {
        if (System.currentTimeMillis() - actualTime > frameTime) {
            this.posYball = posY + bmRun[0].getHeight() / 2;
            this.posXball = posX + bmRun[0].getWidth() / 2;
            if (System.currentTimeMillis() < (pressShootTime + 1200)) {
                added += u.getDpH(20);
                posXball += added;
                rectShootball = new Rect(posXball, posYball, posXball + imgShootball.getWidth(), posYball + imgShootball.getHeight());
            } else {
                shooting = false;
                added = 0;
                this.posYball = posY + bmRun[0].getHeight() / 2;
                this.posXball = posX + bmRun[0].getWidth() / 2;
            }
            if (jumping && !death) {
                rectPersonaje = new Rect(posX + bmJump[index].getWidth() / 4, posY,
                        posX + bmJump[index].getWidth() * 2 / 3, posY + bmJump[index].getHeight());
                if (index < bmJump.length - 1)
                    index++;
            } else if (jumping && death) {
                if (index < bmDeath.length - 1)
                    index++;
            } else {
                if (!shooting) {
                    posXball = posX + bmRun[index].getWidth();
                    rectShootball = new Rect(posXball, posYball, posXball + imgShootball.getWidth(), posYball + imgShootball.getHeight());
                } else {

                }
                rectPersonaje = new Rect(posX + bmRun[index].getWidth() / 4, posY,
                        posX + bmRun[index].getWidth() * 2 / 3, posY + bmRun[index].getHeight());
                index++;
                if (index == bmRun.length) index = 0;
            }
            actualTime = System.currentTimeMillis();
        }
    }

    /**
     * Paint character properties and image on screen
     *
     * @param c Canvas
     */
    public void dibujar(Canvas c) {
        if (!death && System.currentTimeMillis() - metresTime > 500 && !pause) {
            setMetres(getMetres() + 1);
            metresTime = System.currentTimeMillis();
        }
        c.drawBitmap(bmLifes[lifes], u.getDpW(300), u.getDpH(75), null);
        c.drawBitmap(bitmapBullets, u.getDpW(700), u.getDpH(75), null);
        c.drawText(getBullets()+"", u.getDpW(850), u.getDpH(150), pText);
        c.drawText(context.getString(R.string.distance) + ": " + metres + " m", u.getDpW(screenWidth) * 3 / 5, u.getDpH(150), pText);
        if (isBroke()) {
            p.setAlpha((int) (Math.random() * 255 + 100));
        } else {
            p.setAlpha(255);
        }
        if (shooting) {
            c.drawBitmap(imgShootball, posXball, posYball, null);
        }
        if (jumping && !death) {
            c.drawBitmap(bmJump[index], posX, posY, p);
//            c.drawRect(rectPersonaje, p);
        } else if (jumping && death) {
            c.drawBitmap(bmDeath[index], posX, posY, p);
        } else {
            c.drawBitmap(bmRun[index], posX, posY, p);
//            c.drawRect(rectPersonaje, p);
        }
    }

    /**
     * Returns character position on horizontal axis
     *
     * @return Position X
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Set character position on horizontal axis
     *
     * @param posX Position X
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * Returns character position on vertical axis
     *
     * @return Position Y
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Set character position on vertical axis
     *
     * @param posY Position Y
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     * Returns character speed
     *
     * @return Speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Set character speed
     *
     * @param speed Character speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Returns jumping boolean
     *
     * @return Jumping boolean
     */
    public boolean isJumping() {
        return jumping;
    }

    /**
     * Set jumping boolean
     *
     * @param jumping Jumping boolean value
     */
    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    /**
     * Returns index
     *
     * @return Index value
     */
    public int getIndex() {
        return index;
    }

    /**
     * Set index
     *
     * @param index Index value
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Returns press time
     *
     * @return Press time
     */
    public long getPressJumpTime() {
        return pressJumpTime;
    }

    /**
     * Set press time
     */
    public void setPressJumpTime() {
        this.pressJumpTime = System.currentTimeMillis();
    }

    public void setPressShootTime() {
        this.pressShootTime = System.currentTimeMillis();
    }

    /**
     * Returns Change of frame time
     *
     * @return Change of frame time
     */
    public int getFrameTime() {
        return frameTime;
    }

    /**
     * Set frameTime
     *
     * @param frameTime Change of frame time value
     */
    public void setFrameTime(int frameTime) {
        this.frameTime = frameTime;
    }

    /**
     * Returns character lifes
     *
     * @return Character lifes
     */
    public int getLifes() {
        return lifes;
    }

    /**
     * Set character lifes
     *
     * @param lifes Character lifes
     */
    public void setLifes(int lifes) {
        this.lifes = lifes;
    }

    /**
     * Returns broke boolean
     *
     * @return Hit boolean value
     */
    public boolean isBroke() {
        return broke;
    }

    /**
     * Set broke boolean
     *
     * @param broke Hit boolean value
     */
    public void setBroke(boolean broke) {
        this.broke = broke;
    }

    /**
     * Returns metres
     *
     * @return Running metres
     */
    public int getMetres() {
        return metres;
    }

    /**
     * Set metres
     *
     * @param metres Metres value
     */
    public void setMetres(int metres) {
        this.metres = metres;
    }

    /**
     * Return death boolean
     *
     * @return Death boolean value
     */
    public boolean isDeath() {
        return death;
    }

    /**
     * Set death value
     *
     * @param death Death boolean value
     */
    public void setDeath(boolean death) {
        this.death = death;
    }

    /**
     * Returns death time
     *
     * @return Death time value
     */
    public long getDeathTime() {
        return deathTime;
    }

    /**
     * Set death time
     */
    public void setDeadTime() {
        this.deathTime = System.currentTimeMillis();
    }

    /**
     * Returns Initial position on vertical axis
     *
     * @return Initial position Y
     */
    public int getInitialPosY() {
        return initialPosY;
    }

    /**
     * Set initial position on vertical axis
     *
     * @param initialPosY Initial position Y
     */
    public void setInitialPosY(int initialPosY) {
        this.initialPosY = initialPosY;
    }

    public boolean isShooting() {
        return shooting;
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }

    public int getAdded() {
        return added;
    }

    public void setAdded(int added) {
        this.added = added;
    }

    public int getPosXball() {
        return posXball;
    }

    public void setPosXball(int posXball) {
        this.posXball = posXball;
    }

    public int getPosYball() {
        return posYball;
    }

    public void setPosYball(int posYball) {
        this.posYball = posYball;
    }

    public int getBullets() {
        return bullets;
    }

    public void setBullets(int bullets) {
        this.bullets = bullets;
    }
}



