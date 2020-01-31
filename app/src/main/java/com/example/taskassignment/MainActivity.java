package com.example.taskassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mAuth = FirebaseAuth.getInstance();
//        myRef = FirebaseDatabase.getInstance().getReference("users");
//
//        // Check if User is already logged in
//        currentUser = mAuth.getCurrentUser();
//        if(currentUser != null) {
//            onAuthSuccess(currentUser);
//        }
//        else {
//            finish();
//            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
//            startActivity(intent);
//        }
        Log.e("TEST", "Test commit");
    }

    private void onAuthSuccess(FirebaseUser user) {
        // Read from the database
        myRef.child(user.getUid()).child("role").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String role = dataSnapshot.getValue(String.class);
                // Go to MainActivity
                finish();
                if (role.equalsIgnoreCase("Manager")) {
                    Intent intent = new Intent(MainActivity.this, ManagerActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, EmployeeActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }
}
