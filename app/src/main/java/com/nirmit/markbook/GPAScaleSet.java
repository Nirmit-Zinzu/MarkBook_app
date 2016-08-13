package com.nirmit.markbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GPAScaleSet extends AppCompatActivity {


    // default gpa scale (user can change it)
    EditText aPlusGPA, aGPA, aMinusGPA,
             bPlusGPA, bGPA, bMinusGPA,
             cPlusGPA, cGPA, cMinusGPA,
             dPlusGPA, dGPA, dMinusGPA,
             fGPA;

    finalGPAScale finalGPAset;   // sets the final standard
    Button saveGPAScale;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpascale_set);

        finalGPAset = new finalGPAScale();

        aPlusGPA = (EditText) findViewById(R.id.aPlusGPA);
        aGPA = (EditText) findViewById(R.id.aGPA);
        aMinusGPA = (EditText) findViewById(R.id.aMinusGPA);

        bPlusGPA = (EditText) findViewById(R.id.bPlusGPA);
        bGPA = (EditText) findViewById(R.id.bGPA);
        bMinusGPA = (EditText) findViewById(R.id.bMinusGPA);

        cPlusGPA = (EditText) findViewById(R.id.cPlusGPA);
        cGPA = (EditText) findViewById(R.id.cGPA);
        cMinusGPA = (EditText) findViewById(R.id.cMinusGPA);

        dPlusGPA = (EditText) findViewById(R.id.dPlusGPA);
        dGPA = (EditText) findViewById(R.id.dGPA);
        dMinusGPA = (EditText) findViewById(R.id.dMinusGPA);

        fGPA = (EditText) findViewById(R.id.fGPA);

        saveGPAScale = (Button) findViewById(R.id.saveButton);

        saveGPAInfo();     // save the information (database)
        displayGPAInfo();  // display the saved information

    }


    // following method saves the gpa scale (saves it - even if app closed, data retrieved)
    public void saveGPAInfo() {
        saveGPAScale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = getSharedPreferences("GPAinfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putString("aPlus", aPlusGPA.getText().toString());
                editor.putString("a", aGPA.getText().toString());
                editor.putString("aMinus", aMinusGPA.getText().toString());

                editor.putString("bPlus", bPlusGPA.getText().toString());
                editor.putString("b", bGPA.getText().toString());
                editor.putString("bMinus", bMinusGPA.getText().toString());

                editor.putString("cPlus", cPlusGPA.getText().toString());
                editor.putString("c", cGPA.getText().toString());
                editor.putString("cMinus", cMinusGPA.getText().toString());

                editor.putString("dPlus", dPlusGPA.getText().toString());
                editor.putString("d", dGPA.getText().toString());
                editor.putString("dMinus", dMinusGPA.getText().toString());

                editor.putString("f", fGPA.getText().toString());

                setScale();   // sets final scale

                editor.apply();

                Toast.makeText(GPAScaleSet.this, "Saved", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(GPAScaleSet.this, ChoseGPAType.class);
                startActivity(intent);
            }
        });


    }

    // following method displays changed GPA scale
    public void displayGPAInfo() {
        SharedPreferences sharedPref = getSharedPreferences("GPAinfo", MODE_PRIVATE);

        aPlusGPA.setText(sharedPref.getString("aPlus", "4.0"));
        aGPA.setText(sharedPref.getString("a", "4.0"));
        aMinusGPA.setText(sharedPref.getString("aMinus", "3.7"));

        bPlusGPA.setText(sharedPref.getString("bPlus", "3.3"));
        bGPA.setText(sharedPref.getString("b", "3.0"));
        bMinusGPA.setText(sharedPref.getString("bMinus", "2.7"));

        cPlusGPA.setText(sharedPref.getString("cPlus", "2.3"));
        cGPA.setText(sharedPref.getString("c", "2.0"));
        cMinusGPA.setText(sharedPref.getString("cMinus", "1.7"));

        dPlusGPA.setText(sharedPref.getString("dPlus", "1.3"));
        dGPA.setText(sharedPref.getString("d", "1.0"));
        dMinusGPA.setText(sharedPref.getString("dMinus", "0.7 "));

        fGPA.setText(sharedPref.getString("f", "0.0"));


    }


    // sets the changed GPA scale in finalGPA method
    public void setScale() {
        finalGPAset.setaPlusGPA( Double.parseDouble(aPlusGPA.getText().toString()) );
        finalGPAset.setbPlusGPA( Double.parseDouble(bPlusGPA.getText().toString()) );
        finalGPAset.setcPlusGPA( Double.parseDouble(cPlusGPA.getText().toString()) );
        finalGPAset.setdPlusGPA( Double.parseDouble(dPlusGPA.getText().toString()) );

        finalGPAset.setaGPA( Double.parseDouble(aGPA.getText().toString()) );
        finalGPAset.setbGPA( Double.parseDouble(bGPA.getText().toString()) );
        finalGPAset.setcGPA( Double.parseDouble(cGPA.getText().toString()) );
        finalGPAset.setdGPA( Double.parseDouble(dGPA.getText().toString()) );
        finalGPAset.setfGPA( Double.parseDouble(fGPA.getText().toString()) );

        finalGPAset.setaMinusGPA( Double.parseDouble(aMinusGPA.getText().toString()) );
        finalGPAset.setbMinusGPA( Double.parseDouble(bMinusGPA.getText().toString()) );
        finalGPAset.setcMinusGPA( Double.parseDouble(cMinusGPA.getText().toString()) );
        finalGPAset.setdMinusGPA( Double.parseDouble(dMinusGPA.getText().toString()) );
    }




}
