package ru.belonogov.bugg;

import android.graphics.Bitmap;
import android.graphics.Matrix;

class Bug {
    boolean isRunning;
    Matrix matrix;
    boolean alive;
    Bitmap texture;
    Float x, y, stepX, stepY, destX, destY;
    Integer p;


    Bug() {
        matrix = new Matrix();
        x = 0f;
        y = 0f;
        p = 0;
        destX = 0f;
        destY = 0f;
        alive = true;
    }

    void die() {
        alive = false;
    }

}
