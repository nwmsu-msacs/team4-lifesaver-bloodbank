package com.example.lifesaverbloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailET;
    private EditText passwordET;
    private EditText nameET;
    private Spinner getBloodgroupSP;
    private RadioGroup usertypeRG;
    private Button signinBTN,signupBTN;
    Spinner bloodgroupSP;
    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        nameET = findViewById(R.id.nameET);
        getBloodgroupSP = findViewById(R.id.bloodgroupSP);
        usertypeRG = findViewById(R.id.usertypeRG);
        signupBTN = findViewById(R.id.signupBTN);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        signupBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                final String name = nameET.getText().toString();
                final String bloodgroup = bloodgroupSP.getSelectedItem().toString();
                int selectedRB = usertypeRG.getCheckedRadioButtonId();
                String RBtext = "";
                if (selectedRB != -1) {
                    RadioButton selectedRadioButton = (RadioButton) findViewById(selectedRB);
                    RBtext = selectedRadioButton.getText().toString();
                }
                final String usertype = RBtext;


                if (email.length() == 0 || password.length() == 0 || name.length() == 0 || bloodgroup == "Blood Group" || usertypeRG.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(SignUpActivity.this,
                            "All fields must be filled", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 8 && !PasswordValidation(password)) {
                    Toast.makeText(getApplicationContext(), "password must contain minimum 8 characters with at least" +
                            "1 Alphabet,1 Number and 1 special character", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!isEmailValid(email)) {
                    Toast.makeText(getApplicationContext(), "Email address should be of valid format", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
    }
    public static boolean PasswordValidation(String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isEmailValid(final String email) {
        final String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}