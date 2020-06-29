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
        signinBTN = findViewById(R.id.signinBTN);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        signinBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

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
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            if (usertype.equalsIgnoreCase("Donor") || usertype.equalsIgnoreCase("Acceptor")) {
                                Toast.makeText(getApplicationContext(), "Registered Successfully!", Toast.LENGTH_SHORT).show();
                                DocumentReference documentReference = firebaseFirestore.collection("Users List").document(email);
                                Map<String, Object> user = new HashMap<>();
                                user.put("Name", name);
                                user.put("EmailId", email);
                                user.put("Blood Group", bloodgroupSP.getSelectedItem().toString());
                                user.put("User Type", usertype);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("TAG", "user data created" + userId);
                                    }
                                });
                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        }
                        else {
                            //progressBar.setVisibility(View.GONE);
                            Log.e("TAG", "onComplete: Failed=" + task.getException().getMessage());
                            Toast.makeText(SignUpActivity.this,"could not register",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        bloodgroupSP = findViewById(R.id.bloodgroupSP);
        List<String> bloodgroups = new ArrayList<>();
        bloodgroups.add("Blood Group");
        bloodgroups.add("AB+");
        bloodgroups.add("AB-");
        bloodgroups.add("A+");
        bloodgroups.add("A-");
        bloodgroups.add("B+");
        bloodgroups.add("B-");
        bloodgroups.add("O+");
        bloodgroups.add("O-");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, bloodgroups);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodgroupSP.setAdapter(arrayAdapter);
    }
    public void onRadioButtonClicked(View v) {
        // Is the button now checked?
        boolean checked = ((RadioButton) v).isChecked();

        // Check which radio button was clicked
        switch(v.getId()) {
            case R.id.donor:
                if (checked)
                    Toast.makeText(SignUpActivity.this," You selected donor",Toast.LENGTH_SHORT).show();
                break;
            case R.id.acceptor:
                if (checked)
                    Toast.makeText(SignUpActivity.this,"You selected acceptor",Toast.LENGTH_SHORT).show();
                break;
        }
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