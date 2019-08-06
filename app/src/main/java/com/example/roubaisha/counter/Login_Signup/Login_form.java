package com.example.roubaisha.counter.Login_Signup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.roubaisha.counter.R;

public class Login_form extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        getSupportActionBar().setTitle("Login Form");
    }

    public void btn_signupForm(View view) {
        startActivity(new Intent(getApplicationContext(),Signup_form.class));
    }
}
