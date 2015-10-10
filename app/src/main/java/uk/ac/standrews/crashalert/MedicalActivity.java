package uk.ac.standrews.crashalert;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.CheckBox;
import android.content.Intent;

import android.view.View;

import android.widget.EditText;

public class MedicalActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical);

        Button button3 = (Button) findViewById(R.id.button3);

        final SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);

        button3.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                EditText editText4 = (EditText) findViewById(R.id.input_blood_type);
                String bloodType = editText4.getText().toString();
                settings.edit().putString("blood_type", bloodType).apply();

                EditText editText3 = (EditText) findViewById(R.id.input_medication);
                String medication = editText4.getText().toString();
                settings.edit().putString("medication", medication).apply();

                goToInformation();
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        boolean hiv,hep,dia;

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBox: {
                if (checked)
                    hiv = true;
                else
                    hiv = false;
                break;
            }
            case R.id.checkBox2: {
                if (checked)
                    hep = true;
                else
                     hep = false;
                break;
            }
            case R.id.checkBox3: {
                if (checked)
                    dia = true;
                else
                     dia = false;
                break;
            }
            
        }
    }
    private void goToInformation() {

        Intent intent = new Intent(this, InformationActivity.class);

        startActivity(intent);
        finish();

    }
}
