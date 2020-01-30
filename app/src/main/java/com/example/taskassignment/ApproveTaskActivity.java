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

public class ApproveTaskActivity extends AppCompatActivity {

    TextView taskName, requestedByUser, timeStamp;
    Button approveTask, reject;
    String taskId, fragmentName, assignToUser;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef, myUserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_task);
        Bundle bundle = getIntent().getExtras();
        taskId = bundle.getString("taskId");
        fragmentName = bundle.getString("fragment");

        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("tasks");
        myUserRef = FirebaseDatabase.getInstance().getReference("users");
        taskName = findViewById(R.id.task_approve);
        requestedByUser = findViewById(R.id.requested_by_user_approve);
        timeStamp = findViewById(R.id.timestamp_approve);
        approveTask = findViewById(R.id.approve_task_button);
        reject = findViewById(R.id.reject_task_button);

        myRef.child(taskId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.;
                Task task = dataSnapshot.getValue(Task.class);
                if(task != null) {
                    taskName.setText(task.taskName);
                    if(fragmentName.equalsIgnoreCase("pending")) {
                        getUserEmail(task.requestedByUser);
                    }
                    else {
                        getUserEmail(task.assignedToUser);
                    }
                    timeStamp.setText(task.timeStampTask);
                    assignToUser = task.requestedByUser;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
        approveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Write to database
                myRef.child(taskId).child("requestedByUser").setValue("");
                myRef.child(taskId).child("assignedToUser").setValue(assignToUser);
                myRef.child(taskId).child("taskStatus").setValue("Approved");
                Toast.makeText(getApplicationContext(), "Approved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child(taskId).child("requestedByUser").setValue("");
                myRef.child(taskId).child("assignedToUser").setValue("");
                Toast.makeText(getApplicationContext(), "Rejected", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        if(!fragmentName.equalsIgnoreCase("pending")) {
            approveTask.setVisibility(View.INVISIBLE);
            reject.setVisibility(View.INVISIBLE);
        }
    }

    public void getUserEmail(String requestedBy) {
        myUserRef.child(requestedBy).child("email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userEmail = dataSnapshot.getValue(String.class);
                if(userEmail != null) {
                    requestedByUser.setText(userEmail);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
