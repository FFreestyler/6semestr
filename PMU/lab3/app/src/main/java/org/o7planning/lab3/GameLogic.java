package org.o7planning.lab3;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

class GameLogic {
    public View view;
    private ArrayList<Unit> unitList;
    private int unitCounter;
    int point;

    private void CheckLive() {
        ArrayList<Unit> newUnitList = new ArrayList<>(5);
        for(Unit unit : unitList) {
            if (unit.unitAlive) {
                newUnitList.add(unit);
            }
        }
        unitList = newUnitList;
    }

    void drawUnit(Canvas canvas) {
        for(Unit unit : unitList) {
            canvas.drawBitmap(unit.unitTexture, unit.matrix, null);
        }
    }

    private void createUnit() {
        Unit unit = new Unit();
        switch(ThreadLocalRandom.current().nextInt(0, 2)) {
            case 0:
                unit.unitTexture = BitmapFactory.decodeResource(view.getResources(), R.drawable.bugorange);
                break;
            case 1:
                unit.unitTexture = BitmapFactory.decodeResource(view.getResources(), R.drawable.bugred);
                break;
            case 2:
                unit.unitTexture = BitmapFactory.decodeResource(view.getResources(), R.drawable.bugcyan);
        }
        unitList.add(unit);
        unit.matrix.setRotate(0, unit.unitTexture.getWidth(), unit.unitTexture.getHeight());
        unit.matrix.reset();
        unit.p = 0;
        unit.isRun = false;
        float ty, tx;
        int temp = (int) Math.floor(Math.random() * 4);
        switch (temp) {
            case 0:
                ty = (float) Math.random() * view.getHeight();
                unit.x = 0f;
                unit.y = ty;
                break;
            case 1:
                ty = (float) Math.random() * view.getHeight();
                unit.x = (float) view.getWidth();
                unit.y = ty;
                break;
            case 2:
                tx = (float) Math.random() * view.getWidth();
                unit.x = tx;
                unit.y = 0f;
                break;
            case 3:
                tx = (float) Math.random() * view.getWidth();
                unit.x = tx;
                unit.y = (float) view.getHeight();
                break;
        }
        unit.matrix.postTranslate(unit.x, unit.y);
        }

        private void handleUnit(Unit unit) {
        if(!unit.isRun) {
            unit.destX = (float) Math.random() * view.getWidth();
            unit.destY = (float) Math.random() * view.getHeight();
            unit.stepX = (unit.destX - unit.x) / 57;
            unit.stepY = (unit.destY - unit.y) / 57;
            Integer tp;
            if (unit.x <= unit.destX && unit.y >= unit.destY)
                tp = (int) Math.floor(Math.toDegrees(Math.atan(Math.abs(unit.x - unit.destX) / Math.abs(unit.y - unit.destY))));
            else if (unit.x <= unit.destX && unit.y <= unit.destY)
                tp = 90 + (int) Math.floor(Math.toDegrees(Math.atan(Math.abs(unit.y - unit.destY) / Math.abs(unit.x - unit.destX))));
            else if (unit.x >= unit.destX && unit.y <= unit.destY)
                tp = 180 + (int) Math.floor(Math.toDegrees(Math.atan(Math.abs(unit.x - unit.destX) / Math.abs(unit.y - unit.destY))));
            else
                tp = 270 + (int) Math.floor(Math.toDegrees(Math.atan(Math.abs(unit.y - unit.destY) / Math.abs(unit.x - unit.destX))));
            unit.matrix.preRotate(tp - unit.p, unit.unitTexture.getWidth() / 2, unit.unitTexture.getHeight() / 2);
            unit.p = tp;
            unit.isRun = true;
        } else {
            if (Math.abs(unit.x - unit.destX) < 0.1 && Math.abs(unit.y - unit.destY) < 0.1) {
                unit.isRun = false;
            }
            unit.matrix.postTranslate(unit.stepX, unit.stepY);
            unit.x += unit.stepX;
            unit.y += unit.stepY;
        }
    }

    void touchEvent(float x, float y) {
        boolean touch = false;
        Integer hitCounter = 0;
        for(Unit unit : unitList) {
            if(Math.abs(unit.x - x + 60) < 140
                    && Math.abs(unit.y - y + 60) < 150) {
                unit.unitNotAlive();
                point++;
                touch = true;
                break;
            }
        }
        if(!touch) {
            point--;
        }
    }

    GameLogic(int unitCounter, View view) {
        point = 0;
        this.view = view;
        this.unitCounter = unitCounter;
        unitList = new ArrayList<>(5);
    }

    void update() {
        CheckLive();
        while (unitList.size() < unitCounter) {
            createUnit();
        }
        for(final Unit unit : unitList) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    handleUnit(unit);
                }
            }).start();
        }
    }
}
