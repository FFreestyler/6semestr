package ru.belonogov.bugg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private GameView view;
    private Handler handler;
    private final static int interval = 1000 / 60;  // 60 fps

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new GameView(this);
        setContentView(view);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        handler = new Handler();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.invalidate();
                    }
                });
            }
        }, 0, interval);
    }
}