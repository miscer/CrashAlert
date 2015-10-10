package uk.ac.standrews.crashalert;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class CrashActivity extends AppCompatActivity {

    private final static String TAG = "CrashActivity";
    private final static int DELAY = 10;

    private CountDownTimer timer;
    private TextView countdownLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        timer = new CountDownTimer(DELAY * 1000, 1000) {
            @Override
            public void onTick(long l) {
                int seconds = (int) Math.ceil(l / 1000);
                updateSecondsLabel(seconds);
            }

            @Override
            public void onFinish() {
                updateSecondsLabel(0);
                sendMessage();
            }
        };

        countdownLabel = (TextView) findViewById(R.id.label_seconds);
        updateSecondsLabel(DELAY);

        Button cancelButton = (Button) findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        timer.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    private void updateSecondsLabel(final int seconds) {
        countdownLabel.post(new Runnable() {
            @Override
            public void run() {
                String value = String.valueOf(seconds);
                countdownLabel.setText(value);
            }
        });
    }

    private void sendMessage() {
        Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();
        finish();
    }

}
