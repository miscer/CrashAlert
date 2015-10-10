package uk.ac.standrews.crashalert;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences settings = getPreferences(0);
        boolean setup = settings.getBoolean("setup", false);

        if (setup) {
            Intent intent = new Intent(this, StartActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, DescriptionActivity.class);
            startActivity(intent);
        }
    }
}
