package com.example.taskassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateNewTaskActivity extends AppCompatActivity {

    EditText createNewTask;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_task);
        createNewTask = findViewById(R.id.create_new_task);
        save = findViewById(R.id.button_save_task);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
//                Intent intent = new Intent(CreateNewTaskActivity.this, MainActivity.class);
//                startActivity(intent);
            }
        });
    }
}
