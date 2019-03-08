package com.example.came.cameselleabreujavier_proyecto;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import static com.example.came.cameselleabreujavier_proyecto.Character.metres;

public class Save extends Scene {

    private Utils u;
    private Context context;
    private Paint pText, pText2, pRect, pText3;
    private int screenWidth, screenHeight, flCont, slCont, tlCont;
    private Bitmap bitmapAux, up, down, bitmapButton,dropDB;
    private String letter;
    private Rect flUp, slUp, tlUp, flDown, slDown, tlDown, fl, sl, tl, button,dropTable;

    public Save(Context context, int idEscena, int screenWidth, int screenHeight) {
        super(context, idEscena, screenWidth, screenHeight);
        this.context = context;
        u = new Utils(context);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        bitmapButton = BitmapFactory.decodeResource(context.getResources(), R.drawable.button);
        bitmapButton = Bitmap.createScaledBitmap(bitmapButton, screenWidth * 2 / 10, screenHeight * 2 / 7, false);
        button = new Rect(screenWidth * 7 / 10, screenHeight * 3 / 7, screenWidth * 9 / 10, screenHeight * 5 / 7);
        dropDB = BitmapFactory.decodeResource(context.getResources(), R.drawable.droptable);
        dropDB = Bitmap.createScaledBitmap(dropDB, screenWidth/ 10, screenHeight *7, false);
        dropTable=new Rect(screenWidth*9/10,screenHeight * 6 / 7, screenWidth, screenHeight);

        up = BitmapFactory.decodeResource(context.getResources(), R.drawable.up);
        up = Bitmap.createScaledBitmap(up, screenWidth / 10, screenHeight / 8, false);

        down = BitmapFactory.decodeResource(context.getResources(), R.drawable.down);
        down = Bitmap.createScaledBitmap(down, screenWidth / 10, screenHeight / 8, false);

        flUp = new Rect(screenWidth * 1 / 10, screenHeight * 2 / 7, screenWidth * 1 / 10 + up.getWidth(), screenHeight * 2 / 7 + up.getHeight());
        flDown = new Rect(screenWidth * 1 / 10, screenHeight * 5 / 7, screenWidth * 1 / 10 + up.getWidth(), screenHeight * 5 / 7 + up.getHeight());
        fl = new Rect(flUp.left, flUp.bottom + u.getDpH(25), flUp.right, flDown.top - u.getDpH(25));
        flCont = 0;
        slUp = new Rect(screenWidth * 3 / 10, screenHeight * 2 / 7, screenWidth * 3 / 10 + up.getWidth(), screenHeight * 2 / 7 + up.getHeight());
        slDown = new Rect(screenWidth * 3 / 10, screenHeight * 5 / 7, screenWidth * 3 / 10 + up.getWidth(), screenHeight * 5 / 7 + up.getHeight());
        sl = new Rect(slUp.left, slUp.bottom + u.getDpH(25), slUp.right, slDown.top - u.getDpH(25));
        slCont = 0;
        tlUp = new Rect(screenWidth * 5 / 10, screenHeight * 2 / 7, screenWidth * 5 / 10 + up.getWidth(), screenHeight * 2 / 7 + up.getHeight());
        tlDown = new Rect(screenWidth * 5 / 10, screenHeight * 5 / 7, screenWidth * 5 / 10 + up.getWidth(), screenHeight * 5 / 7 + up.getHeight());
        tl = new Rect(tlUp.left, tlUp.bottom + u.getDpH(25), tlUp.right, tlDown.top - u.getDpH(25));
        tlCont = 0;
        letter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        pRect = new Paint();
        pRect.setColor(Color.GREEN);
        pRect.setStyle(Paint.Style.STROKE);
        pRect.setStrokeWidth(5);

        pText = new Paint();
        pText.setColor(Color.BLACK);
        pText.setTextSize(u.getDpW(200));
        pText.setTextAlign(Paint.Align.CENTER);

        pText3 = new Paint();
        pText3.setColor(Color.BLACK);
        pText3.setTextSize(u.getDpW(100));
        pText3.setTextAlign(Paint.Align.CENTER);

        pText2 = new Paint();
        pText2.setColor(Color.BLACK);
        pText2.setTextSize(u.getDpW(100));


    }

    public void acualizarFisica() {
    }

    public void dibujar(Canvas c) {
        c.drawColor(Color.argb(50, 255, 255, 204));
//        c.drawBitmap(fondo, 0, 0, null);

//        Log.i("xxxx", "METROS:" + metres + "  - ancho:" + screenWidth + " - alto:" + screenHeight + " - ancho/10:" + screenWidth / 10 + " - alto/10:" + screenHeight);
        c.drawBitmap(up, flUp.centerX() - up.getWidth() / 2, flUp.centerY() - up.getHeight() / 2, null);
//        c.drawRect(fl,pRect);
        c.drawText(letter.charAt(flCont) + "", fl.centerX(), fl.centerY(), pText);
        c.drawBitmap(down, flDown.centerX() - down.getWidth() / 2, flDown.centerY() - down.getHeight() / 2, null);

        c.drawBitmap(up, slUp.centerX() - up.getWidth() / 2, slUp.centerY() - up.getHeight() / 2, null);
//        c.drawRect(sl,pRect);
        c.drawText(letter.charAt(slCont) + "", sl.centerX(), sl.centerY(), pText);
        c.drawBitmap(down, slDown.centerX() - down.getWidth() / 2, slDown.centerY() - down.getHeight() / 2, null);

        c.drawBitmap(up, tlUp.centerX() - up.getWidth() / 2, tlUp.centerY() - up.getHeight() / 2, null);
//        c.drawRect(tl,pRect);
        c.drawText(letter.charAt(tlCont) + "", tl.centerX(), tl.centerY(), pText);
        c.drawBitmap(down, tlDown.centerX() - down.getWidth() / 2, tlDown.centerY() - down.getHeight() / 2, null);

        c.drawBitmap(bitmapButton, button.centerX() - bitmapButton.getWidth() / 2, button.centerY() - bitmapButton.getHeight() / 2, null);
        c.drawText(context.getString(R.string.submit), button.centerX(), button.centerY(), pText3);

        c.drawText(context.getString(R.string.distance) + ": " + metres + " m", u.getDpW(screenWidth) * 2 / 5, u.getDpH(100), pText2);

        super.dibujar(c);
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
            case MotionEvent.ACTION_POINTER_UP:  // Al levantar un dedo que no es el último
                if (pulsa(flUp, event)) {
                    if (flCont != 0) {
                        flCont--;
                    } else {
                        flCont = letter.length() - 1;
                    }
                } else if (pulsa(flDown, event)) {
                    if (flCont < letter.length() - 1) {
                        flCont++;
                    } else {
                        flCont = 0;
                    }
                }
                if (pulsa(slUp, event)) {
                    if (slCont != 0) {
                        slCont--;
                    } else {
                        slCont = letter.length() - 1;
                    }
                } else if (pulsa(slDown, event)) {
                    if (slCont < letter.length() - 1) {
                        slCont++;
                    } else {
                        slCont = 0;
                    }

                }
                if (pulsa(tlUp, event)) {
                    if (tlCont != 0) {
                        tlCont--;
                    } else {
                        tlCont = letter.length() - 1;
                    }
                } else if (pulsa(tlDown, event)) {
                    if (tlCont < letter.length() - 1) {
                        tlCont++;
                    } else {
                        tlCont = 0;
                    }
                }

                if (pulsa(button, event)) {
                    DataBase db = new DataBase(context, "records", null, 1);
                    SQLiteDatabase database = db.getWritableDatabase();
                    String query = "INSERT INTO records (name,distance) VALUES ('" + letter.charAt(flCont) + letter.charAt(slCont) + letter.charAt(tlCont) + "'," + metres + ")";
                    database.execSQL(query);
                    if (database != null) {
                        database.close();
                    }
                    return 0;
                }
                break;

            case MotionEvent.ACTION_MOVE: // Se mueve alguno de los dedos

                break;
            default:
                Log.i("Otra acción", "Acción no definida: " + accion);
        }

        int idPadre = super.onTouchEvent(event);
        if (idPadre != idEscena) {
            return idPadre;
        }
        return idEscena;
    }

}
