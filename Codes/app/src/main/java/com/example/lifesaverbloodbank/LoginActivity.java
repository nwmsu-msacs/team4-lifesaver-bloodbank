package com.example.lifesaverbloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    private Button loginBTN;
    private Button signupBTN;
    private EditText emailET;
    private EditText passwordET;
    private TextView forgetTV;
    public static String userName, emailId = "";
    public static boolean isUserLogin = false;
    FirebaseAuth fAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailET = (EditText) findViewById(R.id.emailET);
        passwordET = (EditText) findViewById(R.id.passwordET);
        loginBTN = (Button) findViewById(R.id.loginBTN);
        signupBTN = (Button) findViewById(R.id.signupBTN);
        forgetTV = (TextView) findViewById(R.id.forgetTV);
        fAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                if (emailET.length() == 0) {
                    Toast.makeText(getApplicationContext(), "username must be filled", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (passwordET.length() == 0) {
                    Toast.makeText(getApplicationContext(), "password must be filled", Toast.LENGTH_SHORT).show();
                    return;
                }

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            isUserLogin = true;
                            //getUserName(email);
                            emailId = email;
                            Toast.makeText(getApplicationContext(), "Logged in Successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Entered Email or password does not match", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
            }
        });
        signupBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        forgetTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, forgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}
