package com.example.taskassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {

    EditText email, password;
    Button signIn, newUser;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    String TAG = "Sign";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("users");
        signIn = findViewById(R.id.button_sign_in);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = findViewById(R.id.email_sign_in);
                password = findViewById(R.id.password_sign_in);
                Toast.makeText(SignInActivity.this, "Authentication processing.",
                        Toast.LENGTH_SHORT).show();
                if(checkCredentials(email.getText().toString(), password.getText().toString())) {
                    signIn(email.getText().toString(), password.getText().toString());
                }
                else {
                    Toast.makeText(SignInActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        newUser = findViewById(R.id.button_new_user);
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SignInActivity.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            onAuthSuccess(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public boolean checkCredentials(String email, String password) {
        if(email == null || password == null) {
            return false;
        }
        else if(password.length() < 8) {
            return false;
        }
        else return true;
    }

    private void onAuthSuccess(FirebaseUser user) {
        // Read from the database
        myRef.child(user.getUid()).child("role").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String role = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + role);
                // Go to MainActivity
                finish();
                if(role.equalsIgnoreCase("Manager")) {
                    Intent intent = new Intent(SignInActivity.this, ManagerActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(SignInActivity.this, EmployeeActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

}