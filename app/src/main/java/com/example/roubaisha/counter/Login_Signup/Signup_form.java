package com.example.roubaisha.counter.Login_Signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.roubaisha.counter.HomeActivity;
import com.example.roubaisha.counter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup_form extends AppCompatActivity {

    EditText txt_fullname, txt_email, txt_password;
    Button btn_register;
    RadioButton radio_male, radio_female;
    String gender="";

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);

        txt_fullname = (EditText) findViewById(R.id.txt_fullname);
        txt_email = (EditText) findViewById(R.id.txt_email);
        txt_password = (EditText) findViewById(R.id.txt_password);
        btn_register = (Button) findViewById(R.id.btn_register);
        radio_male = (RadioButton) findViewById(R.id.radio_male);
        radio_female = (RadioButton) findViewById(R.id.radio_female);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String fullname = txt_fullname.getText().toString();
                final String email = txt_email.getText().toString();
                String password = txt_password.getText().toString();

                if(radio_male.isChecked()){
                    gender = "Male";
                    if (radio_female.isChecked()){
                        gender = "Female";
                    }

                    if (TextUtils.isEmpty(email)){
                        Toast.makeText(Signup_form.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    }
                    if (TextUtils.isEmpty(password)){
                        Toast.makeText(Signup_form.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    }
                    if (TextUtils.isEmpty(fullname)){
                        Toast.makeText(Signup_form.this, "Please Enter FullName", Toast.LENGTH_SHORT).show();
                    }
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Signup_form.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        Users information = new Users(fullname, email, gender);
                                        FirebaseDatabase.getInstance().getReference("Users")
                                                .child(FirebaseAuth.getInstance().getCurrentUser()
                                                        .getUid()).setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(Signup_form.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                            }
                                        });

                                    } else {
                                    }

                                    // ...
                                }
                            });
                }
            }
        });

    }
}
