package com.example.taskassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateNewTaskActivity extends AppCompatActivity {

    EditText createNewTask;
    Button save;
    private FirebaseUser currentUser;
    private DatabaseReference myRef;
    int taskId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_task);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();

        createNewTask = findViewById(R.id.create_new_task);
        save = findViewById(R.id.button_save_task);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTaskInFirebase(createNewTask.getText().toString());
//                finish();
//                Intent intent = new Intent(CreateNewTaskActivity.this, MainActivity.class);
//                startActivity(intent);
            }
        });
    }

    public void createTaskInFirebase(String taskCreated) {
        Task task = new Task(taskCreated, currentUser.getUid());
        myRef.child("tasks").child(String.valueOf(taskId)).setValue(task);
        taskId++;
    }
}
