package uk.ac.standrews.crashalert;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private TextView text;

    private final double CRASH_LIMIT = 20;
    private boolean crashed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        text = (TextView) findViewById(R.id.sensor_value);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (crashed) return;

        double length = Math.sqrt(
                Math.pow(event.values[0], 2) +
                Math.pow(event.values[1], 2) +
                Math.pow(event.values[2], 2)
        );

        String label = String.valueOf(length);
        text.setText(label);

        if (length > CRASH_LIMIT) {
            crashed = true;
            showCancelNotification();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void showCancelNotification() {
        Notification notification = new NotificationCompat.Builder(this)
            .setContentTitle("CrashAlert")
            .setContentText("Tap to cancel alert")
            .setSmallIcon(android.R.drawable.alert_light_frame)
            .setPriority(Notification.PRIORITY_MAX)
            .build();

        NotificationManager manager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        manager.notify(0, notification);
    }
}
