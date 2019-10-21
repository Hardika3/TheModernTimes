package com.mcoefoss.modernTimes;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseUser;

public class Init extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Parse.enableLocalDatastore(getApplicationContext());
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId(getString(R.string.parse_ID))
                .clientKey("")
                .server(getString(R.string.URL))
                .build()
        );
        setContentView(R.layout.activity_init);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(ParseUser.getCurrentUser() == null){
                    finish();
                    startActivity(new Intent(Init.this, MainActivity.class));
                }
                else{
                    Toast.makeText(getApplicationContext(), "Already logged in", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(Init.this,Main2Activity.class);
                    finish();
                    startActivity(in);

                }
            }
        }, 1500);
    }
}
