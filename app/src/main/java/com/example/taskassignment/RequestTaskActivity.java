package com.example.taskassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RequestTaskActivity extends AppCompatActivity {

    TextView taskName, createdByUser, timeStamp;
    Button requestForTask;
    String taskId;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef, myUserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_task);
        Bundle bundle = getIntent().getExtras();
        taskId = bundle.getString("taskId");

        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("tasks");
        myUserRef = FirebaseDatabase.getInstance().getReference("users");
        taskName = findViewById(R.id.task_request);
        createdByUser = findViewById(R.id.created_by_user_request);
        timeStamp = findViewById(R.id.timestamp_request);
        requestForTask = findViewById(R.id.request_for_task_button);

        myRef.child(taskId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.;
                Task task = dataSnapshot.getValue(Task.class);
                if(task != null) {
                    taskName.setText(task.taskName);
                    getUserEmail(task.createdByUser);
                    timeStamp.setText(task.timeStampTask);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        requestForTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Write to database
                myRef.child(taskId).child("requestedByUser").setValue(mAuth.getCurrentUser().getUid());
                Toast.makeText(getApplicationContext(), "Request Successful", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getUserEmail(String createdBy) {
        myUserRef.child(createdBy).child("email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userEmail = dataSnapshot.getValue(String.class);
                createdByUser.setText(userEmail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
