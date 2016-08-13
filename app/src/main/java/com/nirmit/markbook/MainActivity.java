package com.nirmit.markbook;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends Activity {

    Button addMarkButton; // button
    ImageButton myLogo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //set content view AFTER ABOVE sequence (to avoid crash)
        setContentView(R.layout.activity_main);

        addMarkButton = (Button) findViewById(R.id.addMarkButton);
        myLogo = (ImageButton) findViewById(R.id.nZlogo);
        goNextActivity();
        goToMyWeb();

    }

    // takes user to the AddMarks activity when the btn is clicked
    public void goNextActivity(){
        addMarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddMarks.class);
                startActivity(intent);

            }
        });
    }

    public void goToMyWeb() {
        myLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUrl ("https://nirmit-zinzu.github.io/");
            }
        });
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}
