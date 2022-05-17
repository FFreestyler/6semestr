package org.o7planning.lab3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class GameClass extends View {
    private Bitmap background;
    private Paint statusGameWin;
    private Paint statusGameLose;
    private Paint score;

    GameLogic gameLogic;
    Paint style;
    boolean isStop = false;
    String str = "";
    long timer = 0;

    public GameClass(Context context) {
        super(context);
        this.gameLogic = new GameLogic(5, this);

        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);

        score = new Paint();
        score.setColor(Color.WHITE);
        score.setTextSize(50);
        score.setAntiAlias(true);

        statusGameWin = new Paint();
        statusGameWin.setTextSize(100);
        statusGameWin.setColor(Color.WHITE);
        statusGameWin.setTextAlign(Paint.Align.CENTER);
        statusGameWin.setAntiAlias(true);

        statusGameLose = new Paint();
        statusGameLose.setTextSize(100);
        statusGameLose.setColor(Color.WHITE);
        statusGameLose.setTextAlign(Paint.Align.CENTER);
        statusGameLose.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(isStop) {
            canvas.drawBitmap(background, 0, 0, null);
            canvas.drawText(str, getWidth() / 2F, getHeight() / 2F, style);
            if(System.currentTimeMillis() > timer + 5000) {
                isStop = false;
                gameLogic.point = 0;
            }
            return;
        }
        if(gameLogic.point < 20 && gameLogic.point >= -10) {
            super.onDraw(canvas);
            gameLogic.update();
            int scoreCount = gameLogic.point;
            canvas.drawBitmap(background, 0, 0, null);
            canvas.drawText("SCORE " + scoreCount, 30, 80, score);
            gameLogic.drawUnit(canvas);
        } else if(gameLogic.point>=20) {
            timer = System.currentTimeMillis();
            isStop = true;
            str = "Win!";
            style = statusGameWin;
        } else {
            timer = System.currentTimeMillis();
            isStop = true;
            str = "Lose!";
            style = statusGameLose;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            float eventX = event.getX();
            float eventY = event.getY();
            gameLogic.touchEvent(eventX, eventY);
        }
        return true;
    }
}
