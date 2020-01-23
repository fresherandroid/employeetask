package com.example.taskassignment.ui.home;

import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.taskassignment.EmployeeActivity;
import com.example.taskassignment.ManagerActivity;
import com.example.taskassignment.SignInActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("tasks");
        myRef.child(String.valueOf(0)).child("task").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String task = dataSnapshot.getValue(String.class);
                mText.setValue(task);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    public LiveData<String> getText() {
        return mText;
    }
}