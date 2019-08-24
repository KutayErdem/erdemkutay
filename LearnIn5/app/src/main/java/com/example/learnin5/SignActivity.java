package com.example.learnin5;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignActivity extends AppCompatActivity {

    private EditText name;
    private EditText surname;
    private EditText email;
    private EditText username;
    private EditText password;
    private Button signup;
    private Button cancel;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        name = (EditText)findViewById(R.id.name);
        surname = (EditText)findViewById(R.id.surname);
        email = (EditText)findViewById(R.id.email);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        signup = (Button)findViewById(R.id.signbutton);
        cancel = (Button)findViewById(R.id.cancelbutton);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = true;
                sharedpreferences = getSharedPreferences("Members", Context.MODE_PRIVATE);
                if ( sharedpreferences.contains(username.getText().toString()) &&
                        sharedpreferences.contains(password.getText().toString())){
                    username.setSelectAllOnFocus(true);
                    password.setSelectAllOnFocus(true);
                    username.selectAll();
                    password.selectAll();
                    username.setError("invalid username");
                    password.setError("invalid password");
                }
                else if (sharedpreferences.contains(username.getText().toString())) {
                    username.setSelectAllOnFocus(true);
                    username.selectAll();
                    username.setError("invalid username");
                }
                else if (sharedpreferences.contains(password.getText().toString())) {
                    password.setSelectAllOnFocus(true);
                    password.selectAll();
                    password.setError("invalid password");
                }
                else {
                    if (name.getText().toString().isEmpty()) {
                        name.setError("enter your name");
                        valid = false;
                    }
                    if (surname.getText().toString().isEmpty() ) {
                        surname.setError("enter your surname");
                        valid = false;
                    }
                    if (email.getText().toString().isEmpty() ||
                            !android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                        email.setSelectAllOnFocus(true);
                        email.selectAll();
                        email.setError("enter a valid email address");
                        valid = false;
                    }
                    if (username.getText().toString().isEmpty() || username.length() < 3) {
                        username.setSelectAllOnFocus(true);
                        username.selectAll();
                        username.setError("at least 3 characters");
                        valid = false;
                    }
                    if (password.getText().toString().isEmpty() || password.length() < 4 || password.length() > 10) {
                        password.setSelectAllOnFocus(true);
                        password.selectAll();
                        password.setError("between 4 and 10 characters");
                        valid = false;
                    }
                    if (valid == true) {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(password.getText().toString() + password.getText().toString(),
                                name.getText().toString() + " " + surname.getText().toString());
                        editor.putString(password.getText().toString() + password.getText().toString() + password.getText().toString(),
                                email.getText().toString());
                        editor.putString(username.getText().toString(), password.getText().toString());
                        editor.putString(password.getText().toString(), password.getText().toString());
                        editor.commit();
                        Toast.makeText(getApplicationContext(),
                                "Creating Account...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignActivity.this, MainActivity.class));
                    }
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*sharedpreferences = getSharedPreferences("Members", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();*/
                startActivity(new Intent(SignActivity.this, MainActivity.class));
            }
        });
    }

}
