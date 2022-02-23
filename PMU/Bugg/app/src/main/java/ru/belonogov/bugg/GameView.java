package ru.belonogov.bugg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {
    private Bitmap background;
    private Paint endgameWin;
    private Paint endgameLose;
    private Paint score;

    BugLogic bugLogic;
    Paint style;
    boolean isStop = false;
    String str = "";
    long timer = 0;

    public GameView(Context context) {
        super(context);
        this.bugLogic = new BugLogic(5, this);

        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.wallpaper);
        score = new Paint();
        score.setColor(Color.WHITE);
        score.setTextAlign(Paint.Align.CENTER);
        score.setTextSize(75);
        score.setTypeface(Typeface.DEFAULT_BOLD);
        score.setAntiAlias(true);

        endgameLose = new Paint();
        endgameLose.setColor(Color.WHITE);
        endgameLose.setTextAlign(Paint.Align.CENTER);
        endgameLose.setTextSize(120);
        endgameLose.setTypeface(Typeface.DEFAULT_BOLD);
        endgameLose.setAntiAlias(true);

        endgameWin = new Paint();
        endgameWin.setColor(Color.WHITE);
        endgameWin.setTextAlign(Paint.Align.CENTER);
        endgameWin.setTextSize(120);
        endgameWin.setTypeface(Typeface.DEFAULT_BOLD);
        endgameWin.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isStop){
            canvas.drawBitmap(background, 0, 0, null);
            canvas.drawText(str, getWidth() / (float) 2, 200, style);
            if (System.currentTimeMillis() > timer + 4000)
            {
                isStop = false;
                bugLogic.points = 0;
            }
            return;
        }
        if (bugLogic.points < 500 && bugLogic.points >= -20) {
            super.onDraw(canvas);
            bugLogic.update();
            int sc = bugLogic.points;
            canvas.drawBitmap(background, 0, 0, null);
            canvas.drawText("СЧЁТ: " + sc, getWidth() / (float) 2, 80, score);
            bugLogic.drawBugs(canvas);
        }else if (bugLogic.points>=150)
        {
            timer = System.currentTimeMillis();
            isStop = true;
            str = "ТЫ ВЫИГРАЛ!";
            style = endgameWin;

        }else
        {
            timer = System.currentTimeMillis();
            isStop = true;
            str = "ТЫ ЖУК!";
            style = endgameLose;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float eventX = event.getX();
            float eventY = event.getY();
            bugLogic.touchEvent(eventX, eventY);
        }
        return true;
    }
}
