package org.o7planning.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private ImageView image;
    private float rotate = 0f;
    private SensorManager sensorManager;
    TextView degreeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = (ImageView) findViewById(R.id.imageView2);
        degreeText = (TextView) findViewById(R.id.textView);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float degree = Math.round(sensorEvent.values[0]);
        degreeText.setText(degree + "");

        RotateAnimation rotateAnim = new RotateAnimation(
                rotate,
                -degree,
                Animation.RELATIVE_TO_SELF,
                0.5F,
                Animation.RELATIVE_TO_SELF,
                0.5F
        );

        rotateAnim.setDuration(1000);
        rotateAnim.setFillAfter(true);

        image.startAnimation(rotateAnim);
        rotate = -degree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}