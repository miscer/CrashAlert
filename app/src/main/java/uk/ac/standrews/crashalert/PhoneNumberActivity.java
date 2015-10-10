package uk.ac.standrews.crashalert;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import android.view.View;

import android.widget.EditText;
import android.widget.TextView;

import uk.ac.standrews.crashalert.R;

public class PhoneNumberActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);

        Button button2 = (Button) findViewById(R.id.button2);

        final SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);

        button2.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                EditText number = (EditText) findViewById(R.id.editText2);
                String value = number.getText().toString();
                settings.edit().putString("number", value).apply();
                goToMedical();

            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    private void goToMedical() {

        Intent intent = new Intent(this, MedicalActivity.class);

        startActivity(intent);
        finish();

    }
}
