package com.example.lifesaverbloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPasswordActivity extends AppCompatActivity {
    ProgressBar progressBar1;
    Button sendBTN;
    EditText EmailTxt;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        sendBTN = (Button) findViewById(R.id.sendBTN);
        EmailTxt = (EditText) findViewById(R.id.EmailTxt);
        progressBar1 = findViewById(R.id.progressBar);
        firebaseAuth = FirebaseAuth.getInstance();
        sendBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EmailTxt.length()==0){
                    Toast.makeText(forgotPasswordActivity.this,"Please enter email address",Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar1.setVisibility(View.VISIBLE);
                firebaseAuth.sendPasswordResetEmail(EmailTxt.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(forgotPasswordActivity.this,"reset link sent to your registered email",Toast.LENGTH_SHORT).show();
                            progressBar1.setVisibility(View.GONE);
                            Intent intent2 = new Intent(forgotPasswordActivity.this,LoginActivity.class);
                            startActivity(intent2);
                        }
                        else {
                            Toast.makeText(forgotPasswordActivity.this,"entered email is not registered",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}