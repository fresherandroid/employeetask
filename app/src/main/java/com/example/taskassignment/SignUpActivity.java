package com.example.taskassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] roles = {"Select your role", "Manager", "Employee"};
    EditText email, password, confirmPassword;
    Spinner roleSelector;
    Button signUp, alreadyRegistered;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        roleSelector = findViewById(R.id.spinner_role_selector);
        roleSelector.setOnItemSelectedListener(this);
        ArrayAdapter dropDownList = new ArrayAdapter(SignUpActivity.this,android.R.layout.simple_spinner_item,roles);
        dropDownList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSelector.setAdapter(dropDownList);

        signUp = findViewById(R.id.button_sign_up);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = findViewById(R.id.email_sign_up);
                password = findViewById(R.id.password_sign_up);
                confirmPassword = findViewById(R.id.confirm_password_sign_up);
                Toast.makeText(SignUpActivity.this, "Authentication processing.",
                        Toast.LENGTH_SHORT).show();
                if(checkCredentials(email.getText().toString(), password.getText().toString(), confirmPassword.getText().toString())) {
                    createAccount(email.getText().toString(), password.getText().toString());
                }
                else {
                    Toast.makeText(SignUpActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        alreadyRegistered = findViewById(R.id.button_already_registered);
        alreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(SignUpActivity.this, roles[position], Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SignUpActivity.this, "Authentication successful.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            finish();
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public boolean checkCredentials(String email, String password, String confirmPassword) {
        if(email == null || password == null || confirmPassword == null) {
            return false;
        }
        else if(password.length() < 8) {
            return false;
        }
        else if(!password.equals(confirmPassword)) {
            return false;
        }
        else return true;
    }
}
