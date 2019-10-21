package com.mcoefoss.modernTimes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;

public class signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button register = findViewById(R.id.register_button);
        final EditText userName = findViewById(R.id.register_username);
        final EditText password = findViewById(R.id.register_password);
        final EditText email = findViewById(R.id.register_email);
        final EditText passwordC = findViewById(R.id.register_password_confirm);
        final EditText DisplayName =  findViewById(R.id.register_Display_name);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String user  = userName.getText().toString();
                    String pass  = password.getText().toString();
                    String passC = passwordC.getText().toString();
                    String Email = email.getText().toString();
                    String Name  = DisplayName.getText().toString();

                    if(user.length() == 0 || pass.length() == 0 || Email.length() == 0 || Name.length() == 0  ){
                        throw new NullPointerException("Please enter all fields");
                    }
                    if(pass.length()<8)
                        throw new NullPointerException("Password should be at least 8 characters long");

                    if (!pass.equals(passC))
                        throw new NullPointerException("Password's don't match");

                    if (signUp(user, pass, Email,Name)) {
                        ParseUser.logOut();
                        Toast.makeText(getApplicationContext(),"Login to Continue",Toast.LENGTH_LONG).show();
//                        Intent register_intent = new Intent(signup.this, MainActivity.class);
                        finish();
//                        startActivity(register_intent);
                    }
                }catch(NullPointerException e){
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    boolean signUp(String user,String pass,String email,String name){
        ParseUser User = new ParseUser();
        User.setUsername(user);
        User.setPassword(pass);
        User.setEmail(email);
        User.put("Display_name",name);
        try{
            User.signUp();
            return true;
        }catch(ParseException e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return false;
    }
}
