package uk.ac.standrews.crashalert;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.content.Intent;

import android.view.View;

import android.widget.EditText;
import android.widget.TextView;

import uk.ac.standrews.crashalert.R;

public class NameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        Button button4 = (Button) findViewById(R.id.button4);

        final SharedPreferences settings = getPreferences(0);

        button4.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.editText);
                String text = editText.getText().toString();
                settings.edit().putString("name", text).apply();
                goToPhoneNumber();

            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    private void goToPhoneNumber() {

        Intent intent = new Intent(this, PhoneNumberActivity.class);

        startActivity(intent);
        finish();
    }

}
