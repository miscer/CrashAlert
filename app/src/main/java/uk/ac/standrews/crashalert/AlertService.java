package uk.ac.standrews.crashalert;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class AlertService extends Service {

    private boolean started = false;
    private Notification notification;
    private PowerManager.WakeLock wakeLock;

    private Timer timer;
    private TimerTask timerTask;

    private final static String TAG = "AlertService";
    private final static int NOTIFICATION_ID = 0;

    @Override
    public void onCreate() {
        super.onCreate();

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Log.d(TAG, "Timed out");
                hideCancelNotification();
                releaseWakeLock();
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Started");

        if (started) {
            hideCancelNotification();
            releaseWakeLock();
            stopCancelTimer();
        } else {
            started = true;
            showCancelNotification();
            acquireWakeLock();
            startCancelTimer();
        }

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void showCancelNotification() {
        Intent intent = new Intent(this, AlertService.class);
        PendingIntent onClick = PendingIntent.getService(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        notification = new NotificationCompat.Builder(this)
                .setContentTitle("CrashAlert")
                .setContentText("Tap to cancel alert")
                .setContentIntent(onClick)
                .setSmallIcon(android.R.drawable.btn_plus)
                .setPriority(Notification.PRIORITY_MAX)
                .build();

        NotificationManager manager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        manager.notify(NOTIFICATION_ID, notification);
    }

    private void hideCancelNotification() {
        NotificationManager manager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        manager.cancel(NOTIFICATION_ID);
    }

    private void acquireWakeLock() {
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
        wakeLock.acquire();
    }

    private void releaseWakeLock() {
        wakeLock.release();
    }

    private void startCancelTimer() {
        timer.schedule(timerTask, 10000);
    }

    private void stopCancelTimer() {
        timerTask.cancel();
    }
}
