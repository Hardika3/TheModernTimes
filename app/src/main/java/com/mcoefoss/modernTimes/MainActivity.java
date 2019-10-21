package com.mcoefoss.modernTimes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = findViewById(R.id.login_button_id);
        final EditText userName = findViewById(R.id.login_username);
        final EditText password = findViewById(R.id.login_password);
        TextView signup_button = findViewById(R.id.signup_text);

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup_intent = new Intent(MainActivity.this,signup.class);
                startActivity(signup_intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Logging you In Please Wait",Toast.LENGTH_SHORT).show();
                if(loggedIn(userName.getText().toString(),password.getText().toString())) {
                    Intent login_intent = new Intent(MainActivity.this, Main2Activity.class);
                    finish();
                    startActivity(login_intent);
                }
            }
        });
    }
    boolean loggedIn(String user,String pass){
        try {
            if (ParseUser.logIn(user, pass) != null)    return true;
        }catch(ParseException e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return false;
    }
}
