package com.nirmit.markbook;


import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayGPA extends AppCompatActivity {

    TextView gpaDisplay, percentageDisplay, performanceDisplay;
    ChoseGPAType gpaType;           // need this object to check which btn is pressed


    public String percentageString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_gp);

        gpaDisplay = (TextView) findViewById(R.id.gpaDisplay);
        percentageDisplay = (TextView) findViewById(R.id.percentage);
        performanceDisplay = (TextView) findViewById(R.id.performance);

        gpaType = new ChoseGPAType();  // instantiating gpaType class

        performanceDisplay.setText(gpaType.getPerformanceStatus());
        appropiateBackgroundColor();  // appropriate background is displayed based on grade
        displayAppropriateInfo();     // displays all the information on the screen

    }

    // sets appropriate background color
    public void appropiateBackgroundColor() {
        if (gpaType.getPerformanceStatus().compareToIgnoreCase("Excellent") == 0) {
            getWindow().getDecorView().setBackgroundColor(Color.parseColor("#8edab4"));
        } else if (gpaType.getPerformanceStatus().compareToIgnoreCase("Good") == 0) {
            getWindow().getDecorView().setBackgroundColor(Color.parseColor("#88c269"));
        }  else if (gpaType.getPerformanceStatus().compareToIgnoreCase("Adequate") == 0) {
            getWindow().getDecorView().setBackgroundColor(Color.parseColor("#e1e14b"));
        } else if (gpaType.getPerformanceStatus().compareToIgnoreCase("Marginal") == 0) {
            getWindow().getDecorView().setBackgroundColor(Color.parseColor("#ca9048"));
        } else if (gpaType.getPerformanceStatus().compareToIgnoreCase("Inadeqate") == 0) {
            getWindow().getDecorView().setBackgroundColor(Color.parseColor("#ce6261"));
        }
    }

    // sets appropriate text to be displayed on the screen
    public void displayAppropriateInfo() {
        if (gpaType.iscGPABtn() == true) {
            gpaDisplay.setText(Double.toString(Math.round(gpaType.getcGPA()*100.0)/100.0) );
            gpaType.setcGPABtn(false);
        } else if (gpaType.issGPABtn() == true) {
            gpaDisplay.setText(Double.toString(Math.round(gpaType.getsGPA()*100.0)/100.0) );
            gpaType.setsGPABtn(false);
        }
        percentageString = Double.toString(Math.round(gpaType.getPercentage())) + " %";
        percentageDisplay.setText(percentageString);
    }

}
