package org.o7planning.lab3;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class Unit {
    boolean isRun;
    Matrix matrix;
    boolean unitAlive;
    Bitmap unitTexture;
    Float x;
    Float y;
    Float stepX;
    Float stepY;
    Float destX;
    Float destY;
    Integer p;

    Unit() {
        matrix = new Matrix();
        x = 0f;
        y = 0f;
        p = 0;
        destX = 0f;
        destY = 0f;
        unitAlive = true;
    }

    void unitNotAlive() {
        unitAlive = false;
    }
}
